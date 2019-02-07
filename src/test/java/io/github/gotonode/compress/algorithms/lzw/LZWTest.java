package io.github.gotonode.compress.algorithms.lzw;

import io.github.gotonode.compress._generic._Generic;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.*;

public class LZWTest {

    private File inputFile;
    private static TemporaryFolder tempFolder;
    private _Generic _generic;

    @BeforeClass
    public static void beforeClass() throws IOException {
        tempFolder = new TemporaryFolder();
        tempFolder.create();
    }

    @Before
    public void before() throws IOException {
        _generic = new _Generic();
        if (inputFile == null) {
            inputFile = _generic.generateBinaryFile(tempFolder.getRoot() + "/lzw.bin");
        }
    }

    @Test
    public void lzwNodeNotNullTest() {
        LZWNode lzwNode = new LZWNode();
        assertNotNull(lzwNode);
    }

    @Test
    public void lzwTreeValueTest() {
        LZWTree lzwTree = new LZWTree();
        lzwTree.add('a', 0);
        int value = lzwTree.get("a");
        assertEquals(0, value);
    }

    @Test
    public void lzwTreePrefixTest() {
        LZWTree lzwTree = new LZWTree();
        lzwTree.add("a", 0);
        String prefix = lzwTree.prefix("a");
        assertEquals("a", prefix);
    }

    @Test
    public void lzwTreeZeroLengthNullTest() {
        LZWTree lzwTree = new LZWTree();
        assertNull(lzwTree.prefix(""));
    }

    @Test
    public void lzwTreeEmptyTest() {
        LZWTree lzwTree = new LZWTree();
        assertEquals(-1, lzwTree.get("a"));
    }

    @Test
    public void lzwTreeForestTest() {
        LZWTree lzwTree = new LZWTree();

        Character firstChar = null;

        for (int i = 0; i < 1000; i++) {
            char c = _generic.getRandomChar();

            if (firstChar == null) {
                firstChar = c;
            }

            lzwTree.add(c, _generic.getRandomInt());
        }

        String prefix = lzwTree.prefix(firstChar.toString());

        assertEquals(1, prefix.length());
    }

    @Test
    public void lzwToStringTest() {
        File outputFile = new File(tempFolder.getRoot() + "/lzw.COMPRESSED");
        LZW lzw = new LZW(inputFile, outputFile);
        assertFalse(lzw.toString().isEmpty());
    }

    @Test
    public void lzwNodeToStringTest() {
        LZWNode lzwNode = new LZWNode();
        assertFalse(lzwNode.toString().isEmpty());
    }

    @Test
    public void lzwTreeToStringTest() {
        LZWTree lzwTree = new LZWTree();
        assertFalse(lzwTree.toString().isEmpty());
    }

    @Test
    public void lzwCompressionTest() {
        File outputFile = new File(tempFolder.getRoot() + "/lzw.COMPRESSED");

        LZW lzw = new LZW(inputFile, outputFile);

        boolean result = lzw.compress();

        assertTrue(result);
    }

    @Test
    public void lzwDecompressionTest() {
        File compressedFile = getCompressedFile();

        File outputFile = new File(tempFolder.getRoot() + "/lzw.DECOMPRESSED");

        LZW lzw = new LZW(compressedFile, outputFile);

        boolean result = lzw.decompress();

        assertTrue(result);
    }

    /**
     * This private method returns a LZW-compressed file for use in tests.
     *
     * @return The compressed file.
     */
    private File getCompressedFile() {
        File outputFile = new File(tempFolder.getRoot() + "/lzw.COMPRESSED");

        LZW lzw = new LZW(inputFile, outputFile);

        lzw.compress();

        return outputFile;
    }
}
