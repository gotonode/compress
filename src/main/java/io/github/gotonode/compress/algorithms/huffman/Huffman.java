package io.github.gotonode.compress.algorithms.huffman;

import io.github.gotonode.compress.algorithms.CompressAlgorithm;
import io.github.gotonode.compress.io.BinaryReadTool;
import io.github.gotonode.compress.io.BinaryWriteTool;
import io.github.gotonode.compress.main.Main;
import io.github.gotonode.compress.ui.UiController;
import io.github.gotonode.compress.utils.MinQueue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * This class contains my personal Huffman coding implementation.
 * <p>
 * It has been created by following the definition from its respective Wikipedia article and other online sources.
 * <p>
 * Huffman works by counting the instances of specific characters from the data. It
 * then creates a tree structure that maps each character as a sequence of bits. Characters
 * that appear more frequently get a shorter bit string representation, and
 * uncommon/rare characters get a longer string.
 * <p>
 * Usually, each character is encoded with either 8 or 16 bits. For an example,
 * the character 'A' is 01000001. Huffman coding can encode 'A' to be 01 or 0110
 * for an example, potentially saving a lot of space.
 * <p>
 * Uses {@link HuffmanNode} internally.
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
        } catch (IOException ex) {
            UiController.printErrorMessage(ex);
            return;
        }

        try {
            this.binaryWriteTool = new BinaryWriteTool(target);
        } catch (FileNotFoundException ex) {
            UiController.printErrorMessage(ex);
            return; // Find a way to inform the user about a failed initialization.
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

        int dataLength = 0;

        // Write an integer to indicate that this file is Huffman coded.
        try {
            binaryWriteTool.writeInt(Main.HUFFMAN_CODE);
        } catch (IOException ex) {
            UiController.printErrorMessage(ex);
            return false;
        }

        int[] weights = new int[Main.ALPHABET_SIZE];

        // While the stream (file) has data to read, read one character
        // at a time, increase its weight by one and update how
        // many bytes we have read thus far.
        while (binaryReadTool.streamHasData()) {

            try {
                char character = binaryReadTool.readChar();

                weights[character]++;

                dataLength++; // One more byte was read in.

            } catch (IOException ex) {
                UiController.printErrorMessage(ex);
                return false;
            }
        }

        // Write the length of the data into the output file. This
        // is measured in bytes (8 bits).
        try {
            binaryWriteTool.writeInt(dataLength);
        } catch (IOException ex) {
            UiController.printErrorMessage(ex);
            return false;
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
            UiController.printErrorMessage(ex);
            return false;
        }

        try {
            binaryReadTool.reset();
        } catch (IOException ex) {
            UiController.printErrorMessage(ex);
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
            char character = 0;
            try {
                character = binaryReadTool.readChar();
            } catch (IOException ex) {
                UiController.printErrorMessage(ex);
                return false;
            }

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
                    UiController.printErrorMessage(ex);
                    return false;
                }
            }
        }

        try {
            // Write the bits to the file and close the stream. It cannot be reused.
            binaryWriteTool.flushAndClose();
            binaryReadTool.close();
        } catch (IOException ex) {
            UiController.printErrorMessage(ex);
            return false;
        }

        // The compression operation succeeded, so a true is returned.
        return true;
    }

    @Override
    public boolean decompress() {

        // Read the first integer in. It is used to determine what algorithm
        // was used to compress this file.
        int code;

        try {
            code = binaryReadTool.readInt();
        } catch (IOException ex) {
            UiController.printErrorMessage(ex);
            return false;
        }

        // If the identification 32-bit integer is not what we'd expect for this algorithm.
        if (code != Main.HUFFMAN_CODE) {
            throw new RuntimeException("Corrupted file.");
        }

        // We'll read (as a 32-bit integer) the data area's length from
        // the file. A compressed file always has this byte.
        Integer dataLength = null;

        try {
            dataLength = binaryReadTool.readInt();
        } catch (IOException ex) {
            UiController.printErrorMessage(ex);
            return false;
        }

        HuffmanNode huffmanRootNode = null;

        // After the data length, we'll read the actual Huffman tree
        // from the compressed file.
        try {
            huffmanRootNode = readTreeFromFile();
        } catch (IOException ex) {
            UiController.printErrorMessage(ex);
            return false;
        }

        // Now that we have the tree, we can start decompressing the
        // data and writing the characters to output as we go.
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
            UiController.printErrorMessage(ex);
            return false;
        }

        try {
            binaryWriteTool.flushAndClose();
            binaryReadTool.close();
        } catch (IOException ex) {
            UiController.printErrorMessage(ex);
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
     *
     * @return The fully constructed Huffman tree.
     * @throws IOException If there's an error reading or constructing the tree.
     */
    private HuffmanNode readTreeFromFile() throws IOException {

        // The first bit tells if this is a leaf or not.
        boolean leafNode = binaryReadTool.readBool();

        if (leafNode) {
            char character = binaryReadTool.readChar();
            return new HuffmanNode(character, null);
        } else {
            return new HuffmanNode(null, null, readTreeFromFile(), readTreeFromFile());
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

        // This is my own minimum priority queue.
        MinQueue<HuffmanNode> nodes = new MinQueue<>();

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
        while (nodes.getSize() > 1) {

            // The ordering here is important! Do not swap the right node
            // in front of the left node.
            HuffmanNode leftNode = nodes.poll(); // Has a binary code of 0.
            HuffmanNode rightNode = nodes.poll(); // Has a binary code of 1.

            // These are separated for easier debugging.
            int leftNodeWeight = leftNode.getWeight();
            int rightNodeWeight = rightNode.getWeight();

            int combinedNodeWeight = leftNodeWeight + rightNodeWeight;

            HuffmanNode newNode = new HuffmanNode(null, combinedNodeWeight, leftNode, rightNode);

            // The newly created tree ("node"), which has combined the two previous trees
            // is added to the queue.
            nodes.offer(newNode);
        }

        // We only have 1 node left, which is the root node.
        return nodes.poll();
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
            int charCode = (int) character; // For debugging.

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

