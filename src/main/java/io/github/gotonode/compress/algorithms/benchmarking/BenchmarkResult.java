package io.github.gotonode.compress.algorithms.benchmarking;

/**
 * Holds the results from a benchmarking run.
 */
public class BenchmarkResult {

    private long compressionTime;
    private long decompressionTime;
    private long compressedSize;

    /**
     * Creates a new object that holds the benchmarking results.
     *
     * @param compressionTime   How much time it took to compress the file (in milliseconds).
     * @param decompressionTime How much time it took to decompress the file (in milliseconds).
     * @param compressedSize    Size of the compressed file (in bytes).
     */
    BenchmarkResult(long compressionTime, long decompressionTime, long compressedSize) {
        this.compressionTime = compressionTime;
        this.decompressionTime = decompressionTime;
        this.compressedSize = compressedSize;
    }

    public long getCompressionTime() {
        return compressionTime;
    }

    public long getDecompressionTime() {
        return decompressionTime;
    }

    public long getCompressedSize() {
        return compressedSize;
    }
}
