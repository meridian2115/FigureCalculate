package ru.projectfx.interfaces;


import ru.projectfx.models.Point;

import java.util.List;

/**
 * @author Fedor Danilov 13.11.2021
 */
public interface FigureInterface {

    //Повернуть фигуру на определенный градус
    void rotateFigure(double degree);

    //Сместить фигуру на x и y координат
    void moveFigure(double x, double y);

    //Увеличить/ уменьшить в
    void multiplyFigure(double multiply);

    //Подсчет площади объекта
    void calculateArea();

    //Нахождение барицентра фигуры
    Point barycenter(List<Point> coordinates);

    //Проверить точку на вхождение в фигуру
    boolean pointInFigure(double x, double y);

    double getArea();

    void setArea(double area);

    List<Point> getCoordinates();

    void setCoordinates(List<Point> coordinates);

    String getType();

    void setType(String type);
}
