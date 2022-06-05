package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Properties;

public record GameObjects(Size fieldSize, Size birdSize, Position birdPosition,
                          BarrierModel curBarrier, BarrierModel prevBarrier, Integer currentScore,
                          Integer groundHeight) {

    private static Integer getValue(Properties p, String s){
        return Integer.parseInt(p.getProperty(s));
    }

    public static GameObjects readFromFile(){
        Properties property = new Properties();
        try (FileInputStream file = new FileInputStream("src/main/resources/DefaultSettings.properties")) {
            property.load(file);
            BarrierModel curBarrier = new BarrierModel(getValue(property, "barrier_position"), getValue(property, "barrier_width"),
                    getValue(property, "barrier_gap"), null);

            return new GameObjects(new Size(getValue(property, "field_height"), getValue(property, "field_width")),
                                   new Size(getValue(property, "bird_height"), getValue(property, "bird_width")),
                    new Position(getValue(property, "bird_x"), getValue(property, "bird_y")),
                    curBarrier,null, getValue(property, "score"), getValue(property, "ground_height"));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}