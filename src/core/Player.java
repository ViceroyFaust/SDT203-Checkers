package core;

/**
 * Represents the white and black players of the checkers game and gives them a String symbol for debugging purposes.
 * @author Danylo Rybchynskyi
 * @version 2024-04-26
 */
public enum Player {
    WHITE("O"),
    BLACK("X");

    private final String symbol;

    Player(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
