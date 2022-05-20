package model;

public class Bird {
    private final int x;
    private int y;
    private static final int WIDTH = 90;
    private static final int HEIGHT = 80;
    private static final int BOTTOM_FIELD = Field.getHeight() - Field.getGroundHeight();
    private static final int TOP_FIELD = 0;
    private static final boolean DOWN = true;
    private boolean direction = DOWN;

    Bird(int startX, int startY){
        x = startX;
        y = startY;
    }

    public synchronized void move(){
        if (direction == DOWN){
            y += 1;
        }
        else{
            y -= 2;
        }
//        direction = DOWN;
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

    public static int getWidth(){
        return WIDTH;
    }

    public static int getHeight(){
        return HEIGHT;
    }

    public synchronized void changeDirection(){
        direction = !direction;
    }

    public boolean isTouchBarrier(Barrier b){
        return ((x + WIDTH >= b.getCurrentPosition() && x + WIDTH <= b.getCurrentPosition() + Barrier.getWidth() ||
                (x >= b.getCurrentPosition() && x <= b.getCurrentPosition() + Barrier.getWidth())) &&
                (y + HEIGHT >= b.getUpperY() || y <= b.getUpperY() - Barrier.getSpace()));
    }

    public boolean isTouchBorder() {
        return (y + HEIGHT >= BOTTOM_FIELD) || (y <= TOP_FIELD);
    }
}
