package agh.ics.oop;

import java.util.Map;

public class Animal {
    private MapDirection orientation = MapDirection.NORTH;
    private Vector2d position = new Vector2d(2, 2);

    public String toString() {
        return "pozycja: " + position.toString() + ", orientacja: " + orientation.toString();
    }

    public boolean isAt(Vector2d position) {
        return this.position.equals(position);
    }

    public void move(MoveDirection direction) {
        switch (direction) {
            case RIGHT:
                this.orientation = this.orientation.next();
                break;
            case LEFT:
                this.orientation = this.orientation.previous();
                break;
            case FORWARD:
                this.position = this.position.add(this.orientation.toUnitVector());
                break;
            case BACKWARD:
                this.position = this.position.add(this.orientation.toUnitVector().opposite());
                break;
        }
        if (!this.position.follows(new Vector2d(0, 0)) || !this.position.precedes(new Vector2d(4, 4))) {
            switch (direction) {
                case FORWARD -> this.position = this.position.substract(this.orientation.toUnitVector());
                case BACKWARD -> this.position = this.position.substract(this.orientation.toUnitVector().opposite());
            }
        }
    }
}
