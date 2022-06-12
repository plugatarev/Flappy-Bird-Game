package view;

import utils.GameConfig;
import utils.GameObjects;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FieldPanel extends JPanel {
    GameObjects field;
    private final int SCREEN_CENTER;
    private final int FONT_SIZE;
    private final int SCREEN_HEIGHT;
    FieldPanel(PressListener listener, GameObjects field){
        this.field = field;
        FONT_SIZE = field.fieldSize().height() / 8;
        SCREEN_HEIGHT = field.fieldSize().height() - field.groundHeight();
        SCREEN_CENTER = field.fieldSize().width() / 3 + 85;
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
        g.drawString(String.valueOf(field.currentScore()), SCREEN_CENTER, field.fieldSize().height() / 7);
    }

    private void drawBird(Graphics g){
        Image birdImage = new ImageIcon(GameConfig.getInstance().birdImage()).getImage();
        g.drawImage(birdImage, field.birdPosition().x(), field.birdPosition().y(), field.birdSize().width(), field.birdSize().height(), null);
    }

    private void drawBarriers(Graphics g){
        int curBarrierX = field.curBarrier().x();
        int curBarrierY = field.curBarrier().gapUpperY();
        int barrierWidth = field.curBarrier().width();
        g.setColor(Color.CYAN);
        g.fillRect(curBarrierX, curBarrierY, barrierWidth, SCREEN_HEIGHT - curBarrierY);
        g.fillRect(curBarrierX, 0, barrierWidth, curBarrierY - field.curBarrier().gapSize());
        if (field.prevBarrier() != null){
            int prevBarrierX = field.prevBarrier().x();
            int prevBarrierY = field.prevBarrier().gapUpperY();
            g.fillRect(prevBarrierX, prevBarrierY, barrierWidth, SCREEN_HEIGHT - prevBarrierY);
            g.fillRect(prevBarrierX, 0, barrierWidth, prevBarrierY - field.prevBarrier().gapSize());
        }
    }

    private void drawBackground(Graphics g){
        Image backgroundImage = new ImageIcon(GameConfig.getInstance().backgroundImage()).getImage();
        g.drawImage(backgroundImage, 0, 0, this);
    }
}
