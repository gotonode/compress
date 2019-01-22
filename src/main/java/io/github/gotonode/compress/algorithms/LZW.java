package main.java.io.github.gotonode.compress.algorithms;

import java.io.File;

/**
 * LZW is implemented here.
 */
public class LZW implements CompressAlgorithms {

    private final File source;
    private final File target;

    /**
     * Creates a new LZW object. This is used to compress/decompress a file using LZW.
     * @param source The file to be compressed/decompressed.
     * @param target The file to write the results of the compression/decompression to.
     */
    public LZW(File source, File target) {
        this.source = source;
        this.target = target;
    }

    @Override
    public String toString() {
        return "LZW{" +
                "source='" + source.getName() +
                "', target='" + target.getName() +
                "'}";
    }

    @Override
    public void compress() {

    }

    @Override
    public void decompress() {

    }
}
