import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;



public class SimplePasswordManager <K extends Comparable<K>, V > {
	
	// construct a SimplePasswordManager with 4000 places and default hash parameters
	// multiplier = 1 and modulus = 4271
	private int multiplier = 1;
	private int modulus = 4271;
	private int hashMapSize = 4000;
	private int size = 0; // number of elements in HashMap
	private ChainingHashMap database; // select chaining hash map, and save all user-password pairs to a 'database'
	
	public SimplePasswordManager() {
		this.database = new ChainingHashMap(this.hashMapSize, this.multiplier, this.modulus);
	}
	
	// construct a SimplePasswordManager with the supplied parameters
	public SimplePasswordManager(int size, int multiplier, int modulus) {
		this.hashMapSize = size;
		this.multiplier = multiplier;
		this.modulus = modulus;
		this.database = new ChainingHashMap(this.hashMapSize, this.multiplier, this.modulus);
	}
	
	// hashing
	/**
	 * This method aims to use hash to secure password, then it will return a hash value based on 
	 * given password value.
	 * 
	 * @param password
	 * @return passwordHashValue
	 */
	public Long hashPassword(String password) {
		long passwordHashValue = Math.abs(multiplier * password.hashCode()) % modulus % this.hashMapSize;
		return passwordHashValue; 
	}
	
	// interface methods
	// return an array of the usernames of the users currently stored
	
	/**
	 * This method aims to return all users in the database.
	 * 
	 * @return users
	 */
	public List<String> listUsers() {
		
		List<String> users = new ArrayList<String>();
		
		for (int i = 0; i < database.keys().size(); i++) {
			users.add((String) database.keys().get(i));
		}
		return users;
	}
	
	/**
	 * Check that the hash of the input password is the same as the stored password for the given user.
	 * If no user exists in the store with the given username, return “No such user exists.”
	 * If the hashed password does not matches, return “Failed to authenticate user.”
	 * Otherwise, return the username
	 * @param username
	 * @param password
	 * @return returnString
	 */
	public String authenticate(String username, String password) {
		
		String returnString = "";
		
		String storePassword = (String) database.get(username);
		
		if (storePassword.equals("null")) {
			returnString = "No such user exists.\n";
		} else if (hashPassword(storePassword) != hashPassword(password)) {
			returnString = "Failed to authenticate user.\n";
		} else {
			returnString = username;
		}
		
		return returnString;
		
	}
	
	/**
	 * Add a new user to your HashMap store with the given username and password.
	 * If a user with the given username is already stored, just return “User already exists.”
	 * Otherwise, store the user and password hash, then return the username.
	 * 
	 * @param username
	 * @param password
	 * @return returnString
	 */
	public String addNewUser(String username, String password) {
		
		String returnString = "";
		
		if (database.get(username) != null) { // when username already in database
			returnString = "User already exists.\n";
		} else {
			database.put(username, password);
			returnString = username;
		}
		
		return returnString;
		
	}
	
	/**
	 * Delete the given user from the store of users after authenticating the given password.
	 * If no user exists in the store with the given username, return “No such user exists.”
	 * If the given password fails authentication, print the message “Failed to authenticate user.”
	 * Otherwise, delete the user and then return the username.
	 * 
	 * @param username
	 * @param password
	 * @return returnString
	 */
	public String deleteUser(String username, String password) {
		
		String returnString = "";
		
		String authenticationResult = authenticate(username, password);
		
		if (authenticationResult.equals(username)) { // after authenticating, delete username and password
			database.remove(username);
			returnString = username;
		} else if (authenticationResult.equals("No such user exists.\n")) {
			returnString = "No such user exists.\n";
		} else if (! authenticationResult.equals(username)) {
			returnString = "Failed to authenticate user.\n";
		}
		
		return returnString;
		
	}
	
	/**
	 * Update the stored password for the given user to the (hash of) the newPassword after authenticating the oldPassword
	 * If no user exists in the store with the given username, just return “No such user exists.”
	 * If the oldPassword fails authentication, just return “Failed to authenticate user.”
	 * Otherwise, update the password hash and then return the username
	 * 
	 * @param username
	 * @param oldPassword
	 * @param newPassword
	 * @return returnString
	 */
	public String resetPassword(String username, String oldPassword, String newPassword) {
		
		String returnString = "";
		
		String authenticationResult = authenticate(username, oldPassword);
		
		if (authenticationResult.equals(username)) { // after authenticating, delete username and password
			database.put(username, newPassword);
			returnString = username;
		} else if (authenticationResult.equals("No such user exists.\n")) {
			returnString = "No such user exists.\n";
		} else if (! authenticationResult.equals(username)) {
			returnString = "Failed to authenticate user.\n";
		}
		
		return returnString;
		
	}
	
	public static void printHashCollisions(String pathToFile) throws FileNotFoundException, IOException {
		
		HashMap<Long, List<String>> map = new HashMap<Long, List<String>>(50000, 1, 56897);
		SimplePasswordManager spm = new SimplePasswordManager();
		BufferedReader br = new BufferedReader(new FileReader(pathToFile));
		
		int totalPasswordNumber = 0;
		
		try {
			String line = br.readLine();
			
			while (line != null) {
				totalPasswordNumber++;
				String password = line.trim();
				Long passwordHash = spm.hashPassword(password);
				
				// TODO: if passwordHash is in a, add password to its list value
				// else, instantiate a new ArrayList and add password to it
				
				if (map.get(passwordHash) != null) {
					map.get(passwordHash).add(password);
				} else {
					List<String> tempList = new ArrayList<String>();
					map.put(passwordHash, tempList);
				}
				
				line = br.readLine();
			}
			
		} finally {
			br.close();
		}
		
		List<Long> hashes = map.keys();
		int numberOfDuplication = 0;
		for (Long hash : hashes){
			List<String> passwords = map.get(hash);
			if (passwords.size() > 1){
				numberOfDuplication++;
			}
		}
		
		System.out.printf("In the %d passwords, there are %d multiple password having the same hash representation.\n", totalPasswordNumber, numberOfDuplication);
	}
	
	public static void main(String [] args) throws FileNotFoundException, IOException {
		String fileName = "datasetB.txt";
		printHashCollisions(fileName);
	}
}
