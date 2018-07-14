package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;

/**
 * This class represents {@link Element} which is used for representing
 * operators. It only has operator symbol, not operator behaviour.
 */
public class ElementOperator implements Element {

    /**
     * Symbol of operator.
     */
    private String symbol;

    /**
     * Basic constructor.
     *
     * @param symbol Symbol of operator
     */
    public ElementOperator(String symbol) {
        Objects.requireNonNull(symbol, "Symbol ne može biti null");

        this.symbol = symbol;
    }

    @Override
    public String asText() {
        return symbol;
    }
}
