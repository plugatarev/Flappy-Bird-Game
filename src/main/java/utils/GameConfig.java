package utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public record GameConfig(String name, String menu, String newGame, String exit,
                         String highScores, String about, String gameRules,
                         String endGame, String birdImage, String backgroundImage,
                         String choice) {
    private static GameConfig GAME_CONFIG;

    public static GameConfig getInstance() {
        if (GAME_CONFIG == null) {
            GAME_CONFIG = loadConfig();
        }
        return GAME_CONFIG;
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
}
