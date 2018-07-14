package hr.fer.zemris.java.custom.scripting.elems;

/**
 * This class represents {@link Element} which has value of type int.
 */
public class ElementConstantInteger extends Element {

    /**
     * Value of element.
     */
    private int value;

    /**
     * Basic constructor.
     *
     * @param value Value
     */
    public ElementConstantInteger(int value) {
        this.value = value;
    }

    /**
     * Getter for value.
     *
     * @return Value
     */
    public int getValue() {
        return value;
    }

    @Override
    public String asText() {
        return "" + value;
    }
}
