package agh.ics.oop;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SimulationEngineTest {
    @Test
    public void movementTest() {
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
}