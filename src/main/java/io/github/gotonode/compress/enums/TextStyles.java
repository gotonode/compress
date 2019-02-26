package io.github.gotonode.compress.enums;

import io.github.gotonode.compress.main.Main;

/**
 * Used to color the output in the console. May not work on all systems.
 * <p>
 * If console output is not looking right, refer to the static final boolean
 * in the Main class to disable text formatting altogether.
 */
public enum TextStyles {

    // Available colors. There are more, just do a Web search on them.
    Magenta("\u001b[35m"),
    WhiteBold("\u001B[1m"),
    Red("\u001b[31m"),
    Blue("\u001b[34m"),
    Default("\u001b[0m");

    // Feel free to change these to get the UI to look even prettier.
    private static TextStyles titleColor = Red;
    private static TextStyles commandColor = Magenta;
    private static TextStyles importantColor = WhiteBold;
    private static TextStyles urlColor = Blue;
    private static TextStyles huffmanColor = WhiteBold;
    private static TextStyles lzwColor = WhiteBold;

    private final String value;

    TextStyles(String value) {
        this.value = value;
    }

    private static String stringWithColor(String text, TextStyles color) {
        if (Main.CONSOLE_COLORS) {
            return color + text + Default;
        } else {
            return text;
        }
    }

    @Override
    public String toString() {
        return value;
    }

    // Used to distinguish UI calls.
    public static String commandText(char command) {
        return stringWithColor(String.valueOf(command), commandColor);
    }

    // Used to distinguish UI calls.
    public static String titleText(String title) {
        return stringWithColor(title, titleColor);
    }

    /**
     * Prints the algorithm's name, and optionally in a nice text format, too.
     *
     * @param algorithm     The name of the algorithm.
     * @param printFullName Whether to print the full name or the enum code.
     * @return A String containing the algorithm's name and ANSI codes.
     */
    public static String algorithmText(Algorithms algorithm, boolean printFullName) {

        TextStyles color;

        if (algorithm == Algorithms.HUFFMAN) {
            color = TextStyles.huffmanColor;
        } else if (algorithm == Algorithms.LZW) {
            color = TextStyles.lzwColor;
        } else {
            // This is here just in case new algorithms are added.
            throw new IllegalArgumentException();
        }

        String name;

        if (printFullName) {
            name = algorithm.getName();
        } else {
            name = algorithm.name();
        }

        assert color != null;
        return stringWithColor(name, color);
    }

    // A convenience function. Refer to the function this one calls.
    public static String importantText(String value) {
        return stringWithColor(value, importantColor);
    }

    // A convenience function. Refer to the function this one calls.
    public static String importantText(char value) {
        return importantText(String.valueOf(value));
    }

    // A convenience function. Refer to the function this one calls.
    public static String importantText(int value) {
        return importantText(String.valueOf(value));
    }

    // A convenience function. Refer to the function this one calls.
    public static String importantText(long value) {
        return importantText(String.valueOf(value));
    }

    // A convenience function. Refer to the function this one calls.
    public static String importantText(double value) {
        return importantText(String.valueOf(value));
    }

    // A convenience function. Refer to the function this one calls.
    public static String urlText(String value) {
        return stringWithColor(value, urlColor);
    }
}
