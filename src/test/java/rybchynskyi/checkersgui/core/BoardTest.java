package rybchynskyi.checkersgui.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    Board toTest;

    @BeforeEach
    void init() {
        toTest = new Board();
    }

    @Test
    void validCoordinates() {
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                assertTrue(toTest.isValidCoordinate(i, j));
            }
        }
    }

    @Test
    void isOccupiedBlack() {
        int occupiedCount = 0;
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if (toTest.isOccupiedByPlayer(i, j, Player.BLACK))
                    occupiedCount++;
            }
        }
        assertEquals(12, occupiedCount);
    }

    @Test
    void isOccupiedWhite() {
        int occupiedCount = 0;
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if (toTest.isOccupiedByPlayer(i, j, Player.WHITE))
                    occupiedCount++;
            }
        }
        assertEquals(12, occupiedCount);
    }

    @Test
    void removePieceValid() {
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                toTest.removePiece(i, j);
                assertTrue(toTest.isEmpty(i, j));
            }
        }
    }

    @Test
    void removePieceInvalid() {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> toTest.removePiece(-1, 10));
    }

    @Test
    void movePiece() {
        toTest.movePiece(7, 0, 4, 4);
        assertFalse(toTest.isEmpty(4, 4));
        assertTrue(toTest.isEmpty(7, 0));
    }

    @Test
    void isEmpty() {
        assertFalse(toTest.isEmpty(7, 0));
        assertTrue(toTest.isEmpty(4, 4));
    }

    @Test
    void getRows() {
        assertEquals(8, toTest.getRows());
    }

    @Test
    void getCols() {
        assertEquals(8, toTest.getRows());
    }

    @Test
    void getSymbol() {
        assertEquals("O", toTest.getSymbol(1, 0));
        assertEquals("X", toTest.getSymbol(7, 0));
        assertEquals("_", toTest.getSymbol(4, 4));
    }
}