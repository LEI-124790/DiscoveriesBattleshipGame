package iscteiul.ista.battleship;

import java.util.List;

/**
 * Defines firing, shot statistics and board display for a game against a fleet.
 */
public interface IGame {
    /**
     * Fires at a position and updates the corresponding shot statistics.
     * Invalid or repeated shots do not damage ships or enter the shot history.
     *
     * @param pos the non-null target position
     * @return the ship sunk by this shot, or {@code null} for a miss, an invalid
     *         or repeated shot, or a hit that does not sink a ship
     */
    IShip fire(IPosition pos);

    /**
     * Returns the history of valid, non-repeated shots.
     *
     * @return the game's mutable shot list in firing order
     */
    List<IPosition> getShots();

    /**
     * Returns the number of shots at previously targeted valid positions.
     *
     * @return the repeated shot count
     */
    int getRepeatedShots();

    /**
     * Returns the number of shots rejected by the game's coordinate validation.
     *
     * @return the invalid shot count
     */
    int getInvalidShots();

    /**
     * Returns the number of valid, non-repeated shots that hit ships.
     *
     * @return the hit count
     */
    int getHits();

    /**
     * Returns the number of ships sunk by shots during this game.
     *
     * @return the sunk ship count
     */
    int getSunkShips();

    /**
     * Returns the number of ships in the fleet that remain afloat.
     *
     * @return the remaining ship count
     */
    int getRemainingShips();

    /** Prints the board with recorded valid shots marked by {@code X}. */
    void printValidShots();

    /** Prints the board with all ship positions marked by {@code #}. */
    void printFleet();
}
