package model;

import javax.swing.*;
import java.awt.*;

public class Bird {
    private final int x;
    private int y;
    private final int WIDTH = 150;
    private final int WEIGH = 150;
    private final Image image = new ImageIcon("src/main/resources/Bird.png").getImage();

    Bird(int startX, int startY){
        x = startX;
        y = startY;
    }

    public void moveUp(){
        y += 10;
    }

    public void moveDown(){
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
    public int getWeigh(){
        return WEIGH;
    }

    public Image getImage(){
        return image;
    }
}
