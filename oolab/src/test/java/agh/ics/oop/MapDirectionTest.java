package agh.ics.oop;

import org.junit.Test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class MapDirectionTest {

    @Test
    public void nextMethodTest() {
        assertEquals(MapDirection.WEST, MapDirection.SOUTH.next());
        assertEquals(MapDirection.NORTH, MapDirection.WEST.next());
        assertEquals(MapDirection.EAST, MapDirection.NORTH.next());
        assertEquals(MapDirection.SOUTH, MapDirection.EAST.next());
        assertNotEquals(MapDirection.NORTH, MapDirection.NORTH.next());
        assertNotEquals(MapDirection.NORTH, MapDirection.EAST.next());
        assertNotEquals(MapDirection.NORTH, MapDirection.SOUTH.next());
        assertNotEquals(MapDirection.EAST, MapDirection.EAST.next());
        assertNotEquals(MapDirection.EAST, MapDirection.SOUTH.next());
        assertNotEquals(MapDirection.EAST, MapDirection.WEST.next());
        assertNotEquals(MapDirection.SOUTH, MapDirection.NORTH.next());
        assertNotEquals(MapDirection.SOUTH, MapDirection.SOUTH.next());
        assertNotEquals(MapDirection.SOUTH, MapDirection.WEST.next());
        assertNotEquals(MapDirection.WEST, MapDirection.NORTH.next());
        assertNotEquals(MapDirection.WEST, MapDirection.EAST.next());
        assertNotEquals(MapDirection.WEST, MapDirection.WEST.next());
    }

    @Test
    public void previousMethodTest() {
        assertEquals(MapDirection.SOUTH, MapDirection.WEST.previous());
        assertEquals(MapDirection.WEST, MapDirection.NORTH.previous());
        assertEquals(MapDirection.NORTH, MapDirection.EAST.previous());
        assertEquals(MapDirection.EAST, MapDirection.SOUTH.previous());
        assertNotEquals(MapDirection.NORTH, MapDirection.NORTH.previous());
        assertNotEquals(MapDirection.NORTH, MapDirection.SOUTH.previous());
        assertNotEquals(MapDirection.NORTH, MapDirection.WEST.previous());
        assertNotEquals(MapDirection.EAST, MapDirection.NORTH.previous());
        assertNotEquals(MapDirection.EAST, MapDirection.EAST.previous());
        assertNotEquals(MapDirection.EAST, MapDirection.WEST.previous());
        assertNotEquals(MapDirection.SOUTH, MapDirection.NORTH.previous());
        assertNotEquals(MapDirection.SOUTH, MapDirection.EAST.previous());
        assertNotEquals(MapDirection.SOUTH, MapDirection.SOUTH.previous());
        assertNotEquals(MapDirection.WEST, MapDirection.EAST.previous());
        assertNotEquals(MapDirection.WEST, MapDirection.WEST.previous());
        assertNotEquals(MapDirection.WEST, MapDirection.SOUTH.previous());
    }
}
