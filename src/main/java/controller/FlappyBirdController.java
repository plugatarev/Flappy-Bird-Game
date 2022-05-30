package controller;

import model.Field;
import view.FlappyBirdFrame;
import view.NewGameListener;
import view.PressListener;

import javax.swing.*;

public class FlappyBirdController implements Runnable, PressListener, NewGameListener {
    private final Field field;
    private final FlappyBirdFrame frame;
    private final static int TIMER_DELAY = 4;
    final Timer timer = new Timer(TIMER_DELAY, e -> this.handleTimer());

    public FlappyBirdController(Field field) {
        this.field = field;
        this.frame = new FlappyBirdFrame(this, this, field.getGameObjects());
    }

    public void run() {
        frame.update(field.getGameObjects());
    }

    @Override
    public void newGame() {
        field.clear();
        timer.start();
    }

    @Override
    public void changeDirection() {
        field.changeBirdDirection();
    }

    public void handleTimer() {
        field.update();
        frame.update(field.getGameObjects());
        frame.repaint();
        if (field.hasEnded()) {
            timer.stop();
            frame.end();
        }
    }
}
