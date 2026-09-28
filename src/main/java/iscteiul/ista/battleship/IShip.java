package iscteiul.ista.battleship;

import java.util.List;

/**
 * Defines a ship's placement, occupied positions and response to shots.
 * A ship remains afloat while at least one of its positions has not been hit.
 */
public interface IShip {
    /**
     * Returns the category identifying the ship type.
     *
     * @return the category name, such as {@code "Barca"}
     */
    String getCategory();

    /**
     * Returns the number of positions occupied by this ship.
     *
     * @return the ship size
     */
    Integer getSize();

    /**
     * Returns the positions occupied by this ship.
     *
     * @return the ship's mutable list of positions
     */
    List<IPosition> getPositions();

    /**
     * Returns the reference position supplied when the ship was placed.
     *
     * @return the initial position
     */
    IPosition getPosition();

    /**
     * Returns the ship's orientation.
     *
     * @return the compass bearing
     */
    Compass getBearing();

    /**
     * Checks whether any part of the ship remains unhit.
     *
     * @return {@code true} if at least one occupied position has not been hit
     */
    boolean stillFloating();

    /**
     * Returns the top boundary of the ship.
     *
     * @return the smallest occupied row index
     */
    int getTopMostPos();

    /**
     * Returns the bottom boundary of the ship.
     *
     * @return the largest occupied row index
     */
    int getBottomMostPos();

    /**
     * Returns the left boundary of the ship.
     *
     * @return the smallest occupied column index
     */
    int getLeftMostPos();

    /**
     * Returns the right boundary of the ship.
     *
     * @return the largest occupied column index
     */
    int getRightMostPos();

    /**
     * Checks whether the ship occupies the supplied coordinates.
     *
     * @param pos the non-null position to check
     * @return {@code true} if one of the ship's positions has these coordinates
     */
    boolean occupies(IPosition pos);

    /**
     * Checks whether two ships overlap or touch, including diagonally.
     *
     * @param other the non-null ship to check
     * @return {@code true} if any occupied positions overlap or are adjacent
     */
    boolean tooCloseTo(IShip other);

    /**
     * Checks whether a position overlaps or touches this ship, including diagonally.
     *
     * @param pos the non-null position to check
     * @return {@code true} if the position overlaps or is adjacent to the ship
     */
    boolean tooCloseTo(IPosition pos);

    /**
     * Marks matching positions of this ship as hit; a miss has no effect.
     *
     * @param pos the non-null target position
     */
    void shoot(IPosition pos);
}
