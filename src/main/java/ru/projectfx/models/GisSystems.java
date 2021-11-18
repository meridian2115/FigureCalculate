package ru.projectfx.models;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Fedor Danilov 18.11.2021
 */
public abstract class GisSystems {
    String file;
    Map<Integer, Figure> figureMap = new HashMap<>();

    abstract Map<Integer, Figure> parseFile(String file);

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Map<Integer, Figure> getFigureMap() {
        return figureMap;
    }

    public void setFigureMap(Map<Integer, Figure> figureMap) {
        this.figureMap = figureMap;
    }
}
