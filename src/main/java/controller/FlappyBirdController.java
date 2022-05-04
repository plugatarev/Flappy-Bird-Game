package controller;

import model.Field;
import utils.Position;
import utils.Records;
import utils.Size;
import utils.ViewField;
import view.FlappyBirdFrame;
import view.NewGameListener;
import view.TabListener;

import javax.swing.*;

public class FlappyBirdController implements Runnable, TabListener, NewGameListener {
    private final Field field;
    private final FlappyBirdFrame frame;
    Timer timer;
    public FlappyBirdController(Field field) {
        this.field = field;
        this.frame = new FlappyBirdFrame(this, this, field);
    }

    public void run() {
        frame.update(field);
    }

    @Override
    public void newGame() {
        field.clear();
        timer = new Timer(5, e -> this.handleTimer());
        timer.start();
    }

    @Override
    public void changeDirection() {
        field.getBird().changeDirection();
    }

    public void handleTimer() {
        field.update();
        if (field.hasEnded()) {
            timer.stop();
            frame.end();
            return;
        }
        frame.update(field);
        frame.repaint();
    }
}
