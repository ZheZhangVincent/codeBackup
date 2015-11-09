package Voting.copy;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class ElectionScrutinizer {
	
	String ppfile;
	String tffile;
	String csvFile = "./HouseDopByPPDownload-17496/HouseDopByPPDownload-17496-NSW-EMON.csv";
	
	public static void main(String args[]) {
		ElectionScrutinizer es = new ElectionScrutinizer();
		es.menu();
	}
	
	public static List<String> readFile(String csvFile) {
		String line = "";
		List<String> dataLines = new ArrayList<String>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(csvFile));
			br.readLine();
			br.readLine();
				while ((line = br.readLine()) != null) {
					dataLines.add(line);
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
		System.out.print("enter the command (press 'h' to see the menu option):");
		String input = ((new Scanner(System.in)).next());
		if (input.equals("h")) {
			System.out.println("press 'h' to display this menu");
			System.out.println("press 'l' to list all division names");
			System.out.println("press 'a' to select a division name and analyse the data");
			System.out.println("press 'q' to quit the program");
			System.out.print("enter the command (press 'h' to see the menu option):");
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
			System.out.print("enter the command (press 'h' to see the menu option):");
		}
		if (input.equals("a")) {
			Properties properties = new Properties();
			try {
			  properties.load(new FileInputStream("names.properties"));
			  
			  for(String key : properties.stringPropertyNames()) {
				  String[] value = properties.getProperty(key).split(",");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.print("enter the division name ('b' to return main menu): ");
			
			String type = ((new Scanner(System.in)).next());
				try {
				  properties.load(new FileInputStream("names.properties"));
				  
				  for(String key : properties.stringPropertyNames()) {
					  if (type.equals(key)) {
						  String[] value = properties.getProperty(key).split(",");
						  ElectionScrutinizer ee = new ElectionScrutinizer();
						  ee.ppfile = value[0];
						  ee.tffile = value[1];
					  }
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
		
	}
	}
	
}
