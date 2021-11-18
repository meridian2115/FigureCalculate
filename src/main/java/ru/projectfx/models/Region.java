package ru.projectfx.models;

import java.util.List;
import java.util.Scanner;

/**
 * @author Fedor Danilov 13.11.2021
 */
public class Region extends Figure {

    public Region() {
    }

    public Region(List<Point> coordinates) {
        this.coordinates = coordinates;
        this.type = "Region";
    }
    /*
    * readerString - Строка из файла, приходит прямоугольник
    * */
    public Region(String readerString) {
            Scanner scanner = new Scanner(readerString).useDelimiter("\\s* ");
            double x1 = 0;
            double y1 = 0;
            double x2 = 0;
            double y2 = 0;
            while (scanner.hasNext()){
                scanner.next();
                x1 = Double.parseDouble(scanner.next());
                y1 = Double.parseDouble(scanner.next());
                x2 = Double.parseDouble(scanner.next());
                y2 = Double.parseDouble(scanner.next());
            }
            coordinates.add(new Point(x1, y1));
            coordinates.add(new Point(x2, y1));
            coordinates.add(new Point(x2, y2));
            coordinates.add(new Point(x1, y2));
            coordinates.add(new Point(x1, y1));
            this.type = "Region";
    }
}
