package io.github.gotonode.compress.algorithms.huffman;

import io.github.gotonode.compress.algorithms.CompressAlgorithm;
import io.github.gotonode.compress.algorithms.huffman.HuffmanNode;
import io.github.gotonode.compress.main.Main;

import java.io.File;
import java.util.PriorityQueue;

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

    /**
     * This method constructs the Huffman tree structure. It'll return
     * the root node once the tree has been built.
     *
     * @param weights An array of integers representing each character's weights.
     * @return The root node of the newly created Huffman tree.
     */
    private HuffmanNode buildTree(int[] weights) {

        // TODO: Replace this PriorityQueue with my own implementation.
        PriorityQueue<HuffmanNode> nodes = new PriorityQueue<>();

        // Loop through the alphabet (default size is 256).
        for (char index = 0; index < Main.ALPHABET_SIZE; index++) {

            int currentWeight = weights[index];

            if (currentWeight > 0) {
                // A character of this code (index from 0) exists (at least one).
                HuffmanNode huffmanNode = new HuffmanNode(index, currentWeight);

                nodes.offer(huffmanNode);
            }
        }

        // Repeatedly combines two trees of the smallest size together, until
        // only one tree is left.
        while (nodes.size() > 1) {

            // The ordering here is important! Do not swap the right node
            // in front of the left node.
            HuffmanNode leftNode = nodes.poll(); // Has a binary code of 0.
            HuffmanNode rightNode = nodes.poll(); // Has a binary code of 1.

            // These are separated for easier debugging.
            int leftNodeWeight = leftNode.getWeight();
            int rightNodeWeight = rightNode.getWeight();

            int combinedNodeWeight = (leftNodeWeight + rightNodeWeight);

            HuffmanNode newNode = new HuffmanNode('\0', combinedNodeWeight, leftNode, rightNode);

            // The newly created tree ("node"), which has combined the two previous trees
            // is added to the queue.
            nodes.offer(newNode);
        }

        // We only have 1 node left, which is the root node.
        HuffmanNode rootNode = nodes.poll();

        return rootNode;
    }

    /**
     * This is a recursive function which builds a table (array of Strings) that indicates what
     * character is represented by what binary sequence (as a String).
     * <p>
     * Here's an example entry of the output:
     * <p>
     * 65 = 1010 (ASCII code for the character 'a' is 65)
     * 66 = 101111 (ASCII code the the character 'b' is 66)
     * <p>
     * We'll using the character's code (ASCII or UTF-8 or otherwise) to indicate the position
     * (index) in the table. Then at that position, a String value is stored. This String value
     * represents the binary sequence of 0's and 1's which is used by Huffman coding.
     *
     * @param stringArray The array of Strings we'll build up as the table.
     * @param huffmanNode The root node we'll start building the table from.
     * @param current     Holds the binary value we'll build for each character.
     */
    private void buildTable(String[] stringArray, HuffmanNode huffmanNode, String current) {
        if (huffmanNode.isLeafNode()) {
            // We are at a leaf node, so we have the binary sequence for that
            // character. We'll add it to the array of Strings. For an example, character
            // 'a' at position 65 of the array of Strings might get "1101" as
            // the string representation of the actual binary sequence.
            stringArray[huffmanNode.getValue()] = current;
        } else {
            // We'll recursively traverse the tree in order to build the table structure.
            // It's important to be uniform about whether the right or the left child node
            // will get a 0 or a 1 in binary.
            buildTable(stringArray, huffmanNode.getLeftNode(), current + Main.LEFT_TREE_BINARY_VALUE);
            buildTable(stringArray, huffmanNode.getRightNode(), current + Main.RIGHT_TREE_BINARY_VALUE);
        }
    }
}

