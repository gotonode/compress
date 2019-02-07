package io.github.gotonode.compress.enums;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommandsTest {

    @Test
    public void commandsDecompressTest() {
        Commands commands = Commands.DECOMPRESS;
        assertEquals('D', commands.getCommand());
    }

    @Test
    public void commandsHuffmanTest() {
        Commands commands = Commands.COMPRESS_HUFFMAN;
        assertEquals('H', commands.getCommand());
    }

    @Test
    public void commandsLzwTest() {
        Commands commands = Commands.COMPRESS_LZW;
        assertEquals('L', commands.getCommand());
    }

    @Test
    public void commandsCommandListTest() {
        Commands commands = Commands.COMMANDS;
        assertEquals('X', commands.getCommand());
    }

    @Test
    public void commandsBenchmarkTest() {
        Commands commands = Commands.BENCHMARK;
        assertEquals('B', commands.getCommand());
    }

    @Test
    public void commandsExitTest() {
        Commands commands = Commands.EXIT;
        assertEquals('E', commands.getCommand());
    }
}
