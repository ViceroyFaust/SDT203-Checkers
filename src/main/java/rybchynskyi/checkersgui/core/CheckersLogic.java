package rybchynskyi.checkersgui.core;

import java.util.List;
import java.util.ArrayList;

/**
 * The game class. It is responsible for the logic and state of the checkers game.
 * @author Danylo Rybchynskyi
 * @version 2024-04-27
 */
public class CheckersLogic {
    private final Board gameBoard;
    private Player currentPlayer;
    private List<Coordinate> jumpPieces;
    private List<Coordinate> movePieces;
    private List<Move> moves;

    /**
     * Calculates the next player in turn
     * @return next player (also the opponent)
     */
    private Player nextPlayer() {
        return (currentPlayer == Player.BLACK) ? Player.WHITE : Player.BLACK;
    }

    /**
     * Checks whether a given coordinate is occupied by the current player
     * @param row row to check
     * @param col column to check
     * @return true - occupied by current player; false - otherwise
     */
    private boolean isOccupiedByCurrentPlayer(int row, int col) {
        return gameBoard.isOccupiedByPlayer(row, col, currentPlayer);
    }

    /**
     * Checks whether a given coordinate is occupied by the opponent player
     * @param row row to check
     * @param col column to check
     * @return true - occupied by enemy player; false - otherwise
     */
    private boolean isOccupiedByEnemy(int row, int col) {
        return gameBoard.isOccupiedByPlayer(row, col, nextPlayer());
    }

    /**
     * Returns whether there are enemy pieces in front and to the sides of the given coordinate
     * @param row player row to check
     * @param col player column to check
     * @param colDirection the direction in which to test
     * @return true - there is an enemy piece in the given direction of attack; false - otherwise
     */
    private boolean hasEnemyPiece(int row, int col, ColumnDirection colDirection) {
        int enemyRow = row + rowDirection();
        int enemyCol = col + colDirection.toInt();

        return gameBoard.isValidCoordinate(enemyRow, enemyCol) && isOccupiedByEnemy(enemyRow, enemyCol);
    }

    /**
     * Checks whether a player could capture a piece from a given coordinate in a given direction
     * @param row player row
     * @param col player column
     * @param colDirection the direction in which you would like to capture
     * @return true - the piece in the given direction can be captured; false - otherwise
     */
    private boolean canCapturePiece(int row, int col, ColumnDirection colDirection) {
        return hasEnemyPiece(row, col, colDirection) && canJump(row, col, colDirection);
    }

    /**
     * Checks whether the current player piece can jump and capture
     * @param row player piece row
     * @param col player piece column
     * @return true - the piece has an available jump capture move; false - otherwise
     */
    private boolean canJumpCapture(int row, int col) {
        if (!isOccupiedByCurrentPlayer(row, col)) return false;

        return canCapturePiece(row, col, ColumnDirection.LEFT) ||
                canCapturePiece(row, col, ColumnDirection.RIGHT);
    }

    /**
     * Checks whether a piece could jump from a given coordinate in a given direction
     * @param row row to jump from
     * @param col column to jump from
     * @param colDirection direction to jump in
     * @return true - the piece can jump in the given direction; false - otherwise
     */
    private boolean canJump(int row, int col, ColumnDirection colDirection) {
        int targetRow = row + 2 * rowDirection();
        int targetCol = col + 2 * colDirection.toInt();

        return gameBoard.isValidCoordinate(targetRow, targetCol) &&
                gameBoard.isEmpty(targetRow, targetCol) &&
                hasEnemyPiece(row, col, colDirection);
    }

    /**
     * Checks whether a valid piece from a coordinate can move forward either to the left or right
     * @param row the player piece row
     * @param col the player piece column
     * @param colDirection the column direction, left or right
     * @return true - the piece can move; false - the piece can't move
     */
    private boolean canMove(int row, int col, ColumnDirection colDirection) {
        int targetRow = row + rowDirection();
        int targetCol = col + colDirection.toInt();

        return gameBoard.isValidCoordinate(targetRow, targetCol) && gameBoard.isEmpty(targetRow, targetCol);
    }

    /**
     * Checks whether a player piece can move forward
     * @param row the player piece row
     * @param col the player piece column
     * @return true - the piece has moves available to it; false - otherwise
     */
    private boolean canMove(int row, int col) {
        if (!isOccupiedByCurrentPlayer(row, col)) return false;

        return canMove(row, col, ColumnDirection.LEFT) ||
                canMove(row, col, ColumnDirection.RIGHT);
    }

    /**
     * Calculates and populates a list with pieces that can currently jump
     */
    private void calcJumpPieces() {
        jumpPieces.clear();
        for (int i = 0; i < gameBoard.getRows(); ++i) {
            for (int j = 0; j < gameBoard.getCols(); ++j) {
                if (canJumpCapture(i, j))
                    jumpPieces.add(new Coordinate(i, j));
            }
        }
    }

    /**
     * Calculates and populates a list with pieces that can move forward
     */
    private void calcMovePieces() {
        movePieces.clear();
        for (int i = 0; i < gameBoard.getRows(); ++i) {
            for (int j = 0; j < gameBoard.getCols(); ++j) {
                if (canMove(i, j))
                    movePieces.add(new Coordinate(i, j));
            }
        }
    }

    /**
     * Returns the move representing a jump in the given direction
     * @param piece piece to jump
     * @param colDirection direction to jump in
     * @return Move representation of the jump
     */
    private Move pieceJumpMove(Coordinate piece, ColumnDirection colDirection) {
        int targetRow = piece.row() + 2 * rowDirection();
        int targetCol = piece.col() + 2 * colDirection.toInt();

        return new Move(piece, new Coordinate(targetRow, targetCol));
    }

