package core;

import java.util.Objects;
import java.util.regex.Pattern;

public record Coordinate(int row, int col) {
    private static final Pattern VALID_INPUT = Pattern.compile("([1-8][a-h])");
    private static final char FIRST_CHARACTER = 'a';

    /**
     * Checks whether a string symbol represents this object in the proper format.
     * Regex: ([1-8][a-h])
     * @param symbol that represents the coordinate
     * @return true - the representation is valid; false - otherwise
     */
    public static boolean isStringValid(String symbol) {
        var matcher = VALID_INPUT.matcher(symbol);
        return matcher.matches();
    }

    /**
     * Parses a single checkers board input coordinate into a coordinate. Examples include 4g, 1a, etc.
     * Regex: [1-8][a-h]
     * @param input String representation of a checkers board coordinate
     * @return Coordinate
     */
    public static Coordinate parse(String input) {
        if (!isStringValid(input)) throw new IllegalArgumentException();

        int row = 8 - Character.getNumericValue(input.charAt(0));
        int col = input.charAt(1) - FIRST_CHARACTER;

        return new Coordinate(row, col);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return row == that.row && col == that.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
}
