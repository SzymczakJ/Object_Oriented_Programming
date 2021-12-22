package simulation.gui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class App extends Application {
    private List<Node> listOfVBoxElements = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) throws Exception {
        createText("Map properties");
        TextField mapHeightTextField = createHBoxWithTextField("Map height: ");
        TextField mapWidthTextField = createHBoxWithTextField("Map width: ");
        TextField jungleHeightTextField = createHBoxWithTextField("Jungle height: ");
        TextField jungleWidthTextField = createHBoxWithTextField("Jungle width: ");

        createText("Energy properties");
        TextField grassEnergyBoostTextField = createHBoxWithTextField("Grass energy boost: ");
        TextField minimalEnergyToBreedTextField = createHBoxWithTextField("Minimal energy to breed: ");
        TextField animalStartingEnergyTextField = createHBoxWithTextField("Animal starting energy: ");
        TextField dailyEnergyCostTextField = createHBoxWithTextField("Daily energy cost: ");

        createText("Spawning properties");
        TextField animalsSpawningAtTheStart = createHBoxWithTextField("Animals spawning at the start");
        TextField grassSpawnedInEachDay = createHBoxWithTextField("Grass spawned in each day");

        createText("Others");
        TextField realRefreshTime = createHBoxWithTextField("Real refresh time(ms)");

        Button startButton = new Button("Start simulation");

        VBox vBox = new VBox();
        addElementsToVBox(vBox);
        vBox.getChildren().add(startButton);
        vBox.setSpacing(20);
        vBox.setAlignment(Pos.BASELINE_CENTER);

        Scene scene = new Scene(vBox, 600, 700);
        primaryStage.setTitle("Symulation settings"); //git
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private TextField createHBoxWithTextField(String textFieldName) {
        Text nameOfTextField = new Text(textFieldName);
        TextField textField = new TextField();
        HBox hBox = new HBox(20, nameOfTextField, textField);
        hBox.setAlignment(Pos.BASELINE_CENTER);
        listOfVBoxElements.add(hBox);
        return textField;
    }

    private void createText(String text) {
        Text createdText = new Text(text);
        listOfVBoxElements.add(createdText);
    }

    private void addElementsToVBox(VBox vBox) {
        for (Node elementOfVBox: listOfVBoxElements) {
            vBox.getChildren().add(elementOfVBox);
        }
    }
}
