package main.java.io.github.gotonode.compress.app;

import main.java.io.github.gotonode.compress.algorithms.Huffman.Huffman;
import main.java.io.github.gotonode.compress.algorithms.LZW.LZW;
import main.java.io.github.gotonode.compress.enums.Algorithms;
import main.java.io.github.gotonode.compress.enums.Commands;
import main.java.io.github.gotonode.compress.io.IO;
import main.java.io.github.gotonode.compress.ui.UiController;

import java.io.File;
import java.util.Arrays;

/**
 * This is a non-static class which acts as the "engine" for the project. It is created and called in the Main-class.
 */
public class App {

    private final UiController uiController;
    private final IO io;
    private boolean appRunning;

    public App(UiController uiController, IO io) {
        this.uiController = uiController;
        this.io = io;
    }

    public void run() {
        appRunning = true;

        uiController.printGreetings();
        uiController.printUrl();

        uiController.printEmptyLine();
        uiController.printInstructions();

        // Get a list of available commands from the enum.
        Character[] availableCommands = Arrays.stream(Commands.values()).map(a -> a.getCommand()).toArray(Character[]::new);

        // This is the main loop for the app. It won't break until explicitly told to do so via the EXIT-command.
        while (appRunning) {

            uiController.printEmptyLine();

            // Ask the user for a single character. It'll always return a valid, uppercase character.
            char character = uiController.askForCharacter(availableCommands, "Command");

            // This line of code returns a "Commands" enum, as dictated by "character". For an example, 'L' returns
            // a Commands.LIST and so forth.
            Commands command = Arrays.stream(Commands.values()).filter(a -> a.getCommand() == character).findFirst().get();

            switch (command) {

                case EXIT:
                    appRunning = false;
                    uiController.printGoodbye();
                    return;

                case COMPRESS_HUFFMAN:
                    processCompression(Algorithms.HUFFMAN);
                    break;

                case COMPRESS_LZW:
                    processCompression(Algorithms.LZW);
                    break;

                case BENCHMARK:
                    benchmark();
                    break;

                case DECOMPRESS:
                    processDecompression();
                    break;

                case COMMANDS:
                    uiController.printInstructions();
                    uiController.printEmptyLine();
                    break;
            }
        }

    }

    private void processCompression(Algorithms algorithm) {
        uiController.printUsing(algorithm);

        File sourceFile = io.askForSourceFile(uiController);

        if (sourceFile == null) {
            uiController.printFileError();
            return;
        }

        File targetFile = io.askForTargetFile(uiController);

        if (sourceFile.equals(targetFile)) {
            uiController.printFilesCannotBeTheSame();
            return;
        }

        if (targetFile.exists() && !targetFile.canWrite()) {
            uiController.printCannotWrite();
            return;
        }

        // At this point, we have an input file we can read, and an output file we can write to.

        switch (algorithm) {

            case HUFFMAN:
                Huffman huffman = new Huffman(sourceFile, targetFile);
                huffman.compress();
                break;
            case LZW:
                LZW lzw = new LZW(sourceFile, targetFile);
                lzw.compress();
                break;
        }
    }

    /**
     * Asks the user for the input and output files, then decompresses the input file into the output location. The
     * used algorithm is determined automatically.
     */
    private void processDecompression() {

        File sourceFile = io.askForSourceFile(uiController);

        if (sourceFile == null) {
            uiController.printFileError();
            return;
        }

        File targetFile = io.askForTargetFile(uiController);

        if (targetFile.exists() && !targetFile.canWrite()) {
            uiController.printCannotWrite();
            return;
        }

        // At this point, we have an input file we can read, and an output file we can write to.

        uiController.printDecompressionSuccessful(targetFile.getName());
    }

    /**
     * Benchmarks Huffman against LZW. Only asks for the source file, since the files created during the benchmark
     * are placed in a temporary folder, and removed once the benchmarking is complete. Finally, reports on the
     * benchmark (in milliseconds, and in kilobytes).
     */
    private void benchmark() {

        uiController.printBenchmarking();

        File sourceFile = io.askForSourceFile(uiController);

        if (sourceFile == null) {
            uiController.printFileError();
            return;
        }
    }

}
