package model;

import utils.GameObjects;
import utils.Position;
import utils.Size;

public class Field{
    private final int fieldBottom;
    private final int height;
    private final int width;
    private final Bird bird;
    private Barrier curBarrier;
    private Barrier prevBarrier;
    private int currentScore;

    public Field(GameObjects settings) {
        height = settings.fieldSize().height();
        width = settings.fieldSize().width();
        fieldBottom = height - settings.groundHeight();
        Position birdPosition = settings.birdPosition();
        bird = new Bird(birdPosition.x(), birdPosition.y(), settings.birdSize());
        curBarrier = new Barrier(settings.barrierPosition(), settings.barrierSize());
        prevBarrier = settings.prevBarrierPosition() == null ?
                null : new Barrier(settings.prevBarrierPosition(), settings.barrierSize());
    }

    public void update(){
        if (prevBarrier != null) {
            prevBarrier.moveBarrier();
        }
        if (bird.getX() + bird.getWidth() >= barrierCenterX()) {
            prevBarrier = curBarrier;
            curBarrier = new Barrier(new Position(width - 50, null), new Size(prevBarrier.getGapSize(), prevBarrier.getWidth()));
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

    public void clear(){
        curBarrier = new Barrier(new Position(width - 50, null), new Size(curBarrier.getGapSize(), curBarrier.getWidth()));
        prevBarrier = null;
        currentScore = 0;
        bird.reset(height / 3);
    }

    public GameObjects getGameObjects(){
        Position birdPosition = new Position(bird.getX(), bird.getY());
        Position barrierPosition = new Position(curBarrier.getX(), curBarrier.getGapUpperY());
        Position prevBarrierPosition = prevBarrier == null ? null : new Position(prevBarrier.getX(), prevBarrier.getGapUpperY());
        return new GameObjects(new Size(height, width), new Size(bird.getHeight(), bird.getWidth()),
                new Size(curBarrier.getGapSize(), curBarrier.getWidth()),
                birdPosition, barrierPosition, prevBarrierPosition, getCurrentScore(), height - fieldBottom);
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
        int TOP_FIELD = 0;
        return (bird.getY() + bird.getHeight() >= fieldBottom) || (bird.getY() <= TOP_FIELD);
    }
}
