package model;

import utils.BarrierModel;
import utils.GameObjects;
import utils.Position;
import utils.Size;

public class Field{
    private final int fieldBottom;
    private final int height;
    private final Bird bird;
    private final int width;
    private Barrier curBarrier;
    private Barrier prevBarrier;
    private int currentScore;

    private static final double INITIAL_BARRIER_OFFSET = 15.0 / 16;

    public Field(GameObjects settings) {
        height = settings.fieldSize().height();
        width = settings.fieldSize().width();
        fieldBottom = height - settings.groundHeight();
        bird = new Bird(settings.birdPosition(), settings.birdSize());
        curBarrier = new Barrier(settings.curBarrier());
        prevBarrier = settings.prevBarrier() == null ? null : new Barrier(settings.prevBarrier());
    }

    public void update(){
        if (prevBarrier != null) {
            prevBarrier.moveBarrier();
        }
        if (bird.getX() + bird.getWidth() >= barrierCenterX()) {
            prevBarrier = curBarrier;
            curBarrier = new Barrier(new BarrierModel((int) (width * INITIAL_BARRIER_OFFSET), prevBarrier.getWidth(), prevBarrier.getGapSize(), null));
            currentScore++;
        }
        curBarrier.moveBarrier();
        bird.move();
    }

    private int barrierCenterX() {
        return curBarrier.getX() + curBarrier.getWidth() / 2;
    }

    public int getCurrentScore(){
        return currentScore;
    }

    public boolean hasEnded() {
        return birdTouchBarrier(curBarrier) || birdTouchBorder() ||
                (prevBarrier != null && birdTouchBarrier(prevBarrier));
    }

    public GameObjects getGameObjects(){
        Position birdPosition = new Position(bird.getX(), bird.getY());
        BarrierModel curBarrierModel = new BarrierModel(curBarrier.getX(), curBarrier.getWidth(), curBarrier.getGapSize(), curBarrier.getGapUpperY());
        BarrierModel prevBarrierModel = prevBarrier == null ?
                null : new BarrierModel(prevBarrier.getX(), prevBarrier.getWidth(), prevBarrier.getGapSize(), prevBarrier.getGapUpperY());
        return new GameObjects(new Size(height, width), new Size(bird.getHeight(), bird.getWidth()),
                birdPosition, curBarrierModel, prevBarrierModel, getCurrentScore(), height - fieldBottom);
    }

    public void clear(){
        curBarrier = new Barrier(new BarrierModel((int) (width * INITIAL_BARRIER_OFFSET), curBarrier.getWidth(), curBarrier.getGapSize(), null));
        prevBarrier = null;
        currentScore = 0;
        bird.reset(height / 3);
    }

    public void changeBirdDirection(){
        bird.changeDirection();
    }

    public boolean birdTouchBarrier(Barrier b){
        return ((bird.getX() + bird.getWidth() >= b.getX() && bird.getX() + bird.getWidth() <= b.getX() + curBarrier.getWidth() ||
                (bird.getX() >= b.getX() && bird.getX() <= b.getX() + curBarrier.getWidth())) &&
                (bird.getY() + bird.getHeight() >= b.getGapUpperY() || bird.getY() <= b.getGapUpperY() - curBarrier.getGapSize()));
    }

    public boolean birdTouchBorder() {
        return (bird.getY() + bird.getHeight() >= fieldBottom) || (bird.getY() <= 0);
    }
}
