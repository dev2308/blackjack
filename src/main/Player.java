package main;

import java.io.InputStream;
import java.util.Scanner;

public abstract class Player {
	private int money;
	private Hand hand;
	private String name;

	public Player(){
		hand = new Hand();
		money = 0;
	}

	public Player(String name){
		hand = new Hand();
		this.name = name;
		money = 0;
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




}
