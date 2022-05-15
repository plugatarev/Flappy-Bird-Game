package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UncheckedIOException;

public class GameConfig {

    private static GameConfig gameConfig;

    private final String name;


    private GameConfig(String name) {
        this.name = name;
    }

    public static GameConfig getInstance() {
        if (gameConfig != null) {
            return gameConfig;
        }
        gameConfig = loadConfig();
        return gameConfig;
    }

    private static GameConfig loadConfig() {
        java.util.Properties property = new java.util.Properties();
        try (FileInputStream file = new FileInputStream("src/main/resources/game.properties")) {
            // load things
//            String name = file....;
            return new GameConfig(null);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}
