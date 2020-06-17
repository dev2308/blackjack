package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class Deck {
	ArrayList<Card> cards = new ArrayList<>();
	private int decks;

	public Deck() {
		decks = 1;
		this.reshuffle();
	}

	public Deck(int numDecks){
		decks = numDecks;
		this.reshuffle();
	}


	public void shuffle(){
		Collections.shuffle(cards);
	}

	public void reshuffle(){
		cards.clear();
		for(int i = 0; i < decks; i++){
			this.addDeck();
		}
		this.shuffle();
	}

	public Card deal(){
		return cards.remove(0);
	}

	public int size(){
		return cards.size();
	}

	private void addDeck(){
		for (Suit suit : Suit.values()) {
			for(Face face: Face.values()){
				cards.add(new Card(face, suit));
			}
		}
	}
}
