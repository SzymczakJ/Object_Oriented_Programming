package simulation;

import java.util.*;


public abstract class AbstractWorldMap implements IPositionChangeObserver {
//    protected Map<Vector2d, Map<Animal, Animal>> animals = new HashMap<>();
    protected Map<Vector2d, List<Animal>> animals = new HashMap<>();
    protected Map<Vector2d, Grass> grassTufts = new HashMap<>();
    public static int maxEnergy = 100;
    protected int energyGivenByGrass;
    protected int mapHeight;
    protected int mapWidth;
    protected double jungleRatio;
    protected Vector2d lowerLeftSavannaCorner;
    protected Vector2d higherRightSavannaCorner;
    protected Vector2d lowerLeftJungleCorner;
    protected Vector2d higherRightJungleCorner;

    public abstract Vector2d stepsOutOfBounds(Vector2d oldPosition, Vector2d newPosition);

    public AbstractWorldMap(int height, int width, double jungleRatio, int energyGivenByGrass) {
        mapHeight = height;
        mapWidth = width;
        this.jungleRatio = jungleRatio;
        lowerLeftSavannaCorner = new Vector2d(0, 0);
        higherRightSavannaCorner = new Vector2d(width, height);
        lowerLeftJungleCorner = new Vector2d((int) (width * (1 - jungleRatio) / 2), (int) (height * (1 - jungleRatio) / 2));
        higherRightJungleCorner = new Vector2d((int) (width * (1 + jungleRatio) / 2), (int) (height * (1 + jungleRatio) / 2));
        this.energyGivenByGrass = energyGivenByGrass;
    }

    public boolean isInBounds(Vector2d newPosition) {
        return newPosition.follows(lowerLeftSavannaCorner) && newPosition.precedes(higherRightSavannaCorner);
    }

    public void place(Animal animal) throws IllegalArgumentException {
        if (this.isInBounds(animal.getPosition())) {
            putAnimalInPositionList(animal);
        }
        else throw new IllegalArgumentException();
    }

    public void putAnimalInPositionList(Animal animal) {
        if (animals.get(animal.getPosition()) != null) {
            List<Animal> animalsAtPosition = animals.get(animal.getPosition());
            animalsAtPosition.add(animal);
        }
        else {
            List<Animal> animalsAtPosition = new ArrayList<>();
            animals.put(animal.getPosition(), animalsAtPosition);
            animalsAtPosition.add(animal);
        }
    }

    public void positionChanged(Vector2d oldPosition, Animal animal) {
        List<Animal> animalsAtPosition = animals.get(oldPosition);
        animalsAtPosition.remove(animal);
        putAnimalInPositionList(animal);
    }

    public boolean isOccupied(Vector2d position) {
        if (grassTufts.get(position) == null) {
            if (animals.get(position) != null) {
                List<Animal> animalHashMap = animals.get(position);
                return (!animalHashMap.isEmpty());
            }
            else return false;
        }
        else return true;
    }

    public boolean createAndPlaceGrass(Vector2d position) {
        if (!isOccupied(position)) {
            Grass grass = new Grass(position);
            grassTufts.put(grass.getPosition(), grass);
            return true;
        }
        else return false;
    }

    public void growGrassOnJungle() {
        int toManyTimes = (higherRightJungleCorner.x - lowerLeftJungleCorner.x) * (higherRightJungleCorner.y - lowerLeftJungleCorner.y) * 2;
        int i = 0;
        Random random = new Random();

        int x = random.nextInt((int) (mapWidth * jungleRatio)) + lowerLeftJungleCorner.x;
        int y = random.nextInt((int) (mapHeight * jungleRatio)) + lowerLeftJungleCorner.y;
        Vector2d positionOfGrass = new Vector2d(x, y);
        while(i < toManyTimes && !createAndPlaceGrass(positionOfGrass)) {
            x = random.nextInt((int) (mapWidth * jungleRatio)) + lowerLeftJungleCorner.x;
            y = random.nextInt((int) (mapHeight * jungleRatio)) + lowerLeftJungleCorner.y;
            positionOfGrass = new Vector2d(x, y);
            i++;
        }
    }

    public void growGrassOnSavanna() {
        int toManyTimes = (int) (mapHeight * mapWidth * 2 * (1 - jungleRatio));
        int i = 0;

        Vector2d positionOfGrass = generateSavannaPosition();
        while(i < toManyTimes && !createAndPlaceGrass(positionOfGrass)) {
            positionOfGrass = generateSavannaPosition();
            i++;
        }
    }

    public Vector2d generateSavannaPosition() {
        Random random = new Random();
        int areaToRandomFrom = random.nextInt(4);
        int x;
        int y;
        switch (areaToRandomFrom) {
            case 0 -> {
                x = random.nextInt(higherRightJungleCorner.x + 1);
                y = random.nextInt(lowerLeftJungleCorner.y);
            }
            case 1 -> {
                x = random.nextInt(higherRightSavannaCorner.x - higherRightJungleCorner.x) + higherRightJungleCorner.x + 1;
                y = random.nextInt(higherRightJungleCorner.y);
            }
            case 2 -> {
                x = random.nextInt(higherRightSavannaCorner.x - lowerLeftJungleCorner.x) + lowerLeftJungleCorner.x + 1;
                y = random.nextInt(higherRightSavannaCorner.y - higherRightJungleCorner.y) + higherRightJungleCorner.y + 1;
            }
            case 3 -> {
                x = random.nextInt(lowerLeftJungleCorner.x);
                y = random.nextInt(higherRightSavannaCorner.y - lowerLeftSavannaCorner.y) + lowerLeftSavannaCorner.y + 1;
            }
            default -> throw new IllegalStateException("Unexpected value: " + areaToRandomFrom);
        };
        return new Vector2d(x, y);
    }
    //TODO TOWYJEBAC
    public void printGrasses() {
        grassTufts.forEach(
                (key, value)
                    -> System.out.println(key));
    }

    public List<Animal> getStrongestAnimalsAtPosition(Vector2d position) {
        int largestEnergy = 0;
        List<Animal> animalsAtPositionList = animals.get(position);
        for (Animal animal: animalsAtPositionList) {
            if (largestEnergy < animal.getEnergy()) largestEnergy = animal.getEnergy();
        }
        List<Animal> result = new ArrayList<>();
        for (Animal animal: animalsAtPositionList) {
            if (largestEnergy == animal.getEnergy()) result.add(animal);
        }
        return result;
    }

    public Animal getSecondStrongestAnimalAtPosition(Vector2d position, int energy) {
        int largestEnergy = 0;
        List<Animal> animalsAtPositionList = animals.get(position);
        Animal result = animalsAtPositionList.get(0);
        for (Animal animal: animalsAtPositionList) {
            if (largestEnergy < animal.getEnergy() && animal.getEnergy() < energy) {
                largestEnergy = animal.getEnergy();
                result = animal;
            }
        }
        return result;
    }

    public void grazeOnGrass(Vector2d position) {
        List<Animal> strongestAnimals = getStrongestAnimalsAtPosition(position);
        int energyBoost = (int) (energyGivenByGrass / strongestAnimals.size());
        for (Animal animal: strongestAnimals) {
            animal.increaseEnergy(energyBoost);
        }
        grassTufts.remove(position);
    }
}
