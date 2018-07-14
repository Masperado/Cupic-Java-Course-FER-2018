package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;

/**
 * This class represents {@link Element} which is used for representing
 * functions. It only has function name, not function behaviour.
 */
public class ElementFunction extends Element {

    /**
     * Name of function
     */
    private String name;

    /**
     * Basic constructor.
     *
     * @param name Name of function
     */
    public ElementFunction(String name) {
        Objects.requireNonNull(name, "Name ne može biti null!");

        this.name = name;
    }

    @Override
    public String asText() {
        return name;
    }
}
