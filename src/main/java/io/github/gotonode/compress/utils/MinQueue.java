package io.github.gotonode.compress.utils;

import java.util.PriorityQueue;

/**
 * This is my own minimum priority queue. Work in progress. Currently
 * it only encapsulates Java's PriorityQueue.
 *
 * @param <Type> What type should this queue consist of.
 */
public class MinQueue<Type> {

    private PriorityQueue<Type> pq;

    public MinQueue() {
        pq = new PriorityQueue<>();
    }

    public void offer(Type type) {
        pq.offer(type);
    }

    public int size() {
        return pq.size();
    }

    public Type poll() {
        return pq.poll();
    }
}
