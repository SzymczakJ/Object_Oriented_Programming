package simulation.maps;

import simulation.*;

import java.util.*;
import java.util.List;


public abstract class AbstractWorldMap implements IPositionChangeObserver {
    protected List<Animal> animalList = new ArrayList<>();
    protected List<Animal> deadAnimalList = new ArrayList<>();
    protected Map<Vector2d, List<Animal>> animals = new HashMap<>();
    protected Map<Vector2d, Grass> grassTufts = new HashMap<>();
    protected Map<Genotype, Integer> genotypes = new HashMap<>();
    protected int mapEra = 0;
    protected Random random = new Random();
    public static int maxEnergy = 1000;
    protected int startingEnergy;
    protected int energyGivenByGrass;
    protected int moveEnergy;
    protected int mapHeight;
    protected int mapWidth;
    protected double jungleRatio;
    protected int numberOfAnimals = 0;
    protected int numberOfGrasses = 0;
    public final Vector2d lowerLeftSavannaCorner;
    public final Vector2d higherRightSavannaCorner;
    public final Vector2d lowerLeftJungleCorner;
    public final Vector2d higherRightJungleCorner;
    protected AnimalTracker currentTracker = null;

    public Vector2d[] computeBounds() {
        return new Vector2d[] {lowerLeftSavannaCorner, higherRightSavannaCorner};
    }

    public abstract Vector2d stepsOutOfBounds(Vector2d oldPosition, Vector2d newPosition);

    public AbstractWorldMap(int height, int width, double jungleRatio, int energyGivenByGrass, int startingEnergy, int moveEnergy) {
        mapHeight = height;
        mapWidth = width;
        this.jungleRatio = jungleRatio;
        lowerLeftSavannaCorner = new Vector2d(0, 0);
        higherRightSavannaCorner = new Vector2d(width, height);
        lowerLeftJungleCorner = new Vector2d((int) (width * (1 - jungleRatio) / 2), (int) (height * (1 - jungleRatio) / 2));
        higherRightJungleCorner = new Vector2d((int) (width * (1 + jungleRatio) / 2), (int) (height * (1 + jungleRatio) / 2));
        this.energyGivenByGrass = energyGivenByGrass;
        this.startingEnergy = startingEnergy;
        this.moveEnergy = moveEnergy;
    }

    public void initializeMapWithAnimals(int ammountOfAnimals, int startingEnergy) {
        int i = 0;
        while (i < ammountOfAnimals) {
            Vector2d position = new Vector2d(random.nextInt(mapWidth + 1), random.nextInt(mapHeight + 1));
            if (!isOccupied(position)) {
                Animal animal = new Animal(this, position, startingEnergy, this.getMapEra());
                place(animal);
                i++;
            }
        }
    }

    public void moveAllAnimals() {
        mapEra += 1;
        for (Animal animal: animalList) {
            animal.moveRandomly();
            animal.reduceEnergy(moveEnergy);
        }
    }

    public void allAnimalsGrazeOnGrass() {
        animals.forEach((position, animalListAtPosition) -> {
            if (grassTufts.get(position) != null && !animalListAtPosition.isEmpty()) grazeOnGrass(position);
        });
    }

    public void deleteDeadAnimals() {
        List<Animal> animalsToDelete = new ArrayList<>();
        List<Vector2d> positionsToDelete = new ArrayList<>();
        animals.forEach((position, animalListAtPosition) -> {
            for (Animal animal: animalListAtPosition) {
                if (animal.getEnergy() <= 0) {
                    animalsToDelete.add(animal);
                    animalList.remove(animal);
                    numberOfAnimals -= 1;
                    deadAnimalList.add(animal);
                    removeGenetypeFromGenotypes(animal.genotype);
                    animal.setDeathEra(this.getMapEra());
                    notifyTrackerOfDeath(animal);
                    if (animalListAtPosition.isEmpty()) positionsToDelete.add(position);
                }
            }
            for (Animal animal: animalsToDelete) {
                animalListAtPosition.remove(animal);
            }
            animalsToDelete.clear();
        });
        for (Vector2d positions: positionsToDelete) {
            animals.remove(positions);
        }
    }

    public void allAnimalsMakeLove() {
        animals.forEach((position, animalList) -> {
            if (animalList.size() > 1) makeLove(position);
        });
    }

    public boolean isInBounds(Vector2d newPosition) {
        return newPosition.follows(lowerLeftSavannaCorner) && newPosition.precedes(higherRightSavannaCorner);
    }

    public void place(Animal animal) throws IllegalArgumentException {
        if (this.isInBounds(animal.getPosition())) {
            putAnimalInPositionList(animal);
            animalList.add(animal);
            numberOfAnimals += 1;
            putGenotypeIntoGenotypes(animal.genotype);
        }
        else throw new IllegalArgumentException();
    }

    public void putGenotypeIntoGenotypes(Genotype genotype) {
        if (genotypes.get(genotype) == null) {
            genotypes.put(genotype, 1);
        }
        else {
            genotypes.put(genotype, genotypes.get(genotype) + 1);
        }
    }

    public void removeGenetypeFromGenotypes(Genotype genotype) {
        if (genotypes.get(genotype).intValue() == 1) {
            genotypes.remove(genotype);
        }
        else {
            genotypes.put(genotype, genotypes.get(genotype) - 1);
        }
    }

    public Object getStrongestAnimalAtPosition(Vector2d position) {
        if (animals.get(position) != null) {
            List<Animal> animalListAtPosition = animals.get(position);
            if (animalListAtPosition.size() > 0) {
                int highestEnergy = 0;
                Animal strongestAnimal = animalListAtPosition.get(0);
                for (Animal animal: animalListAtPosition) {
                    if (animal.getEnergy() > highestEnergy) {
                        strongestAnimal = animal;
                        highestEnergy = animal.getEnergy();
                    }
                }
                return strongestAnimal;
            }
            else return null;
        }
        else return null;
    }

