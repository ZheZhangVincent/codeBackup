package NCValue;


import java.util.HashMap;
import java.util.Map;

public class ContextWords {
	
	public static Map<String, ContextWord> contextWords = new HashMap<String, ContextWord>();
	
	/**
	 * This method used to return the weight of a contextWord
	 * 
	 * @param contextWordName
	 * @return contextWords.get(contextWordName).weight
	 */
	public static double getContextWord(String contextWordName) {
		if (contextWords.containsKey(contextWordName)) {
			return contextWords.get(contextWordName).weight;
		} else {
			return 0;
		}
	}

}
