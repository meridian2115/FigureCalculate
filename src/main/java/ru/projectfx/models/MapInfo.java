package ru.projectfx.models;

import ru.projectfx.interfaces.FigureInterface;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * @author Fedor Danilov 18.11.2021
 */
public class MapInfo extends GisSystems {

    public MapInfo() {
    }

    public MapInfo(String file) {
        this.figureMap = parseFile(file);
        this.file = file;
    }

    @Override
    public Map<Integer, FigureInterface> parseFile(String file){
        Map<Integer, FigureInterface> figure = new HashMap<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(file)))
            {
                String s;
                int figureNum = 0;
                String delimiter = "\\s* ";
                while((s=reader.readLine())!=null){
                    Scanner scanner = new Scanner(s).useDelimiter("\\s* ");
                    while (scanner.hasNext()){
                        switch (scanner.next()){
                            case ("Rect"):  //Прямоугольник
                                figure.put(figureNum++, new Region(s));
                                break;
                            case ("Ellipse"):   //Эллипс
                                figure.put(figureNum++, new Ellipse(s));
                                break;
                            case ("Region"):    //Полигон
                                List<Point> coordinates = new ArrayList<>();
                                int i1 = scanner.nextInt();
                                for (int i = 0; i < i1; i++){
                                    Scanner regionScan = new Scanner(reader.readLine()).useDelimiter(delimiter);
                                    int j1 = regionScan.nextInt();
                                    for (int j = 0; j < j1; j++){
                                        s = reader.readLine();
                                        Scanner pointScan = new Scanner(s).useDelimiter(delimiter);
                                        while (pointScan.hasNext()) {
                                            coordinates.add(new Point(Double.parseDouble(pointScan.next()), Double.parseDouble(pointScan.next())));
                                        }
                                    }
                                }
                                figure.put(figureNum++, new Region(coordinates));
                                break;
                            case ("Pline"): //Полилиния
                                coordinates = new ArrayList<>();
                                i1 = scanner.nextInt();
                                for (int i = 0; i < i1; i++){
                                    s = reader.readLine();
                                    Scanner pointScan = new Scanner(s).useDelimiter(delimiter);
                                    while (pointScan.hasNext()) {
                                        coordinates.add(new Point(Double.parseDouble(pointScan.next()), Double.parseDouble(pointScan.next())));
                                    }
                                }
                                figure.put(figureNum++, new PolyLine(coordinates));    //Полигон
                                break;
                            default:
                                break;
                        }
                    }
                }
            return figure;
        }
        catch(IOException ex){
            System.out.println("Ошибка чтения файла");
            System.out.println(ex.getMessage());
            return null;
        }
    }
}
