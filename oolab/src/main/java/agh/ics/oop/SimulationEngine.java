package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class SimulationEngine implements IEngine {
    private final IWorldMap map;
    private final MoveDirection[] commands;
    private final Vector2d[] positions;
    private final List<Animal> animals = new ArrayList<>();;

    public SimulationEngine(IWorldMap map, MoveDirection[] commands, Vector2d[] positions) {
        this.map = map;
        this.commands = commands;
        this.positions = positions;
        for (Vector2d position: positions) {
            Animal animal = new Animal(map, position);
            if (this.map.place(animal))  {
                animals.add(animal);

            }
        }
    }

    @Override
    public void run() {
        int i = 0;
        while(i<commands.length){
            for (Animal animal : animals) {
                if(i<commands.length){
                    animal.move(commands[i]);
                    i++;
                }
                else break;
            }
        }
    }
}
