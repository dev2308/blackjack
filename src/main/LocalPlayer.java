package main;

import java.io.InputStream;
import java.util.Scanner;

public class LocalPlayer extends Player{
	public void println(String s){
		System.out.println(s);
	}

	public void print(String s){
		System.out.print(s);
	}

	public String getInput(Scanner input){
		return input.next();
	}
}
