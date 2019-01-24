package io.github.gotonode.compress.main;

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
    public void appNameTest() {
        String name = Main.APP_NAME;
        assertEquals(name, "Compress");
    }

    /**
     * This test will cause an error if the Main class somehow can't be created.
     */
    @Test
    public void mainTest() {
        Main main = new Main();
        assertEquals(main.toString(), "Compress");
    }
}
