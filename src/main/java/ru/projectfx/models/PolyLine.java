package ru.projectfx.models;

import java.util.List;

/**
 * @author Fedor Danilov 14.11.2021
 */
public class PolyLine extends Figure {
    public PolyLine() {
    }

    public PolyLine(List<Point> coordList) {
        this.coordinates = coordList;
        this.type = "Pline";
    }
}
