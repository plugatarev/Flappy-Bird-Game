package view;

import model.Field;

import javax.swing.*;
import java.awt.*;

public class FlappyBirdFrame extends JFrame {
    private static final String NAME = "FlappyBird";
    private static final String MENU = "Menu";
    private static final String NEW_GAME = "New Game";
    private static final String EXIT = "Exit";
    private static final String HIGH_SCORES = "High Scores";
    private static final String ABOUT = "About";
    private static final String[] WINNER_OPTIONS = {"New game", "Cancel"};
    private static final String END_GAME = "End game";

    private final NewGameListener newGameListener;
    private FieldPanel fieldPanel;
    private final Field field;
    public FlappyBirdFrame(NewGameListener newGameListener, TabListener listener, Field field){
        super(NAME);
        this.newGameListener = newGameListener;
        this.field = field;

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setPreferredSize(new Dimension(field.getWidth(), field.getHeight()));

        setupMenu();
        createFieldPanel(listener);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void createFieldPanel(TabListener listener) {
        fieldPanel = new FieldPanel(listener);
        setContentPane(fieldPanel);
    }

    private void setupMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu(MENU);

        JMenuItem aboutGameItem = new JMenuItem(ABOUT);
        JMenuItem highScoresItem = new JMenuItem(HIGH_SCORES);
        JMenuItem newGameItem = new JMenuItem(NEW_GAME);
        JMenuItem exitItem = new JMenuItem(EXIT);

        newGameItem.addActionListener(event -> newGameListener.newGame());
        exitItem.addActionListener(event -> System.exit(0));
        //TODO:
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
    }

    private void printAboutInformation() {
        String information = """
                Flappy Bird is an arcade-style game in which the player controls the bird Faby, which moves persistently to the right. 
                The player is tasked with navigating Faby through pairs of pipes that have equally sized gaps placed at random heights. 
                Faby automatically descends and only ascends when the player taps the touchscreen. 
                Each successful pass through a pair of pipes awards the player one point. Colliding with a pipe or the ground ends the gameplay. 
                During the game over screen, the player is awarded a bronze medal if they reached ten or more points, a silver medal from twenty 
                points, a gold medal from thirty points, and a platinum medal from forty points.""";
        JOptionPane.showMessageDialog(this, information);
    }

    public void update(Field field) {
        fieldPanel.setField(field);
        fieldPanel.repaint();
    }

    public void end() {
        String message = "The end!";
        int choice = JOptionPane.showOptionDialog(this, message, END_GAME,
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, WINNER_OPTIONS, WINNER_OPTIONS[1]);
        if (choice == JOptionPane.YES_OPTION) {
            newGameListener.newGame();
        }
    }
}
