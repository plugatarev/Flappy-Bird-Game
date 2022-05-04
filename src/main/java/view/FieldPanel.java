package view;

import model.Barrier;
import model.Bird;
import model.Field;
import utils.Properties;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FieldPanel extends JPanel {
    Field field;

    FieldPanel(TabListener listener){
//        this.addKeyListener(new KeyAdapter() {
//            @Override
//            public synchronized void keyTyped(KeyEvent event) {
//               if (event.getKeyCode() == KeyEvent.VK_SPACE) listener.changeDirection();
//            }
//
//            @Override
//            public synchronized void keyPressed(KeyEvent event) {
//                if (event.getKeyCode() == KeyEvent.VK_SPACE) listener.changeDirection();
//            }
//        });
        this.addMouseListener(new MouseAdapter() {
            @Override
            public synchronized void mouseClicked(MouseEvent event) {
                listener.changeDirection();
            }

            // e.g. when touchpad is used
            @Override
            public synchronized void mousePressed(MouseEvent event) {
                listener.changeDirection();
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
        drawScore(g);
    }

    private void drawScore(Graphics g){
        g.setColor(Color.BLACK);
        g.setFont(new Font("Times New Roman", Font.PLAIN, 100));
        g.drawString(String.valueOf(field.getCurrentScore()), field.getWidth() / 3 + 85, field.getHeight() / 7);
    }

    private void drawBird(Graphics g){
        Bird bird = field.getBird();
        Image birdImage = new ImageIcon(Properties.getProperty("bird_image")).getImage();
        g.drawImage(birdImage, bird.getX(), bird.getY(), bird.getWidth(), bird.getHeight(), null);
    }

    private void drawBarriers(Graphics g){
        Barrier b1 = field.getCurrentBarrier();
        Barrier b2 = field.getNextBarrier();
        g.setColor(Color.CYAN);
        g.fillRect(b1.getCurrentPosition(), b1.getUpperY(), b1.getWidth(), 705 - b1.getUpperY());
        g.fillRect(b1.getCurrentPosition(), 0, b1.getWidth(), b1.getUpperY() - b1.getSpace());
        if (b2 != null){
            g.fillRect(b2.getCurrentPosition(), b2.getUpperY(), b2.getWidth(), 705 - b2.getUpperY());
            g.fillRect(b2.getCurrentPosition(), 0, b2.getWidth(), b2.getUpperY() - b2.getSpace());
        }
    }

    private void drawBackground(Graphics g){
        Image backgroundImage = new ImageIcon(Properties.getProperty("background_image")).getImage();
        g.drawImage(backgroundImage, 0, 0, this);
    }
}
