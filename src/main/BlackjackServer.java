package main;

import main.genericstuff.Command;
import main.genericstuff.GenericServer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*

start()
	set player list
	deal hands
	show hands
	set current player
	ask current player if they want to hit

finish()
	do dealer play
	display results
	do start()

onPlayerInput()
	if input from non-current player
		broadcast chat
	else
		if player wants to hit
			deal card to current player
			if current player score > 21
				if more players
					set current player <- next player
					ask current player if they want to hit
				else
					finish()
				endif
			else
				ask player if they want to hit
			endif
		else
			if more players
				set current player < next player
				ask current player if they want to hit
			else
				state < dealerPlay
			endif
		endif
	endif

onPlayerAdded
	if first player
		greet
		start()
	else
		greet
	endif

onPlayerLeft
	if player is current player
		if more players
			set current player <- next player
			ask current player if they want to hit
		else
			finish()
		endif
	else
		account player quit
	endif

 */

public class BlackjackServer extends GenericServer {
	private Map<ClientProxy, Player> players;
	private Player dealer;
	private Deck deck;
	private List<ClientProxy> playersInGame;
	private int currentPlayer;

	public BlackjackServer() {
		super();
		players = new HashMap<>();
		dealer = new Player("Dealer");
		deck = new Deck();
		playersInGame = new ArrayList<>();
	}

	@Override
	protected void removeClient(ClientProxy client) {
		super.removeClient(client);
		// send announcement that the player left and remove them from player list
		String name = players.get(client).name();
		boolean wasPreviousPlayer = playersInGame.contains(client) && playersInGame.indexOf(client) < currentPlayer;
		boolean wasCurrentPlayer = playersInGame.indexOf(client) == currentPlayer;
		broadcast(name + " has left the game");
		players.remove(client);
		playersInGame.remove(client);
		if (playersInGame.size() == 0) {
			// nobody left in this game, so clean up after it
			finishGame();
			if (players.size() > 0) {
				// still someone waiting, so start a new game
				startGame();
			}
		} else if (wasPreviousPlayer) {
			currentPlayer = currentPlayer - 1;
		} else if (wasCurrentPlayer) {
			currentPlayer = currentPlayer - 1;
			askPlayerForHit();
		}
	}

	@Override
	protected void handleMessageFromClient(ClientProxy client, Command command, String message) {
		switch (command) {
			case HANDLE_NAME:
				// The first thing a new player does is send this message with their name
				players.put(client, new Player(message));
				sendToPlayer("Welcome "+ message, client);
				broadcastFrom(message + " has joined", client);
				if (players.size() == 1) {
					// first player, start a game
					startGame();
				}
				break;
			case HANDLE_INPUT:
				// the player typed something, so go handle it
				handlePlayerInput(client, players.get(client), message);
				break;
			case HANDLE_OUTPUT:
				// We should never get this.  This is meant to go from
				// client to server ONLY.
				break;
			default:
				// Did we miss something?  What the heck is going on here?
				break;
		}

	}

	private void handlePlayerInput(ClientProxy client, Player player, String input) {
		// at this point, there's a game running and somebody said something.
		// was it the current player?
		if (playersInGame.indexOf(client) != currentPlayer) {
			// Not current player.  Some rando.
			String name = players.get(client).name();
			broadcast(name + "> " + input);
		} else {
			// the current player has spoken
			if ("y".equalsIgnoreCase(input)) {
				broadcast(player.name() + " wants a hit");
				player.hand().add(deck.deal());
				printCardsHidden();
				if (player.hand().score() < 21) {
					askPlayerForHit();
				} else {
					// Player busted.  Start next player.
					broadcast(player.name() + " busted");
					nextPlayer();
				}
			} else {
				// Player does not want a hit.  Next player.
				broadcast(player.name() + " holds");
				nextPlayer();
			}
		}
	}

	private void nextPlayer() {
		currentPlayer++;
		if (currentPlayer >= playersInGame.size()) {
			// we've gone through all the players, so finish up
			finishGame();
			startGame();
		} else {
			// we've moved on to the next player, so ask if they want a hit.
			askPlayerForHit();
		}
	}

	//-------------------------------------------------------------------------------
	// Game logic

	private void startGame() {
		if (players.size() > 0) {
			// everyone current here joins the current game
			playersInGame.addAll(players.keySet());
			// announce start of game
			broadcast("Dealing hands to " + playersInGame.size() + " players:");
			StringBuilder nameList = new StringBuilder();
			for (ClientProxy client : playersInGame) {
				nameList.append(players.get(client).name());
				nameList.append(" ");
			}
			broadcast(nameList.toString());
			broadcast("");
			// deal and show cards
			dealCards();
			printCardsHidden();
			// ask first player if they want a hit
			currentPlayer = 0;
			askPlayerForHit();
		}
	}

	private void finishGame() {
		runDealerHand();
		printFinalCards();
		for(Player p: players.values()){
			p.clearHand();
		}
		dealer.clearHand();
		playersInGame.clear();
	}

	private void dealCards(){
		for(int i = 0; i < 2; i ++){
			for(ClientProxy c: playersInGame) {
				Player p = players.get(c);
				p.deal(deck.deal());
			}
			dealer.deal(deck.deal());
		}
	}

	private void printCardsHidden(){
		broadcast("Dealer Cards: " + dealer.hand().toStringDealer());
		for(ClientProxy c: playersInGame) {
			Player p = players.get(c);
			broadcast(p.name() + ": " + p.hand().score() + " points, " + p.hand().toString());
		}
	}

	private void printFinalCards(){
		broadcast("Final Cards");
		broadcast("Dealer Cards: " + dealer.hand().toString());
		for(ClientProxy c: playersInGame) {
			Player p = players.get(c);
			broadcast(p.name() + ": " + p.hand().score() + " points, "
					+ p.hand().toString() + " - " + printResult(p.hand().score(), dealer.hand().score()));
		}
		broadcast("");
	}

	private void runDealerHand() {
		while(dealer.hand().score() < 17){
			dealer.hand().add(deck.deal());
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

	private void askPlayerForHit() {
		sendToPlayer("Do you want a hit? ", getCurrentClient());
	}

	private ClientProxy getCurrentClient() {
		return playersInGame.get(currentPlayer);
	}

	//-------------------------------------------------------------------------------
	// Utilities to send messages to players

	// Send a message to a specific player
	protected void sendToPlayer(String message, ClientProxy client) {
		try {
			client.sendMessageToClient(Command.HANDLE_OUTPUT, "Welcome "+ message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Send message to all players
	protected void broadcast(String message) {
		broadcastFrom(message, null);
	}

	// Send message to all players except one
	protected void broadcastFrom(String message, ClientProxy exceptPlayer) {
		String name = (exceptPlayer == null) ? "" : players.get(exceptPlayer).name() + "> ";

		for (ClientProxy client : players.keySet()) {
			if (exceptPlayer != client) {
				try {
					client.sendMessageToClient(Command.HANDLE_OUTPUT, name + message);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
