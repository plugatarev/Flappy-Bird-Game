package view;

import model.Barrier;
import model.Bird;
import model.Field;
import utils.GameConfig;
import utils.GameObjects;
import utils.Position;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FieldPanel extends JPanel {
    GameObjects field;
    private static final int SCREEN_CENTER = Field.getWidth() / 3 + 85;
    private static final int FONT_SIZE = 100;
    private static final int SCREEN_HEIGHT = 705;
    private static final GameConfig gc = GameConfig.getInstance();
    FieldPanel(PressListener listener){
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent event) {
               if (event.getKeyCode() == KeyEvent.VK_SPACE) listener.changeDirection();
            }

            @Override
            public void keyPressed(KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.VK_SPACE) listener.changeDirection();
            }
        });
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                listener.changeDirection();
            }

            @Override
            public void mousePressed(MouseEvent event) {
                listener.changeDirection();
            }
        });

    }

    void setField(GameObjects field){
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
        g.setFont(new Font("Arial", Font.PLAIN, FONT_SIZE));
        g.drawString(String.valueOf(field.currentScore()), SCREEN_CENTER, Field.getHeight() / 7);
    }

    private void drawBird(Graphics g){
        Image birdImage = new ImageIcon(gc.getBIRD_IMAGE()).getImage();
        g.drawImage(birdImage, field.birdPosition().x(), field.birdPosition().y(), Bird.getWidth(), Bird.getHeight(), null);
    }

    private void drawBarriers(Graphics g){
        Position b1 = field.barrierPosition();
        Position b2 = field.prevBarrierPosition();
        g.setColor(Color.CYAN);
        g.fillRect(b1.x(), b1.y(), Barrier.getWidth(), SCREEN_HEIGHT - b1.y());
        g.fillRect(b1.x(), 0, Barrier.getWidth(), b1.y() - Barrier.getSpace());
        if (b2 != null){
            g.fillRect(b2.x(), b2.y(), Barrier.getWidth(), SCREEN_HEIGHT - b2.y());
            g.fillRect(b2.x(), 0, Barrier.getWidth(), b2.y() - Barrier.getSpace());
        }
    }

    private void drawBackground(Graphics g){
        Image backgroundImage = new ImageIcon(gc.getBACKGROUND_IMAGE()).getImage();
        g.drawImage(backgroundImage, 0, 0, this);
    }
}
