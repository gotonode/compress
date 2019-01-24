package main.java.io.github.gotonode.compress.algorithms.Huffman;

public class Result implements Comparable<Result> {

    private final char value;
    private final int count;
    private final String code;

    public Result(char value, int count, String code) {
        this.value = value;
        this.count = count;
        this.code = code;
    }

    public char getValue() {
        return value;
    }

    public int getCount() {
        return count;
    }

    public String getCode() {
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
