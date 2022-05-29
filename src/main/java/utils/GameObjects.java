package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Properties;

public record GameObjects(Size fieldSize, Size birdSize, Size barrierSize, Position birdPosition,
                          Position barrierPosition, Position prevBarrierPosition, Integer currentScore) {

    private static Integer getValue(Properties p, String s){
        return Integer.parseInt(p.getProperty(s));
    }

    public static GameObjects loadDefaultGameObjects(){
        Properties property = new Properties();
        try (FileInputStream file = new FileInputStream("src/main/resources/DefaultSettings.properties")) {
            property.load(file);
            return new GameObjects(new Size(getValue(property, "field_height"), getValue(property, "field_width")),
                                   new Size(getValue(property, "bird_height"), getValue(property, "bird_width")),
                                   new Size(getValue(property, "barrier_space"), getValue(property, "barrier_width")),
                                   new Position(getValue(property, "bird_x"), getValue(property, "bird_y")),
                                   new Position(getValue(property, "barrier_position"), null),
                                   null,
                                   getValue(property, "score"));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}