import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * 
 * where cells of your hash map have been elements in an array in 
 * which store up to one object of type HashMapNode, they will now 
 * hold one element on type ChainingHashMapNode.
 *
 */
public class ChainingHashMap<K extends Comparable<K>, V> {
	
	private int multiplier;
	private int modulus;
	private int hashMapSize; // available space of HashMap
	private int size = 0; // number of elements in HashMap
	private ChainingHashMapNode[] content; // content of chaining map
	
	private int putCollision = 0; // statistics use, initial to 0
	private int totalCollision = 0;
	private int maxCollision = 0;
	
	// construct a HashMap with 4000 places and given hash parameters
	public ChainingHashMap(int multiplier, int modulus) {
		this.multiplier = multiplier;
		this.modulus = modulus;
		this.content = new ChainingHashMapNode[4000];
	}
	
	// construct a HashMap with given capacity and given hash parameters
	public ChainingHashMap(int hashMapSize, int multiplier, int modulus) {
		this.hashMapSize = hashMapSize;
		this.multiplier = multiplier;
		this.modulus = modulus;
		this.content = new ChainingHashMapNode[hashMapSize];
	}
	
	// hashing
	public int hash(K key) {
		
		int hashValue = Math.abs(multiplier * key.hashCode()) % modulus % this.hashMapSize;
		return hashValue;  
	}
	
	// size (return the number of nodes currently stored in the map)
	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		if (size == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * with the first element being the number of nodes stored in the fullest hash 
	 * map cell and the second element being the number of hash map cells containing 
	 * this number of nodes, which returns an int[] of size two
	 * 
	 * @return fullestsBuckets
	 */
	public int[] getFullestBuckets() {
		
		int[] fullestsBuckets = new int[2]; // int[] of size two
		
		// our task is to over all hash values, and get all their key-value pairs
		int maxBucketSize = 0;
		int maxBucketIndex = 0;
		
		for (int bucketIndex = 0; bucketIndex < content.length; bucketIndex++) {
			int currentBucketSize = 0;
			ChainingHashMapNode startNode = content[bucketIndex]; 
			
			while (startNode != null) { // when this node exists
				currentBucketSize = currentBucketSize + 1;
				startNode = startNode.getNext(); // move to next node
			}
			
			if (currentBucketSize > maxBucketSize) { // when find this bucket have elements more than max
				maxBucketSize = currentBucketSize;
				maxBucketIndex = bucketIndex;
			}
		} 
		
		fullestsBuckets[0] = maxBucketSize;
		fullestsBuckets[1] = maxBucketIndex;
		
		return fullestsBuckets;
	}
	
	/**
	 * Return all keys in this map
	 * 
	 * @return returnList
	 */
	public List<K> keys() {
		
		List<K> returnList = new ArrayList<K>();  
		
        for(int i = 0; i < this.hashMapSize; i++){  
            ChainingHashMapNode node = content[i];
            
            while(node != null){  // if it exist next node, continue return
            	returnList.add((K)node.getKey());  
                node = node.getNext();  
            }  
        }  
        
        return returnList; 
	}
	
	/**
	 * Used to insert key-value pair. When its key hashvalue already have a value, add the new pair after it.
	 * 
	 * @param key
	 * @param value
	 * @return null
	 */
	public V put(K key, V value) {
		
		int hashValue = hash(key);  
		
        if(content[hashValue] == null){ // if current position is empty
        	
            ChainingHashMapNode node = new ChainingHashMapNode(key, value);  
            content[hashValue] = node;  
            
            size = size + 1;  
            return null;  
        }  
        
        ChainingHashMapNode node = content[hashValue];  
        ChainingHashMapNode pre = node;  
        
        int currentCollision = 0;
        
        while (node != null) { // when this position had been used
        	
        	totalCollision = totalCollision + 1; // when change node position, increase one
        	currentCollision = currentCollision + 1;
        	
            if (key.equals((K)node.getKey())) { // if they have same key
                V oldValue = (V)node.getValue();  
                node.setValue(value);  
                putCollision = putCollision + 1; // when it add success, increase one
                
                return oldValue;  
                
            } else { // add the new value into this node 
            	
                pre = node;  
                node = node.getNext();  // move to the end of these same key node list
                
                // when loop end, current available node is pre
            }  
        }  
        
        if (currentCollision > maxCollision) {
        	maxCollision = currentCollision;
        }
        
        ChainingHashMapNode latestNode = new ChainingHashMapNode(key, value); 
        pre.setNext(latestNode);  // update latestNode to pre next
        size = size + 1;  
        return null;  
	}
	
	public V get(K key) {
		
		int hashValue = hash(key);
		ChainingHashMapNode startNode = content[hashValue]; // start node is the first node which under this hash value
		
		while (startNode != null) {
			
			if(key.equals((K)startNode.getKey())){ // when find current key-value pair
                return (V)startNode.getValue();  
            }else{  
            	startNode = startNode.getNext();  
            }
		}
		
		return null;
		
	}
	
	/**
	 * Used to delete key-value pair
	 * 
	 * @param key
	 * @return V
	 */
	public V remove(K key) {
		
		int hashValue = hash(key);  
        ChainingHashMapNode startNode = content[hashValue];
        
        if(startNode != null && key.equals((K)startNode.getKey())){ // when find this key-value pair
        	
            content[hashValue] = startNode.getNext(); // skip this node by put its next node on its location
            
            size = size - 1; // decrease the size of element in map  
            
            return (V)startNode.getValue();  
        }  
        
        // if not on the first node on current hash value position
        ChainingHashMapNode nextNode = startNode.getNext();  // get next node
        
        while(nextNode != null){
        	
            if (key.equals((K)nextNode.getKey())) {  // when find this key-pair at next node
            	
            	startNode.setNext(nextNode.getNext());  // skip it
                size = size - 1;  
                
                return (V)nextNode.getValue(); 
                
            } else {  // if not find, move to next node
            	startNode = nextNode;  
            	nextNode = startNode.getNext();  // we move one step
            }  
        }  
        return null;
	}
	
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
		
		ChainingHashMap<String, Double> map = new ChainingHashMap<String, Double>(hashMapSize, multiplier, modulus); // create this map
		
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
		System.out.printf("FullestBuckets is: {%d, %d}\n", map.getFullestBuckets()[0], map.getFullestBuckets()[1]);
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
		hashMapSize = 2000;
		
		System.out.printf("multiplier is %d, modulus is %d, hashMapSize is %d\n\n", multiplier, modulus, hashMapSize);
		exploreData(multiplier, modulus, hashMapSize, fileName);
		
	}
	
}
