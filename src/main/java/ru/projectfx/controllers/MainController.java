package ru.projectfx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ru.projectfx.models.*;

import java.io.*;
import java.util.*;

/**
 * @author Fedor Danilov 13.11.2021
 */
public class MainController {
    @FXML    private SplitPane root;
    @FXML    private AnchorPane graph;
    @FXML   private TextField xPosition;
    @FXML   private TextField yPosition;

    private double x;
    private double y;
    private Map<Integer, Figure> figureMap = new HashMap<>();

    @FXML
    private void handleOpenFile(ActionEvent event) throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Открыть");
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("Обменный формат MapInfo (*.mif)", "*.mif");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.getInitialDirectory();
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            MapInfo mapInfo = new MapInfo(file.getAbsolutePath());
            this.figureMap = mapInfo.getFigureMap();
            drawGraph(figureMap);
        }
    }

    public void graphMouseMoved(MouseEvent event){
        this.xPosition.setText(String.valueOf(event.getX()));
        this.yPosition.setText(String.valueOf(event.getY()));
    }

    public void graphMouseReleased(MouseEvent event){
        double x = event.getX();
        double y = event.getY();
        graph.setTranslateX(graph.getTranslateX() + x - this.x);
        graph.setTranslateY(graph.getTranslateY() + y - this.y);
    }

    public void graphMousePressed(MouseEvent event){
        this.x = event.getX();
        this.y = event.getY();
    }

    public void graphCheckFigure(MouseEvent event){
        this.x = event.getX();
        this.y = event.getY();
    }

    private void drawGraph(Map<Integer, Figure> figureMap){
        graph.getChildren().clear();
        Path path = new Path();
        double x = 0;
        double y = 0;
        for (Map.Entry<Integer, Figure> item : figureMap.entrySet()) {
            int key = item.getKey();
            Figure figure = item.getValue();
            boolean firstPoint = true;
            for (Point point : figure.getCoordinates()) {
                if (firstPoint) {
                    MoveTo moveTo = new MoveTo(point.getX(), point.getY());
                    path.getElements().add(moveTo);
                    firstPoint = false;
                    x = point.getX();
                    y = point.getY();
                    continue;
                }
                LineTo lineTo = new LineTo(point.getX(), point.getY());
                path.getElements().add(lineTo);
            }
        }
        graph.getChildren().add(path);
        graph.addEventFilter(ScrollEvent.ANY, scrollEvent -> {
            double zoomFactor = 1.05;
            double deltaY = scrollEvent.getDeltaY();
            if (deltaY < 0) {
                zoomFactor = 2.0 - zoomFactor;
            }
            graph.setScaleX(graph.getScaleX() * zoomFactor);
            graph.setScaleY(graph.getScaleY() * zoomFactor);
        });
    }
}
