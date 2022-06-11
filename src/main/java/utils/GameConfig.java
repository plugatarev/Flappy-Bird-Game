package utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class GameConfig {
    private final String name;
    private final String menu;
    private final String newGame;
    private final String exit;
    private final String highScores;
    private final String about;
    private final String gameRules;
    private final String endGame;
    private final String birdImage;
    private final String backgroundImage;
    private final String choice;

    private static GameConfig GAME_CONFIG;

    private GameConfig(String name, String menu, String newGame, String exit, String highScores, String about,
                       String gameRules, String endGame, String birdImage, String backgroundImage,
                       String choice)  {
        this.name = name;
        this.menu = menu;
        this.newGame = newGame;
        this.exit = exit;
        this.highScores = highScores;
        this.about = about;
        this.gameRules = gameRules;
        this.endGame = endGame;
        this.birdImage = birdImage;
        this.backgroundImage = backgroundImage;
        this.choice = choice;
    }

    private static void getInstance() {
        if (GAME_CONFIG != null) {
            return;
        }
        GAME_CONFIG = loadConfig();
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
        // CR: redundant
        getInstance();
        // CR: just make this class record, no need for name -> property matching
        return switch (config){
            case "name" -> GAME_CONFIG.name;
            case "menu" -> GAME_CONFIG.menu;
            case "newGame" -> GAME_CONFIG.newGame;
            case "exit" -> GAME_CONFIG.exit;
            case "highScores" -> GAME_CONFIG.highScores;
            case "about" -> GAME_CONFIG.about;
            case "end" -> GAME_CONFIG.endGame;
            case "birdImage" -> GAME_CONFIG.birdImage;
            case "backgroundImage" -> GAME_CONFIG.backgroundImage;
            case "choice" -> GAME_CONFIG.choice;
            case "gameRules" -> GAME_CONFIG.gameRules;
            default -> throw new RuntimeException("Not found config: " + config);
        };
    }
}
