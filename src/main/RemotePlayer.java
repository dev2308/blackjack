package main;

import java.util.Scanner;

public class RemotePlayer extends Player{
	public void println(String s){
		System.out.println(s);
	}

	public void print(String s){
		System.out.print(s);
	}

	public String getInput(Scanner input){
		return "Hey don't use RemotePlayer";
	}
}
