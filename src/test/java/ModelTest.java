import model.Field;
import org.junit.jupiter.api.Test;
import utils.GameObjects;
import utils.Position;

import static org.junit.jupiter.api.Assertions.*;

public class ModelTest {

    private static final GameObjects defSettings = GameObjects.readFromFile();
    private static final int birdPositionX = defSettings.birdPosition().x();
    private static final int fieldHeight = defSettings.fieldSize().height();
    private static final int birdWidth = defSettings.birdSize().width();
    private static final int birdHeight = defSettings.birdSize().height();

    private GameObjects createGameObjects(Position bird, Position barrier){
        return new GameObjects(defSettings.fieldSize(), defSettings.birdSize(), defSettings.barrierSize(),
                bird, barrier, null, 0, defSettings.groundHeight());
    }

    @Test
    public void BorderCrash() {
        Position birdPosition = new Position(birdPositionX,fieldHeight - birdHeight - defSettings.groundHeight());
        Field field = new Field(createGameObjects(birdPosition, defSettings.barrierPosition()));
        assertTrue(field.hasEnded());

        birdPosition = new Position(birdPositionX, 0);
        field = new Field(createGameObjects(birdPosition, defSettings.barrierPosition()));
        assertTrue(field.hasEnded());
    }

    @Test
    public void BarrierCrash() {
        Position birdPosition = new Position(birdPositionX, fieldHeight / 2);
        GameObjects gameObjects = createGameObjects(birdPosition, new Position(birdPositionX + birdWidth, 0));
        Field field = new Field(gameObjects);
        assertTrue(field.hasEnded());
    }

    @Test
    public void UpperAngleBarrierCrash(){
        int birdPosition = fieldHeight / 2;
        Field field = new Field(createGameObjects(new Position(birdPositionX, birdPosition),
                new Position(birdPositionX + birdWidth, birdPosition)));
        assertTrue(field.hasEnded());
    }

    @Test
    public void LowerAngleBarrierCrash(){
        int birdPosition = fieldHeight / 2;
        int upperY = birdPosition + defSettings.birdSize().height() + defSettings.barrierSize().height();
        Field field = new Field(createGameObjects(new Position(birdPositionX, birdPosition),
                new Position(birdPositionX + birdWidth, upperY)));
        assertTrue(field.hasEnded());
    }

    @Test
    public void InsideBarrierCrash(){
        int birdPosition = fieldHeight / 3;
        Field field = new Field(createGameObjects(new Position(birdPositionX, birdPosition),
                new Position(birdPositionX + birdWidth / 2, birdPosition)));
        assertTrue(field.hasEnded());

        int upperY = birdPosition + birdHeight + defSettings.barrierSize().height();
        field = new Field(createGameObjects(new Position(birdPositionX, birdPosition),
                new Position(birdPositionX + birdWidth / 2, upperY)));
        assertTrue(field.hasEnded());
    }

    @Test
    public void SuccessfulPassageBarrier(){
        /*
                    |        |
                  b
                         b   |
                    |        |
         */
        int birdPosition = fieldHeight / 3;
        int upperY = birdPosition - 100;
        GameObjects gameObjects = createGameObjects(new Position(birdPositionX, birdPosition),
                new Position(birdPositionX + birdWidth / 2, upperY));
        Field field = new Field(gameObjects);
        System.out.println(upperY + defSettings.barrierSize().height());
        while (true) {
            gameObjects = field.getGameObjects();
            if (gameObjects.prevBarrierPosition() != null && isPassBarrier(gameObjects.prevBarrierPosition().x())) break;
//            else if (isPassBarrier(gameObjects.barrierPosition().x())) break;
            field.update();
            int birdY = field.getGameObjects().birdPosition().y();
            if (upperY + defSettings.barrierSize().height() - birdY - birdHeight <= 5){
                field.changeBirdDirection();
            }
            if (birdY - upperY <= 5){
                field.changeBirdDirection();
            }
        }
        assertFalse(field.hasEnded());
        assertEquals(field.getCurrentScore(), 1);
    }

    private boolean isPassBarrier(int barrierPosition){
        return birdPositionX > barrierPosition + defSettings.barrierSize().width();
    }
}
