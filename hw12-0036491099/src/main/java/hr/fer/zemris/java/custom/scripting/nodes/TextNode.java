package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.Objects;

/**
 * This class represents TextNode. It has value of type String. TextNode
 * can't have children.
 */
public class TextNode implements Node {

    /**
     * Text of node.
     */
    private String text;

    /**
     * Basic constructor.
     *
     * @param text Text
     */
    public TextNode(String text) {
        Objects.requireNonNull(text, "Text ne može biti null");

        this.text = text;
    }

    /**
     * Getter for text.
     *
     * @return text
     */
    public String getText() {
        return text;
    }

    @Override
    public void addChildNode(Node child) {
        throw new RuntimeException("TextNode ne može imati djecu!");
    }

    @Override
    public int numberOfChildren() {
        return 0;
    }

    @Override
    public Node getChild(int index) {
        throw new RuntimeException("TextNode ne može imati djecu!");
    }

    @Override
    public void accept(INodeVisitor visitor) {
        visitor.visitTextNode(this);
    }
}
