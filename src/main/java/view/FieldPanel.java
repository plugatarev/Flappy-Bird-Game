package view;

import model.Barrier;
import model.Bird;
import model.Field;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class FieldPanel extends JPanel {
    Field field;

    FieldPanel(TabListener listener){
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) listener.onGoUp();
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
        g.drawImage(bird.getImage(), bird.getX(), bird.getY(), bird.getWidth(), bird.getHeight(), null);
    }

    private void drawBarriers(Graphics g){
        Barrier b1 = field.getCurrentBarrier();
        Barrier b2 = field.getNextBarrier();
        g.setColor(Color.CYAN);
        g.fillRect(b1.getCurrentPosition(), b1.getUpperY(), b1.getWidth(), 702 - b1.getUpperY());
        g.fillRect(b1.getCurrentPosition(), 0, b1.getWidth(), b1.getUpperY() - b1.getSpace());
        if (b2 != null){
            g.fillRect(b2.getCurrentPosition(), b2.getUpperY(), b2.getWidth(), 702 - b2.getUpperY());
            g.fillRect(b2.getCurrentPosition(), 0, b2.getWidth(), b2.getUpperY() - b2.getSpace());
        }
    }

    private void drawBackground(Graphics g){
        g.drawImage(field.getBackGround(), 0, 0, this);
    }
}
