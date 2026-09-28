package iscteiul.ista.battleship;

/**
 * Represents a Caravel ship in the Battleship game.
 * <p>
 * A Caravel has a fixed size of two positions and can be oriented
 * vertically or horizontally according to the specified {@link Compass}
 * bearing.
 * </p>
 *
 * <p>
 * The positions occupied by the ship are calculated from the initial
 * position supplied to the constructor.
 * </p>
 *
 * @author
 * @version 1.0
 */
public class Caravel extends Ship {

    /**
     * The fixed number of positions occupied by a Caravel.
     */
    private static final Integer SIZE = 2;

    /**
     * The name used to identify a Caravel.
     */
    private static final String NAME = "Caravela";

    /**
     * Creates a new Caravel with the specified bearing and initial position.
     * <p>
     * The Caravel occupies two consecutive positions starting from
     * {@code pos}. If the bearing is {@link Compass#NORTH} or
     * {@link Compass#SOUTH}, the ship is positioned vertically.
     * If the bearing is {@link Compass#EAST} or {@link Compass#WEST},
     * the ship is positioned horizontally.
     * </p>
     *
     * @param bearing the direction in which the Caravel is oriented
     * @param pos     the initial position used to place the Caravel
     *
     * @throws NullPointerException
     *         if {@code bearing} is {@code null}
     * @throws IllegalArgumentException
     *         if {@code bearing} is not a valid direction for positioning
     *         the Caravel
     */
    public Caravel(Compass bearing, IPosition pos)
            throws NullPointerException, IllegalArgumentException {

        super(Caravel.NAME, bearing, pos);

        if (bearing == null)
            throw new NullPointerException(
                    "ERROR! invalid bearing for the caravel"
            );

        switch (bearing) {

            case NORTH:
            case SOUTH:
                for (int r = 0; r < SIZE; r++)
                    getPositions().add(
                            new Position(
                                    pos.getRow() + r,
                                    pos.getColumn()
                            )
                    );
                break;

            case EAST:
            case WEST:
                for (int c = 0; c < SIZE; c++)
                    getPositions().add(
                            new Position(
                                    pos.getRow(),
                                    pos.getColumn() + c
                            )
                    );
                break;

            default:
                throw new IllegalArgumentException(
                        "ERROR! invalid bearing for the caravel"
                );
        }
    }

    /**
     * Returns the size of the Caravel.
     * <p>
     * A Caravel always occupies exactly two positions.
     * </p>
     *
     * @return the number of positions occupied by the Caravel
     */
    @Override
    public Integer getSize() {
        return SIZE;
    }
}

