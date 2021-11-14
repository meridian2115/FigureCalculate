package ru.projectfx.models;

import ru.projectfx.interfaces.CalculateInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Fedor Danilov 13.11.2021
 */
public abstract class Figure implements CalculateInterface {
    double area;    //Площадь
    List<Coord> coordinates = new ArrayList<>(); //Координаты
    String type;

    /*
    Метод для нахождения барицентра фигуры
     */
    public Coord barycenter(List<Coord> coordinates){
        double x = 0;
        double y = 0;
        for (Coord item: coordinates) {
            x = item.getX();
            y = item.getY();
        }
        int listSize = coordinates.size()-1;
        return new Coord((x/listSize), (y/listSize));
    }
}
