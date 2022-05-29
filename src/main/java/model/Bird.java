package model;

import utils.Size;

public class Bird {
    private static final boolean DOWN = true;
    private final int x;
    private int y;
    private final int WIDTH;
    private final int HEIGHT;
    private boolean direction = DOWN;
    private int upTime = 0;

    Bird(int startX, int startY, Size birdSize){
        x = startX;
        y = startY;
        HEIGHT = birdSize.height();
        WIDTH = birdSize.width();
    }

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }

    public synchronized void move(){
        if (direction == DOWN) moveDown();
        else moveUp();
    }

    private void moveDown(){ y += 1; }

    private void moveUp(){
        y -= 2;
        upTime++;
        if (upTime == 30){
            upTime = 0;
            direction = DOWN;
        }
    }

    public synchronized void changeDirection(){
        direction = !direction;
    }

    public void reset(int startY){
        direction = DOWN;
        y = startY;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }
}
