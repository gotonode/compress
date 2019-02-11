package io.github.gotonode.compress.utils;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MinQueueTest {

    private MinQueue minQueue;

    @Before
    public void before() {
        minQueue = new MinQueue<Object>();
    }

    @Test
    public void minQueueAddTo() {

        String s = new String("TEST");

        minQueue.offer(s);

        String out = (String) minQueue.poll();

        assertEquals(s, out);
    }

    @Test
    public void minQueueSize() {

        String s = new String("TEST");

        minQueue.offer(s);

        int size = minQueue.getSize();

        assertEquals(1, size);
    }

    @Test
    public void minQueueLowest() {

        for (int i = 100; i >= 0; i--) {

            String s = new String(String.valueOf(i));

            minQueue.offer(s);
        }

        String out = (String) minQueue.poll();

        assertEquals("0", out);
    }
}
