package io.github.gotonode.compress.algorithms.LZW;

import io.github.gotonode.compress.algorithms.CompressAlgorithms;

import java.io.File;

/**
 * This class contains my personal Lempel–Ziv–Welch implementation.
 *
 * It has been created by following the definition from its respective Wikipedia article and other online sources.
 *
 * This section will eventually contain more info.
 *
 * Sources:
 *
 * - <a href="https://en.wikipedia.org/wiki/Lempel%E2%80%93Ziv%E2%80%93Welch">Lempel–Ziv–Welch</a> (Wikipedia)
 *
 * @author gotonode (github.com/gotonode)
 */
public class LZW implements CompressAlgorithms {

    private final File source;
    private final File target;

    /**
     * Creates a new LZW object. This is used to compress/decompress a file using LZW.
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

    private String compress (String input) {
        init(input.length());
        String out = "";
        String w = "";
        char k;
        while (input.length() > 0) {
            k = input.charAt(0);
            input = input.substring(1);
            if (dictionaryContains(w + k)) {
                w+= k;
            }
            else {
                out+=dictionaryCode(w);
                dictionaryAdd(w+k);
                w = k + "";
            }
        }
        return out + dictionaryCode(w);
    }

    private String decompress(String input) {
        init(input.length() + 1);
        String out = "";
        String w = "";
        int k = (int)input.charAt(0);
        w = dictionaryCharacter(k);
        input = input.substring(1);
        out += w;

        while (input.length() > 0) {
            k = (int)input.charAt(0);
            input = input.substring(1);
            out += dictionaryCharacter(k);
            dictionaryAdd(w + dictionaryCharacter(k).charAt(0));
            w = dictionaryCharacter(k);
        }
        return out;
    }

    private void init(int size) {
        dictionary = new String[size];

        // Just in case it has been modified elsewhere.
        position = 0;
    }

    private boolean dictionaryContains(String s) {
        return (dictionaryCode(s).length() > 0);
    }

    private String dictionaryCode(String s) {
        String code = "";
        char c;
        int i = 0;
        if (s.length() == 1) {
            return "" + s.charAt(0);
        } else {
            while ((code.length() == 0) && (i < dictionary.length)) {
                if ((dictionary[i] != null) && dictionary[i].equals(s)) {
                    c = (char)(i+256);
                    code = "" + c;
                }
                i++;
            }
            return code;
        }
    }

    private String dictionaryCharacter(int code) {
        if (code < 256) {
            return "" + (char)code;
        }
        else if ((code-256) < dictionary.length) {
            return dictionary[code-256];
        } else {
            return "";
        }
    }

    private void dictionaryAdd(String s) {
        dictionary[position] = s;
        position++;
    }

    public void runTest(String s) {
        String compressed = compress(s);
        System.out.println(compressed);

        String decompressed = decompress(compressed);
        System.out.println(decompressed);
    }
}
