package utils;
public record FieldModel(Position birdPosition, Position barrierPosition,
                         Position prevBarrierPosition, boolean isDownDirection) {}