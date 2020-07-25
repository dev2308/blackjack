package main;

import java.io.InputStream;
import java.util.Scanner;

public class LocalPlayer extends Player{
	private static Scanner scan1 = new Scanner(System.in);

	public void println(String s){
		System.out.println(s);
	}

	public void print(String s){
		System.out.print(s);
	}

	public String getInput(){
		return scan1.next();
	}
}
