package simulation.gui;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import simulation.*;
import simulation.maps.AbstractWorldMap;
import simulation.maps.FoldedMap;
import simulation.maps.BoundedMap;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.sqrt;

public class App extends Application {
    private List<Node> listOfVBoxElements = new ArrayList<>();
    private GridPane[] gridPane = {new GridPane(), new GridPane()};
    private XYChart.Series[] numberOfAnimalsSeries = {new XYChart.Series(), new XYChart.Series()};
    private XYChart.Series[] numberOfGrassesSeries = {new XYChart.Series(), new XYChart.Series()};
    private XYChart.Series[] averageEnergySeries = {new XYChart.Series(), new XYChart.Series()};
    private XYChart.Series[] averageChildrenCount = {new XYChart.Series(), new XYChart.Series()};
    private XYChart.Series[] averageLifeSpan = {new XYChart.Series(), new XYChart.Series()};
    private VBox[] genotypeDominantVBox = {new VBox(), new VBox()};
    private boolean[] stopSimulation = {false, false};
    private VBox[] selectedAnimalVBox = {new VBox(), new VBox()};
    private List<List<double[]>> statistics = new ArrayList<List<double[]>>(Arrays.asList(new ArrayList<>(), new ArrayList<>()));
    private boolean boundedMapIsMagical = false;
    private boolean foldedMapIsMagical = false;


