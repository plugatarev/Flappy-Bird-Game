package controller;

import model.Field;
import view.FlappyBirdFrame;
import view.NewGameListener;
import view.TabListener;

import javax.swing.*;

public class FlappyBirdController implements Runnable, TabListener, NewGameListener {
    private final Field field;
    private final FlappyBirdFrame frame;
    final Timer timer = new Timer(4, e -> this.handleTimer());

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
