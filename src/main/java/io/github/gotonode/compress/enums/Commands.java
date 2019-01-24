package io.github.gotonode.compress.enums;

/**
 * These are the commands available to the user. Using enums just makes everything simpler and neater.
 */
public enum Commands {

    EXIT('E'), COMPRESS_HUFFMAN('H'), COMPRESS_LZW('L'), BENCHMARK('B'), DECOMPRESS('D'), COMMANDS('X');

    private final char command;

    Commands(char command) {
        this.command = command;
    }

    public char getCommand() {
        return this.command;
    }
}
