package rybchynskyi.checkersgui.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheckersLogicTest {

    CheckersLogic game;

    @BeforeEach
    void init() {
        game = new CheckersLogic();
    }

    @Test
    void simpleSimulationTest() {
        String moves[] = {
                "3a-4b",
                "6d-5c",
                "4b-6d",
                "7c-5e",
                "2b-3a",
                "7e-6d",
                "3a-4b",
                "5e-4f",
                "3g-5e",
                "5e-7c"
        };
        assertDoesNotThrow(() -> {
            for (String move : moves) {
                game.move(Move.parseMove(move));
            }
        });
    }

    @Test
    void testValidMove() {
        assertTrue(game.isMoveValid(Move.parseMove("3a-4b")));
    }

    @Test
    void testInvalidMove() {
        assertFalse(game.isMoveValid(Move.parseMove("4a-5b")));
    }

    @Test
    void testGameNotOver() {
        assertFalse(game.isGameOver());
    }

    @Test
    void testInvalidMoveWhenJump() {
        String moves[] = {
                "3a-4b",
                "6d-5c",
                "4b-6d",
                "7c-5e",
                "2b-3a",
                "7e-6d",
                "3a-4b",
                "5e-4f",
                "3g-5e",
                "5e-7c"
        };
        assertDoesNotThrow(() -> {
            for (String move : moves) {
                game.move(Move.parseMove(move));
            }
        });
        assertThrows(IllegalArgumentException.class, () -> game.move(Move.parseMove("8d-7e")));
    }

    @Test
    void getCurrentPlayerString() {
        assertEquals("X", game.getCurrentPlayerString());
        game.move(Move.parseMove("3a-4b"));
        assertEquals("O", game.getCurrentPlayerString());
    }

    @Test
    void getCurrentPlayer() {
        assertEquals(Player.BLACK, game.getCurrentPlayer());
        game.move(Move.parseMove("3a-4b"));
        assertEquals(Player.WHITE, game.getCurrentPlayer());
    }

    @Test
    void getNextPlayerString() {
        assertEquals("O", game.getNextPlayerString());
        game.move(Move.parseMove("3a-4b"));
        assertEquals("X", game.getNextPlayerString());
    }

    @Test
    void getBoardString() {
        Board startBoard = new Board();
        assertEquals(startBoard.toString(), game.getBoardString());
    }

    @Test
    void getSymbol() {
        assertEquals("X", game.getSymbol(5, 0));
        assertEquals("O", game.getSymbol(1, 0));
        assertEquals("_", game.getSymbol(4, 4));
    }

    @Test
    void getMoveCount() {
        assertEquals(7, game.getMoveCount());
    }
}