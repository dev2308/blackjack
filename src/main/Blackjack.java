package main;

import java.util.*;

public class Blackjack {
    private Hand playerCards = new Hand();
	private Hand dealerCards = new Hand();

	public  static void main(String[] args) {
		new Blackjack().run();

	}

	public Blackjack() {
	}

    public void run() {
        Deck deck = new Deck();
        deck.shuffle();
		boolean playing = true;
	    Scanner scan1 = new Scanner(System.in);
		while(playing){
			playOneRound(deck);
			System.out.println("Play Again? (Y or N)");
			String response = scan1.next();
			if(!response.equalsIgnoreCase("y")){
				playing = false;
			}

		}
	    scan1.close();
    }

     void playOneRound(Deck deck){
        deck = dealCards(deck);
        printCardsHidden();
		deck = runPlayerHand(deck);
		printCards();
		deck = runDealerHand(deck);
		printResult(playerCards.score(), dealerCards.score());
	    playerCards.clear();
	    dealerCards.clear();
    }

    private Deck runPlayerHand(Deck deck){
    	Scanner scan = new Scanner(System.in);
    	boolean acceptingCards = true;
    	while(acceptingCards && playerCards.score() <= 21){
    		System.out.println("Hit or Knock (Enter H or K)");
    		String response = scan.next();
    		if(response.equalsIgnoreCase("H")){
    			playerCards.add(deck.deal());
			    System.out.flush();
			    printCardsHidden();
		    }
    		else{
    			acceptingCards = false;
		    }
	    }
    	return deck;
    }

    private Deck runDealerHand(Deck deck){
    	while(dealerCards.score() < 17){
			dealerCards.add(deck.deal());
		    System.out.flush();
		    printCards();
	    }
    	return deck;
    }

	private Deck dealCards(Deck deck){
		for(int i = 0; i < 2; i ++){
			playerCards.add(deck.deal());
			dealerCards.add(deck.deal());
		}
		return deck;
	}

	private  void printCards(){
		System.out.println("|-----Your Cards-----|---Dealer's Cards--|");
		System.out.print("| " + Utils.formatSpaces(playerCards.toString()) + " | " + Utils.formatSpaces(dealerCards.toString()) + " |");
		System.out.println("\nYour Score: " + playerCards.score() + "\nDealer Score: " + dealerCards.score());
	}

    private void printCardsHidden(){
        System.out.println("|-----Your Cards-----|---Dealer's Cards--|");
	    System.out.print("| " + Utils.formatSpaces(playerCards.toString()) + " | " + Utils.formatSpaces(dealerCards.toStringDealer()) + " |");
        System.out.println("\nYour Score: " + playerCards.score());
    }

	private  void printResult(int playerScore, int dealerScore){
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

}
