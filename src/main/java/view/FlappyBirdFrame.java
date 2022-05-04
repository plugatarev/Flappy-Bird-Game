package view;

import model.Field;
import utils.Properties;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FlappyBirdFrame extends JFrame {
    private static final String NAME = Properties.getProperty("name");
    private static final String MENU = Properties.getProperty("menu");
    private static final String NEW_GAME = Properties.getProperty("new_game");
    private static final String EXIT = Properties.getProperty("exit");
    private static final String HIGH_SCORES = Properties.getProperty("high_scores");
    private static final String ABOUT = Properties.getProperty("about");
    private static final String[] CHOOSE_OPTIONS = {NEW_GAME, HIGH_SCORES, EXIT};
    private static final String END_GAME = Properties.getProperty("end_game");

    private final NewGameListener newGameListener;
    private FieldPanel fieldPanel;
    public FlappyBirdFrame(NewGameListener newGameListener, TabListener listener, Field field){
        super(NAME);
        this.newGameListener = newGameListener;
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
        List<Integer> scores = fieldPanel.field.getTableOfScores();
        StringBuilder table = new StringBuilder("<html>");
        for (Integer score : scores){
            table.append(score).append("<br>");
        }
        table.append("</html>");
        JLabel label = new JLabel(table.toString());
        System.out.println(label.getText());
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setHorizontalAlignment(JLabel.CENTER);
        JOptionPane.showMessageDialog(this, label, "High Score Table", JOptionPane.PLAIN_MESSAGE);
    }

    private void printAboutInformation() {
        String information = Properties.getProperty("about_information");
        JOptionPane.showMessageDialog(this, information);
    }

    public void update(Field field) {
        fieldPanel.setField(field);
        fieldPanel.repaint();
    }

    public void end() {
        int result = JOptionPane.showOptionDialog(this, Properties.getProperty("choice"),
                END_GAME,
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                CHOOSE_OPTIONS,
                CHOOSE_OPTIONS[2]);
        if (result == JOptionPane.CANCEL_OPTION) System.exit(0);
        if (result == JOptionPane.YES_OPTION) newGameListener.newGame();
        if (result == JOptionPane.NO_OPTION) {
            printHighScoresInformation();
            end();
        }
    }
}
