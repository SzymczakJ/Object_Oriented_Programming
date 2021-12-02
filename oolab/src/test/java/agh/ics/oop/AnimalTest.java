package agh.ics.oop;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AnimalTest {
    @Test
    public void animalOrientationTest() {
        Animal familiar = new Animal();
        assertEquals("^", familiar.toString());
        familiar.move(MoveDirection.RIGHT);
        assertEquals(">", familiar.toString());
        familiar.move(MoveDirection.RIGHT);
        assertEquals("v", familiar.toString());
        familiar.move(MoveDirection.RIGHT);
        assertEquals("<", familiar.toString());
        familiar.move(MoveDirection.RIGHT);
        assertEquals("^", familiar.toString());
        familiar.move(MoveDirection.LEFT);
        assertEquals("<", familiar.toString());
        familiar.move(MoveDirection.LEFT);
        assertEquals("v", familiar.toString());
        familiar.move(MoveDirection.RIGHT);
        assertEquals("<", familiar.toString());
        familiar.move(MoveDirection.LEFT);
        assertEquals("v", familiar.toString());
        familiar.move(MoveDirection.LEFT);
        assertEquals(">", familiar.toString());
        familiar.move(MoveDirection.LEFT);
        assertEquals("^", familiar.toString());
    }

    @Test
    public void animalMovementTest() {
        Animal familiar = new Animal();
        assertTrue(familiar.isAt(new Vector2d(2, 2)));
        familiar.move(MoveDirection.RIGHT);
        familiar.move(MoveDirection.FORWARD);
        assertTrue(familiar.isAt(new Vector2d(3, 2)));
        familiar.move(MoveDirection.FORWARD);
        assertTrue(familiar.isAt(new Vector2d(4, 2)));
        familiar.move(MoveDirection.LEFT);
        familiar.move(MoveDirection.FORWARD);
        assertTrue(familiar.isAt(new Vector2d(4, 3)));
        familiar.move(MoveDirection.FORWARD);
        assertTrue(familiar.isAt(new Vector2d(4, 4)));
        familiar.move(MoveDirection.LEFT);
        familiar.move(MoveDirection.FORWARD);
        assertTrue(familiar.isAt(new Vector2d(3, 4)));
        familiar.move(MoveDirection.FORWARD);
        assertTrue(familiar.isAt(new Vector2d(2, 4)));
        familiar.move(MoveDirection.LEFT);
        familiar.move(MoveDirection.FORWARD);
        assertTrue(familiar.isAt(new Vector2d(2, 3)));
        familiar.move(MoveDirection.FORWARD);
        assertTrue(familiar.isAt(new Vector2d(2, 2)));
        familiar.move(MoveDirection.FORWARD);
        assertTrue(familiar.isAt(new Vector2d(2, 1)));
        familiar.move(MoveDirection.FORWARD);
        assertTrue(familiar.isAt(new Vector2d(2, 0)));
        familiar.move(MoveDirection.RIGHT);
        familiar.move(MoveDirection.FORWARD);
        assertTrue(familiar.isAt(new Vector2d(1, 0)));
        familiar.move(MoveDirection.FORWARD);
        assertTrue(familiar.isAt(new Vector2d(0, 0)));
        familiar.move(MoveDirection.RIGHT);
        familiar.move(MoveDirection.FORWARD);
        assertTrue(familiar.isAt(new Vector2d(0, 1)));
        familiar.move(MoveDirection.FORWARD);
        assertTrue(familiar.isAt(new Vector2d(0, 2)));
        familiar.move(MoveDirection.RIGHT);
        familiar.move(MoveDirection.FORWARD);
        assertTrue(familiar.isAt(new Vector2d(1, 2)));
        familiar.move(MoveDirection.FORWARD);
        assertTrue(familiar.isAt(new Vector2d(2, 2)));
    }

    @Test
    public void mapConstraintsTest() {
        Animal familiar = new Animal();
        assertTrue(familiar.isAt(new Vector2d(2, 2)));
        familiar.move(MoveDirection.RIGHT);
        familiar.move(MoveDirection.FORWARD);
        familiar.move(MoveDirection.FORWARD);
        familiar.move(MoveDirection.FORWARD);
        familiar.move(MoveDirection.FORWARD);
        assertTrue(familiar.isAt(new Vector2d(4, 2)));
        familiar.move(MoveDirection.LEFT);
        familiar.move(MoveDirection.FORWARD);
        familiar.move(MoveDirection.FORWARD);
        familiar.move(MoveDirection.FORWARD);
        familiar.move(MoveDirection.FORWARD);
        assertTrue(familiar.isAt(new Vector2d(4, 4)));
        familiar.move(MoveDirection.LEFT);
        familiar.move(MoveDirection.FORWARD);
        familiar.move(MoveDirection.FORWARD);
        familiar.move(MoveDirection.FORWARD);
        familiar.move(MoveDirection.FORWARD);
        familiar.move(MoveDirection.FORWARD);
        familiar.move(MoveDirection.FORWARD);
        familiar.move(MoveDirection.FORWARD);
        assertTrue(familiar.isAt(new Vector2d(0, 4)));
        familiar.move(MoveDirection.LEFT);
        familiar.move(MoveDirection.FORWARD);
        familiar.move(MoveDirection.FORWARD);
        familiar.move(MoveDirection.FORWARD);
        familiar.move(MoveDirection.FORWARD);
        familiar.move(MoveDirection.FORWARD);
        familiar.move(MoveDirection.FORWARD);
        familiar.move(MoveDirection.FORWARD);
        assertTrue(familiar.isAt(new Vector2d(0, 0)));
        familiar.move(MoveDirection.RIGHT);
        familiar.move(MoveDirection.FORWARD);
        assertTrue(familiar.isAt(new Vector2d(0, 0)));
    }
}
