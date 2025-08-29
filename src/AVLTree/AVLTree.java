package AVLTree;

public class AVLTree {
    public static class Node {
        Node leftChild = null;
        Node rightChild = null;
        int height = 1;
        int data;

        public Node(int value) {
            data = value;
        }
    }

    Node root = null;

    public void populate() {
        for (int i = 1; i < 8; ++i) {
            insert(i);
        }
    }

    public void insert(int value) {
        root = insert(root, value);
    }

    public void deleteNode(int value) {

    }

    public void printNodesByInOrder() {

    }

    public void printNodesByPreOrder() {

    }

    public void printNodesByPostOrder() {

    }

    private Node insert(Node node, int value) {
        if (node == null) {
            return new Node(value);
        }

        if (value < node.data) {
            node.leftChild = insert(node.leftChild, value);
        } else if (value > node.data) {
            node.rightChild = insert(node.rightChild, value);
        } else {
            return node;
        }

        node.height = calculateHeight(node);

        int balanceFactor = getBalanceFactorOf(node);

        if (Math.abs(balanceFactor) > 1) {
            return performRotation(node, balanceFactor);
        }

        return node;
    }

    private int calculateHeight(Node node) {
        return Math.max(getHeightOf(node.leftChild), getHeightOf(node.rightChild)) + 1;
    }

    private int getHeightOf(Node node) {
        return  node == null ? 0 : node.height;
    }

    private int getBalanceFactorOf(Node node) {
        return getHeightOf(node.leftChild) - getHeightOf(node.rightChild);
    }

    private Node performRotation(Node node, int balanceFactor) {
        int unbalancedChildFactor = balanceFactor > 0
                ? getBalanceFactorOf(node.leftChild) : getBalanceFactorOf(node.rightChild);

        if (balanceFactor > 0) {
            if (unbalancedChildFactor > 0) {
                return rotateRight(node);
            } else {
                return rotateRightLeft(node);
            }
        } else {
            if (unbalancedChildFactor > 0) {
                return rotateLeftRight(node);
            } else {
                return rotateLeft(node);
            }
        }
    }

    private Node rotateRight(Node node) {
        Node newRoot = node.leftChild;
        node.leftChild = newRoot.rightChild;
        newRoot.rightChild = node;

        node.height = calculateHeight(node);
        newRoot.height = calculateHeight(newRoot);

        return newRoot;
    }

    private Node rotateLeft(Node node) {
        Node newRoot = node.rightChild;
        node.rightChild = newRoot.leftChild;
        newRoot.leftChild = node;

        node.height = calculateHeight(node);
        newRoot.height = calculateHeight(newRoot);

        return newRoot;
    }

    private Node rotateRightLeft(Node node) {
        return rotateLeft(rotateRight(node.rightChild));
    }

    private Node rotateLeftRight(Node node) {
        return rotateRight(rotateLeft(node.leftChild));
    }

}
