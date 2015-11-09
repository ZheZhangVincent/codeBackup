package homework2;

import java.util.Scanner;

public class TestScanner {
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter some text: ");
		String input = sc.next();
		System.out.println("You entered" + input + 
				", it has" + input.length() + "characters");
		sc.close();
		
	}
	
	
}
