package ru.projectfx.models;

/**
 * @author Fedor Danilov 13.11.2021
 */
public class Coord {
    private double x;
    private double y;

    public Coord() {
    }

    public Coord(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
