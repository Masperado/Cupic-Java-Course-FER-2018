package hr.fer.zemris.java.gui.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

/**
 * This class represents implementation of {@link CalcModel}. It is used as a main logic of {@link Calculator}.
 */
public class CalcModelImpl implements CalcModel {

    /**
     * List of listeners.
     */
    private List<CalcValueListener> listeners = new ArrayList<>();

    /**
     * Current displayed value.
     */
    private String numberString = null;

    /**
     * Active operand.
     */
    private double activeOperand;

    /**
     * Flag if active operand is set.
     */
    private boolean activeOperandSet = false;

    /**
     * Current pending operation.
     */
    private DoubleBinaryOperator pendingOperation;

    /**
     * Flag if value is just set.
     */
    private boolean valueJustSet = false;

    /**
     * Flag if error happened.
     */
    private boolean error = false;

    /**
     * This method is used for notifying all listeners that change has happened.
     */
    private void notifyListeners() {
        for (CalcValueListener l : listeners) {
            l.valueChanged(this);
        }
    }

    @Override
    public void addCalcValueListener(CalcValueListener l) {
        listeners.add(l);

    }

    @Override
    public void removeCalcValueListener(CalcValueListener l) {
        listeners.remove(l);
    }

    @Override
    public String toString() {
        if (numberString == null) {
            return "0";
        }


        while (numberString.length() > 1 && numberString.charAt(0) == '0' && numberString.charAt(1) == '0') {
            numberString = numberString.substring(1);
        }

        if (numberString.length() > 1 && numberString.charAt(0) == '0' && numberString.charAt(1) != '.') {
            numberString = numberString.substring(1);
        }

        if (numberString.endsWith(".0")) {
            numberString = numberString.substring(0, numberString.length() - 2);
        }

        return numberString;
    }

    @Override
    public double getValue() {
        if (error) {
            return 0.0;
        }
        if (numberString == null) {
            return 0.0;
        }
        return Double.parseDouble(numberString);
    }

    @Override
    public void setValue(double value) {
        if (error) {
            return;
        }

        if (Double.isInfinite(value) || Double.isNaN(value)) {
            error("Value error!");
            return;
        }
        numberString = String.valueOf(value);
        valueJustSet = true;
        notifyListeners();
    }

    @Override
    public void clear() {
        numberString = null;
        error = false;
        notifyListeners();
    }

    @Override
    public void clearAll() {
        numberString = null;
        error = false;
        activeOperand = 0.0;
        activeOperandSet = false;
        pendingOperation = null;
        notifyListeners();
    }

    @Override
    public void swapSign() {
        if (numberString != null) {
            if (numberString.startsWith("-")) {
                numberString = numberString.substring(1);
            } else {
                numberString = "-" + numberString;
            }
            notifyListeners();
        }
    }

    @Override
    public void insertDecimalPoint() {
        if (numberString != null) {
            if (!(numberString.contains("."))) {
                numberString += ".";
                notifyListeners();
            }
        } else {
            numberString = "0.";
        }
    }

    @Override
    public void insertDigit(int digit) {
        if (error) {
            numberString = null;
        }

        if (valueJustSet) {
            numberString = null;
            valueJustSet = false;
        }

        if (numberString != null) {
            String newNumberString = numberString + String.valueOf(digit);
            try {
                double newNumber = Double.parseDouble(newNumberString);
                if (Double.isFinite(newNumber)) {
                    numberString = newNumberString;
                }
            } catch (NumberFormatException ignored) {

            }

        } else {
            numberString = String.valueOf(digit);
        }
        notifyListeners();
    }

    @Override
    public boolean isActiveOperandSet() {
        return activeOperandSet;
    }

    @Override
    public double getActiveOperand() {
        if (!activeOperandSet) {
            throw new IllegalStateException();
        }

        return activeOperand;
    }

    @Override
    public void setActiveOperand(double activeOperand) {
        this.activeOperand = activeOperand;
        this.activeOperandSet = true;
    }

    @Override
    public void clearActiveOperand() {
        this.activeOperand = 0.0;
        this.activeOperandSet = false;
    }

    @Override
    public DoubleBinaryOperator getPendingBinaryOperation() {
        return pendingOperation;
    }

    @Override
    public void setPendingBinaryOperation(DoubleBinaryOperator op) {
        pendingOperation = op;
    }

    @Override
    public void error(String text) {
        numberString = text;
        error = true;
        notifyListeners();
    }
}
