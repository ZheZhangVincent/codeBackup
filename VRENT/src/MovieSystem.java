import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


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

/**
 * Main file which contains main menu display and control.
 * 
 * @author 
 *
 */
public class MovieSystem {
	
	static List<Movie> movies = new ArrayList<Movie>();
	static List<Basic> basicMembers = new ArrayList<Basic>();
	static List<Premium> premiumMembers = new ArrayList<Premium>();
	static List<Payment> payments = new ArrayList<Payment>();
	static Map<String, Rental> rentals = new HashMap<String, Rental>();
	static Map<String, Map<Movie, Rental>> memberRentals = new HashMap<String, Map<Movie, Rental>>();
	static Map<String, Map<String, List<Copy>>> stocks = new HashMap<String, Map<String, List<Copy>>>();
	static Map<Rental, Copy> rentedCopys = new HashMap<Rental, Copy>();
	
	static Map<String, Integer> existMembers = new HashMap<String, Integer>();
	
	/**
	 * This method used to display all main menu information, and let
	 * user to select option.
	 * 
	 */
	public static void getMainMenu() {
		
		System.out.println("\n>>Main Menu<<");
		System.out.println("1.Stock Control");
		System.out.println("2.Membership Record");
		System.out.println("3.Rental");
		System.out.println("4.Return Movie");
		System.out.println("5.Search Moive");
		System.out.println("0.Exit\n");
		System.out.println();
		System.out.print("Enter Option: ");
		
		Scanner reader = new Scanner(System.in); 
		int enterOption = reader.nextInt();
		getSubMenu(reader, enterOption);
		
	}
	
	/**
	 * This method used to go to sub menu of main menu
	 * 
	 * @param enterOption
	 */
	public static void getSubMenu(Scanner reader, int enterOption) {
		
		
		switch(enterOption) {
		
		case 1: 
			controlStock(reader);
			
		case 2:
			membershipRecord();
		
		case 3:
			rentMovie();
			
		case 4:
			returnMovie();
			
		case 5:
			searchMovie();
			
		case 0: 
			System.exit(0);
		
		}
	}
	
	public static void searchMovie() {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n>>Search Movie<<\n");
		
		System.out.println("Search crieria \n\n 1. Title \n 2. Year \n 3. Director \n 4. Rating \n 5. Genre \n 0. Main Menu \n");
		System.out.print("Enter option: ");
		
		int enterOption = sc.nextInt();
		
		switch(enterOption) {
		
		case 1:
			searchByTitle();
		
		case 2:
			searchByYear();
			
		case 3:
			searchByDirector();
			
		case 4:
			searchByRating();
			
		case 5:
			searchByGenre();
			
		case 0:
			getMainMenu();
		}
		
	}
	
	/**
	 * This method used to implement the search function by movie genre
	 * 
	 */
	public static void searchByGenre() {
		
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter rating to be searched: ");
		String movieGenre = sc.nextLine();
		boolean flag = false;
		
		for (int i = 0; i < movies.size(); i++) {
			if (movies.get(i).getGenre().equals(movieGenre)) {
				flag = true;
				Movie movie = movies.get(i);
				System.out.format("ID: %d, Title: %s, Year: %d, Director: %s, Rating: %s, Genre: %s\n", 
						i + 1, movie.getTitle(), movie.getYear(), movie.getDirector(), movie.getRating(), movie.getGenre());
			}
		}
		if (! flag) {
			System.out.println("There is no Movie: " + movieGenre + "in this store.");
		}
		
		getEnterToReturn();
		
	}
	
	/**
	 * This method used to implement the search function by movie rating
	 * 
	 */
	public static void searchByRating() {
		
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter rating to be searched: ");
		String movieRating = sc.nextLine();
		boolean flag = false;
		
		for (int i = 0; i < movies.size(); i++) {
			if (movies.get(i).getRating().equals(movieRating)) {
				flag = true;
				Movie movie = movies.get(i);
				System.out.format("ID: %d, Title: %s, Year: %d, Director: %s, Rating: %s, Genre: %s\n", 
						i + 1, movie.getTitle(), movie.getYear(), movie.getDirector(), movie.getRating(), movie.getGenre());
			}
		}
		if (! flag) {
			System.out.println("There is no Movie: " + movieRating + "in this store.");
		}
		
		getEnterToReturn();
		
	}
	
