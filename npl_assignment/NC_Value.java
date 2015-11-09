package NCValue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import documentAnalysis.nlp.struct.ComparableObj;
import documentAnalysis.nlp.struct.TaggedToken;
import CValue.C_Value;
import CValue.Filter;
import CValue.LiguisticFilter;
import CValue.Term;

public class NC_Value {
	
	public static void main(String args[]) throws IOException {
				
		String dirFilePath = "data";
		int searchRange = 3;
		
		ArrayList<Term> terms = getNC_Value(dirFilePath, searchRange);
		
		for (Term term : terms) {
			//System.out.print(term.ShowTerm(term.contextTaggedToken) + ": ");
			//System.out.print("C_Value: " + term.C_value);
			//System.out.println(" NC_Value: " + term.NC_value);
			
		}
		
	}
	
	
	public static ArrayList<Term> getNC_Value(String dirFilePath, int searchRange) throws IOException {
		
		//first of all, get the terms with C value
		ArrayList<Term> terms = C_Value.getCValue(dirFilePath); //get the term with C_value
		
		//then, update the Terms' index information
		ArrayList<TaggedToken> wordList = Filter.getWordsList(dirFilePath); //save all words
		Filter.simpleWordPOS(wordList);
		terms = updateTermIndex(terms, wordList); //add terms' index of wordList
		HashSet<Integer> TotalTermIndex = getTotalTermIndex(terms); //then save the total index info
		
		//then, get the context words based on given searchRange
		for (Term term : terms) {
			//at the same time, save the finding context words into contextWords
			term = getContextWords(term, wordList, TotalTermIndex, searchRange);
		}
		
		//after find all contexts, calcuted their weights
		calculateWeight(ContextWords.contextWords, terms);
		
		//then, adding the NC value into the terms
		terms = addNCValue(terms);
		
		//then, order the terms based on NC value
		terms = orderTermsByNCvalue(terms);
		
		return terms;
		
	}

	/**
	 * This method used to return the total set of the index of Terms
	 * 
	 * @param terms
	 * @return totalTermIndex
	 */
	public static HashSet<Integer> getTotalTermIndex(ArrayList<Term> terms) {
		HashSet<Integer> totalTermIndex = new HashSet<Integer>();
		
		for (Term term : terms) {
			for (List<Integer> location : term.locations) {
				for (Integer index : location) {
					totalTermIndex.add(index);
				}
			}
		}
		return totalTermIndex;
	}

	/**
	 * This method used to updateTermIndex into terms
	 * 
	 * @param terms
	 * @param wordList
	 * @return terms
	 */
	public static ArrayList<Term> updateTermIndex(ArrayList<Term> terms, 
			ArrayList<TaggedToken> wordList) {
		
		for (Term term : terms) {
			List<List<Integer>> StringIndexs = getStringIndex(term);
			for (List<Integer> StringIndex : StringIndexs) {
				List<Integer> TaggedIndex = transStringIndexToTaggedIndex(StringIndex, wordList);
				term.addLocations(TaggedIndex);
			}
		}
		return terms;
	}
	
	/**
	 * This method used to get the StringIndex of each term
	 * 
	 * @param term
	 * @return returnList
	 */
	public static List<List<Integer>> getStringIndex(Term term) {
		
		List<List<Integer>> returnList = new ArrayList<List<Integer>>();
		Pattern pattern = LiguisticFilter.getPattern(term.getTermContext(term.contextTaggedToken));
		Matcher matcher = pattern.matcher(term.corpus); //prepare the match and pattern
					
		// Iterate through all matched substrings and save their positions into indexList
		while(matcher.find()){
			List<Integer> temp = new ArrayList<Integer>();
			temp.add(matcher.start() - 1); //add the matcher index into returnList
			temp.add(matcher.end());
			returnList.add(temp);
		}
		return returnList;
	}
	
	/**
	 * This method used to return a TaggedIndex based on given StringIndex
	 * 
	 * @param StringIndex
	 * @param wordList
	 * @return returnList
	 */
	public static List<Integer> transStringIndexToTaggedIndex(List<Integer> StringIndex, 
			ArrayList<TaggedToken> wordList) {
		
		List<Integer> returnList = new ArrayList<Integer>();
		Integer number = 0;
		Integer count = 0;
		for (TaggedToken tt : wordList) {
			if (number == StringIndex.get(0)) {
				returnList.add(count);
				number = number + tt.getWordForm().length(); //add the TaggedToken length
				count = count + 1; //plus 1 to the Tagged Index
			} else if (number > StringIndex.get(0) & number < StringIndex.get(1)) {
				returnList.add(count);
				number = number + tt.getWordForm().length();
				count = count + 1;
			} 
			else if (number == StringIndex.get(1)) {
				break;
			} else {
				number = number + tt.getWordForm().length(); //add the TaggedToken length
				count = count + 1; //plus 1 to the Tagged Index
			}
		}	
		return returnList;
	}
	
