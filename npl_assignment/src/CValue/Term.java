package CValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import documentAnalysis.nlp.struct.TaggedToken;

/**
 * This class used to save the Term information, which include a list contain
 * the relatived TaggedToken, and its c_value, frequency
 * @author masai
 *
 */
public class Term {
	
	public List<TaggedToken> contextTaggedToken = new ArrayList<TaggedToken>();
	public float C_value = 0;
	public double NC_value = 0;
	public int frequency;
	public static String corpus;
	public List<Term> LongerTerm = new ArrayList<Term>();
	public Map<String, Integer> ContextWords;
	public List<List<Integer>> locations;
	public float weights = 0;
	
	public Term(List<TaggedToken> contextTaggedToken, String totalStrings) {
		this.contextTaggedToken = contextTaggedToken;
		this.frequency = getFrequency(totalStrings, contextTaggedToken);
		this.corpus = totalStrings;
		this.locations = new ArrayList<List<Integer>>();
		this.ContextWords = new HashMap<String, Integer>();
	}
	
	public void addLocations(List<Integer> locations) {
		this.locations.add(locations);
	}
	
	public List<TaggedToken> getContextTaggedToken() {
		return contextTaggedToken;
	}
	
	public void setNC_value(double NC_value) {
		this.NC_value = NC_value;
	}
	
	public void AddLongerTerm(Term term) {
		this.LongerTerm.add(term);
	}
	
	public List<Term> getLongerTerm() {
		return this.LongerTerm;
	}
	
	/**
	 * This method used to add ContextWord to the Term
	 * 
	 * @param ContextWord
	 */
	public void addContextWord(String ContextWord){
		if(!ContextWords.containsKey(ContextWord)){
			ContextWords.put(ContextWord, 1);
		}else{
			ContextWords.put(ContextWord, ContextWords.get(ContextWord) + 1);
		}
	}
	
	/**
	 * This method used to show the string context of this term
	 * 
	 * @return termContext
	 */
	public String ShowTerm(List<TaggedToken> contextTaggedToken) {
		String termContext = "";
		for (TaggedToken tt : contextTaggedToken) {
			termContext = termContext + tt.getLemma() + " ";
		}
		return termContext;
	}
	
	
	/**
	 * This method used to update C_value information
	 * @param C_value
	 */
	public void updateC_value(float C_value) {
		this.C_value = C_value;
	}
	
	/**
	 * This method used to return the TermContext String
	 * @return TermContext
	 */
	public static String getTermContext(List<TaggedToken> contextTaggedToken) {
		
		String TermContext = "";
		for (TaggedToken tt : contextTaggedToken) {
			TermContext = TermContext + tt.getLemma();
		}
		return TermContext;	
	}
	
	/**
	 * This method used to return frequency based on 
	 * term context
	 * @return frequency
	 */
	public static Integer getFrequency (String totalStrings, List<TaggedToken> contextTaggedToken) {
		String termContext = getTermContext(contextTaggedToken);
		Pattern pattern = Pattern.compile(termContext); //use the TermContext get pattern
		Integer frequency = 0;
		
		Matcher matcher = pattern.matcher(totalStrings); //prepare the match and pattern
		
		// when find this termForm in the total documents
		while(matcher.find()){
			frequency = frequency + 1;
		}
		return frequency;
	}
	
	/**
	 * This method used to get all the term context in a string list
	 * @return contexts
	 */
	public List<String> getContextLst() {
		
		List<String> contexts = new ArrayList<String>();
		
		for (TaggedToken tt : contextTaggedToken) {
			contexts.add(tt.getLemma());
		}
		
		return contexts;
		
	}
	
	/**
	 * This method used to get the subTerm of this term
	 * @return SubTerms
	 */
	public static ArrayList<Term> getSubTerm(List<TaggedToken> contextTaggedToken) {
		
		ArrayList<Term> subTerms = new ArrayList<Term>();
		
		//get the subTerm and save into subTerm list
		for (int index1 = 0; index1 < contextTaggedToken.size(); index1++) {
			
			//used to save the TaggedToken information
			List<TaggedToken> temp = new ArrayList<TaggedToken>();
			
			for (int index2 = 0; index2 < contextTaggedToken.size(); index2++) {
				if (index2 != index1) {
					temp.add(contextTaggedToken.get(index2));
				}
			}
			//get the subTerm and save it
			Term term = new Term(temp, corpus);
			subTerms.add(term);
		}
		return subTerms;
	}
}