	/**
	 * This method used to implement the search function by movie director
	 * 
	 */
	public static void searchByDirector() {
		
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter director to be searched: ");
		String movieDirector = sc.nextLine();
		boolean flag = false;
		
		for (int i = 0; i < movies.size(); i++) {
			if (movies.get(i).getDirector().equals(movieDirector)) {
				flag = true;
				Movie movie = movies.get(i);
				System.out.format("ID: %d, Title: %s, Year: %d, Director: %s, Rating: %s, Genre: %s\n", 
						i + 1, movie.getTitle(), movie.getYear(), movie.getDirector(), movie.getRating(), movie.getGenre());
			}
		}
		if (! flag) {
			System.out.println("There is no Movie: " + movieDirector + "in this store.");
		}
		
		getEnterToReturn();
		
	}
	
	/**
	 * This method used to implement the search function by movie year
	 * 
	 */
	public static void searchByYear() {
		
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter year to be searched: ");
		String movieYearString = sc.nextLine();
		int moiveYear = Integer.valueOf(movieYearString);
		boolean flag = false;
		
		for (int i = 0; i < movies.size(); i++) {
			if (movies.get(i).getYear() == moiveYear) {
				flag = true;
				Movie movie = movies.get(i);
				System.out.format("ID: %d, Title: %s, Year: %d, Director: %s, Rating: %s, Genre: %s\n", 
						i + 1, movie.getTitle(), movie.getYear(), movie.getDirector(), movie.getRating(), movie.getGenre());
			}
		}
		if (! flag) {
			System.out.println("There is no Movie: " + movieYearString + "in this store.");
		}
		
		getEnterToReturn();
		
	}
	
	/**
	 * This method used to implement the search function by movie title
	 * 
	 */
	public static void searchByTitle() {
		
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter title to be searched: ");
		String movieTitle = sc.nextLine();
		boolean flag = false;
		
		for (int i = 0; i < movies.size(); i++) {
			if (movies.get(i).getTitle().equals(movieTitle)) {
				flag = true;
				Movie movie = movies.get(i);
				System.out.format("ID: %d, Title: %s, Year: %d, Director: %s, Rating: %s, Genre: %s\n", 
						i + 1, movie.getTitle(), movie.getYear(), movie.getDirector(), movie.getRating(), movie.getGenre());
			}
		}
		if (! flag) {
			System.out.println("There is no Movie: " + movieTitle + "in this store.");
		}
		
		getEnterToReturn();
		
	}
	
	/**
	 * This method used to return movie to stock, and compute the incurred fee. 
	 * 
	 */
	public static void returnMovie() {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n>>Return Movie<<\n");
		System.out.print("Enter Member Number: ");
		String memberNumberString = sc.nextLine();
		int memberNumber = Integer.valueOf(memberNumberString);
		System.out.print("Enter movie title to return: ");
		String movieTitle = sc.nextLine();
		
		System.out.print("Enter movie format [VCD/DVD]: ");
		String movieFormat = sc.nextLine();
		
		System.out.println("\nConfirm?\n 1. Yes, 2. No, return to Main Menu");
		int enterOption = sc.nextInt();
		
		if (enterOption == 2) {
			getMainMenu();
		}
		
		// add this movie copy into stocks
		for (Movie movie : movies) {
			if (movie.getTitle().equals(movieTitle)) {
				String key = movie.getTitle() + Integer.toString(movie.getYear()) 
						+  movie.getDirector() + movie.getRating() + movie.getGenre();
				Map<String, List<Copy>> movieStock = stocks.get(key);
				List<Copy> copyStocks = movieStock.get(movieFormat);
				
				for (int i = 0; i < copyStocks.size(); i++) {
					if (copyStocks.get(i).getStatus() == 'b') {
						copyStocks.get(i).setStatus('a');
						break; // if find one available copy, put it back to stack
					}
				}
			}
		}
		
		// then, delete item from this member account
		for (Movie movie : memberRentals.get(memberNumberString).keySet()) {
			if (movie.getTitle().equals(movieTitle)) {
				memberRentals.get(memberNumberString).remove(movie);
			}
		}
		
		double incurredFee = getIncurredPay(movieTitle, memberNumberString, memberNumber, movieFormat);
		
		System.out.format("\n\nFines incurred: %.1f \n\n", incurredFee);
		
		System.out.println("Saving Performated");
		
		getEnterToReturn();
		
	}
	
