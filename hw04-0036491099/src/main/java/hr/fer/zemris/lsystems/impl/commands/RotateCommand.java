package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * This class represent rotate command. It is used for rotating direction of current state of context.
 */
public class RotateCommand implements Command {

    /**
     * Angle for which will be rotated.
     */
    private double angle;

    /**
     * Basic constructor.
     *
     * @param angle Angle
     */
    public RotateCommand(double angle) {
        this.angle = angle;
    }

    /**
     * Getter for angle.
     *
     * @return Angle
     */
    public double getAngle() {
        return angle;
    }

    @Override
    public void execute(Context ctx, Painter painter) {
        TurtleState currentState = ctx.getCurrentState();
        currentState.setDirection(currentState.getDirection().rotated(angle).normalized());
    }
}
