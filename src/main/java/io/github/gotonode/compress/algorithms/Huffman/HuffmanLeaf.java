package io.github.gotonode.compress.algorithms.Huffman;

/**
 * A leaf in the HuffmanTree contains a character (char primitive type)
 * and the frequency (use count) of that character.
 *
 * For an example, in the word "gotonode", a leaf with the character 'o'
 * would have a frequency of 3, since that string has three of those characters.
 */
public class HuffmanLeaf extends HuffmanTree {

    private final char value;

    public HuffmanLeaf(int freq, char value) {
        super(freq);

        this.value = value;
    }

    public char getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "HuffmanLeaf{" +
                "value=" + value +
                '}';
    }
}
