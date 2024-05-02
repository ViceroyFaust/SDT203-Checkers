package rybchynskyi.checkersgui.core;

/**
 * Represents the game board, the cells, and the pieces on the board.
 * @author Danylo Rybchynskyi
 * @version 2024-04-26
 */
public class Board {

    private static final Cell BLACK_EMPTY_CELL = new EmptyCell(Cell.Color.BLACK);
    private static final Cell WHITE_EMPTY_CELL = new EmptyCell(Cell.Color.WHITE);
    private static final Cell CELL_WITH_WHITE_PIECE = new OccupiedCell(Player.WHITE);
    private static final Cell CELL_WITH_BLACK_PIECE = new OccupiedCell(Player.BLACK);
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

    /**
     * Constructs a 8x8 checkerboard and sets the pieces
     */
    Board() {
        board = new Cell[8][8];
        setWhitePieces();
        setBlackPieces();
        setEmptyCells();
    }

    /**
     * Validates a board coordinate
     * @param row the board row
     * @param col the board column
     * @return true - coordinate is on the board; false - otherwise
     */
    public boolean isValidCoordinate(int row, int col) {
        return (row < board.length && row >= 0) &&
                (col < board[0].length && col >= 0);
    }

    /**
     * Checks whether a given cell is occupied by a given player
     * @param player player to check for
     * @param row the board row
     * @param col the board column
     * @return Is cell occupied by given player
     */
    public boolean isOccupiedByPlayer(int row, int col, Player player) {
        return board[row][col].isOccupiedBy(player);
    }

    /**
     * Sets the given coordinate to an empty cell, thus removing any piece on it.
     * @param row the board row
     * @param col the board column
     * @throws ArrayIndexOutOfBoundsException if the coordinate is outside the board
     */
    public void removePiece(int row, int col) {
        if (!isValidCoordinate(row, col)) throw new ArrayIndexOutOfBoundsException();
        board[row][col] =
                (isBlackCell(row, col)) ? BLACK_EMPTY_CELL : WHITE_EMPTY_CELL;
    }

    /**
     * Moves the piece from a square to another square
     * @param fromRow the source row from which to move
     * @param fromCol the source column from which to move
     * @param toRow the destination row
     * @param toCol the destination column
     * @throws ArrayIndexOutOfBoundsException if the coordinate is invalid
     * @throws IllegalArgumentException if there is no piece to move at the "from" coordinate
     */
    public void movePiece(int fromRow, int fromCol, int toRow, int toCol) {
        if (!isValidCoordinate(fromRow, fromCol) || !isValidCoordinate(toRow, toCol))
            throw new ArrayIndexOutOfBoundsException();
        if (!isEmpty(toRow, toCol)) throw new IllegalArgumentException();

        board[toRow][toCol] = board[fromRow][fromCol];
        removePiece(fromRow, fromCol);
    }

    /**
     * Checks a board grid square for pieces
     * @param row the board row
     * @param col the board column
     * @return true - cell is empty; false - cell is not empty
     * @throws ArrayIndexOutOfBoundsException for invalid coordinates
     */
    public boolean isEmpty(int row, int col) {
        if (!isValidCoordinate(row, col)) throw new ArrayIndexOutOfBoundsException();
        return board[row][col].isEmpty();
    }

    /**
     * Returns the number of rows, or vertical squares, of the board
     * @return int rows
     */
    public int getRows() {
        return board.length;
    }

    /**
     * Returns the number of columns, or horizontal squares, of the board
     * @return int columns
     */
    public int getCols() {
        return board[0].length;
    }

    /**
     * Generates a String representation of the board in its current state. Shows empty spots, black player pieces, and
     * white player pieces. Also numbers the rows and the columns.
     * Example output:
     * 8 | _ | o | _ | o | _ | o | _ | o |
     * 7 | o | _ | o | _ | o | _ | o | _ |
     * 6 | _ | o | _ | o | _ | o | _ | o |
     * 5 | _ | _ | _ | _ | _ | _ | _ | _ |
     * 4 | _ | _ | _ | _ | _ | _ | _ | _ |
     * 3 | x | _ | x | _ | x | _ | x | _ |
     * 2 | _ | x | _ | x | _ | x | _ | x |
     * 1 | x | _ | x | _ | x | _ | x | _ |
     *     a   b   c   d   e   f   g   h
     * @return String representation of the board
     */
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
        builder.append("\n");
        return builder.toString();
    }

    /**
     * The abstract representation of a board cell.
     */
    public interface Cell {
        /**
         * Checks whether a given board cell is empty or not
         * @return true - the cell has no pieces on it; false - otherwise
         */
        boolean isEmpty();

        /**
         * Checks whether a given board cell is taken by a piece of a given player
         * @param player the player whose piece to check for
         * @return true - the player owns a piece on this cell; false - otherwise
         */
        boolean isOccupiedBy(Player player);

        /**
         * Represents the cell colour. The programmer could use this class to change symbol representations of white
         * and black cells.
         */
        enum Color {
            /**
             * Black cell with an ASCII representation of it
             */
            BLACK("_"),
            /**
             * White cell with an ASCII representation of it
             */
            WHITE("_");

            private final String symbol;

            Color(String symbol) {
                this.symbol = symbol;
            }

            /**
             * Returns the ASCII symbol representation of the cell's color
             * @return ASCII symbol
             */
            @Override
            public String toString() {
                return symbol;
            }
        }
    }

    /**
     * The abstract representation of an empty cell on a board. It has a colour of either black or white.
     * @param color the colour of the cell
     */
    private record EmptyCell(Color color) implements Cell {
        /**
         * The empty cell is always empty
         * @return true
         */
        @Override
        public boolean isEmpty() {
            return true;
        }

        /**
         * The empty cell is never occupied by another player
         * @param player the player to check for
         * @return false - this cell is always empty
         */
        @Override
        public boolean isOccupiedBy(Player player) {
            return false;
        }

        @Override
        public String toString() {
            return color.toString();
        }
    }

    /**
     * The abstract representation of an occupied cell. It holds a player.
     * @param player the player who owns the piece in this cell
     */
    private record OccupiedCell(Player player) implements Cell {
        /**
         * The occupied cell is never empty
         * @return false
         */
        @Override
        public boolean isEmpty() {
            return false;
        }

        /**
         * The occupied cell can only be occupied by one player at a time
         * @param player the player to check for
         * @return true - it is occupied by the given player; false - otherwise
         */
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