	/**
	 * This method used to calculate the NC value based on the terms'
	 * locations
	 * 
	 * @param terms
	 * @return terms
	 */
	public static ArrayList<Term> addNCValue(ArrayList<Term> terms) {
		for (Term te : terms) {
			double temp = 0;
			for (String contextWord : te.ContextWords.keySet()) {
				//System.out.println(ContextWords.contextWords.get(contextWord).weight);
				//System.out.println(te.ContextWords.get(contextWord));
				temp = temp + te.ContextWords.get(contextWord) 
						* ContextWords.contextWords.get(contextWord).weight;
			}
			double NC_value = 0.8 * te.C_value + 0.2 * te.weights;
			te.setNC_value(NC_value);
		}
		return terms;
	}

	/**
	 * This method used to find the ContextWords based on the given terms' location
	 * 
	 * @param term
	 * @param wordList
	 * @param TotalTermIndex
	 * @return term
	 */
	public static Term getContextWords(Term term, ArrayList<TaggedToken> wordList, 
			HashSet<Integer> TotalTermIndex, Integer searchRange) {
		
		for (List<Integer> location : term.locations) {
			Integer move = 1;
			//first of all, look for the word left on the term
			while (move < searchRange + 1) {
				Integer pointer = location.get(0) - move;
				
				//when we find a term or a "S", means ".", end search
				if (TotalTermIndex.contains(pointer)) {
					break;
				} else if (wordList.get(pointer).getPos().equals("S")) {
					break;
				} else {
					move = move + 1;
					if (detectContextWord(wordList.get(pointer))) {
						String contextWord = wordList.get(pointer).getWordForm();
						term.addContextWord(contextWord);
						ContextWord _ContextWord = new ContextWord(contextWord);
						_ContextWord.changeT_W(term.ShowTerm(term.contextTaggedToken));
						ContextWords.contextWords.put(contextWord, _ContextWord);
					}
				}
			}
			
			//then, look for the word right on the term
			move = 1;
			while (move < searchRange + 1) {
				Integer pointer = location.get(0) + move;
				
				//when we find a term or a "S", means ".", end search
				if (TotalTermIndex.contains(pointer)) {
					break;
				} else if (wordList.get(pointer).getPos().equals("S")) {
					break;
				} else {
					move = move + 1;
					if (detectContextWord(wordList.get(pointer))) {
						String contextWord = wordList.get(pointer).getWordForm();
						term.addContextWord(contextWord);
						ContextWord _ContextWord = new ContextWord(contextWord);
						_ContextWord.changeT_W(term.ShowTerm(term.contextTaggedToken));
						ContextWords.contextWords.put(contextWord, _ContextWord);
					}
				}
			}
		}
		return term;
	}
	
	/**
	 * This method used to make a detect the word POS
	 * 
	 * @param word
	 * @return bool_Value
	 */
	public static boolean detectContextWord(TaggedToken word) {
		
		boolean bool_Value = false;
		List<Pattern> patterns = LiguisticFilter.getPatternList();
		
		for (Pattern pattern : patterns) {
			Matcher matcher = pattern.matcher(word.getPos()); 
			if (matcher.find()){ //when find the related POS
				bool_Value = true;
			}
		}
		return bool_Value;
	}
	
	/**
	 * This method usd to save the rank the terms based on the NC value
	 * 
	 * @param terms
	 * @return rankedTerm
	 */
	public static ArrayList<Term> orderTermsByNCvalue(ArrayList<Term> terms) {
		ArrayList<Term> rankedTerm = new ArrayList<Term>();
		List<ComparableObj<Term,Double>> rank  = new ArrayList<ComparableObj<Term,Double>>();
		
		for (Term term: terms) {
			rank.add(new ComparableObj<Term, Double>(term, term.NC_value));
		}
		Collections.sort(rank);
		
		//get the terms in the rank
		for (ComparableObj<Term, Double> co : rank) {
			rankedTerm.add(co.getComObject());
		}
		return rankedTerm;
	}
	
	/**
	 * This method used to calculate each Context Words' weights
	 * 
	 * @param contextWords
	 */
	public static void calculateWeight(Map<String, ContextWord> contextWords, ArrayList<Term> terms) {
		
		for(ContextWord contextWord: contextWords.values()){
			contextWord.setWeight((double)contextWord.t_w/terms.size());
		}	
	}
	

}
