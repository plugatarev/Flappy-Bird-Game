package utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class GameConfig {
    private final String NAME;
    private final String MENU;
    private final String NEW_GAME;
    private final String EXIT;
    private final String HIGH_SCORES;
    private final String ABOUT;
    private final String GAME_RULES;
    private final String END_GAME;
    private final String BIRD_IMAGE;
    private final String BACKGROUND_IMAGE;
    private final String CHOICE;

    private static GameConfig gameConfig;

    private GameConfig(String name, String menu, String newGame, String exit, String highScores, String about,
                       String gameRules, String endGame, String birdImage, String backgroundImage,
                       String choice)  {
        NAME = name;
        MENU = menu;
        NEW_GAME = newGame;
        EXIT = exit;
        HIGH_SCORES = highScores;
        ABOUT = about;
        GAME_RULES = gameRules;
        END_GAME = endGame;
        BIRD_IMAGE = birdImage;
        BACKGROUND_IMAGE = backgroundImage;
        CHOICE = choice;
    }

    private static void getInstance() {
        if (gameConfig != null) {
            return;
        }
        gameConfig = loadConfig();
    }

    private static GameConfig loadConfig() {
        Properties property = new Properties();
        try (FileInputStream file = new FileInputStream("src/main/resources/game.properties")) {
            property.load(file);
            String aboutInformation = property.getProperty("about_information");
            String gameRules = new String(Files.readAllBytes(Path.of(aboutInformation)));
            return new GameConfig(
                    property.getProperty("name"), property.getProperty("menu"), property.getProperty("new_game"),
                    property.getProperty("exit"), property.getProperty("high_scores"), property.getProperty("about"),
                    gameRules, property.getProperty("end_game"),
                    property.getProperty("bird_image"), property.getProperty("background_image"),
                    property.getProperty("choice"));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static String getConfig(String config){
        getInstance();
        return switch (config){
            case "name" -> gameConfig.NAME;
            case "menu" -> gameConfig.MENU;
            case "newGame" -> gameConfig.NEW_GAME;
            case "exit" -> gameConfig.EXIT;
            case "highScores" -> gameConfig.HIGH_SCORES;
            case "about" -> gameConfig.ABOUT;
            case "end" -> gameConfig.END_GAME;
            case "birdImage" -> gameConfig.BIRD_IMAGE;
            case "backgroundImage" -> gameConfig.BACKGROUND_IMAGE;
            case "choice" -> gameConfig.CHOICE;
            case "gameRules" -> gameConfig.GAME_RULES;
            default -> throw new RuntimeException("Not found config: " + config);
        };
    }
}