    public Object objectAt(Vector2d position) {
        if (getStrongestAnimalAtPosition(position) == null) {
            return grassTufts.get(position);
        }
        else return getStrongestAnimalAtPosition(position);
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
        if (animalsAtPosition.isEmpty()) animals.remove(oldPosition);
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
            numberOfGrasses += 1;
            return true;
        }
        else return false;
    }

    public void growGrassOnJungle() {
        int toManyTimes = (higherRightJungleCorner.x - lowerLeftJungleCorner.x) * (higherRightJungleCorner.y - lowerLeftJungleCorner.y) * 2;
        int i = 0;
        Random random = new Random();

        int x = random.nextInt((int) (mapWidth * jungleRatio + 1)) + lowerLeftJungleCorner.x;
        int y = random.nextInt((int) (mapHeight * jungleRatio + 1)) + lowerLeftJungleCorner.y;
        Vector2d positionOfGrass = new Vector2d(x, y);
        while(i < toManyTimes && !createAndPlaceGrass(positionOfGrass)) {
            x = random.nextInt((int) (mapWidth * jungleRatio + 1)) + lowerLeftJungleCorner.x;
            y = random.nextInt((int) (mapHeight * jungleRatio + 1)) + lowerLeftJungleCorner.y;
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
                y = random.nextInt(higherRightJungleCorner.y + 1);
            }
            case 2 -> {
                x = random.nextInt(higherRightSavannaCorner.x - lowerLeftJungleCorner.x + 1) + lowerLeftJungleCorner.x;
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

    public void makeLove(Vector2d position) {
        List<Animal> strongestAnimals = getStrongestAnimalsAtPosition(position);
        if (strongestAnimals.size() == 1) {
            strongestAnimals.add(getSecondStrongestAnimalAtPosition(position, strongestAnimals.get(0).getEnergy()));
        }
        Animal animal1 = strongestAnimals.get(0);
        Animal animal2 = strongestAnimals.get(1);
        animal1.fertilization(animal2, (int) (startingEnergy / 2));
    }

    public int getNumberOfAnimals() {
        return numberOfAnimals;
    }

    public int getMapEra() {
        return mapEra;
    }

    public int getNumberOfGrasses() {
        return numberOfGrasses;
    }

    public double getAverageEnergy() {
        if (animalList.isEmpty()) return 0;
        int sum = 0;
        for (Animal animal: animalList) {
            sum += animal.getEnergy();
        }
        return (double) sum / (double) animalList.size();
    }

    public double getAverageChildrenCount() {
        if (animalList.isEmpty()) return 0;
        int sum = 0;
        for (Animal animal: animalList) {
            sum += animal.getChildrenCounter();
        }
        return (double) sum / (double) animalList.size();
    }

    public double getAverageLifeSpan() {
        if (deadAnimalList.isEmpty()) return 0;
        int sum = 0;
        for (Animal animal: deadAnimalList) {
            sum += (animal.getDeathEra() - animal.getBirthEra());
        }
        return (double) sum / (double) deadAnimalList.size();
    }

    public List<Genotype> getDominantGenotypes() {
        int maxAmmount = 0;
        for (Integer ammount: genotypes.values()) {
            if (maxAmmount < ammount) maxAmmount = ammount.intValue();
        }
        int finalMaxAmmount = maxAmmount;
        List<Genotype> dominantGenotypes = new ArrayList<>();
        genotypes.forEach((genotype, ammount) -> {
            if (ammount.intValue() == finalMaxAmmount) {
                dominantGenotypes.add(genotype);
            }
        });
        return dominantGenotypes;
    }

    public void setCurrentTracker(AnimalTracker animalTracker) {
        currentTracker = animalTracker;
    }

    public AnimalTracker getAnimalTracker() {
        return currentTracker;
    }

    public boolean notifyTrackerOfBirth(Animal animal1, Animal animal2) {
        boolean trackedAnimalPresent = false;
        if (animal1.getAnimalTracker() != null && animal1.getAnimalTracker().getAnimal() == animal1) trackedAnimalPresent = true;
        else if (animal2.getAnimalTracker() != null && animal2.getAnimalTracker().getAnimal() == animal2) trackedAnimalPresent = true;
        if (trackedAnimalPresent) {
            currentTracker.increaseChildrenAndDescendantCount();
            return true;
        }

        if (currentTracker != null && animal1.getAnimalTracker() == currentTracker) {
            currentTracker.increaseDescendantCount();
            return true;
        }
        else if (currentTracker != null && animal2.getAnimalTracker() == currentTracker) {
            currentTracker.increaseDescendantCount();
            return true;
        } else return false;
    }

    public void notifyTrackerOfDeath(Animal animal) {
        if (animal.getAnimalTracker() != null) {
            currentTracker.setDeathEra(animal);
        }
    }

    public int getStartingEnergy() {
        return startingEnergy;
    }

    public void magicEvolutionHelper() {
        List<Animal> newAnimalList = new ArrayList<>();
        for (Animal animal: animalList) {
            Vector2d position = new Vector2d(random.nextInt(mapWidth), random.nextInt(mapHeight));
            while (!isOccupied(position)) {
                position = new Vector2d(random.nextInt(mapWidth), random.nextInt(mapHeight));
            }
            Animal newAnimal = new Animal(this, position, startingEnergy, mapEra, animal.genotype);
            newAnimalList.add(newAnimal);
        }
        for (Animal animal: newAnimalList) {
            place(animal);
        }
    }
}