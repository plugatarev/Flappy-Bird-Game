package utils;

import java.io.*;
import java.util.Properties;

public class GameConfig {
    private final String NAME;
    private final String MENU;
    private final String NEW_GAME;
    private final String EXIT;
    private final String HIGH_SCORES;
    private final String ABOUT;
    private final String ABOUT_INFORMATION;
    private final String END_GAME;
    private final String BIRD_IMAGE;
    private final String BACKGROUND_IMAGE;
    private final String CHOICE;

    private static GameConfig gameConfig;

    private GameConfig(String name, String menu, String newGame, String exit, String highScores, String about,
                       String aboutInformation, String endGame, String birdImage, String backgroundImage,
                       String choice)  {
        NAME = name;
        MENU = menu;
        NEW_GAME = newGame;
        EXIT = exit;
        HIGH_SCORES = highScores;
        ABOUT = about;
        ABOUT_INFORMATION = aboutInformation;
        END_GAME = endGame;
        BIRD_IMAGE = birdImage;
        BACKGROUND_IMAGE = backgroundImage;
        CHOICE = choice;
    }

    public static GameConfig getInstance() {
        if (gameConfig != null) {
            return gameConfig;
        }
        gameConfig = loadConfig();
        return gameConfig;
    }

    private static GameConfig loadConfig() {
        Properties property = new Properties();
        try (FileInputStream file = new FileInputStream("src/main/resources/game.properties")) {
            property.load(file);
            return new GameConfig(
                    property.getProperty("name"), property.getProperty("menu"), property.getProperty("new_game"),
                    property.getProperty("exit"), property.getProperty("high_scores"), property.getProperty("about"),
                    property.getProperty("about_information"), property.getProperty("end_game"),
                    property.getProperty("bird_image"), property.getProperty("background_image"),
                    property.getProperty("choice"));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public String getNAME() {
        return NAME;
    }

    public String getMENU() {
        return MENU;
    }

    public String getNEW_GAME() {
        return NEW_GAME;
    }

    public String getEXIT() {
        return EXIT;
    }

    public String getHIGH_SCORES() {
        return HIGH_SCORES;
    }

    public String getABOUT() {
        return ABOUT;
    }

    public String getEND_GAME() {
        return END_GAME;
    }

    public String getBIRD_IMAGE() {
        return BIRD_IMAGE;
    }

    public String getBACKGROUND_IMAGE() {
        return BACKGROUND_IMAGE;
    }

    public String getCHOICE() {
        return CHOICE;
    }

    public String getABOUT_INFORMATION() {
        StringBuilder about = new StringBuilder();
        String line;
        try (BufferedReader reader = new BufferedReader(new FileReader(ABOUT_INFORMATION))) {
            while ((line = reader.readLine()) != null) about.append(line).append("\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return about.toString();
    }
}
