package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;

/**
 * This class is used for representing {@link Element} with value of type
 * String.
 */
public class ElementString implements Element {

    /**
     * Value of Element.
     */
    private String value;

    /**
     * Basic constructor.
     *
     * @param value Value of element
     */
    public ElementString(String value) {
        Objects.requireNonNull(value, "Value ne može biti null!");

        this.value = value;
    }

    @Override
    public String asText() {
        return value;
    }
}
