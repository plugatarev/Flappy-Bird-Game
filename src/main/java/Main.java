import controller.FlappyBirdController;
import model.Field;

import javax.swing.*;

public class Main {
    static Timer timer;
    public static void main(String[] args) {
        Field field = new Field();
        FlappyBirdController controller = new FlappyBirdController(field);
        controller.run();
        timer = new Timer(300, e -> controller.handleTimer());
        timer.start();
    }
}
