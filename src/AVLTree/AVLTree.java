package AVLTree;

public class AVLTree {
    public static class Node {
        public Node(int value) {
            data = value;
        }

        private Node leftChild = null;
        private Node rightChild = null;
        private int height = 1;
        private final int data;
    }

    private Node root = null;
    private int size = 0;
    private int printCount = 0;

    public void populate() {
        for (int i = 1; i < 8; ++i) {
            insert(i);
        }
    }

    public boolean contains(int value) {
        return contains(root, value);
    }

    public void insert(int value) {
        root = insert(root, value);
    }

    public void delete(int value) {
        root = delete(root, value);
    }

    public void printNodesByInOrder() {
        printCount = 0;
        printHeader();
        printInOrder(root);
        printFooter();
    }

    public void printNodesByPreOrder() {
        printHeader();
        printPreOrder(root);
        printFooter();
    }

    public void printNodesByPostOrder() {
        printCount = 0;
        printHeader();
        printPostOrder(root);
        printFooter();
    }

    private boolean contains(Node node, int value) {
        if (node == null) {
            return false;
        }

        if (node.data == value) {
            return true;
        }

        return contains(node.leftChild, value) || contains(node.rightChild, value);
    }

    private Node delete(Node node, int value) {
        if (node == null) {
            return node;
        }

        if (value < node.data) {
            node.leftChild = delete(node.leftChild, value);
        } else if (value > node.data) {
            node.rightChild = delete(node.rightChild, value);
        } else if (node.leftChild == null || node.rightChild == null){
            Node temp = node.leftChild != null ? node.leftChild : node.rightChild;
            // Working Here
        }
    }

    private void printHeader() {
        System.out.println("Tree contains " + size + " elements:");
        System.out.print('{');
    }

    private void printFooter() {
        System.out.println(" }\n");
    }

    private void printInOrder(Node node) {

        if (node == null) {
            return;
        }

        if (node.leftChild != null) {
            printInOrder(node.leftChild);
        }

        System.out.print(" " + node.data);
        ++printCount;

        if (printCount < size) {
            System.out.print(",");
        }

        if (node.rightChild != null) {
            printInOrder(node.rightChild);
        }
    }

    private void printPreOrder(Node node) {
        if (node == null) {
            return;
        }

        System.out.print(" " + node.data);

        if (node.leftChild != null) {
            System.out.print(",");
            printPreOrder(node.leftChild);
        }

        if (node.rightChild != null) {
            System.out.print(",");
            printPreOrder(node.rightChild);
        }
    }

    private void printPostOrder(Node node) {
        if (node == null) {
            return;
        }

        if (node.leftChild != null) {
            printPostOrder(node.leftChild);
        }

        if (node.rightChild != null) {
            printPostOrder(node.rightChild);
        }

        System.out.print(" " + node.data);
        ++printCount;

        if (printCount < size) {
            System.out.print(",");
        }
    }

    private Node insert(Node node, int value) {
        if (node == null) {
            ++size;
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
