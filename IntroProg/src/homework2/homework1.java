package homework2;


public class homework1 {//Get the number of years spent to make amount to 20000
	public static void main(String[] args){
    	
		double amount = 10000;
    	double rate = 0.05;
    	int year = 0;
    	  	
    	while(amount < 20000){
    		amount = amount * (1 + rate);
    		year = year + 1;
    		
    	}
    	
    	amount = Math.round(amount);
    	amount = (int)amount;
    	
		System.out.println("You spent " + year + " years to save more than $20000");
		System.out.println("Now, you have $" + amount + " in your account.");
    	
    }
}