	/**
	 * This method used to compute the incurred fee.
	 * 
	 * @param movieTitle
	 * @param memberNumberString
	 * @param memberNumber
	 * @param movieFormat
	 * @return incurredFee
	 */
	public static double getIncurredPay(String movieTitle, String memberNumberString, int memberNumber, String movieFormat) {
		
		String key = memberNumberString + movieTitle + movieFormat;
		double incurredFee = 0.0;
		
		// then, we find the rental information based on given input information
		Rental rental = rentals.get(key);
		
		// use this member no to get member type
		String memberType = checkMembers(memberNumber, movieFormat, false);
		
		Basic basicMember = null;
		Premium premiumMember = null;
		
		if (memberType.equals("Basic")) {
			
			for (Basic member : basicMembers) {
				if (member.getMemberNo() == memberNumber) {
					basicMember = member;
				}
			}
		} else if (memberType.equals("Premium")) {
			for (Premium member : premiumMembers) {
				if (member.getMemberNo() == memberNumber) {
					premiumMember = member;
				}
			}
		}
		
		Date currentDate = new Date();
		
		if (currentDate.before(rental.getDateToReturn())) { // if customer return on time
			
			return incurredFee;
					
		} else { // if delay, then perform punishment based on delay period
			if (currentDate.getTime() - rental.getDateToReturn().getTime() < (4 * 1000 * 60 * 60 * 24)) {
				// delay four days
				int delayDays = (int) ((currentDate.getTime() - rental.getDateToReturn().getTime()) / (1000 * 60 * 60 * 24));
				if (memberType.equals("Basic")) {
					if (movieFormat.equals("DVD")) {
						incurredFee = incurredFee + basicMember.getRenewFeeDVD() * delayDays;
					} else {
						incurredFee = incurredFee + basicMember.getRenewFeeVCD() * delayDays;
					}
				} else {
					if (movieFormat.equals("DVD")) {
						incurredFee = incurredFee + premiumMember.getRenewFeeDVD() * delayDays;
					} else {
						incurredFee = incurredFee + premiumMember.getRenewFeeVCD() * delayDays;
					}
				}
				
			} else if (currentDate.getTime() - rental.getDateToReturn().getTime() > (4 * 1000 * 60 * 60 * 24)) {
				if (memberType.equals("Basic")) {
					if (movieFormat.equals("DVD")) {
						incurredFee = incurredFee + basicMember.getRenewFeeDVD() * 4 + basicMember.getLateFee();
					} else {
						incurredFee = incurredFee + basicMember.getRenewFeeVCD() * 4 + basicMember.getLateFee();
					}
				} else {
					if (movieFormat.equals("DVD")) {
						incurredFee = incurredFee + premiumMember.getRenewFeeDVD() * 4 + premiumMember.getLateFee();
					} else {
						incurredFee = incurredFee + premiumMember.getRenewFeeVCD() * 4 + premiumMember.getLateFee();
					}
				}
			}
		}
		
		return incurredFee;
		
	}
	
