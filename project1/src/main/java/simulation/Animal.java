package simulation;

import simulation.maps.AbstractWorldMap;

import java.util.Random;

public class Animal {
    private Vector2d position;
    private MapDirection orientation;
    private final AbstractWorldMap map;
    public final Genotype genotype;
    private int energy;
    private IPositionChangeObserver observer;
    private Random random = new Random();
    private int childrenCounter = 0;
    private int birthEra;
    private int deathEra = -1;
    private AnimalTracker animalTracker = null;

    public Animal(AbstractWorldMap map, Vector2d position, int energy, int birthEra) {
        this.map = map;
        this.position = position;
        this.energy = energy;
        observer = map;
        this.orientation = MapDirection.createMapDirectionFromNumber(random.nextInt(8));
        genotype = new Genotype();
        this.birthEra = birthEra;
    }

    public Animal(AbstractWorldMap map, Vector2d position, int energy, int birthEra, Genotype genotype) {
        this.map = map;
        this.position = position;
        this.energy = energy;
        observer = map;
        this.orientation = MapDirection.createMapDirectionFromNumber(random.nextInt(8));
        this.birthEra = birthEra;
        this.genotype = genotype;
    }

    public Animal(AbstractWorldMap map, Vector2d position, int energy, MapDirection orientation, int birthEra) {
        this.map = map;
        this.position = position;
        this.energy = energy;
        observer = map;
        this.orientation = orientation;
        genotype = new Genotype();
        this.birthEra = birthEra;
    }

    public Animal(AbstractWorldMap map, Vector2d position, Animal animal1, Animal animal2, int birthEra) {
        this.map = map;
        this.position = position;
        observer = map;
        this.orientation = MapDirection.createMapDirectionFromNumber(random.nextInt(8));

        int animal1Energy = animal1.getEnergy();
        int animal2Energy = animal2.getEnergy();
        energy = (int) ((animal1Energy + animal2Energy) / 4);
        animal1.reduceEnergy((int) (animal1Energy / 4));
        animal2.reduceEnergy((int) (animal2Energy / 4));

        int animal1PartOfGenes = animal1Energy / (animal1Energy + animal2Energy);
        int i = 0;
        int[] genes = new int[32];
        while (i < animal1PartOfGenes) {
            genes[i] = animal1.genotype.genes[i];
            i++;
        }
        while (i < 32) {
            genes[i] = animal2.genotype.genes[i];
            i++;
        }
        genotype = new Genotype(genes);
        this.birthEra = birthEra;
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

    public void moveRandomly() {
        int x = random.nextInt(32);
        MoveDirection moveDirection = this.createMoveDirection(genotype.genes[x]);
        move(moveDirection);
    }

    public MoveDirection createMoveDirection(int i) {
        return switch (i) {
            case 0 -> MoveDirection.MOVEFORWARD;
            case 1 -> MoveDirection.TURN45;
            case 2 -> MoveDirection.TURN90;
            case 3 -> MoveDirection.TURN135;
            case 4 -> MoveDirection.MOVEBACKWARD;
            case 5 -> MoveDirection.TURN225;
            case 6 -> MoveDirection.TURN270;
            case 7 -> MoveDirection.TURN315;
            default -> throw new IllegalStateException("Unexpected value: " + i);
        };
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

    public boolean fertilization(Animal otherAnimal, int requiredEnergy) {
        Animal newAnimal;
        int thisAnimalEnergy = this.getEnergy();
        int otherAnimalEnergy = otherAnimal.getEnergy();
        if (thisAnimalEnergy > requiredEnergy && otherAnimalEnergy > requiredEnergy) {
            this.reduceEnergy((int) (thisAnimalEnergy / 4));
            otherAnimal.reduceEnergy((int) (otherAnimalEnergy / 4));
            int sideOfGenes = random.nextInt(2);
            if (sideOfGenes == 0) newAnimal = new Animal(map, position, this, otherAnimal, map.getMapEra());
            else newAnimal = new Animal(map, position, otherAnimal, this, map.getMapEra());
            map.place(newAnimal);
            this.increaseChildrenCounter();
            otherAnimal.increaseChildrenCounter();
            if (map.notifyTrackerOfBirth(this, otherAnimal)) newAnimal.setAnimalTracker(map.getAnimalTracker());
            return true;
        }
        else return false;
    }

    public String getEnergyColor() {
        if (energy > map.getStartingEnergy()) {
            return "B22222";
        }
        else return "FF1493";
    }

    public void increaseChildrenCounter() {
        childrenCounter += 1;
    }

    public int getChildrenCounter() {
        return childrenCounter;
    }

    public int getBirthEra() {
        return birthEra;
    }

    public int getDeathEra() {
        return deathEra;
    }

    public void setDeathEra(int era) {
        deathEra = era;
    }

    public void setAnimalTracker(AnimalTracker animalTracker) {
        this.animalTracker = animalTracker;
    }

    public AnimalTracker getAnimalTracker() {
        return animalTracker;
    }
}
