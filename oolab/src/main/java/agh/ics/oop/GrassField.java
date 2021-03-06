package agh.ics.oop;

import java.util.*;

import static java.lang.Math.sqrt;

public class GrassField extends AbstractWorldMap implements IWorldMap, IPositionChangeObserver {
    private final int numberOfTufts;
    private Vector2d lowerLeftConstraint = new Vector2d(Integer.MIN_VALUE, Integer.MIN_VALUE);
    private Vector2d upperRightConstraint = new Vector2d(Integer.MAX_VALUE, Integer.MAX_VALUE);
    private List<Grass> tufts = new ArrayList<>();
    private final MapBoundary mapBoundary = new MapBoundary();

    public Vector2d[] computeBounds() {
        lowerLeftConstraint = mapBoundary.computeLowerLeft();
        upperRightConstraint = mapBoundary.computeUpperRightBoundary();
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
                mapBoundary.place(new Vector2d(xCoordinate, yCoordinate));
                i++;
            }
        }
    }

    @Override
    public boolean place(Animal animal) {
        super.place(animal);
        mapBoundary.place(animal.getPosition());
        animal.addObserver(mapBoundary);
        return true;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
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

    @Override
    public Vector2d[] getAllPositions() {
        Vector2d[] res2 = super.getAllPositions();
        Vector2d[] res1 = new Vector2d[tufts.size() + res2.length];
        for (int i = 0; i < tufts.size(); i++) {
            res1[i] = tufts.get(i).getPosition();
        }
        for (int i = 0; i < res2.length; i++) {
            res1[tufts.size() + i] = res2[i];
        }
        return res1;
    }
}
