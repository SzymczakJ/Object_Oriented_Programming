package agh.ics.oop;

public class Vector2d {
    final public int x;
    final public int y;

    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "(" + x + "," + y + ")";
    }

    public boolean precedes(Vector2d other) {
        return other.x >= this.x & other.y >= this.y;
    }

    public boolean follows(Vector2d other) {
        return other.x <= this.x & other.y <= this.y;
    }

    public Vector2d upperRight(Vector2d other) {
        int x;
        int y;
        if (other.x > this.x) x = other.x;
        else x = this.x;

        if (other.y > this.y) y = other.y;
        else y = this.y;

        Vector2d upperRightVector = new Vector2d(x, y);
        return upperRightVector;
    }

    public Vector2d lowerLeft(Vector2d other) {
        int x;
        int y;
        if (other.x < this.x) x = other.x;
        else x = this.x;

        if (other.y < this.y) y = other.y;
        else y = this.y;

        Vector2d lowerLeftVector = new Vector2d(x, y);
        return lowerLeftVector;
    }

    public Vector2d add(Vector2d other) {
        return new Vector2d(this.x + other.x, this.y + other.y);
    }

    public Vector2d substract(Vector2d other) {
        return new Vector2d(this.x - other.x, this.y - other.y);
    }

    public boolean equals(Object other) {
        if (other instanceof Vector2d) {
            Vector2d vector = (Vector2d) other;
            return vector.x == this.x & vector.y == this.y;
        } else return false;
    }

    public Vector2d opposite() {
        return new Vector2d(-this.x, -this.y);
    }
}
