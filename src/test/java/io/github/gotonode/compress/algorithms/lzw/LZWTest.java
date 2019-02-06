package io.github.gotonode.compress.algorithms.lzw;

import io.github.gotonode.compress.algorithms._generic._Generic;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class LZWTest {

    private static TemporaryFolder tempFolder;

    @BeforeClass
    public static void beforeClass() throws IOException {
        tempFolder = new TemporaryFolder();
        tempFolder.create();
    }

    public void huffmanCompressionTest() {
        File input = new File("data/lorem_ipsum.txt");
        File output = new File("data/lorem_ipsum.lzw"); // This file is ignored in source control.

        LZW lzw = new LZW(input, output);
        assertFalse(lzw.compress());
    }

    public void huffmanDecompressionTest() {
        File input = new File("data/lorem_ipsum.lzw");
        File output = new File("data/lorem_ipsum (temp).txt"); // This file is ignored in source control.

        LZW lzw = new LZW(input, output);
        assertFalse(lzw.decompress());
    }

    /**
     * This is a very verbose test on the LZW coding. It's now easy to
     * change the code and run this test to see if it broke it somehow.
     *
     * This is not run automatically.
     *
     * @throws IOException Only when IO fails.
     */
    @Test
    public void lzwTestVerbose() throws IOException {

        _Generic generic = new _Generic();

        System.out.println("Working directory: " + tempFolder.getRoot());

        for (int i = 0; i < 1; i++) {

            File input = new File(tempFolder.getRoot() + "/" + "textFile.txt");

            generic.generateTextFile(input);

            System.out.println("Created a deterministic TXT file to " + input.getAbsolutePath());

            File output = new File(input.getAbsolutePath() + ".lzw");

            LZW lzwCompression = new LZW(input, output);

            lzwCompression.compress();

            System.out.println("Compressed the TXT file into " + output.getAbsolutePath());

            File finalOutput = new File(input.getAbsolutePath() + ".txt");

            LZW lzwDecompression = new LZW(output, finalOutput);

            lzwDecompression.decompress();

            System.out.println("Decompressed the compressed TXT file into " + finalOutput.getAbsolutePath());

            boolean filesIdentical = generic.checkIdenticalFiles(input, finalOutput);

            System.out.print("The original and the decompressed files are: ");

            if (filesIdentical) {
                System.out.println("IDENTICAL (this is a good thing)");
            } else {
                System.out.println("DIFFERENT (something's not working right)");
            }

            assertTrue(filesIdentical);

        }
    }
}
