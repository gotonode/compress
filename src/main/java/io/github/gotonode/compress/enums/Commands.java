package main.java.io.github.gotonode.compress.enums;

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
