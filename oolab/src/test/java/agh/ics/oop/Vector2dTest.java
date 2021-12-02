package agh.ics.oop;

import org.junit.Assert;
import org.junit.Test;

import java.util.Vector;

import static org.junit.Assert.*;

public class Vector2dTest {
    Vector2d vector1 = new Vector2d(1, 2);
    Vector2d vector2 = new Vector2d(1, 2);
    Vector2d vector3 = new Vector2d(3, 6);
    Vector2d vector4 = new Vector2d(3, 6);
    Vector2d vector5 = new Vector2d(10000, 1000);
    Vector2d vector6 = new Vector2d(10000, 1000);
    Vector2d vector7 = new Vector2d(2, 1);

    @Test
    public void equalsMethodTest() {
        assertTrue(vector1.equals(vector2));
        assertTrue(vector2.equals(vector1));
        assertTrue(vector3.equals(vector4));
        assertTrue(vector4.equals(vector3));
        assertTrue(vector5.equals(vector6));
        assertTrue(vector6.equals(vector5));
        assertTrue(vector1.equals(vector1));
        assertFalse(vector1.equals(vector6));
        assertFalse(vector1.equals(vector4));
        assertFalse(vector1.equals(vector3));
        assertFalse(vector6.equals(vector2));
        assertFalse(vector4.equals(vector1));
        assertFalse(vector3.equals(vector6));
    }

    @Test
    public void toStringMethodTest() {
        assertEquals("(1,2)", vector1.toString());
        assertEquals("(1,2)", vector2.toString());
        assertEquals("(3,6)", vector3.toString());
        assertEquals("(10000,1000)", vector5.toString());
        assertNotEquals("(2,3)", vector1.toString());
        assertNotEquals("1000, 10000", vector6.toString());
        assertNotEquals("(1,1)", vector1.toString());
    }

    @Test
    public void precedesMethodTest() {
        assertTrue(vector1.precedes(vector2));
        assertTrue(vector1.precedes(vector3));
        assertTrue(vector1.precedes(vector6));
        assertTrue(vector3.precedes(vector5));
        assertFalse(vector3.precedes(vector1));
        assertFalse(vector6.precedes(vector2));
        assertFalse(vector6.precedes(vector3));
        assertFalse(vector4.precedes(vector2));
    }

    @Test
    public void followsMethodTest() {
        assertTrue(vector2.follows(vector1));
        assertTrue(vector3.follows(vector2));
        assertTrue(vector4.follows(vector3));
        assertTrue(vector5.follows(vector3));
        assertFalse(vector1.follows(vector3));
        assertFalse(vector1.follows(vector6));
        assertFalse(vector4.follows(vector6));
        assertFalse(vector3.follows(vector5));
    }

    @Test
    public void upperRightMethodTest() {
        assertEquals(new Vector2d(1, 2) ,vector1.upperRight(vector2));
        assertEquals(new Vector2d(2, 2), vector1.upperRight(vector7));
        assertEquals(vector3, vector1.upperRight(vector3));
        assertEquals(vector5, vector1.upperRight(vector6));
        assertNotEquals(vector1, vector2.upperRight(vector7));
        assertNotEquals(vector1, vector1.upperRight(vector6));
        assertNotEquals(vector3, vector4.upperRight(vector6));
        assertNotEquals(vector7, vector6.upperRight(vector1));
    }
    @Test
    public void lowerLeftMethodTest() {
        assertEquals(new Vector2d(1, 2) ,vector1.lowerLeft(vector2));
        assertEquals(new Vector2d(1, 1), vector1.lowerLeft(vector7));
        assertEquals(vector1, vector3.lowerLeft(vector1));
        assertEquals(vector1, vector1.lowerLeft(vector6));
        assertNotEquals(vector7, vector2.lowerLeft(vector7));
        assertNotEquals(vector6, vector1.lowerLeft(vector6));
        assertNotEquals(vector6, vector4.lowerLeft(vector6));
        assertNotEquals(vector7, vector6.lowerLeft(vector1));
    }

    @Test
    public void addMethodTest() {
        assertEquals(new Vector2d(2, 4), vector1.add(vector2));
        assertEquals(new Vector2d(4, 8), vector2.add(vector3));
        assertEquals(new Vector2d(20000, 2000), vector5.add(vector6));
        assertEquals(new Vector2d(10003, 1006), vector3.add(vector5));
        assertNotEquals(new Vector2d(763, 123), vector2.add(vector3));
        assertNotEquals(new Vector2d(763, 123), vector6.add(vector5));
        assertNotEquals(new Vector2d(763, 123), vector4.add(vector2));
        assertNotEquals(new Vector2d(763, 123), vector6.add(vector3));
    }

    @Test
    public void substractMethodTest() {
        assertEquals(new Vector2d(0, 0), vector1.substract(vector2));
        assertEquals(new Vector2d(-2, -4), vector2.substract(vector3));
        assertEquals(new Vector2d(0, 0), vector5.substract(vector6));
        assertEquals(new Vector2d(-9997, -994), vector3.substract(vector5));
        assertNotEquals(new Vector2d(763, 123), vector2.substract(vector3));
        assertNotEquals(new Vector2d(763, 123), vector6.substract(vector5));
        assertNotEquals(new Vector2d(763, 123), vector4.substract(vector2));
        assertNotEquals(new Vector2d(763, 123), vector6.substract(vector3));
    }

    @Test
    public void oppositeMethodTest() {
        assertEquals(new Vector2d(-1, -2), vector1.opposite());
        assertEquals(new Vector2d(-3, -6), vector3.opposite());
        assertEquals(new Vector2d(-10000, -1000), vector6.opposite());
        assertEquals(new Vector2d(-2, -1), vector7.opposite());
        assertNotEquals(new Vector2d(1, 2), vector1.opposite());
        assertNotEquals(new Vector2d(1, 2), vector3.opposite());
        assertNotEquals(new Vector2d(3, 6), vector4.opposite());
        assertNotEquals(new Vector2d(100, 100), vector6.opposite());
    }
}
