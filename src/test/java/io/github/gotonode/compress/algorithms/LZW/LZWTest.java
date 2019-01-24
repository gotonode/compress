package io.github.gotonode.compress.algorithms.LZW;

import org.junit.Test;

import java.io.File;

import static junit.framework.TestCase.assertTrue;

public class LZWTest {

    @Test
    public void huffmanCompressionTest() {
        File input = new File("data/lorem_ipsum.txt");
        File output = new File("data/lorem_ipsum.lzw"); // This file is ignored in source control.

        LZW lzw = new LZW(input, output);
        assertTrue(lzw.compress());
    }

    @Test
    public void huffmanDecompressionTest() {
        File input = new File("data/lorem_ipsum.lzw");
        File output = new File("data/lorem_ipsum (temp).txt"); // This file is ignored in source control.

        LZW lzw = new LZW(input, output);
        assertTrue(lzw.decompress());
    }
}
