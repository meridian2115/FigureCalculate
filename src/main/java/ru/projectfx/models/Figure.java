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
        boolean first = type.equals("Region");
        for (Point item: coordinates) {
                if (first) {
                    first = false;
                    continue;
                }
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
                area += - x1 * y2 + x2 * y1;
                x1 = x2;
                y1 = y2;
            }
        }
        this.area = Math.abs(area/2);
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

    public boolean pointInFigure(double x, double y){
        if (this.coordinates.size() > 2){
            int countP = 0; //Количество пересечений
            boolean first = true;
            Point p1 = new Point(), p2 = new Point(), p3 = new Point(0, 0), p4 = new Point(x, y);
            for (Point point:
                 this.coordinates) {
                if (first) {
                    p1.setX(point.getX());
                    p1.setY(point.getY());
                    first = false;
                    continue;
                }
                p2.setX(point.getX());
                p2.setY(point.getY());
                if (checkIntersectionOfTwoLineSegments(p1, p2, p3, p4)) countP++;
            }
            return countP % 2 == 0 && countP != 0;
        }else if (this.coordinates.size() == 2) {
            double x1 = this.coordinates.get(0).getX();
            double y1 = this.coordinates.get(0).getY();
            double x2 = this.coordinates.get(1).getX();
            double y2 = this.coordinates.get(1).getY();
            x = (x - x1)/(x2-x1);
            y = (y - y1)/(y2-y1);
            if (x == y) {
                return true;
            } else return false;
        } else{
            return false;
        }
    }

    private boolean checkIntersectionOfTwoLineSegments(Point p1, Point p2,
                                                       Point p3, Point p4) {
        if (p2.getX() < p1.getX()) {
            Point tmp = p1;
            p1 = p2;
            p2 = tmp;
        }
        if (p4.getX() < p3.getX()) {
            Point tmp = p3;
            p3 = p4;
            p4 = tmp;
        }
        if (p2.getX() < p3.getX()) {
            return false;
        }
        //если оба отрезка вертикальные
        if((p1.getX() - p2.getX()) == 0 && (p3.getX() - p4.getX()) == 0) {
            //если они лежат на одном X
            if(p1.getX() == p3.getX()) {
                //Проверим, пересекаются ли они, т.е. есть ли у них общий Y
                //для этого возьмём отрицание от случая, когда они НЕ пересекаются
                return !((Math.max(p1.getY(), p2.getY()) < Math.min(p3.getY(), p4.getY())) ||
                        (Math.min(p1.getY(), p2.getY()) > Math.max(p3.getY(), p4.getY())));
            }
            return false;
        }
        //если первый отрезок вертикальный
        if (p1.getX() - p2.getX() == 0) {
            //найдём Xa, Ya - точки пересечения двух прямых
            double Xa = p1.getX();
            double A2 = (p3.getY() - p4.getY()) / (p3.getX() - p4.getX());
            double b2 = p3.getY() - A2 * p3.getX();
            double Ya = A2 * Xa + b2;

            if (p3.getX() <= Xa && p4.getX() >= Xa && Math.min(p1.getY(), p2.getY()) <= Ya &&
                    Math.max(p1.getY(), p2.getY()) >= Ya) {
                return true;
            }
            return false;
        }
        //если второй отрезок вертикальный
        if (p3.getX() - p4.getX() == 0) {

            //найдём Xa, Ya - точки пересечения двух прямых
            double Xa = p3.getX();
            double A1 = (p1.getY() - p2.getY()) / (p1.getX() - p2.getX());
            double b1 = p1.getY() - A1 * p1.getX();
            double Ya = A1 * Xa + b1;
            return p1.getX() <= Xa && p2.getX() >= Xa && Math.min(p3.getY(), p4.getY()) <= Ya &&
                    Math.max(p3.getY(), p4.getY()) >= Ya;
        }
        //оба отрезка невертикальные
        double A1 = (p1.getY() - p2.getY()) / (p1.getX() - p2.getX());
        double A2 = (p3.getY() - p4.getY()) / (p3.getX() - p4.getX());
        double b1 = p1.getY() - A1 * p1.getX();
        double b2 = p3.getY() - A2 * p3.getX();
        if (A1 == A2) {
            return false; //отрезки параллельны
        }
        //Xa - абсцисса точки пересечения двух прямых
        double Xa = (b2 - b1) / (A1 - A2);
        if ((Xa < Math.max(p1.getX(), p3.getX())) || (Xa > Math.min( p2.getX(), p4.getX()))) {
            return false; //точка Xa находится вне пересечения проекций отрезков на ось X
        }
        else {
            return true;
        }
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
