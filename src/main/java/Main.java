import controller.FlappyBirdController;
import model.Field;

public class Main {
    public static void main(String[] args) {
        Field field = new Field();
        FlappyBirdController controller = new FlappyBirdController(field);
        controller.run();
    }
}
