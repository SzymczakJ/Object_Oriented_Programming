package simulation.gui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import simulation.*;
import simulation.maps.AbstractWorldMap;
import simulation.maps.RectangularMap;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class App extends Application {
    private List<Node> listOfVBoxElements = new ArrayList<>();
    private GridPane[] gridPane = {new GridPane(), new GridPane()};
    private XYChart.Series[] numberOfAnimalsSeries = {new XYChart.Series(), new XYChart.Series()};
    private XYChart.Series[] numberOfGrassesSeries = {new XYChart.Series(), new XYChart.Series()};
    private XYChart.Series[] averageEnergySeries = {new XYChart.Series(), new XYChart.Series()};
    private XYChart.Series[] averageChildrenCount = {new XYChart.Series(), new XYChart.Series()};
    private XYChart.Series[] averageLifeSpan = {new XYChart.Series(), new XYChart.Series()};
    private VBox genotypeDominantVBox = new VBox();
    private boolean stopSimulation = false;
    private VBox selectedAnimalVBox = new VBox();
    private List<double[]> statistics = new ArrayList<>();

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
        writeToStatistics(map);
        updateDominants(map.getDominantGenotypes());
        updateAnimalTrackerVBox(map);
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
            animalButton.setOnAction(event -> {
                map.setCurrentTracker(new AnimalTracker((Animal) objectAtPosition));
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
        RectangularMap rectangularMap = new RectangularMap(30, 30, (float) 0.34, 50, 50, 5);
        ISimulationEngine engine = new SimulationEngine(rectangularMap, this, 50, 1, 500);
        Thread engineThread = new Thread((Runnable) engine);
        engineThread.start();
        Button stopSimulation = new Button("Stop simulation");
        stopSimulation.setOnAction(event -> {
            if (!this.stopSimulation) {
                    stopSimulation.setText("Start simulation");
                    this.stopSimulation = true;
                }
            else    {
                    stopSimulation.setText("Stop simulation");
                    this.stopSimulation = false;
                }
        });
        Button writeToCSV = new Button("Write to CSV");
        writeToCSV.setOnAction(event -> {
            try {
                writeToCSVFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        VBox vBox = new VBox(gridPane, selectedAnimalVBox, stopSimulation, writeToCSV);
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

    public boolean getStopSimulation1() {
        return stopSimulation;
    }

    public void updateAnimalTrackerVBox(AbstractWorldMap map) {
        if (map.getAnimalTracker() == null) return;

        AnimalTracker animalTracker = map.getAnimalTracker();
        selectedAnimalVBox.getChildren().clear();
        selectedAnimalVBox.getChildren().add(new Text("Animal"));
        selectedAnimalVBox.getChildren().add(new Text("Genotype: " + animalTracker.getAnimal().genotype.toString()));
        selectedAnimalVBox.getChildren().add(new Text("Children: " + String.valueOf(animalTracker.getTrackedChildrenNumber())));
        selectedAnimalVBox.getChildren().add(new Text("Descendants: " + String.valueOf(animalTracker.getTrackedDescendantsNumber())));
        if (animalTracker.getDeathEra() != -1) {
            selectedAnimalVBox.getChildren().add(new Text("Death era: " + String.valueOf(animalTracker.getDeathEra())));
        }
        if (animalTracker.getDeathEra() == -1) {
            System.out.println("dupa");
        }
    }

    public void writeToStatistics(AbstractWorldMap map) {
        double[] eraStatistics = new double[5];
        eraStatistics[0] = map.getNumberOfAnimals();
        eraStatistics[1] = map.getNumberOfGrasses();
        eraStatistics[2] = map.getAverageEnergy();
        eraStatistics[3] = map.getAverageChildrenCount();
        eraStatistics[4] = map.getAverageLifeSpan();
        statistics.add(eraStatistics);
    }

    public void writeToCSVFile() throws IOException {
        FileWriter csvFile = new FileWriter("statistics.csv");
        BufferedWriter out = new BufferedWriter(csvFile);
        String header = "Number of animals, Number of grasses, Average energy, Average children count, Average life span\n";
        out.write(header);

        double[] dataSums = {0, 0, 0, 0, 0};
        for (double[] data: statistics) {
            out.write(String.valueOf(data[0]) + ", " + String.valueOf(data[1]) + ", " + String.valueOf(data[2]) + ", " +
                    String.valueOf(data[3]) + ", " + String.valueOf(data[4]) + "\n");
            dataSums[0] += data[0];
            dataSums[1] += data[1];
            dataSums[2] += data[2];
            dataSums[3] += data[3];
            dataSums[4] += data[4];
        }
        out.write(String.valueOf( dataSums[0] / statistics.size()) + ", " +
                String.valueOf( dataSums[1] / statistics.size()) + ", "
                + String.valueOf( dataSums[2] / statistics.size()) + ", " +
                String.valueOf( dataSums[3] / statistics.size())
                + ", " + String.valueOf( dataSums[4] / statistics.size()));
        out.close();
        System.out.println(dataSums[0] / statistics.size());
    }
}
