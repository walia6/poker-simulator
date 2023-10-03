package com.andrewalia.simulator.permutations.util;

import java.util.HashMap;
import java.util.stream.Stream;

import com.andrewalia.util.HoleCards;
import org.javatuples.Triplet;

/**
 * The ShowdownResultsMap class extends HashMap and is specialized for storing
 * and processing results of card game showdowns.
 */
public class ShowdownResultsMap extends HashMap<HoleCards,Triplet<Long[],Long[],Long[]>>{
    public ShowdownResultsMap() {
        super();
    }

    /**
     * Converts the map to a human-readable String representation.
     *
     * @return The String representation of the map.
     */
    @Override
    public synchronized String toString() {
        if (isEmpty()) {
            return "No results";
        }
        final StringBuilder sb = new StringBuilder();
        entryStreamForEach(entryStreamFilteredSorted(), sb);
        sb.deleteCharAt(sb.length() - 1);

        return sb.toString();
    }

    /**
     * Converts a limited number of entries of the map to a String representation.
     *
     * @param limit The maximum number of entries to include in the String representation.
     * @return The limited String representation of the map.
     */
    public synchronized String toString(int limit) {
        final StringBuilder sb = new StringBuilder();
        entryStreamForEach(entryStreamFilteredSorted().filter(
            (e) -> {
                final Triplet<Long,Long,Long> eTriplet = new Triplet<Long,Long,Long>(e.getValue().getValue0()[0], e.getValue().getValue1()[0], e.getValue().getValue2()[0]);
                return (eTriplet.getValue0() + eTriplet.getValue1() + eTriplet.getValue2()) > 100;
            }            
        ).limit(limit), sb);
        try{
            sb.deleteCharAt(sb.length() - 1);
        } catch (StringIndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    /**
     * Helper method to append map entries to a StringBuilder.
     *
     * @param stream The stream of map entries.
     * @param sb     The StringBuilder object.
     */
    private void entryStreamForEach(Stream<Entry<HoleCards, Triplet<Long[], Long[], Long[]>>> stream, StringBuilder sb){
        stream.forEach( (e) -> {
            final Triplet<Long,Long,Long> eTriplet = new Triplet<Long,Long,Long>(e.getValue().getValue0()[0], e.getValue().getValue1()[0], e.getValue().getValue2()[0]);
            final long eWins = eTriplet.getValue0();
            final long eLosses = eTriplet.getValue1();
            final long eTies = eTriplet.getValue2();

            sb.append(e.getKey());
            if (2 == e.getKey().toString().length()) {
                sb.append(" ");
            }
            sb.append("\n\tWIN%\t");
            ///give the percentage of wins out of wins + losses + ties, to 2 decimal places
            sb.append(String.format("%.2f", ((double) eWins / (eWins + eLosses + eTies)) * 100));
            sb.append("\n\tWIN#\t");
            sb.append(eWins);
            sb.append("\n\tLOSS#\t");
            sb.append(eLosses);
            sb.append("\n\tTIE#\t");
            sb.append(eTies);
            sb.append("\n");
        });
    }
    
    /**
     * Helper method to return a sorted and filtered stream of map entries.
     *
     * @return The sorted and filtered stream of map entries.
     */
    private Stream<Entry<HoleCards, Triplet<Long[], Long[], Long[]>>> entryStreamFilteredSorted() {
        return entrySet().stream()
        .filter((e) -> {
            final Triplet<Long,Long,Long> eTriplet = new Triplet<Long,Long,Long>(e.getValue().getValue0()[0], e.getValue().getValue1()[0], e.getValue().getValue2()[0]);
            return 0 != (eTriplet.getValue0() + eTriplet.getValue1() + eTriplet.getValue2());
        })
        .sorted(
            (e0,e1) -> {
                    final Triplet<Long,Long,Long> e0Triplet = new Triplet<Long,Long,Long>(e0.getValue().getValue0()[0], e0.getValue().getValue1()[0], e0.getValue().getValue2()[0]);
                    final Triplet<Long,Long,Long> e1Triplet = new Triplet<Long,Long,Long>(e1.getValue().getValue0()[0], e1.getValue().getValue1()[0], e1.getValue().getValue2()[0]);
                    final long e0Wins = e0Triplet.getValue0();
                    final long e1Wins = e1Triplet.getValue0();
                    final long e0Losses = e0Triplet.getValue1();
                    final long e1Losses = e1Triplet.getValue1();
                    final long e0Ties = e0Triplet.getValue2();
                    final long e1Ties = e1Triplet.getValue2();

                    return (int) (
                            ((double) e1Wins / (e1Wins + e1Losses + e1Ties)) * 1000000
                        -
                            ((double) e0Wins / (e0Wins + e0Losses + e0Ties)) * 1000000
                    );
                }
        );
    }
}
