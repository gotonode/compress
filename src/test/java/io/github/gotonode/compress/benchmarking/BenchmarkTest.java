package io.github.gotonode.compress.benchmarking;

import io.github.gotonode.compress._generic._Generic;
import io.github.gotonode.compress.enums.Algorithms;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static junit.framework.TestCase.assertTrue;

public class BenchmarkTest {

    private _Generic _generic;
    private File inputFile;

    private static TemporaryFolder tempFolder;

    @BeforeClass
    public static void beforeClass() throws IOException {
        tempFolder = new TemporaryFolder();
        tempFolder.create();
    }

    @Before
    public void before() throws IOException {
        _generic = new _Generic();

        inputFile = _generic.generateBinaryFile(tempFolder.getRoot() + "/benchmark.bin");
    }

    @Test
    public void huffmanBenchmarkingTest() {
        BenchmarkResult benchmarkResult = Benchmark.runBenchmark(inputFile, Algorithms.HUFFMAN);
        assertTrue(benchmarkResult.getCompressedSize() >= 0);
        assertTrue(benchmarkResult.getCompressionTime() >= 0);
        assertTrue(benchmarkResult.getDecompressionTime() >= 0);
    }

    @Test
    public void lzwBenchmarkingTest() {
        BenchmarkResult benchmarkResult = Benchmark.runBenchmark(inputFile, Algorithms.LZW);
        assertTrue(benchmarkResult.getCompressedSize() >= 0);
        assertTrue(benchmarkResult.getCompressionTime() >= 0);
        assertTrue(benchmarkResult.getDecompressionTime() >= 0);
    }
}
