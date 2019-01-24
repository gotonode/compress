package io.github.gotonode.compress.algorithms;

/**
 * This interface is used by both Huffman and LZW. If additional io.github.gotonode.compress.algorithms are added later, they should
 * implement this interface fully.
 */
public interface CompressAlgorithms {

    /**
     * Compresses the input file into the output location.
     * @return True if successful, false otherwise.
     */
    boolean compress();

    /**
     * Decompresses the input file into the output location.
     * @return True if successful, false otherwise.
     */
    boolean decompress();

    /**
     * This has to implemented. It'll contain the name of the algorithm as well as the short names
     * (e.g. "nice_picture.jpeg") for the input and output files.
     */
    String toString();
}
