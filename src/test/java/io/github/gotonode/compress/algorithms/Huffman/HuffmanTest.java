package io.github.gotonode.compress.algorithms.Huffman;

import org.junit.Test;

import java.io.File;

import static junit.framework.TestCase.assertTrue;

public class HuffmanTest {

    @Test
    public void huffmanCompressionTest() {
        File input = new File("data/lorem_ipsum.txt");
        File output = new File("data/lorem_ipsum.huffman"); // This file is ignored in source control.

        Huffman huffman = new Huffman(input, output);
        assertTrue(huffman.compress());
    }

    @Test
    public void huffmanDecompressionTest() {
        File input = new File("data/lorem_ipsum.huffman");
        File output = new File("data/lorem_ipsum (temp).txt"); // This file is ignored in source control.

        Huffman huffman = new Huffman(input, output);
        assertTrue(huffman.decompress());
    }
}
