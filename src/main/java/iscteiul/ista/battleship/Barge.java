package iscteiul.ista.battleship;

/**
 * Represents a barge ({@code Barca}) occupying a single board position.
 * Its bearing is stored but does not affect its shape.
 */
public class Barge extends Ship {
    /** Number of positions occupied by a barge. */
    private static final Integer SIZE = 1;
    /** Category name used to identify barges. */
    private static final String NAME = "Barca";

    /**
     * Creates a barge at a copy of the supplied coordinates.
     *
     * @param bearing the non-null bearing to associate with the barge
     * @param pos the non-null position occupied by the barge
     */
    public Barge(Compass bearing, IPosition pos) {
        super(Barge.NAME, bearing, pos);
        getPositions().add(new Position(pos.getRow(), pos.getColumn()));
    }

    /**
     * Returns the fixed size of a barge.
     *
     * @return one occupied position
     */
    @Override
    public Integer getSize() {
        return SIZE;
    }

}
