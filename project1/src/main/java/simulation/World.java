package simulation;

import javafx.application.Application;
import simulation.gui.App;

public class World {

    public static void main(String[] args) {
//        Application.launch(App.class, args);
        try {
            RectangularMap rectangularMap = new RectangularMap(30, 30, (float) 0.34, 1000);
            Vector2d startingPosition = new Vector2d(1, 0);
            Animal animal1 = new Animal(rectangularMap, startingPosition, 50, MapDirection.NORTH);
            rectangularMap.place(animal1);
            System.out.println(animal1.isAt(startingPosition));
            System.out.println(animal1.getOrientation());
            rectangularMap.createAndPlaceGrass(new Vector2d(1, 1));
            animal1.move(MoveDirection.MOVEFORWARD);
            Animal animal2 = new Animal(rectangularMap, new Vector2d(1, 1), 50, MapDirection.NORTH);
            rectangularMap.place(animal2);
            System.out.println(animal1.getPosition());
            rectangularMap.growGrassOnJungle();
            rectangularMap.growGrassOnSavanna();
            rectangularMap.printGrasses();
            rectangularMap.grazeOnGrass(new Vector2d(1, 1));
            rectangularMap.printGrasses();
            System.out.println(animal1.getEnergy());
        } catch (IllegalArgumentException e) {
            System.out.println("dupsko");
        }
    }

}
