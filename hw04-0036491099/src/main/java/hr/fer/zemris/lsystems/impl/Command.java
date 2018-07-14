package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

/**
 * This interface represents command. Command defines one method "execute".
 */
public interface Command {

    /**
     * This method is used for executing action in given {@link Context} and {@link Painter}.
     *
     * @param ctx     Context
     * @param painter Painter
     */
    void execute(Context ctx, Painter painter);
}
