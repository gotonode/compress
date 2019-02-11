import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class binary_search_tree {

    private static final boolean DEBUG = true;
    private static HashMap<Integer, ArrayList<Node>> gatheredNodes;

    private static Node root;

    private static Random random;

    private static enum TraversalOrder {
        // Three different ways to traverse a tree.
        Inorder, Preorder, Postorder
    }

    // Sample tree is [2, 3, 5, 7, 8, 9] in X order.
    public static void main(String[] args) {

        random = new Random();

        Node rootAlpha = new Node(100, null, null, null);
        addNode(100, rootAlpha);
        addNode(101, rootAlpha);
        addNode(99, rootAlpha);
        addNode(Integer.MAX_VALUE, rootAlpha);
        Node rootBeta = new Node(100, null, null, null);
        addNode(100, rootBeta);
        addNode(101, rootBeta);
        addNode(99, rootBeta);
        addNode(Integer.MIN_VALUE, rootBeta);

        boolean identical = identical(rootAlpha, rootBeta);

        root = new Node(100, null, null, null);

        int iterations = random.nextInt(10);

        for (int i = 0; i < iterations; i++) {
            root = addNode((random.nextInt(Integer.MAX_VALUE) + 1) / 100000, root);
        }

        printTree(root);

        gatheredNodes = new HashMap<>();
        gatherNodes(root, 0);

        printGatheredNodes();

        System.out.println("Node count is " + countNodes(root));
        System.out.println("Depth is " + countDepth(root));
        System.out.println("Greatest value is " + findGreatest(root).key);
        System.out.println("Least value is " + findLeast(root).key);

        boolean isBST = isBinarySearchTree(root, Integer.MIN_VALUE, Integer.MAX_VALUE);

        if (isBST) {
            System.out.print("Is a Binary Search Tree");

            if (isBalanced(root)) {
                System.out.println(" " + "and AVL");
            } else {
                System.out.println(" " + "and not balanced via AVL rules");
            }

        } else {
            System.out.println("Is NOT a Binary Search Tree (and thus can't be AVL either)");
        }

        continouslyTestTree();
    }

    private static void continouslyTestTree() {
        while (true) {
            Node testRoot = new Node(random.nextInt(Integer.MAX_VALUE));
            int iterations = random.nextInt((int) Math.pow(10, 4)) + 1;

            for (int i = 0; i < iterations; i++) {
                int nextValue = random.nextInt(Integer.MAX_VALUE);
                testRoot = addNode(nextValue, testRoot);
            }

            boolean isBST = isBinarySearchTree(testRoot, Integer.MIN_VALUE, Integer.MAX_VALUE);
            boolean isAVL = isBalanced(testRoot);

            if (isBST == false) {
                printTree(testRoot);
                gatheredNodes = new HashMap<>();
                gatherNodes(testRoot, 0);

                printGatheredNodes();
                throw new RuntimeException("Tree wasn't a Binary Search Tree!");
            } else {
                if (isAVL == false) {
                    throw new RuntimeException("Tree wasn't balanced via the AVL rules!");
                } else {
                    System.out.println("Tree was quite okay! Nodes: " + countNodes(testRoot)
                            + ", depth: " + countDepth(testRoot)
                            + ", min: " + findLeast(testRoot).key
                            + ", max: " + findGreatest(testRoot).key);
                }
            }
        }
    }

    private static Node findNode(int value, Node root) {

        if (root == null) {

            if (DEBUG) {
                System.out.println("Node with value " + value + " not found");
            }

            return null;
        }

        int rootValue = root.key;

        if (value == rootValue) {

            if (DEBUG) {
                System.out.println("Node found, had value " + value);
            }

            return root; // The root had the value we wanted, no need to search further.

        } else if (value < rootValue) {

            return findNode(value, root.left);

        } else if (value > rootValue) {

            return findNode(value, root.right);

        }

        throw new RuntimeException("Node search should never reach this point.");
    }

    private static Node addNode(int value, Node root) {

        if (root == null) {
            root = new Node(value);

        } else {
            if (value == root.key) {
                return root; // We don't allow duplicate keys.
            } else if (value < root.key) {
                root.left = addNode(value, root.left);
            } else { // value > root.getValue()
                root.right = addNode(value, root.right);
            }
        }

        root.height = Math.max(countDepth(root.left), countDepth(root.right)) + 1;

        int factor = countDepth(root.left) - countDepth(root.right);

        if (factor > 1) {
            if (value < root.left.key) {
                root = rotateRight(root);
            } else {
                root.left = rotateLeft(root.left);
                root = rotateRight(root);
            }
        } else if (factor < -1) {
            if (value > root.right.key) {
                root = rotateLeft(root);
            } else {
                root.right = rotateRight(root.right);
                root = rotateLeft(root);
            }
        }

        return root;
    }

    /**
     * Removes the specified node from the tree.
     *
     * @param node The node to be removed. Shouldn't be null.
     */
    private static void removeNode(Node node) {
        if (node == null) {
            throw new RuntimeException("Attempting to remove a null node. How can you remove that, which does not exist?");
        }
    }

    private static void printTree(Node root) {
        System.out.println("Printing tree (three different ways)");

        System.out.print(TraversalOrder.Inorder.name() + ": ");
        print(root, TraversalOrder.Inorder);
        System.out.println();

        System.out.print(TraversalOrder.Preorder.name() + ": ");
        print(root, TraversalOrder.Preorder);
        System.out.println();

        System.out.print(TraversalOrder.Postorder.name() + ": ");
        print(root, TraversalOrder.Postorder);
        System.out.println();

        System.out.println();
    }

    private static void setRoot(Node newRoot) {
        root = newRoot;
    }

    private static Node rotateRight(Node root) {

        Node leftChild = root.left;

        root.left = leftChild.right;

        leftChild.right = root;

        root.height = Math.max(countDepth(root.left), countDepth(root.right)) + 1;

        leftChild.height = Math.max(countDepth(leftChild.left), countDepth(leftChild.right)) + 1;

        return leftChild;
    }

    private static Node rotateLeft(Node root) {

        Node rightChild = root.right;

        root.right = rightChild.left;

        rightChild.left = root;

        root.height = Math.max(countDepth(root.left), countDepth(root.right)) + 1;

        rightChild.height = Math.max(countDepth(rightChild.left), countDepth(rightChild.right)) + 1;

        return rightChild;
    }

    private static Node findPredecessor(Node root, int value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private static Node findSuccessor(Node root, int value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private static Node findGreatest(Node root) {

        Node right = root.right;

        if (right == null) {
            return root;
        } else {
            return findGreatest(right);
        }
    }

    private static Node findLeast(Node root) {

        Node left = root.left;

        if (left == null) {
            return root;
        } else {
            return findLeast(left);
        }
    }

    /**
     * This function is called recursively.
     *
     * @param root The root node to begin our search from.
     */
    private static void print(Node root, TraversalOrder order) {
        if (root == null) {
            return;
        }

        switch (order) {
            case Inorder:
                print(root.left, order);
                System.out.print(root.key + " ");
                print(root.right, order);
                break;
            case Preorder:
                System.out.print(root.key + " ");
                print(root.left, order);
                print(root.right, order);
                break;
            case Postorder:
                print(root.left, order);
                print(root.right, order);
                System.out.print(root.key + " ");
                break;
            default:
                throw new AssertionError(order.name());
        }
    }

    private static void printGatheredNodes() {
        for (Map.Entry<Integer, ArrayList<Node>> pair : gatheredNodes.entrySet()) {
            System.out.print("Depth " + pair.getKey() + ": ");

            @SuppressWarnings("unchecked")
            ArrayList<Node> list = pair.getValue();
            list.forEach((node) -> {
                System.out.print("[" + node.key + "] ");
            });
            System.out.println();
        }
        System.out.println();
    }

    private static void gatherNodes(Node root, int depth) {
        if (root == null) {
            return;
        }

        gatherNodes(root.left, depth + 1);

        if (gatheredNodes.containsKey(depth) == false) {
            gatheredNodes.put(depth, new ArrayList<>());
        }

        gatheredNodes.get(depth).add(root);

        gatherNodes(root.right, depth + 1);
    }

    private static int countNodes(Node root) {

        if (root == null) {
            return 0;
        }

        return 1 + countNodes(root.left) + countNodes(root.right);
    }

    private static int countDepth(Node root) {

        if (root == null) {
            return -1;
        }

        return 1 + Math.max(countDepth(root.left), countDepth(root.right));
    }

    private static boolean isBalanced(Node root) {
        if (root == null) {
            return true;
        }

        int depthLeft = countDepth(root.left);
        int depthRight = countDepth(root.right);

        if (Math.abs(depthLeft - depthRight) <= 1
                && isBalanced(root.left)
                && isBalanced(root.right)) {
            return true;
        }

        return false;
    }

    /**
     * Compares two trees, and reports true if they are identical.
     *
     * @param alpha The first tree's root.
     * @param beta The second tree's root.
     * @return True if identical, false otherwise.
     */
    private static boolean identical(Node alpha, Node beta) {
        if (alpha == null || beta == null) {
            return (alpha == null && beta == null);
        }
        if (alpha.key != beta.key) {
            return false;
        }
        return identical(alpha.left, beta.left) && identical(alpha.right, beta.right);
    }

    private static boolean isBinarySearchTree(Node root, int min, int max) {
        if (root == null) {
            return true;
        }

        if (root.key < min || root.key > max) {
            return false;
        }

        return (isBinarySearchTree(root.left, min, root.key - 1)
                && isBinarySearchTree(root.right, root.key + 1, max));
    }

    /**
     * This class is used to represent a node in the tree structure.
     */
    private static class Node {

        int key, height;
        Node parent, left, right;

        Object data; // Optional. May house any object.

        /**
         * Create a new Node with the given values.
         *
         * @param key The key of the Node, an integer.
         * @param parent The parent if this is not the root; null otherwise.
         * @param left The left child if present; null otherwise.
         * @param right The right child if present; null otherwise.
         */
        Node(int key, Node parent, Node left, Node right) {
            this.key = key;
            this.parent = parent;
            this.left = left;
            this.right = right;
            this.height = 1;
        }

        /**
         * Create a new Node with just the key. Other values can be given later.
         *
         * @param key The key of the Node, an integer.
         */
        Node(int key) {
            this.key = key;
            this.parent = null;
            this.left = null;
            this.right = null;
            this.height = 1;
        }

        @Override
        public String toString() {
            return "Node{" + "value=" + key + '}';
        }
    }
}
