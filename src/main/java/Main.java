//import controller.FlappyBirdController;
import controller.FlappyBirdController;
import model.Field;
import view.FlappyBirdFrame;

public class Main {
    public static void main(String[] args) {
//        FlappyBirdFrame fr = new FlappyBirdFrame(null, null);
        Field field = new Field();
        FlappyBirdController controller = new FlappyBirdController(field);
        controller.run();
    }
}
