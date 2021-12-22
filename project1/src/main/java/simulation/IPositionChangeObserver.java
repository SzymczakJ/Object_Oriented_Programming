package simulation;

public interface IPositionChangeObserver {

    void positionChanged(Vector2d oldPosition, Animal animal);

}
