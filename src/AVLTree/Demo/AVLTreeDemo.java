package AVLTree.Demo;

import AVLTree.AVLTree;
import java.util.Scanner;

public class AVLTreeDemo {
    // Handle for the tree data structure.
    AVLTree tree = null;

    // Scanner to read from the console.
    Scanner stdin = new Scanner(System.in);

    // Begins the main loop of the demo.
    public void start() {
        System.out.println("Welcome to the interactive demo for my AVL Tree implementation.");

        printMenu();

        while (true)
        {
            switch (readNumberFromConsole("Please make a numeric selection: "))
            {
                // Create a binary search tree.
                case 1:
                    if (tree == null) {
                        createTree();
                    } else if (confirmDestroy()){
                        createTree();
                    } else {
                        clearScreen();
                        System.out.println("The old tree remains.\n");
                    }
                    break;

                // Add a node.
                case 2:
                    if (tree == null) {
                        warnNull();
                        continue;
                    }
                    addNode();
                    break;

                // Delete a node.
                case 3:
                    if (tree == null) {
                        warnNull();
                        continue;
                    }
                    deleteNode();
                    break;

                // Print nodes by InOrder.
                case 4:
                    if (tree == null) {
                        warnNull();
                        continue;
                    }
                    printInOrder();
                    break;

                // Print nodes by PreOrder.
                case 5:
                    if (tree == null) {
                        warnNull();
                        continue;
                    }
                    printPreOrder();
                    break;

                // Print nodes by PostOrder.
                case 6:
                    if (tree == null) {
                        warnNull();
                        continue;
                    }
                    printPostOrder();
                    break;

                // Exit program.
                case 7:
                    return;

                // Request a valid number and read input again.
                default:
                    System.out.println("Please make a valid selection, try again.");
                    continue;
            }
            printMenu();
        }
    }

    // Request and validate a number from the console and add it to the tree.
    private void addNode() {
        clearScreen();
        int value = readNumberFromConsole("Enter the value you wish to add: ");

        if (tree.contains(value))
        {
            clearScreen();
            System.out.println("The tree already contains the value " + value + ".\n");
            return;
        }

        tree.insert(value);

        //clearScreen();
        System.out.println("Value " + value + " inserted into the tree.\n");
    }

    // Clear the console.
    private  void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // If the user tries to create a tree when one already exists, warn that
    // data will be lost and confirm before destroying the existing tree.
    private boolean confirmDestroy() {
        System.out.println("A tree already exists, if you create a new one, data will be lost.");
        return readBoolFromConsole("Destroy it? (y/n): ");
    }

    // Create a new tree and populate it with { 1, 2, 3, 4, 5, 6, 7 } per requirements.
    private void createTree() {
        clearScreen();

        tree = new AVLTree();
        tree.populate();

        System.out.println("New tree created and populated.\n");
    }

    // Request and validate a number from the console and remove it from the tree.
    private void deleteNode() {
        clearScreen();
        int value = readNumberFromConsole("Enter the value you wish to remove: ");

        if (!tree.contains(value))
        {
            clearScreen();
            System.out.println("The tree does not contain the value " + value + ".\n");
            return;
        }

        tree.delete(value);

        clearScreen();
        System.out.println("Value " + value + " removed from the tree.\n");
    }

    // Displays the elements of the tree in "InOrder" traversal.
    private void printInOrder() {
        clearScreen();
        tree.printNodesByInOrder();
    }

    // Displays the operation menu.
    private  void printMenu() {
        System.out.println("What would you like to do?");
        System.out.println("1. Create a binary search tree");
        System.out.println("2. Add a node");
        System.out.println("3. Delete a node");
        System.out.println("4. Print nodes by InOrder");
        System.out.println("5. Print nodes by PreOrder");
        System.out.println("6. Print nodes by PostOrder");
        System.out.println("7. Exit program");
    }

    // Displays the elements of the tree in "PostOrder" traversal.
    private void printPostOrder() {
        clearScreen();
        tree.printNodesByPostOrder();
    }

    // Displays the elements of the tree in "PreOrder" traversal.
    private void printPreOrder() {
        clearScreen();
        tree.printNodesByPreOrder();
    }

    // Read a valid yes/no from the console and return it's boolean equivalent.
    private boolean readBoolFromConsole(String prompt) {
        System.out.println(prompt);

        while (true) {
            String input = stdin.nextLine();

            if (input.length() == 1) {
                if (Character.toLowerCase(input.charAt(0)) == 'y') {
                    return true;
                }
                if (Character.toLowerCase(input.charAt(0)) == 'n') {
                    return false;
                }
            }

            System.out.println("Please enter 'y' or 'n': ");
        }
    }

    // Read a valid number from the console and return said number.
    private int readNumberFromConsole(String prompt) {
        System.out.print(prompt);

        while (true) {
            String input = stdin.nextLine();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Integers only please, enter a number: ");
            }
        }
    }

    // Warns the user that the tree does not exist if it has not been created yet.
    private  void warnNull() {
        System.out.println("Please create a tree before trying to interact with said tree.");
    }
}

