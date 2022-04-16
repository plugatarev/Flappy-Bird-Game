package model;

import jdk.swing.interop.SwingInterOpUtils;

import javax.swing.*;
import java.awt.*;

public class Field {
    private final static int WEIGHT = 800, WIDTH = 800;
    private Image backGround = new ImageIcon("src/main/resources/Background.png").getImage();
    private Bird bird = new Bird(WIDTH / 4, WIDTH / 3 );
    private Barrier barrier = new Barrier();
    private int best;

    public void update(){
//        Barrier pred;
//        if (bird.getX() > barrier.getCurrentPosition() + barrier.getWidth()){
//            pred = barrier;
//            barrier = new Barrier();
//        }
//        if (barrier.getCurrentPosition() > 0) barrier.moveBarrier();
////        if (pred.getCurrentPosition() > 0) pred.moveBarrier();
//        if (hasEnded()) return;
        bird.moveUp();

    }
    public boolean hasEnded() {
        return true;
    }

    public void clear(){

    }

    int getWeight(){
        return WEIGHT;
    }

    int getWidth(){
        return WIDTH;
    }
    public Bird getBird(){
        return bird;
    }

    public Image getBackGround() {
        return backGround;
    }
}
