package model;

import utils.BarrierModel;

import java.util.Objects;
import java.util.Random;

public class Barrier {
    private int x;
    private final int width;
    private final int gapSize;
    private final int gapUpperY;

    public Barrier(BarrierModel barrier){
        width = barrier.width();
        gapSize = barrier.gapSize();
        this.gapUpperY = Objects.requireNonNullElseGet(barrier.gapUpperY(), () -> new Random().nextInt(500) + 200);
        this.x = barrier.x();
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
