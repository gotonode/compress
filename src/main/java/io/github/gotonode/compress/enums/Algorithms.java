package main.java.io.github.gotonode.compress.enums;

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
