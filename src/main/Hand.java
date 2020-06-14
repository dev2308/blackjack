package main;

import java.util.ArrayList;

public class Hand {
	private ArrayList<String> cards;
	private int score;
	public Hand() {
		cards = new ArrayList<>();
		score = 0;
	}

	//Getter methods

	@Override
	public String toString(){
		String cardString = "";
		for(String card: cards){
			cardString += card + " ";
		}
		return cardString;
	}

	public String toStringDealer(){
		return (cards.get(0) + " ▮ ");
	}

	public String[] cards(){
		String[] cardsArray = new String[cards.size()];
		for(int i = 0; i < cards.size(); i++){
			cardsArray[i] = cards.get(i);
		}
		return cardsArray;
	}

	public int score(){
		for(String card: cards){
			if(card.contains("A") && score > 21){
				return score-10;
			}
		}
		return score;
	}

	//Methods to modify hand

	public void add(String newCard){
		cards.add(newCard);
		score += parseCardScore(newCard);
	}

	public void clear(){
		score = 0;
		cards = new ArrayList<>();
	}

	//Misc calculation/utility methods

	private int parseCardScore(String card){
		switch(card.charAt(0)){
			case 'A':
				return 11;
			case 'K':
				return 10;
			case 'Q':
				return 10;
			case 'J':
				return 10;
			case '1':
				if(card.length() > 2){
					return 10;
				}
				else{
					return 1;
				}
			default:
				return card.charAt(0) - 48;
		}
	}


}
