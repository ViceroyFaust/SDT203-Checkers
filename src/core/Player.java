package core;

/**
 * Represents the white and black players of the checkers game and gives them a String symbol for debugging purposes.
 * @author Danylo Rybchynskyi
 * @version 2024-04-26
 */
public enum Player {
    /**
     * The white player piece along with their ASCII symbol
     */
    WHITE("O"),
    /**
     * The black player piece along with their ASCII symbol
     */
    BLACK("X");

    private final String symbol;

    Player(String symbol) {
        this.symbol = symbol;
    }

    /**
     * The ASCII symbol representing this player
     * @return ASCII symbol
     */
    @Override
    public String toString() {
        return symbol;
    }
}
