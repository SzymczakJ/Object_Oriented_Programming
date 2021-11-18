package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class RectangularMap implements IWorldMap {
    private final int height;
    private final int width;
    private final Vector2d  upperRightConstraint;
    private final Vector2d lowerLeftConstraint;
    private MapVisualizer mapVisualizer = new MapVisualizer(this);
    private List<Animal> animals = new ArrayList<>();

    public RectangularMap(int width, int height) {
        this.height = height - 1;
        this.width = width - 1;
        this.upperRightConstraint = new Vector2d(this.width, this.height);
        this.lowerLeftConstraint = new Vector2d(0, 0);
    }

    public String toString() {
        return mapVisualizer.draw(lowerLeftConstraint, upperRightConstraint);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        Vector2d upperRightConstraint = new Vector2d(this.width, this.height);
        Vector2d lowerLeftConstraint = new Vector2d(0, 0);
        return position.follows(lowerLeftConstraint) && position.precedes(upperRightConstraint) &&
                !isOccupied(position);
    }

    @Override
    public boolean place(Animal animal) {
        if (!this.isOccupied(animal.getPosition())) {
            animals.add(animal);
            return true;
        }
        else return false;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        for (Animal animal: animals) {
            if (animal.isAt(position)) return true;
        }
        return false;
    }

    @Override
    public Object objectAt(Vector2d position) {
        for (Animal animal: animals) {
            if (animal.isAt(position)) return animal;
        }
        return null;
    }
}
