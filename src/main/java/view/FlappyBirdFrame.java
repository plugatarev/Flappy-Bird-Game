package view;

import utils.GameConfig;
import utils.GameObjects;
import utils.Records;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FlappyBirdFrame extends JFrame {
    private FieldPanel fieldPanel;
    private static final GameConfig gc = GameConfig.getInstance();
    private static final String[] CHOOSE_OPTIONS = {gc.getNEW_GAME(), gc.getHIGH_SCORES(), gc.getEXIT()};
    private final NewGameListener newGameListener;
    private final Records records = Records.getInstance();

    public FlappyBirdFrame(NewGameListener newGameListener, PressListener listener, GameObjects field){
        super(gc.getNAME());
        this.newGameListener = newGameListener;
        createFieldPanel(listener, field);
        addReactionWindowClosing();
        this.setLayout(null);
        this.setPreferredSize(new Dimension(field.fieldSize().width(), field.fieldSize().height()));
        setupMenu();
        this.pack();
        this.setLocationRelativeTo(null);
        fieldPanel.setFocusable(true);
        this.setVisible(true);
    }

    public void update(GameObjects field) {
        fieldPanel.setField(field);
        fieldPanel.repaint();
    }

    private void addReactionWindowClosing(){
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                records.saveScores();
                System.exit(0);
            }
        });
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
            //OK CR: save on close window
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
        int score = fieldPanel.field.currentScore();
        if (score > 0) {
            String res;
            do{
                res = JOptionPane.showInputDialog(this, "Enter your name");
            }while(!isCorrect(res));
            records.addNewScore(res, score);
        }
        showChoiceMenu();
    }

    private boolean isCorrect(String res){
        if (res.length() == 0) return false;
        int i = 0;
        while (i < res.length()){
            if (res.charAt(i) == ' ') return false;
            i++;
        }
        return true;
    }

    private void createFieldPanel(PressListener listener, GameObjects field) {
        fieldPanel = new FieldPanel(listener, field);
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
        String table = records.createRecordTable();
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
