package io.github.gotonode.compress.app;

import io.github.gotonode.compress.algorithms.CompressAlgorithm;
import io.github.gotonode.compress.algorithms.huffman.Huffman;
import io.github.gotonode.compress.algorithms.lzw.LZW;
import io.github.gotonode.compress.enums.Algorithms;

import java.io.File;

/**
 *
 */
final class Benchmark {

    private Benchmark() {
        // Cannot create an instance of this class by purpose.
        throw new UnsupportedOperationException();
    }

    /**
     * @param sourceFile
     * @param algorithm
     * @return
     */
    static long[] runBenchmark(final File sourceFile, Algorithms algorithm) {

        long start;
        long end;

        long[] output = new long[3];

        File compressedFile = new File(sourceFile.getAbsolutePath() + ".COMPRESSED");
        File decompressedFile = new File(sourceFile.getAbsolutePath() + ".DECOMPRESSED");

        CompressAlgorithm compressAlgorithm;

        if (algorithm == Algorithms.HUFFMAN) {
            compressAlgorithm = new Huffman(sourceFile, compressedFile);
        } else { // It's LZW.
            compressAlgorithm = new LZW(sourceFile, compressedFile);
        }

        start = System.currentTimeMillis();

        compressAlgorithm.compress();

        end = System.currentTimeMillis();

        output[0] = end - start;

        if (algorithm == Algorithms.HUFFMAN) {
            compressAlgorithm = new Huffman(compressedFile, decompressedFile);
        } else { // It's LZW again.
            compressAlgorithm = new LZW(compressedFile, decompressedFile);
        }

        start = System.currentTimeMillis();

        compressAlgorithm.decompress();

        end = System.currentTimeMillis();

        output[1] = end - start;

        long min = Math.min(sourceFile.length(), compressedFile.length());
        long max = Math.max(sourceFile.length(), compressedFile.length());

        output[2] = max - min;

        compressedFile.delete();
        decompressedFile.delete();

        return output;
    }
}
