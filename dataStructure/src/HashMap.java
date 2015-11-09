import java.io.*;
import java.util.*;


/*
 * This class used to save all HashMap nodes, and methods related to HashMap
 */
public class HashMap<K extends Comparable<K>, V> {
	
	private int multiplier;
	private int modulus;  
	private int hashMapSize; // available space of HashMap
	private int size = 0; // number of elements in HashMap
	private HashMapNode[] content; // content of HashMap
	
	private int putCollision = 0; // statistics use, initial to 0
	private int totalCollision = 0;
	private int maxCollision = 0;
	
	// construct a HashMap with 4000 places and given hash parameters
	public HashMap(int multiplier, int modulus) {
		this.multiplier = multiplier;
		this.modulus = modulus;
		this.hashMapSize = 4000;
		this.size = 0; // there is no elements in HashMap currently
		this.content = new HashMapNode[4000]; // malloc 4000 size for content
	}
	
	// construct a HashMap with given capacity and given hash parameters
	public HashMap(int hashMapSize, int multiplier, int modulus) {
		this.hashMapSize = hashMapSize;
		this.multiplier = multiplier;
		this.modulus = modulus;
		this.size = 0;
		this.content = new HashMapNode[hashMapSize]; // malloc size for content based on given size
	}
	
	/**
	 * Based on hash value definition, its value is hash(key) = multiplier · abs(hashCode(key)) mod modulus.
	 * Therefore, we use the given value to return hash value of given key.
	 * 
	 * @param key
	 * @return hashValue
	 */
	public int hash(K key) {
		
		// hash value of given key
		int hashValue = Math.abs(multiplier * key.hashCode()) % modulus % this.hashMapSize;
		
		return hashValue;  
	}
	
	// size (return the number of nodes currently stored in the map)
	public int size() {
		return size;
	}
	
	/**
	 * If there is no element in HashMap, return false, else return true
	 * @return booleanValue
	 */
	public boolean isEmpty() {
		
		Boolean booleanValue;
		
		if (size == 0) {
			booleanValue = false;
		} else {
			booleanValue = true;
		}
		return booleanValue;
	}
	
	/**
	 * 
	 * This method used to return all keys in this HashMap.
	 * 
	 * @return keyList
	 */
	public List<K> keys() {
		
		List<K> keyList = new ArrayList<K>();
		
		for(int i = 0; i < this.hashMapSize; i++){  
			if(content[i] != null){
				keyList.add((K)content[i].getKey());  
		    }  
		}  
		
		return keyList;
	}
	
	/**
	 * This method used to add HashMap node into HashMap. In the lecture, it requests return null 
	 * when add key-value pair successfully, or return old value when this key-value pair exists.
	 * 
	 * @param key
	 * @param value
	 * @return null
	 */
	public V put(K key, V value) {
		
		// firstly, transform from key to HashValue
		int hashValue = hash(key);
		
		// when there is no key-value pair in this HashValue, we save them here.
		if (content[hashValue] == null) {
			HashMapNode node = new HashMapNode(key, value); // construct a HashMap node
			content[hashValue] = node;
			size = size + 1;
			return null;
		} else if (key.equals((K)content[hashValue].getKey())) { //when this key had already exist
			V oldValue = (V)content[hashValue].getValue(); // get old value of this key
			content[hashValue].setValue(value); // update value
			return oldValue;
		} 
		
		/* when we face collisions, we use linear probing method to handle. 
		 * Linear probing: handles collisions by placing the colliding item
		 * in the next (circularly) available table cell.
		 * */
		
		// when the current hashValue location is not null, we change index to next position
		int prob = (hashValue + 1) % this.hashMapSize;
		
		int thisStepCollision = 0;
		
		V returnValue = null;
		
		while (prob != hashValue) {
			
			totalCollision = totalCollision + 1; // increase value, when move prob one time
			thisStepCollision = thisStepCollision + 1;
			
			if (content[prob] == null) {
				
				HashMapNode node = new HashMapNode(key, value); // construct a HashMap node
				content[prob] = node;
				size = size + 1;
				
				putCollision = putCollision + 1; // increase puCollision value, which used to statistic
				
				if (thisStepCollision > maxCollision) { // save the max collision number on each times
					maxCollision = thisStepCollision;
				}
				
				returnValue = null;
				break;
				
			} else if (key.equals((K)content[prob].getKey())) { //when this key had already exist
				
				V oldValue = (V)content[prob].getValue(); // get old value of this key
				
				content[prob].setValue(value); // update value
				
				putCollision = putCollision + 1; // increase puCollision value, which used to statistic
				
				if (thisStepCollision > maxCollision) {
					maxCollision = thisStepCollision;
				}
				
				returnValue = oldValue;
				break;
				
			} else { // when did not find available location, move to next position 
				prob = (prob + 1) % this.hashMapSize;

			}
		}
		
		return returnValue;
		
	}
	
	/**
	 * This method used to return value based on given key
	 * 
	 * @param key
	 * @return V
	 */
	public V get(K key) {
		
		int hashValue = hash(key); 
	    
		if(content[hashValue] != null && key.equals((K)content[hashValue].getKey())) {  
			return (V)content[hashValue].getValue();  
	    }  
	    
		int prob = (hashValue + 1) % this.hashMapSize; // if not in its original location, move to search
	    
	    while (prob != hashValue) {
	    	if (content[prob] == null) {  
	    		prob = (prob + 1) % this.hashMapSize;  
	    		continue;  
	    		
	    	} else if (key.equals((K)content[prob].getKey())) {  
	    		return (V)content[prob].getValue();  
	    	} else {  
	    		prob = (prob + 1) % this.hashMapSize;  
	    	}  
	    }  
	    
	    return null;  // if cannot find, return null
	}
	
