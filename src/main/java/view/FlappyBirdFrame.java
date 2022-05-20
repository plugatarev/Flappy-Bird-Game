package view;

import model.Field;
import utils.GameConfig;
import utils.Records;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class FlappyBirdFrame extends JFrame {
    private static final GameConfig gc = GameConfig.getInstance();
    private static final String[] CHOOSE_OPTIONS = {gc.getNEW_GAME(), gc.getHIGH_SCORES(), gc.getEXIT()};
    private final NewGameListener newGameListener;
    private FieldPanel fieldPanel;
    private final Records records = new Records();

    public FlappyBirdFrame(NewGameListener newGameListener, PressListener listener){
        super(gc.getNAME());
        this.newGameListener = newGameListener;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setPreferredSize(new Dimension(Field.getWidth(), Field.getHeight()));
        setupMenu();
        createFieldPanel(listener);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
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

    public void update(Field field) {
        fieldPanel.setField(field);
        fieldPanel.repaint();
    }

    private void showChoiceMenu(){
        int result = JOptionPane.showOptionDialog(this, gc.getCHOICE(),
                gc.getEND_GAME(),
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                CHOOSE_OPTIONS,
                CHOOSE_OPTIONS[2]);
        if (result == JOptionPane.CANCEL_OPTION) {
            records.saveScores();
            System.exit(0);
        }
        if (result == JOptionPane.YES_OPTION) newGameListener.newGame();
        if (result == JOptionPane.NO_OPTION) {
            printHighScoresInformation();
            showChoiceMenu();
        }
    }

    public void end() {
        String res = JOptionPane.showInputDialog(this, "Enter your name");
        records.addNewScore(res, fieldPanel.field.getCurrentScore());
        showChoiceMenu();
    }

    private void createFieldPanel(PressListener listener) {
        fieldPanel = new FieldPanel(listener);
        setContentPane(fieldPanel);
    }

    private void setupMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu(gc.getMENU());

        JMenuItem aboutGameItem = new JMenuItem(gc.getABOUT());
        JMenuItem highScoresItem = new JMenuItem(gc.getHIGH_SCORES());
        JMenuItem newGameItem = new JMenuItem(gc.getNEW_GAME());
        JMenuItem exitItem = new JMenuItem(gc.getEXIT());

        newGameItem.addActionListener(event -> newGameListener.newGame());
        exitItem.addActionListener(event -> System.exit(0));
        aboutGameItem.addActionListener(event -> printAboutInformation());
        highScoresItem.addActionListener(event -> printHighScoresInformation());

        menu.add(newGameItem);
        menu.add(aboutGameItem);
        menu.add(highScoresItem);
        menuBar.add(menu);
        menu.add(exitItem);
        this.setJMenuBar(menuBar);
    }

    private void printHighScoresInformation() {
        StringBuilder table = new StringBuilder();
        records.createRecordTable(table);
        JTextArea textArea = new JTextArea(String.valueOf(table));
        textArea.setLineWrap(true);
        textArea.setOpaque(false);
        textArea.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        JOptionPane.showMessageDialog(this, textArea, "High Score Table", JOptionPane.PLAIN_MESSAGE);
    }

    private void printAboutInformation() {
        String information = gc.getABOUT_INFORMATION();
        JOptionPane.showMessageDialog(this, information);
    }
}
