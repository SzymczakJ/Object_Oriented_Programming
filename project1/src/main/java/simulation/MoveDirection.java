package simulation;

public enum MoveDirection {
    MOVEFORWARD,
    TURN45,
    TURN90,
    TURN135,
    MOVEBACKWARD,
    TURN225,
    TURN270,
    TURN315;

    public MoveDirection createMoveDirection(int i) {
        return switch (i) {
            case 0 -> MOVEFORWARD;
            case 1 -> TURN45;
            case 2 -> TURN90;
            case 3 -> TURN135;
            case 4 -> MOVEBACKWARD;
            case 5 -> TURN225;
            case 6 -> TURN270;
            case 7 -> TURN315;
            default -> throw new IllegalStateException("Unexpected value: " + i);
        };
    }
}
