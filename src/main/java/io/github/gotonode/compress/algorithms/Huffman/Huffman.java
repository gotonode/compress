package io.github.gotonode.compress.algorithms.Huffman;

import io.github.gotonode.compress.algorithms.CompressAlgorithm;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * This class contains my personal Huffman coding implementation.
 * <p>
 * This section will eventually contain more info.
 * <p>
 * It has been created by following the definition from its respective Wikipedia article and other online sources.
 * <p>
 * Sources:
 * - <a href="https://en.wikipedia.org/wiki/Huffman_coding">Huffman coding</a> (Wikipedia)
 *
 * @author gotonode (github.com/gotonode)
 */
public class Huffman implements CompressAlgorithm {

    private final File source;
    private final File target;
    private HashMap<String, Character> mapInverse;
    private HashMap<Character, String> map;

    private ArrayList<Result> results;

    /**
     * Creates a new Huffman object. This is used to compress/decompress a file using Huffman coding.
     *
     * @param source The file to be compressed/decompressed.
     * @param target The file to write the results of the compression/decompression to.
     */
    public Huffman(File source, File target) {

        this.source = source;
        this.target = target;

        results = new ArrayList<>();

        map = new HashMap<>();
        mapInverse = new HashMap<>();
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

    private HuffmanTree buildHuffmanTree(int[] frequencies) {

        PriorityQueue<HuffmanTree> forest = new PriorityQueue<>();

        for (int i = 0; i < frequencies.length; i++) {
            if (frequencies[i] > 0) {
                // Now we have some copies of this specific character.
                forest.offer(new HuffmanLeaf(frequencies[i], (char) i));
            }
        }

        while (forest.size() > 1) {
            HuffmanTree alpha = forest.poll();
            HuffmanTree beta = forest.poll();

            assert (alpha != null && beta != null);
            forest.offer(new HuffmanNode(alpha, beta));
        }

        return forest.poll();
    }

    // TODO: REMOVE THIS WHEN DONE!
    public void runTest(String s) {

        int[] frequencies = new int[256];

        char[] charArray = s.toCharArray();

        for (char character : charArray) {
            frequencies[character]++;
        }

        HuffmanTree tree = buildHuffmanTree(frequencies);
        traverse(tree, new StringBuffer());

        System.out.println();

        StringBuilder out = new StringBuilder();

        for (char c : charArray) {
            out.append(map.get(c));
        }

        System.out.println(out);

        Collections.sort(results);
        Collections.reverse(results);

        printTable();

        String decodedString = decode(out.toString());

        System.out.println(decodedString);
    }

    private String decode(String s) {

        char[] chars = s.toCharArray();
        StringBuilder output = new StringBuilder();
        int pos = 0;

        while (pos < chars.length) {
            String key = "";
            while (!(mapInverse.containsKey(key))) {
                key += chars[pos];
                pos++;
            }
            output.append(mapInverse.get(key));
        }

        return output.toString();
    }

    private void printTable() {
        for (Result result : results) {
            System.out.println(result);
        }
    }

    private void traverse(HuffmanTree huffmanTree, StringBuffer stringBuffer) {

        if (huffmanTree instanceof HuffmanLeaf) {

            // This is a safe cast since the type is always the correct one.
            HuffmanLeaf huffmanLeaf = (HuffmanLeaf) huffmanTree;

            map.put(huffmanLeaf.getValue(), stringBuffer.toString());
            mapInverse.put(stringBuffer.toString(), huffmanLeaf.getValue());

            results.add(new Result(huffmanLeaf.getValue(), huffmanLeaf.getFreq(), stringBuffer.toString()));

        } else if (huffmanTree instanceof HuffmanNode) {

            // Same here, this is a safe cast. It should never cause an exception.
            HuffmanNode huffmanNode = (HuffmanNode) huffmanTree;

            stringBuffer.append('0');
            traverse(huffmanNode.getLeftTree(), stringBuffer);
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);

            stringBuffer.append('1');
            traverse(huffmanNode.getRightTree(), stringBuffer);
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);

        } else {
            throw new IllegalArgumentException("This method was called with an illegal type of HuffmanTree.");
        }
    }
}

class HuffmanLeaf extends HuffmanTree {

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

class HuffmanNode extends HuffmanTree {

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

abstract class HuffmanTree implements Comparable<HuffmanTree> {

    private final int freq;

    /**
     * This package-private constructor creates a new HuffmanTree object.
     *
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
     *
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

