package homework2;

import java.util.Scanner;
import java.util.InputMismatchException;
//import static javax.swing.JOptionPane.showInputDialog;

/** written by
 * @author abx
 * in a haste
 * @version first and last
 */

public class ScannerExample {

	public static void main(String[] args) {
		
		//ArrayList<String> input = new ArrayedList<String>();
		String[] input;
		
		String word;
		Scanner s = new Scanner(System.in);
		
		System.out.print("For how long are you prepared to go? Give a number: ");
		int length = 0;
		while (s.hasNextLine()) {
			try {
				length = s.nextInt();
				break;
			} catch (InputMismatchException ime) {
				System.out.print("Enter integres only: ");
				s.nextLine();
			}
		}
		//int length = Integer.parseInt(showInputDialog("How many words?"));
		if (length <= 0) {
			System.out.println("Non-positive number means no input, good bye.");
			System.exit(1);
		}
 		
		input = new String[length];
		
		System.out.print("> ");
		int i = 0;
		while (i < length &&  s.hasNext()) {
			System.out.print("> ");
			input[i] = s.next(); 
			i++;
		}
		
	    	System.out.println("This is what you have entered in the reversed order:");
		
		/* for (int elem : input)
		  	System.out.println(elem); */
		for (i = length - 1; i >= 0; i--) 
			System.out.println(input[i].toUpperCase());
	}

}
