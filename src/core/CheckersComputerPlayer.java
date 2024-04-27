package core;

import java.util.concurrent.ThreadLocalRandom;

/**
 * The abstract representation of a checkers computer player
 * @author Danylo Rybchynskyi
 * @version 2024-04-26
 */
public class CheckersComputerPlayer {
    private CheckersLogic logic;

    public CheckersComputerPlayer(CheckersLogic checkersLogic) {
        logic = checkersLogic;
    }

    /**
     * Returns a random index from 0 to an exclusive upper bound
     * @param upperBound exclusive maximum value
     * @return random index
     */
    private int randomIndex(int upperBound) {
        return ThreadLocalRandom.current().nextInt(upperBound);
    }

    /**
     * Calculates the next move to be made by the computer player
     * @return the Move
     */
    public Move calculateMove() {
        int numMoves = logic.getMoveCount();
        int moveIndex = randomIndex(numMoves);
        return logic.getMove(moveIndex);
    }
}
