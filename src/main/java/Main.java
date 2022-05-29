import controller.FlappyBirdController;
import model.Field;
import utils.GameObjects;

public class Main {
    public static void main(String[] args) {
        GameObjects defaultSettings = GameObjects.loadDefaultGameObjects();
        Field field = new Field(defaultSettings);
        FlappyBirdController controller = new FlappyBirdController(field, defaultSettings);
        controller.run();
    }
}
