package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * This class is used for calculating area or cicumfence of a rectangle. Width and height of rectangle can be given
 * through command line or via console.
 */
public class Rectangle {

    /**
     * Main method.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {

        if (args.length == 0) {
            noArguments();
        } else if (args.length == 2) {
            twoArguments(args[0], args[1]);
        } else {
            System.out.println("Invalid number of arguments!");
        }

    }

    /**
     * This method is used for calculating rectangle for command line arguments.
     *
     * @param widthString  Width argument
     * @param heightString Height argument
     */
    public static void twoArguments(String widthString, String heightString) {

        try {

            double width = Double.parseDouble(widthString);
            double height = Double.parseDouble(heightString);

            System.out.println("Pravokutnik širine " + width + " i visine " + height + " ima površinu " + area(width,
                    height) + " te opseg " + circumfence(width, height) + ".");
        } catch (NumberFormatException ex) {
            System.out.println("Argumenti zadani preko komandnog retka se ne mogu protumačiti kao broj.");
        }

    }

    /**
     * This method i used for calculating rectangle which height are with have to be entered from console.
     */
    public static void noArguments() {
        double width = 0.0;
        double height = 0.0;
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.print("Unesite širinu > ");

            String input = sc.next();

            try {
                width = Double.parseDouble(input);

                if (width < 0) {
                    System.out.println("Unijeli ste negativnu vrijednost.");
                    continue;
                }

                break;
            } catch (NumberFormatException ex) {
                System.out.println("'" + input + "' se ne može protumačiti kao broj.");
            }
        }
        while (true) {
            System.out.print("Unesite visinu > ");

            String input = sc.next();

            try {
                height = Double.parseDouble(input);

                if (height < 0) {
                    System.out.println("Unijeli ste negativnu vrijednost.");
                    continue;
                }
                break;
            } catch (NumberFormatException ex) {
                System.out.println("'" + input + "' se ne može protumačiti kao broj.");
            }
        }
        System.out.println("Pravokutnik širine " + width + " i visine " + height + " ima površinu " + area(width,
                height) +
                " te opseg " + circumfence(width, height) + ".");

        sc.close();
    }

    /**
     * This method is used for calculating circumfence of a rectangle.
     *
     * @param width  Width of a rectangle
     * @param height Height of a rectangle
     * @return Circumfence of a rectangle
     */
    public static double circumfence(double width, double height) {
        return 2 * (width + height);
    }

    /**
     * This method is used for calculating area of a rectangle.
     *
     * @param width  Width of a rectangle
     * @param height Height of a rectangle
     * @return Area of a rectangle
     */
    public static double area(double width, double height) {
        return width * height;
    }

}
