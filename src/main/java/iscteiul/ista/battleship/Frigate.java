package iscteiul.ista.battleship;

/**
 * Represents a frigate ({@code Fragata}) occupying four consecutive positions.
 * North and south bearings place it vertically; east and west place it horizontally.
 */
public class Frigate extends Ship {
    /** Number of positions occupied by a frigate. */
    private static final Integer SIZE = 4;
    /** Category name used to identify frigates. */
    private static final String NAME = "Fragata";

    /**
     * Creates a frigate extending from the supplied position towards increasing
     * row indices for a vertical bearing or increasing column indices for a
     * horizontal bearing. Board boundaries are checked when adding it to a fleet.
     *
     * @param bearing the non-null cardinal direction of the frigate
     * @param pos the non-null topmost or leftmost position of the frigate
     * @throws IllegalArgumentException if {@code bearing} is {@link Compass#UNKNOWN}
     */
    public Frigate(Compass bearing, IPosition pos) throws IllegalArgumentException {
        super(Frigate.NAME, bearing, pos);
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
                throw new IllegalArgumentException("ERROR! invalid bearing for thr frigate");
        }
    }

    /**
     * Returns the fixed size of a frigate.
     *
     * @return four occupied positions
     */
    @Override
    public Integer getSize() {
        return Frigate.SIZE;
    }

}
