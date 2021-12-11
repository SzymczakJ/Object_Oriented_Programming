package agh.ics.oop;

public class Grass extends AbstractWorldMapElement{
    public Grass(int x, int y){
        position = new Vector2d(x, y);
    }

    public String toString() {
        return "*";
    }

}
