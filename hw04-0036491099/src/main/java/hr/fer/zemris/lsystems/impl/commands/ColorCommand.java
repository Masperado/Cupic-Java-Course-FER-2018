package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

import java.awt.*;

/**
 * This class represents color command. It is used for changing color at current state of context.
 */
public class ColorCommand implements Command {

    /**
     * Color in which state will be changed into.
     */
    private Color color;

    /**
     * Basic constructor.
     *
     * @param color Color
     */
    public ColorCommand(Color color) {
        this.color = color;
    }

    /**
     * Getter for color.
     *
     * @return Color
     */
    public Color getColor() {
        return color;
    }

    @Override
    public void execute(Context ctx, Painter painter) {
        ctx.getCurrentState().setColor(color);
    }
}
