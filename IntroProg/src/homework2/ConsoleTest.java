package homework2;

import java.util.Scanner;

public class ConsoleTest {
   public static void main(String[] args) {
      Scanner sc = new Scanner(System.in);
      // the user is expected to type something and hit "return"	
      System.out.print("Enter some text: "); 
      String input = sc.nextLine();
      System.out.println("You entered " + input + 
      			", it has " + input.length() + " characters");
   }
}
