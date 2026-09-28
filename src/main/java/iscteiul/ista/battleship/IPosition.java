package iscteiul.ista.battleship;

/**
 * Describes a board position by its row, column, occupancy and shot state.
 * Coordinates use zero-based indices, and equality compares coordinates only.
 *
 * @author fba
 */
public interface IPosition {
    /**
     * Returns the row coordinate.
     *
     * @return the zero-based row index
     */
    int getRow();

    /**
     * Returns the column coordinate.
     *
     * @return the zero-based column index
     */
    int getColumn();

    /**
     * Compares this position with another object using coordinates only.
     *
     * @param other the object to compare, which may be {@code null}
     * @return {@code true} if the object is an {@code IPosition} with the same
     *         row and column, regardless of occupancy or shot state
     */
    boolean equals(Object other);

    /**
     * Checks whether another position is at most one row and one column away.
     * This includes diagonal neighbours and the position itself.
     *
     * @param other the non-null position to compare
     * @return {@code true} if the positions overlap or are adjacent
     */
    boolean isAdjacentTo(IPosition other);

    /** Marks this position as occupied by a ship. */
    void occupy();

    /** Marks this position as having received a shot, regardless of occupancy. */
    void shoot();

    /**
     * Checks whether this position has been marked as occupied.
     *
     * @return {@code true} if occupied
     */
    boolean isOccupied();

    /**
     * Checks whether this position has received a shot.
     *
     * @return {@code true} if the position has been shot
     */
    boolean isHit();
}
