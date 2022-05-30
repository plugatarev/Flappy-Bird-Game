import controller.FlappyBirdController;
import model.Field;
import utils.GameObjects;

public class Main {
    public static void main(String[] args) {
        GameObjects defaultSettings = GameObjects.defaultGameObjects();
        Field field = new Field(defaultSettings);
        FlappyBirdController controller = new FlappyBirdController(field);
        controller.run();
    }
}