    @Override
    public void start(Stage primaryStage) throws Exception {
        createText("Map properties");
        TextField mapHeightTextField = createHBoxWithTextField("Map height: ");
        mapHeightTextField.setText("30");
        TextField mapWidthTextField = createHBoxWithTextField("Map width: ");
        mapWidthTextField.setText("30");
        TextField jungleRatio = createHBoxWithTextField("Jungle ratio: ");
        jungleRatio.setText("0.125");
        createText("Energy properties");
        TextField grassEnergyBoostTextField = createHBoxWithTextField("Grass energy boost: ");
        grassEnergyBoostTextField.setText("100");
        TextField animalStartingEnergyTextField = createHBoxWithTextField("Animal starting energy: ");
        animalStartingEnergyTextField.setText("200");
        TextField dailyEnergyCostTextField = createHBoxWithTextField("Daily energy cost: ");
        dailyEnergyCostTextField.setText("2");
        createText("Spawning properties");
        TextField animalsSpawningAtTheStart = createHBoxWithTextField("Animals spawning at the start");
        animalsSpawningAtTheStart.setText("20");
        createText("Others");
        TextField realRefreshTime = createHBoxWithTextField("Real refresh time(ms)");
        realRefreshTime.setText("100");
        createText("If simulation usues magic rule");
        Button boundedMapButton = new Button("Bounded map");
        boundedMapButton.setOnAction(event -> {
            this.boundedMapIsMagical = true;
        });
        Button foldedMapButton = new Button("Folded map");
        foldedMapButton.setOnAction(event -> {
            this.foldedMapIsMagical = true;
        });
        HBox hBox = new HBox(20, boundedMapButton, foldedMapButton);
        hBox.setAlignment(Pos.BASELINE_CENTER);

        Button startButton = new Button("Start simulation");
        startButton.setOnAction((event) -> {
            try {
                showSimulation(Integer.valueOf(mapHeightTextField.getText()), Integer.valueOf(mapWidthTextField.getText()),
                        toTrueJungleRatio(Double.valueOf(jungleRatio.getText())), Integer.valueOf(grassEnergyBoostTextField.getText()),
                        Integer.valueOf(animalStartingEnergyTextField.getText()), Integer.valueOf(dailyEnergyCostTextField.getText()),
                        Integer.valueOf(animalsSpawningAtTheStart.getText()), Integer.valueOf(realRefreshTime.getText()));
            } catch (NumberFormatException e) {
                System.out.println("You haven't filled the text fields correctly!");
            }
        });

        VBox vBox = new VBox();
        addElementsToVBox(vBox);
        vBox.getChildren().add(hBox);
        vBox.getChildren().add(startButton);
        vBox.setSpacing(20);
        vBox.setAlignment(Pos.BASELINE_CENTER);

        Scene scene = new Scene(vBox, 600, 700);
        primaryStage.setTitle("Symulation settings");
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

    public void showSimulation(int height, int width, double jungleRatio, int energyGivenByGrass, int stratingEnergy, int moveEnergy, int ammountOfAnimals, int moveDelay) {
        BoundedMap boundedMap = new BoundedMap(height, width, jungleRatio, energyGivenByGrass, stratingEnergy, moveEnergy);
        FoldedMap foldedMap = new FoldedMap(height, width, jungleRatio, energyGivenByGrass, stratingEnergy, moveEnergy);
        ISimulationEngine engine1;
        ISimulationEngine engine2;
        if (boundedMapIsMagical) engine1 = new MagicSimulationEngine(boundedMap, this, stratingEnergy, ammountOfAnimals, moveDelay, 0);
        else engine1 = new SimulationEngine(boundedMap, this, stratingEnergy, ammountOfAnimals, moveDelay, 0);
        if (foldedMapIsMagical) engine2 = new MagicSimulationEngine(foldedMap, this, stratingEnergy, ammountOfAnimals, moveDelay, 1);
        else engine2 = new SimulationEngine(foldedMap, this, stratingEnergy, ammountOfAnimals, moveDelay, 1);


        Thread engineThread1 = new Thread((Runnable) engine1);
        engineThread1.start();
        Thread engineThread2 = new Thread((Runnable) engine2);
        engineThread2.start();

        VBox generalSimulationBox1 = new VBox();
        VBox generalSimulationBox2 = new VBox();
        setUpEverythingExceptCharts(generalSimulationBox1, 0, boundedMap);
        setUpEverythingExceptCharts(generalSimulationBox2, 1, foldedMap);
        setUpCharts(generalSimulationBox1, generalSimulationBox2);
        HBox generalBox = new HBox(generalSimulationBox1, generalSimulationBox2);
        Scene simulationScene = new Scene(generalBox, 1000, 1000);
        Stage simulationStage = new Stage();
        simulationStage.setTitle("Simulation");
        simulationStage.setScene(simulationScene);
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        simulationStage.setX(bounds.getMinX());
        simulationStage.setY(bounds.getMinY());
        simulationStage.setWidth(bounds.getWidth());
        simulationStage.setHeight(bounds.getHeight());
        simulationStage.show();
        simulationStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                engine1.stop();
                engine2.stop();
            }
        });
    }

    public void renderGui(AbstractWorldMap map, int simulationId) {
        numberOfAnimalsSeries[simulationId].getData().add(new XYChart.Data(map.getMapEra(), map.getNumberOfAnimals()));
        numberOfGrassesSeries[simulationId].getData().add(new XYChart.Data(map.getMapEra(), map.getNumberOfGrasses()));
        averageEnergySeries[simulationId].getData().add(new XYChart.Data(map.getMapEra(), map.getAverageEnergy()));
        averageChildrenCount[simulationId].getData().add(new XYChart.Data(map.getMapEra(), map.getAverageChildrenCount()));
        averageLifeSpan[simulationId].getData().add(new XYChart.Data(map.getMapEra(), map.getAverageLifeSpan()));
        writeToStatistics(map, simulationId);
        if (map.getDominantGenotypes().size() != map.getNumberOfAnimals()) updateDominants(map.getDominantGenotypes(), simulationId);
        updateAnimalTrackerVBox(map, simulationId);
        gridPane[simulationId].setGridLinesVisible(false);
        gridPane[simulationId].getColumnConstraints().clear();
        gridPane[simulationId].getRowConstraints().clear();
        gridPane[simulationId].getChildren().clear();
        gridPane[simulationId].setGridLinesVisible(true);
        Vector2d[] constraints = map.computeBounds();
        int maxX = constraints[1].x;
        int maxY = constraints[1].y;
        for (int x = 0; x <= maxX; x++) {
            for (int y = 0; y <= maxY; y++) {
                Node singleCell = createCell(x, y, map);
                gridPane[simulationId].add(singleCell, x, y, 1, 1);
            }
        }
    }

    public Node createCell(int x, int y, AbstractWorldMap map) {
        Vector2d position = new Vector2d(x, y);
        Object objectAtPosition = map.objectAt(position);
        Button button = new Button();
        if (objectAtPosition instanceof Animal) {
            button.setStyle("-fx-background-color: " + ((Animal) objectAtPosition).getEnergyColor());
            button.setOnAction(event -> {
                map.setCurrentTracker(new AnimalTracker((Animal) objectAtPosition));
            });
        }
        else if (objectAtPosition instanceof Grass) {
            button.setStyle("-fx-background-color: #7cfc00");
        }
        else {
            if (position.follows(map.lowerLeftJungleCorner) && position.precedes(map.higherRightJungleCorner)) {
                button.setStyle("-fx-background-color: #008000");
            }
            else {
                button.setStyle("-fx-background-color: #F5DEB3");
            }
        }
        button.setMinWidth(1);
        button.setMaxWidth(15);
        button.setPrefWidth(600 / map.higherRightSavannaCorner.x);

        button.setMinHeight(1);
        button.setMaxHeight(15);
        button.setPrefHeight(600 / map.higherRightSavannaCorner.y);
        return button;
    }



    public void setUpCharts(VBox vBox1, VBox vBox2) {
        wipeChart();
        final NumberAxis xAxis1 = new NumberAxis();
        final NumberAxis yAxis1 = new NumberAxis();
        xAxis1.setLabel("Era");
        LineChart<Number,Number> lineChart1 = new LineChart<Number,Number>(xAxis1,yAxis1);
        numberOfAnimalsSeries[0].setName("Number of animals");
        lineChart1.getData().add(numberOfAnimalsSeries[0]);
        numberOfGrassesSeries[0].setName("Number of grasses");
        lineChart1.getData().add(numberOfGrassesSeries[0]);
        averageEnergySeries[0].setName("Average energy");
        lineChart1.getData().add(averageEnergySeries[0]);
        averageChildrenCount[0].setName("Average children counter");
        lineChart1.getData().add(averageChildrenCount[0]);
        averageLifeSpan[0].setName("Average children counter");
        lineChart1.getData().add(averageLifeSpan[0]);
        lineChart1.setCreateSymbols(false);
        final NumberAxis xAxis2 = new NumberAxis();
        final NumberAxis yAxis2 = new NumberAxis();
        xAxis2.setLabel("Era");
        LineChart<Number,Number> lineChart2 = new LineChart<Number,Number>(xAxis2,yAxis2);
        numberOfAnimalsSeries[1].setName("Number of animals");
        lineChart2.getData().add(numberOfAnimalsSeries[1]);
        numberOfGrassesSeries[1].setName("Number of grasses");
        lineChart2.getData().add(numberOfGrassesSeries[1]);
        averageEnergySeries[1].setName("Average energy");
        lineChart2.getData().add(averageEnergySeries[1]);
        averageChildrenCount[1].setName("Average children counter");
        lineChart2.getData().add(averageChildrenCount[1]);
        averageLifeSpan[1].setName("Average children counter");
        lineChart2.getData().add(averageLifeSpan[1]);
        lineChart2.setCreateSymbols(false);
        vBox1.getChildren().add(lineChart1);
        vBox2.getChildren().add(lineChart2);
    }

    public void wipeChart() {
        numberOfAnimalsSeries[0].getData().clear();
        numberOfGrassesSeries[0].getData().clear();
        averageEnergySeries[0].getData().clear();
        averageChildrenCount[0].getData().clear();
        averageLifeSpan[0].getData().clear();

        numberOfAnimalsSeries[1].getData().clear();
        numberOfGrassesSeries[1].getData().clear();
        averageEnergySeries[1].getData().clear();
        averageChildrenCount[1].getData().clear();
        averageLifeSpan[1].getData().clear();
    }

    void setUpButtons(VBox vBox, int simulationId, AbstractWorldMap map) {
        Button stopSimulation = new Button("Stop simulation");
        stopSimulation.setOnAction(event -> {
            if (!this.stopSimulation[simulationId]) {
                stopSimulation.setText("Start simulation");
                this.stopSimulation[simulationId] = true;
            }
            else    {
                stopSimulation.setText("Stop simulation");
                this.stopSimulation[simulationId] = false;
            }
        });
        Button writeToCSV = new Button("Write to CSV");
        writeToCSV.setOnAction(event -> {
            try {
                writeToCSVFile(simulationId);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        Button highlightAnimalsWithDominantGenotypeButton = new Button("Highlight Animals with dominant genotype");
        highlightAnimalsWithDominantGenotypeButton.setOnAction(event -> {
            highlightAnimalsWithDominantGenotype(map, simulationId);
        });
        VBox newVBox = new VBox(stopSimulation, writeToCSV, highlightAnimalsWithDominantGenotypeButton);
        vBox.getChildren().add(newVBox);
    }

    public void highlightAnimalsWithDominantGenotype(AbstractWorldMap map, int simulationId) {
        List<Genotype> dominantGenotypes = map.getDominantGenotypes();
        if (dominantGenotypes.size() == map.getNumberOfAnimals()) return;
        gridPane[simulationId].setGridLinesVisible(false);
        gridPane[simulationId].getColumnConstraints().clear();
        gridPane[simulationId].getRowConstraints().clear();
        gridPane[simulationId].getChildren().clear();
        gridPane[simulationId].setGridLinesVisible(true);
        Vector2d[] constraints = map.computeBounds();
        int maxX = constraints[1].x;
        int maxY = constraints[1].y;
        for (int x = 0; x <= maxX; x++) {
            for (int y = 0; y <= maxY; y++) {
                Node singleCell;
                if (isAnimalWithDominantGenotype(x, y, map, dominantGenotypes)) {
                    singleCell = createDominantAnimalButton(x, y, map);
                } else singleCell = createCell(x, y, map);
                gridPane[simulationId].add(singleCell, x, y, 1, 1);
            }
        }
    }

    public Node createDominantAnimalButton(int x, int y, AbstractWorldMap map) {
        Vector2d position = new Vector2d(x, y);
        Object objectAtPosition = map.objectAt(position);
        Button button = new Button();
        button.setStyle("-fx-background-color: #3377ff");
        button.setOnAction(event -> {
            map.setCurrentTracker(new AnimalTracker((Animal) objectAtPosition));
        });
        button.setMinWidth(1);
        button.setMaxWidth(15);
        button.setPrefWidth(600 / map.higherRightSavannaCorner.x);

        button.setMinHeight(1);
        button.setMaxHeight(15);
        button.setPrefHeight(600 / map.higherRightSavannaCorner.y);
        return button;
    }

    public boolean isAnimalWithDominantGenotype(int x,int y,AbstractWorldMap map, List<Genotype> dominantGenotypes) {
        Vector2d position = new Vector2d(x, y);
        Object objectAtPosition = map.objectAt(position);
        if (objectAtPosition instanceof Animal) {
            Genotype animalGenotype = ((Animal) objectAtPosition).genotype;
            for (Genotype dominantGenotype: dominantGenotypes) {
                if (dominantGenotype.equals(animalGenotype)) return true;
            }
        }
        return false;
    }

    public double toTrueJungleRatio(double jungleRatio) {
        return sqrt(jungleRatio / (1 + jungleRatio));
    }

    public void setUpEverythingExceptCharts(VBox generalBox, int simulationId, AbstractWorldMap map) {
        HBox hBox = new HBox();
        hBox.getChildren().add(gridPane[simulationId]);
        VBox vBox = new VBox();
        vBox.getChildren().add(genotypeDominantVBox[simulationId]);
        vBox.getChildren().add(selectedAnimalVBox[simulationId]);
        setUpButtons(vBox, simulationId, map);
        hBox.getChildren().add(vBox);
        generalBox.getChildren().add(hBox);
    }

    public void updateDominants(List<Genotype> genotypes, int simulationId) {
        genotypeDominantVBox[simulationId].getChildren().clear();
        genotypeDominantVBox[simulationId].getChildren().add(new Text("Dominants:"));
        for (Genotype genotype: genotypes) {
            genotypeDominantVBox[simulationId].getChildren().add(new Text(genotype.toString()));
        }
    }

    public boolean getStopSimulation(int simulationId) {
        return stopSimulation[simulationId];
    }

    public void updateAnimalTrackerVBox(AbstractWorldMap map, int simulationId) {
        if (map.getAnimalTracker() == null) return;

        AnimalTracker animalTracker = map.getAnimalTracker();
        selectedAnimalVBox[simulationId].getChildren().clear();
        selectedAnimalVBox[simulationId].getChildren().add(new Text("Animal"));
        selectedAnimalVBox[simulationId].getChildren().add(new Text("Genotype: " + animalTracker.getAnimal().genotype.toString()));
        selectedAnimalVBox[simulationId].getChildren().add(new Text("Children: " + String.valueOf(animalTracker.getTrackedChildrenNumber())));
        selectedAnimalVBox[simulationId].getChildren().add(new Text("Descendants: " + String.valueOf(animalTracker.getTrackedDescendantsNumber())));
        if (animalTracker.getDeathEra() != -1) {
            selectedAnimalVBox[simulationId].getChildren().add(new Text("Death era: " + String.valueOf(animalTracker.getDeathEra())));
        }
    }

    public void writeToStatistics(AbstractWorldMap map, int simulationId) {
        double[] eraStatistics = new double[5];
        eraStatistics[0] = map.getNumberOfAnimals();
        eraStatistics[1] = map.getNumberOfGrasses();
        eraStatistics[2] = map.getAverageEnergy();
        eraStatistics[3] = map.getAverageChildrenCount();
        eraStatistics[4] = map.getAverageLifeSpan();
        statistics.get(simulationId).add(eraStatistics);
    }

    public void writeToCSVFile(int simulationId) throws IOException {
        FileWriter csvFile = new FileWriter("statistics" + String.valueOf(simulationId) + ".csv" );
        BufferedWriter out = new BufferedWriter(csvFile);
        String header = "Number of animals, Number of grasses, Average energy, Average children count, Average life span\n";
        out.write(header);

        double[] dataSums = {0, 0, 0, 0, 0};
        for (double[] data: statistics.get(simulationId)) {
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
