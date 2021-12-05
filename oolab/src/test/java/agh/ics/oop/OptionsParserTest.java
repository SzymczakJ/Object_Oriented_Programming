package agh.ics.oop;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class OptionsParserTest {

    @Test
    public void parserTest() {
        String[] input = new String[] {"r", "right", "f", "forward", "l", "left", "b", "backward"};
        MoveDirection[] output = new MoveDirection[] {MoveDirection.RIGHT, MoveDirection.RIGHT, MoveDirection.FORWARD,
                MoveDirection.FORWARD, MoveDirection.LEFT, MoveDirection.LEFT, MoveDirection.BACKWARD, MoveDirection.BACKWARD};
        assertTrue(Arrays.equals(output, OptionsParser.parse(input)));
        String[] input1 = new String[] {"r", "right", "f", "forward", "l", "left", "b", "backward"};
        MoveDirection[] output1 = new MoveDirection[] {MoveDirection.RIGHT, MoveDirection.RIGHT, MoveDirection.FORWARD,
                MoveDirection.FORWARD, MoveDirection.LEFT, MoveDirection.LEFT, MoveDirection.BACKWARD, MoveDirection.BACKWARD};
        assertTrue(Arrays.equals(output1, OptionsParser.parse(input1)));
        String[] input2 = new String[] {"r", "right", "f", "forward", "l", "left", "b", "backward", "left", "r"};
        MoveDirection[] output2 = new MoveDirection[] {MoveDirection.RIGHT, MoveDirection.RIGHT, MoveDirection.FORWARD,
                MoveDirection.FORWARD, MoveDirection.LEFT, MoveDirection.LEFT, MoveDirection.BACKWARD};
        assertFalse(Arrays.equals(output2, OptionsParser.parse(input2)));
        String[] input3 = new String[] {"r", "right", "f", "fewa", "das", "left", "b", "ba", "sadasd", "adas"};
        assertThrows(IllegalArgumentException.class, () -> {
            OptionsParser.parse(input3);
        });
    }
}
