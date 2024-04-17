package core;

/**
 * The game class. It is responsible for the logic and state of the checkers game.
 * @author Danylo Rybchynskyi
 */
public class Game {
    private final char[] players = {'X', 'O'};
    private final char[][] board = new char[8][8];
    private int currentPlayer;

    /**
     * Constructs the Game class and assigns the starting player index
     * @param startingPlayer the index of the starting player. 0 - X, 1 - O.
     */
    public Game(int startingPlayer) {
        // Set up the O pieces at the top of the board
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 8; ++j) {
                if ((i + j) % 2 == 1)
                    board[i][j] = 'O';
                else
                    board[i][j] = ' ';
            }
        }
        // Create blank spaces in the middle of the board
        for (int i = 3; i <= 4; ++i) {
            for (int j = 0; j < 8; ++j) {
                board[i][j] = ' ';
            }
        }
        // Set up the X pieces at the bottom of the board
        for (int i = 7; i > 4; --i) {
            for (int j = 7; j >= 0; --j) {
                if ((i + j) % 2 == 1)
                    board[i][j] = 'X';
                else
                    board[i][j] = ' ';
            }
        }
        currentPlayer = startingPlayer;
    }

    /**
     * Constructs the game class and assigns the standard starting player index of 0 - X.
     */
    public Game() {
        this(0);
    }

    /**
     * Validates a checker move. If the starting position starts not at a checker or the checker moves to an invalid
     * position, this function will return false.
     * @param startRow the starting checker row
     * @param startCol the starting checker column
     * @param endRow the destination row
     * @param endCol the destination column
     * @return whether the move was valid
     */
    public boolean validateMove(int startRow, int startCol, int endRow, int endCol) {
        // TODO
        return false;
    }

    /**
     * Returns whether a piece can be attacked from the given coordinates
     * @param startRow row to attack from
     * @param startCol column to attack from
     * @param endRow row to attack
     * @param endCol column to attack
     * @return true: selected piece is attacking selected enemy piece; false - otherwise
     */
    public boolean isAttacking(int startRow, int startCol, int endRow, int endCol) {
        // Determine the vertical direction of the piece based on the current player
        int yDirection = (currentPlayer == 0) ? -1 : 1;
        // Determine the horizontal direction of the attack based on where the enemy is located relative to piece
        int xDirection = (endCol < startCol) ? -1 : 1;
        // Check whether the enemy is in the front diagonal of the selected spot
        if (startRow + yDirection != endRow || startCol + xDirection != endCol) return false;
        // Check whether there is vertical space behind the enemy
        if (endRow + yDirection < 0 || endRow + yDirection > 7) return false;
        // Check whether there is horizontal space behind the enemy
        if (endCol + xDirection < 0 || endCol + xDirection > 7) return false;
        // Check if there is an empty spot behind the enemy
        return board[endRow + yDirection][endCol + xDirection] == ' ';
    }

    /**
     * Checks whether the game end state has been reached. If one of the players has lost all of their pieces or a
     * player has exhausted all of their moves, the game is over.
     * @return true for game over, false for game not over
     */
    public boolean isGameOver() {
        // TODO
        return false;
    }

    /**
     * Returns the number of possible moves on a given checker. If an empty location is given (which is invalid), the
     * function will return a negative number.
     * @param row the row of the given checker
     * @param col the column of the given checker
     * @return number of possible moves of a given checker, or -1 if no checker is selected
     */
    public int numPossibleMoves(int row, int col) {
        // TODO
        return 0;
    }

    /**
     * Moves the given checker to a new position. Will do nothing if the positions are invalid, so be careful.
     * @param startRow starting checker row
     * @param startCol starting checker column
     * @param endRow destination row
     * @param endCol destination column
     */
    public void move(int startRow, int startCol, int endRow, int endCol) {
        // TODO
    }

    /**
     * Returns the current player index
     * @return 0 for player X, 1 for player O
     */
    public int getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Returns the selected player character. Be careful, there are only two players. Invalid indexes will result in a
     * crash, so check bounds before using this function.
     * @param playerIndex 0 for player 1, 1 for player 2.
     * @return character representing player
     */
    public char getPlayer(int playerIndex) {
        return players[playerIndex];
    }

    /**
     * Returns the game board
     * @return the game board
     */
    public char[][] getBoard() {
        return board;
    }
}
