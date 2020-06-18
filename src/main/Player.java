package main;

public class Player {
	private int money;
	private Hand hand;
	public Player(){
		hand = new Hand();
		money = 0;
	}

	public Player(int startingMoney){
		hand = new Hand();
		money = startingMoney;
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

	public boolean bet(int amount){
		if(amount < money){
			money -= amount;
			return true;
		}
		return false;
	}

	public void addMoney(int amount){
		money += amount;
	}
}
