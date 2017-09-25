package io.stemlab.github.entity;

public class Coordinate {
    public double x;
    public double y;

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Coordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
}
