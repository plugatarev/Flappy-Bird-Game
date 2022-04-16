package model;

import java.util.Random;

public class Barrier {
    private int currentPosition = 800;
    private final int space = 150;
    final static Random random= new Random();
    private final int WIDTH = 200;
    private final int upperY;
    private final int lowerY;

    Barrier(){
        upperY = random.nextInt(500) + 50;
        lowerY = 500 - space;
    }

    void moveBarrier(){
        currentPosition -= 5;
    }

    int getWidth() {
        return WIDTH;
    }

    int getUpperY(){
        return upperY;
    }

    int getLowerY(){
        return lowerY;
    }

    int getCurrentPosition(){
        return currentPosition;
    }
}
