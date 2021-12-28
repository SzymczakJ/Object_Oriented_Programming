package simulation;

import javafx.application.Platform;
import simulation.gui.App;
import simulation.maps.AbstractWorldMap;

public class SimulationEngine implements Runnable, ISimulationEngine{
    private final AbstractWorldMap map;
    private final App simulationObserver;
    private final int moveDelay;
    private final int simulationId;
    private boolean exit = false;

    public SimulationEngine(AbstractWorldMap map, App simulationObserver, int startingEnergy, int ammountOfAnimals, int moveDelay, int simulationId) {
        this.map = map;
        this.simulationObserver = simulationObserver;
        this.moveDelay = moveDelay;
        this.simulationId = simulationId;
        map.initializeMapWithAnimals(ammountOfAnimals, startingEnergy);
    }

    @Override
    public void run() {
        Platform.runLater(() -> {
            simulationObserver.renderGui(map, simulationId);
        });
        try {
            Thread.sleep(moveDelay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (!exit) {
            while (simulationObserver.getStopSimulation(simulationId)) { try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                System.out.println("zakończono działanie");
            }}
            map.moveAllAnimals();
            map.deleteDeadAnimals();
            map.allAnimalsGrazeOnGrass();
            map.allAnimalsMakeLove();
            map.growGrassOnJungle();
            map.growGrassOnSavanna();
            Platform.runLater(() -> {
                simulationObserver.renderGui(map, simulationId);
            });
            try {
                Thread.sleep(moveDelay);
            } catch (InterruptedException e) {
                System.out.println("zakończono działanie");
            }
        }
    }

    public void stop() {
        exit = true;
    }
}
