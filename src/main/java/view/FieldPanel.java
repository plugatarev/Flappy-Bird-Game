package view;

import model.Bird;
import model.Field;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class FieldPanel extends JPanel {
    Field field;

    FieldPanel(TabListener listener){
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
//                if (e.getKeyCode() == KeyEvent.VK_SPACE) listener.onTab();
                listener.onTab();
            }
        });
    }

    void setField(Field field){
        this.field = field;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBackground(g);
        drawBird(g);
        drawBarriers(g);
    }

    private void drawBird(Graphics g){
        Bird bird = field.getBird();
        g.drawImage(bird.getImage(), bird.getX(), bird.getY(), bird.getWidth(), bird.getWeigh(), null);
    }

    private void drawBarriers(Graphics g){
//        g.drawRect();
    }

    private void drawBackground(Graphics g){
        g.drawImage(field.getBackGround(), 0, 0, this);
    }
}