	/**
	 * This method used to rent a movie from stock, which includes check member number, check copy stocks and delete
	 * it copy from stock.
	 * 
	 */
	public static void rentMovie() {
		
		double amountPayment = 0;
		Copy copy = null;
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n>>Movie Rental<<\n");
		System.out.print("Enter Member Number: ");
		String memberNumberString = sc.nextLine();
		int memberNumber = Integer.valueOf(memberNumberString);
		
		System.out.print("Enter Movie Title: ");
		String movieTitle = sc.nextLine();
		
		System.out.print("Enter Movie Format: ");
		String movieFormat = sc.nextLine();
		
		// first of all, check this member exist, and have right to borrow this copy
		String memberType = checkMembers(memberNumber, movieFormat, true);
		
		if (! checkMovieTitle(movieTitle)) {
			System.out.println("There is not this movie");
			getEnterToReturn();
		} else {
			copy = checkStock(movieTitle, movieFormat);
		}
		
		System.out.println("\nSaving Performed");
		System.out.println("Continue to rent more movies?\n 1. Yes 2. No\n");
		System.out.print("Enter option: ");
		int enterOption = sc.nextInt();
		
		if (enterOption == 1) {
			rentMovie();
		}
		
		System.out.println("\n<<<Printing rented itmes>>>\n");
		
		if (memberType.equals("Basic")) {
			if (movieFormat.equals("DVD")) {
				amountPayment = amountPayment + 5;
			} else {
				amountPayment = amountPayment + 2.5;
			}
		} else {
			if (movieFormat.equals("DVD")) {
				amountPayment = amountPayment + 3;
			} else {
				amountPayment = amountPayment + 1;
			}
		}
		
		System.out.format("Amount Payment: $%.2f\n", amountPayment);
		Movie borrowMovie = null;
		int i = 0;
		while (i < movies.size()) {
			if (movies.get(i).getTitle().equals(movieTitle)) {
				borrowMovie = movies.get(i);
				break;
			}
			i++;
		}
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		Date borrowDate = new Date(); // save the borrow date, and add four as return date
		Date returnDate = new Date(borrowDate.getTime() + (4 * 1000 * 60 * 60 * 24));
		System.out.print("Date Rented: " + dateFormat.format(borrowDate) 
				+ ", Date to Return: " + dateFormat.format(returnDate));
		System.out.format(", Moive ID: %d, Title: %s, Year: %d, Director: %s, Rating: %s, Genre: %s\n", 
				i + 1, movieTitle, borrowMovie.getYear(), borrowMovie.getDirector(), borrowMovie.getRating(), borrowMovie.getGenre());
		
		// then ,add this rent to rentals with member number, movie name, and format
		String key = memberNumberString + movieTitle + movieFormat;
		Rental rental = new Rental(borrowDate, returnDate);
		rentals.put(key, rental); // consider of there is no one will borrow same movie with same format more than once
		
		// add this rental link to this member with movie and rental information
		Movie searchMoive = null;
				
		for (Movie moive : movies) {
			if (moive.getTitle().equals(movieTitle)) {
				searchMoive = moive;
			}
		}
		
		Map<Movie, Rental> tempMap = new HashMap<Movie, Rental>();

		tempMap.put(searchMoive, rental);
		memberRentals.put(memberNumberString, tempMap);
		
		if (copy != null) {
			rentedCopys.put(rental, copy);
		}
		
		System.out.println("Saving Performed");
		getEnterToReturn();

	}
	
	/**
	 * This method used to check the given member number is exits in the 
	 * members list, and reduce one available borrow items, and return
	 * member type.
	 * 
	 * @param memberNumber
	 * @param format
	 * @param controlFlag
	 * @return memberType
	 */
	public static String checkMembers(int memberNumber, String format, boolean controlFlag) {
		
		boolean flag = false;
		String memberType = null;
		
		for (Basic basicMmeber : basicMembers) {
			if (basicMmeber.getMemberNo() == memberNumber) {
				
				if (controlFlag) {
					checkBasicMemberAvailableItem(basicMmeber, format);
				}
				
				flag = true;
				memberType = "Basic";
				break;
			}
		}
		
		if (! flag) {
			
			for (Premium premiumMember : premiumMembers) {
				if (premiumMember.getMemberNo() == memberNumber) {
					
					if (controlFlag) {
						checkPremiumMemberAvailableItem(premiumMember, format);
					}
					
					flag = true;
					memberType = "Premium";
					break;
				}
			}
		}
		
		if (! flag) {
			System.out.println("There is not given member number");
			getEnterToReturn();
		} 
		
		return memberType;
		
	}
	
	/**
	 * This method used to check the given member have the available capability to 
	 * borrow this format of copy.
	 * 
	 * @param member
	 * @param format
	 */
	public static void checkBasicMemberAvailableItem(Basic member, String format) {
		
		// if this member still have capability to borrow one DVD
		if (format.equals("DVD")) {
			if (member.getMaxDVD() > 0) {
				member.setMaxDVD(member.getMaxDVD() - 1);
			} else {
				System.out.println("Sorry, this member had already borrow one DVD");
				getEnterToReturn();
			} 
		}
		if (format.equals("VCD")) {
			if (member.getMaxVCD() > 0) {
				member.setMaxVCD(member.getMaxVCD() - 1);
			} else {
				System.out.println("Sorry, this member had already borrow two VCDs");
				getEnterToReturn();
			}
		}

	}
	
	/**
	 * This method used to check the given member have the available capability to 
	 * borrow this format of copy.
	 * 
	 * @param member
	 * @param format
	 */
	public static void checkPremiumMemberAvailableItem(Premium member, String format) {
		
		// if this member still have capability to borrow one DVD
		if (format.equals("DVD")) {
			if (member.getMaxDVD() > 0) {
				member.setMaxDVD(member.getMaxDVD() - 1);
			} else {
				System.out.println("Sorry, this member had already borrow two DVDs");
				getEnterToReturn();
			} 
		}
		if (format.equals("VCD")) {
			if (member.getMaxVCD() > 0) {
				member.setMaxVCD(member.getMaxVCD() - 1);
			} else {
				System.out.println("Sorry, this member had already borrow four VCDs");
				getEnterToReturn();
			}
		}

	}
	
