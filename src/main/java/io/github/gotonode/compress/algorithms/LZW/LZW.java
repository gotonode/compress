package io.github.gotonode.compress.algorithms.LZW;

import io.github.gotonode.compress.algorithms.CompressAlgorithm;

import java.io.File;

/**
 * This class contains my personal Lempel–Ziv–Welch implementation.
 * <p>
 * It has been created by following the definition from its respective Wikipedia article and other online sources.
 * <p>
 * This section will eventually contain more info.
 * <p>
 * Sources:
 * <p>
 * - <a href="https://en.wikipedia.org/wiki/Lempel%E2%80%93Ziv%E2%80%93Welch">Lempel–Ziv–Welch</a> (Wikipedia)
 *
 * @author gotonode (github.com/gotonode)
 */
public class LZW implements CompressAlgorithm {

    private final File source;
    private final File target;

    /**
     * Creates a new LZW object. This is used to compress/decompress a file using LZW.
     *
     * @param source The file to be compressed/decompressed.
     * @param target The file to write the results of the compression/decompression to.
     */
    public LZW(File source, File target) {
        this.source = source;
        this.target = target;
    }

    @Override
    public String toString() {
        return "LZW{" +
                "source=" + source +
                ", target=" + target +
                '}';
    }

    @Override
    public boolean compress() {
        return false;
    }

    @Override
    public boolean decompress() {
        return false;
    }

    private String[] dictionary;
    private int position;

    private String compress(String input) {
        init(input.length());
        StringBuilder out = new StringBuilder();
        String w = "";
        char k;
        while (input.length() > 0) {
            k = input.charAt(0);
            input = input.substring(1);
            if (has(w + k)) {
                w += k;
            } else {
                out.append(getCode(w));
                add(w + k);
                w = k + "";
            }
        }
        return out + getCode(w);
    }

    private String decompress(String input) {
        init(input.length() + 1);
        StringBuilder out = new StringBuilder();
        String w = "";
        int k = (int) input.charAt(0);
        w = getChar(k);
        input = input.substring(1);
        out.append(w);

        while (input.length() > 0) {
            k = (int) input.charAt(0);
            input = input.substring(1);
            out.append(getChar(k));
            add(w + getChar(k).charAt(0));
            w = getChar(k);
        }
        return out.toString();
    }

    private void init(int size) {
        dictionary = new String[size];

        // Just in case it has been modified elsewhere.
        position = 0;
    }

    private boolean has(String s) {
        return (getCode(s).length() > 0);
    }

    private String getCode(String s) {
        String code = "";
        char c;
        int i = 0;
        if (s.length() == 1) {
            return "" + s.charAt(0);
        } else {
            while ((code.length() == 0) && (i < dictionary.length)) {
                if ((dictionary[i] != null) && dictionary[i].equals(s)) {
                    c = (char) (i + 256);
                    code = "" + c;
                }
                i++;
            }
            return code;
        }
    }

    private String getChar(int code) {
        if (code < 256) {
            return "" + (char) code;
        } else if ((code - 256) < dictionary.length) {
            return dictionary[code - 256];
        } else {
            return "";
        }
    }

    private void add(String s) {
        dictionary[position] = s;
        position++;
    }

    // TODO: REMOVE THIS WHEN DONE!
    public void runTest(String s) {
        String compressed = compress(s);
        System.out.println(compressed);

        String decompressed = decompress(compressed);
        System.out.println(decompressed);
    }
}
