package iscteiul.ista.battleship;

import java.util.Objects;

/**
 * Stores a pair of zero-based board coordinates with mutable occupancy and shot flags.
 * Coordinates are not checked against board boundaries. Equality uses only the
 * row and column, and adjacency includes overlapping and diagonal positions.
 */
public class Position implements IPosition {
    /** Row coordinate. */
    private int row;
    /** Column coordinate. */
    private int column;
    /** Whether the position has been marked as occupied. */
    private boolean isOccupied;
    /** Whether the position has received a shot. */
    private boolean isHit;

    /**
     * Creates an unoccupied position that has not been shot.
     *
     * @param row the zero-based row coordinate
     * @param column the zero-based column coordinate
     */
    public Position(int row, int column) {
        this.row = row;
        this.column = column;
        this.isOccupied = false;
        this.isHit = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getRow() {
        return row;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getColumn() {
        return column;
    }

    /**
     * Computes a hash from the coordinates and the current occupancy and shot flags.
     *
     * @return the hash of the current position state
     */
    @Override
    public int hashCode() {
        return Objects.hash(column, isHit, isOccupied, row);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object otherPosition) {
        if (this == otherPosition)
            return true;
        if (otherPosition instanceof IPosition) {
            IPosition other = (IPosition) otherPosition;
            return (this.getRow() == other.getRow() && this.getColumn() == other.getColumn());
        } else {
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAdjacentTo(IPosition other) {
        return (Math.abs(this.getRow() - other.getRow()) <= 1 && Math.abs(this.getColumn() - other.getColumn()) <= 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void occupy() {
        isOccupied = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void shoot() {
        isHit = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isOccupied() {
        return isOccupied;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isHit() {
        return isHit;
    }

    /**
     * Returns the coordinates with the Portuguese labels {@code Linha} and {@code Coluna}.
     *
     * @return a description of the row and column
     */
    @Override
    public String toString() {
        return ("Linha = " + row + " Coluna = " + column);
    }

}
