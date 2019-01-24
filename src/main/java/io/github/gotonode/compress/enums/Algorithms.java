package io.github.gotonode.compress.enums;

/**
 * All of the used algorithms are listed here.
 */
public enum Algorithms {

    HUFFMAN("Huffman"), LZW("Lempel–Ziv–Welch");

    private final String name;

    Algorithms(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
