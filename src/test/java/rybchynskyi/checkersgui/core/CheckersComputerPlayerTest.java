package rybchynskyi.checkersgui.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheckersComputerPlayerTest {

    @Test
    void calculateMove() {
        CheckersLogic game = new CheckersLogic();
        CheckersComputerPlayer player = new CheckersComputerPlayer(game);
        assertDoesNotThrow(player::calculateMove);
        assertNotEquals(null, player.calculateMove());
    }
}