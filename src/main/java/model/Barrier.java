package model;

import java.util.Random;

public class Barrier {
    private int currentPosition;
    private static final int WIDTH = 100;
    private static final int SPACE = 200;
    private final int upperY;

    Barrier(){
        upperY = new Random().nextInt(500) + 200;
        currentPosition = 750;
    }

    Barrier(int currentPosition, int upperY){
        this.upperY = upperY;
        this.currentPosition = currentPosition;
    }

    public void moveBarrier(){
        currentPosition -= 1;
    }

    public static int getWidth() {
        return WIDTH;
    }

    public int getUpperY(){
        return upperY;
    }

    public int getCurrentPosition(){
        return currentPosition;
    }

    public static int getSpace(){
        return SPACE;
    }
}
