package io.github.gotonode.compress.main;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class MainTest {
	
	// MORE TESTS COMING SOON! Once I've got something that can actually be tested
	// in a meaningful way. I'll expect week 4 to introduce real unit tests.

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
        assertEquals("Compress", name);
    }

    /**
     * This test will cause an error if the Main class somehow can't be created.
     */
    @Test
    public void mainTest() {
        Main main = new Main();
        assertFalse(main.toString().isEmpty());
    }
}
