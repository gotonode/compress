package io.github.gotonode.compress.algorithms.huffman;

/**
 * This is a node in the Huffman tree. Each node contains the character in question,
 * which is unique to the tree, the amount of times this character appears in the data
 * (its weight or frequency of appearance), and possibly left and right child nodes.
 * <p>
 * To compress data with Huffman, we start at the specific character's leaf and traverse
 * the tree up to the root and print the bits in reverse order.
 * <p>
 * To decompress data with Huffman, we start at the root, traverse to left child node
 * if the bit is 0, and traverse to right child node if the bit is 1, and once we
 * reach a leaf node, we'll output the character.
 */
public class HuffmanNode implements Comparable<HuffmanNode> {

    // The character in question. Only one node in a tree can have the same character.
    private Character value;

    // How many times this character appears in the data. Also called a frequency.
    private Integer weight;

    // If this is not a leaf node, it has both (left and right) child nodes.
    private HuffmanNode leftNode;
    private HuffmanNode rightNode;

    /**
     * Constructs a HuffmanNode. All values must be passed. Use the other constructor if
     * passed nodes should be null.
     *
     * @param value     The character in question. Unique to the tree.
     * @param weight    How many occurrences of this character are there in the data.
     * @param leftNode  A non-null left node (marked as binary 0).
     * @param rightNode A non-null right node (marked as binary 1).
     */
    HuffmanNode(Character value, Integer weight, HuffmanNode leftNode, HuffmanNode rightNode) {
        this.value = value;
        this.weight = weight;
        this.leftNode = leftNode;
        this.rightNode = rightNode;
    }

    /**
     * Constructs a HuffmanNode. All values must be passed. Use the other constructor if
     * nodes will be linked in the tree at creation time.
     *
     * @param value  The character in question. Unique to the tree.
     * @param weight How many occurrences of this character are there in the data.
     */
    HuffmanNode(char value, Integer weight) {
        this.value = value;
        this.weight = weight;
        this.leftNode = null;
        this.rightNode = null;
    }

    // Getters and setters are self-explanatory, and do not get their own Javadoc comments.

    char getValue() {
        return value;
    }

    Integer getWeight() {
        return weight;
    }

    HuffmanNode getLeftNode() {
        return leftNode;
    }

    HuffmanNode getRightNode() {
        return rightNode;
    }

    public void setValue(char value) {
        this.value = value;
    }

    void setLeftNode(HuffmanNode leftNode) {
        this.leftNode = leftNode;
    }

    void setRightNode(HuffmanNode rightNode) {
        this.rightNode = rightNode;
    }

    /**
     * Checks to see if this node has any children. If not, it is a leaf. A Huffman
     * tree is unique because all nodes either have two children or no children at all.
     *
     * @return True if this is a leaf node, false otherwise.
     */
    boolean isLeafNode() {
        return leftNode == null && rightNode == null;
    }

    /**
     * Compares two HuffmanNode-objects together. This is used by the PriorityQueue
     * structure to order nodes in order of their respective weights.
     *
     * @param huffmanNode The HuffmanNode to compare this node to.
     * @return An integer value that is either greater than zero, exactly zero
     * or less than zero.
     */
    @Override
    public int compareTo(HuffmanNode huffmanNode) {
        return this.weight - huffmanNode.getWeight();
    }
}
