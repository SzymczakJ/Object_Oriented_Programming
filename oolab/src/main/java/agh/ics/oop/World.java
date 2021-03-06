package agh.ics.oop;

import agh.ics.oop.gui.App;
import javafx.application.Application;

import javax.sound.midi.SysexMessage;

public class World {
    public static void main(String[] args) {
        try {
            Application.launch(App.class, args);
        } catch(IllegalArgumentException e) {
            System.out.println(e);
        }
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