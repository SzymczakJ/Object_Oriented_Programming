package agh.ics.oop;


import java.util.ArrayList;
import java.util.List;

public abstract class AbstractWorldMap implements IWorldMap{
    protected List<Animal> animals = new ArrayList<Animal>();
    private MapVisualizer mapVisualizer = new MapVisualizer(this);

    public abstract boolean canMoveTo(Vector2d position);
    public abstract boolean isOccupied(Vector2d position);
    public abstract Object objectAt(Vector2d position);
    public abstract Vector2d[] computeBounds();

    public boolean place(Animal animal) {
        if (this.canMoveTo(animal.getPosition())) {
            animals.add(animal);
            return true;
        }
        else return false;
    }

    public String toString() {
        Vector2d[] bounds = this.computeBounds();
        return mapVisualizer.draw(bounds[0], bounds[1]);
    }
}
