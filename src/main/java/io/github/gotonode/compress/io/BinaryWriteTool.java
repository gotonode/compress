package io.github.gotonode.compress.io;

import io.github.gotonode.compress.main.Main;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This tool is used to write in binary data. Used by both Huffman and LZW.
 *
 * Helper methods have been created to write in the specified amount of
 * data at once (1 bit, 32 bits, 1 byte etc).
 */
public class BinaryWriteTool {

    private BufferedOutputStream bufferedOutputStream;
    private int slots; // How much space is left.
    private int bitBuffer; // Bits waiting to be flushed.

    /**
     * Instantiates this object.
     *
     * @param file The file we'll be writing to.
     * @throws FileNotFoundException If the file isn't found.
     */
    public BinaryWriteTool(File file) throws FileNotFoundException {

        FileOutputStream fileOutputStream = new FileOutputStream(file);

        bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
    }

    /**
     * Writes an integer value into the stream. Bitwise operations to make
     * it exactly 32 bits long, in case it's shorter.
     *
     * @param value The 32-bit integer to write in.
     * @throws IOException If we can't write to the stream.
     */
    public void writeInt(int value) throws IOException {
        writeByte((value >>> 24) & 0xff);
        writeByte((value >>> 16) & 0xff);
        writeByte((value >>> 8) & 0xff);
        writeByte(value & 0xff);
    }

    /**
     * Writes a 0 (false boolean) into the stream. This is buffered, and
     * may not be written when this method is invoked. We only write 8 bits
     * (a byte) at a time. Flushing is done at the end.
     *
     * @throws IOException If we can't write to the stream.
     */
    public void writeZeroBit() throws IOException {
        writeBit(0);
    }

    /**
     * Writes a 1 (true boolean) into the stream. This is buffered, and
     * may not be written when this method is invoked. We only write 8 bits
     * (a byte) at a time. Flushing is done at the end.
     *
     * @throws IOException If we can't write to the stream.
     */
    public void writeOneBit() throws IOException {
        writeBit(1);
    }

    /**
     * Writes an entire byte (8 bits) into the stream.
     *
     * @param value The byte value to be written.
     * @throws IOException If we can't write to the stream.
     */
    private void writeByte(int value) throws IOException {

        if (slots == 0) {

            // We already have exactly 8 bits, so we can write them directly.
            bufferedOutputStream.write(value);

        } else {

            // Need to do some binary magic to make it 8 bits (a byte).
            for (int i = 0; i < Main.BITS_IN_A_BYTE; i++) {

                boolean bit = ((value >> (Main.BITS_IN_A_BYTE - i - 1)) & 1) == 1;

                // Write one bit (0 or 1) at a time. Will not be written to disk
                // until flushed.
                if (bit) {
                    writeOneBit();
                } else {
                    writeZeroBit();
                }
            }
        }
    }

    /**
     * This private method does the actual writing of the bit.
     *
     * @param bitValue Either a 0 or a 1, representing the bit.
     * @throws IOException If we can't write to the stream.
     */
    private void writeBit(int bitValue) throws IOException {

        bitBuffer <<= 1;

        if (bitValue == 1) {
            bitBuffer |= 1;
        }

        slots++;

        // Clear the buffer if it's full.
        if (slots == Main.BITS_IN_A_BYTE) {
            clear();
        }
    }

    /**
     * Writes into the stream.
     *
     * @param value The value to be written.
     * @throws IOException If we can't write to the stream.
     */
    public void write(int value) throws IOException {

        // Loop through all 8 bits.
        for (int i = 0; i < Main.BITS_IN_A_BYTE; i++) {

            boolean bit = ((value >>> (Main.BITS_IN_A_BYTE - i - 1)) & 1) == 1;

            // Writes one bit at a time. Content is written to disk only
            // when it's flushed (at the end).
            if (bit) {
                writeOneBit();
            } else {
                writeZeroBit();
            }
        }
    }

    /**
     * Writes a codeword to the stream.
     *
     * @param value The codeword to write.
     * @throws IOException If we can't write to the stream.
     */
    public void writeCodeword(int value) throws IOException {

        for (int i = 0; i < Main.CODEWORD_WIDTH; i++) {

            boolean bit = ((value >>> (Main.CODEWORD_WIDTH - i - 1)) & 1) == 1;

            // Writes one bit at a time. Content is written to disk only
            // when it's flushed (at the end).
            if (bit) {
                writeOneBit();
            } else {
                writeZeroBit();
            }
        }
    }

    /**
     * Clears the buffer, adding the contents into the stream. Stream is not
     * written to disk until it is flushed (which is done at the end).
     *
     * @throws IOException If we can't write to the stream.
     */
    private void clear() throws IOException {

        if (slots == 0) {
            // This was causing issues. It shouldn't do anything if it's empty.
            return;
        }

        if (slots > 0) {
            // Bitwise move to make it into a byte.
            bitBuffer <<= Main.BITS_IN_A_BYTE - slots;
        }

        // Write the bit buffer (containing a byte) into the stream to
        // await for flushing.
        bufferedOutputStream.write(bitBuffer);

        // Reset these to zero.
        slots = 0;
        bitBuffer = 0;
    }

    /**
     * Clears the buffer (writes the bytes into the stream), flushes the stream
     * into the output file (actual disk I/O operation) and closes the streams.
     *
     * After this method has been called, this class should not be reused.
     *
     * @throws IOException If we can't write to the stream or to the file.
     */
    public void flushAndClose() throws IOException {

        // If we have stuff we haven't written to the stream yet, do
        // so before we flush everything down.
        if (slots != 0) {
            clear();
        }

        // Flushes the bits into the output.
        bufferedOutputStream.flush();

        // Closes this stream. We're done with it.
        bufferedOutputStream.close();

        // So it'll throw an error if this is tried to be reused.
        bufferedOutputStream = null;
    }

    /**
     * Writes a String into the stream. It is processed one character at a time, so this is a convenience function.
     *
     * @param value The String value to be written to the stream.
     * @throws IOException If we can't write to the stream or to the file.
     */
    public void writeString(String value) throws IOException {
        for (int i = 0; i < value.length(); i++) {
            write(value.charAt(i));
        }
    }
}
