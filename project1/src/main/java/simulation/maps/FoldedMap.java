package simulation.maps;

import simulation.Vector2d;
import simulation.maps.AbstractWorldMap;

public class FoldedMap extends AbstractWorldMap {
//    private Map<Vector2d, Map<Animal, Animal>> animals = new HashMap<>();
//    private final Vector2d lowerLeftSavannaCorner;
//    private final Vector2d higherRightSavannaCorner;
//    private final Vector2d lowerLeftJungleCorner;
//    private final Vector2d higherRightJungleCorner;


    public FoldedMap(int height, int width, double jungleRatio, int energyGivenByGrass, int startingEnergy, int moveEnergy) {
        super(height, width, jungleRatio, energyGivenByGrass, startingEnergy, moveEnergy);
//        lowerLeftSavannaCorner = new Vector2d(0, 0);
//        higherRightSavannaCorner = new Vector2d(width, height);
//        lowerLeftJungleCorner = new Vector2d((int) (width * (1 - jungleRatio) / 2), (int) (height * (1 - jungleRatio) / 2));
//        higherRightJungleCorner = new Vector2d((int) (width * (1 + jungleRatio) / 2), (int) (height * (1 + jungleRatio) / 2));
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
