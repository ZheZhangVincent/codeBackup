package homework2;


public class Student {
	String name = null;
	String address = null;
	String degreename = null;
	String department = null;
	String yearcommenced = null;
	private int studentID = 0;
	private static int nextID = 0;
	
	public static void main(String args[]){
		
	}
	
	//Constructor Method
	public Student(String name, String address, String degreename,String department, String yearcommenced){
		this.name = name;
		this.address = address;
		this.degreename = degreename;
		this.department = department;
		this.yearcommenced = yearcommenced;
		increment();
		
		String temp = yearcommenced.substring(2,4);
		int first_two = Integer.parseInt(temp);
		first_two = first_two * 100000;
		studentID = first_two + nextID;
		
		}
	
	//New Constructor Method
	public Student(String name){
		this.name = name;
		
	}
	
	public Student(String name, String address){
		this.name = name;
		this.address = address;
		
	}
	
	public Student(String name, String address,String degreename){
		this.name = name;
		this.address = address;
		this.degreename = degreename;

	}
	
	//Accessor Method
	public String getname(){
		return name;
		
	}
	public String getaddress(){
		return address;
		
	}
	public String getdegreename(){
		return degreename;
		
	}
	public String getdepartment(){
		return department;
		
	}
	public String getyearcommenced(){
		return yearcommenced;

	}
	
	public int getstudentID(){
		
		return studentID;
		
	}

	
	public String toString(){
		
		return getname() + ", whose studentID is " + getstudentID() + " at " + getdepartment() + " will graduate at " + getyearcommenced() + ".";
	}
	
	
	//private method

	private void increment(){
		
		nextID = nextID + 1;
		
	}
	
	
		
	}
	





	
	
	

