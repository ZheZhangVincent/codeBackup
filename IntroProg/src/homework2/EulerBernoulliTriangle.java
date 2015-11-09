package homework2;

import java.text.DecimalFormat;

public class EulerBernoulliTriangle {
	
	private int height;
	private double[][] elems;
	
	public static void main(String[] args) {
		assert args.length > 0;
		int i = 0;
		try { 
			i = Integer.parseInt(args[0]);
		} catch (NumberFormatException nfe) {
			System.out.println(nfe.toString() + " must be odd integer");
		} finally {
			if (i%2 != 1 || i == 1) {
				System.out.println("Enter an odd number greater than 2 only");
				System.exit(1);
			}
			System.out.println("The height of the triangle is " + i);
		}
		
		EulerBernoulliTriangle triangle = new EulerBernoulliTriangle(i);
	}
	
	public EulerBernoulliTriangle(int h) {
		this.height = h;
		initialize();
		//printTriangle();
		System.out.println("The Euler numbers:\n" + getEulerNumbers());

	}
	
	private void initialize() {
		elems = new double[height][];
		elems[0] = new double[1];
		elems[0][0] = 1;
		int lodd, leven;
		for (int i = 1; i < height; i+=2) {
			lodd = i+1;
			leven = i+2;
			elems[i] = new double[lodd];
			elems[i+1] = new double[leven];
			elems[i][0] = 0;
			elems[i+1][leven - 1] = 0;
			for (int j = 1; j < lodd; j++) {
				elems[i][j] = elems[i-1][j-1] + elems[i][j-1];
			}
			for (int j = leven - 2; j >= 0; j--) {
				elems[i+1][j] = elems[i+1][j+1] + elems[i][j];
			}
		}		
	}
	
	public void printTriangle() {
		for (int i = 0; i < elems.length; i++) {
			for (int j = 0; j < elems[i].length; j++)
				System.out.print(elems[i][j] + " ");
			System.out.println();
		}
	}
	
	public String[] euler() { 
		String[] left = new String[(height-1) / 2];
		DecimalFormat format = new DecimalFormat();
			for (int i = 0; i<left.length; i++)
			left[i] =  String.valueOf(format.format(elems[2*(i+1)][0]));
			
		return left;
	}
	
	public String getEulerNumbers() {
		String s = "";
		for(String eul : euler())			
			s += eul + "\n";
		return s + "That\'s " + (euler().length)+ " Euler numbers\n" ;
	}
	
	
	
	
}