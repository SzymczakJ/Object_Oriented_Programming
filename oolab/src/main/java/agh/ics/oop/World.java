package agh.ics.oop;

public class World {
    public static void main(String[] args) {
        System.out.println("system wystartował");
        Direction[] newArgs;
        newArgs = changeToDirections(args);
        run(newArgs);
        System.out.println("system zakończył działanie");
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

    public static void run(Direction[] args) {
//        System.out.println("zwierzak idzie do przodu");
        for (Direction arg : args) {
//            if (!Objects.equals(arg, args[args.length - 1])) System.out.print(arg + ",");
//            else System.out.println(arg);
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
                case NOWHERE:
            }
        }
        Vector2d position1 = new Vector2d(1,2);
        System.out.println(position1);
        Vector2d position2 = new Vector2d(-2,1);
        System.out.println(position2);
        System.out.println(position1.add(position2));
        Integer[] dupa = new Integer[20];
    }
}