import model.Barrier;
import model.Bird;
import model.Field;
import org.junit.jupiter.api.Test;
import utils.FieldModel;
import utils.Position;

import static org.junit.jupiter.api.Assertions.*;

public class ModelTest {
    private static final int birdPositionX = Field.getWidth() / 4;

    private boolean isPassBarrier(Barrier b){
        return birdPositionX > b.getCurrentPosition() + Barrier.getWidth();
    }

    @Test
    public void BorderCrash() {
        int birdPosition = Field.getHeight() - Field.getGroundHeight();
        Field field = new Field(new FieldModel(new Position(birdPositionX, birdPosition),
                new Position(0, Field.getWidth() / 2),null, true));
        assertTrue(field.hasEnded());

        birdPosition = 0;
        field = new Field(new FieldModel(new Position(birdPositionX, birdPosition), new Position(0, Field.getWidth() / 2),
                null, true));
        assertTrue(field.hasEnded());
    }

    @Test
    public void BarrierCrash() {
        int birdPosition = Field.getHeight() / 2;
        Field field = new Field(new FieldModel(new Position(birdPositionX, birdPosition),
                new Position(birdPositionX + Bird.getWidth(), 0),null, true));
        assertTrue(field.hasEnded());
    }

    @Test
    public void UpperAngleBarrierCrash(){
        int birdPosition = Field.getHeight() / 2;
        Field field = new Field(new FieldModel(new Position(birdPositionX, birdPosition),
                new Position(birdPositionX + Bird.getWidth(), birdPosition),null, true));
        assertTrue(field.hasEnded());
    }

    @Test
    public void LowerAngleBarrierCrash(){
        int birdPosition = Field.getHeight() / 2;
        int upperY = birdPosition + Bird.getHeight() + Barrier.getSpace();
        Field field = new Field(new FieldModel(new Position(birdPositionX, birdPosition),
                new Position(birdPositionX + Bird.getWidth(), upperY),null, true));
        assertTrue(field.hasEnded());
    }
    @Test
    public void InsideBarrierCrash(){
        int birdPosition = Field.getHeight() / 3;
        Field field = new Field(new FieldModel(new Position(birdPositionX, birdPosition),
                new Position(birdPositionX + Bird.getWidth() / 2, birdPosition),null, true));
        assertTrue(field.hasEnded());

        int upperY = birdPosition + Bird.getHeight() + Barrier.getSpace();
        field = new Field(new FieldModel(new Position(birdPositionX, birdPosition),
                new Position(birdPositionX + Bird.getWidth() / 2, upperY),null, true));
        assertTrue(field.hasEnded());
    }
    @Test
    public void SuccessfulPassageBarrier(){
        int birdPosition = Field.getHeight() / 3;
        int upperY = birdPosition - 100;
        Field field = new Field(new FieldModel(new Position(birdPositionX, birdPosition),
                new Position(birdPositionX + Bird.getWidth() / 2, upperY),null, true));
        System.out.println(upperY + Barrier.getSpace());
        while (true) {
            if (field.getPrevBarrier() != null && isPassBarrier(field.getPrevBarrier())) break;
            else if (isPassBarrier(field.getCurrentBarrier())) break;
            field.update();
            if (upperY + Barrier.getSpace() - field.getBird().getY() - Bird.getHeight() <= 5){
                field.getBird().changeDirection();
            }
            if (field.getBird().getY() - upperY <= 5){
                field.getBird().changeDirection();
            }
        }
        assertFalse(field.hasEnded());
        assertEquals(field.getCurrentScore(), 1);
    }
}
