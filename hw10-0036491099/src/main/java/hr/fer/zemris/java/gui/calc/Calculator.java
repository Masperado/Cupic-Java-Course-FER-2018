package hr.fer.zemris.java.gui.calc;

import hr.fer.zemris.java.gui.layout.CalcLayout;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * This class represents calculator. It is used as a basic Windows XP calculator.
 */
public class Calculator extends JFrame {

    /**
     * Logic of a calculator.
     */
    private CalcModel calcModel = new CalcModelImpl();

    /**
     * List of operator that have inverse operations.
     */
    private List<OperatorButton> inverseOperations = new ArrayList<>();

    /**
     * Stack of this calculator.
     */
    private Stack<Double> calcStack = new Stack<>();

    /**
     * Basic constructor.
     */
    public Calculator() {
        super();
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Calculator");
        setLocation(20, 20);
        setSize(500, 200);
        initGUI();
    }

    /**
     * This class is used for initializing graphical user interface.
     */
    private void initGUI() {
        Container cp = getContentPane();

        JPanel p = new JPanel(new CalcLayout(3));
        p.setBackground(Color.WHITE);

        JLabel ekran = new JLabel(calcModel.toString());
        ekran.setBackground(Color.YELLOW);
        ekran.setHorizontalAlignment(SwingConstants.RIGHT);
        ekran.setOpaque(true);
        calcModel.addCalcValueListener(model -> ekran.setText(model.toString()));
        p.add(ekran, "1,1");

        p.add(new OperatorButton("0", e -> calcModel.insertDigit(0), null), "5,3");
        p.add(new OperatorButton("1", e -> calcModel.insertDigit(1), null), "4,3");
        p.add(new OperatorButton("2", e -> calcModel.insertDigit(2), null), "4,4");
        p.add(new OperatorButton("3", e -> calcModel.insertDigit(3), null), "4,5");
        p.add(new OperatorButton("4", e -> calcModel.insertDigit(4), null), "3,3");
        p.add(new OperatorButton("5", e -> calcModel.insertDigit(5), null), "3,4");
        p.add(new OperatorButton("6", e -> calcModel.insertDigit(6), null), "3,5");
        p.add(new OperatorButton("7", e -> calcModel.insertDigit(7), null), "2,3");
        p.add(new OperatorButton("8", e -> calcModel.insertDigit(8), null), "2,4");
        p.add(new OperatorButton("9", e -> calcModel.insertDigit(9), null), "2,5");
        p.add(new OperatorButton("+/-", e -> calcModel.swapSign(), null), "5,4");
        p.add(new OperatorButton(".", e -> calcModel.insertDecimalPoint(), null), "5,5");
        p.add(new OperatorButton("clr", e -> calcModel.clear(), null), "1,7");
        p.add(new OperatorButton("res", e -> calcModel.clearAll(), null), "2,7");
        p.add(new OperatorButton("push", e -> calcStack.push(calcModel.getValue()), null), "3,7");
        p.add(new OperatorButton("pop", e -> {
            if (calcStack.isEmpty()) {
                calcModel.error("Stack is empty!");
            } else {
                calcModel.setValue(calcStack.pop());
            }
        }, null), "4,7");

        JCheckBox inverse = new JCheckBox("Inv");
        inverse.addItemListener(e -> {
            for (OperatorButton operator : inverseOperations) {
                operator.setInverse(inverse.isSelected());
            }
        });
        inverse.setBackground(Color.BLUE);
        inverse.setForeground(Color.WHITE);
        p.add(inverse, "5,7");


        OperatorButton sin = new OperatorButton("sin", e -> calcModel.setValue(Math.sin(calcModel.getValue())),
                e -> calcModel.setValue(Math.asin(calcModel.getValue())));
        inverseOperations.add(sin);
        p.add(sin, "2,2");

        OperatorButton cos = new OperatorButton("cos", e -> calcModel.setValue(Math.cos(calcModel.getValue())),
                e -> calcModel.setValue(Math.acos(calcModel.getValue())));
        inverseOperations.add(cos);
        p.add(cos, "3,2");

        OperatorButton tan = new OperatorButton("tan", e -> calcModel.setValue(Math.tan(calcModel.getValue())),
                e -> calcModel.setValue(Math.atan(calcModel.getValue())));
        inverseOperations.add(tan);
        p.add(tan, "4,2");

        OperatorButton ctg = new OperatorButton("ctg", e -> calcModel.setValue(1.0 / Math.tan(calcModel.getValue())),
                e -> calcModel.setValue(Math.PI / 2 - Math.atan(calcModel.getValue())));
        inverseOperations.add(ctg);
        p.add(ctg, "5,2");

        OperatorButton log = new OperatorButton("log", e -> calcModel.setValue(Math.log10(calcModel.getValue())),
                e -> calcModel.setValue(Math.pow(10, calcModel.getValue())));
        inverseOperations.add(log);
        p.add(log, "3,1");

        OperatorButton ln = new OperatorButton("ln", e -> calcModel.setValue(Math.log(calcModel.getValue())),
                e -> calcModel.setValue(Math.pow(Math.E, calcModel.getValue())));
        inverseOperations.add(ln);
        p.add(ln, "4,1");

        OperatorButton reciprot = new OperatorButton("1/x", e -> calcModel.setValue(1.0 / (calcModel.getValue())),
                null);
        inverseOperations.add(reciprot);
        p.add(reciprot, "2,1");

        OperatorButton plus = new OperatorButton("+", e -> {
            BinaryOperatorChecker();
            calcModel.setPendingBinaryOperation((left, right) -> left + right);
        }, null);
        inverseOperations.add(plus);
        p.add(plus, "5,6");

        OperatorButton mul = new OperatorButton("*", e -> {
            BinaryOperatorChecker();
            calcModel.setPendingBinaryOperation((left, right) -> left * right);
        }, null);
        inverseOperations.add(mul);
        p.add(mul, "3,6");

        OperatorButton div = new OperatorButton("/", e -> {
            BinaryOperatorChecker();
            calcModel.setPendingBinaryOperation((left, right) -> left / right);
        }, null);
        inverseOperations.add(div);
        p.add(div, "2,6");

        OperatorButton minus = new OperatorButton("-", e -> {
            BinaryOperatorChecker();
            calcModel.setPendingBinaryOperation((left, right) -> left - right);
        }, null);
        inverseOperations.add(minus);
        p.add(minus, "4,6");

        OperatorButton potency = new OperatorButton("x^n", e -> {
            BinaryOperatorChecker();
            calcModel.setPendingBinaryOperation(Math::pow);
        }, e -> {
            BinaryOperatorChecker();
            calcModel.setPendingBinaryOperation((left, right) -> Math.pow(left, 1 / right));
        });
        inverseOperations.add(potency);
        p.add(potency, "5,1");

        OperatorButton equals = new OperatorButton("=", e -> {
            if (calcModel.isActiveOperandSet() && calcModel.getPendingBinaryOperation() != null) {
                calcModel.setValue(calcModel.getPendingBinaryOperation().applyAsDouble(calcModel.getActiveOperand(),
                        calcModel.getValue()));
                calcModel.setPendingBinaryOperation(null);
            }
        }, null);
        inverseOperations.add(equals);
        p.add(equals, "1,6");


        cp.add(p);
        cp.setPreferredSize(p.getPreferredSize());
    }

    /**
     * This method is used for checking state of calculator when binary operator happened.
     */
    private void BinaryOperatorChecker() {
        if (calcModel.getPendingBinaryOperation() == null) {
            calcModel.setActiveOperand(calcModel.getValue());
            calcModel.setValue(calcModel.getValue());
        } else if (calcModel.isActiveOperandSet()) {
            calcModel.setValue(calcModel.getPendingBinaryOperation().applyAsDouble(calcModel.getActiveOperand(),
                    calcModel.getValue()));
            calcModel.setActiveOperand(calcModel.getValue());

        }
    }

    /**
     * Main method.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Calculator prozor = new Calculator();
            prozor.setVisible(true);
        });
    }
}
