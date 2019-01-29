package io.github.gotonode.compress.algorithms.huffman;

import io.github.gotonode.compress.algorithms.CompressAlgorithm;
import io.github.gotonode.compress.io.BinaryReadTool;
import io.github.gotonode.compress.io.BinaryWriteTool;
import io.github.gotonode.compress.main.Main;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
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

    private BinaryWriteTool binaryWriteTool;
    private BinaryReadTool binaryReadTool;

    /**
     * Creates a new Huffman object. This is used to compress/decompress a file using Huffman coding.
     *
     * @param source The file to be compressed/decompressed.
     * @param target The file to write the results of the compression/decompression to.
     */
    public Huffman(File source, File target) {

        this.source = source;
        this.target = target;
        try {
            this.binaryReadTool = new BinaryReadTool(source);
            this.binaryWriteTool = new BinaryWriteTool(target);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public String toString() {
        return "Huffman{" +
                "source=" + source +
                ", target=" + target +
                '}';
    }

    /**
     * Compresses the input file and produces the output file. Please
     * refer to the comments in this method for a more in-depth look.
     *
     * @return True if the operation succeeded, false otherwise.
     */
    @Override
    public boolean compress() {

        String input = null;
        try {
            input = binaryReadTool.readData();
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }

        int dataLength = input.length();

        if (dataLength <= 0) {
            throw new RuntimeException("That file was empty. Try another one?");
        }

        // Write a bit to indicate that this file is Huffman coded.
        try {
            binaryWriteTool.writeZeroBit();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return false;
        }

        // Write the length of the data into the output file. This
        // is measured in bytes (8 bits).
        try {
            binaryWriteTool.writeInt(dataLength);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return false;
        }

        char[] characters = input.toCharArray();

        if (Main.DEBUG) {
            System.out.println("Current characters:");
            System.out.println(Arrays.toString(characters));
        }

        int[] weights = new int[Main.ALPHABET_SIZE];

        for (char character : characters) {
            // For each character with the same code, we'll increase
            // its weight.
            weights[character]++;
        }

        // We'll build and assign the Huffman root node. This will
        // also contain the entire tree, traversable from the root.
        HuffmanNode rootHuffmanNode = buildTree(weights);

        if (Main.DEBUG) {
            System.out.println("Root Huffman node:");
            System.out.println(rootHuffmanNode);
        }

        // This will build the table used to find the binary representation
        // (in String format) quickly by means of the array's index.
        String[] table = new String[Main.ALPHABET_SIZE];

        buildTable(table, rootHuffmanNode, "");

        if (Main.DEBUG) {
            for (int i = 0; i < table.length; i++) {
                String representation = table[i];
                if (representation != null) {
                    // Example: 65=1010
                    System.out.println(i + "=" + representation);
                }
            }
        }

        // Print the binary tree to the output file. This is
        // later used to decompress the file. Adds overhead,
        // so smaller files might actually get bigger after they
        // have been compressed through Huffman.
        try {
            writeTree(rootHuffmanNode);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return false;
        }

        // Iterate over each character in the input data, looking up the binary
        // representation of it and writing it to the output stream.
        //
        // For an example, if the first character in the data is a 'A', which has
        // a code of 65, we'll look up the binary representation from the String array
        // at index 65, and get a 1010 (demonstration value only), and then write
        // the bits out to the output file.
        for (int index = 0; index < dataLength; index++) {

            // The current character from the input stream.
            char character = characters[index];

            // The binary representation of the current character. This value
            // varies from file to file, and is based on the character's respective
            // weights in the data.
            String binaryRepresentation = table[character];

            // Loop through the binary representation of the character and write
            // it to the output stream, one bit at a time (the stream is buffered).
            for (int indexInner = 0; indexInner < binaryRepresentation.length(); indexInner++) {
                char currentChar = binaryRepresentation.charAt(indexInner);

                try {
                    if (currentChar == '0') {
                        // Writes a zero into the output stream.
                        binaryWriteTool.writeZeroBit();
                    } else { // It's not a '0' so it's a '1'.
                        // Writes a one into the output stream.
                        binaryWriteTool.writeOneBit();
                    }
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                    return false;
                }
            }
        }

        try {
            // Write the bits to the file and close the stream. It cannot be reused.
            binaryWriteTool.flushAndClose();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return false;
        }

        // The compression operation succeeded, so a true is returned.
        return true;
    }

    @Override
    public boolean decompress() {

        // Read the first bit in. We don't do anything with it here. It
        // was used to determine what algorithm was used to compress this
        // file.
        try {
            binaryReadTool.readBool();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // We'll read (as a 32-bit integer) the data area's length from
        // the file. A compressed file always begins with this byte.
        Integer dataLength = null;

        try {
            dataLength = binaryReadTool.readInt();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        HuffmanNode huffmanRootNode = null;

        // After the data length, we'll read the actual Huffman tree
        // from the compressed file.
        try {
            huffmanRootNode = readTreeFromFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        // Now that we have the tree, we can start decompressing the
        // the data and writing the characters to output as we go.
        try {
            for (int i = 0; i < dataLength; i++) {
                // Start from the root node.
                HuffmanNode huffmanNode = huffmanRootNode;

                // Loop until we reach a leaf.
                while (!huffmanNode.isLeafNode()) {
                    if (binaryReadTool.readBool()) {
                        // Move to the right subnode.
                        huffmanNode = huffmanNode.getRightNode();
                    } else {
                        // Move to the left subnode.
                        huffmanNode = huffmanNode.getLeftNode();
                    }
                }

                binaryWriteTool.write(huffmanNode.getValue());

            }
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }

        try {
            binaryWriteTool.flushAndClose();
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }

        // At this point, everything went well and we can return
        // a true boolean value to mark the success.
        return true;
    }

    /**
     * This recursive method reads binary data from the input file and constructs a
     * Huffman tree based on it. First bit tells if it's a leaf node or not, then
     * we'll read additional data such as the character itself.
     * @return The fully constructed Huffman tree.
     * @throws IOException If there's an error reading or constructing the tree.
     */
    private HuffmanNode readTreeFromFile() throws IOException {

        // The first bit tells if this is a leaf or not.
        boolean leafNode = binaryReadTool.readBool();

        if (leafNode) {
            char character = binaryReadTool.readChar();
            HuffmanNode huffmanNode = new HuffmanNode(character, null);
            return huffmanNode;
        } else {
            HuffmanNode huffmanNode = new HuffmanNode(null, null, readTreeFromFile(), readTreeFromFile());
            return huffmanNode;
        }
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

            HuffmanNode newNode = new HuffmanNode(null, combinedNodeWeight, leftNode, rightNode);

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
     * 65 = 1010 (ASCII code for the character 'A' is 65)
     * 66 = 101111 (ASCII code the the character 'B' is 66)
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
            // 'A' at position 65 of the array of Strings might get "1010" as
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

    /**
     * This recursive method builds a binary representation of the
     * Huffman tree and writes it at the beginning of the output file.
     *
     * @param rootHuffmanNode The root node from which traversal will begin.
     */
    private void writeTree(HuffmanNode rootHuffmanNode) throws IOException {

        if (rootHuffmanNode.isLeafNode()) {
            // We reached a leaf, meaning it has no child nodes.

            char character = rootHuffmanNode.getValue();
            int charCode = (int)character; // For debugging.

            // Writes a '1'.
            binaryWriteTool.writeOneBit();

            // Writes the current character's code.
            binaryWriteTool.write(charCode);

        } else {
            // Writes a '0'.
            binaryWriteTool.writeZeroBit();

            // Recursively traverse the tree, always starting from the
            // left child node of the current node.
            writeTree(rootHuffmanNode.getLeftNode());
            writeTree(rootHuffmanNode.getRightNode());
        }
    }

}

