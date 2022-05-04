package model;

public class Bird {
    private final int x;
    private int y;
    private static final int WIDTH = 100;
    private static final int HEIGHT = 100;
    private int direction = 1;
    Bird(int startX, int startY){
        x = startX;
        y = startY;
    }

    public void move(){
        if (direction == 1) y += direction;
        else y += 2 * direction;
    }

    public void setDefault(int startY){
        direction = 1;
        y = startY;
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

    public synchronized void changeDirection(){
        direction *= -1;
    }

    public boolean isTouchBarrier(Barrier b){
        return ((x + WIDTH > b.getCurrentPosition() && x + WIDTH < b.getCurrentPosition() + b.getWidth() ||
                (x > b.getCurrentPosition() && x < b.getCurrentPosition() + b.getWidth())) &&
                (y + HEIGHT > b.getUpperY() || y < b.getUpperY() - b.getSpace()));
    }
    public boolean isTouchBorder(int height) {
        return (y + HEIGHT > height - 95) || (y < 0);
    }
}
