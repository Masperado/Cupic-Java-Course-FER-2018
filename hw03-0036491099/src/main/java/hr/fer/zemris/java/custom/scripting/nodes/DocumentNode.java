package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;

import java.util.Objects;

/**
 * This class represents document node. It is used as a root node of syntax
 * tree generated in
 * {@link hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser}
 */
public class DocumentNode extends Node {

    /**
     * Collection of children.
     */
    private ArrayIndexedCollection collection;

    @Override
    public void addChildNode(Node child) {
        Objects.requireNonNull(child, "Dijete ne može biti null!");


        if (collection == null) {
            collection = new ArrayIndexedCollection();
        }

        collection.add(child);
    }

    @Override
    public int numberOfChildren() {
        return collection.size();
    }

    @Override
    public Node getChild(int index) {
        return (Node) collection.get(index);
    }
}
