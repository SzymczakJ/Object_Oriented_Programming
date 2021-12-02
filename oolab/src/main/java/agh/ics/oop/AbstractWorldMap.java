package agh.ics.oop;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver{
    protected List<Animal> animalsList = new ArrayList<Animal>();
    protected MapVisualizer mapVisualizer = new MapVisualizer(this);
    protected Map<Vector2d, Animal> animals = new HashMap<>();

    public abstract boolean canMoveTo(Vector2d position);
    public abstract Vector2d[] computeBounds();

    public boolean place(Animal animal) {
        if (this.canMoveTo(animal.getPosition())) {
            animals.put(animal.getPosition(), animal);
            animalsList.add(animal);
            animal.addObserver(this);
            return true;
        }
        else return false;
    }

    public boolean isOccupied(Vector2d position) {
        return animals.get(position) != null;
    }

    public Object objectAt(Vector2d position) {
        return animals.get(position);
    }

    public String toString() {
        Vector2d[] bounds = this.computeBounds();
        return mapVisualizer.draw(bounds[0], bounds[1]);
    }

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        Animal animal = animals.remove(oldPosition);
        animals.put(newPosition, animal);
    }

}
