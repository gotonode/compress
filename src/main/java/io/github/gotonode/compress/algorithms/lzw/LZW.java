package io.github.gotonode.compress.algorithms.lzw;

import io.github.gotonode.compress.algorithms.CompressAlgorithm;

import java.io.File;

/**
 * This class contains my personal Lempel–Ziv–Welch implementation.
 * <p>
 * It has been created by following the definition from its respective Wikipedia article and other online sources.
 * <p>
 * This section will eventually contain more info.
 * <p>
 * Sources:
 * <p>
 * • <a href="https://en.wikipedia.org/wiki/Lempel%E2%80%93Ziv%E2%80%93Welch">Lempel–Ziv–Welch</a> (Wikipedia)
 *
 * @author gotonode (github.com/gotonode)
 */
public class LZW implements CompressAlgorithm {

    private final File source;
    private final File target;

    /**
     * Creates a new LZW object. This is used to compress/decompress a file using LZW.
     *
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
