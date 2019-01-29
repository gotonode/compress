package io.github.gotonode.compress.io;

import io.github.gotonode.compress.main.Main;

import java.io.*;

public class BinaryWriteTool {

    private final int BITS_IN_A_BYTE = 8;
    private BufferedOutputStream bufferedOutputStream;
    private int freeSlots = 0; // How much space is left.
    private int bitBuffer = 0; // Bits waiting to be flushed.

    public BinaryWriteTool(File file) throws FileNotFoundException {

        FileOutputStream fileOutputStream = new FileOutputStream(file);

        bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
    }

    public void writeInt(int dataLength) throws IOException {
        writeByte((dataLength >>> 24) & 0xff);
        writeByte((dataLength >>> 16) & 0xff);
        writeByte((dataLength >>> 8) & 0xff);
        writeByte((dataLength) & 0xff);
    }

    public void writeZeroBit() throws IOException {
        writeBit(0);
    }

    public void writeOneBit() throws IOException {
        writeBit(1);
    }

    private void writeByte(int value) throws IOException {
        
        if (freeSlots == 0) {

            bufferedOutputStream.write(value);

        } else {

            for (int i = 0; i < BITS_IN_A_BYTE; i++) {

                boolean bit = ((value >> (BITS_IN_A_BYTE - i - 1)) & 1) == 1;

                if (bit) {
                    writeOneBit();
                } else {
                    writeZeroBit();
                }
            }
        }
    }

    private void writeBit(int bitValue) throws IOException {

        bitBuffer <<= 1;
        if (bitValue == 1) {
            bitBuffer |= 1;
        }

        freeSlots++;
        if (freeSlots == BITS_IN_A_BYTE) {
            clear();
        }
    }

    public void write(int value) throws IOException {

        for (int i = 0; i < Main.BITS_IN_A_BYTE; i++) {

            boolean bit = ((value >>> (Main.BITS_IN_A_BYTE - i - 1)) & 1) == 1;
            if (bit) {
                writeOneBit();
            } else {
                writeZeroBit();
            }
        }
    }

    private void clear() throws IOException {

        if (freeSlots == 0) {
            // This was causing issues. It won't do anything if its empty.
            return;
        }

        if (freeSlots > 0) {
            // Bitwise move to make it into a byte.
            bitBuffer <<= (BITS_IN_A_BYTE - freeSlots);
        }

        bufferedOutputStream.write(bitBuffer);

        freeSlots = 0;
        bitBuffer = 0;
    }

    public void flushAndClose() throws IOException {

        if (freeSlots != 0) {
            clear();
        }

        // Flushes the bits into the output.
        bufferedOutputStream.flush();

        // Closes this stream. We're done with it.
        bufferedOutputStream.close();
    }
}
