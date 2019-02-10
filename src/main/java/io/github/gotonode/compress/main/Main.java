package io.github.gotonode.compress.main;

import io.github.gotonode.compress.app.App;
import io.github.gotonode.compress.io.IO;
import io.github.gotonode.compress.ui.UiController;

import java.util.Scanner;

/**
 * This is the main class for the program. It has a static function that will be called
 * when the program starts. This static function creates a non-static (instanced)
 * object which runs the actual engine.
 * <p>
 * NOTICE: This file is ignored in code coverage.
 */
public class Main {

    // This is the name. Not to be confused with the UNIX tool that shares the same name.
    public static final String APP_NAME = "Compress";

    // Newest version can be found here.
    public static final String APP_URL = "https://github.com/gotonode/compress/";

    // Per week numbering. So week 3 will have a version number of 3 and so forth.
    public static final int APP_VERSION = 5;

    // We're using the Extended ASCII alphabet here (256 instead of 128).
    public static final int ALPHABET_SIZE = 256;

    // You can change these, but usually there's no need unless you want to open
    // Huffman coded files that had these the other way around.
    public static final int LEFT_TREE_BINARY_VALUE = 0;
    public static final int RIGHT_TREE_BINARY_VALUE = 1;

    // Used by the binary write and read tools. Preferably do not change this value.
    public static final int BITS_IN_A_BYTE = 8;

    // Pretty self-explanatory.
    public static final int BYTES_IN_A_KILOBYTE = 1024;

    // We're using a codeword of size 12, as that's the most common one.
    public static final int CODEWORD_WIDTH = 12;

    // We'll use 2 to the power of CODEWORD_WIDTH codewords in total (2^12 = 4096).
    public static final int CODEWORD_COUNT = (int) Math.pow(2, CODEWORD_WIDTH);

    // Set to true to enable verbose logging to console. Will slow things down!
    public static final boolean DEBUG = false;

    // Make it look nice! Or disable console colors if they don't work.
    public static final boolean CONSOLE_COLORS = true;

    // These codes are added at the beginning of compressed files, and checked
    // when decompressing a file. If the file chosen for decompression doesn't
    // start with either of these, an error is thrown.
    public static final int HUFFMAN_CODE = 0xAAAAAAAA; // ‭2863311530‬
    public static final int LZW_CODE = 0xBBBBBBBB; // ‭3149642683‬

    // Multiply the size of the priority queue by this amount, and also
    // divide the size with the same amount. Usually no need to change.
    public static final int PRIORITY_QUEUE_SCALE_FACTOR = 2;

    /**
     * This is the main entry point for the program.
     *
     * @param args Command-line arguments. Currently not used.
     */
    public static void main(String[] args) {

        // Where to read user input from.
        Scanner scanner = new Scanner(System.in);

        // This class handles reading from and writing to the console.
        UiController uiController = new UiController(scanner);

        if (args.length > 0) {
            String argsAsString = java.util.Arrays.toString(args);
            argsAsString = argsAsString.substring(1, argsAsString.length() - 1).trim();
            uiController.printArgumentsNotSupported(argsAsString);
            uiController.printEmptyLine();
        }

        // IO handles basic input/output, such as file processing.
        IO io = new IO();

        // Create the App-object.
        App app = new App(uiController, io);

        // Launch the actual application.
        app.run();

        // Once this location has been reached, the program has terminated.
        System.exit(0);
    }

    /**
     * Used to return the app's name and version.
     *
     * @return A String, containing the app's name and version.
     */
    @Override
    public String toString() {
        return APP_NAME + " (version " + APP_VERSION + ")";
    }
}
