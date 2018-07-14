package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

import java.util.Objects;

/**
 * This class represents ForLoopNode. For loop node have to have its variable
 * which have to be of type {@link ElementVariable}, start and end Expression
 * of type {@link Element} and optionally stepExpression of same type.
 * ForLoop can have children.
 */
public class ForLoopNode extends Node {

    /**
     * Variable of for loop.
     */
    private ElementVariable variable;

    /**
     * Start expression of for loop.
     */
    private Element startExpression;

    /**
     * End expression of for loop.
     */
    private Element endExpression;

    /**
     * Step expression of for loop.
     */
    private Element stepExpression;

    /**
     * Collection of children.
     */
    private ArrayIndexedCollection collection;

    /**
     * Basic constructor.
     *
     * @param variable        Variable
     * @param startExpression Start expression
     * @param endExpression   End expression
     * @param stepExpression  Step expression
     */
    public ForLoopNode(ElementVariable variable, Element startExpression,
                       Element endExpression, Element
            stepExpression) {
        Objects.requireNonNull(variable, "Variable ne može biti null!");
        Objects.requireNonNull(startExpression, "StartExpression ne može biti" +
                " null!");
        Objects.requireNonNull(endExpression, "EndExpression ne može biti " +
                "null!");

        this.variable = variable;
        this.startExpression = startExpression;
        this.endExpression = endExpression;
        this.stepExpression = stepExpression;
    }

    /**
     * Getter for variable.
     *
     * @return Variable
     */
    public ElementVariable getVariable() {
        return variable;
    }

    /**
     * Getter for start expression.
     *
     * @return Start expression
     */
    public Element getStartExpression() {
        return startExpression;
    }

    /**
     * Getter for end expression.
     *
     * @return End expression
     */
    public Element getEndExpression() {
        return endExpression;
    }

    /**
     * Getter for step expression.
     *
     * @return Step expression
     */
    public Element getStepExpression() {
        return stepExpression;
    }

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
