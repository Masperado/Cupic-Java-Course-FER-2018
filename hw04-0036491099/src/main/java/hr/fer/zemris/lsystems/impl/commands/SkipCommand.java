package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * This class represents draw command. It is used for skipping lines in painter using context.
 */
public class SkipCommand implements Command {

    /**
     * Step length that will be skipped.
     */
    private double step;

    /**
     * Basic constructor.
     *
     * @param step Step
     */
    public SkipCommand(double step) {
        this.step = step;
    }

    /**
     * Getter for step.
     *
     * @return Step
     */
    public double getStep() {
        return step;
    }

    @Override
    public void execute(Context ctx, Painter painter) {
        TurtleState currentState = ctx.getCurrentState();
        Vector2D currentPosition = currentState.getPosition();
        Vector2D direction = currentState.getDirection();
        double moveLength = currentState.getMoveLength();
        Vector2D nextPosition = currentPosition.translated(direction.scaled(moveLength * step));
        currentState.setPosition(nextPosition);
    }
}
