package io.github.gotonode.compress.algorithms.huffman;

import io.github.gotonode.compress.algorithms._generic._Generic;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static junit.framework.TestCase.assertTrue;

public class HuffmanTest {

    private static TemporaryFolder tempFolder;

    @BeforeClass
    public static void beforeClass() throws IOException {
        tempFolder = new TemporaryFolder();
        tempFolder.create();
    }

//    public void huffmanCompressionTest() {
//        File input = new File("data/lorem_ipsum.txt");
//        File output = new File("data/lorem_ipsum.huffman"); // This file is ignored in source control.
//
//        Huffman huffman = new Huffman(input, output);
//        boolean a = huffman.compress();
//        assertTrue(a);
//    }
//
//    public void huffmanDecompressionTest() {
//        File input = new File("data/lorem_ipsum.huffman");
//        File output = new File("data/lorem_ipsum (temp).txt"); // This file is ignored in source control.
//
//        Huffman huffman = new Huffman(input, output);
//        assertTrue(huffman.decompress());
//    }

    @Test
    public void huffmanCompressionTest() throws IOException {
        _Generic generic = new _Generic();
        File input = new File(tempFolder.getRoot() + "/" + "textFile.txt");
        generic.generateTextFile(input);
        System.out.println("Wrote a deterministic TXT file to " + input.getAbsolutePath());

        File output = new File(input.getAbsolutePath() + ".huffman");

        Huffman huffman = new Huffman(input, output);

        System.out.println("Compressing the TXT file into " + output.getAbsolutePath());

        boolean result = huffman.compress();

        assertTrue(result);
    }

    /**
     * This is a very verbose test on the Huffman coding.
     * @throws IOException Only when IO fails somehow.
     */
    @Test
    public void huffmanTestVerbose() throws IOException {

        for (int i = 0; i < 10000; i++) {

            _Generic generic = new _Generic();

            //System.out.println("Working directory: " + tempFolder.getRoot());

            File input = new File(tempFolder.getRoot() + "/" + "textFile.txt");

            generic.generateBinaryFile(input);

            //System.out.println("Created a deterministic TXT file to " + input.getAbsolutePath());

            File output = new File(input.getAbsolutePath() + ".huffman");

            Huffman huffmanCompression = new Huffman(input, output);

            huffmanCompression.compress();

            //System.out.println("Compressed the TXT file into " + output.getAbsolutePath());

            File finalOutput = new File(input.getAbsolutePath() + ".txt");

            Huffman huffmanDecompression = new Huffman(output, finalOutput);

            huffmanDecompression.decompress();

            //System.out.println("Decompressed the compressed TXT file into " + finalOutput.getAbsolutePath());

            boolean filesIdentical = generic.checkIdenticalFiles(input, finalOutput);

            //System.out.print("The original and the decompressed files are: ");

            if (filesIdentical) {
                //System.out.println("IDENTICAL (this is a good thing)");
            } else {
                //System.out.println("DIFFERENT (something's not working right)");
            }

            assertTrue(filesIdentical);

        }
    }
}
