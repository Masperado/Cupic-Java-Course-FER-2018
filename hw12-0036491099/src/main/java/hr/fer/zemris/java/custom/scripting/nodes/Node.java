package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * This class represents node. Node is basic element of syntax tree which is
 * built when parsing text with
 * {@link hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser}. Node
 * has collection of children which are also of type Node.
 */
public interface Node {

    /**
     * This method is used for adding child to this node.
     *
     * @param child New child
     */
    void addChildNode(Node child);

    /**
     * This method is used for getting number of children.
     *
     * @return Number of children
     */
    int numberOfChildren();

    /**
     * This method is used for getting child at given index.
     *
     * @param index Index of child
     * @return Child at given index
     */
    Node getChild(int index);

    /**
     * This method is used for accepting visit from {@link INodeVisitor}.
     *
     * @param visitor {@link INodeVisitor}
     */
    void accept(INodeVisitor visitor);

}
