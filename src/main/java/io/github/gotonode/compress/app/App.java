package main.java.io.github.gotonode.compress.app;

import main.java.io.github.gotonode.compress.algorithms.Huffman;
import main.java.io.github.gotonode.compress.algorithms.LZW;
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
                    compress(Algorithms.HUFFMAN);
                    break;

                case COMPRESS_LZW:
                    compress(Algorithms.LZW);
                    break;

                case BENCHMARK:
                    benchmark();
                    break;

                case DECOMPRESS:
                    decompress();
                    break;

                case COMMANDS:
                    uiController.printInstructions();
                    uiController.printEmptyLine();
                    break;
            }
        }

    }

    private void compress(Algorithms algorithm) {
        uiController.printUsing(algorithm);

        File sourceFile = io.askForAndFindSourceFile(uiController);
        if (sourceFile == null) {
            return;
        }

        uiController.printAcknowledgeFile(sourceFile.getAbsolutePath());

        File targetFile = io.askForAndDeleteAndUseTargetFile(uiController);

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
     * Benchmarks Huffman against LZW. Only asks for the source file, since the files created during the benchmark
     * are placed in a temporary folder, and removed once the benchmarking is complete.
     */
    private void benchmark() {
         uiController.printBenchmarking();
         File sourceFile = io.askForAndFindSourceFile(uiController);
    }

    private void decompress() {

        File sourceFile = io.askForAndFindSourceFile(uiController);

        if (sourceFile == null) {
            return;
        }

        uiController.printAcknowledgeFile(sourceFile.getAbsolutePath());

        File targetFile = io.askForAndDeleteAndUseTargetFile(uiController);

        uiController.printDecompressionSuccessful(targetFile.getName());
    }

}
