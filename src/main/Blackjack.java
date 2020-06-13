package main;

import java.lang.reflect.Array;
import java.util.*;
import main.Hand;

public class Blackjack {
    private static final String[] suits = {"♠", "♥", "♣", "♦"};
    private static final String[] faces = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
    private static Hand playerCards = new Hand();
	private static Hand dealerCards = new Hand();
    public static void main(String[] args) {
        String[] deck = makeDeck(suits, faces);
		boolean playing = true;
	    Scanner scan1 = new Scanner(System.in);
		while(playing){
			playOneRound(new ArrayList<>(shuffleDeck(deck)));
			System.out.println("Play Again? (Y or N)");

			String response = scan1.next();
			if(!response.equalsIgnoreCase("y")){
				playing = false;
			}
			playerCards.clear();
			dealerCards.clear();
		}
	    scan1.close();
    }

    static void playOneRound(ArrayList<String> deck){
        deck = dealCards(deck);
        printCardsHidden();
		deck = runPlayerHand(deck);
		printCards();
		deck = runDealerHand(deck);
		printResult(playerCards.score(), dealerCards.score());
    }

    private static ArrayList<String> runPlayerHand(ArrayList<String> deck){
    	Scanner scan = new Scanner(System.in);
    	boolean acceptingCards = true;
    	while(acceptingCards && playerCards.score() <= 21){
    		System.out.println("Hit or Knock (Enter H or K)");
    		String response = scan.next();
    		if(response.equalsIgnoreCase("H")){
    			playerCards.add(deck.remove(0));
			    System.out.flush();
			    printCardsHidden();
		    }
    		else{
    			acceptingCards = false;
		    }
	    }
    	return deck;
    }

    private static ArrayList<String> runDealerHand(ArrayList<String> deck){
    	while(dealerCards.score() < 17){
			dealerCards.add(deck.remove(0));
		    System.out.flush();
		    printCards();
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

	private static ArrayList<String> dealCards(ArrayList<String> deck){
		for(int i = 0; i < 2; i ++){
			playerCards.add(deck.remove(0));
			dealerCards.add(deck.remove(0));
		}
		return deck;
	}

	private static List<String> shuffleDeck(String[] unShuffled){
		List<String> deckList = Arrays.asList(unShuffled);
		Collections.shuffle(deckList);
		return deckList;
	}



	private static void printCards(){
		System.out.println("|-----Your Cards-----|---Dealer's Cards--|");
		System.out.print("| " + formatSpaces(playerCards.toString()) + " | " + formatSpaces(dealerCards.toString()) + " |");
		System.out.println("\nYour Score: " + playerCards.score() + "\nDealer Score: " + dealerCards.score());
	}

    static void printCardsHidden(){
        System.out.println("|-----Your Cards-----|---Dealer's Cards--|");
	    System.out.print("| " + formatSpaces(playerCards.toString()) + " | " + formatSpaces(dealerCards.toStringDealer()) + " |");
        System.out.println("\nYour Score: " + playerCards.score());
    }

	private static void printResult(int playerScore, int dealerScore){
    	System.out.println("#########################");
		if(playerScore == dealerScore || (playerScore > 21 && dealerScore > 21)){
			System.out.println("Push");
		}
		else if(playerScore > 21 || (dealerScore > playerScore && dealerScore <= 21)){
			System.out.println("Dealer Wins");
		}
		else{
			System.out.println("Player Wins!");
		}
		System.out.println("#########################");
	}

	static String formatSpaces(String s){
    	for(int i = s.length(); i < 15; i++){
    		s+= " ";
	    }
    	s+="\t";
    	return s;
	}

    static void printArray(String[] arr){
        for(String asdf: arr){
            System.out.print(asdf + " ");
        }
    }
}
