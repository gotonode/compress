package test.java.algorithms.Huffman;

import main.java.io.github.gotonode.compress.algorithms.Huffman.Huffman;
import org.junit.Before;
import org.junit.Test;

public class HuffmanTest {

    Huffman huffman;

    @Before
    public void before() {
        huffman = new Huffman(null, null);
    }

    @Test
    public void atest() {
        huffman.runTest("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur ac feugiat sem. Duis eu maximus mi. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Maecenas commodo massa odio, quis faucibus eros dignissim eu. Proin mauris nulla, faucibus id interdum porttitor, ullamcorper nec ante. Pellentesque pretium, massa in fermentum sollicitudin, dui ante gravida justo, a tristique leo tortor sit amet lacus. Suspendisse sem eros, convallis quis aliquam non, vestibulum et nulla. Suspendisse in iaculis nunc. Etiam sapien ligula, faucibus in facilisis id, blandit vel felis. Donec quam libero, auctor non maximus sed, congue eget mauris.");
    }
}
