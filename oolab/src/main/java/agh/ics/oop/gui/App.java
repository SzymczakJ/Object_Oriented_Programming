package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.List;

public class App extends Application {

    @Override
    public void init() throws Exception {
        MoveDirection[] directions = new OptionsParser().parse(getParameters().getRaw().toArray(new String[0]));
        Vector2d[] positions = {new Vector2d(2, 2), new Vector2d(3, 4)};
        IWorldMap grassField = new GrassField(10);
        IEngine grassEngine = new SimulationEngine(grassField, directions, positions);
        System.out.println(grassField.toString());
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.show();
        Label label = new Label("Zwierzak");
        Label label1 = new Label("dupa");
        GridPane gridPane = new GridPane();
        gridPane.add(label, 0, 0, 1, 1);
        gridPane.add(label1, 1, 1, 1, 1);
        gridPane.setGridLinesVisible(true);
        Scene scene = new Scene(gridPane, 400, 400);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
