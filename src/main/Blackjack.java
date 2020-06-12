package main;

import java.lang.reflect.Array;
import java.util.*;

public class Blackjack {
    private static final String[] suits = {"♠", "♥", "♣", "♦"};
    private static final String[] faces = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
    public static void main(String[] args) {
        String[] deck = makeDeck(suits, faces);
        //printArray(deck);
	    //String[] shuffled = shuffleDeck(deck);
        //printArray(shuffled);
        //System.out.println("\n" + shuffled.length);

    }

    static void printArray(String[] arr){
        for(String asdf: arr){
            System.out.print(asdf + " ");
        }
    }

    static String[] makeDeck(String[] suits, String[] faces){
        String[] unshuffled = new String[52];
        int ind = 0;
        for(String suit: suits){
            for(String face: faces){
                unshuffled[ind++] = face + suit;
            }
        }
        return unshuffled;
    }

    static String[] shuffleDeck(String[] unShuffled){
    	String[] shuffled = new String[52];
    	List<String> deckList = Arrays.asList(unShuffled);
	    Collections.shuffle(deckList);
    	deckList.toArray(shuffled);
    	return shuffled;
    }
}