	/**
	 * This method used to check the copy number of given movie name, and if
	 * there is not exist this kind of movie, or enough copy, it will go back
	 * to main menu.
	 * 
	 * @param movieTitle
	 * @param format
	 * @return rentedCopy 
	 */
	public static Copy checkStock(String movieTitle, String format) {
		
		Copy rentedCopy = null;
		
		for (Movie movie : movies) {
			if (movie.getTitle().equals(movieTitle)) {
				String key = movie.getTitle() + Integer.toString(movie.getYear()) 
						+  movie.getDirector() + movie.getRating() + movie.getGenre();
				Map<String, List<Copy>> movieStock = stocks.get(key);
				
				if (! movieStock.containsKey(format)) {
					System.out.println("There is not such item in our store");
					getEnterToReturn();
				}
				
				List<Copy> copyStocks = movieStock.get(format);

				
				for (int i = 0; i < copyStocks.size(); i++) {
					if (copyStocks.get(i).getStatus() == 'a') {
						copyStocks.get(i).setStatus('b');
						rentedCopy = copyStocks.get(i);
						break; // if find one available copy, borrow it out
					}
					if (i == copyStocks.size() - 1) {
						System.out.println("There is not avaiable enough copy of this movie");
						getEnterToReturn();
					}
				}
			}
		}
		
		return rentedCopy;
		
	}
	
	/**
	 * This method used to query member information on which movie and copy the member borrow
	 * 
	 */
	public static void queryMember() {
		
		Scanner sc = new Scanner(System.in);
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		System.out.println("\n>>Query Member<<\n");
		
		System.out.print("Enter Member Number: ");
		String memberNumberString = sc.nextLine();
		
		if (memberRentals.containsKey(memberNumberString)) {
			
			if (memberRentals.get(memberNumberString).size() == 0) {
				System.out.println("This member does not rent any items from store.\n");
				getEnterToReturn();
			}
			
			Map<Movie, Rental> tempMap = memberRentals.get(memberNumberString);
			
			System.out.println("\nMovie Details: ");
			
			int i = 0;
			
			// first of all, display movie information
			for (Movie movie : tempMap.keySet()) {
				i++;
				
				Rental rental = tempMap.get(movie);
					
				System.out.println("Date Rented: " + dateFormat.format(rental.getDateRented()) + 
						", Date to Return: " + dateFormat.format(rental.getDateToReturn()) + " Movie ID: " 
						+ String.valueOf(i) + ", Title: " + movie.getTitle() + ", Year: " + movie.getDirector() 
						+ ", Rating: " + movie.getRating() + ", Genre: " + movie.getGenre());
					
				// then, display copy information
				Copy copy = rentedCopys.get(rental);
					
				System.out.println("\nCopy Details: ");
					
				System.out.println("Copy ID: " + String.valueOf(i) + ", Cost: " + copy.getCost() + ", Format: " 
				+ copy.getFormat() + " , Date Created: " +  dateFormat.format(rental.getDateRented()));

			}
			
		} else {
			System.out.println("This member does not rent any items from store.\n");
		}
		
		getEnterToReturn();
		
	}
	
	/**
	 * This method used to check the movie exists in movie list.
	 * 
	 * @param movieTitle
	 * @return returnFlag
	 */
	public static boolean checkMovieTitle(String movieTitle) {
		
		boolean returnFlag = false;
		
		for (Movie movie : movies) {
			if (movie.getTitle().equals(movieTitle)) {
				returnFlag = true;
			}
		}
		return returnFlag;
	}
	
	/**
	 * This method used to ask user to type a enter to return main menu.
	 * 
	 */
	public static void getEnterToReturn() {
		System.out.println("Hit Enter to return to Main Menu");
		Scanner sc = new Scanner(System.in);
		sc.nextLine();
		getMainMenu();
	}
	
