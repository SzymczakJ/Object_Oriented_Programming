package agh.ics.oop;

import java.util.Objects;

public class Grass extends AbstractWorldMapElement{
    private Vector2d position;
    public Grass(int x, int y){
        position = new Vector2d(x, y);
    }

    public String toString() {
        return "*";
    }

    @Override
    public String getImage() {
        return "src/main/resources/grass.png";
    }

    public Vector2d getPosition() {return new Vector2d(this.position.x, this.position.y);}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grass grass = (Grass) o;
        return Objects.equals(position, grass.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position);
    }
}
