package main.java.io.github.gotonode.compress.ui;

import main.java.io.github.gotonode.compress.enums.Algorithms;
import main.java.io.github.gotonode.compress.main.Main;

import java.util.Arrays;
import java.util.Scanner;

public class UiController {

    private Scanner scanner;

    public UiController(Scanner scanner) {
        this.scanner = scanner;
    }

    public void printGreetings() {
        System.out.println("Welcome to " + Main.APP_NAME + " (version " + Main.APP_VERSION + ")!");
    }

    public void printUrl() {
        System.out.println("You can find the latest version here: " + Main.APP_URL);
    }

    /**
     * Continuously ask the user for a character, until a valid one is given.
     *
     * @param allowedChars A list of chars that are accepted.
     * @return A valid uppercase character.
     */
    public char askForCharacter(Character[] allowedChars, String prompt) {

        while (true) {

            String next = readLine(prompt.trim() + ":");

            if (next.isEmpty()) {
                println("Please enter something.");
                continue;
            }

            if (next.length() > 1) {
                println("Please only enter 1 character.");
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
                println("Please enter a character from the following: " + Arrays.toString(allowedChars));
            } else {
                return input;
            }
        }

    }

    public String readLine(String prompt) {
        System.out.println(prompt.trim() + " "); // Trim is here just in case it already has a trailing space.
        return scanner.nextLine();
    }

    public void println(String input) {
        System.out.println(input);
    }

    public void printGoodbye() {
        System.out.println("Thanks for using " + Main.APP_NAME + ". Come back soon!");
    }

    public void printUsing(Algorithms algorithm) {
        System.out.println("Great! We'll be using the " + algorithm.getName() + " algorithm.");
    }

    public String askForSourceFilePath() {
       return askForString("Name of your source file (must already exist)", false);
    }

    public String askForTargetFilePath() {
        return askForString("Name of your target file (will be overwritten if it exists)", false);
    }

    /**
     * Asks the user for a string. Optionally doesn't allow empty strings.
     *
     * @param prompt What to ask (prompt) from the user.
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
                    println("Please write something.");
                }
            } else {
                break;
            }
        }

        return data;
    }

    public void printEmptyLine() {
        System.out.println();
    }

    public void printInstructions() {
        System.out.println("Please choose a command from the following:");
        System.out.println("H: Compress a file using Huffman coding");
        System.out.println("L: Compress a file using LZW");
        System.out.println("D: Decompress a previously compressed file");
        System.out.println("B: Benchmark Huffman against LZW");
        System.out.println("X: Print these instructions again");
        System.out.println("E: Exit from the program");
    }

    public void printFileError() {
        System.out.println("An error occurred trying to access that file. Maybe it doesn't exist or you don't have the necessary permissions?");
    }

    public void printDecompressionSuccessful(String targetPath) {
        System.out.println("Done! Decompression was successful, and your decompressed file is located at '" + targetPath + "'.");
    }

    public void printBenchmarking() {
        System.out.println("We'll benchmark Huffman against LZW using your chosen file. No new files will be created.");
    }

    public void printCannotWrite() {
        System.out.println("Cannot write to that output location. Is the file in use, and do you have the necessary permissions?");
    }

    public void printFilesCannotBeTheSame() {
        System.out.println("Your input and output files cannot be the same.");
    }
}
