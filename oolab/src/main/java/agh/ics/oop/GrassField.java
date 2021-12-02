package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import static java.lang.Math.sqrt;

public class GrassField extends AbstractWorldMap implements IWorldMap, IPositionChangeObserver {
    private final int numberOfTufts;
    private final Vector2d lowerLeftConstraint = new Vector2d(Integer.MIN_VALUE, Integer.MIN_VALUE);
    private final Vector2d upperRightConstraint = new Vector2d(Integer.MAX_VALUE, Integer.MAX_VALUE);
//    private List<Animal> animals = new ArrayList<>();
    private List<Grass> tufts = new ArrayList<>();

    public Vector2d[] computeBounds() {
        Vector2d lowerLeftConstraint = new Vector2d((int) ((int) 10 * sqrt(numberOfTufts) + 1), (int) ((int) 10 * sqrt(numberOfTufts) + 1));
        Vector2d upperRightConstraint = new Vector2d(0, 0);
        Vector2d newLowerLeftConstraint;
        Vector2d newUpperRightConstraint;
        for (Animal animal: animalsList){
            newLowerLeftConstraint = animal.getPosition().lowerLeft(lowerLeftConstraint);
            newUpperRightConstraint = animal.getPosition().upperRight(upperRightConstraint);
            if (newLowerLeftConstraint.precedes(lowerLeftConstraint)) lowerLeftConstraint = newLowerLeftConstraint;
            if (newUpperRightConstraint.follows(upperRightConstraint)) upperRightConstraint = newUpperRightConstraint;
        }
        for (Grass tuft: tufts){
            newLowerLeftConstraint = tuft.getPosition().lowerLeft(lowerLeftConstraint);
            newUpperRightConstraint = tuft.getPosition().upperRight(upperRightConstraint);
            if (newLowerLeftConstraint.precedes(lowerLeftConstraint)) lowerLeftConstraint = newLowerLeftConstraint;
            if (newUpperRightConstraint.follows(upperRightConstraint)) upperRightConstraint = newUpperRightConstraint;
        }
        return new Vector2d[] {lowerLeftConstraint, upperRightConstraint};
    }

    public GrassField(int numberOfTufts) {
        this.numberOfTufts = numberOfTufts;
        int i = 0;
        Random rand = new Random();
        int upperBound = (int) ((int) 10 * sqrt(numberOfTufts) + 1);
        int xCoordinate;
        int yCoordinate;
        while (i < numberOfTufts){
            xCoordinate = rand.nextInt(upperBound);
            yCoordinate = rand.nextInt(upperBound);
            if (!isOccupiedByGrass(new Vector2d(xCoordinate, yCoordinate))){
                Grass grass = new Grass(xCoordinate, yCoordinate);
                tufts.add(grass);
                i++;
            }
        }
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        if (!position.follows(lowerLeftConstraint) || !position.precedes(upperRightConstraint)) return false;
        if (!this.isOccupied(position)) return true;
        else if (this.objectAt(position) instanceof Grass) return true;
        else return false;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        if (super.isOccupied(position)) return true;
        for (Grass tuft: tufts) {
            if (tuft.getPosition().equals(position)) return true;
        }
        return false;
    }

    public boolean isOccupiedByGrass(Vector2d position) {
        for (Grass tuft: tufts) {
            if (tuft.getPosition().equals(position)) return true;
        }
        return false;
    }

    @Override
    public Object objectAt(Vector2d position) {
        if (super.objectAt(position) != null) {
            return super.objectAt(position);
        }
        for (Grass tuft: tufts) {
            if (tuft.getPosition().equals(position)) return tuft;
        }
        return null;
    }
}
