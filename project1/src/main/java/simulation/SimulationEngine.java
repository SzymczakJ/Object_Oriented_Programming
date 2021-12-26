package simulation;

import javafx.application.Platform;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import simulation.gui.App;

public class SimulationEngine implements Runnable{
    private final AbstractWorldMap map;
    private final App simulationObserver;

    private final int moveDelay;

    public SimulationEngine(AbstractWorldMap map, App simulationObserver, int startingEnergy, int ammountOfAnimals, int moveDelay) {
        this.map = map;
        this.simulationObserver = simulationObserver;
        this.moveDelay = moveDelay;
        map.initializeMapWithAnimals(ammountOfAnimals, startingEnergy);

    }

    @Override
    public void run() {
        Platform.runLater(() -> {
            simulationObserver.renderMap(map);
        });
        try {
            Thread.sleep(moveDelay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (true) {
            map.moveAllAnimals();
            map.deleteDeadAnimals();
            map.allAnimalsGrazeOnGrass();
            map.allAnimalsMakeLove();
            map.growGrassOnJungle();
            map.growGrassOnSavanna();
            Platform.runLater(() -> {
                simulationObserver.renderMap((AbstractWorldMap) map);
            });
            try {
                Thread.sleep(moveDelay);
            } catch (InterruptedException e) {
                System.out.println("zakończono działanie");
            }
        }
    }
}
