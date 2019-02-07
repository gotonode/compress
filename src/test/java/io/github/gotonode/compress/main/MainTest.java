package io.github.gotonode.compress.main;

import org.junit.Test;

import static org.junit.Assert.*;

public class MainTest {

    /**
     * Tests that the app's name is correct.
     */
    @Test
    public void appNameTest() {
        String name = Main.APP_NAME;
        assertEquals("Compress", name);
    }

    /**
     * This test will cause an error if the Main class somehow can't be created.
     */
    @Test
    public void mainTest() {
        Main main = new Main();
        assertNotNull(main);
    }

    @Test
    public void mainToStringTest() {
        Main main = new Main();
        assertNotNull(main.toString());
        assertFalse(main.toString().isEmpty());
    }
}
