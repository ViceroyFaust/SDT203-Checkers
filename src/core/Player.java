package core;

/**
 * Represents the white and black players of the checkers game
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
