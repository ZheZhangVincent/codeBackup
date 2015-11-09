
public class Beer {
	
	private String brandName;
	private double strength;
	
	public Beer(String brandName, double strength) {
		this.brandName = brandName;
		this.strength = strength;
	}
	
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public double getStrength() {
		return strength;
	}
	public void setStrength(double strength) {
		this.strength = strength;
	}

}
