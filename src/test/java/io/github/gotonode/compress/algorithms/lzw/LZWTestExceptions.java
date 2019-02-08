package io.github.gotonode.compress.algorithms.lzw;

import io.github.gotonode.compress._generic._Generic;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

public class LZWTestExceptions {

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

    @Test(expected = NullPointerException.class)
    public void outputFileIsNullTest() {
        new LZW(inputFile, null).compress();
    }

    @Test(expected = NullPointerException.class)
    public void inputFileIsNullTest() {

        File outputFile = new File(tempFolder.getRoot() + "/lzw.COMPRESSED");

        new LZW(null, outputFile).compress();
    }

}
