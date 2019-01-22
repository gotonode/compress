package main.java.io.github.gotonode.compress.algorithms;

import java.io.File;

/**
 * The Huffman coding is implemented here.
 */
public class Huffman implements CompressAlgorithms {

    private final File source;
    private final File target;


    /**
     * Creates a new Huffman object. This is used to compress/decompress a file using Huffman coding.
     * @param source The file to be compressed/decompressed.
     * @param target The file to write the results of the compression/decompression to.
     */
    public Huffman(File source, File target) {
        this.source = source;
        this.target = target;
    }

    @Override
    public String toString() {
        return "Huffman{" +
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
