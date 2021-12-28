package simulation.maps;

import simulation.Vector2d;

public class BoundedMap extends AbstractWorldMap {

    public BoundedMap(int height, int width, double jungleRatio, int energyGivenByGrass, int startingEnergy, int moveEnergy) {
        super(height, width, jungleRatio, energyGivenByGrass, startingEnergy, moveEnergy);
    }

    @Override
    public Vector2d stepsOutOfBounds(Vector2d oldPosition, Vector2d newPosition) {
        return oldPosition;
    }
}
