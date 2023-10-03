package com.andrewalia.util;

/**
 * Represents the rank of a playing card.
 */
public class Rank implements Comparable<Rank> {

    public static final Rank ACE = new Rank(14, "Ace", 'A');
    public static final Rank KING = new Rank(13, "King", 'K');
    public static final Rank QUEEN = new Rank(12, "Queen", 'Q');
    public static final Rank JACK = new Rank(11, "Jack", 'J');
    public static final Rank TEN = new Rank(10, "Ten", 'T');
    public static final Rank NINE = new Rank(9, "Nine", '9');
    public static final Rank EIGHT = new Rank(8, "Eight", '8');
    public static final Rank SEVEN = new Rank(7, "Seven", '7');
    public static final Rank SIX = new Rank(6, "Six", '6');
    public static final Rank FIVE = new Rank(5, "Five", '5');
    public static final Rank FOUR = new Rank(4, "Four", '4');
    public static final Rank THREE = new Rank(3, "Three", '3');
    public static final Rank TWO = new Rank(2, "Two", '2');
    public static final Rank[] RANKS = { ACE, KING, QUEEN, JACK, TEN, NINE, EIGHT, SEVEN, SIX, FIVE, FOUR, THREE, TWO };

    private final int value;
    private final char shortName;
    private final String longName;

    /**
     * Constructs a Rank object.
     *
     * @param value     The numerical value of the rank.
     * @param longName  The full name of the rank.
     * @param shortName The abbreviated name of the rank.
     */
    public Rank(int value, String longName, char shortName) {
        this.value = value;
        this.shortName = shortName;
        this.longName = longName;
    }

    /**
     * Gets the full name of the rank.
     *
     * @return The full name.
     */
    public String getLongName() {
        return longName;
    }

    /**
     * Gets the abbreviated name of the rank.
     *
     * @return The abbreviated name.
     */
    public char getShortName() {
        return shortName;
    }

    /**
     * Gets the numerical value of the rank.
     *
     * @return The numerical value.
     */
    public int getValue() {
        return value;
    }

    /**
     * Compares this rank to another rank.
     *
     * @param o The other rank.
     * @return A negative, zero, or positive integer.
     */
    @Override
    public int compareTo(Rank o) {
        return value - o.value;
    }

    /**
     * Returns the full name of the rank as its string representation.
     *
     * @return The full name.
     */
    @Override
    public String toString() {
        return longName;
    }

    /**
     * Determines if this rank is equal to another object.
     *
     * @param obj The other object.
     * @return True if equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Rank other = (Rank) obj;
        return this.value == other.value;
    }

    /**
     * Gets the rank corresponding to the given abbreviated name.
     *
     * @param shortName The abbreviated name.
     * @return The corresponding rank.
     * @throws IllegalArgumentException If no matching rank is found.
     */
    public static Rank valueOf(char shortName) {
        for (Rank rank : new Rank[] { ACE, KING, QUEEN, JACK, TEN, NINE, EIGHT, SEVEN, SIX, FIVE, FOUR, THREE, TWO }) {
            if (rank.getShortName() == shortName) {
                return rank;
            }
        }
        throw new IllegalArgumentException("No rank with short name " + shortName);
    }
}
