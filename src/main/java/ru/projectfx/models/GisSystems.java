package ru.projectfx.models;

import ru.projectfx.interfaces.FigureInterface;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Fedor Danilov 18.11.2021
 */
public abstract class GisSystems {
    String file;
    Map<Integer, FigureInterface> figureMap = new HashMap<>();

    abstract Map<Integer, FigureInterface> parseFile(String file);

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Map<Integer, FigureInterface> getFigureMap() {
        return figureMap;
    }

    public void setFigureMap(Map<Integer, FigureInterface> figureMap) {
        this.figureMap = figureMap;
    }
}
