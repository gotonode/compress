package main.java.io.github.gotonode.compress.algorithms.Huffman;

import main.java.io.github.gotonode.compress.algorithms.CompressAlgorithms;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * This class contains my personal Huffman coding implementation.

 * It has been created by following the definition from its respective Wikipedia article and other online sources.
 *
 * Sources:
 * - <a href="https://en.wikipedia.org/wiki/Huffman_coding">Huffman coding</a> (Wikipedia)
 * - <a href="https://algs4.cs.princeton.edu/55compression/Huffman.java.html">Huffman.java</a> (Princeton University)
 *
 * @author gotonode (github.com/gotonode)
 */
public class Huffman implements CompressAlgorithms {

    private final File source;
    private final File target;
    private HashMap<String, Character> mapInverse;
    private HashMap<Character, String> map;

    private ArrayList<Result> results;

    /**
     * Creates a new Huffman object. This is used to compress/decompress a file using Huffman coding.
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
                forest.offer(new HuffmanLeaf(frequencies[i], (char)i));
            }
        }

        while (forest.size() > 1) {
            HuffmanTree alpha = forest.poll();
            HuffmanTree beta = forest.poll();

            assert(alpha != null && beta != null);
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

        //System.out.println(out);

        Collections.sort(results);
        Collections.reverse(results);

        printTable();

        //String decodedString = decode(out.toString());

        //System.out.println(decodedString);
    }

    private String decode(String s) {

        char[] chars = s.toCharArray();
        String output = "";
        int pos = 0;

        while (pos < chars.length){
            String key = "";
            while (!(mapInverse.containsKey(key))) {
                key += chars[pos];
                pos++;
            }
            output += mapInverse.get(key);
        }

        return output;
    }

    private void printTable() {
        for (Result result : results) {
            System.out.println(result);
        }
    }

    private void traverse(HuffmanTree huffmanTree, StringBuffer stringBuffer) {

        if (huffmanTree instanceof HuffmanLeaf) {

            // This is a safe cast since the type is always the correct one.
            HuffmanLeaf huffmanLeaf = (HuffmanLeaf)huffmanTree;

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
