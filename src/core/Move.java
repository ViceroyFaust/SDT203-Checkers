package core;

import java.util.Objects;
import java.util.regex.Pattern;

public record Move(Coordinate from, Coordinate to) {
    private static final Pattern VALID_INPUT = Pattern.compile("([1-8][a-h])-([1-8][a-h])");

    /**
     * Checks whether a string symbol represents this object in the proper format
     * regex: ([1-8][a-h])-([1-8][a-h])
     * @param symbol that represents the move
     * @return true - the representation is valid; false - otherwise
     */
    public static boolean isStringValid(String symbol) {
        var matcher = VALID_INPUT.matcher(symbol);
        return matcher.matches();
    }

    /**
     * Parses a double checkers board input, such as a representation of a source and destination coordinates.
     * Example 3a-4b. Regex: ([1-8][a-h])-([1-8][a-h])
     * @param input String representing two coordinates
     * @return A pair of Coordinates
     */
    public static Move parseMove(String input) {
        var matcher = VALID_INPUT.matcher(input);
        if (!matcher.matches()) throw new IllegalArgumentException();

        return new Move(
                Coordinate.parse(matcher.group(1)),
                Coordinate.parse(matcher.group(2))
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return from.equals(move.from) && to.equals(move.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }
}
