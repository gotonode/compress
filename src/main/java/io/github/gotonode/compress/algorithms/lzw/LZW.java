package io.github.gotonode.compress.algorithms.lzw;

import io.github.gotonode.compress.algorithms.CompressAlgorithm;
import io.github.gotonode.compress.io.BinaryReadTool;
import io.github.gotonode.compress.io.BinaryWriteTool;
import io.github.gotonode.compress.main.Main;
import io.github.gotonode.compress.ui.UiController;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * This class contains my personal Lempel–Ziv–Welch implementation.
 * <p>
 * It has been created by following the definition from its respective Wikipedia article and other online sources.
 * <p>
 * Uses {@link LZWNode} and {@link LZWTree} internally.
 * <p>
 * Sources:
 * • <a href="https://en.wikipedia.org/wiki/Lempel%E2%80%93Ziv%E2%80%93Welch">Lempel–Ziv–Welch</a> (Wikipedia)
 *
 * @author gotonode (github.com/gotonode)
 */
public class LZW implements CompressAlgorithm {

    private BinaryWriteTool binaryWriteTool;
    private BinaryReadTool binaryReadTool;

    private final File source;
    private final File target;

    /**
     * Creates a new LZW object. This is used to compress/decompress a file using LZW.
     *
     * @param source The file to be compressed/decompressed.
     * @param target The file to write the results of the compression/decompression to.
     */
    public LZW(File source, File target) {
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
            return;
        }

    }

    @Override
    public String toString() {
        return "LZW{" +
                "source=" + source +
                ", target=" + target +
                '}';
    }

    @Override
    public boolean compress() {

        // This marks the end of the file. It's the first unavailable
        // character, meaning it's alphabet + 1.
        int endOfFile = Main.ALPHABET_SIZE + 1;

        // Write an integer to indicate that this file is LZW coded.
        try {
            binaryWriteTool.writeInt(Main.LZW_CODE);
        } catch (IOException ex) {
            UiController.printErrorMessage(ex);
            return false;
        }

        String data;

        try {
            data = binaryReadTool.readData();
        } catch (IOException ex) {
            UiController.printErrorMessage(ex);
            return false;
        }

        int dataLength = data.length();

        // Write the length of the data into the output file. This
        // is measured in bytes (8 bits).
        try {
            binaryWriteTool.writeInt(dataLength);
        } catch (IOException ex) {
            UiController.printErrorMessage(ex);
            return false;
        }

        LZWTree lzwTree = new LZWTree();

        for (int index = 0; index < Main.ALPHABET_SIZE; index++) {
            lzwTree.add((char) index, index);
        }

        while (data.length() > 0) {

            String prefix = lzwTree.prefix(data);

            try {
                binaryWriteTool.writeCodeword(lzwTree.get(prefix));
            } catch (IOException ex) {
                UiController.printErrorMessage(ex);
                return false;
            }

            int temp = prefix.length();

            if (temp < data.length()) {
                if (Main.CODEWORD_COUNT > endOfFile) {
                    lzwTree.add(data.substring(0, temp + 1), endOfFile++);
                }
            }

            // Seek forward by how much progress we just made.
            data = data.substring(temp);
        }

        try {
            binaryWriteTool.writeCodeword(Main.ALPHABET_SIZE);
        } catch (IOException ex) {
            UiController.printErrorMessage(ex);
            return false;
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

        if (code != Main.LZW_CODE) {
            throw new RuntimeException("Corrupted file.");
        }

        // We'll read (as a 32-bit integer) the data area's length from
        // the file. A compressed file always has this byte. Unlike with Huffman,
        // LZW doesn't use this information. So we'll just read and
        // forget about it.
        try {
            binaryReadTool.readInt();
        } catch (IOException ex) {
            UiController.printErrorMessage(ex);
            return false;
        }

        String[] table = new String[Main.CODEWORD_COUNT];

        int index;

        for (index = 0; index < Main.ALPHABET_SIZE; index++) {
            table[index] = String.valueOf((char) index);
        }

        table[index++] = "";

        int codeword;

        try {
            codeword = binaryReadTool.readCodeword();
        } catch (IOException ex) {
            UiController.printErrorMessage(ex);
            return false;
        }

        String value = table[codeword];

        while (true) {

            try {
                binaryWriteTool.writeString(value);
                codeword = binaryReadTool.readCodeword();
            } catch (IOException ex) {
                UiController.printErrorMessage(ex);
                return false;
            }

            if (codeword == Main.ALPHABET_SIZE) {
                break;
            }

            String s = table[codeword];

            if (index == codeword) {
                s = value + value.charAt(0);
            }

            if (index < Main.CODEWORD_COUNT) {
                table[index++] = value + s.charAt(0);
            }

            value = s;
        }

        try {
            // Write the bits to the file and close the stream. It cannot be reused.
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

}
