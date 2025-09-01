package AVLTree;

public class AVLTree {

    // Node object.
    public static class Node {
        // Node constructor.
        public Node(int value) {
            data = value;
        }

        // Node state.
        private Node leftChild = null;
        private Node rightChild = null;
        private int height = 1;
        private int data;
    }

    // Tree handle state.
    private Node root = null;
    private int size = 0;

    // The printCount variable is used to manage trailing commas when
    // printing the elements during "InOrder" or "PostOrder" traversal.
    private int printCount = 0;


// PUBLIC METHODS

    // Searches the tree for the specified value, returns true of the key is found.
    public boolean contains(int value) {
        return contains(root, value);
    }

    // Removes the specified value from the tree.
    public void delete(int value) {
        root = delete(root, value);
    }

    // Adds the specified value to the tree.
    public void insert(int value) {
        root = insert(root, value);
    }

    // Populates the tree with { 1, 2, 3, 4, 5, 6, 7 } per requirements.
    public void populate() {
        for (int i = 1; i < 8; ++i) {
            insert(i);
        }
    }

    // Displays the elements of the tree in "InOrder" traversal.
    public void printNodesByInOrder() {
        printCount = 0;
        printHeader();
        printInOrder(root);
        printFooter();
    }

    // Displays the elements of the tree in "PostOrder" traversal.
    public void printNodesByPostOrder() {
        printCount = 0;
        printHeader();
        printPostOrder(root);
        printFooter();
    }

    // Displays the elements of the tree in "PreOrder" traversal.
    public void printNodesByPreOrder() {
        printHeader();
        printPreOrder(root);
        printFooter();
    }

// PRIVATE METHODS

    // Returns the largest value of the left and right side heights.
    private int calculateHeight(Node node) {
        return Math.max(getHeightOf(node.leftChild), getHeightOf(node.rightChild)) + 1;
    }

    // Recursive method to search for a value in the tree.
    private boolean contains(Node node, int value) {
        if (node == null) {
            // Bottom of tree reached without finding the value, return false.
            return false;
        }

        if (node.data == value) {
            // Value found, return true.
            return true;
        }

        // Compare the value to the current node:
        // - Recurse left if less than the node value.
        // - Recurse right if greater than the node value.
        if (value < node.data) {
            return contains(node.leftChild, value);
        } else {
            return contains(node.rightChild, value);
        }
    }

    // Recursive method to delete a value from the tree.
    private Node delete(Node node, int value) {
        if (node == null) {
            return null;
        }

        if (value < node.data) {
            // Recurse left.
            node.leftChild = delete(node.leftChild, value);
        } else if (value > node.data) {
            // Recurse right.
            node.rightChild = delete(node.rightChild, value);
        } else {
            // Target found, decrement the tree size and remove the node.
            --size;

            if (node.leftChild == null && node.rightChild == null) {
                // If both left and right nodes are null, simply pop the target node.
                return null;
            } else if (node.leftChild == null || node.rightChild == null) {
                // If only one side is not null, simply replace the deleted
                // node with said non-null node.
                return node.leftChild != null ? node.leftChild : node.rightChild;
            } else {
                // If both children are non-null:
                // - Find the node with the smallest value.
                // - Set the current node to that value.
                // - Then remove said smallest value from the children to
                //   prevent duplicates.
                Node smallest = getSmallestChild(node.rightChild);
                node.data = smallest.data;
                node.rightChild = delete(node.rightChild, smallest.data);
            }
        }

        // As recursion unwinds:
        // - Update the height of the node.
        // - Check the balance factor.
        // - Perform rotations as necessary.
        node.height = calculateHeight(node);

        int balanceFactor = getBalanceFactorOf(node);

        if (Math.abs(balanceFactor) > 1) {
            return performRotation(node, balanceFactor);
        }

        return node;
    }

    // Returns the balance factor for a given node.
    private int getBalanceFactorOf(Node node) {
        return getHeightOf(node.leftChild) - getHeightOf(node.rightChild);
    }

    // Returns the height of a given node, returns zero if the node is null.
    private int getHeightOf(Node node) {
        return  node == null ? 0 : node.height;
    }

    // Recursive method for finding the smallest child of a given node.
    private Node getSmallestChild(Node node) {
        Node current = node;

        while (current.leftChild != null) {
            current = current.leftChild;
        }

        return current;
    }

    // Recursive method for adding a new value to the tree.
    private Node insert(Node node, int value) {
        // Empty node found, add a new node with the value and increment tree size.
        if (node == null) {
            ++size;
            return new Node(value);
        }

        if (value < node.data) {
            // Recurse left.
            node.leftChild = insert(node.leftChild, value);
        } else if (value > node.data) {
            // Recurse right.
            node.rightChild = insert(node.rightChild, value);
        }

        // As recursion unwinds:
        // - Update the height of the node.
        // - Check the balance factor.
        // - Perform rotations as necessary.
        node.height = calculateHeight(node);

        int balanceFactor = getBalanceFactorOf(node);

        if (Math.abs(balanceFactor) > 1) {
            return performRotation(node, balanceFactor);
        }

        return node;
    }

