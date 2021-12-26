package simulation.gui;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import simulation.*;

import java.util.ArrayList;
import java.util.List;

public class App extends Application {
    private List<Node> listOfVBoxElements = new ArrayList<>();
    private GridPane gridPane = new GridPane();
    XYChart.Series numberOfAnimalsSeries = new XYChart.Series();
    XYChart.Series numberOfGrassesSeries = new XYChart.Series();
    XYChart.Series averageEnergySeries = new XYChart.Series();
    XYChart.Series averageChildrenCount = new XYChart.Series();
    XYChart.Series averageLifeSpan = new XYChart.Series();
    VBox genotypeDominantVBox = new VBox();

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
        startButton.setOnAction((event) -> {
            showSimulation();
        });

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

    public void renderMap(AbstractWorldMap map) {
        numberOfAnimalsSeries.getData().add(new XYChart.Data(map.getMapEra(), map.getNumberOfAnimals()));
        numberOfGrassesSeries.getData().add(new XYChart.Data(map.getMapEra(), map.getNumberOfGrasses()));
        averageEnergySeries.getData().add(new XYChart.Data(map.getMapEra(), map.getAverageEnergy()));
        averageChildrenCount.getData().add(new XYChart.Data(map.getMapEra(), map.getAverageChildrenCount()));
        averageLifeSpan.getData().add(new XYChart.Data(map.getMapEra(), map.getAverageLifeSpan()));
        updateDominants(map.getDominantGenotypes());
        gridPane.setGridLinesVisible(false);
        gridPane.getColumnConstraints().clear();
        gridPane.getRowConstraints().clear();
        gridPane.getChildren().clear();
        gridPane.setGridLinesVisible(true);
        Vector2d[] constraints = map.computeBounds();
        int maxX = constraints[1].x;
        int maxY = constraints[1].y;
        for (int x = 0; x <= maxX; x++) {
            for (int y = 0; y <= maxY; y++) {
                Node singleCell = createCell(x, y, map);
                gridPane.add(singleCell, x, y, 1, 1);
            }
        }
    }

    public Node createCell(int x, int y, AbstractWorldMap map) {
        Vector2d position = new Vector2d(x, y);
        Object objectAtPosition = map.objectAt(position);
        if (objectAtPosition instanceof Animal) {
            Button animalButton = new Button();
            animalButton.setStyle("-fx-background-color: " + ((Animal) objectAtPosition).getEnergyColor());
            animalButton.setOnAction(value -> {
                animalButton.setStyle("-fx-background-color: #00ff00");
            });
            return animalButton;
        }
        else if (objectAtPosition instanceof Grass) {
            Button grass = new Button();
            grass.setStyle("-fx-background-color: #7cfc00");
            return grass;
        }
        else {
            Button normalTile = new Button();
            if (position.follows(map.lowerLeftJungleCorner) && position.precedes(map.higherRightJungleCorner)) {
                normalTile.setStyle("-fx-background-color: #008000");
            }
            else {
                normalTile.setStyle("-fx-background-color: #F5DEB3");
            }
            return normalTile;
        }
    }

    public void showSimulation() {
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Era");
        LineChart<Number,Number> lineChart = new LineChart<Number,Number>(xAxis,yAxis);
        numberOfAnimalsSeries.setName("Number of animals");
        lineChart.getData().add(numberOfAnimalsSeries);
        numberOfGrassesSeries.setName("Number of grasses");
        lineChart.getData().add(numberOfGrassesSeries);
        averageEnergySeries.setName("Average energy");
        lineChart.getData().add(averageEnergySeries);
        averageChildrenCount.setName("Average children counter");
        lineChart.getData().add(averageChildrenCount);
        averageLifeSpan.setName("Average children counter");
        lineChart.getData().add(averageLifeSpan);
        RectangularMap rectangularMap = new RectangularMap(30, 30, (float) 0.34, 500, 100, 5);
        SimulationEngine engine = new SimulationEngine(rectangularMap, this, 50, 5, 1000);
        Thread engineThread = new Thread(engine);
        engineThread.start();
        VBox vBox = new VBox(gridPane, genotypeDominantVBox);
        Scene simulationScene = new Scene(vBox, 1000, 1000);
        Stage simulationStage = new Stage();
        simulationStage.setTitle("Simulation");
        simulationStage.setScene(simulationScene);
        simulationStage.show();
    }

    public void updateDominants(List<Genotype> genotypes) {
        genotypeDominantVBox.getChildren().clear();
        genotypeDominantVBox.getChildren().add(new Text("Dominants:"));
        for (Genotype genotype: genotypes) {
            genotypeDominantVBox.getChildren().add(new Text(genotype.toString()));
        }
    }
}
