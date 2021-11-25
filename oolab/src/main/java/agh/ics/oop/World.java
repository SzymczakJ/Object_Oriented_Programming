package agh.ics.oop;

import javax.sound.midi.SysexMessage;

public class World {
    public static void main(String[] args) {
        MoveDirection[] directions = new OptionsParser().parse(args);
        for (MoveDirection direction: directions) System.out.println(direction.toString());
        IWorldMap map = new RectangularMap(10, 5);
        Vector2d[] positions = { new Vector2d(2,2), new Vector2d(3,4) };
        IEngine engine = new SimulationEngine(map, directions, positions);
        System.out.println(map.toString());
        engine.run();
        System.out.println(map.toString());
        IWorldMap grassField = new GrassField(10);
        IEngine grassEngine = new SimulationEngine(grassField, directions, positions);
        System.out.println(grassField.toString());
        grassEngine.run();
        System.out.println(grassField.toString());
//        System.out.println("system wystartował");
//        Animal familiar = new Animal();
//        MoveDirection[] moveCommand = OptionsParser.parser(args);
//        for (MoveDirection arg : moveCommand) {
//            System.out.println(familiar.toString());
//            familiar.move(arg);
//            System.out.println(familiar.toString());
//        }
//        System.out.println("system zakończył działanie");
    }

    public static Direction[] changeToDirections(String[] args) {
        Direction[] directions;
        directions = new Direction[args.length];
        for (int i = 0; i < args.length; i++) {
            directions[i] = switch (args[i]) {
                case "f" -> Direction.FORWARD;
                case "b" -> Direction.BACKWARD;
                case "r" -> Direction.RIGHT;
                case "l" -> Direction.LEFT;
                default -> Direction.NOWHERE;
            };
        }
        return directions;
    }

    public static void run(MoveDirection[] args) {
        for (MoveDirection arg : args) {
            switch (arg) {
                case FORWARD:
                    System.out.println("zwierzak idzie do przodu");
                    break;
                case BACKWARD:
                    System.out.println("zwierzak idzie do tyłu");
                    break;
                case RIGHT:
                    System.out.println("zwierzak skręca w prawo");
                case LEFT:
                    System.out.println("zwierzak skreca w lewo");
                    break;
            }
        }
        Vector2d position1 = new Vector2d(1,2);
        System.out.println(position1);
        Vector2d position2 = new Vector2d(-2,1);
        System.out.println(position2);
        System.out.println(position1.add(position2));
        Animal animal = new Animal();
        System.out.println(animal.toString());
        animal.move(MoveDirection.RIGHT);
        animal.move(MoveDirection.FORWARD);
        animal.move(MoveDirection.FORWARD);
        animal.move(MoveDirection.FORWARD);
        System.out.println(animal.toString());
    }
}