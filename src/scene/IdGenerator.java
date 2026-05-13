package scene;

import java.util.concurrent.atomic.AtomicLong;


public final class IdGenerator {

    private static final AtomicLong counter = new AtomicLong(0);

    private IdGenerator() {
    }

    public static long next() {
        return counter.incrementAndGet();
    }

    public static void set(long value) {
        counter.set(value);
    }

    public static void reset() {
        counter.set(0);
    }
}
