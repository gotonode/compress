package io.github.gotonode.compress.algorithms.huffman;

import io.github.gotonode.compress._generic._Generic;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class HuffmanTest {

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
            inputFile = _generic.generateBinaryFile(tempFolder.getRoot() + "/huffman.bin");
        }
    }

    @Test
    public void huffmanNodeValueTest() {
        HuffmanNode huffmanNode = new HuffmanNode('a', 0);
        huffmanNode.setValue('b');
        assertEquals('b', huffmanNode.getValue());
    }

    @Test
    public void huffmanNodeLeftChildTest() {

        HuffmanNode rootHuffmanNode = new HuffmanNode('a', 0);

        HuffmanNode leftHuffmanNode = new HuffmanNode('b', null);
        rootHuffmanNode.setLeftNode(leftHuffmanNode);
        assertEquals(leftHuffmanNode, rootHuffmanNode.getLeftNode());
    }

    @Test
    public void huffmanNodeRightChildTest() {

        HuffmanNode rootHuffmanNode = new HuffmanNode('a', 0);

        HuffmanNode rightHuffmanNode = new HuffmanNode('b', null);
        rootHuffmanNode.setRightNode(rightHuffmanNode);
        assertEquals(rightHuffmanNode, rootHuffmanNode.getRightNode());
    }

    @Test
    public void huffmanToStringTest() {
        File outputFile = new File(tempFolder.getRoot() + "/huffman.COMPRESSED");
        Huffman huffman = new Huffman(inputFile, outputFile);
        assertFalse(huffman.toString().isEmpty());
    }

    @Test
    public void huffmanNodeToStringTest() {
        HuffmanNode huffmanNode = new HuffmanNode('a', 0);
        assertFalse(huffmanNode.toString().isEmpty());
    }

    @Test
    public void huffmanCompressionTest() {
        File outputFile = new File(tempFolder.getRoot() + "/huffman.COMPRESSED");

        Huffman huffman = new Huffman(inputFile, outputFile);

        boolean result = huffman.compress();

        assertTrue(result);
    }

    @Test
    public void huffmanDecompressionTest() {
        File compressedFile = getCompressedFile();

        File outputFile = new File(tempFolder.getRoot() + "/huffman.DECOMPRESSED");

        Huffman huffman = new Huffman(compressedFile, outputFile);

        boolean result = huffman.decompress();

        assertTrue(result);
    }

    /**
     * This private method returns a Huffman-compressed file for use in tests.
     *
     * @return The compressed file.
     */
    private File getCompressedFile() {
        File outputFile = new File(tempFolder.getRoot() + "/huffman.COMPRESSED");

        Huffman huffman = new Huffman(inputFile, outputFile);

        huffman.compress();

        return outputFile;
    }

}
