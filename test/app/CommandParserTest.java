package app;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CommandParserTest {

    @Test
    void testLigneNullRetourneNull() {
        assertNull(CommandParser.parse(null));
    }

    @Test
    void testLigneVideRetourneNull() {
        assertNull(CommandParser.parse(""));
        assertNull(CommandParser.parse("   "));
    }

    @Test
    void testCommentaireSeulRetourneNull() {
        assertNull(CommandParser.parse("# juste un commentaire"));
    }

    @Test
    void testCommandeSimple() {
        Command c = CommandParser.parse("exit");
        assertEquals("exit", c.getName());
        assertTrue(c.getStrs().isEmpty());
        assertTrue(c.getInts().isEmpty());
    }

    @Test
    void testPointAvecCoords() {
        Command c = CommandParser.parse("point 10 20");
        assertEquals("point", c.getName());
        assertEquals(0, c.getStrs().size());
        assertEquals(2, c.getInts().size());
        assertEquals(10, c.getInts().get(0));
        assertEquals(20, c.getInts().get(1));
    }

    @Test
    void testListShapes() {
        Command c = CommandParser.parse("list shapes");
        assertEquals("list", c.getName());
        assertEquals(1, c.getStrs().size());
        assertEquals("shapes", c.getStrs().get(0));
        assertEquals(0, c.getInts().size());
    }

    @Test
    void testNormalisationLowercase() {
        Command c = CommandParser.parse("POINT 5 6");
        assertEquals("point", c.getName());
        assertEquals(5, c.getInts().get(0));
    }

    @Test
    void testCommentaireEnFin() {
        Command c = CommandParser.parse("point 1 2 # un point");
        assertEquals("point", c.getName());
        assertEquals(2, c.getInts().size());
    }

    @Test
    void testEspacesMultiples() {
        Command c = CommandParser.parse("point   1    2");
        assertEquals(2, c.getInts().size());
    }

    @Test
    void testEntierNegatif() {
        Command c = CommandParser.parse("point -5 10");
        assertEquals(-5, c.getInts().get(0));
        assertEquals(10, c.getInts().get(1));
    }

    @Test
    void testMatches() {
        Command c = CommandParser.parse("rectangle 0 0 10 5");
        assertTrue(c.matches(0, 4));
        assertFalse(c.matches(0, 3));
        assertFalse(c.matches(1, 4));
    }
}
