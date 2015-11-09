import java.util.*;
import java.util.Map.Entry;

public class BottleShop {
	
	private static List<Bottle> inventory = new ArrayList<Bottle>();
	private static Map<String, Bottle> inventory_name = new HashMap<String, Bottle>();
	private static Map<Double, Bottle> inventory_price = new HashMap<Double, Bottle>();
	private static Map<Double, Bottle> inventory_alcohol = new HashMap<Double, Bottle>();
	
	private static Scanner scn; 
	
	public static void main(String args[]) {
		
		//make some product into inventory
		Beer beer1 = new Beer("Tiger", 5.0);
		Bottle bottle1 = new Bottle(beer1, 330, Bottle.Colour.BROWN, 3.65, 24);
		inventory.add(bottle1);
		inventory_name.put("Tiger",bottle1);
		inventory_price.put(3.65, bottle1);
		inventory_alcohol.put(beer1.getStrength()*330, bottle1);
		Beer beer2 = new Beer("XXXX Gold", 3.5);
		Bottle bottle2 = new Bottle(beer2, 375, Bottle.Colour.BROWN, 2.65, 24);
		inventory.add(bottle1);
		inventory_name.put("XXXX Gold",bottle2);
		inventory_price.put(2.65, bottle2);
		inventory_alcohol.put(beer2.getStrength()*375, bottle2);
		Beer beer3 = new Beer("Victoria Bitter", 4.9);
		Bottle bottle3 = new Bottle(beer3, 375, Bottle.Colour.BROWN, 2.95, 24);
		inventory.add(bottle1);
		inventory_name.put("Victoria Bitter",bottle3);
		inventory_price.put(2.95, bottle3);
		inventory_alcohol.put(beer3.getStrength()*375, bottle3);
		
		scn = new Scanner(System.in);

		menu();
		
		scn.close();
	}
	
	public static void menu() {
		while (true) {
			
			System.out.println("Please select the function as guidence: ");
			System.out.println("(type 'manage' to manage inventory situation)");
			System.out.println("(type 'check' to check inventory situation)");			
			
			String input = scn.nextLine();
			
			if (input.equals("manage")) {
				managemenu();
			}
			if (input.equals("check")) {
				checkmenu();
			}			
		}
		
	}
	
	public static void managemenu() {
		System.out.println("Please select the function as guidence to manage inventory: ");
		System.out.println("(type 'in' to make new product into inventory)");
		System.out.println("(type 'out' to make exist prodcut leave inventory)");
		System.out.println("(type 'back' to go back to the main menu)");
		
		//Scanner scn = new Scanner(System.in);
		while (scn.hasNextLine()){
			String input = scn.nextLine();
			if (input.equals("in")) {
				in();
				break;
			}
			if (input.equals("out")) {
				out();
				break;
			}
			if (input.equals("back")) {
				menu();
				break;
			}
			else {
				managemenu();
				break;
			}
		}
		//scn.close();
	}
	
	public static void in() {
		//Scanner scn = new Scanner(System.in);
		System.out.println("Please type the brandname: ");
		String input1 = scn.nextLine();
		if ((inventory_name.keySet()).contains(input1)) {
			System.out.println("You alraedy have this name of bottle in the inventory, do you wan to add the quantity of this bottle with same situation? Y/N");
			String input = scn.nextLine();
			if (input.equals("Y")) {
				for (Bottle bottle : inventory) {
					if (input1.equals((bottle.getBeer()).getBrandName())) {
						System.out.println("Please type the number of bottles which would put into inventory: ");
						String input_num = scn.nextLine();

						bottle.setQuantity(bottle.getQuantity() + Integer.parseInt(input_num));
						sortbybrand();
						break;
					}
				}
			}
			if (input.equals("N")) {
				subin(input1);
			}
		}
		else {
			subin(input1);
		}
		//scn.close();
	}
	
	public static void subin(String brandName) {

		//Scanner scn = new Scanner(System.in);
		System.out.println("Please type the strength: ");
		String input2 = scn.nextLine();
		Beer beer = new Beer(brandName, Double.parseDouble(input2));
		System.out.println("Please type the volume: ");
		String input3 = scn.nextLine();
		System.out.println("Please type the glass colour: ");
		String input4 = scn.nextLine();
		Bottle.Colour input5 = toColour(input4);
		System.out.println("Please type the price: ");
		String input6 = scn.nextLine();
		System.out.println("Please type the quantity: ");
		String input7 = scn.nextLine();
		Bottle bottle = new Bottle(beer, Double.parseDouble(input3), input5, Double.parseDouble(input6), Integer.parseInt(input7));
		inventory.add(bottle);
		inventory_name.put(brandName, bottle);
		inventory_price.put(Double.parseDouble(input6), bottle);
		inventory_alcohol.put(Double.parseDouble(input3) * beer.getStrength(), bottle);
		sortbybrand();
		//scn.close();
	}
	
