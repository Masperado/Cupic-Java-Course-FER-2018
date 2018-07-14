package hr.fer.zemris.java.gui.calc;

import java.util.function.DoubleBinaryOperator;

/**
 * This interface represents calculator logic.
 */
public interface CalcModel {

    /**
     * This method is used for adding listener to calculator.
     *
     * @param l Listener
     */
    void addCalcValueListener(CalcValueListener l);

    /**
     * This method is used for removing listener from calculator.
     *
     * @param l listener
     */
    void removeCalcValueListener(CalcValueListener l);

    /**
     * This method is used for returning current String value of displayed value of calculator.
     *
     * @return String value of current displayed value
     */
    String toString();

    /**
     * This method is used for returning current Double value of displayed value of calculator.
     *
     * @return Double value of current displayed value
     */
    double getValue();

    /**
     * This method is used for setting new displayed value of calculator.
     *
     * @param value New value
     */
    void setValue(double value);

    /**
     * This method is used for clearing displayed value of calculator.
     */
    void clear();

    /**
     * This method is used for clearing displayed value of calculator and all data.
     */
    void clearAll();

    /**
     * This method is used for swapping sign of displayed value.
     */
    void swapSign();

    /**
     * This method is used for inserting decimal point to displayed value.
     */
    void insertDecimalPoint();

    /**
     * This method is used for writing digit to displayed value.
     *
     * @param digit Digit
     */
    void insertDigit(int digit);

    /**
     * This method is used for checking if active operand is set.
     *
     * @return True if it is, false otherwise
     */
    boolean isActiveOperandSet();

    /**
     * Getter for active operand.
     *
     * @return Active operand
     */
    double getActiveOperand();

    /**
     * Setter for active operand.
     *
     * @param activeOperand Active operand
     */
    void setActiveOperand(double activeOperand);

    /**
     * This method is used for clearing active operand.
     */
    void clearActiveOperand();

    /**
     * This method is used for getting current {@link DoubleBinaryOperator}.
     *
     * @return {@link DoubleBinaryOperator}
     */
    DoubleBinaryOperator getPendingBinaryOperation();

    /**
     * This method is used for setting current {@link DoubleBinaryOperator}.
     *
     * @param op {@link DoubleBinaryOperator}
     */
    void setPendingBinaryOperation(DoubleBinaryOperator op);

    /**
     * This method is used for changing calculator into error state.
     *
     * @param text Error text
     */
    void error(String text);
}