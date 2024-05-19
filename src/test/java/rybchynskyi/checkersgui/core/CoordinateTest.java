package rybchynskyi.checkersgui.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoordinateTest {
    @Test
    void parse1a() {
        Coordinate expected = new Coordinate(7, 0);
        assertEquals(expected, Coordinate.parse("1a"));
    }

    @Test
    void parse8h() {
        Coordinate expected = new Coordinate(0, 7);
        assertEquals(expected, Coordinate.parse("8h"));
    }

    @Test
    void parse4c() {
        Coordinate expected = new Coordinate(4, 2);
        assertEquals(expected, Coordinate.parse("4c"));
    }

    @Test
    void failParse10z() {
        assertThrows(IllegalArgumentException.class, () -> Coordinate.parse("10z"));
    }

    @Test
    void failParse9a() {
        assertThrows(IllegalArgumentException.class, () -> Coordinate.parse("9a"));
    }

    @Test
    void failParse4o() {
        assertThrows(IllegalArgumentException.class, () -> Coordinate.parse("4o"));
    }
}