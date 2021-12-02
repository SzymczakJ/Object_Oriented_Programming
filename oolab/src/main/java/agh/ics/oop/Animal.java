package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Animal {
    private MapDirection orientation = MapDirection.NORTH;
    private Vector2d position;
    private IWorldMap map;
    private List<IPositionChangeObserver> observers = new ArrayList<IPositionChangeObserver>();

    public Animal() {
        position = new Vector2d(2, 2);
        map = new RectangularMap(5, 5);
    }

    public Animal(IWorldMap map) {
        position = new Vector2d(2, 2);
        this.map = map;
    }

    public Animal(IWorldMap map, Vector2d initialPosition) {
        this.map = map;
        this.position = initialPosition;
    }

    public String toString() {
        return orientation.toString();
    }

    public boolean isAt(Vector2d position) {
        return this.position.equals(position);
    }

    public void move(MoveDirection direction) {
        MapDirection newOrientation;
        Vector2d newPosition;
        switch (direction) {
            case RIGHT:
                newOrientation = this.orientation.next();
                newPosition = this.position;
                break;
            case LEFT:
                newOrientation = this.orientation.previous();
                newPosition = this.position;
                break;
            case FORWARD:
                newOrientation = this.orientation;
                newPosition = this.position.add(this.orientation.toUnitVector());
                break;
            case BACKWARD:
                newOrientation = this.orientation;
                newPosition = this.position.add(this.orientation.toUnitVector().opposite());
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + direction);
        }
        if (map.canMoveTo(newPosition)) {
            if (orientation.equals(newOrientation)) positionChanged(this.position, newPosition);
            this.orientation = newOrientation;
            this.position = newPosition;
        }
//        if (!this.position.follows(new Vector2d(0, 0)) || !this.position.precedes(new Vector2d(4, 4))) {
//            switch (direction) {
//                case FORWARD -> this.position = this.position.substract(this.orientation.toUnitVector());
//                case BACKWARD -> this.position = this.position.substract(this.orientation.toUnitVector().opposite());
//            }
//        }
    }

    public void addObserver(IPositionChangeObserver observer) {
        observers.add(observer);
    }

    void removeObserver(IPositionChangeObserver observer) {
        observers.remove(observer);
    }

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        for (IPositionChangeObserver observer: observers) {
            observer.positionChanged(oldPosition, newPosition);
        }
    }

    public Vector2d getPosition() {
        return new Vector2d(this.position.x, this.position.y);
    }
}
