package hr.fer.zemris.java.custom.scripting.elems;

/**
 * This class represents {@link Element} which has value of type double.
 */
public class ElementConstantDouble extends Element {

    /**
     * Value of Element.
     */
    private double value;

    /**
     * Basic constructor.
     *
     * @param value Value
     */
    public ElementConstantDouble(double value) {
        this.value = value;
    }

    /**
     * Getter for value.
     *
     * @return Value
     */
    public double getValue() {
        return value;
    }

    @Override
    public String asText() {
        return "" + value;
    }
}
