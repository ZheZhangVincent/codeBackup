package homework2;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Percent {

	public static void main(String args[]){
		
		ArrayList<Integer> number_list = new ArrayList<Integer>();
		
		Scanner input = new Scanner(System.in);
		
		int number = 0;
		
		while (input.hasNextLine()) {
			try {
				number = input.nextInt();
				break;
			} catch (InputMismatchException ime) {
				System.out.print("Non-negative integers only, try again:");
				input.nextLine();
			}
			
			
		}
		
		if (number <= 0){
			
			System.out.println("Non-negative integers only, try again:");
			System.exit(1);
			
		}
			
		else{
			
			number_list.add(number);
		
		}
		
		
		//while (input.next.length() != 0);
		
		}
	
}
