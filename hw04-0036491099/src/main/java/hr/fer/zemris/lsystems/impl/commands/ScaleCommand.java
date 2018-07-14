package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * This class is used for scaling move length at current state of context.
 */
public class ScaleCommand implements Command {

    /**
     * Factor for scaling.
     */
    private double factor;

    /**
     * Basic constructor.
     *
     * @param factor Factor
     */
    public ScaleCommand(double factor) {
        this.factor = factor;
    }

    /**
     * Getter for factor.
     *
     * @return Factor
     */
    public double getFactor() {
        return factor;

    }

    @Override
    public void execute(Context ctx, Painter painter) {
        TurtleState current = ctx.getCurrentState();
        current.setMoveLength(current.getMoveLength() * factor);
    }
}
