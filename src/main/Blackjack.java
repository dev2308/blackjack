package main;

import java.util.*;

public class Blackjack {
    private Player p1 = new Player();
	private Player dealer = new Player();

	public  static void main(String[] args) {
		new Blackjack().run();

	}

	public Blackjack() {
	}

    public void run() {
        Deck deck = new Deck();
		boolean playing = true;
	    Scanner scan1 = new Scanner(System.in);
		while(playing){
			playOneRound(deck);
			System.out.println("Play Again? (Y or N)");
			String response = scan1.next();
			if(!response.equalsIgnoreCase("y")){
				playing = false;
			}
			if(deck.size() <= 13){
				deck.reshuffle();
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
		printResult(p1.hand().score(), dealer.hand().score());
	    p1.clearHand();
	    dealer.clearHand();
    }

    private Deck runPlayerHand(Deck deck){
    	Scanner scan = new Scanner(System.in);
    	boolean acceptingCards = true;
    	while(acceptingCards && p1.hand().score() <= 21){
    		System.out.println("Hit or Knock (Enter H or K)");
    		String response = scan.next();
    		if(response.equalsIgnoreCase("H")){
    			p1.deal(deck.deal());
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
    	while(dealer.hand().score() < 17){
			dealer.hand().add(deck.deal());
		    System.out.flush();
		    printCards();
	    }
    	return deck;
    }

	private Deck dealCards(Deck deck){
		for(int i = 0; i < 2; i ++){
			p1.deal(deck.deal());
			dealer.deal(deck.deal());
		}
		return deck;
	}

	private  void printCards(){
		System.out.println("|-----Your Cards-----|---Dealer's Cards--|");
		System.out.print("| " + Utils.formatSpaces(p1.hand().toString()) + " | " + Utils.formatSpaces(dealer.hand().toString()) + " |");
		System.out.println("\nYour Score: " + p1.hand().score() + "\nDealer Score: " + dealer.hand().score());
	}

    private void printCardsHidden(){
        System.out.println("|-----Your Cards-----|---Dealer's Cards--|");
	    System.out.print("| " + Utils.formatSpaces(p1.hand().toString()) + " | " + Utils.formatSpaces(dealer.hand().toStringDealer()) + " |");
        System.out.println("\nYour Score: " + p1.hand().score());
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
