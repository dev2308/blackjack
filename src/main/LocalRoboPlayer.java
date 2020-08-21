package main;

public class LocalRoboPlayer extends Player {
	public LocalRoboPlayer(String name) {
		super(name);
	}

	@Override
	public boolean wantsHit() {
		return hand().score() < 17;
	}
}
