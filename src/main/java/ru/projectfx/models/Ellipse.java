package ru.projectfx.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Fedor Danilov 14.11.2021
 */
public class Ellipse extends Figure {

    public Ellipse() {
    }
    /*
    * Эллипс преобразуется в Полигон
    * */
    public Ellipse(String readerString) {
        this.type = "";
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
        List<Point> coordList = new ArrayList<>();
        coordList.add(new Point(x1, y1));
        coordList.add(new Point(x2, y2));
        Point center = barycenter(coordList);   //Находим центр эллипса
        double a = (x2 - x1)/2;     //большая полуось
        double b = (y2 - y1)/2;     //малая полуось
        List<Point> coordinates = new ArrayList<>();
        for (int i = 0; i <= 360; i+=10) {
            double rad = Math.toRadians(i);
            double x = a*(Math.cos(rad)) + center.getX();
            double y = b*(Math.sin(rad)) + center.getY();
            coordinates.add(new Point( x, y));
        }
        this.coordinates = coordinates;
        this.type = "Region";
    }
}
