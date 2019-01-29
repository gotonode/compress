package io.github.gotonode.compress.io;

import java.io.*;

public class BinaryReadTool {

    private BufferedInputStream bufferedInputStream;
    private int freeSlots = 0; // How much space is left.
    private int bitBuffer = 0; // Bits waiting to be flushed.

    public BinaryReadTool(File file) throws FileNotFoundException {

        FileInputStream fileInputStream = new FileInputStream(file);

        bufferedInputStream = new BufferedInputStream(fileInputStream);
    }
}
