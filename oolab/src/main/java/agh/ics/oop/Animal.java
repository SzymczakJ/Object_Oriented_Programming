package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Animal extends AbstractWorldMapElement{
    private MapDirection orientation = MapDirection.NORTH;
    private IWorldMap map;
    private List<IPositionChangeObserver> observers = new ArrayList<IPositionChangeObserver>();
    private Vector2d position;

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

    public Animal(IWorldMap map, Vector2d initialPosition, MapDirection direction) {
        this.map = map;
        this.orientation = direction;
        this.position = initialPosition;
    }

    public boolean equals(Object other) {
        if (other instanceof Animal) {
            Animal animal = (Animal) other;
            return animal.getPosition().equals(this.position) && this.orientation.equals(animal.orientation);
        }
        else return false;
    }

    public String toString() {
        return orientation.toString();
    }

    @Override
    public String getImage() {
        String res = "src/main/resources/";
        String additionToRes = switch (this.orientation) {
            case EAST -> "hedgehog_right.png";
            case WEST -> "hedgehog_left.png";
            case NORTH -> "hedgehog_front.png";
            case SOUTH -> "hedgehog_back.png";
        };
        return res + additionToRes;
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
            System.out.println(orientation.toString() + newOrientation.toString());
            this.position = newPosition;
        }
        this.orientation = newOrientation;
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

    public MapDirection getOrientation() {
        return this.orientation;
    }

    public Vector2d getPosition() {return new Vector2d(this.position.x, this.position.y);}


    @Override
    public int hashCode() {
        return Objects.hash(orientation, position);
    }
}
