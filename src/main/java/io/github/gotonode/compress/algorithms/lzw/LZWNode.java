package io.github.gotonode.compress.algorithms.lzw;

/**
 * Represents a node in the LZW compression scheme.
 *
 * Each node will contain a character, an integer value and a left child
 * node, a middle child node, and a right child node.
 *
 * Please refer to {@link LZWTree} for more information.
 */
class LZWNode {

    // The purpose of this node is to house this character.
    private char character;

    // A tertiary search tree uses three child nodes, not just two
    // like the binary search tree does.
    private LZWNode leftNode;
    private LZWNode middleNode;
    private LZWNode rightNode;

    // Used by LZW to arrange things.
    private int value;

    // This class only contains getters and setters, and those don't get their own Javadoc
    // comments. This is on purpose.

    char getCharacter() {
        return character;
    }

    void setCharacter(char character) {
        this.character = character;
    }

    LZWNode getLeftNode() {
        return leftNode;
    }

    void setLeftNode(LZWNode leftNode) {
        this.leftNode = leftNode;
    }

    LZWNode getMiddleNode() {
        return middleNode;
    }

    void setMiddleNode(LZWNode middleNode) {
        this.middleNode = middleNode;
    }

    LZWNode getRightNode() {
        return rightNode;
    }

    void setRightNode(LZWNode rightNode) {
        this.rightNode = rightNode;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
