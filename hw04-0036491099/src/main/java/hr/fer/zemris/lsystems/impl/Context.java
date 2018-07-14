package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * This class represents context. It is used for handling {@link TurtleState}-s.
 */
public class Context {

    /**
     * Stack where {@link TurtleState}-s will be stored.
     */
    private ObjectStack stack;

    /**
     * Basic constructor.
     */
    public Context() {
        this.stack = new ObjectStack();
    }

    /**
     * This method is used for peeking current state from stack.
     *
     * @return Current {@link TurtleState}
     */
    public TurtleState getCurrentState() {
        Object turtleState = stack.peek();

        if (turtleState instanceof TurtleState) {
            return (TurtleState) turtleState;
        } else {
            throw new RuntimeException("Ne stogu nisu samo TurtleState-ovi!");
        }
    }

    /**
     * This method is used for pushing {@link TurtleState} to stack.
     *
     * @param state {@link TurtleState}
     */
    public void pushState(TurtleState state) {
        this.stack.push(state);
    }

    /**
     * This method is used for popping one {@link TurtleState} from stack.
     */
    public void popState() {
        stack.pop();
    }
}
