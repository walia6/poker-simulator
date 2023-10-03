package com.andrewalia.util;

/**
 * Represents a playing card with a rank and suit.
 */
public class Card implements Comparable<Card> {

    private final Rank rank;
    private final Suit suit;

    /**
     * Constructor to initialize the card with a rank and suit.
     * @param rank The rank of the card.
     * @param suit The suit of the card.
     */
    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    /**
     * Gets the rank of the card.
     * @return The rank of the card.
     */
    public Rank getRank() {
        return rank;
    }

    /**
     * Gets the suit of the card.
     * @return The suit of the card.
     */
    public Suit getSuit() {
        return suit;
    }

    /**
     * Converts the card to its String representation.
     * @return The String representation of the card.
     */
    @Override
    public String toString() {
        return rank + " of " + suit;
    }

    /**
     * Compares this card to another object for equality.
     * @param obj The object to compare.
     * @return True if equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Card)) {
            return false;
        }
        final Card other = (Card) obj;
        if (this.rank != other.rank && (this.rank == null || !this.rank.equals(other.rank))) {
            return false;
        }
        if (this.suit != other.suit && (this.suit == null || !this.suit.equals(other.suit))) {
            return false;
        }
        return true;
    }

    /**
     * Gets the short name of the card, e.g., "AS" for Ace of Spades.
     * @return The short name of the card.
     */
    public String getShortName() {
        return Character.toString(rank.getShortName()) + Character.toString(suit.getShortName());
    }

    /**
     * Compares this card to another card.
     * @param o The card to compare.
     * @return An integer representing the comparison result.
     */
    @Override
    public int compareTo(Card o) {
        int result = rank.compareTo(o.rank);
        return result;
    }

    /**
     * Creates a new Card object from its short name.
     * @param shortName The short name of the card.
     * @return A new Card object.
     */
    public static Card valueOf(String shortName) {
        if (shortName.length() != 2) {
            throw new IllegalArgumentException("Invalid card name " + shortName);
        }
        Rank rank = Rank.valueOf(shortName.charAt(0));
        Suit suit = Suit.valueOf(shortName.charAt(1));
        return new Card(rank, suit);
    }
}
