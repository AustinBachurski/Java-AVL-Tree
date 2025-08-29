import AVLTree.AVLTree;

public class Main {
    public static void main(String[] args) {
        AVLTree tree = new AVLTree();

        tree.populate();


        System.out.println("**Pre Order**");
        tree.printNodesByPreOrder();
        System.out.println("\n**In Order**");
        tree.printNodesByInOrder();
        System.out.println("\n**Post Order**");
        tree.printNodesByPostOrder();
    }
}