package agh.ics.oop;

import org.junit.Test;

import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

public class SimulationEngineTest {
    @Test
    public void rectangularMapTest() {
        String[] arg = {"f", "b", "r", "l"};
        MoveDirection[] directions = new OptionsParser().parse(arg);
        IWorldMap map = new RectangularMap(10, 5);
        Vector2d[] positions = { new Vector2d(2,2), new Vector2d(3,4) };
        IEngine engine = new SimulationEngine(map, directions, positions);
        engine.run();
        assertTrue(map.isOccupied(new Vector2d(2, 3)));
        assertTrue(map.isOccupied(new Vector2d(3, 3)));
        assertFalse(map.isOccupied(new Vector2d(1, 1)));
    }

    @Test
    public void grassFieldTest() {
        String[] arg = {"f", "b", "r", "l"};
        MoveDirection[] directions = new OptionsParser().parse(arg);
        IWorldMap map = new GrassField(10);
        Vector2d[] positions = { new Vector2d(2,2), new Vector2d(3,4) };
        IEngine engine = new SimulationEngine(map, directions, positions);
        engine.run();
        Vector2d position1 = new Vector2d(2, 3);
        Vector2d position2 = new Vector2d(3, 3);
        assertTrue(map.isOccupied(position1));
        assertTrue(map.isOccupied(position2));
        assertTrue(map.objectAt(position1) instanceof Animal);
        assertTrue(map.objectAt(position2) instanceof Animal);
    }
}