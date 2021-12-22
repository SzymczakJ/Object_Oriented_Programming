package simulation;

import java.util.Objects;
import java.util.Random;
import java.util.Vector;

public class Animal {
    private Vector2d position;
    private MapDirection orientation;
    private final AbstractWorldMap map;
    private final Genotype genotype = new Genotype();
    private int energy;
    private IPositionChangeObserver observer;

    public Animal(AbstractWorldMap map, Vector2d position, int energy) {
        this.map = map;
        this.position = position;
        this.energy = energy;
        observer = map;
        Random random = new Random();
        this.orientation = MapDirection.createMapDirectionFromNumber(random.nextInt(8));
    }

    public Animal(AbstractWorldMap map, Vector2d position, int energy, MapDirection orientation) {
        this.map = map;
        this.position = position;
        this.energy = energy;
        observer = map;
        this.orientation = orientation;
    }

    public Vector2d getPosition() {
        return new Vector2d(position.x, position.y);
    }

    public void move(MoveDirection direction) {
        Vector2d oldPosition = position;
        Vector2d newPosition;
        switch (direction) {
            case MOVEFORWARD -> {
                newPosition = this.position.add(this.orientation.toUnitVector());
                if (map.isInBounds(newPosition)) {
                    position = newPosition;
                } else {
                    position = map.stepsOutOfBounds(oldPosition, newPosition);
                }
                positionChanged(oldPosition);
            }
            case MOVEBACKWARD -> {
                orientation = orientation.turn180Degrees();
                newPosition = this.position.add(this.orientation.toUnitVector());
                if (map.isInBounds(newPosition)) {
                    position = newPosition;
                } else {
                    position = map.stepsOutOfBounds(oldPosition, newPosition);
                }
                positionChanged(oldPosition);
            }
            case TURN45 -> orientation = orientation.turn45Degrees();
            case TURN90 -> orientation = orientation.turn90Degrees();
            case TURN135 -> orientation = orientation.turn135Degrees();
            case TURN225 -> orientation = orientation.turn225Degrees();
            case TURN270 -> orientation = orientation.turn270Degrees();
            case TURN315 -> orientation = orientation.turn315Degrees();
        }
    }

    public void increaseEnergy(int energy) {
        this.energy += energy;
        if (this.energy > AbstractWorldMap.maxEnergy) {
            this.energy = AbstractWorldMap.maxEnergy;
        }
    }

    public void reduceEnergy(int energy) {
        this.energy -= energy;
    }

    public boolean isAt(Vector2d position) {
        return this.position.equals(position);
    }

    public void positionChanged(Vector2d oldPosition) {
        observer.positionChanged(oldPosition, this);
    }

    public MapDirection getOrientation() {
        return this.orientation;
    }

    public int getEnergy() {
        return energy;
    }
}
