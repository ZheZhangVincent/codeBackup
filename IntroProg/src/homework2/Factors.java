package homework2;

import java.util.ArrayList;


public class Factors {
	
	public static void main(String[] args){
		
		ArrayList<Integer> number_list = new ArrayList<Integer>(); //define the array which contains all numbers could divide "number".
		
		
		int number = 0;

		number = Integer.parseInt(args[0]);				

		int count = 2; //count is used for the iteration. 
		
		while(count <= number){

			if (number % count == 0){
				
				number_list.add(count);
				
				number = number / count;
				
			}
			
			else{
			
				count = count + 1;
			
			}
			
		}
		
		
		
		if (number_list.size() == 2){
			
			System.out.print("1 " + "* " + number_list.get(0) + " = " + number);
			System.out.println("The " + number_list.get(0) + " is a primer number.");
			
		}
		else{ 
			
			int n1 = 0;
			System.out.print("1");
			
			while( n1 < number_list.size() ){
				
				System.out.print(" * ");
				System.out.print(number_list.get(n1));
				n1 = n1 + 1;
			
				}
			
			int n2 = 0;
			while( n2 < number_list.size() ){
				number = number * number_list.get(n2);
				n2 = n2 + 1;
				
			}
			
			System.out.print(" = " + number);
		
		}
		
	}
	

}
	
	
	
	
	
	

