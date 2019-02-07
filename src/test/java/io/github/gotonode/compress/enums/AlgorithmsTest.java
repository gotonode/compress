package io.github.gotonode.compress.enums;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AlgorithmsTest {

    @Test
    public void algorithmsHuffmanTest() {
        Algorithms algorithms = Algorithms.HUFFMAN;
        assertEquals(Algorithms.HUFFMAN.getName(), algorithms.getName());
    }

    @Test
    public void algorithmsLzwTest() {
        Algorithms algorithms = Algorithms.LZW;
        assertEquals(Algorithms.LZW.getName(), algorithms.getName());
    }
}
