package simulation;

import java.util.Objects;

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

    public Vector2d add(Vector2d other) {
        return new Vector2d(this.x + other.x, this.y + other.y);
    }

    public boolean equals(Object other) {
        if (other instanceof Vector2d vector) {
            return vector.x == this.x & vector.y == this.y;
        } else return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}