package ru.projectfx.interfaces;


/**
 * @author Fedor Danilov 13.11.2021
 */
public interface CalculateInterface {

    //Повернуть фигуру на определенный градус
    void rotateFigure(double degree);

    //Сместить фигуру на x и y координат
    void moveFigure(double x, double y);

    //Увеличить/ уменьшить в
    void multiplyFigure(double multiply);

    //Подсчет площади объекта
    void calculateArea();
}
