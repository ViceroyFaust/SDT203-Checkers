package rybchynskyi.checkersgui.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveTest {
    @Test
    void testParse3a4b() {
        Move expected = new Move(new Coordinate(5, 0), new Coordinate(4, 1));
        assertEquals(expected, Move.parseMove("3a-4b"));
    }

    @Test
    void testParse1a8h() {
        Move expected = new Move(new Coordinate(7, 0), new Coordinate(0, 7));
        assertEquals(expected, Move.parseMove("1a-8h"));
    }

    @Test
    void failParse0a10b() {
        assertThrows(IllegalArgumentException.class, () -> Coordinate.parse("0a-10b"));
    }

    @Test
    void failParse3a4i() {
        assertThrows(IllegalArgumentException.class, () -> Coordinate.parse("3a-4i"));
    }

    @Test
    void failParse3z4b() {
        assertThrows(IllegalArgumentException.class, () -> Coordinate.parse("3z-4b"));
    }
}