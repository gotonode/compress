package io.github.gotonode.compress.app;

import io.github.gotonode.compress.benchmarking.Benchmark;
import io.github.gotonode.compress.benchmarking.BenchmarkResult;
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

/**
 * This is a non-static class which acts as the "engine" for the project. It is created and
 * called in the Main-class.
 * <p>
 * This is not tested as it simply asks the user for commands and executes them. Unit testing
 * is done directly without referencing this class.
 */
public class App {

    private final UiController uiController;
    private final IO io;

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
     * <p>
     * Contains a permanent loop that runs until the user has asked the app to exit.
     */
    public void run() {
        boolean appRunning = true;

        // UiController is used to print data to the console, and read data from it.

        uiController.printGreetings();
        uiController.printUrl();

        uiController.printEmptyLine();
        uiController.printInstructions();

        // Get a list of available commands from the enum.
        Character[] availableCommands = java.util.Arrays.stream(
                Commands.values()).map(Commands::getCommand).toArray(Character[]::new);

        // This is the main loop for the app. It won't break until explicitly told to do so via the EXIT-command.
        while (appRunning) {

            uiController.printEmptyLine();

            // Ask the user for a single character. It'll always return a valid, uppercase character.
            char character = uiController.askForCharacter(availableCommands, "Command");

            // This line of code returns a "Commands" enum, as dictated by "character". For an example, 'L' returns
            // a Commands.LIST and so forth.
            Commands command = java.util.Arrays.stream(Commands.values())
                    .filter(a -> a.getCommand() == character)
                    .findFirst()
                    .get();

            // The user has entered a command. Refer to the actual methods for comments.
            switch (command) {

                case EXIT:
                    // User wants to exit from the app.
                    appRunning = false;
                    uiController.printGoodbye();
                    return;

                case COMPRESS_HUFFMAN:
                    // User wants to compress a file with Huffman.
                    processCompression(Algorithms.HUFFMAN);
                    break;

                case COMPRESS_LZW:
                    // User wants to compress a file with LZW.
                    processCompression(Algorithms.LZW);
                    break;

                case BENCHMARK:
                    // User wants to benchmark Huffman against LZW.
                    benchmark();
                    break;

                case DECOMPRESS:
                    // User wants to decompress a previously-compressed file.
                    processDecompression();
                    break;

                case COMMANDS:
                    // User has forgotten the commands and asks for them again.
                    uiController.printInstructions();
                    break;

                default:
                    // Happens only when new commands are added, and this method
                    // is called with any of them.
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

        // What file to use as the source (is never modified).
        File sourceFile = io.askForSourceFile(uiController);

        // The source file must exist, otherwise we cannot continue.
        if (sourceFile == null) {
            uiController.printFileError();
            return;
        }

        // Where to write the compressed file.
        File targetFile = io.askForTargetFile(uiController);

        // Source and target must not be the same file.
        if (sourceFile.equals(targetFile)) {
            uiController.printFilesCannotBeTheSame();
            return;
        }

        // If we cannot write to the target file, abort the operation.
        if (targetFile.exists() && !targetFile.canWrite()) {
            uiController.printCannotWrite();
            return;
        }

        // At this point, we have an input file we can read, and an output file we can write to.

        long current = System.currentTimeMillis();

        // This method was called with the chosen algorithm, to be used for compression.
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

        // If the compression succeeds, inform the user about it.
        uiController.printCompressionSuccessful(algorithm, targetFile.getName());

        long sourceFileSize = sourceFile.length();
        long targetFileSize = targetFile.length();

        // Calculate the difference between the original file's length and
        // the compressed file's length as a percentage.
        double difference = calculateDifference(sourceFileSize, targetFileSize);

        uiController.printDifference(sourceFileSize, targetFileSize, difference);

        // Finally, we are done with compression. Print how long it took.
        uiController.printOperationTime(time);
    }

    /**
     * Asks the user for the input and output files, then decompresses the input file into the output location. The
     * used algorithm is determined automatically.
     */
    private void processDecompression() {

        // What file to decompress.
        File sourceFile = io.askForSourceFile(uiController);

        // The source file must, obviously, exist to be able to be decompressed.
        if (sourceFile == null) {
            uiController.printFileError();
            return;
        }

        // The first integer of a compressed file is used to identify the file
        // as either Huffman- or LZW-coded. Incorrect integer values result in an error.
        int algorithmCode = 0;
        int decompressedDataLength = 0;

        try {
            BinaryReadTool binaryReadTool = new BinaryReadTool(sourceFile);

            // The first integer is the identifier (or should be).
            algorithmCode = binaryReadTool.readInt();

            // How big was the original file.
            decompressedDataLength = binaryReadTool.readInt();

            binaryReadTool.close();

        } catch (IOException ex) {
            UiController.printErrorMessage(ex);
            return;
        }

        Algorithms algorithm;

        // We compare the code we got from the file with the predefined one's.
        if (algorithmCode == Main.LZW_CODE) {
            algorithm = Algorithms.LZW;
        } else if (algorithmCode == Main.HUFFMAN_CODE) {
            algorithm = Algorithms.HUFFMAN;
        } else {

            // The code didn't match either Huffman nor LZW, so the file is most likely
            // not compressed via those methods via this app.
            uiController.printFileCorrupted();
            return;
        }

        // Acknowledge the detected algorithm.
        uiController.printAlgorithmDetected(algorithm);

        // Print out how big was the original file, i.e. how many bytes we are going
        // to be writing for decompression.
        uiController.printDecompressedDataLength(decompressedDataLength);

        // Where to save the decompressed file.
        File targetFile = io.askForTargetFile(uiController);

        // Source and target must differ.
        if (sourceFile.equals(targetFile)) {
            uiController.printFilesCannotBeTheSame();
            return;
        }

        // In the case we cannot write to the output location.
        if (targetFile.exists() && !targetFile.canWrite()) {
            uiController.printCannotWrite();
            return;
        }

        // At this point, we have an input file we can read, and an output file we can write to.

        long current = System.currentTimeMillis();

        // This method was called with the chosen algorithm.
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

                // This should actually never happen, as you can only call this method
                // with any from the enum. Only when this app is extended with more algorithms
                // can this exception be thrown.
                throw new IllegalArgumentException();
        }

        long next = System.currentTimeMillis();

        long time = next - current;

        // Decompression was a success.
        uiController.printDecompressionSuccessful(algorithm, targetFile.getName());

        long sourceFileSize = sourceFile.length();
        long targetFileSize = targetFile.length();

        // Get the difference, as a percentage, of the original when compared to
        // the compressed version.
        double difference = calculateDifference(sourceFileSize, targetFileSize);

        uiController.printDifference(sourceFileSize, targetFileSize, difference);

        // Decompression succeeded. Print how long it took.
        uiController.printOperationTime(time);
    }

    /**
     * Benchmarks Huffman against LZW. Only asks for the source file, since the files created during the benchmark
     * are removed once the benchmarking is complete. Finally, reports on the benchmark (in milliseconds,
     * and in bytes).
     */
    private void benchmark() {

        uiController.printBenchmarking();

        // We benchmark Huffman against LZW using the user's chosen file.
        File sourceFile = io.askForSourceFile(uiController);

        // The source file must exist.
        if (sourceFile == null) {
            uiController.printFileError();
            return;
        }

        long originalSize = sourceFile.length();

        // Benchmark using Huffman and store the results.
        uiController.printBenchmarkStart(Algorithms.HUFFMAN);
        BenchmarkResult huffmanResults = Benchmark.runBenchmark(sourceFile, Algorithms.HUFFMAN);

        // And the same with LZW, storing the results.
        uiController.printBenchmarkStart(Algorithms.LZW);
        BenchmarkResult lzwResults = Benchmark.runBenchmark(sourceFile, Algorithms.LZW);

        uiController.printBenchmarkComplete();

        uiController.printEmptyLine();

        // Just some fancy UI stuff.
        uiController.printCompressionResultsHeader();

        // How long compression took on each of the algorithms.
        uiController.printCompressionBenchmarkResults(Algorithms.HUFFMAN, huffmanResults.getCompressionTime());
        uiController.printCompressionBenchmarkResults(Algorithms.LZW, lzwResults.getCompressionTime());

        // Print out the winner (faster compression time).
        if (huffmanResults.getCompressionTime() < lzwResults.getCompressionTime()) {
            uiController.printCompressionTimeWinner(Algorithms.HUFFMAN);
        } else if (huffmanResults.getCompressionTime() > lzwResults.getCompressionTime()) {
            uiController.printCompressionTimeWinner(Algorithms.LZW);
        } else {
            uiController.printEqualCompressionTime();
        }

        uiController.printEmptyLine();

        uiController.printDecompressionResultsHeader();

        // How long decompression took.
        uiController.printDecompressionBenchmarkResults(Algorithms.HUFFMAN, huffmanResults.getDecompressionTime());
        uiController.printDecompressionBenchmarkResults(Algorithms.LZW, lzwResults.getDecompressionTime());

        // Print the winner (faster decompression time).
        if (huffmanResults.getDecompressionTime() < lzwResults.getDecompressionTime()) {
            uiController.printDecompressionTimeWinner(Algorithms.HUFFMAN);
        } else if (huffmanResults.getDecompressionTime() > lzwResults.getDecompressionTime()) {
            uiController.printDecompressionTimeWinner(Algorithms.LZW);
        } else {
            uiController.printEqualDecompressionTime();
        }

        uiController.printEmptyLine();

        // Show how big the original file was.
        uiController.printOriginalFileSize(originalSize);

        // Calculate, as a percentage, the difference between the original file
        // and the compressed file. This is important.
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

        // Finally, print out the winner in regards to file size.
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
