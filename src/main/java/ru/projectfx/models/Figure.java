package ru.projectfx.models;

import ru.projectfx.interfaces.CalculateInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Fedor Danilov 13.11.2021
 */
public abstract class Figure implements CalculateInterface {
    double area = 0;    //Площадь
    List<Point> coordinates = new ArrayList<>(); //Координаты
    String type;

    /*
    Метод для нахождения барицентра фигуры
     */
    public Point barycenter(List<Point> coordinates){
        double x = 0;
        double y = 0;
        for (Point item: coordinates) {
            x += item.getX();
            y += item.getY();
        }
        int listSize = (type.equals("Region")) ? coordinates.size()-1 : coordinates.size();
        return new Point((x/listSize), (y/listSize));
    }

    @Override
    public void rotateFigure(double degree) {
        double rad = Math.toRadians(degree);
        double x = 0;
        double y = 0;
        double x1 = 0;
        double y1 = 0;
        Point center = barycenter(this.coordinates);
        List<Point> newCoord = new ArrayList<>();

        for (Point item: this.coordinates) {
            /*Расчет должен происходить относительно начала координат*/
            x = item.getX() - center.getX();
            y = item.getY() - center.getY();

            /*Матрица поворота в двумерном пространстве*/
            x1 = x * Math.cos(rad) - y * Math.sin(rad);
            y1 = x * Math.sin(rad) + y * Math.cos(rad);

            /*Возвращаем координату на свое место*/
            x = x1 + center.getX();
            y = y1 + center.getY();

            newCoord.add(new Point(x, y));
        }

        this.coordinates = newCoord;
    }

    @Override
    public void moveFigure(double x, double y) {
        double x1 = 0;
        double y1 = 0;
        List<Point> newCoord = new ArrayList<>();
        for (Point item: this.coordinates) {
            x1 = item.getX() + x;
            y1 = item.getY() + y;
            newCoord.add(new Point(x1, y1));
        }
        this.coordinates = newCoord;
    }

    /*Подсчет происходит с помощью формулы площади Гаусса*/
    @Override
    public void calculateArea() {
        if(!this.type.equals("Region")) return;
        double area = 0;
        double x1 = 0;
        double y1 = 0;
        double x2 = 0;
        double y2 = 0;
        boolean first = true;
        for (Point item: this.coordinates) {
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

    @Override
    public void multiplyFigure(double multiply) {
        Point center = barycenter(this.coordinates);
        double x = 0;
        double y = 0;
        List<Point> newCoord = new ArrayList<>();
        for (Point item: this.coordinates) {
            x = multiply * (item.getX() - center.getX()) + center.getX();
            y = multiply * (item.getY() - center.getY()) + center.getY();

            newCoord.add(new Point(x, y));
        }
        this.coordinates = newCoord;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public List<Point> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Point> coordinates) {
        this.coordinates = coordinates;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
