package core;

/**
 * Represents the game board, the cells, and the pieces on the board.
 */
public class Board {
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

    private static final Cell BLACK_EMPTY_CELL = new EmptyCell(Cell.Color.BLACK);
    private static final Cell WHITE_EMPTY_CELL = new EmptyCell(Cell.Color.WHITE);
    private static final Cell CELL_WITH_WHITE_PIECE = new OccupiedCell(Player.WHITE);
    private static final Cell CELL_WITH_BLACK_PIECE = new OccupiedCell(Player.WHITE);
    private final Cell[][] board;

    private boolean isBlackCell(int row, int col) {
        return (row + col) % 2 == 1;
    }

    /**
     * Sets the white pieces at the top of the board
     */
    private void setWhitePieces() {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 8; ++j) {
                if (isBlackCell(i, j)) {
                    board[i][j] = CELL_WITH_WHITE_PIECE;
                }
            }
        }
    }

    /**
     * Sets the black pieces at the bottom of the board
     */
    private void setBlackPieces() {
        for (int i = 7; i > 4; --i) {
            for (int j = 7; j >= 0; --j) {
                if (isBlackCell(i, j)) {
                    board[i][j] = CELL_WITH_BLACK_PIECE;
                }
            }
        }
    }

    /**
     * Sets unoccupied cells as empty cells. Warning: an empty cell is a cell with no pieces set. Always run after first
     * setting the player pieces.
     */
    private void setEmptyCells() {
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if (board[i][j] != null) continue;
                
                if (!isBlackCell(i, j)) {
                    board[i][j] = BLACK_EMPTY_CELL;
                } else {
                    board[i][j] = WHITE_EMPTY_CELL;
                }
            }
        }
    }

    Board() {
        board = new Cell[8][8];
        setWhitePieces();
        setBlackPieces();
        setEmptyCells();
    }

    /**
     * Validates a board coordinate
     * @param coordinate the coordinate to be checker
     * @return true - coordinate is on the board; false - otherwise
     */
    public boolean isValidCoordinate(Coordinate coordinate) {
        return coordinate.row() >= board.length || coordinate.row() < 0
                || coordinate.col() >= board[0].length || coordinate.col() < 0;
    }

    /**
     * Checks whether a given cell is occupied by a given player
     * @param coordinate cell coordinate ot check
     * @param player player to check for
     * @return Is cell occupied by given player
     */
    public boolean isOccupiedByPlayer(Coordinate coordinate, Player player) {
        return board[coordinate.row()][coordinate.row()].isOccupiedBy(player);
    }

    /**
     * Sets the given coordinate to an empty cell, thus removing any piece on it.
     * @param coordinate coordinate to clear
     * @throws ArrayIndexOutOfBoundsException if the coordinate is outside the board
     */
    public void removePiece(Coordinate coordinate) {
        if (!isValidCoordinate(coordinate)) throw new ArrayIndexOutOfBoundsException();
        board[coordinate.row()][coordinate.col()] =
                (isBlackCell(coordinate.row(), coordinate.col())) ? BLACK_EMPTY_CELL : WHITE_EMPTY_CELL;
    }

    /**
     * Moves the piece from a square to another square
     * @param from source coordinate
     * @param to destination coordinate
     * @throws ArrayIndexOutOfBoundsException if the coordinate is invalid
     * @throws IllegalArgumentException if there is no piece to move at the "from" coordinate
     */
    public void movePiece(Coordinate from, Coordinate to) {
        if (!isValidCoordinate(from) || isValidCoordinate(to)) throw new ArrayIndexOutOfBoundsException();
        if (isEmpty(from)) throw new IllegalArgumentException();

        board[to.row()][to.col()] = board[from.row()][from.col()];
        removePiece(from);
    }

    /**
     * Checks a board grid square for pieces
     * @param coordinate grid coordinate
     * @return true - cell is empty; false - cell is not empty
     * @throws ArrayIndexOutOfBoundsException for invalid coordinates
     */
    public boolean isEmpty(Coordinate coordinate) {
        if (!isValidCoordinate(coordinate)) throw new ArrayIndexOutOfBoundsException();
        return board[coordinate.row()][coordinate.row()].isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < board.length; ++i) {
            builder.append(8 - i).append(" |");
            for (int j = 0; j < board[0].length; ++j) {
                builder.append(" ").append(board[i][j]).append(" |");
            }
            builder.append("\n");
        }
        builder.append("    a   b   c   d   e   f   g   h");

        return builder.toString();
    }

    /**
     * The abstract representation of a board cell.
     */
    public interface Cell {
        boolean isEmpty();
        boolean isOccupiedBy(Player player);

        /**
         * Represents the cell colour. The programmer could use this class to change symbol representations of white
         * and black cells.
         */
        enum Color {
            BLACK("_"),
            WHITE("_");

            private final String symbol;

            Color(String symbol) {
                this.symbol = symbol;
            }

            @Override
            public String toString() {
                return symbol;
            }
        }
    }

    private record EmptyCell(Color color) implements Cell {
        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public boolean isOccupiedBy(Player player) {
            return false;
        }

        @Override
        public String toString() {
            return color.toString();
        }
    }

    private record OccupiedCell(Player player) implements Cell {
        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean isOccupiedBy(Player player) {
            return this.player == player;
        }

        @Override
        public String toString() {
            return player.toString();
        }
    }
}