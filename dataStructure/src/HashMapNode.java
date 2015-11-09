
public class HashMapNode<K extends Comparable<K>, V> {
	
	//Use the Generic type to define variables
	public K key;
	public V value;
	
	// construction method
	public HashMapNode(K key, V value) {
		this.key = key;
		this.value = value;
	}
	
	public K getKey() {
		return key;
		
	}
	
	public V getValue() {
		return value;
	}
	
	// set method, change value variables
	public void setValue(V newValue) {
		this.value = newValue;
	}
	
}
