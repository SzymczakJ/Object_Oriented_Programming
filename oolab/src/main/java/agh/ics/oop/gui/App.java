package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import java.util.List;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            MoveDirection[] directions = new OptionsParser().parse(getParameters().getRaw().toArray(new String[0]));
            Vector2d[] positions = {new Vector2d(5, 5), new Vector2d(-1, -6)};
            AbstractWorldMap grassField = new GrassField(10);
            IEngine grassEngine = new SimulationEngine(grassField, directions, positions);
            Vector2d[] constraints = grassField.computeBounds();
            int maxX = constraints[1].getX();
            int maxY = constraints[1].getY();
            int minX = constraints[0].getX();
            int minY = constraints[0].getY();
            GridPane gridPane = new GridPane();
            System.out.println(maxX);
            System.out.println(minX);
            System.out.println(maxY);
            System.out.println(minY);
            Label labelXY = new Label("y/x");
            gridPane.add(labelXY, 0, 0, 1, 1);
            for (int i = 1; i <= maxX - minX + 1; i++) {
                Label label = new Label(String.valueOf(i + minX - 1));
                gridPane.getColumnConstraints().add(new ColumnConstraints(20));
                gridPane.add(label, i, 0, 1, 1);
                GridPane.setHalignment(label, HPos.CENTER);
            }
            for (int i = 1; i <= maxY - minY + 1; i++) {
                Label label = new Label(String.valueOf(maxY - i + 1));
                gridPane.getRowConstraints().add(new RowConstraints(20));
                gridPane.add(label, 0, i, 1, 1);
                GridPane.setHalignment(label, HPos.CENTER);
            }

            Vector2d[] positionsOfObjects = grassField.getAllPositions();
            for (Vector2d position : positionsOfObjects) {
                int xCoordinate = position.getX();
                int yCoordinate = position.getY();
                Object object = grassField.objectAt(position);
                Label label = new Label(object.toString());
                gridPane.add(label, 1 + xCoordinate - minX, 1 + maxY - yCoordinate, 1, 1);
                GridPane.setHalignment(label, HPos.CENTER);
            }
            gridPane.setGridLinesVisible(true);
            Scene scene = new Scene(gridPane, 400, 400);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IllegalArgumentException e){
            System.out.println(e);
        }
    }
}
