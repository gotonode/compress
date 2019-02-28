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
 * LZW works differently from Huffman. It creates a dictionary of strings (preferably long strings)
 * that map to binary sequences. For an example, if textual data contains the word "welcome" many
 * times, each entry of that word gets a binary representation that is shorter than the original
 * (in the optimal scenario).
 * <p>
 * Thus, LZW works best with data that has many long, repeating strings containing the same
 * data. Usually this is the case with text files, but not the case with binary files.
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

        // Contains all of the data. Not practical with larger files. If
        // a file is larger than available physical memory, virtual memory
        // is used and that means HDD/SDD reading and writing which is a
        // lot slower than pure-RAM processing.
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

        // Create the tree by first populating it with single characters
        // through the alphabet (A to Z, 0 to 9 and so forth).
        for (int index = 0; index < Main.ALPHABET_SIZE; index++) {
            lzwTree.add((char) index, index);
        }

        // These are used to test where the biggest performance gains can be gained.
        long timeSpentFetchingPrefixes = 0;
        long timeSpentWritingCodewords = 0;
        long timeSpentAddingNodesToTree = 0;
        long timeSpentSeekingForward = 0;

        long start;

        int offset = 0;
        int itemsInTree = 0;

        // Then create prefixed codewords for longer substrings. Optimally,
        // a longer substring will get a shorter key.
        while (data.length() > 0) {

            // TODO: This whole function is quite slow.

            // It contains measuring code. Go to the Main-class to turn verbose
            // debug logging on to see the results.

            // I do believe it's quite effective at what it does (in finding
            // the prefixes), but this comes at a performance/memory cost.

            // Possible remedies:
            // - empty the dictionary (tree) intermittently
            // - don't read the full data as a String
            // - read the full data, but as an array of characters
            // - write in a different loop

            // For testing purposes. Ignore the following.
            start = System.currentTimeMillis();
            // For testing purposes. Ignore the previous.

            String prefix = lzwTree.prefix(data);

            // For testing purposes. Ignore the following.
            timeSpentFetchingPrefixes += System.currentTimeMillis() - start;

            start = System.currentTimeMillis();
            // For testing purposes. Ignore the previous.

            try {
                binaryWriteTool.writeCodeword(lzwTree.get(prefix));
            } catch (IOException ex) {
                UiController.printErrorMessage(ex);
                return false;
            }

            // For testing purposes. Ignore the following.
            timeSpentWritingCodewords += System.currentTimeMillis() - start;
            // For testing purposes. Ignore the previous.

            // The following code reduces the memory usage by a wide margin, but increases
            // the time it takes to perform the compression by a lot. Thus is is disabled.
            if (false) {
                if (lzwTree.getNodes() >= 100) {
                    lzwTree = new LZWTree();
                    for (int index = 0; index < Main.ALPHABET_SIZE; index++) {
                        lzwTree.add((char) index, index);
                    }
                    prefix = "";
                }
            }

            // Cached for debugging purposes (so we don't need to constantly
            // pop into the method to fetch the value).
            int prefixLength = prefix.length();

            // For testing purposes. Ignore the following.
            start = System.currentTimeMillis();
            // For testing purposes. Ignore the previous.

            // If the prefixes length is less than of the remaining data's length,
            // we'll add it to the tree for further processing.
            if (prefixLength < data.length() && Main.CODEWORD_COUNT > endOfFile) {
                lzwTree.add(data.substring(0, prefixLength + 1), endOfFile);
                itemsInTree++;
                endOfFile++;
            }

            // For testing purposes. Ignore the following.
            timeSpentAddingNodesToTree += System.currentTimeMillis() - start;

            start = System.currentTimeMillis();
            // For testing purposes. Ignore the previous.

            // Seek forward by how much progress we just made.
            data = data.substring(prefixLength);

            // It seems that the substring-part of this loop takes about
            // 86 % of the total time. Biggest gains can be won here.

            offset += prefixLength;

            // For testing purposes. Ignore the following.
            timeSpentSeekingForward += System.currentTimeMillis() - start;
            // For testing purposes. Ignore the previous.
        }

        if (Main.DEBUG) {
            System.out.println("Time spent finding prefixes (ms): " + timeSpentFetchingPrefixes);
            System.out.println("Time spent writing codewords (ms): " + timeSpentWritingCodewords);
            System.out.println("Time spent adding nodes to tree (ms): " + timeSpentAddingNodesToTree);
            System.out.println("Time spent seeking forward (ms): " + timeSpentSeekingForward);

            long total = timeSpentAddingNodesToTree + timeSpentFetchingPrefixes
                    + timeSpentSeekingForward + timeSpentWritingCodewords;

            System.out.println("Total time spent (ms): " + total);
        }

        // Once the dictionary is done, write the ending character.
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

        System.gc();

        data = "";

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

        // Create a table of Strings that contains all of our codewords.
        String[] table = new String[Main.CODEWORD_COUNT];

        // This index is used past the following loop. That's why it's
        // defined here and not in the loop itself.
        int index;

        // Creates the dictionary (table) with single-byte characters.
        for (index = 0; index < Main.ALPHABET_SIZE; index++) {
            table[index] = String.valueOf((char) index);
        }

        // We'll start here (next step from index). Set it to empty.
        table[index++] = "";

        // Stores the codeword we're processing. Useful for debugging.
        int codeword;

        // Read in the very first codeword.
        try {
            codeword = binaryReadTool.readCodeword();
        } catch (IOException ex) {
            UiController.printErrorMessage(ex);
            return false;
        }

        // Used to hold the codeword value from the table. Updated with
        // new values from the table with added characters from the input stream.
        String value = table[codeword];

        // Read in data from the compressed file (codewords) and write them
        // to the output file. Once we have exhausted the codeword-base
        // exit the loop.
        while (true) {

            try {
                binaryWriteTool.writeString(value);
                codeword = binaryReadTool.readCodeword();
            } catch (IOException ex) {
                UiController.printErrorMessage(ex);
                return false;
            }

            // We have it all. Time to exit the loop.
            if (codeword == Main.ALPHABET_SIZE) {
                break;
            }

            // Read in the next value from the table. Store it for convenience.
            String current = table[codeword];

            // If our index matches the current codeword, prepare for
            // the value to be changed. Add the first character of it.
            if (index == codeword) {
                current = value + value.charAt(0);
            }

            // If we can, place the newly-generated value into the next
            // available slot on the table.
            if (index < Main.CODEWORD_COUNT) {
                table[index++] = value + current.charAt(0);
            }

            // Move on the process the next value, replacing the old one.
            value = current;
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
