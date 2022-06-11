import model.Field;
import org.junit.jupiter.api.Test;
import utils.BarrierModel;
import utils.GameObjects;
import utils.Position;
import utils.Size;

import static org.junit.jupiter.api.Assertions.*;

public class ModelTest {

    private static final int BIRD_POSITION_X = 200;
    private static final int FIELD_HEIGHT = 800;
    private static final int FIELD_WIDTH = 800;
    private static final int BIRD_WIDTH = 90;
    private static final int BIRD_HEIGHT = 80;
    private static final int GROUND_HEIGHT = 95;
    private static final int BARRIER_GAP = 200;
    private static final int BARRIER_WIDTH = 120;

    private GameObjects createGameObjects(Position bird, int barrierX, int barrierGapUpperY){
        return new GameObjects(new Size(FIELD_HEIGHT, FIELD_WIDTH), new Size(BIRD_HEIGHT, BIRD_WIDTH),
                bird, new BarrierModel(barrierX, BARRIER_WIDTH, BARRIER_GAP, barrierGapUpperY), null, 0, GROUND_HEIGHT);
    }

    @Test
    public void borderCrash() {
        Position birdPosition = new Position(BIRD_POSITION_X, FIELD_HEIGHT - BIRD_HEIGHT - GROUND_HEIGHT - 1);
        Field field = new Field(createGameObjects(birdPosition, FIELD_WIDTH, 0));
        field.update();
        assertTrue(field.hasEnded());

        birdPosition = new Position(BIRD_POSITION_X, 1);
        field = new Field(createGameObjects(birdPosition, FIELD_WIDTH, 0));
        field.changeBirdDirection();
        field.update();
        assertTrue(field.hasEnded());
    }

    @Test
    public void barrierCrash() {
        Position birdPosition = new Position(BIRD_POSITION_X, FIELD_HEIGHT / 2);
        GameObjects gameObjects = createGameObjects(birdPosition, BIRD_POSITION_X + BIRD_WIDTH + 1, 0);
        Field field = new Field(gameObjects);
        field.update();
        assertTrue(field.hasEnded());
    }

    @Test
    public void upperAngleBarrierCrash(){
        int birdPosition = FIELD_HEIGHT / 2;
        Field field = new Field(createGameObjects(new Position(BIRD_POSITION_X, birdPosition),
                BIRD_POSITION_X + BIRD_WIDTH, birdPosition - 1));
        field.changeBirdDirection();
        field.update();
        assertTrue(field.hasEnded());
    }

    @Test
    public void lowerAngleBarrierCrash(){
        int birdPosition = FIELD_HEIGHT / 2;
        int upperY = birdPosition + BIRD_HEIGHT + BARRIER_GAP + 1;
        Field field = new Field(createGameObjects(new Position(BIRD_POSITION_X, birdPosition), BIRD_POSITION_X + BIRD_WIDTH, upperY));
        field.update();
        assertTrue(field.hasEnded());
    }

    @Test
    public void insideBarrierCrash(){
        int birdPosition = FIELD_HEIGHT / 3;
        Field field = new Field(createGameObjects(new Position(BIRD_POSITION_X, birdPosition), BIRD_POSITION_X + BIRD_WIDTH / 2, birdPosition - 1));
        field.changeBirdDirection();
        field.update();
        assertTrue(field.hasEnded());

        int upperY = birdPosition + BIRD_HEIGHT + BARRIER_GAP + 1;
        field = new Field(createGameObjects(new Position(BIRD_POSITION_X, birdPosition), BIRD_POSITION_X + BIRD_WIDTH / 2, upperY));
        field.update();
        assertTrue(field.hasEnded());
    }

    /*
            |        |
          b
                 b   |
            |        |
    */
    @Test
    public void testSuccessfulPassageBarrier(){
        int birdPosition = 10;
        int upperY = 0;
        GameObjects gameObjects = createGameObjects(new Position(BIRD_POSITION_X, birdPosition), BIRD_POSITION_X + BIRD_WIDTH, upperY);
        Field field = new Field(gameObjects);
        // CR: this test might be indefinite, better to have some timeout
        while (true){
            gameObjects = field.getGameObjects();
            if (gameObjects.prevBarrier() != null && isPassBarrier(gameObjects.prevBarrier().x())) break;
            field.update();
        }
        assertFalse(field.hasEnded());
        assertEquals(field.getCurrentScore(), 1);

    }

    @Test
    public void successfulPassageBarrier(){
        int birdPosition = FIELD_HEIGHT / 3;
        int upperY = birdPosition - 100;
        GameObjects gameObjects = createGameObjects(new Position(BIRD_POSITION_X, birdPosition), BIRD_POSITION_X + BIRD_WIDTH / 2, upperY);
        Field field = new Field(gameObjects);
        while (true) {
            gameObjects = field.getGameObjects();
            if (gameObjects.prevBarrier() != null && isPassBarrier(gameObjects.prevBarrier().x())) break;
            field.update();
            int birdY = field.getGameObjects().birdPosition().y();
            if (upperY + BARRIER_GAP - birdY - BIRD_HEIGHT <= 5){
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
        return BIRD_POSITION_X > barrierPosition + BARRIER_WIDTH;
    }
}