    /**
     * Calculates moves based on jumps
     */
    private void calcJumpMoves() {
        for (Coordinate piece : jumpPieces) {
            if (canJump(piece.row(), piece.col(), ColumnDirection.LEFT))
                moves.add(pieceJumpMove(piece, ColumnDirection.LEFT));
            if (canJump(piece.row(), piece.col(), ColumnDirection.RIGHT))
                moves.add(pieceJumpMove(piece, ColumnDirection.RIGHT));
        }
    }

    /**
     * Returns the move representing a move forward in the given direction
     * @param piece piece to move
     * @param colDirection direction to move in
     * @return Move representation of the move
     */
    private Move pieceForwardMove(Coordinate piece, ColumnDirection colDirection) {
        int targetRow = piece.row() + rowDirection();
        int targetCol = piece.col() + colDirection.toInt();

        return new Move(piece, new Coordinate(targetRow, targetCol));
    }

    /**
     * Calculates moves based on jumps
     */
    private void calcMovesForward() {
        for (Coordinate piece : movePieces) {
            if (canMove(piece.row(), piece.col(), ColumnDirection.LEFT))
                moves.add(pieceForwardMove(piece, ColumnDirection.LEFT));
            if (canMove(piece.row(), piece.col(), ColumnDirection.RIGHT))
                moves.add(pieceForwardMove(piece, ColumnDirection.RIGHT));
        }
    }

    /**
     * Calculates the current moves available based on the lists of pieces that can jump or move forward
     */
    private void calcMoves() {
        moves.clear();
        calcJumpPieces();
        if (!jumpPieces.isEmpty()) {
            calcJumpMoves();
            return;
        }
        calcMovePieces();
        calcMovesForward();
    }

    /**
     * Calculates the current row direction based on the current player
     * @return -1 for player black (up the board); 1 for player white (down the board)
     */
    private int rowDirection() {
        return currentPlayer == Player.BLACK ? -1 : 1;
    }

    /**
     * Moves a single piece with a standard forward move
     * @param move the Move forward to make
     */
    private void moveForward(Move move) {
        gameBoard.movePiece(move.from().row(), move.from().col(), move.to().row(), move.to().col());
    }

    /**
     * Calculates the column direction that a move makes
     * @param move the move to make
     * @return the column direction of the move
     */
    private ColumnDirection moveColumnDirection(Move move) {
        return move.from().col() > move.to().col() ? ColumnDirection.LEFT : ColumnDirection.RIGHT;
    }

    /**
     * Moves a single piece with a jump
     * @param move the jump move
     */
    private void jump(Move move) {
        gameBoard.movePiece(move.from().row(), move.from().col(), move.to().row(), move.to().col());
        int removalRow = move.from().row() + rowDirection();
        int removalCol = move.from().col() + moveColumnDirection(move).toInt();
        gameBoard.removePiece(removalRow, removalCol);
    }

    private enum ColumnDirection {
        LEFT(-1),
        RIGHT(1);
        private final int ratio;

        ColumnDirection(int ratio) {
            this.ratio = ratio;
        }

        public int toInt() {
            return ratio;
        }
    }

    /**
     * Constructs the CheckersLogic class by initializing the game board and setting the starting player.
     * @param startingPlayer the player to move first
     */
    public CheckersLogic(Player startingPlayer) {
        gameBoard = new Board();
        currentPlayer = startingPlayer;
        jumpPieces = new ArrayList<>(12);
        movePieces = new ArrayList<>(12);
        moves = new ArrayList<>();
    }

    /**
     * Constructs the CheckersLogic class by initializing the game board and setting black as the starting player.
     */
    public CheckersLogic() {
        this(Player.BLACK);
    }

    /**
     * Checks if the given move is valid
     * @param move the move given by the user
     * @return true - move is valid; false - otherwise
     */
    public boolean isMoveValid(Move move) {
        for (Move validMove : moves) {
            if (validMove.equals(move))
                return true;
        }
        return false;
    }

    /**
     * Checks whether the game is over
     * @return true - no more moves; false - otherwise
     */
    public boolean isGameOver() {
        calcMoves();
        return moves.isEmpty();
    }

    /**
     * Moves the player according to the move provided. If it is a forward move, will move forward. If it is a jump, it
     * will jump and automatically remove any captured pieces. However, if the move is invalid, the function will throw
     * an exception.
     * @param move the move to make
     * @throws IllegalArgumentException when the move is invalid
     */
    public void move(Move move) {
        calcMoves();
        if (!isMoveValid(move)) throw new IllegalArgumentException();
        if (!jumpPieces.isEmpty()) {
            jump(move);
            if(!canJumpCapture(move.to().row(), move.to().col()))
                currentPlayer = nextPlayer();
        } else {
            moveForward(move);
            currentPlayer = nextPlayer();
        }
    }

    /**
     * Returns the string representation of the current player
     * @return String player symbol
     */
    public String getCurrentPlayerString() {
        return currentPlayer.toString();
    }

    /**
     * Returns the enum representing the current player
     * @return Player enum
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Returns the string representation of the next player
     * @return String player symbol
     */
    public String getNextPlayerString() {
        return nextPlayer().toString();
    }

    /**
     * Returns the string representation of the game board in its current state
     * @return String representing the board
     */
    public String getBoardString() {
        return gameBoard.toString();
    }

    /**
     * Returns the number of possible moves for the current player
     * @return number of moves for the current player
     */
    public int getMoveCount() {
        return moves.size();
    }

    /**
     * Returns the i'th move possible
     * @param i - the index of the possible move
     * @return the possible Move
     */
    public Move getMove(int i) {
        return moves.get(i);
    }
}
