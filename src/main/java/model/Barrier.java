package model;

import utils.Position;
import utils.Size;

import java.util.Objects;
import java.util.Random;

public class Barrier {
    private int currentPosition;
    private final int WIDTH;
    private final int SPACE;
    private final int upperY;

    public Barrier(Position currentPosition, Size barrierSize){
        WIDTH = barrierSize.width();
        SPACE = barrierSize.height();
        this.upperY = Objects.requireNonNullElseGet(currentPosition.y(), () -> new Random().nextInt(500) + 200);
        this.currentPosition = currentPosition.x();
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
