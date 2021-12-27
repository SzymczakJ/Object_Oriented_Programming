package simulation.maps;

import simulation.Vector2d;
import simulation.maps.AbstractWorldMap;

public class RectangularMap extends AbstractWorldMap {

    public RectangularMap(int height, int width, float jungleRatio, int energyGivenByGrass, int startingEnergy, int moveEnergy) {
        super(height, width, jungleRatio, energyGivenByGrass, startingEnergy, moveEnergy);
    }

    @Override
    public Vector2d stepsOutOfBounds(Vector2d oldPosition, Vector2d newPosition) {
        return oldPosition;
    }
}
