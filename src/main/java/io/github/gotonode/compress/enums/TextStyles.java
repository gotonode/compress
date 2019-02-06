package io.github.gotonode.compress.enums;

import io.github.gotonode.compress.main.Main;

/**
 * Used to color the output in the console. May not work on all systems.
 */
public enum TextStyles {

    Green("\u001b[32m"),
    Blue("\u001b[34m"),
    Magenta("\u001b[35m"),
    White("\u001b[37m"),
    WhiteBold("\u001B[1m"),
    Red("\u001b[31m"),
    Cyan("\u001b[36m"),
    Default("\u001b[0m");

    private static TextStyles titleColor = Red;
    private static TextStyles commandColor = Magenta;
    private static TextStyles importantColor = WhiteBold;
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

    public static String commandText(char command) {
        return stringWithColor(String.valueOf(command), commandColor);
    }

    public static String titleText(String title) {
        return stringWithColor(title, titleColor);
    }

    public static String algoText(Algorithms algorithms, boolean printFullName) {

        TextStyles color;

        if (algorithms == Algorithms.HUFFMAN) {
            color = TextStyles.huffmanColor;
        } else if (algorithms == Algorithms.LZW) {
            color = TextStyles.lzwColor;
        } else {
            throw new IllegalArgumentException();
        }

        String name;

        if (printFullName) {
            name = algorithms.getName();
        } else {
            name = algorithms.name();
        }

        assert color != null;
        return stringWithColor(name, color);
    }

    public static String importantText(String value) {
        return stringWithColor(value, importantColor);
    }

    public static String importantText(char value) {
        return importantText(String.valueOf(value));
    }

    public static String importantText(int value) {
        return importantText(String.valueOf(value));
    }

    public static String importantText(long value) {
        return importantText(String.valueOf(value));
    }

    public static String importantText(double value) {
        return importantText(String.valueOf(value));
    }
}
