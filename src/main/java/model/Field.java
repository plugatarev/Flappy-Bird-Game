package model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Field{
    private final static int HEIGHT = 800, WIDTH = 800;
    private final Bird bird = new Bird(WIDTH / 4, HEIGHT / 3 );
    private Barrier curBarrier = new Barrier();
    private Barrier nextBarrier = null;
    private final List<Integer> scores = new ArrayList<>();
    private int currentScore;
    public void update(){
        if (nextBarrier != null && nextBarrier.getCurrentPosition() + nextBarrier.getWidth() > 0) {
            nextBarrier.moveBarrier();
        }
        else{
            nextBarrier = null;
        }
        if (bird.getX() + bird.getWidth() >= curBarrier.getCurrentPosition() + curBarrier.getWidth() / 2 && nextBarrier == null) {
            nextBarrier = curBarrier;
            curBarrier = new Barrier();
            currentScore++;
        }
        curBarrier.moveBarrier();
        bird.move();
        if (hasEnded()) {
            addNewResult(currentScore);
        }
    }

    private void addNewResult(int score){
        if (score != 0 && !scores.contains(score)) scores.add(score);
    }

    public int getCurrentScore(){
        return currentScore;
    }

    public List<Integer> getTableOfScores(){
        scores.sort(Comparator.reverseOrder());
        return scores;
    }

    public boolean hasEnded() {
        if (nextBarrier != null) return bird.isTouchBarrier(curBarrier) || bird.isTouchBarrier(nextBarrier) || bird.isTouchBorder(HEIGHT);
        return bird.isTouchBarrier(curBarrier) || bird.isTouchBorder(HEIGHT);
    }

    public void clear(){
        curBarrier = new Barrier();
        nextBarrier = null;
        currentScore = 0;
        bird.setDefault(HEIGHT / 3);
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

    public Barrier getNextBarrier(){
        return nextBarrier;
    }

}
