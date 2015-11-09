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

public class Rental {
	
	private Date dateRented;
	private Date dateToReturn;
	
	public Rental(Date dateRented, Date dateToReturn) {
		this.dateRented = dateRented;
		this.dateToReturn = dateToReturn;
	}
	
	public Date getDateRented() {
		return dateRented;
	}
	
	public Date getDateToReturn() {
		return dateToReturn;
	}
	
	public void setDateRented(Date dateRented) {
		this.dateRented = dateRented;
	}
	
	public void setDateToReturn(Date dateToReturn) {
		this.dateToReturn = dateToReturn;
	}

}
