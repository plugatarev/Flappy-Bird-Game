package controller;

import model.Field;
import view.FlappyBirdFrame;
import view.NewGameListener;
import view.TabListener;

public class FlappyBirdController implements Runnable, TabListener, NewGameListener {
    private final Field field;
    private final FlappyBirdFrame frame;

    public FlappyBirdController(Field field){
        this.field = field;
        this.frame = new FlappyBirdFrame(this, this);
    }
    public void run() {
        newGame();
    }

    @Override
    public void newGame() {
        field.clear();
        field.getBird().moveDown();
        frame.update(field);
    }

    @Override
    public void onTab() {
        field.update();
        frame.update(field);
        if (field.hasEnded()) {
            frame.end();
        }
    }

}
