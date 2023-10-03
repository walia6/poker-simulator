package com.andrewalia.util;

import it.unimi.dsi.util.XoRoShiRo128PlusRandom;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import org.apache.commons.collections4.list.UnmodifiableList;

/**
 * Represents a deck of playing cards.
 */
public class Deck {

    private final List<Card> cards;
    private final Random random = new XoRoShiRo128PlusRandom();
    public static final List<Card> FULL_DECK;
    /**
     * Full deck as an array. Please don't modify this array.
     */
    public static final Card[] FULL_DECK_ARRAY;

    static {
        LinkedList<Card> TEMP_FULL_DECK = new LinkedList<>();
        FULL_DECK_ARRAY = new Card[52];
        int i = 0;
        for (Suit suit : new Suit[] { Suit.HEARTS, Suit.DIAMONDS, Suit.CLUBS, Suit.SPADES }) {
            for (Rank rank : new Rank[] {
                Rank.ACE,
                Rank.KING,
                Rank.QUEEN,
                Rank.JACK,
                Rank.TEN,
                Rank.NINE,
                Rank.EIGHT,
                Rank.SEVEN,
                Rank.SIX,
                Rank.FIVE,
                Rank.FOUR,
                Rank.THREE,
                Rank.TWO,
            }) {
                final Card card = new Card(rank, suit);
                TEMP_FULL_DECK.add(card);
                FULL_DECK_ARRAY[i++] = card;
            }
        }
        FULL_DECK = UnmodifiableList.unmodifiableList(TEMP_FULL_DECK);
    }

    /**
     * Constructor that initializes a deck with a full set of 52 cards.
     */
    public Deck() {
        cards = new LinkedList<Card>();
        for (Card card : FULL_DECK) {
            cards.add(new Card(card.getRank(), card.getSuit()));
        }
    }

    /**
     * Shuffles the deck.
     */
    public void shuffle() {
        Collections.shuffle(cards, random);
    }

    /**
     * Deals one card from the top of the deck.
     * @return The dealt card.
     */
    public Card deal() {
        return cards.remove(0);
    }

    /**
     * Deals multiple cards from the top of the deck.
     * @param n The number of cards to deal.
     * @return An array containing the dealt cards.
     */
    public Card[] deal(int n) {
        Card[] cards = new Card[n];
        for (int i = 0; i < n; i++) {
            cards[i] = deal();
        }
        return cards;
    }

    /**
     * Peeks at the top card without removing it.
     * @return The top card.
     */
    public Card peek() {
        return cards.get(0);
    }

    /**
     * Gets the size of the deck.
     * @return The size of the deck.
     */
    public int size() {
        return cards.size();
    }

    /**
     * Gets the string representation of the deck.
     * @return The string representation of the deck.
     */
    @Override
    public String toString() {
        return cards.toString();
    }

    /**
     * Adds a card to the deck.
     * @param card The card to add.
     */
    public void add(Card card) {
        cards.add(card);
    }

    /**
     * Removes a card from the deck.
     * @param card The card to remove.
     */
    public void remove(Card card) {
        cards.remove(card);
    }

    /**
     * Gets a card at a specified index.
     * @param i The index of the card.
     * @return The card at the specified index.
     */
    public Card get(int i) {
        return cards.get(i);
    }
}
