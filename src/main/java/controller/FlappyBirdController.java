package controller;

import model.Field;
import utils.GameObjects;
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
        this.frame = new FlappyBirdFrame(this, this);
    }

    public void run() {
        frame.update(GameObjects.getGameObjects(field));
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
        frame.update(GameObjects.getGameObjects(field));
        frame.repaint();
        if (field.hasEnded()) {
            timer.stop();
            frame.end();
        }
    }
}
