package io.github.gotonode.compress.app;

import io.github.gotonode.compress.benchmarking.Benchmark;
import io.github.gotonode.compress.benchmarking.BenchmarkResult;
import io.github.gotonode.compress.algorithms.huffman.Huffman;
import io.github.gotonode.compress.algorithms.lzw.LZW;
import io.github.gotonode.compress.enums.Algorithms;
import io.github.gotonode.compress.enums.Commands;
import io.github.gotonode.compress.io.BinaryReadTool;
import io.github.gotonode.compress.io.IO;
import io.github.gotonode.compress.ui.UiController;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * This is a non-static class which acts as the "engine" for the project. It is created and
 * called in the Main-class.
 *
 * This is not tested as it simply asks the user for commands and executes them. Unit testing
 * is done directly without referencing this class.
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

        long sourceFileSize = sourceFile.length();
        long targetFileSize = targetFile.length();

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
        uiController.printDecompressedDataLength(decompressedDataLength);

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

        long sourceFileSize = sourceFile.length();
        long targetFileSize = targetFile.length();

        double difference = calculateDifference(sourceFileSize, targetFileSize);

        uiController.printDifference(sourceFileSize, targetFileSize, difference);
        uiController.printOperationTime(time);
    }

    /**
     * Benchmarks Huffman against LZW. Only asks for the source file, since the files created during the benchmark
     * are removed once the benchmarking is complete. Finally, reports on the benchmark (in milliseconds,
     * and in bytes).
     */
    private void benchmark() {

        uiController.printBenchmarking();

        File sourceFile = io.askForSourceFile(uiController);

        if (sourceFile == null) {
            uiController.printFileError();
            return;
        }

        long originalSize = sourceFile.length();

        uiController.printEmptyLine();

        BenchmarkResult huffmanResults = Benchmark.runBenchmark(sourceFile, Algorithms.HUFFMAN);
        BenchmarkResult lzwResults = Benchmark.runBenchmark(sourceFile, Algorithms.LZW);

        uiController.printCompressionResultsHeader();

        uiController.printCompressionBenchmarkResults(Algorithms.HUFFMAN, huffmanResults.getCompressionTime());
        uiController.printCompressionBenchmarkResults(Algorithms.LZW, lzwResults.getCompressionTime());

        if (huffmanResults.getCompressionTime() < lzwResults.getCompressionTime()) {
            uiController.printCompressionTimeWinner(Algorithms.HUFFMAN);
        } else if (huffmanResults.getCompressionTime() > lzwResults.getCompressionTime()) {
            uiController.printCompressionTimeWinner(Algorithms.LZW);
        } else {
            uiController.printEqualCompressionTime();
        }

        uiController.printEmptyLine();

        uiController.printDecompressionResultsHeader();

        uiController.printDecompressionBenchmarkResults(Algorithms.HUFFMAN, huffmanResults.getDecompressionTime());
        uiController.printDecompressionBenchmarkResults(Algorithms.LZW, lzwResults.getDecompressionTime());

        if (huffmanResults.getDecompressionTime() < lzwResults.getDecompressionTime()) {
            uiController.printDecompressionTimeWinner(Algorithms.HUFFMAN);
        } else if (huffmanResults.getDecompressionTime() > lzwResults.getDecompressionTime()) {
            uiController.printDecompressionTimeWinner(Algorithms.LZW);
        } else {
            uiController.printEqualDecompressionTime();
        }

        uiController.printEmptyLine();

        uiController.printOriginalFileSize(originalSize);

        double huffmanReduction = calculateReduction(huffmanResults.getCompressedSize(), originalSize);
        double lzwReduction = calculateReduction(lzwResults.getCompressedSize(), originalSize);

        if (huffmanReduction >= 0.0d) {
            // The Huffman-compressed file got smaller.
            uiController.printReducedFileSize(
                    Algorithms.HUFFMAN, huffmanResults.getCompressedSize(), huffmanReduction);
        } else {
            // The Huffman-compressed file came out larger than the original. Probably due to overhead.
            double huffmanIncrease = Math.abs(huffmanReduction);
            uiController.printIncreasedFileSize(
                    Algorithms.HUFFMAN, huffmanResults.getCompressedSize(), huffmanIncrease);
        }

        if (lzwReduction >= 0.0d) {
            // The LZW-compressed file got smaller.
            uiController.printReducedFileSize(Algorithms.LZW, lzwResults.getCompressedSize(), lzwReduction);
        } else {
            // The LZW-compressed file came out larger than the original. Probably due to overhead.
            double lzwIncrease = Math.abs(lzwReduction);
            uiController.printIncreasedFileSize(Algorithms.LZW, lzwResults.getCompressedSize(), lzwIncrease);
        }

        if (huffmanResults.getCompressedSize() < lzwResults.getCompressedSize()) {
            uiController.printCompressionSizeWinner(Algorithms.HUFFMAN);
        } else if (huffmanResults.getCompressedSize() > lzwResults.getCompressedSize()) {
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
    private double calculateDifference(double size1, double size2) {

        double bigger = Math.max(size1, size2);
        double smaller = Math.min(size1, size2);

        return (smaller * 100.0d) / bigger;
    }

    /**
     * Simply calculates the difference of the two files, as a percentage.
     *
     * @return The percentage difference (as a double).
     */
    private double calculateReduction(double originalFileSize, double compressedFileSize) {

        double difference = compressedFileSize - originalFileSize;

        return (difference * 100.0d) / compressedFileSize;
    }

}
