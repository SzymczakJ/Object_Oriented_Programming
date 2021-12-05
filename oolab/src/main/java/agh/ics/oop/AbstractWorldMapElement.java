package agh.ics.oop;

public abstract class AbstractWorldMapElement {
    protected Vector2d position;

    public abstract String toString();

    public Vector2d getPosition() {return new Vector2d(this.position.x, this.position.y);}
}
