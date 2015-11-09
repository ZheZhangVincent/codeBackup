import java.util.*;
import java.util.regex.Pattern;


public class Percent {
	
	public static List<Integer> datalist = new ArrayList<Integer>();
	
	public static void main(String[] args) {
		readdata();
	}
	
	public static void readdata() {
		Scanner scn = new Scanner(System.in);
		String input;
		while (!("").equals(input = scn.nextLine())) {
			if (postiveNum(input) && Integer.parseInt(input) > 0) {
				savedata(Integer.parseInt(input));
				}
			else {
				System.out.println("Non-negative integers only, try again:");
				}
		}
		printdata(datalist, gettotal(datalist));
		scn.close();
		}
	
	public static boolean postiveNum(String str) {
		Pattern pattern = Pattern.compile("[0-9]*"); 
	    return pattern.matcher(str).matches();     
	}
	
	public static List<Integer> savedata(Integer input_number) {
		datalist.add(input_number);
		return datalist;
	} 
	
	public static Integer gettotal(List<Integer> datalist) {
		int total_num = 0;
		for (int data : datalist) {
			total_num = total_num + data;
		}
		datalist.add(total_num);
		return total_num;
	}
	
	public static float getpercentage(Integer data, Integer total_num) {
		float percentage = (float) data/ (float) total_num * 100;
		return percentage;
	}
	
	public static void printdata(List<Integer> datalist, Integer total_num) {
		if (datalist.isEmpty()) {
			System.out.println("You should input some numbers to calculate percentage.");
			readdata();
		}
		else {
			System.out.println("The numbers and percentage:");
			for (int data : datalist) {
				System.out.printf("%4s %10.1f", data, getpercentage(data, total_num));
				System.out.println("%");
			}
		}
	}
}
