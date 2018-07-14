package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;

/**
 * This class represents {@link Element} which is used for representing
 * variables. It only has variable name, not variable value.
 */
public class ElementVariable implements Element {

    /**
     * Name of variable.
     */
    private String name;

    /**
     * Basic constructor.
     *
     * @param name Name of variable
     */
    public ElementVariable(String name) {
        Objects.requireNonNull(name, "Name ne može biti null!");

        this.name = name;
    }

    @Override
    public String asText() {
        return name;
    }
}
