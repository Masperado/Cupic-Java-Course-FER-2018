package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * This class is used for sorting numbers using binary tree. Numbers are entered from console.
 */
public class UniqueNumbers {

    /**
     * This class represents node of binary tree.
     */
    static class TreeNode {

        /**
         * Left child of node.
         */
        TreeNode left;

        /**
         * Right child of node.
         */
        TreeNode right;

        /**
         * Value of node.
         */
        int value;
    }

    /**
     * This method is used for adding new nodes to tree.
     *
     * @param glava Root node
     * @param value Value of new node
     * @return Updated head
     */
    public static TreeNode addNode(TreeNode glava, int value) {
        if (glava == null) {
            TreeNode glavaNova = new TreeNode();
            glavaNova.value = value;
            return glavaNova;
        }

        if (value < glava.value) {
            glava.left = addNode(glava.left, value);
        } else if (value == glava.value) {
            throw new RuntimeException("Vrijednost već postoji u stablu!");
        } else {
            glava.right = addNode(glava.right, value);
        }

        return glava;
    }

    /**
     * This method is used for calculating size of tree.
     *
     * @param glava Root
     * @return Size of tree
     */
    public static int treeSize(TreeNode glava) {
        if (glava == null) {
            return 0;
        }

        return 1 + treeSize(glava.left) + (treeSize(glava.right));
    }

    /**
     * This method is used for checking if tree contains given value.
     *
     * @param glava Root node
     * @param value Value to be checked
     * @return True if contains, false otherwise
     */
    public static boolean containsValue(TreeNode glava, int value) {
        if (glava == null) {
            return false;
        }

        return glava.value == value || containsValue(glava.left, value) || containsValue(glava.right, value);
    }

    /**
     * This method is used for printing tree ascending.
     *
     * @param glava Root node
     */
    public static void printSorted(TreeNode glava) {
        if (glava == null) {
            return;
        }

        if (glava.left != null) {
            printSorted(glava.left);
        }
        System.out.print(glava.value + " ");
        if (glava.right != null) {
            printSorted(glava.right);
        }
    }


    /**
     * This method is used for printing tree descending.
     *
     * @param glava Root node
     */
    public static void printReverseSorted(TreeNode glava) {
        if (glava == null) {
            return;
        }

        if (glava.right != null) {
            printReverseSorted(glava.right);
        }
        System.out.print(glava.value + " ");
        if (glava.left != null) {
            printReverseSorted(glava.left);
        }
    }

    /**
     * Main method.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        String input = "";

        Scanner sc = new Scanner(System.in);

        TreeNode glava = null;

        while (true) {
            System.out.print("Unesite broj > ");

            input = sc.next();

            if (input.equals("kraj")) {
                System.out.print("Ispis od najmanjeg: ");
                printSorted(glava);
                System.out.println();
                System.out.print("Ispis od najvećeg: ");
                printReverseSorted(glava);
                break;
            }

            try {
                int value = Integer.parseInt(input);

                if (containsValue(glava, value)) {
                    System.out.println("Broj već postoji. Preskačem.");
                } else {
                    glava = addNode(glava, Integer.parseInt(input));
                    System.out.println("Dodano.");
                }
            } catch (NumberFormatException ex) {
                System.out.println("'" + input + "' nije cijeli broj.");
            }
        }

        sc.close();
    }
}
