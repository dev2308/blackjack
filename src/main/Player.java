package main;

import java.util.Scanner;

public class Player {
	private int money;
	private Hand hand;
	private String name;

	public Player(Blackjack game, String name){
		money = 0;
		hand = new Hand();
		this.name = name;
		new Thread() {
			public void run() {
				Scanner scan1 = new Scanner(System.in);
				do {
					String line = scan1.next();
					game.handleUserInput(Player.this, line);
				} while (!this.isInterrupted());
			}
		};
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
}
