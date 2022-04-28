package controller;


/*
.............||
.............||
.............||
.............||
.............@
.............||
.............||
.............||
.............||
 */

public record ViewField(Position birdPosition, Position firstBarrier, Position secondBarrier) {}

record Position(int x, int y) {}
