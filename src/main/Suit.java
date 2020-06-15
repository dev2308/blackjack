package main;

public enum Suit {
		SPADES("♠"),
		HEARTS("♥"),
		DIAMONDS("♦"),
		CLUBS("♣");

		Suit(String name) {
			this.name = name;
		}

		public String toString() {
			return name;
		}

		private String name;
}
