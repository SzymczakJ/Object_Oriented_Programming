package simulation;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import simulation.gui.App;

public class World {

    public static void main(String[] args) {
        Application.launch(App.class, args);
        try {
//            RectangularMap rectangularMap = new RectangularMap(30, 30, (float) 0.34, 50, 20);
//            rectangularMap.initializeMapWithAnimals(5, 10);
//            rectangularMap.moveAllAnimals();
        } catch (IllegalArgumentException e) {
            System.out.println("dupsko");
        }
    }



}
