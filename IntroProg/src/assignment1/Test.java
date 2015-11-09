package assignment1;

import java.util.ArrayList;

public class Test {
	
	ArrayList<String> al = new  ArrayList<String>();
	
	public static void main(String args[]) {
	
	Test obj = new Test();
	obj.run();
	
	}
	
	public void run() {
		String[] a = {"a", "b", "c", "3"};
		String[] b = {"a", "b", "c", "1"};
		
		al.add(a);
		al.add(b);
		
		foreach (String item in al) {
			
			
		}
		
	}
	
}
