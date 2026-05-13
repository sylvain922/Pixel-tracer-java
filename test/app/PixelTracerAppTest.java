package app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import scene.IdGenerator;
import static org.junit.jupiter.api.Assertions.*;

class PixelTracerAppTest {

    @BeforeEach
    void resetIds() {
        IdGenerator.reset();
    }

    @Test
    void testInitCreeUneAireUnLayer() {
        PixelTracerApp app = new PixelTracerApp();
        app.init();
        assertEquals(1, app.getAreas().size());
        assertNotNull(app.getCurrentArea());
        assertNotNull(app.getCurrentLayer());
        assertNull(app.getCurrentShape());
        assertEquals(1, app.getCurrentArea().getLayers().size());
        assertSame(app.getCurrentArea().getLayers().get(0), app.getCurrentLayer());
    }

    @Test
    void testInitUtiliseDimensionsParDefaut() {
        PixelTracerApp app = new PixelTracerApp();
        app.init();
        assertEquals(PixelTracerApp.DEFAULT_WIDTH, app.getCurrentArea().getWidth());
        assertEquals(PixelTracerApp.DEFAULT_HEIGHT, app.getCurrentArea().getHeight());
    }
}
