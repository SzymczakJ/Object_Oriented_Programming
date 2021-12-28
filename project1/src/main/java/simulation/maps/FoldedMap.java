package simulation.maps;

import simulation.Vector2d;
import simulation.maps.AbstractWorldMap;

public class FoldedMap extends AbstractWorldMap {

    public FoldedMap(int height, int width, double jungleRatio, int energyGivenByGrass, int startingEnergy, int moveEnergy) {
        super(height, width, jungleRatio, energyGivenByGrass, startingEnergy, moveEnergy);
    }

    @Override
    public Vector2d stepsOutOfBounds(Vector2d oldPosition, Vector2d newPosition) {
        int x;
        int y;
        if (newPosition.x > higherRightSavannaCorner.x) x = lowerLeftSavannaCorner.x;
        else if (newPosition.x < lowerLeftSavannaCorner.x) x = higherRightSavannaCorner.x;
        else x = newPosition.x;

        if (newPosition.y > higherRightSavannaCorner.y) y = lowerLeftSavannaCorner.y;
        else if (newPosition.y < lowerLeftSavannaCorner.y) y = higherRightSavannaCorner.y;
        else y = newPosition.y;

        return new Vector2d(x, y);
    }
}