	public static void registMember() {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n>>Register Member<<");
		System.out.println("Enter details of new member: ");
		System.out.print("Name: ");
		String name = sc.nextLine();
		System.out.print("Member Number: ");
		String memberNo = sc.nextLine();
		int memberNumber = Integer.valueOf(memberNo);
		System.out.print("Address: ");
		String address = sc.nextLine();
		System.out.print("Contact No: ");
		String contactNo = sc.nextLine();
		System.out.print("Type(B/P): ");
		String type = sc.nextLine();
		
		int maxPoint = 4; // max quantity of items that can borrow
		if (type.equals("B")) {
			maxPoint = 2;
		} 
		
		System.out.println(">>Displaying all members<<");
		System.out.format("Name: %s, Member Number: %d, Member Type: %s, Address: %s, Contact No: %s, Max points: %d\n",
				name, memberNumber, address, contactNo, type, maxPoint);
		
		String key = contactNo + name + address;
		
		if (existMembers.containsKey(key)) { // if already exist, not add them
			System.out.println("\nMember already exists");
			getEnterToReturn();
		}
		
		if (type.equals("B")) {
			createBasicMember(contactNo, name, address, memberNumber);
		} else if (type.equals("P")) {
			createPremiumMember(contactNo, name, address, memberNumber);
		}
		
		getEnterToReturn();
		
	}
	
	public static void createBasicMember(String contactNo, String name, String address, int memberNumber) {
		Basic basicMember = new Basic(contactNo, name, address, memberNumber);
		basicMembers.add(basicMember);
		
		String key = contactNo + name + address;
		existMembers.put(key, 1);
		
		System.out.println("Saving Performed");
		
	}
	
	public static void createPremiumMember(String contactNo, String name, String address, int memberNumber) {
		Premium premiumMember = new Premium(contactNo, name, address, memberNumber);
		premiumMembers.add(premiumMember);
		
		String key = contactNo + name + address;
		existMembers.put(key, 1);
		System.out.println("Saving Performed");
		
	}
	
	public static void membershipRecord() {
		
		Scanner reader = new Scanner(System.in);
		
		System.out.println("\n>>Membership Record<<");
		System.out.println("1. Register Member");
		System.out.println("2. Delete Member");
		System.out.println("3. Upgrade Member");
		System.out.println("4. View Member List");
		System.out.println("5. Query Member Details");
		System.out.println("0. Main Menu\n");
		
		System.out.print("Enter option: ");
		
		int enterOption = reader.nextInt();
		
		switch (enterOption) {
		
		case 1:
			registMember();
			
		case 2:
			deteleMember();
		
		case 3:
			upgradeMember();
			
		case 4:
			displayAllMembers();
			
		case 5:
			queryMember();
			
		case 0:
			getMainMenu();
		}
		
	}
	
	
	/**
	 * This method used to upgrade member ship level based on given member number
	 * 
	 */
	public static void upgradeMember() {
		Scanner sc = new Scanner(System.in);
		System.out.println(">>Upgrade Member<<\n");
		System.out.print("Enter Membership Number to upgrade: ");
		
		int memberNo = sc.nextInt();
		
		for (int i = 0; i < basicMembers.size(); i++) {
			Basic basicMember = basicMembers.get(i);
			if (basicMember.getMemberNo() == memberNo) { // if find it in basic member list, delete from basic
				basicMembers.remove(i);
				
				// then, add it to premium list
				Premium premiumMember = new Premium(basicMember.getContactNo(), 
						basicMember.getName(), basicMember.getAdd(), memberNo);
				
				premiumMembers.add(premiumMember);
				
				System.out.format("Upgraded Meber Details: Name: %s, Member Number: %d, "
						+ "Member Type: P, Address: %s, Contact No: %s2, Max Points: 4\n", 
						basicMember.getName(), memberNo, basicMember.getAdd(), basicMember.getContactNo());
				
				System.out.println("\nSaving Performed");
				
				getEnterToReturn();
			}
		}
		
	}
	
	/**
	 * This method used to delete membership information from input member number
	 * 
	 */
	public static void deteleMember() {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n>>Delete Member<<\n");
		System.out.print("Enter Membership Number: ");
		int memberNo = sc.nextInt();
		
		for (int i = 0; i < basicMembers.size(); i++) {
			Basic basicMember = basicMembers.get(i);
			if (basicMember.getMemberNo() == memberNo) { // if find it in basic member list, delete
				basicMembers.remove(i);
				
				String key = basicMember.getContactNo() + basicMember.getName() + basicMember.getAdd();
				existMembers.remove(key); // delete members from exist map
				
				System.out.println("Saving Performed");
				System.out.println("Member is deleted");
				
				getEnterToReturn();
			}
		}
		
		for (int i = 0; i < premiumMembers.size(); i++) {
			Premium premiumMember = premiumMembers.get(i);
			if (premiumMember.getMemberNo() == memberNo) { // if find it in basic member list, delete
				premiumMembers.remove(i);
				
				String key = premiumMember.getContactNo() + premiumMember.getName() + premiumMember.getAdd();
				existMembers.remove(key); // delete members from exist map
				
				System.out.println("Saving Performed");
				System.out.println("Member is deleted");
				
				getEnterToReturn();
			}
		}
		
		// if not find this member no, display error informaiton
		System.out.println("Member not found");
		
		getEnterToReturn();
		
	}
	
	
	
