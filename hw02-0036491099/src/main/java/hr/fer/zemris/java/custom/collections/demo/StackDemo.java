package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * This class is used for calculating expressions in postfix notation using {@link ObjectStack}. Expressions are given as a
 * string
 * through command line. Result is written in console.
 */
public class StackDemo {

    /**
     * Main method.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Neispravan broj argumenata!");
            System.exit(1);
        }

        // Elements of expression
        String[] elements = args[0].split("\\s+");

        ObjectStack stack = new ObjectStack();

        for (String element : elements) {

            // First checking operands, then numbers
            // Everything is in try catch because you can't know when EmptyStackException will be thrown
            try {
                switch (element) {
                    case "+": {
                        int secondOperand = (int) stack.pop();
                        int firstOperand = (int) stack.pop();
                        stack.push(firstOperand + secondOperand);
                        break;
                    }
                    case "-": {
                        int secondOperand = (int) stack.pop();
                        int firstOperand = (int) stack.pop();
                        stack.push(firstOperand - secondOperand);
                        break;
                    }
                    case "/": {
                        int secondOperand = (int) stack.pop();
                        int firstOperand = (int) stack.pop();

                        if (secondOperand == 0) {
                            System.out.println("Djeljenje s nulom nije moguće!");
                            System.exit(1);
                        }

                        stack.push(firstOperand / secondOperand);
                        break;
                    }
                    case "*": {
                        int secondOperand = (int) stack.pop();
                        int firstOperand = (int) stack.pop();
                        stack.push(firstOperand * secondOperand);
                        break;
                    }
                    case "%": {
                        int secondOperand = (int) stack.pop();
                        int firstOperand = (int) stack.pop();
                        stack.push(firstOperand % secondOperand);
                        break;
                    }
                    default:
                        try {
                            int number = Integer.parseInt(element);
                            stack.push(number);
                        } catch (NumberFormatException ex) {
                            System.out.println("'" + element + "' se ne može protumačiti niti kao broj niti kao operand!");
                        }
                        break;
                }
            } catch (EmptyStackException ex) {
                invalidExpression(args[0]);

            }

        }

        if (stack.size() != 1) {
            invalidExpression(args[0]);
        }

        System.out.println("Expression evaluates to " + stack.pop() + ".");

    }

    /**
     * This method is used for printing invalid expressions.
     *
     * @param expression Expression
     */
    public static void invalidExpression(String expression) {
        System.out.println("Izraz '" + expression + "' nije ispravan!");
        System.exit(1);
    }

}