package io.github.gotonode.compress.algorithms.Huffman;

public abstract class HuffmanTree implements Comparable<HuffmanTree> {

    private final int freq;

    /**
     * This package-private constructor creates a new HuffmanTree object.
     * @param freq This tree's frequency.
     */
    HuffmanTree(int freq) {
        this.freq = freq;
    }

    int getFreq() {
        // Optionally add a debug print here to see when this is being read.
        return this.freq;
    }

    /**
     * Compares the two trees together.
     * @param other The other tree to compare this tree to.
     * @return An integer value that is either negative (< 0.0), exactly zero (0.0), or positive (> 0.0).
     */
    @Override
    public int compareTo(HuffmanTree other) {
        return this.freq - other.getFreq();
    }

    @Override
    public String toString() {
        return "HuffmanTree{" +
                "freq=" + freq +
                '}';
    }
}
