package agh.ics.oop;

import java.util.Arrays;

public class OptionsParser {

    public static MoveDirection[] parse(String[] input) {
        MoveDirection[] res;
        res = new MoveDirection[input.length];
        int counter = 0;
        for (String s : input) {
            switch (s) {
                case "f":
                case "forward":
                    res[counter] = MoveDirection.FORWARD;
                    counter += 1;
                    break;
                case "b":
                case "backward":
                    res[counter] = MoveDirection.BACKWARD;
                    counter += 1;
                    break;
                case "r":
                case "right":
                    res[counter] = MoveDirection.RIGHT;
                    counter += 1;
                    break;
                case "l":
                case "left":
                    res[counter] = MoveDirection.LEFT;
                    counter += 1;
                    break;
                default:
            }
        }
        return Arrays.copyOfRange(res, 0, counter);
    }
}
