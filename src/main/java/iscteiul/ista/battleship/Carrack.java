package iscteiul.ista.battleship;

/**
 * Represents a carrack ({@code Nau}) occupying three consecutive positions.
 * North and south bearings place it vertically; east and west place it horizontally.
 */
public class Carrack extends Ship {
    /** Number of positions occupied by a carrack. */
    private static final Integer SIZE = 3;
    /** Category name used to identify carracks. */
    private static final String NAME = "Nau";

    /**
     * Creates a carrack extending from the supplied position towards increasing
     * row indices for a vertical bearing or increasing column indices for a
     * horizontal bearing. Board boundaries are checked when adding it to a fleet.
     *
     * @param bearing the non-null cardinal direction of the carrack
     * @param pos the non-null topmost or leftmost position of the carrack
     * @throws IllegalArgumentException if {@code bearing} is {@link Compass#UNKNOWN}
     */
    public Carrack(Compass bearing, IPosition pos) throws IllegalArgumentException {
        super(Carrack.NAME, bearing, pos);
        switch (bearing) {
            case NORTH:
            case SOUTH:
                for (int r = 0; r < SIZE; r++)
                    getPositions().add(new Position(pos.getRow() + r, pos.getColumn()));
                break;
            case EAST:
            case WEST:
                for (int c = 0; c < SIZE; c++)
                    getPositions().add(new Position(pos.getRow(), pos.getColumn() + c));
                break;
            default:
                throw new IllegalArgumentException("ERROR! invalid bearing for the carrack");
        }
    }

    /**
     * Returns the fixed size of a carrack.
     *
     * @return three occupied positions
     */
    @Override
    public Integer getSize() {
        return Carrack.SIZE;
    }

}
