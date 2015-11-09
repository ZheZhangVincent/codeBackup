package Voting;

import java.io.*;
import java.util.*;

public class Copy_2_of_ElectionScrutinizer {
	
	public static void main(String args[]) {
		Copy_2_of_ElectionScrutinizer.menu();
	}
	
	public static List<List> readFile(String csvFile) {
		String line = "";
		List<List> dataLines = new ArrayList<List>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(csvFile));
			br.readLine();
			br.readLine();
				while ((line = br.readLine()) != null) {
					List temp = new ArrayList();
					StringTokenizer token = new StringTokenizer(line,",");
					while (token.hasMoreTokens()) {
						temp.add((String) (token.nextToken().replaceAll("\"", "")));
					}
					dataLines.add(temp);
					}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return dataLines;
	}
	
	public static void menu() {
		System.out.print("enter the command (press 'h' to see the menu option): ");
		String input = ((new Scanner(System.in)).next());
		if (input.equals("h")) {
			System.out.println("press 'h' to display this menu");
			System.out.println("press 'l' to list all division names");
			System.out.println("press 'a' to select a division name and analyse the data");
			System.out.println("press 'q' to quit the program");
		}
		
		if (input.equals("q")) {
			System.out.print("exiting...");
	        System.exit(1);
	        
		}
		if (input.equals("l")) {
			Properties properties = new Properties();
			try {
			  properties.load(new FileInputStream("names.properties"));
			  for(String key : properties.stringPropertyNames()) {
				  String[] value = properties.getProperty(key).split(",");
				  System.out.println(key);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (input.equals("a")) {
			Copy_2_of_ElectionScrutinizer.submenu();
		}
		else {
			System.out.print("Please entry command as hints, ");
		}
		Copy_2_of_ElectionScrutinizer.menu();
	}
	
	public static void submenu() {
		System.out.print("enter the division name ('b' to return main menu): ");
		String type = ((new Scanner(System.in)).next());
		
		if (type.equals("b")) {
			Copy_2_of_ElectionScrutinizer.menu();
		}
		else {
			Properties properties = new Properties();
			try {
				  properties.load(new FileInputStream("names.properties"));
				  
				  for(String key : properties.stringPropertyNames()) {
					  if (type.equals(key)) {
						  String[] csvfiles = properties.getProperty(key).split(",");
						  //PollingPlace.integrityTest(csvfiles);
					  }
					  else {
						  System.out.println("this division is not available, try again ");
						  Copy_2_of_ElectionScrutinizer.submenu();
					  }
				}
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
}
