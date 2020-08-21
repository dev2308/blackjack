package main;

public class Player {
	private int money;
	private Hand hand;
	private String name;

	public Player(String name){
		money = 0;
		hand = new Hand();
		this.name = name;
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

	public String name(){
		return name;
	}

	public void println(String s){
		System.out.println(name+"> "+s);
	}

	public boolean wantsHit() {
		return "y".equalsIgnoreCase(prompt("Hit?"));

	}

	public String prompt(String text) {
		return "";
	}
}
