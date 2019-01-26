package io.github.gotonode.compress.algorithms.huffman;

import io.github.gotonode.compress.algorithms.CompressAlgorithm;
import io.github.gotonode.compress.algorithms.huffman.HuffmanNode;

import java.io.File;

/**
 * This class contains my personal Huffman coding implementation.
 * <p>
 * This section will eventually contain more info.
 * <p>
 * It has been created by following the definition from its respective Wikipedia article and other online sources.
 * <p>
 * Sources:
 * • <a href="https://en.wikipedia.org/wiki/Huffman_coding">Huffman coding</a> (Wikipedia)
 *
 * @author gotonode (github.com/gotonode)
 */
public class Huffman implements CompressAlgorithm {

    private final File source;
    private final File target;

    /**
     * Creates a new Huffman object. This is used to compress/decompress a file using Huffman coding.
     *
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
                "source=" + source +
                ", target=" + target +
                '}';
    }

    @Override
    public boolean compress() {

        return false;
    }

    @Override
    public boolean decompress() {

        return false;
    }
}

