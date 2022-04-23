package model;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Field{
    private final static int HEIGHT = 800, WIDTH = 800;
    private final Image backGround = new ImageIcon("src/main/resources/Background.png").getImage();
    private final Bird bird = new Bird(WIDTH / 4, WIDTH / 3 );
    private Barrier curBarrier = new Barrier();
    private Barrier nextBarrier = null;
    private final List<Integer> scores = new ArrayList<>();

    public void update(){
        //TODO: handle collisions with map borders
        if (curBarrier.getCurrentPosition() > 0) curBarrier.moveBarrier();
        if (nextBarrier != null && nextBarrier.getCurrentPosition() > 0) nextBarrier.moveBarrier();
        if (bird.getX() + bird.getWidth() >= curBarrier.getCurrentPosition()) nextBarrier = new Barrier();
        if (bird.getX() >= curBarrier.getCurrentPosition() + curBarrier.getWidth()){
            curBarrier = nextBarrier;
            nextBarrier = null;
        }

        bird.moveDown();
        //TODO: add save current score
    }
    public boolean hasEnded() {
        return true;
    }

    public void clear(){

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

    public Image getBackGround() {
        return backGround;
    }

    public Barrier getCurrentBarrier(){
        return curBarrier;
    }

    public Barrier getNextBarrier(){
        return nextBarrier;
    }
}
