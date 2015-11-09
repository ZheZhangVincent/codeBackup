package homework2;

import java.util.Scanner;

public class DanglingElses {
	
	public static void main( String[] args ) {
		Scanner sc = new Scanner(System.in);
		System.out.print("What was the seismograph reading? ");
		int richter = Integer.parseInt(sc.next());
		
		//this is wrong 
		if (richter >= 0) 
			if (richter <= 4) 
			System.out.println("The earthquake is harmless"); 
		else // pitfall! 
			System.out.println("Negative value not allowed");
			
		// use curly braces to group statements and avoid 
		// "dangling else" mistake
		/*if (richter >= 0) {
			if (richter <= 4) 
				System.out.println("The earthquake is harmless"); 
			}
		else // misaligned but not a mistake
			System.out.println("Negative value not allowed");*/ 
	}
}
