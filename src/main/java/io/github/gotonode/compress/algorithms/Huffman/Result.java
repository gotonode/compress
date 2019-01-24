package io.github.gotonode.compress.algorithms.Huffman;

public class Result implements Comparable<Result> {

    private final char value;
    private final int count;
    private final String code;

    Result(char value, int count, String code) {

        this.value = value;
        this.count = count;
        this.code = code;
    }

    char getValue() {
        return value;
    }

    int getCount() {
        return count;
    }

    String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return this.value + ":" + this.count + " (" + this.code + ")";
    }

    @Override
    public int compareTo(Result result) {
        return this.count - result.getCount();
    }
}