    // Determine and perform the appropriate rotation for the tree state.
    private Node performRotation(Node node, int balanceFactor) {
        // Retrieve the balance factor for the left or right child based
        // on the balance factor of this node.
        int unbalancedChildFactor = balanceFactor > 0
                ? getBalanceFactorOf(node.leftChild)
                : getBalanceFactorOf(node.rightChild);

        // Select the appropriate function based on node and child balance:
        // - Both nodes are positive -> rotate right.
        // - Both nodes are negative -> rotate left.
        // - One node's balance factor is positive while the other's is
        //   negative -> perform the appropriate left-right or right-left rotation.
        if (balanceFactor > 0) {
            if (unbalancedChildFactor > 0) {
                return rotateRight(node);
            } else {
                return rotateLeftRight(node);
            }
        } else {
            if (unbalancedChildFactor > 0) {
                return rotateRightLeft(node);
            } else {
                return rotateLeft(node);
            }
        }
    }

    // Display the closing brace and appropriate whitespace for printing the
    // elements of the tree.
    private void printFooter() {
        System.out.println(" }\n");
    }

    // Display the size information and opening brace for printing the
    // elements of the tree.
    private void printHeader() {
        System.out.println("Tree contains " + size + " elements:");
        System.out.print('{');
    }

    // Recursive method for printing the elements of the tree in "InOrder" traversal.
    private void printInOrder(Node node) {
        // End of this subtree reached, return;
        if (node == null) {
            return;
        }

        // Recurse on the left side of the subtree.
        if (node.leftChild != null) {
            printInOrder(node.leftChild);
        }

        System.out.print(" " + node.data);
        ++printCount;

        // The printCount variable is used to manage trailing commas when
        // printing the elements during "InOrder" or "PostOrder" traversal.
        if (printCount < size) {
            System.out.print(",");
        }

        // Recurse on the right side of the subtree.
        if (node.rightChild != null) {
            printInOrder(node.rightChild);
        }
    }

    // Recursive method for printing the elements of the tree in "PostOrder" traversal.
    private void printPostOrder(Node node) {
        // End of this subtree reached, return;
        if (node == null) {
            return;
        }

        // Recurse on the left side of the subtree.
        if (node.leftChild != null) {
            printPostOrder(node.leftChild);
        }

        // Recurse on the right side of the subtree.
        if (node.rightChild != null) {
            printPostOrder(node.rightChild);
        }

        System.out.print(" " + node.data);
        ++printCount;

        // The printCount variable is used to manage trailing commas when
        // printing the elements during "InOrder" or "PostOrder" traversal.
        if (printCount < size) {
            System.out.print(",");
        }
    }

    // Recursive method for printing the elements of the tree in "PreOrder" traversal.
    // Due to the nature of the traversal, the printCount variable is not used here.
    private void printPreOrder(Node node) {
        // End of this subtree reached, return;
        if (node == null) {
            return;
        }

        System.out.print(" " + node.data);

        // Recurse on the right side of the subtree.
        if (node.leftChild != null) {
            System.out.print(",");
            printPreOrder(node.leftChild);
        }

        // Recurse on the right side of the subtree.
        if (node.rightChild != null) {
            System.out.print(",");
            printPreOrder(node.rightChild);
        }
    }

    // Perform a left-left rotation.
    private Node rotateLeft(Node node) {
        // Perform the rotation.
        Node newRoot = node.rightChild;
        node.rightChild = newRoot.leftChild;
        newRoot.leftChild = node;

        // Update node heights following rotation.
        node.height = calculateHeight(node);
        newRoot.height = calculateHeight(newRoot);

        return newRoot;
    }

    // Perform a left-right rotation.
    private Node rotateLeftRight(Node node) {
        // Rotate the child node first, then the parent node.
        node.leftChild = rotateLeft(node.leftChild);
        return rotateRight(node);
    }

    // Perform a right-right rotation.
    private Node rotateRight(Node node) {
        // Perform the rotation.
        Node newRoot = node.leftChild;
        node.leftChild = newRoot.rightChild;
        newRoot.rightChild = node;

        // Update node heights following rotation.
        node.height = calculateHeight(node);
        newRoot.height = calculateHeight(newRoot);

        return newRoot;
    }

    // Perform a right-left rotation.
    private Node rotateRightLeft(Node node) {
        // Rotate the child node first, then the parent node.
        node.rightChild = rotateRight(node.rightChild);
        return rotateLeft(node);
    }
}

