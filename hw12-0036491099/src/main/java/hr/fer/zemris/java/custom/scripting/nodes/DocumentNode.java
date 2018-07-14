package hr.fer.zemris.java.custom.scripting.nodes;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class represents document node. It is used as a root node of syntax
 * tree generated in
 * {@link hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser}
 */
public class DocumentNode implements Node {

    /**
     * Collection of children.
     */
    private List<Node> collection;

    @Override
    public void addChildNode(Node child) {
        Objects.requireNonNull(child, "Dijete ne može biti null!");


        if (collection == null) {
            collection = new ArrayList<>();
        }

        collection.add(child);
    }

    @Override
    public int numberOfChildren() {
        return collection.size();
    }

    @Override
    public Node getChild(int index) {
        return collection.get(index);
    }

    @Override
    public void accept(INodeVisitor visitor) {
        visitor.visitDocumentNode(this);
    }

}
