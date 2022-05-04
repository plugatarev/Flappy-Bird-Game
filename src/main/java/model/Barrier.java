package model;

import java.util.Random;

public class Barrier {
    private int currentPosition = 750;
    private static final int WIDTH = 100;
    private static final int SPACE = 200;
    private final int upperY;

    Barrier(){
        upperY = new Random().nextInt(500) + 200;
    }

    public void moveBarrier(){
        currentPosition -= 1;
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
