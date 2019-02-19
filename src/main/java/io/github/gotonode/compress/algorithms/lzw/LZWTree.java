package io.github.gotonode.compress.algorithms.lzw;

/**
 * This class presents a ternary search trie structure used by LZW.
 * <p>
 * It's primary function is to traverse itself finding the longest prefix in
 * the given data.
 * <p>
 * Keys are of type String and values are of type 32-bit integer.
 * <p>
 * For more information, please refer to the following sources:
 * • <a href="https://en.wikipedia.org/wiki/Ternary_search_tree">Ternary search tree</a> (Wikipedia)
 */
class LZWTree {

    // The root node. Once the tree shifts, this can change.
    private LZWNode rootLzwNode;

    /**
     * Get the value from this search tree with the given key.
     *
     * @param key A String key to fetch the value for.
     * @return The integer value.
     */
    int get(String key) {

        // Start with displacement 0 (no displacement).
        LZWNode lzwNode = getNode(rootLzwNode, key, 0);

        if (lzwNode == null) {
            // Returns -1 if no node is found.
            return -1;
        } else {
            // Returns a non-negative value if it is found.
            return lzwNode.getValue();
        }
    }

    /**
     * Retrieves a node that has the specific key. Uses a displacement integer.
     *
     * @param node         The node to start from (either recursively or from the root).
     * @param key          The key (String) to search for.
     * @param displacement Also called an offset.
     * @return A new node with the given parameters.
     */
    private LZWNode getNode(LZWNode node, String key, int displacement) {

        if (node == null) {
            return null;
        }

        char character = key.charAt(displacement);

        // Recurse through the tree, choosing either the left, middle or right
        // child node to go to depending on the character value and the displacement.

        if (character < node.getCharacter()) {
            return getNode(node.getLeftNode(), key, displacement);

        } else if (character > node.getCharacter()) {
            return getNode(node.getRightNode(), key, displacement);

        } else if (displacement < key.length() - 1) {

            // Important to add one to the displacement here.
            return getNode(node.getMiddleNode(), key, displacement + 1);

        } else {
            return node;
        }
    }

    /**
     * Used to find the longest common prefix from the data. LZW utilizes this when
     * creating the dictionary.
     *
     * @param data The input data to find the prefix for.
     * @return A String representation of the prefix.
     */
    String prefix(String data) {

        // Return null if our data is exhausted (empty).
        if (data.length() == 0) {
            return null;
        }

        // This is used when returning data. Keep an eye out for it in debugging.
        int length = 0;

        // Start at the root.
        LZWNode lzwNode = rootLzwNode;

        int index = 0;

        // Go through the tree in a non-recursive way. Depending on the
        // character, choose either the left, middle or right child node
        // and continue from there unless we arrive at an empty node or
        // our index goes over the data's length.
        while (lzwNode != null && index < data.length()) {

            char character = data.charAt(index);

            if (character < lzwNode.getCharacter()) {
                lzwNode = lzwNode.getLeftNode();

            } else if (character > lzwNode.getCharacter()) {
                lzwNode = lzwNode.getRightNode();

            } else {

                index++;

                if (lzwNode.getValue() != -1) {
                    // If the value is non-negative, update the length.
                    length = index;
                }

                lzwNode = lzwNode.getMiddleNode();
            }
        }

        return data.substring(0, length);
    }

    /**
     * This is a convenience function when adding chars.
     *
     * @param key   A char that will be converted into a String to be passed along.
     * @param value The value to be added to this key-value pair.
     */
    void add(char key, int value) {
        add(String.valueOf(key), value);
    }

    /**
     * Adds the given key-value pair to the search tree.
     *
     * @param key   A String containing the key.
     * @param value An integer value.
     */
    void add(String key, int value) {
        rootLzwNode = addNode(rootLzwNode, key, value, 0);
    }

    /**
     * Adds a node into the ternary search trie.
     *
     * @param node         The node (usually the root) to add the new node into.
     * @param key          The String key for this specific node.
     * @param value        Used for relational management.
     * @param displacement May also be called an offset. Used with the key.
     * @return Returns the new node.
     */
    private LZWNode addNode(LZWNode node, String key, int value, int displacement) {

        char character = key.charAt(displacement);

        if (node == null) {
            node = new LZWNode();
            node.setCharacter(character);
        }

        if (character < node.getCharacter()) {
            node.setLeftNode(addNode(node.getLeftNode(), key, value, displacement));

        } else if (character > node.getCharacter()) {
            node.setRightNode(addNode(node.getRightNode(), key, value, displacement));

        } else if (displacement < key.length() - 1) {

            // Important to add one to the displacement here.
            node.setMiddleNode(addNode(node.getMiddleNode(), key, value, displacement + 1));

        } else {
            node.setValue(value);

        }

        return node;
    }

    @Override
    public String toString() {
        return "LZWTree{" +
                "rootLzwNode=" + rootLzwNode +
                '}';
    }
}