	/**
	 * This method used to display all members, which include basic members and premium members.
	 * 
	 */
	public static void displayAllMembers() {
		
		System.out.println("\n>>Displaying all members<<");
		
		for (Basic basicMember : basicMembers) {
			System.out.format("Name: %s, Member Number: %d, Member Type: %s, Address: %s, Contact No: %s, Max points: %d\n",
					basicMember.getName(), basicMember.getMemberNo(), basicMember.getAdd(), basicMember.getContactNo(), "B", 2);
		}
		
		for (Premium premiumMember : premiumMembers) {
			System.out.format("Name: %s, Member Number: %d, Member Type: %s, Address: %s, Contact No: %s, Max points: %d\n",
					premiumMember.getName(), premiumMember.getMemberNo(), premiumMember.getAdd(), premiumMember.getContactNo(), "P", 4);
		}
		
		getEnterToReturn();
		
	}
	
	/**
	 * This method used to display all movie information from movie list
	 * 
	 */
	public static void displayMovieTitle() {
		
		System.out.println("\n>>Display All Movies<<");
		
		for (int i = 0; i < movies.size(); i++) {
			Movie movie = movies.get(i);
			System.out.format("ID: %d, Title: %s, Year: %d, Director: %s, Rating: %s, Genre: %s\n", 
					i + 1, movie.getTitle(), movie.getYear(), movie.getDirector(), movie.getRating(), movie.getGenre());
		}
		
		getEnterToReturn();
	}
	
	/**
	 * This method used to perform stock control by enter option from user.
	 * 
	 * @param reader
	 */
	public static void controlStock(Scanner reader) {
		
		System.out.println("\n>>Stock Control<<");
		System.out.println("1.Add Movie");
		System.out.println("2.Display all Movies");
		System.out.println("0.Main Menu\n");
		System.out.print("Enter Option: ");

		int enterOption = reader.nextInt();
		
		switch(enterOption) {
		
		case 1: 
			createMovie();
		
		case 2:
			displayMovieTitle();
			
		case 0: 
			getMainMenu();
		
		}
	}
	
	
	
	/**
	 * This method used to add new movies into stock
	 * 
	 */
	public static void createMovie() {
		
		Scanner sc = new Scanner(System.in); 
		
		System.out.println("\n>>Stock Control<<\n");
		System.out.print("Title: ");
		String title = sc.nextLine();
		System.out.print("Year: ");
		String yearString = sc.nextLine();
		int year = Integer.valueOf(yearString);
		System.out.print("Director: ");
		String director = sc.nextLine();
		System.out.print("Rating [G/PG/M/MA15/R18]: ");
		String rating = sc.nextLine();
		System.out.print("Genre [a - Action/b - Drama/c - Comedy/d - Musical/e - Family/f - Documentary]: ");
		String genre = sc.nextLine();
		System.out.print("Format[VCD/DVD]: ");
		String format = sc.nextLine();
		System.out.print("Cost: ");
		String costString = sc.nextLine();
		double cost = Double.valueOf(costString);
		System.out.print("Quantity: ");
		String quantityString = sc.nextLine();
		int quantity = Integer.valueOf(quantityString);
		
		System.out.println("\nDetails of new Movie\n");
		System.out.format("Title: %s\n", title);
		System.out.format("Year: %d\n", year);
		System.out.format("Director: %s\n", director);
		System.out.format("Rating: %s\n", rating);
		System.out.format("Genre: %s\n", genre);
		System.out.format("Format: %s\n", format);
		System.out.format("Cost: %.2f\n", cost);
		System.out.format("quantity: %d\n", quantity);
		
		Date createDate = new Date();
		
		System.out.println("Confirm?");
		System.out.println("1. Yes 2. No, return to main menu\n");
		
		System.out.print("Enter option: ");
		int enterOption = sc.nextInt();
		System.out.println();
		
		while (enterOption > -1) {
			
			if (enterOption == 0) {
				getMainMenu();
				
			} else if (enterOption == 1) {
				
				displayEnterMoiveWithCopy(quantity, cost, createDate, format);
				Movie movie = new Movie(title, year, director, rating, genre);
				Copy copy = new Copy(format, 'a', cost); // a means available
				
				// then, add movie into movies, if it already in movies, there is no need add again
				String key = movie.getTitle() + Integer.toString(movie.getYear()) 
						+  movie.getDirector() + movie.getRating() + movie.getGenre();
				if (! stocks.containsKey(key)) {
					movies.add(movie);
				}
				
				// only update the quantity in stock
				addMoiveToStocks( movie, copy, quantity);
				
				System.out.println("\nSaving Performed");
				System.out.println("Moive(s) is added to store");
				
				getEnterToReturn();
				
			} else {
				System.out.print("Enter option: ");
				enterOption = sc.nextInt();
			}
		}
	}
	
