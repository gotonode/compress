package io.github.gotonode.compress.algorithms.lzw;

/**
 *
 */
class LZWTree {

    private int n;
    private LZWNode rootLzwNode;

    /**
     * @param key srht
     * @return
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

    void add(char key, int value) {
        add(String.valueOf(key), value);
    }

    void add(String key, int value) {
        if (get(key) != -1) {
            n++;
        }

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

}