	/**
	 * Use the similar approach to get, delete key-value pair based on given key.
	 * 
	 * @param key
	 * @return V
	 */
	public V remove(K key) {
		
		int hashValue = hash(key);  
		
	    if(content[hashValue] != null && key.equals((K)content[hashValue].getKey())) {  
	    	V oldValue = (V)content[hashValue].getValue();  
	    	content[hashValue] = null; // set this key-value pair value to null
	    	size = size - 1;  // when delete one, reduce the size of HashMap
	    	return oldValue;  
	    }  
	    
	    int prob = (hashValue + 1) % this.hashMapSize;  // if not find, move index
	    
	    while (prob != hashValue) {  
	    	if (content[prob] == null) {  
	    		prob = (prob + 1) % this.hashMapSize;  
	    		continue;
	    		
	    	} else if (key.equals((K)content[prob].getKey())) {  
	    		V oldValue = (V)content[prob].getValue();  
	    		content[prob] = null;  
	    		size = size - 1; 
	    		return oldValue;  
	    	} else {  
	    		prob = (prob + 1) % this.hashMapSize;  
	    	}  
	    }  
	    return null; 
	}
	
	// then, we define four methods on compute collisions
	
	/**
	 * return the number of calls to the put method which encountered a collision, 
	 * resulting in the entry not being place in the index indicated by hash, 
	 * but instead one found by moving the linear probe.
	 * 
	 * @return putCollision
	 */
	public int putCollisions() {
		return putCollision;
	}
	
	/**
	 * return the (accrued) number of probing steps needed in put method calls to 
	 * add entries to the hash map; i.e. increment on each probing step needed 
	 * within any call to put.
	 * 
	 * @return totalCollisions
	 */
	public int totalCollisions() {
		return totalCollision;
	}
	
	/**
	 * return the maximum number of probing steps that has been needed to add 
	 * a new entry to the table on any call to put
	 * 
	 * @return maxCollision
	 */
	public int maxCollisions() {
		return maxCollision;
	}
	
	/*
	 * This method used to reset all statistics used instances
	 * *
	 */
	public void resetStatistics() {
		this.putCollision = 0;
		this.maxCollision = 0;
		this.totalCollision = 0;
	}
	
	/**
	 * 
	 * This method used to read files based on given name, and save information into hash map.
	 * 
	 * @param pathToFile
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void exploreData(int multiplier, int modulus, int hashMapSize, String pathToFile) throws FileNotFoundException, IOException {
		
		HashMap<String, Double> map = new HashMap<String, Double>(hashMapSize, multiplier, modulus); // create this map
		
		// instantiate hash maps
		BufferedReader br = new BufferedReader(new FileReader(pathToFile));
			try {
				String line = br.readLine();
				while (line != null) {
					String[] pieces = line.trim().split("\\s+");
					
					/* In each line, it saves information on IP, 
					 * Frequency of Access, Cumulative Frequency, and Rank
					 * We only use first and second values
					 * */
					if (pieces.length == 4){
						String key = pieces[0];
						Double value = Double.parseDouble(pieces[1]);
						
						map.put(key, value);
					}
					line = br.readLine();
				}
			} finally {
				br.close();
			}
			
		System.out.printf("putCollision is: %d\n", map.putCollisions());
		System.out.printf("totalCollision is: %d\n", map.totalCollisions());
		System.out.printf("maxCollision is: %d\n", map.maxCollisions());
		System.out.printf("Finnally, reset all variables\n");
		map.resetStatistics();
		
	}
	
	/**
	 * Used to start all statistic data
	 * 
	 * @param args
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void main(String [] args) throws FileNotFoundException, IOException {
		
		String fileName = "datasetA.txt";
		
		int multiplier = 1;
		int modulus = 4000;
		int hashMapSize = 4000;
		
		System.out.printf("multiplier is %d, modulus is %d, hashMapSize is %d\n\n", multiplier, modulus, hashMapSize);
		exploreData(multiplier, modulus, hashMapSize, fileName);
		System.out.println("-----------------------------------");
		
		multiplier = 10;
		modulus = 4000;
		hashMapSize = 4000;
		
		System.out.printf("multiplier is %d, modulus is %d, hashMapSize is %d\n\n", multiplier, modulus, hashMapSize);
		exploreData(multiplier, modulus, hashMapSize, fileName);
		System.out.println("-----------------------------------");
		
		multiplier = 1;
		modulus = 4271;
		hashMapSize = 4000;
		
		System.out.printf("multiplier is %d, modulus is %d, hashMapSize is %d\n\n", multiplier, modulus, hashMapSize);
		exploreData(multiplier, modulus, hashMapSize, fileName);
		System.out.println("-----------------------------------");
		
		multiplier = 5;
		modulus = 4271;
		hashMapSize = 4000;
		
		System.out.printf("multiplier is %d, modulus is %d, hashMapSize is %d\n\n", multiplier, modulus, hashMapSize);
		
		exploreData(multiplier, modulus, hashMapSize, fileName);
		System.out.println("-----------------------------------");
		
		multiplier = 1;
		modulus = 4271;
		hashMapSize = 4000;
		
		System.out.printf("multiplier is %d, modulus is %d, hashMapSize is %d\n\n", multiplier, modulus, hashMapSize);
		exploreData(multiplier, modulus, hashMapSize, fileName);
		
	}
}
