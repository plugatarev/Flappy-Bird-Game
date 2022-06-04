package model;

import utils.Size;

public class Bird {
    private final int x;
    private int y;
    private final int width;
    private final int height;
    private boolean isDown = true;
    private int upTime = 0;

    Bird(int startX, int startY, Size birdSize){
        x = startX;
        y = startY;
        height = birdSize.height();
        width = birdSize.width();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public synchronized void move(){
        if (isDown) moveDown();
        else moveUp();
    }

    private void moveDown(){ y += 1; }

    private void moveUp(){
        y -= 2;
        upTime++;
        if (upTime == 30){
            upTime = 0;
            isDown = true;
        }
    }

    public synchronized void changeDirection(){
        isDown = !isDown;
    }

    public void reset(int startY){
        isDown = true;
        y = startY;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }
}
