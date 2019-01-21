package test.java;

import main.java.io.github.gotonode.compress.main.Main;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class MainTest {

    /**
     * A sample test.
     */
    @Test
    public void genericTest() {
        assertTrue(true);
    }

    /**
     * Tests that the app's name is correct.
     */
    @Test
    public void testAppName() {
        String name = Main.APP_NAME;
        assertEquals(name, "Compress");
    }
}
