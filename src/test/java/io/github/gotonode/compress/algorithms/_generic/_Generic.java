package io.github.gotonode.compress.algorithms._generic;

import java.io.*;
import java.util.Random;

import org.apache.commons.io.FileUtils;

public class _Generic {

    // You can change this if you want. A seed should be used so the created files
    // are deterministic in a "random" way. What this means is that each time
    // the code is run, you'd get the exact same generated files.
    public static final int RANDOM_SEED = 1337;

    public static final int FILE_SIZE = 8192;

    private Random random;

    private final int size = FILE_SIZE;

    public _Generic() {
        random = new Random(RANDOM_SEED);
    }

    /**
     * This method generates a deterministic TXT (plaintext) file.
     * @param path Where the new file will be created.
     * @throws IOException If buffers don't work.
     */
    public void generateTextFile(File path) throws IOException {

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path.getAbsolutePath()));

        for (int i = 0; i < size; i++) {
            bufferedWriter.write(getRandomChar());
        }

        bufferedWriter.close();
    }

    /**
     * This method generates a deterministic binary file.
     * @param path Where the new file will be created.
     * @throws IOException If buffers don't work.
     */
    public void generateBinaryFile(File path) throws IOException {

        FileOutputStream fileOutputStream = new FileOutputStream(path);

        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);

        for (int i = 0; i < size; i++) {
            bufferedOutputStream.write(getRandomByte());
        }

        bufferedOutputStream.close();
    }

    private int getRandomByte() {
        return random.nextInt();
    }

    private char getRandomChar() {

        double d = random.nextDouble();

        if (d < 0.2d) {
            return ' ';
        } else if (d >= 0.2d && d <= 0.22d) {
            return '.';
        } else {
            return (char)(random.nextInt(26) + 'a');
        }
    }

    public boolean checkIdenticalFiles(File file1, File file2) throws IOException {
        return FileUtils.contentEquals(file1, file2);
    }

}
