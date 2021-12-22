package simulation;

public enum MapDirection {
    NORTH,
    NORTHEAST,
    EAST,
    SOUTHEAST,
    SOUTH,
    SOUTHWEST,
    WEST,
    NORTHWEST;

    public static MapDirection createMapDirectionFromNumber(int number) throws IllegalArgumentException {
        return switch (number) {
            case 0 -> NORTH;
            case 1 -> NORTHEAST;
            case 2 -> EAST;
            case 3 -> SOUTHEAST;
            case 4 -> SOUTH;
            case 5 -> SOUTHWEST;
            case 6 -> WEST;
            case 7 -> NORTHWEST;
            default -> throw new IllegalArgumentException("wrong argument given, only 0-7 are acceptable");
        };
    }

    public String toString() {
        return switch (this) {
            case NORTH -> "^";
            case NORTHEAST -> "^>";
            case EAST -> ">";
            case SOUTHEAST -> "v>";
            case SOUTH -> "v";
            case SOUTHWEST -> "<v";
            case WEST -> "<";
            case NORTHWEST -> "<^";
        };
    }

    public MapDirection turn(MoveDirection spin) {
        return switch (spin) {
            case MOVEFORWARD -> this;
            case TURN45 -> turn45Degrees();
            case TURN90 -> turn90Degrees();
            case TURN135 -> turn135Degrees();
            case MOVEBACKWARD -> turn180Degrees();
            case TURN225 -> turn225Degrees();
            case TURN270 -> turn270Degrees();
            case TURN315 -> turn315Degrees();
        };
    }

    public MapDirection turn45Degrees() {
        return switch (this) {
            case NORTH -> NORTHEAST;
            case NORTHEAST -> EAST;
            case EAST -> SOUTHEAST;
            case SOUTHEAST -> SOUTH;
            case SOUTH -> SOUTHWEST;
            case SOUTHWEST -> WEST;
            case WEST -> NORTHWEST;
            case NORTHWEST -> NORTH;
        };
    }

    public MapDirection turn90Degrees() {
        return switch (this) {
            case NORTH -> EAST;
            case NORTHEAST -> SOUTHEAST;
            case EAST -> SOUTH;
            case SOUTHEAST -> SOUTHWEST;
            case SOUTH -> WEST;
            case SOUTHWEST -> NORTHWEST;
            case WEST -> NORTH;
            case NORTHWEST -> NORTHEAST;
        };
    }

    public MapDirection turn135Degrees() {
        return switch (this) {
            case NORTH -> SOUTHEAST;
            case NORTHEAST -> SOUTH;
            case EAST -> SOUTHWEST;
            case SOUTHEAST -> WEST;
            case SOUTH -> NORTHWEST;
            case SOUTHWEST -> NORTH;
            case WEST -> NORTHEAST;
            case NORTHWEST -> SOUTHEAST;
        };
    }

    public MapDirection turn180Degrees() {
        return switch (this) {
            case NORTH -> SOUTH;
            case NORTHEAST -> SOUTHWEST;
            case EAST -> WEST;
            case SOUTHEAST -> NORTHWEST;
            case SOUTH -> NORTH;
            case SOUTHWEST -> SOUTHEAST;
            case WEST -> EAST;
            case NORTHWEST -> SOUTHEAST;
        };
    }

    public MapDirection turn225Degrees() {
        return switch (this) {
            case NORTH -> SOUTHWEST;
            case NORTHEAST -> WEST;
            case EAST -> NORTHWEST;
            case SOUTHEAST -> NORTH;
            case SOUTH -> NORTHEAST;
            case SOUTHWEST -> EAST;
            case WEST -> SOUTHEAST;
            case NORTHWEST -> SOUTH;
        };
    }

    public MapDirection turn270Degrees() {
        return switch (this) {
            case NORTH -> WEST;
            case NORTHEAST -> NORTHWEST;
            case EAST -> NORTH;
            case SOUTHEAST -> NORTHEAST;
            case SOUTH -> EAST;
            case SOUTHWEST -> SOUTHEAST;
            case WEST -> SOUTH;
            case NORTHWEST -> SOUTHWEST;
        };
    }

    public MapDirection turn315Degrees() {
        return switch (this) {
            case NORTH -> NORTHWEST;
            case NORTHEAST -> NORTH;
            case EAST -> NORTHEAST;
            case SOUTHEAST -> EAST;
            case SOUTH -> SOUTHEAST;
            case SOUTHWEST -> SOUTH;
            case WEST -> SOUTHWEST;
            case NORTHWEST -> WEST;
        };
    }

    public Vector2d toUnitVector() {
        return switch (this) {
            case NORTH -> new Vector2d(0, 1);
            case NORTHEAST -> new Vector2d(1, 1);
            case EAST -> new Vector2d(1, 0);
            case SOUTHEAST -> new Vector2d(1, -1);
            case SOUTH -> new Vector2d(0, -1);
            case SOUTHWEST -> new Vector2d(-1, -1);
            case WEST -> new Vector2d(-1, 0);
            case NORTHWEST -> new Vector2d(-1, 1);
        };
    }
}