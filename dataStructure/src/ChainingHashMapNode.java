
public class ChainingHashMapNode<K extends Comparable<K>, V> {
	
	private K key;  
    private V value;  
    private ChainingHashMapNode nextNode; // define the next node of current one
	
	// construction
	public ChainingHashMapNode(K key, V value) {
		this.key = key;
		this.value = value;
		this.nextNode = null; // on the construction, set next node to empty
	}
	// get methods
	public K getKey() {
		return key;
	}
	public V getValue() {
		return value;
	}
	
	public ChainingHashMapNode<K, V> getNext() {
		return nextNode;
	}
	
	// set methods
	public void setValue(V newValue) {
		this.value = newValue;
	}
	
	public void setNext(ChainingHashMapNode<K, V> newNextNode) {
		this.nextNode = newNextNode;
	}

}
