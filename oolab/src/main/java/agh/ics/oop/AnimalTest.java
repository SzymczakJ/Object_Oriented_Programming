package agh.ics.oop;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnimalTest {
    @Test
    public void animalOrientationTest() {
        Animal familiar = new Animal();
        assertEquals("pozycja: (2,2), orientacja: Północ", familiar.toString());
        familiar.move(MoveDirection.RIGHT);
        assertEquals("pozycja: (2,2), orientacja: Wschód", familiar.toString());
        familiar.move(MoveDirection.RIGHT);
        assertEquals("pozycja: (2,2), orientacja: Południe", familiar.toString());
        familiar.move(MoveDirection.RIGHT);
        assertEquals("pozycja: (2,2), orientacja: Zachód", familiar.toString());
        familiar.move(MoveDirection.RIGHT);
        assertEquals("pozycja: (2,2), orientacja: Północ", familiar.toString());
        familiar.move(MoveDirection.LEFT);
        assertEquals("pozycja: (2,2), orientacja: Zachód", familiar.toString());
        familiar.move(MoveDirection.LEFT);
        assertEquals("pozycja: (2,2), orientacja: Południe", familiar.toString());
        familiar.move(MoveDirection.RIGHT);
        assertEquals("pozycja: (2,2), orientacja: Zachód", familiar.toString());
        familiar.move(MoveDirection.LEFT);
        assertEquals("pozycja: (2,2), orientacja: Południe", familiar.toString());
        familiar.move(MoveDirection.LEFT);
        assertEquals("pozycja: (2,2), orientacja: Wschód", familiar.toString());
        familiar.move(MoveDirection.LEFT);
        assertEquals("pozycja: (2,2), orientacja: Północ", familiar.toString());
    }

    @Test
    public void animalMovementTest() {
        Animal familiar = new Animal();
        assertEquals("pozycja: (2,2), orientacja: Północ", familiar.toString());
        familiar.move(MoveDirection.RIGHT);
        familiar.move(MoveDirection.FORWARD);
        assertEquals("pozycja: (3,2), orientacja: Wschód", familiar.toString());
        familiar.move(MoveDirection.FORWARD);
        assertEquals("pozycja: (4,2), orientacja: Wschód", familiar.toString());
        familiar.move(MoveDirection.LEFT);
        familiar.move(MoveDirection.FORWARD);
        assertEquals("pozycja: (4,3), orientacja: Północ", familiar.toString());
        familiar.move(MoveDirection.FORWARD);
        assertEquals("pozycja: (4,4), orientacja: Północ", familiar.toString());
        familiar.move(MoveDirection.LEFT);
        familiar.move(MoveDirection.FORWARD);
        assertEquals("pozycja: (3,4), orientacja: Zachód", familiar.toString());
        familiar.move(MoveDirection.FORWARD);
        assertEquals("pozycja: (2,4), orientacja: Zachód", familiar.toString());
        familiar.move(MoveDirection.LEFT);
        familiar.move(MoveDirection.FORWARD);
        assertEquals("pozycja: (2,3), orientacja: Południe", familiar.toString());
        familiar.move(MoveDirection.FORWARD);
        assertEquals("pozycja: (2,2), orientacja: Południe", familiar.toString());
        familiar.move(MoveDirection.FORWARD);
        assertEquals("pozycja: (2,1), orientacja: Południe", familiar.toString());
        familiar.move(MoveDirection.FORWARD);
        assertEquals("pozycja: (2,0), orientacja: Południe", familiar.toString());
        familiar.move(MoveDirection.RIGHT);
        familiar.move(MoveDirection.FORWARD);
        assertEquals("pozycja: (1,0), orientacja: Zachód", familiar.toString());
        familiar.move(MoveDirection.FORWARD);
        assertEquals("pozycja: (0,0), orientacja: Zachód", familiar.toString());
        familiar.move(MoveDirection.RIGHT);
        familiar.move(MoveDirection.FORWARD);
        assertEquals("pozycja: (0,1), orientacja: Północ", familiar.toString());
        familiar.move(MoveDirection.FORWARD);
        assertEquals("pozycja: (0,2), orientacja: Północ", familiar.toString());
        familiar.move(MoveDirection.RIGHT);
        familiar.move(MoveDirection.FORWARD);
        assertEquals("pozycja: (1,2), orientacja: Wschód", familiar.toString());
        familiar.move(MoveDirection.FORWARD);
        assertEquals("pozycja: (2,2), orientacja: Wschód", familiar.toString());
    }

    @Test
    public void mapConstraintsTest() {
        Animal familiar = new Animal();
        assertEquals("pozycja: (2,2), orientacja: Północ", familiar.toString());
        familiar.move(MoveDirection.RIGHT);
        familiar.move(MoveDirection.FORWARD);
        familiar.move(MoveDirection.FORWARD);
        familiar.move(MoveDirection.FORWARD);
        familiar.move(MoveDirection.FORWARD);
        assertEquals("pozycja: (4,2), orientacja: Wschód", familiar.toString());
        familiar.move(MoveDirection.LEFT);
        familiar.move(MoveDirection.FORWARD);
        familiar.move(MoveDirection.FORWARD);
        familiar.move(MoveDirection.FORWARD);
        familiar.move(MoveDirection.FORWARD);
        assertEquals("pozycja: (4,4), orientacja: Północ", familiar.toString());
        familiar.move(MoveDirection.LEFT);
        familiar.move(MoveDirection.FORWARD);
        familiar.move(MoveDirection.FORWARD);
        familiar.move(MoveDirection.FORWARD);
        familiar.move(MoveDirection.FORWARD);
        familiar.move(MoveDirection.FORWARD);
        familiar.move(MoveDirection.FORWARD);
        familiar.move(MoveDirection.FORWARD);
        assertEquals("pozycja: (0,4), orientacja: Zachód", familiar.toString());
        familiar.move(MoveDirection.LEFT);
        familiar.move(MoveDirection.FORWARD);
        familiar.move(MoveDirection.FORWARD);
        familiar.move(MoveDirection.FORWARD);
        familiar.move(MoveDirection.FORWARD);
        familiar.move(MoveDirection.FORWARD);
        familiar.move(MoveDirection.FORWARD);
        familiar.move(MoveDirection.FORWARD);
        assertEquals("pozycja: (0,0), orientacja: Południe", familiar.toString());
        familiar.move(MoveDirection.RIGHT);
        familiar.move(MoveDirection.FORWARD);
        assertEquals("pozycja: (0,0), orientacja: Zachód", familiar.toString());
    }
}
