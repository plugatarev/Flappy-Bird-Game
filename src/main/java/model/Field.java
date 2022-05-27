package model;

import utils.FieldModel;
import utils.Position;

public class Field{
    private final static int HEIGHT = 800;
    private final static int WIDTH = 800;
    private static final int GROUND_HEIGHT = 95;
    private Bird bird;
    private Barrier curBarrier;
    private Barrier prevBarrier;
    private int currentScore;

    public Field(FieldModel fieldModel) {
        if (fieldModel == null) setDefaultSettings();
        else{
            Position birdPosition = fieldModel.birdPosition();
            bird = new Bird(birdPosition.x(), birdPosition.y());
            curBarrier = new Barrier(fieldModel.barrierPosition().x(), fieldModel.barrierPosition().y());
            prevBarrier = new Barrier(fieldModel.barrierPosition().x(), fieldModel.barrierPosition().y());
        }
    }

    private void setDefaultSettings() {
        bird = new Bird(WIDTH / 4, HEIGHT / 3 );
        curBarrier = new Barrier();
        prevBarrier = null;
    }

    public void update(){
        if (prevBarrier != null) {
            prevBarrier.moveBarrier();
        }
        if (bird.getX() + Bird.getWidth() >= curBarrier.getCurrentPosition() + Barrier.getWidth() / 2) {
            prevBarrier = curBarrier;
            curBarrier = new Barrier();
            currentScore++;
        }
        curBarrier.moveBarrier();
        bird.move();
    }

    public synchronized int getCurrentScore(){
        return currentScore;
    }

    public boolean hasEnded() {
        return bird.isTouchBarrier(curBarrier) || bird.isTouchBorder() ||
                (prevBarrier != null && bird.isTouchBarrier(prevBarrier));
    }

    public void clear(){
        curBarrier = new Barrier();
        prevBarrier = null;
        currentScore = 0;
        bird.reset(HEIGHT / 3);
    }

    public static int getHeight(){
        return HEIGHT;
    }

    public static int getWidth(){
        return WIDTH;
    }

    public Bird getBird(){
        return bird;
    }

    public Barrier getCurrentBarrier(){
        return curBarrier;
    }

    public Barrier getPrevBarrier(){
        return prevBarrier;
    }

    public static int getGroundHeight(){
        return GROUND_HEIGHT;
    }
}
