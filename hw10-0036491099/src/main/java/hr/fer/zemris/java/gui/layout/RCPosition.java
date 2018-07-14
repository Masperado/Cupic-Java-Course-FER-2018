package hr.fer.zemris.java.gui.layout;

import java.util.Objects;

/**
 * This class represents RCPosition. It is used for representing constraints in {@link CalcLayout}.
 */
public class RCPosition {

    /**
     * Row.
     */
    private int row;

    /**
     * Column.
     */
    private int column;

    /**
     * Basic constructor.
     *
     * @param row    Row
     * @param column Column
     */
    public RCPosition(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Getter for row.
     *
     * @return Row
     */
    public int getRow() {
        return row;
    }

    /**
     * Getter for column.
     *
     * @return Column
     */
    public int getColumn() {
        return column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RCPosition that = (RCPosition) o;
        return row == that.row &&
                column == that.column;
    }

    @Override
    public int hashCode() {

        return Objects.hash(row, column);
    }
}
