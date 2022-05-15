package model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Field{
    private final static int HEIGHT = 800;
    private final static int WIDTH = 800;
    private final Bird bird = new Bird(WIDTH / 4, HEIGHT / 3 );
    private Barrier curBarrier = new Barrier();
    private Barrier prevBarrier = null;
    private final List<Integer> scores = new ArrayList<>();
    private int currentScore;

//    private static class FieldModel {
//        private final Point birdPosition;
//        private final Point barrierPosition;
//        private final Point prevBarrierPositon;
//        private final boolean isDownDirection;
//    }
//
//    Field(FieldModel fieldModel) {
//        Point birdPosition = fieldModel.birdPosition;
//        bird = new Bird(birdPosition.x())
//    }

    public void update(){
        if (prevBarrier != null) {
            prevBarrier.moveBarrier();
        }
        if (bird.getX() + bird.getWidth() >= curBarrier.getCurrentPosition() + curBarrier.getWidth() / 2) {
            prevBarrier = curBarrier;
            curBarrier = new Barrier();
            currentScore++;
        }
        curBarrier.moveBarrier();
        bird.move();
        if (hasEnded()) {
            addNewResult(currentScore);
        }
    }

    public int getCurrentScore(){
        return currentScore;
    }

    public List<Integer> getTableOfScores(){
        scores.sort(Comparator.reverseOrder());
        return scores;
    }

    public boolean hasEnded() {
        return bird.isTouchBarrier(curBarrier) || bird.isTouchBorder(HEIGHT) ||
                (prevBarrier != null && bird.isTouchBarrier(prevBarrier));
    }

    public void clear(){
        curBarrier = new Barrier();
        prevBarrier = null;
        currentScore = 0;
        bird.reset(HEIGHT / 3);
    }

    public int getHeight(){
        return HEIGHT;
    }

    public int getWidth(){
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

    private void addNewResult(int score){
        if (score != 0 && !scores.contains(score)) scores.add(score);
    }

}
