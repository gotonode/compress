package io.github.gotonode.compress.algorithms.lzw;

/**
 * This class presents a ternary search trie structure.
 *
 * It's primary function is to traverse itself finding the longest prefix in
 * the given data.
 *
 * Keys are of type String and values are of type 32-bit integer.
 *
 * For more information, please see the following URL:
 * <a href="https://en.wikipedia.org/wiki/Ternary_search_tree">Ternary search tree</a> (Wikipedia)
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

        LZWNode lzwNode = getNode(rootLzwNode, key, 0);

        if (lzwNode == null) {
            return -1;
        } else {
            return lzwNode.getValue();
        }
    }

    private LZWNode getNode(LZWNode node, String key, int displacement) {

        if (node == null) {
            return null;
        }

        char character = key.charAt(displacement);

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

        if (data.length() == 0) {
            return null;
        }

        int length = 0;

        // Start at the root.
        LZWNode lzwNode = rootLzwNode;

        int index = 0;

        while (lzwNode != null && index < data.length()) {

            char character = data.charAt(index);

            if (character < lzwNode.getCharacter()) {
                lzwNode = lzwNode.getLeftNode();

            } else if (character > lzwNode.getCharacter()) {
                lzwNode = lzwNode.getRightNode();

            } else {

                index++;

                if (lzwNode.getValue() != -1) {
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
