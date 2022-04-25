package com.jack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.io.*;

public class CivFinder 
{
	
	List<String> civData;
	
	static Set<Integer> taken = new HashSet<>();
	static Set<String>  falseTerms = new HashSet<>();
	static { loadFalseTerms(); }
	List<String> players;
	int n;
	
	//Constructor
	CivFinder(List<String> players, int n) {
		super();
		this.players = players;
		this.n = (n > 10) ? 10 : n;
		civData = new ArrayList<String>();
	}
	
	
	/* Load hardcoded list of "false terms" that do not indicate a new civ */
	private static void loadFalseTerms() {
		
		falseTerms.add(":");
		falseTerms.add("Tier");
		falseTerms.add("Note");
		falseTerms.add("+");
		falseTerms.add("%");
		falseTerms.add("/");
		
		falseTerms.add("UA");
		falseTerms.add("UB");
		falseTerms.add("UI");
		falseTerms.add("UU");
		falseTerms.add("UU1");
		falseTerms.add("UU2");
	}
	
	public int initFile() {
		//Read into the file and write out simple terms for
		//future use
				final String inPath = "resources/civs.txt";
				File inFile = new File(inPath);
				
				
				
				final int buffSize = 1024;
				int count = 0;
				int prev = 1;
				
				//attempt to open the file
				try {
					BufferedReader br = new BufferedReader(new FileReader(inFile), buffSize);
					
					//read until we hit EOF
					int a = '\0';
					StringBuilder nextData = new StringBuilder();
					while((a = br.read()) != -1) 
					{
						String next = (char) a + br.readLine();
						boolean errors = false;
						for(String term : falseTerms) {
							if(next.contains(term)) {
								errors = true;
								break;
							}
						}
						
						 if (next.contains(" - ") && !errors) {
							 count++;
						 } 
						 
						 if (count > 0) 
						 {	 
							 if(prev < count) {
								 //System.out.println("Adding to civData: " + nextData.toString());
								 civData.add(nextData.toString());
								 nextData = new StringBuilder(next + "\n");
								 prev++;
							 } else {
								 nextData.append(next + "\n");
							 }
							 
						 }
						
					}
					//END WHILE
					civData.add(nextData.toString());
					
					//close resources
					br.close();
					
				} catch (FileNotFoundException e) {
					System.out.println("Error opening File: closing");
					e.printStackTrace();
					System.exit(1);
				} catch (IOException e) {
					System.out.println("Error reading from file: closing");
					e.printStackTrace();
					System.exit(2);
				} 
				
				System.out.println("Finished, exiting cleanly with count: " + count);
				System.out.println("\n\n\n");
				return count;

	}
	//END INIT FILE
	
	public void results() 
	{
		List<String> shuffled = new ArrayList<>(civData);
		Collections.shuffle(shuffled);
		int i=0;
		
		for(String p : players) 
		{
			List<String> retData = new ArrayList<>(shuffled.subList(i, i+n));
			i+=n;
			//Write results to file
			final String outPath = "resources/" + p + ".txt";
			File outFile = new File(outPath);
			
			try {
				PrintWriter pw = new PrintWriter( new BufferedWriter(new FileWriter(outFile)));
				
				for(String data : retData) {
					pw.print(data);
					pw.println("---------------------------------------------------------------\n");
				}
					
				pw.close();
			} catch (Exception e) {
				System.out.println("Error writing to file: closing");
				e.printStackTrace();
				System.exit(2);
			}
			//END TRY CATCH
			
		}
		//END FOR
		
		
		
	
	}
	
}
