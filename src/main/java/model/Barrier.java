package model;

import java.util.Random;

public class Barrier {
    private int currentPosition = 750;
    final static Random random= new Random();
    private final int WIDTH = 100;
    private final int SPACE = 200;
    private final int upperY;

    Barrier(){
        upperY = random.nextInt(500) + 200;
    }

    public void moveBarrier(){
        currentPosition -= 5;
    }

    public int getWidth() {
        return WIDTH;
    }

    public int getUpperY(){
        return upperY;
    }

    public int getCurrentPosition(){
        return currentPosition;
    }

    public int getSpace(){
        return SPACE;
    }
}
