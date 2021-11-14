package ru.projectfx.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Fedor Danilov 13.11.2021
 */
public class Region extends Figure {

    public Region() {
    }

    public Region(List<Coord> coordinates) {
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
            coordinates.add(new Coord(x1, y1));
            coordinates.add(new Coord(x2, y1));
            coordinates.add(new Coord(x2, y2));
            coordinates.add(new Coord(x1, y2));
            coordinates.add(new Coord(x1, y1));
            this.type = "Region";
    }

    @Override
    public void rotateFigure(double degree) {
        double rad = Math.toRadians(degree);
        double x = 0;
        double y = 0;
        double x1 = 0;
        double y1 = 0;
        Coord center = barycenter(this.coordinates);
        List<Coord> newCoord = new ArrayList<>();

        for (Coord item: this.coordinates) {
            /*Расчет должен происходить относительно начала координат*/
            x = item.getX() - center.getX();
            y = item.getY() - center.getY();

            /*Матрица поворота в двумерном пространстве*/
            x1 = x * Math.cos(rad) - y * Math.sin(rad);
            y1 = x * Math.sin(rad) + y * Math.cos(rad);

            /*Возвращаем координату на свое место*/
            x = x1 + center.getX();
            y = y1 + center.getY();

            newCoord.add(new Coord(x, y));
        }

        this.coordinates = newCoord;
    }

    @Override
    public void moveFigure(double x, double y) {
        double x1 = 0;
        double y1 = 0;
        List<Coord> newCoord = new ArrayList<>();
        for (Coord item: this.coordinates) {
            x1 = item.getX() + x;
            y1 = item.getY() + y;
            newCoord.add(new Coord(x1, y1));
        }
        this.coordinates = newCoord;
    }

    @Override
    public void multiplyFigure(double multiply) {
        Coord center = barycenter(this.coordinates);
        double x = 0;
        double y = 0;
        List<Coord> newCoord = new ArrayList<>();
        for (Coord item: this.coordinates) {
            x = multiply * (item.getX() - center.getX()) + center.getX();
            y = multiply * (item.getY() - center.getY()) + center.getY();

            newCoord.add(new Coord(x, y));
        }
        this.coordinates = newCoord;
    }

    /*Подсчет происходит с помощью формулы площади Гаусса*/
    @Override
    public void calculateArea() {
        double area = 0;
        double x1 = 0;
        double y1 = 0;
        double x2 = 0;
        double y2 = 0;
        boolean first = true;
        for (Coord item: this.coordinates) {
            if (first) {
                x1 = item.getX();
                y1 = item.getY();
                first = false;      /*Заполнение первой координаты в переменные*/
            } else {
                 x2 = item.getX();
                 y2 = item.getY();
                 area += x1 * y2 - x2 * y1;
                 x1 = x2;
                 y1 = y2;
            }
        }
        this.area = area/2;
    }


    public void setArea(double area) {
        this.area = area;
    }


    public List<Coord> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Coord> coordinates) {
        this.coordinates = coordinates;
    }
}
