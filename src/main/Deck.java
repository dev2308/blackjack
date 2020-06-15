package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Deck {
	ArrayList<Card> cards = new ArrayList<>();

	public Deck() {
		for (Suit suit : Suit.values()) {
			for(Face face: Face.values()){
				cards.add(new Card(face, suit));
			}
		}
	}

	public void shuffle(){
		Collections.shuffle(cards);
	}

	public Card deal(){
		return cards.remove(0);
	}
}
