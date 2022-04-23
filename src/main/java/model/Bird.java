package model;

import javax.swing.*;
import java.awt.*;

public class Bird {
    private final int x;
    private int y;
    private static final int WIDTH = 150;
    private static final int HEIGHT = 150;
    private final Image image = new ImageIcon("src/main/resources/Bird.png").getImage();

    Bird(int startX, int startY){
        x = startX;
        y = startY;
    }

    public void moveDown(){
//        synchronized (LOCK) {
//            direction = DOWN;
//        }
        y += 10;
    }

    public void moveUp(){
//        synchronized (LOCK) {
//            direction = UP;
//        }
        y -= 10;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getWidth(){
        return WIDTH;
    }

    public int getHeight(){
        return HEIGHT;
    }

    public Image getImage(){
        return image;
    }
}
