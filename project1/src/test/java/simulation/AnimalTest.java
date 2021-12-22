package simulation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AnimalTest {

    @Test
    public void animalMovementTest() {
        RectangularMap rectangularMap = new RectangularMap(30, 30, (float) 0.34);
        Vector2d startingPosition = new Vector2d(2, 3);
        Animal animal1 = new Animal(rectangularMap, startingPosition, 100, MapDirection.NORTH);
        rectangularMap.place(animal1);
        animal1.move(MoveDirection.MOVEFORWARD);
        assertEquals(animal1.getPosition(), new Vector2d(2, 4));
        animal1.move(MoveDirection.TURN270);
        animal1.move(MoveDirection.MOVEFORWARD);
        animal1.move(MoveDirection.MOVEFORWARD);
        assertEquals(animal1.getPosition(), new Vector2d(0, 4));
        animal1.move(MoveDirection.MOVEFORWARD);
        assertEquals(animal1.getPosition(), new Vector2d(0, 4));

        FoldedMap foldedMap = new FoldedMap(30, 30, (float) 0.34);
        Animal animal2 = new Animal(foldedMap, startingPosition, 100, MapDirection.NORTH);
        foldedMap.place(animal2);
        animal2.move(MoveDirection.MOVEFORWARD);
        assertEquals(new Vector2d(2, 4), animal2.getPosition());
        animal2.move(MoveDirection.TURN270);
        animal2.move(MoveDirection.MOVEFORWARD);
        animal2.move(MoveDirection.MOVEFORWARD);
        assertEquals(new Vector2d(0, 4), animal2.getPosition());
        animal2.move(MoveDirection.MOVEFORWARD);
        assertEquals(new Vector2d(30, 4), animal2.getPosition());
    }
}