package core;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * The representation of a board coordinate
 * @author Danylo Rybchynskyi
 * @version 2024-04-26
 * @param row
 * @param col
 */
public record Coordinate(int row, int col) {
    private static final Pattern VALID_INPUT = Pattern.compile("([1-8][a-h])");
    private static final char FIRST_CHARACTER = 'a';

    /**
     * Parses a single checkers board input coordinate into a coordinate. Examples include 4g, 1a, etc.
     * Regex: [1-8][a-h]
     * @param input String representation of a checkers board coordinate
     * @return Coordinate
     */
    public static Coordinate parse(String input) {
        var matcher = VALID_INPUT.matcher(input);
        if (!matcher.matches()) throw new IllegalArgumentException();

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
