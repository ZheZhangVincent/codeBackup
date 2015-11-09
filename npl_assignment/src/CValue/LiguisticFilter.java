package CValue;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class LiguisticFilter {
	
	public static String _filter1 = "N+N";
	public static String _filter2 = "(J|N)+N";
	public static String _filter3 = "((J|N)+|(J|N)*(P)?(J|N)*)N+";
	
	public static String _filter4 = "N";
	public static String _filter5 = "J";
	public static String _filter6 = "V";
	
	/**
	 * This method used to return a list which contains patterns from
	 * LiguisticFilter class used in C value
	 */
	public static List<Pattern> getLiguisticList() {
		
		List<Pattern> patterns = new ArrayList<Pattern>();
		Pattern pattern = Pattern.compile(_filter1);
		patterns.add(pattern);
		pattern = Pattern.compile(_filter2);
		patterns.add(pattern);
		pattern = Pattern.compile(_filter3);
		patterns.add(pattern);
		
		return patterns;
		
	}
	
	/**
	 * This method used to return a list which contains patterns from
	 * LiguisticFilter class used in NC value
	 */
	public static List<Pattern> getPatternList() {
		
		List<Pattern> patterns = new ArrayList<Pattern>();
		Pattern pattern = Pattern.compile(_filter4);
		patterns.add(pattern);
		pattern = Pattern.compile(_filter5);
		patterns.add(pattern);
		pattern = Pattern.compile(_filter6);
		patterns.add(pattern);
		
		return patterns;
		
	}
	
	/**
	 * This method used to return pattern based on given String
	 * 
	 * @param word
	 * @return pattern
	 */
	public static Pattern getPattern(String word) {
		Pattern pattern = Pattern.compile(word);
		return pattern;
	}
	
}
