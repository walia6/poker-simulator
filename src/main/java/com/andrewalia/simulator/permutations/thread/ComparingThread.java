package com.andrewalia.simulator.permutations.thread;

import java.util.Queue;
import java.util.concurrent.atomic.AtomicLong;

import org.javatuples.Triplet;

import com.andrewalia.simulator.permutations.util.ShowdownResultsMap;
import com.andrewalia.util.Card;
import com.andrewalia.util.Deck;
import com.andrewalia.util.HoleCards;
import com.andrewalia.util.NCardHand;

/**
 * The ComparingThread class handles the comparison of different permutations of cards.
 * It implements the Runnable interface for parallel execution.
 */
public class ComparingThread implements Runnable {

    private static final int REPORTING_INTERVAL = 500000;

    /** Local map to store results of showdown comparisons */
    private final ShowdownResultsMap localShowdownResultsMap;

    /** Counter for tracking number of permutations processed */
    private final AtomicLong permutations;

    /** Global map to store results of showdown comparisons */
    private final ShowdownResultsMap globalShowdownResultsMap;

    /** Queue for card index permutations */
    private final Queue<int[]> queue;

    /** Counter for the number of reports generated */
    private long reports;

    /**
     * Constructor to initialize ComparingThread.
     *
     * @param queue Queue of card index permutations
     * @param permutations AtomicLong counter for permutations
     * @param globalShowdownResultsMap Global map to store showdown results
     */
    public ComparingThread(Queue<int[]> queue, AtomicLong permutations, ShowdownResultsMap globalShowdownResultsMap) {
        this.permutations = permutations;
        this.localShowdownResultsMap = new ShowdownResultsMap();
        this.reports = 0;
        this.globalShowdownResultsMap = globalShowdownResultsMap;
        this.queue = queue;
    }

    /**
     * The main run method where the thread execution starts.
     */
    @Override
    public void run() {
        int[] cardIndexes;
        while (true) {
            synchronized(queue) {
                while (queue.isEmpty()) {
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                cardIndexes = queue.poll();
                queue.notify();
            }

            compareAndUpdateLocalShowdownResultsMap(cardIndexes);

            if (permutations.getAndIncrement() > (reports + 1 ) * REPORTING_INTERVAL) {
                report();
            }
        }
    }


    /**
     * Compares card permutations and updates the local showdown results map.
     *
     * @param cardIndexes An array containing indices of cards
     */
    private void compareAndUpdateLocalShowdownResultsMap(int[] cardIndexes) {
        HoleCards holeCards;
        try {
            holeCards = HoleCards.valueOf(Deck.FULL_DECK.get(cardIndexes[0]), Deck.FULL_DECK.get(cardIndexes[1]));
        } catch (NullPointerException e) {
            System.out.println("cardIndexes[0] = " + cardIndexes[0]);
            System.out.println("cardIndexes[1] = " + cardIndexes[1]);
            throw e;
        }

        if (!localShowdownResultsMap.containsKey(holeCards)) {
            localShowdownResultsMap.put(holeCards, new Triplet<Long[],Long[],Long[]>(new Long[] {0L}, new Long[] {0L}, new Long[] {0L}));
        }

        final int comparison =
                new NCardHand( new Card[] {
                    Deck.FULL_DECK_ARRAY[cardIndexes[0]],
                    Deck.FULL_DECK_ARRAY[cardIndexes[1]],
                    Deck.FULL_DECK_ARRAY[cardIndexes[2]],
                    Deck.FULL_DECK_ARRAY[cardIndexes[3]],
                    Deck.FULL_DECK_ARRAY[cardIndexes[4]],
                    Deck.FULL_DECK_ARRAY[cardIndexes[5]],
                    Deck.FULL_DECK_ARRAY[cardIndexes[6]]})
            .compareTo(
                new NCardHand( new Card[] {
                    Deck.FULL_DECK_ARRAY[cardIndexes[7]],
                    Deck.FULL_DECK_ARRAY[cardIndexes[8]],
                    Deck.FULL_DECK_ARRAY[cardIndexes[2]],
                    Deck.FULL_DECK_ARRAY[cardIndexes[3]],
                    Deck.FULL_DECK_ARRAY[cardIndexes[4]],
                    Deck.FULL_DECK_ARRAY[cardIndexes[5]],
                    Deck.FULL_DECK_ARRAY[cardIndexes[6]]}));

        final Triplet<Long[],Long[],Long[]> localShowdownResults = localShowdownResultsMap.get(holeCards);
      
        if (comparison > 0) { // if the holeCards win
            localShowdownResults.getValue0()[0] = (localShowdownResultsMap.get(holeCards).getValue0()[0] + 1);
        } else if (comparison < 0) { // loss
            localShowdownResults.getValue1()[0] = (localShowdownResultsMap.get(holeCards).getValue1()[0] + 1);
        } else { // tie
            localShowdownResults.getValue2()[0] = (localShowdownResultsMap.get(holeCards).getValue2()[0] + 1);
        }
    }

    /**
     * Reports results by updating the global showdown results map.
     */
    private void report(){
        synchronized (globalShowdownResultsMap) {
            for (final HoleCards holeCards : localShowdownResultsMap.keySet()) {
                if (!(globalShowdownResultsMap.containsKey(holeCards))) {
                    globalShowdownResultsMap.put(holeCards, new Triplet<Long[],Long[],Long[]>(new Long[] {0L}, new Long[] {0L}, new Long[] {0L}));
                }
                final Triplet<Long[],Long[],Long[]> localShowdownResults = localShowdownResultsMap.get(holeCards);
                final Triplet<Long[],Long[],Long[]> globalShowdownResults = globalShowdownResultsMap.get(holeCards);

                globalShowdownResults.getValue0()[0] = (globalShowdownResults.getValue0()[0] + localShowdownResults.getValue0()[0]);
                localShowdownResults.getValue0()[0] = 0L;

                globalShowdownResults.getValue1()[0] = (globalShowdownResults.getValue1()[0] + localShowdownResults.getValue1()[0]);
                localShowdownResults.getValue1()[0] = 0L;

                globalShowdownResults.getValue2()[0] = (globalShowdownResults.getValue2()[0] + localShowdownResults.getValue2()[0]);
                localShowdownResults.getValue2()[0] = 0L;
            }
        }
        reports++;
    }

    
}