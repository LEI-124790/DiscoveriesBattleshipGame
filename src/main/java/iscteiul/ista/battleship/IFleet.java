package iscteiul.ista.battleship;

import java.util.List;

/**
 * Defines a collection of ships placed on a square Battleship board.
 * Provides placement validation, ship lookup and fleet status operations.
 */
public interface IFleet {
    /** Number of rows and columns on the board. */
    Integer BOARD_SIZE = 10;
    /** Fleet size threshold used when adding ships. */
    Integer FLEET_SIZE = 10;

    /**
     * Returns the ships currently in the fleet.
     *
     * @return the fleet's mutable list of ships
     */
    List<IShip> getShips();

    /**
     * Attempts to add a ship subject to fleet capacity and placement checks.
     * The ship must fit inside the board and must not overlap or touch another
     * ship, including diagonally.
     *
     * @param s the non-null ship to add
     * @return {@code true} if the ship was added; {@code false} if a check failed
     */
    boolean addShip(IShip s);

    /**
     * Selects ships with the given category name, using case-sensitive matching.
     *
     * @param category the category to select
     * @return a new list containing the matching ships
     */
    List<IShip> getShipsLike(String category);

    /**
     * Selects ships that still have at least one unhit position.
     *
     * @return a new list containing the ships still afloat
     */
    List<IShip> getFloatingShips();

    /**
     * Finds the first ship occupying the supplied coordinates.
     *
     * @param pos the non-null position to inspect
     * @return the occupying ship, or {@code null} if no ship occupies the position
     */
    IShip shipAt(IPosition pos);

    /** Prints all ships, the ships still afloat and the ships in each category. */
    void printStatus();
}
