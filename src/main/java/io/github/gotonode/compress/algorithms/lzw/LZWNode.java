package io.github.gotonode.compress.algorithms.lzw;

class LZWNode {

    private char character;
    private LZWNode leftNode;
    private LZWNode middleNode;
    private LZWNode rightNode;

    private int value;

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
