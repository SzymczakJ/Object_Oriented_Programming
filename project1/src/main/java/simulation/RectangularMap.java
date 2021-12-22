package simulation;

import java.util.HashMap;
import java.util.Map;

public class RectangularMap extends AbstractWorldMap{

    public RectangularMap(int height, int width, float jungleRatio, int energyGivenByGrass) {
        super(height, width, jungleRatio, energyGivenByGrass);
    }

    @Override
    public Vector2d stepsOutOfBounds(Vector2d oldPosition, Vector2d newPosition) {
        return oldPosition;
    }
}
