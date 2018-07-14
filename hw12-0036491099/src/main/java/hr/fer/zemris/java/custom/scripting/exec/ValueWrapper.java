package hr.fer.zemris.java.custom.scripting.exec;

import java.util.function.BiFunction;

/**
 * This class represents ValueWrapper. It is used for wrapping values of types {@link Integer}, {@link Double},
 * {@link String} or null values. It supports basic arithmetic operations.
 */
public class ValueWrapper {

    /**
     * Value of wrapper.
     */
    private Object value;

    /**
     * Basic constructor.
     *
     * @param value Value of wrapper
     * @throws RuntimeException If type of given value is not supported
     */
    public ValueWrapper(Object value) {
        if (!(value == null || value instanceof Integer || value instanceof Double || value instanceof String)) {
            throw new RuntimeException("Tip podatka nije podržan!");
        }

        this.value = value;
    }

    /**
     * Getter for value.
     *
     * @return Value
     */
    public Object getValue() {
        return value;
    }

    /**
     * This method is used for getting double value of wrapper.
     *
     * @return Double value of wrapper
     */
    public double doubleValue() {
        return getValue(this.value);
    }

    @Override
    public String toString() {
        if (value == null) {
            return "";
        } else if (value instanceof Integer) {
            return String.valueOf(((Integer) value).intValue());
        } else if (value instanceof Double) {
            return String.valueOf(((Double) value).doubleValue());
        } else {
            if (value.toString().startsWith("\"")) {
                return value.toString().substring(1, value.toString().length() - 1);
            } else {
                return value.toString();
            }
        }
    }

    /**
     * Setter for value.
     *
     * @param value new value
     * @throws RuntimeException If type of given value is not supported
     */
    public void setValue(Object value) {
        if (!(value == null || value instanceof Integer || value instanceof Double || value instanceof String)) {
            throw new RuntimeException("Tip podatka nije podržan!");
        }

        this.value = value;
    }

    /**
     * This method sums value of this wrapper to given value.
     *
     * @param incValue Value to be added
     */
    public void add(Object incValue) {
        operation(incValue, (o, o2) -> o + o2);
    }

    /**
     * This method substracts given value from value of this wrapper.
     *
     * @param decValue Value to be substracted
     */
    public void sub(Object decValue) {
        operation(decValue, (o, o2) -> o - o2);
    }

    /**
     * This method multiplies value of this wrapper with given value.
     *
     * @param mulValue Value to be multiplied with
     */
    public void multiply(Object mulValue) {
        operation(mulValue, (o, o2) -> o * o2);
    }

    /**
     * This method is used for dividing value of this wrapper with given value.
     *
     * @param divValue Divisor value
     */
    public void divide(Object divValue) {
        operation(divValue, (o, o2) -> o / o2);
    }

    /**
     * This method is used for comparing value of this wrapper with given value.
     *
     * @param withValue Value to be compared with
     * @return 1 if bigger, 0 if equals, -1 if smaller
     */
    public int numCompare(Object withValue) {
        return Double.compare(getValue(value), getValue(withValue));
    }

    /**
     * This class is used for performing given operation on value of this wrapper and given value.
     *
     * @param operatorValue Value
     * @param function      Operation
     */
    private void operation(Object operatorValue, BiFunction<Double, Double, Double> function) {
        if (operatorValue instanceof ValueWrapper) {
            operatorValue = ((ValueWrapper) operatorValue).getValue();
        }

        double firstNumber = getValue(value);
        NumberType firstNumberType = getType(value);

        double secondNumber = getValue(operatorValue);
        NumberType secondNumberType = getType(operatorValue);

        double result = function.apply(firstNumber, secondNumber);

        if (firstNumberType == NumberType.INTEGER && secondNumberType == NumberType.INTEGER) {
            this.value = (int) result;
        } else {
            this.value = result;
        }
    }

    /**
     * This method is used for getting double value from object.
     *
     * @param value Value
     * @return Double value of given object
     * @throws RuntimeException If type of object is unsupported.
     */
    private double getValue(Object value) {
        if (value instanceof ValueWrapper) {
            value = ((ValueWrapper) value).value;
        }

        if (value == null) {
            return 0;
        } else if (value instanceof Integer) {
            return (double) (Integer) value;
        } else if (value instanceof Double) {
            return (double) value;
        } else if (value instanceof String) {
            String valueString = (String) value;
            try {
                if (valueString.contains(".") || valueString.contains("E")) {
                    return Double.parseDouble(valueString);
                } else {
                    return Integer.parseInt(valueString);
                }
            } catch (NumberFormatException ex) {
                throw new RuntimeException("String se ne može pretvoriti u broj!");
            }
        } else {
            throw new RuntimeException("Nepoznati tip podatka!");
        }

    }

    /**
     * This method is used for getting type from object.
     *
     * @param value Value
     * @return {@link NumberType} of given object
     * @throws RuntimeException If type of object is unsupported.
     */
    private NumberType getType(Object value) {
        if (value == null) {
            return NumberType.INTEGER;
        } else if (value instanceof Integer) {
            return NumberType.INTEGER;
        } else if (value instanceof Double) {
            return NumberType.DOUBLE;
        } else if (value instanceof String) {
            String valueString = (String) value;
            if (valueString.contains(".") || valueString.contains("E")) {
                return NumberType.DOUBLE;
            } else {
                return NumberType.INTEGER;
            }
        } else {
            throw new RuntimeException("Nepoznati tip podatka!");
        }
    }

    /**
     * This enumeration is used for representing type of value.
     */
    private enum NumberType {

        /**
         * Integer type.
         */
        INTEGER,

        /**
         * Double type.
         */
        DOUBLE
    }


}
