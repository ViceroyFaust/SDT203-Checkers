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
     * @param coordinate the coordinate to be checked
     * @return true is the coordinate is valid; false if not
     */
    public boolean isCoordinateValid(Coordinate coordinate) {
        int row = coordinate.y;
        int col = coordinate.x;
        return row >= 0 && row <= 7 && col >= 0 && col <= 7;
    }

    /**
     * Checks whether a given piece (of the current player) can jump right now
     * @param coordinate The coordinate of the checker piece
     * @return true if piece can jump; false otherwise
     */
    public boolean canJump(Coordinate coordinate) {
        int row = coordinate.y;
        int col = coordinate.x;
        int yDirection = (currentPlayer == 0) ? -1 : 1;
        // Check if coordinates are valid
        if (!isCoordinateValid(coordinate)) return false;
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
     * @param coordinate the coordinate representing the checker piece
     * @return whether a piece has available moves
     */
    public boolean canMove(Coordinate coordinate) {
        int row = coordinate.y;
        int col = coordinate.x;
        int yDirection = (currentPlayer == 0) ? -1 : 1;
        // Check whether coordinates are valid
        if(!isCoordinateValid(coordinate)) return false;
        // Check whether current player piece is selected
        if (board[row][col] != players[currentPlayer]) return false;
        // Check if there is space in front of the piece
        if (row + yDirection < 0 || row + yDirection > 7) return false;
        // Check whether this piece can jump
        if (canJump(coordinate)) return true;
        // Check if other pieces have to jump
        for (int i = 0; i < board.length; ++i) {
            for (int j = 0; j < board[0].length; ++j) {
                if (canJump(coordinate)) return false;
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
     * @param from The coordinate of the piece to be moved
     * @param to The coordinate of the location to be moved to
     * @return whether the move was valid
     */
    public boolean isMoveValid(Coordinate from, Coordinate to) {
        int yDirection = (currentPlayer == 0) ? -1 : 1;
        int yDistance = to.y - from.y;
        int xDistance = to.x - from.x;
        // Check validity of coordinates
        if (!isCoordinateValid(from) || !isCoordinateValid(to)) return false;
        // Check whether the player piece is selected
        if (board[from.y][from.x] != players[currentPlayer]) return false;
        // Check whether piece can move
        if (!canMove(from)) return false;
        // Check whether the move spot is empty
        if (board[to.y][to.x] != emptySpot) return false;
        // If this is a jump, check whether end coordinates are correct
        if (canJump(from) && from.y + 2 * yDirection == to.y && xDistance * xDistance == 4) return true;
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
                if (players[currentPlayer] == board[i][j] && canMove(new Coordinate(j, i))) return true;
            }
        }
        return true;
    }

    /**
     * Moves the given checker to a new position. Will do nothing if the positions are invalid, so be careful.
     * @param from the coordinate of the chosen piece
     * @param to the coordinate of the spot to be moved to
     */
    public void move(Coordinate from, Coordinate to) {
        // If move is invalid, do not move
        if (!isMoveValid(from, to)) return;
        // Move the piece and make the spot it left a space
        board[to.y][to.x] = board[from.y][from.x];
        board[from.y][from.x] = emptySpot;
        // If this was a jump, clear the enemy piece
        if (canJump(from)) {
            int yDirection = (currentPlayer == 0) ? -1 : 1;
            int xDirection = (from.x > to.x) ? -1 : 1;
            board[from.y + yDirection][from.x + xDirection] = emptySpot;
        }
        // If the piece cannot jump again, go to the next player
        if (!canJump(to))
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
