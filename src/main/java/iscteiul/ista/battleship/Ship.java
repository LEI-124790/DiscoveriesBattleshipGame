package iscteiul.ista.battleship;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 * Represents a ship in the Battleship game.
 *
 * <p>A ship has a category, a bearing (orientation), an initial position,
 * and a collection of positions that it occupies on the board.</p>
 *
 * <p>This class provides common functionality shared by all types of ships,
 * such as checking whether the ship is still floating, determining its
 * boundaries, checking occupied positions, detecting nearby ships, and
 * processing shots.</p>
 */


public abstract class Ship implements IShip {

    private static final String GALEAO = "galeao";
    private static final String FRAGATA = "fragata";
    private static final String NAU = "nau";
    private static final String CARAVELA = "caravela";
    private static final String BARCA = "barca";

    /**
     * Creates a ship of the specified type.
     *
     * @param shipKind the type of ship to create
     * @param bearing the orientation of the ship
     * @param pos the initial position of the ship
     * @return a new ship corresponding to {@code shipKind}, or {@code null}
     *         if the specified type is unknown
     */
    static Ship buildShip(String shipKind, Compass bearing, Position pos) {
        Ship s;
        switch (shipKind) {
            case BARCA:
                s = new Barge(bearing, pos);
                break;
            case CARAVELA:
                s = new Caravel(bearing, pos);
                break;
            case NAU:
                s = new Carrack(bearing, pos);
                break;
            case FRAGATA:
                s = new Frigate(bearing, pos);
                break;
            case GALEAO:
                s = new Galleon(bearing, pos);
                break;
            default:
                s = null;
        }
        return s;
    }

    /** The category/type of the ship. */
    private String category;

    /** The orientation of the ship. */
    private Compass bearing;

    /** The initial position of the ship. */
    private IPosition pos;

    /** The positions occupied by the ship. */
    protected List<IPosition> positions;

    /**
     * Constructs a ship.
     *
     * @param category the category/type of the ship
     * @param bearing the orientation of the ship
     * @param pos the initial position of the ship
     * @throws AssertionError if {@code bearing} or {@code pos} is {@code null}
     */
    public Ship(String category, Compass bearing, IPosition pos) {
        assert bearing != null;
        assert pos != null;

        this.category = category;
        this.bearing = bearing;
        this.pos = pos;
        positions = new ArrayList<>();
    }

    /**
     * Returns the category of this ship.
     *
     * @return the ship category
     */
    @Override
    public String getCategory() {
        return category;
    }

    /**
     * Returns the positions occupied by this ship.
     *
     * @return the list of positions occupied by the ship
     */
    public List<IPosition> getPositions() {
        return positions;
    }

    /**
     * Returns the initial position of this ship.
     *
     * @return the initial position
     */
    @Override
    public IPosition getPosition() {
        return pos;
    }

    /**
     * Returns the bearing (orientation) of this ship.
     *
     * @return the ship bearing
     */
    @Override
    public Compass getBearing() {
        return bearing;
    }

    /**
     * Determines whether the ship is still floating.
     *
     * <p>A ship is considered to be floating if at least one of its
     * positions has not been hit.</p>
     *
     * @return {@code true} if at least one position has not been hit;
     *         {@code false} otherwise
     */
    @Override
    public boolean stillFloating() {
        for (int i = 0; i < getSize(); i++)
            if (!getPositions().get(i).isHit())
                return true;
        return false;
    }

    /**
     * Returns the row of the topmost position occupied by the ship.
     *
     * @return the smallest row index occupied by the ship
     */
    @Override
    public int getTopMostPos() {
        int top = getPositions().get(0).getRow();
        for (int i = 1; i < getSize(); i++)
            if (getPositions().get(i).getRow() < top)
                top = getPositions().get(i).getRow();
        return top;
    }

    /**
     * Returns the row of the bottommost position occupied by the ship.
     *
     * @return the largest row index occupied by the ship
     */
    @Override
    public int getBottomMostPos() {
        int bottom = getPositions().get(0).getRow();
        for (int i = 1; i < getSize(); i++)
            if (getPositions().get(i).getRow() > bottom)
                bottom = getPositions().get(i).getRow();
        return bottom;
    }

    /**
     * Returns the column of the leftmost position occupied by the ship.
     *
     * @return the smallest column index occupied by the ship
     */
    @Override
    public int getLeftMostPos() {
        int left = getPositions().get(0).getColumn();
        for (int i = 1; i < getSize(); i++)
            if (getPositions().get(i).getColumn() < left)
                left = getPositions().get(i).getColumn();
        return left;
    }

    /**
     * Returns the column of the rightmost position occupied by the ship.
     *
     * @return the largest column index occupied by the ship
     */
    @Override
    public int getRightMostPos() {
        int right = getPositions().get(0).getColumn();
        for (int i = 1; i < getSize(); i++)
            if (getPositions().get(i).getColumn() > right)
                right = getPositions().get(i).getColumn();
        return right;
    }

    /**
     * Determines whether this ship occupies the specified position.
     *
     * @param pos the position to check
     * @return {@code true} if this ship occupies {@code pos};
     *         {@code false} otherwise
     * @throws AssertionError if {@code pos} is {@code null}
     */
    @Override
    public boolean occupies(IPosition pos) {
        assert pos != null;

        for (int i = 0; i < getSize(); i++)
            if (getPositions().get(i).equals(pos))
                return true;
        return false;
    }

    /**
     * Determines whether this ship is too close to another ship.
     *
     * <p>The ships are considered too close if any position occupied by
     * the other ship is adjacent to one of this ship's positions.</p>
     *
     * @param other the other ship to check
     * @return {@code true} if the ships are too close;
     *         {@code false} otherwise
     * @throws AssertionError if {@code other} is {@code null}
     */
    @Override
    public boolean tooCloseTo(IShip other) {
        assert other != null;

        Iterator<IPosition> otherPos = other.getPositions().iterator();
        while (otherPos.hasNext())
            if (tooCloseTo(otherPos.next()))
                return true;

        return false;
    }

    /**
     * Determines whether this ship is too close to the specified position.
     *
     * @param pos the position to check
     * @return {@code true} if the position is adjacent to any position
     *         occupied by this ship; {@code false} otherwise
     */
    @Override
    public boolean tooCloseTo(IPosition pos) {
        for (int i = 0; i < this.getSize(); i++)
            if (getPositions().get(i).isAdjacentTo(pos))
                return true;
        return false;
    }

    /**
     * Shoots at the specified position.
     *
     * <p>If the specified position is occupied by this ship, that position
     * is marked as hit.</p>
     *
     * @param pos the position at which to shoot
     * @throws AssertionError if {@code pos} is {@code null}
     */
    @Override
    public void shoot(IPosition pos) {
        assert pos != null;

        for (IPosition position : getPositions()) {
            if (position.equals(pos))
                position.shoot();
        }
    }

    /**
     * Returns a string representation of this ship.
     *
     * @return a string containing the ship category, bearing and position
     */
    @Override
    public String toString() {
        return "[" + category + " " + bearing + " " + pos + "]";
    }
}
