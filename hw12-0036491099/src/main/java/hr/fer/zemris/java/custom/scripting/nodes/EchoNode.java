package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;

import java.util.Objects;

/**
 * This class represents echo node. It has collection of elements but it
 * doesn't have any children.
 */
public class EchoNode implements Node {

    /**
     * Elements collection of node.
     */
    private Element[] elements;

    /**
     * Basic constructor.
     *
     * @param elements Elements
     */
    public EchoNode(Element[] elements) {
        Objects.requireNonNull(elements, "Elements ne može biti null!");

        this.elements = elements;
    }

    /**
     * Getter for elements.
     *
     * @return Elements
     */
    public Element[] getElements() {
        return elements;
    }

    @Override
    public void addChildNode(Node child) {
        throw new RuntimeException("EchoNode ne može imati djecu!");
    }

    @Override
    public int numberOfChildren() {
        return 0;
    }

    @Override
    public Node getChild(int index) {
        throw new RuntimeException("EchoNode ne može imati djecu!");
    }

    @Override
    public void accept(INodeVisitor visitor) {
        visitor.visitEchoNode(this);
    }
}
