package com.jack;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	
	public static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		
		//Main method will run civ builder class
		int numCivs = Integer.parseInt(args[0]);
		List<String> players = new ArrayList<>();
		for (int i=1; i<args.length; i++) {
			players.add(args[i]);
		}
		
		CivFinder cv = new CivFinder(players, numCivs);
		cv.initFile();
		cv.results();	
	}
	//END MAIN

}
//END CLASS