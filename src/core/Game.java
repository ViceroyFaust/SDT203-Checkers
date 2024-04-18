package core;

/**
 * The game class. It is responsible for the logic and state of the checkers game.
 * @author Danylo Rybchynskyi
 */
public class Game {
    private final char emptySpot = '_';
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
                    board[i][j] = players[1];
                else
                    board[i][j] = emptySpot;
            }
        }
        // Create blank spaces in the middle of the board
        for (int i = 3; i <= 4; ++i) {
            for (int j = 0; j < 8; ++j) {
                board[i][j] = emptySpot;
            }
        }
        // Set up the X pieces at the bottom of the board
        for (int i = 7; i > 4; --i) {
            for (int j = 7; j >= 0; --j) {
                if ((i + j) % 2 == 1)
                    board[i][j] = players[0];
                else
                    board[i][j] = emptySpot;
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
     * Checks whether a given coordinate is valid
     * @param row the y coordinate
     * @param col the x coordinate
     * @return true is the coordinate is valid; false if not
     */
    public boolean isCoordinateValid(int row, int col) {
        return row >= 0 && row <= 7 && col >= 0 && col <= 7;
    }

    /**
     * Checks whether a given piece (of the current player) can jump right now
     * @param row the piece's row
     * @param col the piece's column
     * @return true if piece can jump; false otherwise
     */
    public boolean canJump(int row, int col) {
        int yDirection = (currentPlayer == 0) ? -1 : 1;
        // Check if coordinates are valid
        if (!isCoordinateValid(row, col)) return false;
        // Check if current player piece selected
        if (board[row][col] != players[currentPlayer]) return false;
        // Check whether there is enough vertical space to jump
        if (row + 2 * yDirection < 0 || row + 2 * yDirection > 7) return false;
        // Check if we can jump on the left
        if (col - 2 >= 0 && board[row + yDirection][col - 1] == players[(currentPlayer + 1) % 2] && board[row + 2 * yDirection][col - 2] == emptySpot) return true;
        // Check if we can jump on the right
        return col + 2 >= 0 && board[row + yDirection][col + 1] == players[(currentPlayer + 1) % 2] && board[row + 2 * yDirection][col + 2] == emptySpot;
    }

    /**
     * Checks whether a given piece can move
     * @param row the piece's row
     * @param col the piece's column
     * @return whether a piece has available moves
     */
    public boolean canMove(int row, int col) {
        int yDirection = (currentPlayer == 0) ? -1 : 1;
        // Check whether coordinates are valid
        if(!isCoordinateValid(row, col)) return false;
        // Check whether current player piece is selected
        if (board[row][col] != players[currentPlayer]) return false;
        // Check if there is space in front of the piece
        if (row + yDirection < 0 || row + yDirection > 7) return false;
        // Check whether this piece can jump
        if (canJump(row, col)) return true;
        // Check if other pieces have to jump
        for (int i = 0; i < board.length; ++i) {
            for (int j = 0; j < board[0].length; ++j) {
                if (canJump(i, j)) return false;
            }
        }
        // Check left diagonal
        if (col - 1 >= 0 && board[row + yDirection][col - 1] == emptySpot) return true;
        // Check the right diagonal
        return col + 1 <= 7 && board[row + yDirection][col + 1] == emptySpot;
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
    public boolean isMoveValid(int startRow, int startCol, int endRow, int endCol) {
        int yDirection = (currentPlayer == 0) ? -1 : 1;
        int yDistance = endRow - startRow;
        int xDistance = endCol - startCol;
        // Check validity of coordinates
        if (!isCoordinateValid(startRow, startCol) || !isCoordinateValid(endRow, endCol)) return false;
        // Check whether the player piece is selected
        if (board[startRow][startCol] != players[currentPlayer]) return false;
        // Check whether piece can move
        if (!canMove(startRow, startCol)) return false;
        // Check whether the move spot is empty
        if (board[endRow][endCol] != emptySpot) return false;
        // If this is a jump, check whether end coordinates are correct
        if (canJump(startRow, startCol) && startRow + 2 * yDirection == endRow && xDistance * xDistance == 4) return true;
        // Check whether this is a diagonal jump forward
        return yDistance == yDirection && xDistance * xDistance == 1;
    }

    /**
     * Checks for a game over state. The game is over if the current player can no longer make any moves.
     * @return true for game over, false for game not over
     */
    public boolean isGameOver() {
        for (int i = 0; i < board.length; ++i) {
            for (int j = 0; j < board[0].length; ++j) {
                // If a piece of the current player can move, then game is not over
                if (players[currentPlayer] == board[i][j] && canMove(i, j)) return true;
            }
        }
        return true;
    }

    /**
     * Moves the given checker to a new position. Will do nothing if the positions are invalid, so be careful.
     * @param startRow starting checker row
     * @param startCol starting checker column
     * @param endRow destination row
     * @param endCol destination column
     */
    public void move(int startRow, int startCol, int endRow, int endCol) {
        // If move is invalid, do not move
        if (!isMoveValid(startRow, startCol, endRow, endCol)) return;
        // Move the piece and make the spot it left a space
        board[endRow][endCol] = board[startRow][startCol];
        board[startRow][startCol] = emptySpot;
        // If this was a jump, clear the enemy piece
        if (canJump(startRow, startCol)) {
            int yDirection = (currentPlayer == 0) ? -1 : 1;
            int xDirection = (startCol > endCol) ? -1 : 1;
            board[startRow + yDirection][startCol + xDirection] = emptySpot;
        }
        // If the piece cannot jump again, go to the next player
        if (!canJump(endRow, endCol))
            currentPlayer = (currentPlayer + 1) % 2;
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
