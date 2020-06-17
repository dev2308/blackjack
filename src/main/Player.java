package main;

public class Player {
	private int money;
	private Hand hand;
	public Player(){
		hand = new Hand();
	}

	public void deal(Card newCard){
		hand.add(newCard);
	}

	public void clearHand(){
		hand.clear();
	}

	public Hand hand(){
		return hand;
	}
}
