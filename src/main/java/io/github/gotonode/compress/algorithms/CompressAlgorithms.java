package main.java.io.github.gotonode.compress.algorithms;

public interface CompressAlgorithms {

    /**
     * Compresses the input file into the output location.
     */
    void compress();

    /**
     * Decompresses the input file into the output location.
     */
    void decompress();

    /**
     * This has to implemented. It'll contain the name of the algorithm as well as the short names
     * (e.g. "nice_picture.jpeg") for the input and output files.
     */
    String toString();
}
