package ru.projectfx.models;

import ru.projectfx.interfaces.CalculateInterface;

import java.util.List;

/**
 * @author Fedor Danilov 13.11.2021
 */
abstract class Figure implements CalculateInterface {
    double area;    //Площадь
    List<Coord> coordinates; //Координаты
}
