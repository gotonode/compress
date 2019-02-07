package io.github.gotonode.compress.benchmarking;

import io.github.gotonode.compress.algorithms.CompressAlgorithm;
import io.github.gotonode.compress.algorithms.huffman.Huffman;
import io.github.gotonode.compress.algorithms.lzw.LZW;
import io.github.gotonode.compress.enums.Algorithms;

import java.io.File;

/**
 * Used to run a benchmark on the chosen algorithm.
 */
public final class Benchmark {

    private Benchmark() {
        // Cannot create an instance of this class by purpose.
        throw new UnsupportedOperationException();
    }

    /**
     * Runs the benchmark and returns the results.
     *
     * @param sourceFile The file to be used in the benchmarking.
     * @param algorithm  Which algorithm to use. Currently at least Huffman and LZW are available.
     * @return The results as a {@link BenchmarkResult} object.
     */
    public static BenchmarkResult runBenchmark(final File sourceFile, Algorithms algorithm) {

        BenchmarkResult output = null;

        long start;
        long end;

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

        long compressionTime = end - start;

        if (algorithm == Algorithms.HUFFMAN) {
            compressAlgorithm = new Huffman(compressedFile, decompressedFile);
        } else { // It's LZW again.
            compressAlgorithm = new LZW(compressedFile, decompressedFile);
        }

        start = System.currentTimeMillis();

        compressAlgorithm.decompress();

        end = System.currentTimeMillis();

        long decompressionTime = end - start;

        long compressedSize = compressedFile.length();

        compressedFile.delete();
        decompressedFile.delete();

        output = new BenchmarkResult(compressionTime, decompressionTime, compressedSize);

        return output;
    }
}
