package main;

public class Card {

	private Face face;
	private Suit suit;
	public Card(Face face, Suit suit) {
		this.face = face;
		this.suit = suit;
	}

	public String toString(){
		return(face.toString() + suit.toString());
	}

	public int value(){
		return face.value();
	}

	public String face(){
		return face.toString();
	}
}
