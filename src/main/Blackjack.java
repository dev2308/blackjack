package main;

import java.util.ArrayList;
import java.util.Scanner;

public class Blackjack {
	private ArrayList<Player> players;
	private Player dealer;
	private Deck deck;

	public  static void main(String[] args) {
		new Blackjack().run();
	}

	public Blackjack() {
		players = new ArrayList<>();
		for(int i = 0; i < 4; i++){
			players.add(new Player("Player" + i));
		}
		dealer = new Player("Dealer");
		deck = new Deck();
	}

    public void run() {
		boolean playing = true;
		while(playing){
			playOneRound();
			printToPlayer(dealer, "Play Again? (Y or N)");
			if(!"y".equalsIgnoreCase(readFromPlayer(dealer))) {
				playing = false;
			}
			if(deck.size() <= 13){
				deck.reshuffle();
			}
		}
    }

	//-------------------------------
    // these routines handle low level input/output from players
	private final Scanner scanner = new Scanner(System.in);

    private void printToPlayer(Player player, String message) {
		System.out.println(player.name() + "> " + message);
    }

    private String readFromPlayer(Player player) {
		return scanner.nextLine();
    }
    //-------------------------------

	private boolean playerWantsHit(Player player) {
		if (player == dealer) {
			return player.hand().score() < 17;
		} else {
			printToPlayer(player, "Hit? ");
			return "y".equalsIgnoreCase(readFromPlayer(player));
		}
	}

    private void playOneRound(){
		printToPlayer(dealer, "Starting Cards:");
		dealCards();
		printCardsHidden();
		runHands();
		runDealerHand();
	    printToPlayer(dealer, "Final Cards:");
		printCards();
		for(Player p: players){
			p.clearHand();
		}
	    dealer.clearHand();
    }

    private void runHands(){
		for(Player p : players) {
			if (p.hand().score() < 21) {
				boolean askForHit = true;
				while (askForHit) {
					printCardsHidden();
					if (playerWantsHit(p)) {
						p.hand().add(deck.deal());
						askForHit = p.hand().score() < 21;
					} else {
						askForHit = false;
					}
				}
			}
		}
    }

    private void runDealerHand(){
    	while(dealer.hand().score() < 17){
			dealer.hand().add(deck.deal());
	    }
    }

	private void dealCards(){
		for(int i = 0; i < 2; i ++){
			for(Player p: players) {
				p.deal(deck.deal());
			}
			dealer.deal(deck.deal());
		}
	}

	private void printCards(){
		for (Player printPlayer : players) {
			printToPlayer(printPlayer, "Dealer Cards: " + dealer.hand().score() + " points, " + dealer.hand().toString());
			for (Player p : players) {
				printToPlayer(printPlayer, p.name() + ": " + p.hand().score() + " points, "
						+ p.hand().toString() + " - " + printResult(p.hand().score(), dealer.hand().score()));
			}
		}
	}

    private void printCardsHidden(){
	    for (Player printPlayer : players) {
		    printToPlayer(printPlayer, ("Dealer Cards: " + dealer.hand().toStringDealer()));
		    for (Player p : players) {
			    printToPlayer(printPlayer, (p.name() + ": " + p.hand().score() + " points, " + p.hand().toString()));
		    }
	    }
    }

	private String printResult(int playerScore, int dealerScore){
		if (playerScore > 21) {
			return "Dealer Wins";
		} else if (dealerScore > 21) {
			return "Player Winds";
		} else if (playerScore == dealerScore) {
			return "Push";
		} else if (playerScore < dealerScore) {
			return "Dealer Wins";
		} else {
			return "Player Wins";
		}
	}

}
