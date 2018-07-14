package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * This class is used for calculating factorials. Numbers that user want to calculate factorial have to be betweeen 1
 * and 20. Numbers are entered in console.
 */
public class Factorial {

    /**
     * This method is used for calculating factorials.
     *
     * @param number Number which factorial will be calculated
     *
     * @return Factorial of the given number
     */
    public static long factorial(int number) {

        // I am not using recursion because of unnecessary allocation of memory
        long factorial = 1;
        while (number > 1) {
            factorial *= number;
            number--;
        }

        return factorial;
    }

    /**
     * Main method. It is run once program is started.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {

        String input="";
        Scanner sc = new Scanner(System.in);


        while (true) {
            System.out.print("Unesite broj > ");

            input = sc.next();

            if (input.equals("kraj")){
                break;
            }

            try {
                int number = Integer.parseInt(input);

                if (number < 1 || number > 20) {
                    System.out.println("'" + input + "' nije u dozvoljenom rasponu.");
                } else {
                    System.out.println(number + "! = " + factorial(number));
                }
            } catch (NumberFormatException ex) {
                System.out.println("'" + input + "' nije cijeli broj.");
            }
        }

        sc.close();
        System.out.println("Doviđenja.");
    }
}
