package io.github.gotonode.compress.algorithms.Huffman;

public class HuffmanNode extends HuffmanTree {

    private final HuffmanTree leftTree, rightTree;

    public HuffmanNode(HuffmanTree leftTree, HuffmanTree rightTree) {

        // Always call the super first.
        super(leftTree.getFreq() + rightTree.getFreq());

        this.leftTree = leftTree;
        this.rightTree = rightTree;
    }

    public HuffmanTree getLeftTree() {
        return leftTree;
    }

    public HuffmanTree getRightTree() {
        return rightTree;
    }

    @Override
    public String toString() {
        return "HuffmanNode{" +
                "leftTree=" + leftTree +
                ", rightTree=" + rightTree +
                '}';
    }
}
