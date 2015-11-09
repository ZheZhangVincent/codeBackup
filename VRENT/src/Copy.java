
/*
 * Writer:
 * Version: 0.1
 * Time: 20/10/2015
 * 
 * */

/*
 * Writer:
 * Version: 0.2
 * Time: 21/10/2015
 * 
 * */

public class Copy {
	
	private String format;
	private char status;
	private double cost;
	
	public Copy(String f, char s, double c) {
		this.format = f;
		this.status = s;
		this.cost = c;
	}
	
	public String getFormat() {
		return format;
	}
	
	public char getStatus() {
		return status;
	}
	
	public double getCost() {
		return cost;
	}
	
	public void setFormat(String f) {
		this.format = f;
	}
	
	public void setStatus(char s) {
		this.status = s;
	}
	
	public void setCost(double c) {
		this.cost = c;
	}

}
