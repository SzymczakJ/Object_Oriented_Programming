package simulation;

import javafx.application.Platform;
import simulation.gui.App;
import simulation.maps.AbstractWorldMap;

public class MagicSimulationEngine implements Runnable, ISimulationEngine {
    private final AbstractWorldMap map;
    private final App simulationObserver;
    private final int moveDelay;
    private int magicHelpCounter = 0;

    public MagicSimulationEngine(AbstractWorldMap map, App simulationObserver, int startingEnergy, int ammountOfAnimals, int moveDelay) {
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
            while (simulationObserver.getStopSimulation1()) { try {
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
            if (magicHelpCounter < 3 && map.getNumberOfAnimals() <= 5) {
                magicHelpCounter += 1;
                map.magicEvolutionHelper();
            }
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
