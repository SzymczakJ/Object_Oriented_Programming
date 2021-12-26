package simulation;

public class AnimalTracker {
    private int trackedChildren = 0;
    private int trackedDescendants = 0;
    private int eraOfDeath = -1;
    private Animal animal = null;

    public void increaseDescendantCount() {
        trackedDescendants += 1;
    }

    public void increaseChildrenAndDescendantCount() {
        trackedChildren += 1;
        trackedDescendants += 1;
    }

    public Animal getAnimal() {
        return animal;
    }
}
