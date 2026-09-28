package iscteiul.ista.battleship;

/**
 * Represents a galleon ({@code Galeao}) occupying five positions in a T shape.
 * The bearing selects which side of the shape contains its three-position bar.
 */
public class Galleon extends Ship {
    /** Number of positions occupied by a galleon. */
    private static final Integer SIZE = 5;
    /** Category name used to identify galleons. */
    private static final String NAME = "Galeao";

    /**
     * Creates a galleon with its bar on the side indicated by the bearing.
     * The reference position is the leftmost position in the top occupied row;
     * south and east bearings may also occupy columns to its left in lower rows.
     * Board boundaries are checked when adding the ship to a fleet.
     *
     * @param bearing the non-null cardinal direction of the galleon
     * @param pos the non-null reference position for constructing the shape
     * @throws IllegalArgumentException if {@code bearing} is {@link Compass#UNKNOWN}
     * @throws NullPointerException if {@code bearing} or {@code pos} is
     *         {@code null} and assertions are disabled
     * @throws AssertionError if {@code bearing} or {@code pos} is {@code null}
     *         and assertions are enabled
     */
    public Galleon(Compass bearing, IPosition pos) throws IllegalArgumentException {
        super(Galleon.NAME, bearing, pos);

        if (bearing == null)
            throw new NullPointerException("ERROR! invalid bearing for the galleon");

        switch (bearing) {
            case NORTH:
                fillNorth(pos);
                break;
            case EAST:
                fillEast(pos);
                break;
            case SOUTH:
                fillSouth(pos);
                break;
            case WEST:
                fillWest(pos);
                break;

            default:
                throw new IllegalArgumentException("ERROR! invalid bearing for the galleon");
        }
    }

    /**
     * Returns the fixed size of a galleon.
     *
     * @return five occupied positions
     */
    @Override
    public Integer getSize() {
        return Galleon.SIZE;
    }

    /**
     * Adds a horizontal bar at the top with a central stem extending downwards.
     *
     * @param pos the left end of the top bar
     */
    private void fillNorth(IPosition pos) {
        for (int i = 0; i < 3; i++) {
            getPositions().add(new Position(pos.getRow(), pos.getColumn() + i));
        }
        getPositions().add(new Position(pos.getRow() + 1, pos.getColumn() + 1));
        getPositions().add(new Position(pos.getRow() + 2, pos.getColumn() + 1));
    }

    /**
     * Adds a vertical stem ending in a horizontal bar at the bottom.
     *
     * @param pos the top of the stem
     */
    private void fillSouth(IPosition pos) {
        for (int i = 0; i < 2; i++) {
            getPositions().add(new Position(pos.getRow() + i, pos.getColumn()));
        }
        for (int j = 2; j < 5; j++) {
            getPositions().add(new Position(pos.getRow() + 2, pos.getColumn() + j - 3));
        }
    }

    /**
     * Adds a vertical bar on the right with a central stem extending leftwards.
     *
     * @param pos the top of the right bar
     */
    private void fillEast(IPosition pos) {
        getPositions().add(new Position(pos.getRow(), pos.getColumn()));
        for (int i = 1; i < 4; i++) {
            getPositions().add(new Position(pos.getRow() + 1, pos.getColumn() + i - 3));
        }
        getPositions().add(new Position(pos.getRow() + 2, pos.getColumn()));
    }

    /**
     * Adds a vertical bar on the left with a central stem extending rightwards.
     *
     * @param pos the top of the left bar
     */
    private void fillWest(IPosition pos) {
        getPositions().add(new Position(pos.getRow(), pos.getColumn()));
        for (int i = 1; i < 4; i++) {
            getPositions().add(new Position(pos.getRow() + 1, pos.getColumn() + i - 1));
        }
        getPositions().add(new Position(pos.getRow() + 2, pos.getColumn()));
    }

}
