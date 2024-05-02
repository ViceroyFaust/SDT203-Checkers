package rybchynskyi.checkersgui.core;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Represents a move on a checkerboard
 * @author Danylo Rybchynskyi
 * @version 2024-04-26
 * @param from the origin coordinate
 * @param to the destination coordinate
 */
public record Move(Coordinate from, Coordinate to) {
    private static final Pattern VALID_INPUT = Pattern.compile("([1-8][a-h])-([1-8][a-h])");

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
