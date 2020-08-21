package main;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class BlackjackQueued {
	private ArrayList<Player> players;
	private Player dealer;
	private Deck deck;

	//-------------------------------
	// these routines handle low level input/output from players

	// queue that holds all player input
	private final BlockingQueue<PlayerInput> playerInput = new LinkedBlockingDeque<PlayerInput>();

	// one of these is created and put on the queue when the user inputs something
	private static class PlayerInput {
		public PlayerInput(Player player, String input) {
			this.player = player;
			this.input = input;
		}
		public Player player;
		public String input;
	}

	// this thread will read from the console and queue the input
	private class InputThread extends Thread {
		public void run() {
			Scanner scanner = new Scanner(System.in);
			while (!isInterrupted()) {
				String line = scanner.nextLine();
				String[] parts = line.split(",");
				try {
					int playerNdx = Integer.parseInt(parts[0]);
					playerInput.add(new PlayerInput(players.get(playerNdx), parts[1]));
				} catch (NumberFormatException | IndexOutOfBoundsException e) {
					System.out.println("Bad input");
				}
			}
			scanner.close();
		}
	}

	private PlayerInput getInput() {
		try {
			return playerInput.take();
		} catch (InterruptedException e) {
			// Something is shutting down.  Just let stuff die a horrible, empty death.
			e.printStackTrace();
			return null;
		}
	}

	private void printToPlayer(Player player, String message) {
		System.out.println(player.name() + "> " + message);
	}

	private String readFromPlayer(Player player) {
		PlayerInput input = getInput();
		while (input.player != player) {
			// broadcast input from other players as chat messages
			for (Player chatReceiver : players) {
				if (chatReceiver != input.player) {
					printToPlayer(chatReceiver, "<chat from " + input.player.name() + ">" + input.input);
				}
			}
			input = getInput();
		}
		return input.input;
	}
	//-----------------------------------

	public  static void main(String[] args) {
		new BlackjackQueued().run();
	}

	public BlackjackQueued() {
		players = new ArrayList<>();
		for(int i = 0; i < 4; i++){
			players.add(new LocalHumanPlayer("Player" + i));
		}
		dealer = new LocalRoboPlayer("Dealer");
		deck = new Deck();
	}

	public void run() {
		new InputThread().start();
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

	private boolean playerWantsHit(Player player) {
		if (player instanceof LocalRoboPlayer) {
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
		for(Player p : players){
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
			return "Player Wins";
		} else if (playerScore == dealerScore) {
			return "Push";
		} else if (playerScore < dealerScore) {
			return "Dealer Wins";
		} else {
			return "Player Wins";
		}
	}
}
