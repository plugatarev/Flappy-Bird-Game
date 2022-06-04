package model;

import utils.Position;
import utils.Size;

import java.util.Objects;
import java.util.Random;

public class Barrier {
    private int x;
    private final int width;
    private final int gapSize;
    private final int gapUpperY;

    public Barrier(Position barrierPosition, Size barrierSize){
        width = barrierSize.width();
        gapSize = barrierSize.height();
        this.gapUpperY = Objects.requireNonNullElseGet(barrierPosition.y(), () -> new Random().nextInt(500) + 200);
        this.x = barrierPosition.x();
    }

    public void moveBarrier(){
        x -= 1;
    }

    public int getWidth() {
        return width;
    }

    public int getGapUpperY(){
        return gapUpperY;
    }

    public int getX(){
        return x;
    }

    public int getGapSize(){
        return gapSize;
    }
}
