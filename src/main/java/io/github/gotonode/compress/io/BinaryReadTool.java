package io.github.gotonode.compress.io;

import io.github.gotonode.compress.main.Main;

import java.io.*;

public class BinaryReadTool {

    private BufferedInputStream bufferedInputStream;
    private int freeSlots = 0; // How much space is left.
    private int bitBuffer = 0; // Bits waiting to be flushed.

    public BinaryReadTool(File file) throws FileNotFoundException, IOException {

        FileInputStream fileInputStream = new FileInputStream(file);

        bufferedInputStream = new BufferedInputStream(fileInputStream);

        fill();
    }

    private void fill() throws IOException {
        bitBuffer = bufferedInputStream.read();
        freeSlots = Main.BITS_IN_A_BYTE;
    }

    public boolean readBool() throws IOException {
        freeSlots--;

        boolean bit = ((bitBuffer >> freeSlots) & 1) == 1;

        if (freeSlots == 0) {
            fill();
        }

        return bit;
    }

    public char readChar() throws IOException {
        if (freeSlots == Main.BITS_IN_A_BYTE) {
            int temp = bitBuffer;
            fill();
            return (char) (temp & 0xff);
        } else {
            int temp = bitBuffer;
            temp <<= (Main.BITS_IN_A_BYTE - freeSlots);
            int oldFreeSlots = freeSlots;
            fill();
            freeSlots = oldFreeSlots;
            temp |= (bitBuffer >>> freeSlots);
            return (char)(temp & 0xff);
        }
    }

    public int readInt() throws IOException {
        int output = 0;

        for (int i = 0; i < 4; i++) {
            char character = readChar();
            output <<= Main.BITS_IN_A_BYTE;
            output |= character;
        }

        return output;
    }

    public int readCodeword() throws IOException {
        int output = 0;

        for (int i = 0; i < Main.CODEWORD_WIDTH; i++) {
            output <<= 1;
            boolean bit = readBool();
            if (bit == true) {
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
}
