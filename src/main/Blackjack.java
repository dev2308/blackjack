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
		printResult(getHandScore(playerCards), getHandScore(dealerCards));
    }

    private static ArrayList<String> runPlayerHand(ArrayList<String> deck){
    	Scanner scan = new Scanner(System.in);
    	boolean acceptingCards = true;
    	while(acceptingCards && getHandScore(playerCards) <= 21){
    		System.out.println("Hit or Knock (Enter H or K)");
    		String response = scan.next();
    		if(response.equals("H")){
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
    	while(getHandScore(dealerCards) < 17){
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
		System.out.println("|----Your Cards-----|---Dealer's Cards--|");
		System.out.print("|");
		printHand(playerCards);
		printHand(dealerCards);
		System.out.println("\nYour Score: " + getHandScore(playerCards) + "\nDealer Score: " + getHandScore(dealerCards));
	}

    static void printCardsHidden(){
        System.out.println("|----Your Cards-----|---Dealer's Cards--|");
        System.out.print("|");
        printHand(playerCards);
        printDealerHand(dealerCards);
        System.out.println("\nYour Score: " + getHandScore(playerCards));
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

    static int getHandScore(ArrayList<String> hand){
    	int sum = 0;
    	for(String card: hand){
			sum += parseCardScore(card);
	    }
    	return sum;
    }

    static int parseCardScore(String card){
    	switch(card.charAt(0)){
		    case 'A':
			    return 1;
		    case 'K':
		    	return 10;
		    case 'Q':
		    	return 10;
		    case 'J':
			    return 10;
		    case '1':
		    	if(card.length() > 2){
		    		return 10;
			    }
		    	else{
		    		return 1;
			    }
		    default:
			    return card.charAt(0) - 48;
		    }

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

    static void printArray(String[] arr){
        for(String asdf: arr){
            System.out.print(asdf + " ");
        }
    }
}
