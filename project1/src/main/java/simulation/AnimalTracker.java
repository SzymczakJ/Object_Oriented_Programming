package simulation;

public class AnimalTracker {
    private int trackedChildrenNumber = 0;
    private int trackedDescendantsNumber = 0;
    private int eraOfDeath = -1;
    private final Animal animal;

    public AnimalTracker(Animal animal) {
        this.animal = animal;
        animal.setAnimalTracker(this);
    }

    public void increaseDescendantCount() {
        trackedDescendantsNumber += 1;
    }

    public void increaseChildrenAndDescendantCount() {
        trackedChildrenNumber += 1;
        trackedDescendantsNumber += 1;
    }

    public Animal getAnimal() {
        return animal;
    }

    public int getTrackedChildrenNumber() {
        return trackedChildrenNumber;
    }

    public int getTrackedDescendantsNumber() {
        return trackedDescendantsNumber;
    }

    public void setDeathEra(Animal animal) {
        if (this.animal == animal) eraOfDeath = animal.getDeathEra();
    }

    public int getDeathEra() {return eraOfDeath;}

    public boolean animalIsTracked(Animal animal) {
        return this.animal == animal;
    }
}
