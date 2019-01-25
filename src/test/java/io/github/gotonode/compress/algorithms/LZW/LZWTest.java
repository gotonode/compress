package io.github.gotonode.compress.algorithms.LZW;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class LZWTest {

    // THIS FILE IS A WORK IN PROGRESS. TESTS NON-FUNCTIONAL!
    //
    // I'll use a temporary folder to first create a deterministic file, then
    // run the algorithm to produce an output file, and reverse the action into
    // a new file and check that they are a match (no data was corrupted).

    File inputFile;
    File outputFile;

    @BeforeClass
    public static void beforeClass() throws IOException {
        TemporaryFolder tempFolder = new TemporaryFolder();
        tempFolder.create();
    }

    @Before
    public void before() {
        outputFile = new File(inputFile.getName() + ".lzw");
    }

    @Test
    public void huffmanCompressionTest() {
        File input = new File("data/lorem_ipsum.txt");
        File output = new File("data/lorem_ipsum.lzw"); // This file is ignored in source control.

        LZW lzw = new LZW(input, output);
        assertFalse(lzw.compress());
    }

    @Test
    public void huffmanDecompressionTest() {
        File input = new File("data/lorem_ipsum.lzw");
        File output = new File("data/lorem_ipsum (temp).txt"); // This file is ignored in source control.

        LZW lzw = new LZW(input, output);
        assertFalse(lzw.decompress());
    }
}
