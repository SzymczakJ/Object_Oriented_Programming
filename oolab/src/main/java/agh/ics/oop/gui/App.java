package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App extends Application {
    GridPane gridPane = new GridPane();
    MapDirection animalsOrientation = MapDirection.NORTH;

    @Override
    public void start(Stage primaryStage) throws Exception {
        TextField textField = new TextField();
        Button startButton = new Button();
        startButton.setText("Start");
        startButton.setOnAction((event) -> {
            String text = textField.getText();
            String[] commands = text.split(" ");
            MoveDirection[] directions = new OptionsParser().parse(commands);
            Vector2d[] positions = {new Vector2d(5, 5), new Vector2d(3, 4)};
            AbstractWorldMap grassField = new GrassField(10);
            SimulationEngine engine = new SimulationEngine(grassField, directions, positions, this, 100, animalsOrientation);
            Thread engineThread = new Thread(engine);
            engineThread.start();
        });
        Button directionButton = new Button();
        directionButton.setText(animalsOrientation.toString());
        directionButton.setOnAction((event) -> {
            animalsOrientation = animalsOrientation.next();
            directionButton.setText(animalsOrientation.toString());
        });
        HBox hbox = new HBox(gridPane, startButton, textField, directionButton);
        Scene scene = new Scene(hbox, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void renderMap(AbstractWorldMap map) {
        gridPane.setGridLinesVisible(false);
        gridPane.getColumnConstraints().clear();
        gridPane.getRowConstraints().clear();
        gridPane.getChildren().clear();
        gridPane.setGridLinesVisible(true);
        Vector2d[] constraints = map.computeBounds();
        int maxX = constraints[1].getX();
        int maxY = constraints[1].getY();
        int minX = constraints[0].getX();
        int minY = constraints[0].getY();
        Label labelXY = new Label("y/x");
        gridPane.add(labelXY, 0, 0, 1, 1);
        for (int i = 1; i <= maxX - minX + 1; i++) {
            Label label = new Label(String.valueOf(i + minX - 1));
            gridPane.getColumnConstraints().add(new ColumnConstraints(30));
            gridPane.add(label, i, 0, 1, 1);
            GridPane.setHalignment(label, HPos.CENTER);
        }
        for (int i = 1; i <= maxY - minY + 1; i++) {
            Label label = new Label(String.valueOf(maxY - i + 1));
            gridPane.getRowConstraints().add(new RowConstraints(30));
            gridPane.add(label, 0, i, 1, 1);
            GridPane.setHalignment(label, HPos.CENTER);
        }

        Vector2d[] positionsOfObjects = map.getAllPositions();
        for (Vector2d position : positionsOfObjects) {
            int xCoordinate = position.getX();
            int yCoordinate = position.getY();
            Object object = map.objectAt(position);
            GuiElementBox image = GuiElementBox.GuiElementBoxFactory((AbstractWorldMapElement) object);
            gridPane.add(image.getVbox(), 1 + xCoordinate - minX, 1 + maxY - yCoordinate, 1, 1);
            GridPane.setHalignment(image.getVbox(), HPos.CENTER);
        }
    }
}
