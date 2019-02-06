package io.github.gotonode.compress.app;

import io.github.gotonode.compress.algorithms.huffman.Huffman;
import io.github.gotonode.compress.algorithms.lzw.LZW;
import io.github.gotonode.compress.enums.Algorithms;
import io.github.gotonode.compress.enums.Commands;
import io.github.gotonode.compress.io.BinaryReadTool;
import io.github.gotonode.compress.io.IO;
import io.github.gotonode.compress.main.Main;
import io.github.gotonode.compress.ui.UiController;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * This is a non-static class which acts as the "engine" for the project. It is created and called in the Main-class.
 */
public class App {

    private final UiController uiController;
    private final IO io;
    private boolean appRunning;

    /**
     * Initializes a new App-object. This is the engine for the project.
     *
     * @param uiController This controller is used for UI input and output.
     * @param io           The IO module to be attached.
     */
    public App(UiController uiController, IO io) {
        this.uiController = uiController;
        this.io = io;
    }

    /**
     * Actually run the app. Once execution returns from this method, the app will exit.
     *
     * Contains a permanent loop that runs until the user has asked the app to exit.
     */
    public void run() {
        appRunning = true;

        uiController.printGreetings();
        uiController.printUrl();

        uiController.printEmptyLine();
        uiController.printInstructions();

        // Get a list of available commands from the enum.
        Character[] availableCommands = Arrays.stream(
                Commands.values()).map(Commands::getCommand).toArray(Character[]::new);

        // This is the main loop for the app. It won't break until explicitly told to do so via the EXIT-command.
        while (appRunning) {

            uiController.printEmptyLine();

            // Ask the user for a single character. It'll always return a valid, uppercase character.
            char character = uiController.askForCharacter(availableCommands, "Command");

            // This line of code returns a "Commands" enum, as dictated by "character". For an example, 'L' returns
            // a Commands.LIST and so forth.
            Commands command = Arrays.stream(Commands.values())
                    .filter(a -> a.getCommand() == character)
                    .findFirst()
                    .get();

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
                    break;

                default:
                    throw new IllegalArgumentException();
            }
        }

    }

    /**
     * Asks the user for the input and output files, then compresses the input file into the output location. The
     * used algorithm is specified as a parameter.
     */
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

        long current = System.currentTimeMillis();

        switch (algorithm) {

            case HUFFMAN:
                Huffman huffman = new Huffman(sourceFile, targetFile);
                huffman.compress();
                break;
            case LZW:
                LZW lzw = new LZW(sourceFile, targetFile);
                lzw.compress();
                break;

            default:
                throw new IllegalArgumentException();
        }

        long next = System.currentTimeMillis();

        long time = next - current;

        uiController.printCompressionSuccessful(algorithm, targetFile.getName());

        long sourceFileSize = sourceFile.length() / 1024; // In kilobytes.
        long targetFileSize = targetFile.length() / 1024;

        double difference = calculateDifference(sourceFileSize, targetFileSize);

        uiController.printDifference(sourceFileSize, targetFileSize, difference);

        uiController.printOperationTime(time);
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

        boolean algorithmBitCode = false;
        int decompressedDataLength = 0;

        try {
            BinaryReadTool binaryReadTool = new BinaryReadTool(sourceFile);
            algorithmBitCode = binaryReadTool.readBool();
            decompressedDataLength = binaryReadTool.readInt();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Algorithms algorithm;

        if (algorithmBitCode) {
            algorithm = Algorithms.LZW;
        } else {
            algorithm = Algorithms.HUFFMAN;
        }

        uiController.printAlgorithmDetected(algorithm);
        uiController.printDecompressedDataLength(decompressedDataLength / Main.BITS_IN_A_KILOBYTE);

        File targetFile = io.askForTargetFile(uiController);

        if (targetFile.exists() && !targetFile.canWrite()) {
            uiController.printCannotWrite();
            return;
        }

        // At this point, we have an input file we can read, and an output file we can write to.

        long current = System.currentTimeMillis();

        switch (algorithm) {

            case HUFFMAN:
                Huffman huffman = new Huffman(sourceFile, targetFile);
                huffman.decompress();
                break;
            case LZW:
                LZW lzw = new LZW(sourceFile, targetFile);
                lzw.decompress();
                break;
            default:
                throw new IllegalArgumentException();
        }

        long next = System.currentTimeMillis();

        long time = next - current;

        uiController.printDecompressionSuccessful(algorithm, targetFile.getName());

        long sourceFileSize = sourceFile.length() / Main.BITS_IN_A_KILOBYTE;
        long targetFileSize = targetFile.length() / Main.BITS_IN_A_KILOBYTE;

        double difference = calculateDifference(sourceFileSize, targetFileSize);

        uiController.printDifference(sourceFileSize, targetFileSize, difference);
        uiController.printOperationTime(time);
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

        uiController.printEmptyLine();

        long[] huffmanBenchmarkResults = Benchmark.runBenchmark(sourceFile, Algorithms.HUFFMAN);
        long[] lzwBenchmarkResults = Benchmark.runBenchmark(sourceFile, Algorithms.LZW);

        long huffmanCompressionTime = huffmanBenchmarkResults[0];
        long lzwCompressionTime = lzwBenchmarkResults[0];

        long huffmanDecompressionTime = huffmanBenchmarkResults[1];
        long lzwDecompressionTime = lzwBenchmarkResults[1];

        double compressionDifference = calculateDifference(huffmanCompressionTime, lzwCompressionTime);
        double decompressionDifference = calculateDifference(huffmanDecompressionTime, lzwDecompressionTime);

        uiController.printCompressionResultsHeader();

        uiController.printCompressionBenchmarkResults(Algorithms.HUFFMAN, huffmanCompressionTime);
        uiController.printCompressionBenchmarkResults(Algorithms.LZW, lzwCompressionTime);

        if (huffmanCompressionTime < lzwCompressionTime) {
            uiController.printCompressionTimeWinner(Algorithms.HUFFMAN);
        } else if (huffmanCompressionTime > lzwCompressionTime) {
            uiController.printCompressionTimeWinner(Algorithms.LZW);
        } else {
            uiController.printEqualCompressionTime();
        }

        uiController.printEmptyLine();

        uiController.printDecompressionResultsHeader();

        uiController.printDecompressionBenchmarkResults(Algorithms.HUFFMAN, huffmanDecompressionTime);
        uiController.printDecompressionBenchmarkResults(Algorithms.LZW, lzwDecompressionTime);

        if (huffmanDecompressionTime < lzwDecompressionTime) {
            uiController.printDecompressionTimeWinner(Algorithms.HUFFMAN);
        } else if (huffmanDecompressionTime > lzwDecompressionTime) {
            uiController.printDecompressionTimeWinner(Algorithms.LZW);
        } else {
            uiController.printEqualDecompressionTime();
        }

        uiController.printEmptyLine();

        long originalFileSize = sourceFile.length() / Main.BITS_IN_A_KILOBYTE;

        long huffmanCompressedFileSize = huffmanBenchmarkResults[2] / Main.BITS_IN_A_KILOBYTE;
        long lzwCompressedFileSize = lzwBenchmarkResults[2] / Main.BITS_IN_A_KILOBYTE;

        double huffmanDifference = calculateDifference(huffmanCompressedFileSize, originalFileSize);
        double lzwDifference = calculateDifference(lzwCompressedFileSize, originalFileSize);

        uiController.printOriginalFileSize(originalFileSize);

        uiController.printCompressedFileSize(Algorithms.HUFFMAN, huffmanCompressedFileSize, huffmanDifference);
        uiController.printCompressedFileSize(Algorithms.LZW, lzwCompressedFileSize, lzwDifference);

        if (huffmanDifference < lzwDifference) {
            uiController.printCompressionSizeWinner(Algorithms.HUFFMAN);
        } else if (huffmanDifference > lzwDifference) {
            uiController.printCompressionSizeWinner(Algorithms.LZW);
        } else {
            uiController.printEqualCompressionSize();
        }
    }

    /**
     * Simply calculates the difference of the two files, as a percentage.
     *
     * @param size1 First file's size.
     * @param size2 The other file's size.
     * @return The percentage difference (as a double).
     */
    private double calculateDifference(long size1, long size2) {

        long bigger = Math.max(size1, size2);
        long smaller = Math.min(size1, size2);

        return (smaller * 100.0d) / bigger;
    }

}
