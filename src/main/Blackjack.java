package main;

import java.lang.reflect.Array;
import java.util.*;

public class Blackjack {
    private static final String[] suits = {"♠", "♥", "♣", "♦"};
    private static final String[] faces = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
    private static ArrayList<String> playerCards = new ArrayList<>();
    private static ArrayList<String> dealerCards = new ArrayList<>();
    public static void main(String[] args) {
        String[] deck = makeDeck(suits, faces);
	    ArrayList<String> shuffled = new ArrayList<>(shuffleDeck(deck));
        playOneRound(shuffled);
        //System.out.println("\n" + shuffled.length);
        //hidden card: ▮
    }

    static void playOneRound(ArrayList<String> deck){
        deck = dealCards(deck);
        printCards();

    }

    static void printCards(){
        System.out.println("|----Your Cards-----|---Dealer's Cards--|");
        System.out.print("|");
        printHand(playerCards);
        printDealerHand(dealerCards);
    }

    static void printHand(ArrayList<String> hand){
        for(String card: hand){
            System.out.print(card + " ");
        }
        for(int i = 3 * hand.size(); i < 20; i+=4){
            System.out.print("\t");
        }
        System.out.print("|");
    }

    static void printDealerHand(ArrayList<String> hand){
        System.out.print(hand.get(0) + " ▮ ");
        for(int i = 5; i < 20; i+=4){
            System.out.print("\t");
        }
        System.out.print("|");
    }

    static void printArray(String[] arr){
        for(String asdf: arr){
            System.out.print(asdf + " ");
        }
    }

    private static ArrayList<String> dealCards(ArrayList<String> deck){
        for(int i = 0; i < 2; i ++){
            playerCards.add(deck.remove(0));
            dealerCards.add(deck.remove(0));
        }
        return deck;
    }

    private static String[] makeDeck(String[] suits, String[] faces){
        String[] unshuffled = new String[52];
        int ind = 0;
        for(String suit: suits){
            for(String face: faces){
                unshuffled[ind++] = face + suit;
            }
        }
        return unshuffled;
    }

    private static List<String> shuffleDeck(String[] unShuffled){
    	List<String> deckList = Arrays.asList(unShuffled);
	    Collections.shuffle(deckList);
    	return deckList;
    }
}
