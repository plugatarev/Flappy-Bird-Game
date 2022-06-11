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
            Integer barrierPosition = getValue(property, "barrier_position");
            Integer barrierWidth = getValue(property, "barrier_width");
            Integer barrierGap = getValue(property, "barrier_gap");
            BarrierModel curBarrier = new BarrierModel(barrierPosition, barrierWidth, barrierGap, null);

            Size fieldSize = new Size(getValue(property, "field_height"), getValue(property, "field_width"));
            Integer height = getValue(property, "ground_height");

            Size birdSize = new Size(getValue(property, "bird_height"), getValue(property, "bird_width"));
            Position birdPosition = new Position(getValue(property, "bird_x"), getValue(property, "bird_y"));

            Integer score = getValue(property, "score");

            return new GameObjects(fieldSize, birdSize, birdPosition, curBarrier,null, score, height);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}