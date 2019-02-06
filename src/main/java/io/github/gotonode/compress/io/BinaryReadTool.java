package io.github.gotonode.compress.io;

import io.github.gotonode.compress.main.Main;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * This binary reading tool is used by both Huffman and LZW to read in
 * bytes from the stream (the file).
 *
 * Helper methods have been created to read the specified amount of
 * data at once (1 bit, 32 bits, 1 byte etc).
 */
public class BinaryReadTool {

    private BufferedInputStream bufferedInputStream;
    private int slots; // How much space is usable.
    private int bitBuffer; // Bits in the buffer.
    private File file;

    /**
     * Instantiates this object.
     *
     * @param file The file we'll be reading from. This is stored.
     * @throws IOException If the file is not found (amongst other things).
     */
    public BinaryReadTool(File file) throws IOException {
        this.file = file;
        reset();
    }

    /**
     * Starts from the beginning, and resets the streams.
     *
     * @throws IOException On IO error.
     */
    public void reset() throws IOException {
        slots = 0;
        bitBuffer = 0;

        // Can this be done without creating the objects anew?
        FileInputStream fileInputStream = new FileInputStream(file);
        bufferedInputStream = new BufferedInputStream(fileInputStream);

        fill();
    }

    /**
     * Fills the buffer.
     *
     * @throws IOException If we can't read from the stream.
     */
    private void fill() throws IOException {
        bitBuffer = bufferedInputStream.read();
        slots = Main.BITS_IN_A_BYTE;
    }

    /**
     * Reads a boolean value (a single bit, either 0 or 1).
     *
     * @return The boolean, representing the bit.
     * @throws IOException If we can't read from the stream.
     */
    public boolean readBool() throws IOException {

        slots--;

        boolean bit = ((bitBuffer >> slots) & 1) == 1;

        if (slots == 0) {
            fill();
        }

        return bit;
    }

    /**
     * Reads an 8-bit character from the stream.
     *
     * @return The next 8-bit character from the stream.
     * @throws IOException If we can't read from the stream.
     */
    public char readChar() throws IOException {

        if (slots == Main.BITS_IN_A_BYTE) {

            // We can return the char directly.

            int temp = bitBuffer;

            // Fill in the buffer before we continue.
            fill();

            return (char) (temp & 0xff);

        } else {

            // Need to pad the character a little bit, so it's 8 bits.

            int temp = bitBuffer;

            // Use bitwise operations (shift left).

            temp <<= Main.BITS_IN_A_BYTE - slots;

            int oldUsableSlots = slots;

            // Fill in the buffer before we continue.
            fill();

            slots = oldUsableSlots;

            temp |= bitBuffer >>> slots;

            return (char) (temp & 0xff);
        }
    }

    /**
     * Reads an integer (32 bit) from the stream.
     *
     * @return The next integer.
     * @throws IOException If we can't read from the stream.
     */
    public int readInt() throws IOException {

        int output = 0;

        for (int i = 0; i < 4; i++) {

            char character = readChar();

            // Bitwise operations to get a 32-bit integer. We'll
            // iterate over this 4 times, and there are 8 bits in a byte
            // so we'll end up with a 32-bit (4 * 8 = 32) integer value.

            output <<= Main.BITS_IN_A_BYTE;

            output |= character;
        }

        return output;
    }

    /**
     * Reads a codeword of the specified length (12-bit) from the stream.
     *
     * Used by LZW, and not used by Huffman.
     *
     * @return The codeword as an integer (this is a representation).
     * @throws IOException If we can't read from the stream.
     */
    public int readCodeword() throws IOException {

        int output = 0;

        for (int i = 0; i < Main.CODEWORD_WIDTH; i++) {

            output <<= 1;

            if (readBool()) {
                output |= 1;
            }
        }

        return output;
    }

    public String readData() throws IOException {
        String output = "";

        // Read characters until the buffer is emptied out.
        while (bitBuffer != -1) {
            char character = readChar();
            output += character;
        }

        return output;
    }

    /**
     * Checks if the stream still has data available for us.
     *
     * @return True if there's bytes to be read; false otherwise.
     */
    public boolean streamHasData() {
        return bitBuffer != -1;
    }

    public void close() throws IOException {
        bufferedInputStream.close();
    }
}
