package main;

import java.util.ArrayList;

public class Hand {
	private ArrayList<Card> cards;
	private int score;
	public Hand() {
		cards = new ArrayList<>();
		score = 0;
	}

	//Getter methods

	@Override
	public String toString(){
		String cardString = "";
		for(Card card: cards){
			cardString += card + " ";
		}
		return cardString;
	}

	public String toStringDealer(){
		return (cards.get(0) + " ▮ ");
	}

	public Card[] cards(){
		Card[] cardsArray = new Card[cards.size()];
		for(int i = 0; i < cards.size(); i++){
			cardsArray[i] = cards.get(i);
		}
		return cardsArray;
	}

	public int score(){
		for(Card card: cards){
			if(card.face().equals("A") && score > 21){
				return score-10;
			}
		}
		return score;
	}

	//Methods to modify hand

	public void add(Card newCard){
		cards.add(newCard);
		score += newCard.value();
	}

	public void clear(){
		score = 0;
		cards = new ArrayList<>();
	}
}
