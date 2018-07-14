package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * This class represents draw command. It is used for drawing lines in painter using context.
 */
public class DrawCommand implements Command {

    /**
     * Step length that will be drawn.
     */
    private double step;

    /**
     * Basic constructor.
     *
     * @param step Step
     */
    public DrawCommand(double step) {
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
        painter.drawLine(currentPosition.getX(), currentPosition.getY(), nextPosition.getX(), nextPosition.getY(),
                currentState.getColor(), 1);
        currentState.setPosition(nextPosition);
    }
}
