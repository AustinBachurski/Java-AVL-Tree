package AVLTree.Demo;

import AVLTree.AVLTree;

import java.util.Scanner;

public class AVLTreeDemo {
    AVLTree tree = null;

    public void start() {
        System.out.println("Welcome to the interactive demo for my AVL Tree implementation.");

        printMenu();

        String prompt = "Please make a numeric selection: ";

        while (true)
        {
            switch (readNumberFromConsole(prompt))
            {
                //Create a binary search tree
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

                //Add a node
                case 2:
                    if (tree == null) {
                        warnNull();
                        continue;
                    }
                    addNode();
                    break;

                //Delete a node
                case 3:
                    if (tree == null) {
                        warnNull();
                        continue;
                    }
                    deleteNode();
                    break;

                //Print nodes by InOrder
                case 4:
                    if (tree == null) {
                        warnNull();
                        continue;
                    }
                    printInOrder();
                    break;

                //Print nodes by PreOrder
                case 5:
                    if (tree == null) {
                        warnNull();
                        continue;
                    }
                    printPreOrder();
                    break;

                //Print nodes by PostOrder
                case 6:
                    if (tree == null) {
                        warnNull();
                        continue;
                    }
                    printPostOrder();
                    break;

                //Exit program
                case 7:
                    return;

                default:
                    System.out.println("Please make a valid selection, try again.");
                    continue;
            }
            printMenu();
        }
    }

    private  void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

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

        clearScreen();
        System.out.println("Value " + value + " inserted into the tree.\n");
    }

    private boolean confirmDestroy() {
        System.out.println("A tree already exists, if you create a new one, data will be lost.");
        return readBoolFromConsole("Destroy it? (y/n): ");
    }

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

    private void printInOrder() {
        clearScreen();
        tree.printNodesByInOrder();
    }

    private void printPreOrder() {
        clearScreen();
        tree.printNodesByPreOrder();
    }

    private void printPostOrder() {
        clearScreen();
        tree.printNodesByPostOrder();
    }

    private void createTree() {
        clearScreen();

        tree = new AVLTree();
        tree.populate();

        System.out.println("New tree created and populated.\n");
    }

    private boolean readBoolFromConsole(String prompt) {
        System.out.println(prompt);

        Scanner stdin = new Scanner(System.in);

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

    private int readNumberFromConsole(String prompt) {
        System.out.print(prompt);
        Scanner stdin = new Scanner(System.in);

        while (true) {
            String input = stdin.nextLine();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Integers only please, enter a number: ");
            }
        }
    }

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

    private  void warnNull() {
        System.out.println("Please create a tree before trying to interact with said tree.");
    }
}
