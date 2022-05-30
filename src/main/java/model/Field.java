package model;

import utils.GameObjects;
import utils.Position;
import utils.Size;

public class Field{
    private final int TOP_FIELD;
    private final int BOTTOM_FIELD;
    private final int HEIGHT;
    private final int WIDTH;
    private final Bird bird;
    private Barrier curBarrier;
    private Barrier prevBarrier;
    private int currentScore;

    public Field(GameObjects settings) {
        HEIGHT = settings.fieldSize().height();
        WIDTH = settings.fieldSize().width();
        TOP_FIELD = 0;
        BOTTOM_FIELD = HEIGHT - settings.groundHeight();
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
        if (bird.getX() + bird.getWidth() >= curBarrier.getCurrentPosition() + curBarrier.getWidth() / 2) {
            prevBarrier = curBarrier;
            curBarrier = new Barrier(new Position(WIDTH - 50, null), new Size(prevBarrier.getSpace(), prevBarrier.getWidth()));
            currentScore++;
        }
        curBarrier.moveBarrier();
        bird.move();
    }

    public synchronized int getCurrentScore(){
        return currentScore;
    }

    public boolean hasEnded() {
        return birdTouchBarrier(curBarrier) || birdTouchBorder() ||
                (prevBarrier != null && birdTouchBarrier(prevBarrier));
    }

    public void clear(){
        curBarrier = new Barrier(new Position(WIDTH - 50, null), new Size(curBarrier.getSpace(), curBarrier.getWidth()));
        prevBarrier = null;
        currentScore = 0;
        bird.reset(HEIGHT / 3);
    }

    public GameObjects getGameObjects(){
        Position birdPosition = new Position(bird.getX(), bird.getY());
        Position barrierPosition = new Position(curBarrier.getCurrentPosition(), curBarrier.getUpperY());
        Position prevBarrierPosition = prevBarrier == null ? null : new Position(prevBarrier.getCurrentPosition(), prevBarrier.getUpperY());
        return new GameObjects(new Size(HEIGHT, WIDTH), new Size(bird.getHeight(), bird.getWidth()),
                new Size(curBarrier.getSpace(), curBarrier.getWidth()),
                birdPosition, barrierPosition, prevBarrierPosition, getCurrentScore(), HEIGHT - BOTTOM_FIELD);
    }

    public void changeBirdDirection(){
        bird.changeDirection();
    }

    public boolean birdTouchBarrier(Barrier b){
        return ((bird.getX() + bird.getWidth() >= b.getCurrentPosition() && bird.getX() + bird.getWidth() <= b.getCurrentPosition() + curBarrier.getWidth() ||
                (bird.getX() >= b.getCurrentPosition() && bird.getX() <= b.getCurrentPosition() + curBarrier.getWidth())) &&
                (bird.getY() + bird.getHeight() >= b.getUpperY() || bird.getY() <= b.getUpperY() - curBarrier.getSpace()));
    }

    public boolean birdTouchBorder() {
        return (bird.getY() + bird.getHeight() >= BOTTOM_FIELD) || (bird.getY() <= TOP_FIELD);
    }
}
