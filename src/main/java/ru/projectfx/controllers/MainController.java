package ru.projectfx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ru.projectfx.models.Coord;
import ru.projectfx.models.Region;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Fedor Danilov 13.11.2021
 */
public class MainController {
    @FXML
    private SplitPane root;

    @FXML
    private Button openFile;

    @FXML
    private void handleOpenFile(ActionEvent event) throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();//Класс работы с диалогом выборки и сохранения
        fileChooser.setTitle("Открыть");//Заголовок диалога
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("Обменный формат MapInfo (*.mif)", "*.mif");//Расширение
        fileChooser.getExtensionFilters().add(extFilter);
        //File file = fileChooser.showOpenDialog(new Stage());//Указываем текущую сцену CodeNote.mainStage
        if (true/*file != null*/) {
            //Open
            //System.out.println(file.getAbsoluteFile());

            try(BufferedReader reader = new BufferedReader(new FileReader("D:\\Работа\\MySoft\\Spring\\ProjectFX\\test_files\\TestTable.MIF"
                    //file.getAbsoluteFile()
            )))
            {
                //чтение построчно
                String s;
                while((s=reader.readLine())!=null){
                    //System.out.println(s);
                    Scanner scanner = new Scanner(s).useDelimiter("\\s* ");
                    while (scanner.hasNext()){
                        switch (scanner.next()){
                            case ("Rect"):
                                new Region(s, "Rect");   //Прямоугольник
                                break;
                            case ("Ellipse"): System.out.println(s);    //Эллипс
                                break;
                            case ("Region"):
                                List<Coord> coordinates = new ArrayList<>();
                                int i1 = scanner.nextInt();
                                for (int i = 0; i < i1; i++){
                                    Scanner regionScan = new Scanner(reader.readLine()).useDelimiter("\\s* ");
                                    int j1 = regionScan.nextInt();
                                    for (int j = 0; j < j1; j++){
                                        s = reader.readLine();
                                        Scanner coordScan = new Scanner(s).useDelimiter("\\s* ");
                                        while (coordScan.hasNext()) {
                                            coordinates.add(new Coord(Double.parseDouble(coordScan.next()), Double.parseDouble(coordScan.next())));
                                        }
                                    }
                                }
                                new Region(coordinates);
                                break;
                            case ("Pline"): System.out.println(s);  //Полилиния
                                break;
                            case ("Line"): System.out.println(s);   //Линия
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
            catch(IOException ex){

                System.out.println(ex.getMessage());
            }
        }
    }
}
