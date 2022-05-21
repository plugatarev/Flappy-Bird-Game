package utils;

import model.Barrier;
import model.Field;

public record GameObjects(Position birdPosition, Position barrierPosition, Position prevBarrierPosition, int currentScore) {
    public static GameObjects getGameObjects(Field field){
        Position birdPosition = new Position(field.getBird().getX(), field.getBird().getY());
        Position barrierPosition = new Position(field.getCurrentBarrier().getCurrentPosition(), field.getCurrentBarrier().getUpperY());
        Barrier b = field.getPrevBarrier();
        Position prevBarrierPosition = null;
        if (b != null)  prevBarrierPosition = new Position(b.getCurrentPosition(), b.getUpperY());
        return new GameObjects(birdPosition, barrierPosition, prevBarrierPosition, field.getCurrentScore());
    }
}
