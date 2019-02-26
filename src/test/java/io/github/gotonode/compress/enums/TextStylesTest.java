package io.github.gotonode.compress.enums;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TextStylesTest {

    private final String whiteBold = "\u001B[1m";
    private final String reset = "\u001b[0m";
    private final String magenta = "\u001b[35m";
    private final String red = "\u001b[31m";

    @Test
    public void textStylesImportantText() {

        String commandText = TextStyles.importantText('X');
        assertEquals(whiteBold + "X" + reset, commandText);

        commandText = TextStyles.importantText(0);
        assertEquals(whiteBold + "0" + reset, commandText);

        // This test does not use assertEquals because some locales
        // might use a comma as the decimal separator.
        commandText = TextStyles.importantText(0.0d);
        assertFalse(commandText.isEmpty());

        commandText = TextStyles.importantText(0L);
        assertEquals(whiteBold + "0" + reset, commandText);
    }

    @Test
    public void textStylesCommandText() {
        String commandText = TextStyles.commandText('X');
        assertEquals(magenta + "X" + reset, commandText);
    }

    @Test
    public void textStylesTitleText() {
        String commandText = TextStyles.titleText("TITLE");
        assertEquals(red + "TITLE" + reset, commandText);
    }

    @Test
    public void textStylesHuffmanAlgoText() {
        String commandText = TextStyles.algorithmText(Algorithms.HUFFMAN, true);
        assertEquals(whiteBold + Algorithms.HUFFMAN.getName() + reset, commandText);

        commandText = TextStyles.algorithmText(Algorithms.HUFFMAN, false);
        assertEquals(whiteBold + Algorithms.HUFFMAN.name() + reset, commandText);
    }

    @Test
    public void textStylesLzwAlgoText() {
        String commandText = TextStyles.algorithmText(Algorithms.LZW, true);
        assertEquals(whiteBold + Algorithms.LZW.getName() + reset, commandText);

        commandText = TextStyles.algorithmText(Algorithms.LZW, false);
        assertEquals(whiteBold + Algorithms.LZW.name() + reset, commandText);
    }
}
