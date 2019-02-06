package io.github.gotonode.compress.algorithms;

/**
 * This interface is used by both Huffman and LZW.
 */
public interface CompressAlgorithm {

    /**
     * Compresses the input file into the output location.
     *
     * @return True if successful, false otherwise.
     */
    boolean compress();

    /**
     * Decompresses the input file into the output location.
     *
     * @return True if successful, false otherwise.
     */
    boolean decompress();

    /**
     * This has to implemented. It'll contain the name of the algorithm as well as the short names
     * (e.g. "nice_picture.jpeg") for the input and output files. Primarily for debug purposes.
     *
     * @return Just a String is returned, and should never be null.
     */
    String toString();
}