	/**
	 * 
	 * This method used to update the stocks, which contains the information on movie and its 
	 * quantity of copy.
	 * 
	 * @param movie
	 * @param copy
	 * @param quantity
	 */
	public static void addMoiveToStocks(Movie movie, Copy copy, int quantity) {
		
		String key = movie.getTitle() + Integer.toString(movie.getYear()) +  movie.getDirector() + movie.getRating() + movie.getGenre();
		String copyFormat = copy.getFormat();
		
		List<Copy> tempList = new ArrayList<Copy>();
		tempList.add(copy);
		
		if (stocks.containsKey(key)) { // if already have this movie
			if (stocks.get(key).containsKey(copyFormat)) { // if already have this kind of copy, update
				stocks.get(key).get(copyFormat).add(copy);
				
			} else { // if not have this kind of copy, add them
				stocks.get(key).put(copyFormat, tempList);
			}
		} else { // if not have this movie
			Map<String, List<Copy>> tempMap = new HashMap<String, List<Copy>>();
			tempMap.put(copyFormat, tempList);
			stocks.put(key, tempMap);
		}
		

	}
	
	/**
	 * This method used to display movies with their copies information
	 * 
	 * @param quantity
	 * @param cost
	 * @param createDate
	 * @param format
	 */
	public static void displayEnterMoiveWithCopy(int quantity, double cost, Date createDate, String format) {
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		for (int i = 0; i < quantity; i++) {
			System.out.format("Copy ID: %d, Cost: %.2f, Format: %s, Date Created: ", i + 1, cost, format);
			System.out.println(dateFormat.format(createDate));
		}
		
	}
	
	/**
	 * This method used to per-load some movie information into stocks
	 * 
	 */
	public static void preLoadMovieRecording() {
		
		Movie movie1 = new Movie("Superman Returns", 2015, "Ben Airy", "M", "a");
		movies.add(movie1);
		Copy copy1 = new Copy("DVD", 'a', 39.99);
		Map<Copy, Integer> tempMap1 = new HashMap<Copy, Integer>();
		addMoiveToStocks(movie1, copy1, 10);

		
		Movie movie2 = new Movie("Ant-man", 2015, "Steven Young", "PG", "a");
		movies.add(movie2);
		Copy copy2 = new Copy("VCD", 'a', 19.99);
		Map<Copy, Integer> tempMap2 = new HashMap<Copy, Integer>();
		addMoiveToStocks(movie2, copy2, 12);
	}
	
	/**
	 * This method used to pre-load some membership information to membership list.
	 * 
	 */
	public static void preLoadMemerRecording() {
		
		Basic basicMember = new Basic("042316478", "Ben Choi", "UNSW Campus, Sydney", 001);
		basicMembers.add(basicMember);
		String key = "042316478" + "Ben Choi" + "UNSW Campus, Sydney";
		existMembers.put(key, 1);
		
		Premium premiumMember = new Premium("04331122", "Mickey Mouse",  "123 Anzac Pde, Sydney", 002);
		premiumMembers.add(premiumMember);
		key = "04331122" + "Mickey Mouse" + "123 Anzac Pde, Sydney";
		existMembers.put(key, 1);
		
	}
	
	public static void main(String[] args) {
		
		preLoadMovieRecording();
		preLoadMemerRecording();
		
		getMainMenu();
	}

}
