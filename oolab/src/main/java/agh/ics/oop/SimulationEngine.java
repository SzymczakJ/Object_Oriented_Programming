package agh.ics.oop;

import agh.ics.oop.gui.App;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.List;

public class SimulationEngine implements IEngine, Runnable {
    private final IWorldMap map;
    private final MoveDirection[] commands;
    private final Vector2d[] positions;
    private final List<Animal> animals = new ArrayList<>();
    private final App simulationObserver;
    int moveDelay;
    private final MapDirection orientations;

    public SimulationEngine(IWorldMap map, MoveDirection[] commands, Vector2d[] positions, App observer, int moveDelay, MapDirection orientations) {
        this.moveDelay = moveDelay;
        this.orientations = orientations;
        simulationObserver = observer;
        this.map = map;
        this.commands = commands;
        this.positions = positions;
        for (Vector2d position: positions) {
            Animal animal = new Animal(map, position, orientations);
            if (this.map.place(animal))  {
                animals.add(animal);
            }
        }
    }

    @Override
    public void run() {
        Platform.runLater(() -> {
            simulationObserver.renderMap((AbstractWorldMap) map);
        });
        try {
            Thread.sleep(moveDelay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int i = 0;
        while(i<commands.length){
            for (Animal animal : animals) {
                if(i<commands.length){
                    animal.move(commands[i]);
                    i++;
                }
                else break;
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
