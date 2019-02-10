package io.github.gotonode.compress.utils;

import io.github.gotonode.compress.algorithms.huffman.HuffmanNode;
import io.github.gotonode.compress.main.Main;

/**
 * This is my own minimum priority queue. Makes use of Java's
 * Iterable-functionality.
 * <p>
 * To create a new MinQueue, you must specify which variable type
 * it should hold. Currently, only Huffman uses this and thus
 * the type will be {@link HuffmanNode}.
 *
 * @param <Type> What type should this queue consist of.
 */
public class MinQueue<Type> {

    // This array will hold the objects (between 0 and n).
    private Type[] array;

    // Number of items on this queue.
    private int count; // Starts at 0, or empty.

    /**
     * Create a new MinQueue. Initial capacity is set to 1.
     */
    public MinQueue() {
        this.array = (Type[]) new Object[2];
    }

    /**
     * Returns the size of this queue.
     *
     * @return An integer with the size. Returns 0 if the queue is empty.
     */
    public int getSize() {
        return count;
    }

    /**
     * Resize the MinQueue when it can't fit any more new elements in,
     * or if it has become too big for its contents.
     * <p>
     * Actually, creates a new array and replaces the previous one
     * with the array. The new array will get all of the content of the
     * previous array.
     *
     * @param capacity The new size for the array. Usually either double
     *                 (grow) the size or half the size (shrink).
     */
    private void resize(int capacity) {

        Type[] newArray = (Type[]) new Object[capacity];

        // We could also simply use Java's array copy feature.
        for (int i = 1; i <= count; i++) {
            newArray[i] = array[i];
        }

        array = newArray;
    }

    /**
     * Offers an element to be put into the queue.
     *
     * @param type What element is to be added.
     */
    public void offer(Type type) {

        if (count == array.length - 1) {

            // Create a new array of double the size and replace the
            // old array with the new one.
            int newSize = array.length * Main.PRIORITY_QUEUE_SCALE_FACTOR;
            resize(newSize);
        }

        array[++count] = type;

        // Ensure that the minimum queue validity remains.
        up(count);
    }

    /**
     * Takes the next element from the queue. This is the element
     * with the smallest value, which is at the front of the queue.
     *
     * @return The next item in line, with the smallest value.
     */
    public Type poll() {

        // Store this and return at the end of the method.
        Type next = array[1];

        // Swap the elements with each other.
        swap(1, count--);

        // Ensure that the minimum queue validity remains.
        down();

        /*if (count > 0) {
            if (count == (array.length - 1 / 4)) {

                // The old array is now too big, so we'll resize it by
                // creating a new array and replacing the old one.
                int newSize = array.length / Main.PRIORITY_QUEUE_SCALE_FACTOR;
                resize(newSize);
            }
        }*/

        return next;
    }

    /**
     * Ensures the priority queue validity remains. This continuously
     * swaps items until the queue is in order again.
     *
     * @param amount Change by this amount.
     */
    private void up(int amount) {

        while (amount > 1 && greaterThan(amount / 2, amount)) {
            swap(amount, amount / 2);
            amount = amount / 2;
        }
    }

    /**
     * Ensures the priority queue validity remains. This continuously
     * swaps items until the queue is in order again.
     */
    private void down() {

        int amount = 1;

        while (2 * amount <= count) {
            int j = 2 * amount;

            if (j < count) {
                if (greaterThan(j, j + 1)) {
                    j++;
                }
            }

            if (!greaterThan(amount, j)) {
                break;
            }

            swap(amount, j);
            amount = j;
        }
    }

    /**
     * Uses the comparator to compare the two elements at the given indices.
     *
     * @param first  Item from the array with this index.
     * @param second Item from the array with this index.
     * @return True if the first is greater than the second, false otherwise.
     */
    private boolean greaterThan(int first, int second) {

        Comparable<Type> comparable = (Comparable<Type>) array[first];

        int result = comparable.compareTo(array[second]);

        return result > 0;
    }

    /**
     * An extremely simple swap function that uses a temporary variable. Swaps
     * two items in the array from the given indices.
     *
     * @param first  The first index. The order doesn't matter.
     * @param second The second index. The order doesn't matter.
     */
    private void swap(int first, int second) {
        Type temp = array[first];
        array[first] = array[second];
        array[second] = temp;
    }
}
