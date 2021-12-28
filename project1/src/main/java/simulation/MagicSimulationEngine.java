package simulation;

import javafx.application.Platform;
import simulation.gui.App;
import simulation.maps.AbstractWorldMap;

public class MagicSimulationEngine implements Runnable, ISimulationEngine {
    private final AbstractWorldMap map;
    private final App simulationObserver;
    private final int moveDelay;
    private int magicHelpCounter = 0;
    private final int simulationId;
    private boolean exit = false;

    public MagicSimulationEngine(AbstractWorldMap map, App simulationObserver, int startingEnergy, int ammountOfAnimals, int moveDelay, int simulationId) {
        this.map = map;
        this.simulationObserver = simulationObserver;
        this.moveDelay = moveDelay;
        map.initializeMapWithAnimals(ammountOfAnimals, startingEnergy);
        this.simulationId = simulationId;
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
            while (simulationObserver.getStopSimulation(simulationId)) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    System.out.println("zakończono działanie");
                }
            }
            map.moveAllAnimals();
            map.deleteDeadAnimals();
            map.allAnimalsGrazeOnGrass();
            map.allAnimalsMakeLove();
            map.growGrassOnJungle();
            map.growGrassOnSavanna();
            if (magicHelpCounter < 3 && map.getNumberOfAnimals() <= 5) {
                magicHelpCounter += 1;
                map.magicEvolutionHelper();
            }
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
