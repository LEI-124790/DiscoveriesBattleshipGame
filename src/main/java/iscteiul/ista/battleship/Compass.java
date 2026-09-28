package iscteiul.ista.battleship;

/**
 * Defines ship bearings and their lowercase command characters.
 * West uses {@code o}, from the Portuguese word {@code oeste}.
 *
 * @author fba
 */
public enum Compass {
    /** North, represented by {@code n}. */
    NORTH('n'),
    /** South, represented by {@code s}. */
    SOUTH('s'),
    /** East, represented by {@code e}. */
    EAST('e'),
    /** West, represented by {@code o}. */
    WEST('o'),
    /** An unrecognised direction, represented by {@code u}. */
    UNKNOWN('u');

    /** Character used to represent this bearing in commands and output. */
    private final char c;

    /**
     * Associates a bearing with its command character.
     *
     * @param c the character representing the bearing
     */
    Compass(char c) {
        this.c = c;
    }

    /**
     * Returns the character representing this bearing.
     *
     * @return the lowercase direction character
     */
    public char getDirection() {
        return c;
    }

    /**
     * Returns this bearing as a one-character string.
     *
     * @return the direction character as a string
     */
    @Override
    public String toString() {
        return "" + c;
    }

    /**
     * Converts a lowercase command character to a bearing.
     *
     * @param ch {@code n}, {@code s}, {@code e} or {@code o}
     * @return the corresponding bearing, or {@link #UNKNOWN} for any other character
     */
    static Compass charToCompass(char ch) {
        Compass bearing;
        switch (ch) {
            case 'n':
                bearing = NORTH;
                break;
            case 's':
                bearing = SOUTH;
                break;
            case 'e':
                bearing = EAST;
                break;
            case 'o':
                bearing = WEST;
                break;
            default:
                bearing = UNKNOWN;
        }

        return bearing;
    }
}
