
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

public class Privilege {
	
	private int maxQuota;
	private int currentQuota;
	private double rentFee;
	private double renewFee;
	private double replacementFee;
	private double lateFee;
	private double regFee;
	
	public Privilege(int m, int c, double rt, double rw, double rc, double l, double reg) {
		this.maxQuota = m;
		this.currentQuota = c;
		this.rentFee = rt;
		this.renewFee = rw;
		this.replacementFee = rc;
		this.lateFee = l;
		this.regFee = reg;
	}
	
	public int getMaxQuota() {
		return maxQuota;
	}
	
	public int getCurrentQuota() {
		return currentQuota;
	}
	
	public double getRentFee() {
		return rentFee;
	}
	
	public double getRenewFee() {
		return renewFee;
	}
	
	public double getReplacementFee() {
		return replacementFee;
	}
	
	public double getLateFee() {
		return lateFee;
	}
	
	public double getRegFee() {
		return regFee;
	}
	
	public void setMaxQuota(int m) {
		this.maxQuota = m;
	}
	
	public void setCurrentQuota(int c) {
		this.currentQuota = c;
	}
	
	public void setRentFee(double rt) {
		this.rentFee = rt;	
	}
	
	public void setRenewFee(double rw) {
		this.renewFee = rw;
	}
	
	public void setReplacementFee(double rc) {
		this.replacementFee = rc;
	}
	
	public void setLateFee(double lt) {
		this.lateFee = lt;
	}
	
	public void setRegFee(double reg) {
		this.regFee = reg;
	}

}
