package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.math.Vector2D;

import java.awt.*;

/**
 * This class represents TurtleState. It is used for represeting state of turtle in space. Each turtle has position,
 * direction, color and move length.
 */
public class TurtleState {

    /**
     * Position of turtle.
     */
    private Vector2D position;

    /**
     * Direction of turtle.
     */
    private Vector2D direction;

    /**
     * Color of turtle.
     */
    private Color color;

    /**
     * Move length of turtle.
     */
    private double moveLength;

    /**
     * Basic constructor.
     *
     * @param position   Position
     * @param direction  Direction
     * @param color      Color
     * @param moveLength Move length
     */
    public TurtleState(Vector2D position, Vector2D direction, Color color, double moveLength) {
        this.position = position;
        this.direction = direction.normalized();
        this.color = color;
        this.moveLength = moveLength;
    }

    /**
     * Getter for position.
     *
     * @return Position
     */
    public Vector2D getPosition() {
        return position;
    }

    /**
     * Getter for direction.
     *
     * @return Direction
     */
    public Vector2D getDirection() {
        return direction;
    }

    /**
     * Getter for color.
     *
     * @return Color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Getter for move length.
     *
     * @return Move length
     */
    public double getMoveLength() {
        return moveLength;
    }

    /**
     * This method is used for copying this turtle state.
     *
     * @return Copied turtle state
     */
    public TurtleState copy() {
        return new TurtleState(position.copy(), direction.copy(), new Color(color.getRGB()), moveLength);
    }

    /**
     * Setter for position.
     *
     * @param position Position
     */
    public void setPosition(Vector2D position) {
        this.position = position;
    }

    /**
     * Setter for direction.
     *
     * @param direction Direction
     */
    public void setDirection(Vector2D direction) {
        this.direction = direction.normalized();
    }

    /**
     * Setter for color.
     *
     * @param color Color
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Setter for move length.
     *
     * @param moveLength Move length
     */
    public void setMoveLength(double moveLength) {
        this.moveLength = moveLength;
    }
}
