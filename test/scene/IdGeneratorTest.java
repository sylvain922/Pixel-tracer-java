package scene;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class IdGeneratorTest {

    @BeforeEach
    void resetCounter() {
        IdGenerator.reset();
    }

    @Test
    void testPremierIdEstUn() {
        assertEquals(1L, IdGenerator.next());
    }

    @Test
    void testIdsCroissants() {
        long a = IdGenerator.next();
        long b = IdGenerator.next();
        long c = IdGenerator.next();
        assertEquals(a + 1, b);
        assertEquals(b + 1, c);
    }

    @Test
    void testSetEtNext() {
        IdGenerator.set(100L);
        assertEquals(101L, IdGenerator.next());
    }

    @Test
    void testReset() {
        IdGenerator.next();
        IdGenerator.next();
        IdGenerator.reset();
        assertEquals(1L, IdGenerator.next());
    }
}
