package com.andrewalia.util;

/**
 * Represents the suit of a playing card.
 */
public class Suit implements Comparable<Suit> {
    
    public final static Suit HEARTS = new Suit("Hearts");
    public final static Suit DIAMONDS = new Suit("Diamonds");
    public final static Suit CLUBS = new Suit("Clubs");
    public final static Suit SPADES = new Suit("Spades");
    public final static Suit[] SUITS = new Suit[]{HEARTS, DIAMONDS, CLUBS, SPADES};

    private final String longName;
    private final char shortName;

    /**
     * Constructs a Suit object.
     *
     * @param longName The full name of the suit.
     */
    public Suit(String longName) {
        this.longName = longName;
        shortName = longName.charAt(0);
    }

    /**
     * Gets the full name of the suit.
     *
     * @return The full name.
     */
    public String getLongName() {
        return longName;
    }

    /**
     * Gets the abbreviated name of the suit.
     *
     * @return The abbreviated name.
     */
    public char getShortName() {
        return shortName;
    }

    /**
     * Returns the full name of the suit as its string representation.
     *
     * @return The full name.
     */
    @Override
    public String toString() {
        return longName;
    }

    /**
     * Compares this suit to another suit.
     *
     * @param o The other suit.
     * @return A negative, zero, or positive integer.
     */
    @Override
    public int compareTo(Suit o) {
        return shortName - o.shortName;
    }

    /**
     * Returns an array of all possible suits.
     *
     * @return An array of suits.
     */
    public static Suit[] values() {
        return null;
    }

    /**
     * Gets the suit corresponding to the given abbreviated name.
     *
     * @param shortName The abbreviated name.
     * @return The corresponding suit.
     * @throws IllegalArgumentException If no matching suit is found.
     */
    static public Suit valueOf(char shortName) {
        for (Suit suit : new Suit[] {HEARTS, DIAMONDS, CLUBS, SPADES}) {
            if (suit.getShortName() == shortName) {
                return suit;
            }
        }
        throw new IllegalArgumentException("No suit with short name " + shortName);
    }
}
