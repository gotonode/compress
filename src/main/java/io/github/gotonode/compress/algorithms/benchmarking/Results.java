package io.github.gotonode.compress.algorithms.benchmarking;

public class Results {

    private long compressionTime;
    private long decompressionTime;
    private long compressedSize;

    public Results(long compressionTime, long decompressionTime, long compressedSize) {
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
