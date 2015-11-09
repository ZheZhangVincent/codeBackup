
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

import java.util.*;

public class Payment {
	
	private double amtPaid;
	private Date datePaid;
	private int type;
	
	public Payment(double a, Date d, int type) {
		this.amtPaid = a;
		this.datePaid = d;
		this.type = type;
	}
	
	public double getAmount() {
		return amtPaid;
	}
	
	public Date getDatePaid() {
		return datePaid;
	}
	
	public int getType() {
		return type;
	}
	
	public void setAmount(double a) {
		this.amtPaid = a;
	}
	
	public void setDatePaid(Date d) {
		this.datePaid = d;
	}
	
	public void setType(int type) {
		this.type = type;
	}

}
