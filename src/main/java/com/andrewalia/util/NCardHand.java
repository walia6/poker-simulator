package com.andrewalia.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import org.apache.commons.math3.util.Combinations;

/**
 * Class to represent a hand consisting of N cards.
 */
public class NCardHand implements Comparable<NCardHand> {

    private final Card[] cards;
    private FiveCardHand bestFiveCardHand = null;

    /**
     * Constructor to create an NCardHand.
     * 
     * @param cards Array of cards to initialize the hand.
     */
    public NCardHand(Card[] cards) {
        this.cards = Arrays.copyOf(cards, cards.length);
    }

    /**
     * Finds and returns the best possible five-card hand from this N-card hand.
     * 
     * @return The best five-card hand.
     */
    public FiveCardHand getBestFiveCardHand() {
        if (bestFiveCardHand == null) {
            bestFiveCardHand = new FiveCardHand((Card[]) Collections.max(getAllFiveCardHands()).toArray());
        }
        return bestFiveCardHand;
    }

    /**
     * Generates all possible five-card hands from this N-card hand.
     * 
     * @return A collection of all possible five-card hands.
     */
    public Collection<FiveCardHand> getAllFiveCardHands() {
        if (cards.length < 5) {
            throw new IllegalArgumentException("Not enough cards");
        }

        Combinations fiveCardHandIndexCombinations = new Combinations(cards.length, 5);
        Collection<FiveCardHand> fiveCardHandsCombinations = new LinkedList<>();
        
        for (int[] is : fiveCardHandIndexCombinations) {
            final Card[] fiveCardArray = new Card[5];
            for (int i = 0; i < 5; i++) {
                fiveCardArray[i] = cards[is[i]];
            }
            fiveCardHandsCombinations.add(new FiveCardHand(fiveCardArray));
        }
        return fiveCardHandsCombinations;
    }

    /**
     * Compares this hand to another NCardHand based on their best five-card hands.
     * 
     * @param o The other NCardHand to compare.
     * @return An integer indicating comparison result.
     */
    @Override
    public int compareTo(NCardHand o) {
        return getBestFiveCardHand().compareTo(o.getBestFiveCardHand());
    }

    /**
     * Returns a string representation of this hand.
     * 
     * @return A space-separated string of the short names of the cards in this hand.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Card card : cards) {
            sb.append(card.getShortName());
            sb.append(" ");
        }
        return sb.toString();
    }
}
