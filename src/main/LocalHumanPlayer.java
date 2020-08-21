package main;

import java.util.Scanner;

public class LocalHumanPlayer extends Player {
	private static final Scanner scanner = new Scanner(System.in);

	public LocalHumanPlayer(String name) {
		super(name);
	}

	@Override
	public boolean wantsHit() {
		return prompt("Hit?").equalsIgnoreCase("y");
	}

	@Override
	public String prompt(String text) {
		println(text);
		return scanner.nextLine();
	}
}
