package agh.ics.oop;

public class Grass {
    private Vector2d position;

    public Grass(int x, int y){
        position = new Vector2d(x, y);
    }

    public Vector2d getPosition() {
        return new Vector2d(this.position.x, this.position.y);
    }

    public String toString() {
        return "*";
    }
}
