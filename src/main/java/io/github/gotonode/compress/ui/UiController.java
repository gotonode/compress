package io.github.gotonode.compress.ui;

import io.github.gotonode.compress.enums.Algorithms;
import io.github.gotonode.compress.enums.Commands;
import io.github.gotonode.compress.enums.TextStyles;
import io.github.gotonode.compress.main.Main;

import java.text.DecimalFormat;
import java.util.Scanner;

import static io.github.gotonode.compress.enums.TextStyles.algoText;
import static io.github.gotonode.compress.enums.TextStyles.commandText;
import static io.github.gotonode.compress.enums.TextStyles.titleText;
import static io.github.gotonode.compress.enums.TextStyles.importantText;

/**
 * This class handles printing to the console and reading from it.
 *
 * It is not tested and because of this it is ignored in code coverage.
 */
public class UiController {

    private final String twoSpaces = " " + " ";
    private final String fourSpaces = twoSpaces + twoSpaces;

    private Scanner scanner;

    /**
     * Initializes this object.
     *
     * @param scanner The scanner object to use. It's stored internally.
     */
    public UiController(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Prints a greeting message along with the app's name and version.
     */
    public void printGreetings() {
        System.out.println("Welcome to " + titleText(Main.APP_NAME) + " (version " + Main.APP_VERSION + ")!");
    }

    /**
     * Prints the app's URL on GitHub. The user can download the newest version from there.
     */
    public void printUrl() {
        System.out.println("You can find the latest version here: " + Main.APP_URL);
    }

    /**
     * Before the user exits the app, print a thank you message.
     */
    public void printGoodbye() {
        System.out.println("Thanks for using " + titleText(Main.APP_NAME) + ". Come back soon!");
    }

    /**
     * Continuously ask the user for a character, until a valid one is given.
     *
     * @param allowedChars A list of chars that are accepted.
     * @param prompt       What to ask the user.
     * @return A valid uppercase character.
     */
    public char askForCharacter(Character[] allowedChars, String prompt) {

        while (true) {

            String next = readLine(prompt.trim() + ":");

            if (next.isEmpty()) {
                System.out.println("Please enter something. Type "
                        + importantText(String.valueOf(Commands.COMMANDS.getCommand()))
                        + " to list all commands.");
                printEmptyLine();
                continue;
            }

            if (next.length() > 1) {
                System.out.println("Please only enter 1 character.");
                printEmptyLine();
                continue;
            }

            char input = next.toUpperCase().charAt(0);

            boolean found = false;

            for (char c : allowedChars) {
                if (input == c) {
                    found = true;
                    break;
                }
            }

            if (!found) {

                StringBuilder out = new StringBuilder("[");

                for (int i = 0; i < allowedChars.length; i++) {

                    out.append(importantText(allowedChars[i]));

                    if (i < allowedChars.length - 1) {
                        out.append(", ");
                    }
                }

                out.append("]");

                System.out.println("Please enter a character from the following: " + out.toString());
                printEmptyLine();
            } else {
                return input;
            }
        }

    }

    /**
     * Reads in a line from the scanner object.
     *
     * @param prompt Message to be displayed for the user.
     * @return A String containing the user's response.
     */
    private String readLine(String prompt) {
        System.out.print(prompt.trim() + " "); // Trim is here just in case it already has a trailing space.
        return scanner.nextLine();
    }

    /**
     * Prints the algorithm we're going to be using.
     *
     * @param algorithm The algorithm's name.
     */
    public void printUsing(Algorithms algorithm) {
        System.out.println("Great! We'll be using the " + algoText(algorithm, true) + " algorithm.");
    }

    /**
     * Asks the name of the source file.
     *
     * @return A String containing the source file's name.
     */
    public String askForSourceFilePath() {
        return askForString("Name of your source file (must already exist)", false);
    }

    /**
     * Asks the name of the target file.
     *
     * @return A String containing the target file's name.
     */
    public String askForTargetFilePath() {
        return askForString("Name of your target file (will be overwritten if it exists)", false);
    }

    /**
     * Asks the user for a string. Optionally doesn't allow empty strings.
     *
     * @param prompt     What to ask (prompt) from the user.
     * @param allowEmpty If true, the string can be empty. Otherwise it cannot.
     * @return The string the user typed in.
     */
    private String askForString(String prompt, boolean allowEmpty) {

        String data;

        while (true) {
            data = readLine(prompt.trim() + ":");
            if (data.isEmpty()) {
                if (allowEmpty) {
                    break;
                } else {
                    System.out.println("Please write something.");
                }
            } else {
                break;
            }
        }

        return data;
    }

    /**
     * Prints an empty line. Simple.
     */
    public void printEmptyLine() {
        System.out.println();
    }

    /**
     * Prints the instructions for the user to follow.
     */
    public void printInstructions() {
        System.out.println("Please choose a command from the following:");

        System.out.println(commandText('H')
                + ": Compress a file using " + algoText(Algorithms.HUFFMAN, true));

        System.out.println(commandText('L')
                + ": Compress a file using " + algoText(Algorithms.LZW, true));

        System.out.println(commandText('D')
                + ": Decompress a previously compressed file");

        System.out.println(commandText('B')
                + ": Benchmark "
                + algoText(Algorithms.HUFFMAN, true)
                + " against " + algoText(Algorithms.LZW, true));

        System.out.println(commandText('X')
                + ": Print these instructions again");

        System.out.println(commandText('E')
                + ": Exit from the program");
    }

    /**
     * When there's an error with a file, tell about it.
     */
    public void printFileError() {
        System.out.println("An error occurred trying to access that file."
                + " Maybe it doesn't exist or you don't have the necessary permissions?");
    }

    /**
     * Decompression was successful. Report it.
     *
     * @param algorithm  The algorithm that was used.
     * @param targetPath Where the results were written to.
     */
    public void printDecompressionSuccessful(Algorithms algorithm, String targetPath) {
        System.out.println("Done! Decompression with " + algoText(algorithm, true)
                + " was successful, and your decompressed file is located at '"
                + importantText(targetPath) + "'.");
    }

    /**
     * Compression was successful. Report it.
     *
     * @param algorithm  The algorithm that was used.
     * @param targetPath Where the results were written to.
     */
    public void printCompressionSuccessful(Algorithms algorithm, String targetPath) {
        System.out.println("Done! Compression with " + algoText(algorithm, true)
                + " was successful, and your new and tiny file is located at '"
                + importantText(targetPath) + "'.");
    }

    /**
     * Prints the difference in size (input file, output file) in kilobytes.
     *
     * @param sourceBytes The input file's size.
     * @param targetBytes The output file's size.
     * @param difference  The difference (as a percentage). Should be pre-calculated
     *                    as this is only an UI class and shouldn't handle calculations
     *                    like that.
     */
    public void printDifference(long sourceBytes, long targetBytes, double difference) {
        System.out.println("Your source file was " + importantText(sourceBytes)
                + " bytes, and your target file came out at " + importantText(targetBytes)
                + " bytes, which is a " + importantText(formatTwoDecimals(difference))
                + " % difference!");
    }

    /**
     * Prints how long the operation took, in milliseconds.
     *
     * @param milliseconds A long value containing the operation time.
     */
    public void printOperationTime(long milliseconds) {
        System.out.println("This operation took " + importantText(milliseconds) + " milliseconds in total.");
    }

    /**
     * Prints info that we're going to be benchmarking the two algorithms together.
     */
    public void printBenchmarking() {
        System.out.println("We'll benchmark " + algoText(Algorithms.HUFFMAN, true)
                + " against " + algoText(Algorithms.LZW, true)
                + " using your chosen file. No new files will be left as residue.");

        System.out.println(
                "Consider choosing a file that is not already compressed, as compressed files do not compress very well."
        );
    }

    /**
     * When the app can't write to the output file, we'll report it.
     */
    public void printCannotWrite() {
        System.out.println(
                "Cannot write to that output location. Is the file in use, and do you have the necessary permissions?"
        );
    }

    /**
     * If the user has chosen the same file as both the input and output,
     * we'll report that this cannot happen.
     */
    public void printFilesCannotBeTheSame() {
        System.out.println("Your input and output files cannot be the same.");
    }

    /**
     * Inform the user that we have detected the used algorithm.
     *
     * @param algorithm The algorithm that was used.
     */
    public void printAlgorithmDetected(Algorithms algorithm) {
        System.out.println("It seems that this file was compressed with " + algoText(algorithm, true) + ".");
    }

    /**
     * Print the amount of data written when the compressed file is decompressed.
     *
     * @param decompressedDataLength Amount in kilobytes.
     */
    public void printDecompressedDataLength(int decompressedDataLength) {
        System.out.println("We'll write exactly " + importantText(decompressedDataLength) + " bytes to disk.");
    }

    /**
     * If the user starts this app with arguments, tell them that they are not used.
     *
     * @param argsAsString Arguments the user passed.
     */
    public void printArgumentsNotSupported(String argsAsString) {
        System.out.println("Started with arguments: " + importantText(argsAsString));
        System.out.println("Please note that arguments are currently not supported!");
    }

    /**
     * Prints results from compression benchmarking.
     *
     * @param algorithm       Which algorithm was used.
     * @param compressionTime How much time it took to compress the sample data (in milliseconds).
     */
    public void printCompressionBenchmarkResults(Algorithms algorithm, long compressionTime) {
        System.out.println(fourSpaces + algoText(algorithm, true)
                + " took " + importantText(compressionTime) + " ms to compress");
    }

    /**
     * Prints results from decompression benchmarking.
     *
     * @param algorithm         Which algorithm was used.
     * @param decompressionTime How much time it took to decompress the sample data (in milliseconds).
     */
    public void printDecompressionBenchmarkResults(Algorithms algorithm, long decompressionTime) {
        System.out.println(fourSpaces + algoText(algorithm, true)
                + " took " + importantText(decompressionTime) + " ms to decompress");
    }

    /**
     * Prints the compressed file's size and the reduction to the size of the original.
     *
     * @param algorithm          Which algorithm was used.
     * @param compressedFileSize The size of the newly-compressed file.
     * @param reduction          Reduction, as a percentage.
     */
    public void printReducedFileSize(Algorithms algorithm, long compressedFileSize, double reduction) {
        printFileSizeDifference(algorithm, compressedFileSize, reduction, true);
    }

    /**
     * If the compressed file got bigger, print information about it.
     *
     * @param algorithm          Which algorithm was used.
     * @param compressedFileSize The size of the newly-compressed file.
     * @param increase           Increase amount, as a percentage.
     */
    public void printIncreasedFileSize(Algorithms algorithm, long compressedFileSize, double increase) {
        printFileSizeDifference(algorithm, compressedFileSize, increase, false);
    }

    private void printFileSizeDifference(Algorithms algorithm,
                                         long compressedFileSize, double change, boolean sizeWasReduced) {

        // Whether the file got bigger or smaller, choose this word accordingly.
        String directionText;

        if (sizeWasReduced) {
            directionText = "reduction";
        } else {
            directionText = "increase";
        }

        System.out.println(fourSpaces + algoText(algorithm, true)
                + " compressed it to " + importantText(compressedFileSize)
                + " bytes (a " + importantText(formatTwoDecimals(change))
                + " % " + directionText + ")");
    }

    /**
     * Print's the original file's size.
     *
     * @param originalFileSize The size of the original file.
     */
    public void printOriginalFileSize(long originalFileSize) {
        System.out.println(twoSpaces + "Compression size results (the original file was "
                + importantText(originalFileSize) + " bytes):");
    }

    /**
     * In the rare chance that both algorithms produce a compressed file with equal size, report it.
     */
    public void printEqualCompressionSize() {
        System.out.println(fourSpaces
                + "Incredible! Both algorithms produced a compressed file of the exact same size!");
    }

    /**
     * Print whichever algorithm compressed the file to a smaller size.
     *
     * @param algorithm Which algorithm was the winner.
     */
    public void printCompressionSizeWinner(Algorithms algorithm) {
        System.out.println(fourSpaces
                + "Regarding file size, the winner is " + algoText(algorithm, true) + "!");
    }

    /**
     * Print whichever algorithm compressed the file the fastest.
     *
     * @param algorithm Which algorithm was the winner.
     */
    public void printCompressionTimeWinner(Algorithms algorithm) {
        System.out.println(fourSpaces
                + "Regarding compression time, the winner is " + algoText(algorithm, true) + "!");
    }

    /**
     * Print whichever algorithm decompressed the file the fastest.
     *
     * @param algorithm Which algorithm was the winner.
     */
    public void printDecompressionTimeWinner(Algorithms algorithm) {
        System.out.println(fourSpaces
                + "Regarding decompression time, the winner is " + algoText(algorithm, true) + "!");
    }

    /**
     * In the rare chance that both algorithms produce the compressed file in the same time, report it.
     */
    public void printEqualCompressionTime() {
        System.out.println(fourSpaces
                + "Incredible! Both algorithms took the exact same time to compress that file!");
    }

    /**
     * In the rare chance that both algorithms produce the decompressed file in the same time, report it.
     */
    public void printEqualDecompressionTime() {
        System.out.println(fourSpaces
                + "Incredible! Both algorithms took the exact same time to decompress that file!");
    }

    /**
     * Print the compression results header for nice UI visuals, used with the benchmarking function.
     */
    public void printCompressionResultsHeader() {
        System.out.println(twoSpaces + "Compression time results:");
    }

    /**
     * Print the decompression results header for nice UI visuals, used with the benchmarking function.
     */
    public void printDecompressionResultsHeader() {
        System.out.println(twoSpaces + "Decompression time results:");
    }

    private String formatTwoDecimals(Object data) {
        return new DecimalFormat("#.##").format(data);
    }

    /**
     * A static method callable from anywhere to print out an exception message.
     *
     * @param exception Pass the exception directly, and nothing else.
     */
    public static void printErrorMessage(Exception exception) {
        System.out.println("An error occurred. Here's the exact error message for your (in)convenience:");
        System.out.println(exception.getMessage());
        System.out.println("We'll defer from printing the stack trace since nobody likes to see that.");
        System.out.println("Please note that residual files might have been left behind.");
    }
}
