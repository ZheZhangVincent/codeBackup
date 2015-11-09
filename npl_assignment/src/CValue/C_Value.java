package CValue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import documentAnalysis.nlp.struct.TaggedToken;
import documentAnalysis.nlp.utils.StopList;

public class C_Value {
	
	/*
	Please look at the main method to follow the C-value approach.
	*/
	
	public static void main(String args[]) throws IOException {
		
		String dirFilePath = "filter_Test"; //the corpus save in data folder
		ArrayList<Term> terms = getCValue(dirFilePath);
		
		for (Term term: terms) {
			System.out.println("Term: " + term.ShowTerm(term.getContextTaggedToken()));
			System.out.println("C_Value: " + term.C_value);
			System.out.println("--------------");
		}
	} 
	
	/**
	 * This method used to calculated the C value of each term,
	 * and update the C value into them.
	 * 
	 * @param terms
	 * @return terms
	 * @throws IOException 
	 */
	public static ArrayList<Term> getCValue(String dirFilePath) throws IOException {
		
		ArrayList<TaggedToken> wordList = Filter.getWordsList(dirFilePath); //save all words
		Filter.simpleWordPOS(wordList); //make the word's POS simple by the simpleWordPOS method
				
		//then, form the totalPOSString and totalString as corpus based on the wordList
		String totalPOS = Filter.getTotalPOS(wordList);
		String corpus = Filter.getTotalWordForm(wordList);

		List<Pattern> patterns = LiguisticFilter.getLiguisticList();
		List<List<Integer>> indexList = regularExpression(totalPOS, patterns);
				
		ArrayList<Term> terms = getTerms(wordList, corpus, indexList); ; //used to save the terms
		Set<String> stopList = StopList.getStopList("stoplist"); //get stop list
		
		terms = Filter.filterTermByLength(terms); //use term length to filter
		terms = Filter.filteFrequency(terms); //use frequency to filter
		terms = Filter.applyStopList(terms, stopList); //use stop list to filter
		HashMap<String, Term> map = Filter.filterDuplicTerm(terms); //get the terms with keys
		terms.clear(); //empty the original list
		for (Term term : map.values()) {
			terms.add(term);
		}
		terms = Filter.getTermRank(terms); //rank the term
		terms = addLongerTerm(terms); //get Longer Term
		terms = Filter.getTermRank(terms); //rank the term again
		
		int max_length = getMaximumLength(terms); //get the max_length of the terms
		for (Term term : terms) {
			if (term.contextTaggedToken.size() == max_length && term.contextTaggedToken.size() > 2) { //for strings of maximum length
				term.updateC_value((float) (Math.log(term.getContextTaggedToken().size()) 
						/ Math.log(2) * term.frequency)); //long2 frequency multiply term.size

			} else {
				//when the term not have the longerTerm, they are not nested
				if (term.LongerTerm.size() == 0) {
					term.updateC_value((float) (Math.log(term.getContextTaggedToken().size()) 
							/ Math.log(2) * term.frequency)); //long2 frequency multiply term.size
				} else {
					//get this terms' longerTerm list
					List<Term> longTerm = term.getLongerTerm();
					float c = 0;
					float t = 0;
					for (Term te : longTerm) {
						c = c + 1;
						t = t + te.frequency;
					}
					term.updateC_value((float) (Math.log(term.getContextTaggedToken().size()/Math.log(2) 
							* (term.frequency - (t/c)))));
					//when the term longer than 2, continue to get subTerm
					if (term.contextTaggedToken.size() > 2) {
						//get the subTerm of this term
						ArrayList<Term> subTerms = term.getSubTerm(term.contextTaggedToken);
						//update these subterms' t_values and c_values
						for (Term subTerm : subTerms) { //initial their t value to subTerm frequency
							//when the subTerm in the term list
							if (map.containsKey(subTerm.ShowTerm(subTerm.contextTaggedToken))) {
								map.get(subTerm.ShowTerm(subTerm.contextTaggedToken)).AddLongerTerm(term); //add term into the subTerm
								//map.put(subTerm.ShowTerm(subTerm.contextTaggedToken), subTerm);
							}
						}
					}
				}
			}
		}
		terms = Filter.getCvalueRank(terms); //in this method, filter the term with C-value less than 2
		
		return terms;
	}

	/**
	 * This method used to return the distincted multi-term based on 
	 * given pattern and give totalString
	 * @param totalStrings
	 * @param patterns
	 * @return indexList
	 */
	public static List<List<Integer>> regularExpression(String totalStrings, List<Pattern> patterns) {
		
		//create a list to save the patters' position
		List<List<Integer>> indexList = new ArrayList<List<Integer>>();
		
		//get each patterns' result
		for (Pattern pattern : patterns) {
			Matcher matcher = pattern.matcher(totalStrings); //prepare the match and pattern
			
			// Iterate through all matched substrings and save their positions into indexList
			while(matcher.find()){
				
				//add the matcher index information into indexList
				List<Integer> temp = new ArrayList<Integer>();
				temp.add(matcher.start());
				temp.add(matcher.end());
				indexList.add(temp);
			}
		}
		return indexList;
	}
	
	/**
	 * This method used to get the filter term based on the given indexList
	 * @param wordList
	 * @param indexList
	 * @return wordList
	 */
	public static ArrayList<Term> getTerms(ArrayList<TaggedToken> wordList, 
			String totalString, List<List<Integer>> indexList) {
		
		ArrayList<Term> terms = new ArrayList<Term>();
		
		//for the each range in the indexList
		for (List<Integer> range : indexList) {
			List<TaggedToken> temp = new ArrayList<TaggedToken>();
			for (int index = 0; index < wordList.size(); index++) {
				if ((index == range.get(0)) | (index > range.get(0) & index < range.get(1))) {
					if (temp.contains(wordList.get(index)) != true) {
						temp.add(wordList.get(index));
					}
				}
			}
			Term term = new Term(temp, totalString);

			terms.add(term);
		}
		return terms;
	}
	
	/**
	 * This method used to return the max length of a term list
	 * 
	 * @param terms
	 * @return max_length
	 */
	public static int getMaximumLength(ArrayList<Term> terms) {
		int max_length = terms.get(0).contextTaggedToken.size();
		return max_length;
	}
	
	/**
	 * This method used to get back the Term Word Context as the 
	 * Corpus to let subTerm to match
	 * 
	 * @param terms
	 * @return termCorpus
	 */
	public static String getTermCorpus(ArrayList<Term> terms) {
		String termCorpus = "";
		for (Term term : terms) {
			termCorpus = termCorpus + term.getTermContext(term.contextTaggedToken);
		}
		return termCorpus;
	}
	
	/**
	 * This method used to add the longer Term based on the given terms
	 * 
	 * @param terms
	 * @return term
	 */
	public static ArrayList<Term> addLongerTerm(ArrayList<Term> terms) {
		ArrayList<Term> term = new ArrayList<Term>();
		HashMap<String, Term> map = Filter.filterDuplicTerm(terms); //get the terms with keys
		
		for (Term te : map.values()) {
			if (te.contextTaggedToken.size() > 2) { //when the term is longer than 2
				ArrayList<Term> subTerms = te.getSubTerm(te.contextTaggedToken); //get its subTerm
				for (Term subTerm : subTerms) {
					if (map.containsKey(subTerm.ShowTerm(subTerm.contextTaggedToken))) {
						map.get(subTerm.ShowTerm(subTerm.contextTaggedToken)).AddLongerTerm(te);
					}
				}
			}
		}
		for (Term te : map.values()) { //then, return the term lists
			term.add(te);
		}
		return term;
	}
	
}