package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * This interface represents INodeVisitor. It is used for visiting {@link Node}.
 */
public interface INodeVisitor {

    /**
     * This method is used for visiting {@link TextNode}.
     *
     * @param node Node that will be visited
     */
    void visitTextNode(TextNode node);


    /**
     * This method is used for visiting {@link ForLoopNode}.
     *
     * @param node Node that will be visited
     */
    void visitForLoopNode(ForLoopNode node);


    /**
     * This method is used for visiting {@link EchoNode}.
     *
     * @param node Node that will be visited
     */
    void visitEchoNode(EchoNode node);


    /**
     * This method is used for visiting {@link DocumentNode}.
     *
     * @param node Node that will be visited
     */
    void visitDocumentNode(DocumentNode node);
}
