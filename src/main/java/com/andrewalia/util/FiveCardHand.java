package com.andrewalia.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import org.apache.commons.collections4.iterators.ArrayIterator;

/**
 * Represents a five-card poker hand.
 */
public class FiveCardHand implements Comparable<FiveCardHand>, Collection<Card> {

    private final Card[] cards;

    /**
     * Gets the cards in this hand.
     * @return An array of Card objects.
     */
    public Card[] getCards() {
        return cards;
    }

    /**
     * Constructs a FiveCardHand with the given array of cards.
     * @param cards An array of Card objects.
     */
    public FiveCardHand(Card[] cards) {
        this.cards = Arrays.copyOf(cards, cards.length);
        Arrays.sort(cards);

        if (cards.length != 5) {
            throw new IllegalArgumentException("FiveCardHand must have exactly five cards");
        }
    }

    @Override
    public String toString() {
        //short hand names separated by spaces
        return Arrays.stream(cards).map(Card::getShortName).reduce("", (a, b) -> a + " " + b).trim();
    }

    /**
     * Generates a string description of the hand type based on the cards in the hand.
     * @return A string describing the type of poker hand.
     */
    public String description() {
        HandType.getBestHandType(this).rearrange(this);
        if (HandType.ROYAL_FLUSH.test(this)) return "Royal Flush";
        if (HandType.STRAIGHT_FLUSH.test(this)) return "Straight Flush, " + cards[0].getRank() + " high";
        if (HandType.FOUR_OF_A_KIND.test(this)) return "Four of a Kind, " + cards[0].getRank() + "s, " + cards[4].getRank() + " kicker";
        if (HandType.FULL_HOUSE.test(this)) return "Full House, " + cards[0].getRank() + "s over " + cards[3].getRank() + "s";
        if (HandType.FLUSH.test(this)) return "Flush, " + cards[0].getRank() + " high";
        if (HandType.STRAIGHT.test(this)) return "Straight, " + cards[0].getRank() + " high";
        if (HandType.THREE_OF_A_KIND.test(this)) return "Three of a Kind, " + cards[0].getRank() + "s, " + cards[3].getRank() + " kicker";
        if (HandType.TWO_PAIR.test(this)) return (
            "Two Pair, " + cards[0].getRank() + "s and " + cards[2].getRank() + "s, " + cards[4].getRank() + " kicker"
        );
        if (HandType.PAIR.test(this)) return "Pair of " + cards[0].getRank() + "s, " + cards[2].getRank() + " kicker";
        return "High Card, " + cards[0].getRank() + " high";
    }

    public String getShortName() {
        StringBuilder sb = new StringBuilder();
        for (Card card : cards) {
            sb.append(card.getShortName());
            sb.append(" ");
        }
        return sb.toString();
    }

    /**
     * Compares this hand with another hand to break a tie.
     * @param other The other FiveCardHand.
     * @return An integer representing the comparison result.
     */
    private int breakTie(FiveCardHand other) {
        for (int i = 0; i < cards.length; i++) {
            if (cards[i].getRank().getValue() > other.cards[i].getRank().getValue()) {
                return 1;
            } else if (cards[i].getRank().getValue() < other.cards[i].getRank().getValue()) {
                return -1;
            }
        }
        return 0;
    }

    /**
     * Compares this FiveCardHand to another FiveCardHand.
     * @param o The other FiveCardHand.
     * @return An integer representing the comparison result.
     */
    @Override
    public int compareTo(FiveCardHand o) {

        HandType thisBestHandType = HandType.getBestHandType(this);
        HandType otherBestHandType = HandType.getBestHandType(o);

        if (thisBestHandType.compareTo(otherBestHandType) > 0) {
            return 1;
        } else if (thisBestHandType.compareTo(otherBestHandType) < 0) {
            return -1;
        }

        thisBestHandType.rearrange(this);
        otherBestHandType.rearrange(o);
        return breakTie(o);
    }

    @Override
    public int size() {
        return 5;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        if (!(o instanceof Card)) return false;
        final Card otherCard = (Card) o;
        for (Card card : cards) if (card.equals(otherCard)) return true;
        return false;
    }

    @Override
    public Iterator<Card> iterator() {
        return new ArrayIterator<>(cards);
    }

    @Override
    public Object[] toArray() {
        return cards.clone();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length >= 5) {
            for (int i = 0; i < 5; i++) {
                a[i] = (T) cards[i];
            }
            return (T[]) a;
        }
        return (T[]) toArray();
    }

    @Override
    public boolean add(Card e) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object object : c) {
            if (!(object instanceof Card)) return false;
            if (!contains(object)) return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends Card> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    /**
     * Overrides the equals method to compare two FiveCardHand objects for equality.
     * <p>
     * Note: This class has a natural ordering that is inconsistent with this equals implementation.
     * </p>
     * 
     * Two FiveCardHand objects are considered equal if:
     * 1. They have the same set of cards by both rank and suit.
     * 2. The order in which the cards appear does not matter.
     *
     * However, this equality check is not consistent with the compareTo method:
     * Two FiveCardHand objects with the same rank but different suits will be considered 
     * unequal by this equals method, but compareTo would return 0 in such cases.
     * 
     * @param obj The object to compare this FiveCardHand against.
     * @return true if the given object is a FiveCardHand with the same set of cards; false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof FiveCardHand)) return false;
        final FiveCardHand other = (FiveCardHand) obj;
        Card[] thisCards = Arrays.copyOf(cards, cards.length);
        Card[] otherCards = Arrays.copyOf(other.cards, other.cards.length);
        Arrays.sort(thisCards);
        Arrays.sort(otherCards);
        return Arrays.equals(thisCards, otherCards);
    }
}