	public static void out() {
		//Scanner scn = new Scanner(System.in);
		System.out.println("Please type the brandname: ");
		String input1 = scn.nextLine();
		if ((inventory_name.keySet()).contains(input1)) {
			for (Bottle bottle : inventory) {
				if (input1.equals((bottle.getBeer()).getBrandName())) {
					System.out.println("Please type the number of bottles which would out of inventory: ");
					int input2 = scn.nextInt();
					if (input2 > bottle.getQuantity()) {
						System.out.println("Sorry, we only have " + bottle.getQuantity() + " bottles in the inventory.");
					}
					else {
						bottle.setQuantity(bottle.getQuantity() - input2);
						break;
					}
				}
			}
		}
		else {
			System.out.println("Sorry, please type a kinds of bottle which has already in the inventory.");
		}
		sortbybrand();
		//scn.close();
	}
	
	public static void checkmenu() {
		
		//Scanner scn = new Scanner(System.in);
		System.out.println("Please type the attributes which used to sort: ");
		System.out.println("type 'name' to sort inventory by brandname: ");
		System.out.println("type 'price' to sort inventory by prince: ");
		System.out.println("type 'alcohol' to sort inventory by alcoholcontent: ");
		System.out.println("(type 'back' to go back to the main menu)");
		
		String input = scn.nextLine();
		if (input.equals("name")) {
			sortbybrand();
		}
		if (input.equals("price")) {
			sortbyprice();
		}
		if (input.equals("alcohol")) {
			sortbyalcohl();
		}
		if (input.equals("back")) {
			menu();
		}
		else {
			checkmenu();
		}
		//scn.close();
	}
	
	public static void sortbybrand() {
		
		List<String> sortedKeys = new ArrayList(inventory_name.keySet());
		Collections.sort(sortedKeys);
		
		System.out.println("The inventory situation of the bottle shop as follwoing:");
		System.out.printf("%20s %20s %10s %10s %20s", "BrandName", "AlcoholContent", "GlassColour", "Price", "Quantity");
		for (String brandname : sortedKeys) {
			for (Entry<String, Bottle> e: inventory_name.entrySet()) {
				if (brandname.equals(e.getKey())) {
					printout(e.getValue());
				}
			}
		}
		System.out.println();
	}
	
	public static void sortbyprice() {
		List<Double> sortedKeys = new ArrayList(inventory_price.keySet());
		Collections.sort(sortedKeys);

		System.out.println("The inventory situation of the bottle shop as follwoing:");
		System.out.printf("%20s %20s %10s %10s %20s", "BrandName", "AlcoholContent", "GlassColour", "Price", "Quantity");
		for (Double price : sortedKeys) {
			for (Entry<Double, Bottle> e: inventory_price.entrySet()) {
				if (price.equals(e.getKey())) {
					printout(e.getValue());
				}
			}
		}
		System.out.println();
	}
	
	public static void sortbyalcohl() {
		List<Double> sortedKeys = new ArrayList(inventory_alcohol.keySet());
		Collections.sort(sortedKeys);
		
		System.out.println("The inventory situation of the bottle shop as follwoing:");
		System.out.printf("%20s %20s %10s %10s %20s", "BrandName", "AlcoholContent", "GlassColour", "Price", "Quantity");
		for (Double alchol : sortedKeys) {
			for (Entry<Double, Bottle> e: inventory_alcohol.entrySet()) {
				if (alchol.equals(e.getKey())) {
					printout(e.getValue());
				}
			}
		}
		System.out.println();
	}
	
	public static void printout(Bottle bottle) {
		System.out.printf("\n%20s %20.1f %10s %10.2f %20d", bottle.getBeer().getBrandName(), bottle.getAlcoholContent(), bottle.getColour(), bottle.getPrice(), bottle.getQuantity());
	}
	
	public static Bottle.Colour toColour(String input) {
		Bottle.Colour output = null;
		if (input.equals("blue")) {
			output = Bottle.Colour.BLUE;
		}
		if (input.equals("brown")) {
			output = Bottle.Colour.BROWN;
		}
		if (input.equals("black")) {
			output = Bottle.Colour.BLACK;
		}
		
		return output;
	}
	
	public static String toStr(Bottle.Colour input) {
		String output = null;
		if (input.equals(Bottle.Colour.BLUE)) {
			output = "blue";
		}
		if (input.equals(Bottle.Colour.BROWN)) {
			output = "brown";
		}
		if (input.equals(Bottle.Colour.BLACK)) {
			output = "black";
		}
		else {
			System.out.println(Bottle.Colour.CLEAN);
			output = "clean";
		}
		return output;
	}
	
}
