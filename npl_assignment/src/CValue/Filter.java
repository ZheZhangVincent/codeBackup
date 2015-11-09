package CValue;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import documentAnalysis.nlp.NLPPipeline;
import documentAnalysis.nlp.TextProcessingException;
import documentAnalysis.nlp.struct.ComparableObj;
import documentAnalysis.nlp.struct.Document;
import documentAnalysis.nlp.struct.Sentence;
import documentAnalysis.nlp.struct.TaggedToken;
import documentAnalysis.nlp.utils.POSTagConverter;

public class Filter {
	
	/**
	 * This method used to get the wordList<TaggedToken>
	 * from the dirFile folder.
	 * @param dirFilePath
	 * @return ArrayList<TaggedToken> wordList
	 */
	public static ArrayList<TaggedToken> getWordsList(String dirFilePath) {
		
		//create a list to save the return information
		ArrayList<TaggedToken> word_list = new ArrayList<TaggedToken>();
		File dir = new File(dirFilePath);
		
		if(dir.exists()) {
			if(dir.isDirectory()) {
				
				//setting the lemmatizationEnabled and posTaggingEnabled to true to get the data
				NLPPipeline pipeline = new NLPPipeline(true, true); 
				try {
					for(Document doc : pipeline.process(dirFilePath)) {
						for(Sentence sentence : doc) {
							//setence_list.add(sentence);
							for (TaggedToken tt : sentence) {
								word_list.add(tt);
							}
						}
					}
				} catch (TextProcessingException e) {
					e.printStackTrace();
				}
				
			}else{
				System.err.println(dirFilePath + " is not a directory.");
			}
		}else{
			System.err.println(dirFilePath + " cannot be found.");
		}
		
		return word_list;
	}
	
	
	/**
	 * This method used to get the total string based on the TaggedToken words,
	 * specially, the string only contains the POS information
	 * @param wordList
	 * @return String totalString
	 */
	public static String getTotalPOS(ArrayList<TaggedToken> wordList) {
		
		String totalString = "";
		
		for (TaggedToken tt : wordList) {
			totalString = totalString + tt.getPos();
		}
		return totalString;
		
	}
	
	/**
	 * This method used to get the total string based on the TaggedToken words,
	 * specially, the string only contains the word form information
	 * @param wordList
	 * @return String totalString
	 */
	public static String getTotalWordForm(ArrayList<TaggedToken> wordList) {
		
		String totalString = "";
		for (TaggedToken tt : wordList) {
			totalString = totalString + tt.getWordForm();
		}
		return totalString;
	}
	
	/**
	 * This method used to make the TaggedToken word in the wordList has the
	 * simple POS. e.g. make the "IN" into "P" 
	 * @param wordList
	 */
	public static void simpleWordPOS(ArrayList<TaggedToken> wordList) {
		
		for (TaggedToken tt : wordList) {
			//use the POSTagConverter.map to make tt's POS simple
			tt.setPos(POSTagConverter.map(tt.getPos()));
		}
		
	}
	
	/**
	 * This method used to order the Terms based on their length
	 * and their frequency
	 * 
	 * @return rankedTerm
	 */
	public static ArrayList<Term> getTermRank(ArrayList<Term> terms) {
		ArrayList<Term> rankedTerm = new ArrayList<Term>();
		List<ComparableObj<Term,Integer>> rank  = new ArrayList<ComparableObj<Term,Integer>>();
		
		for (Term term: terms) {
			rank.add(new ComparableObj<Term, Integer>(term,term.contextTaggedToken.size()));
		}
		Collections.sort(rank);
		
		//get the terms in the rank
		for (ComparableObj<Term, Integer> co : rank) {
			rankedTerm.add(co.getComObject());
		}
		return rankedTerm;
	}
	
	/**
	 * This method used to rank the term by their C value
	 * 
	 * @param terms
	 * @return rankedTerm
	 */
	public static ArrayList<Term> getCvalueRank(ArrayList<Term> terms) {
		ArrayList<Term> rankedTerm = new ArrayList<Term>();
		List<ComparableObj<Term, Float>> rank  = new ArrayList<ComparableObj<Term,Float>>();
		
		for (Term term: terms) {
			if (term.C_value > 2 || term.C_value == 2) {
				rank.add(new ComparableObj<Term, Float>(term,term.C_value));
			}
		}
		Collections.sort(rank);
		
		//get the terms in the rank
		for (ComparableObj<Term, Float> co : rank) {
			rankedTerm.add(co.getComObject());
		}
		return rankedTerm;
	}
	
	/**
	 * This method used to delete the duplicate term in the term list
	 * 
	 * @param terms
	 * @return
	 */
	public static HashMap<String, Term> filterDuplicTerm(ArrayList<Term> terms) {
		ArrayList<Term> term = new ArrayList<Term>();
		
		//consider of the hashMap cannot save the key-value pair with the same key
		HashMap<String, Term> map = new HashMap<String, Term>();
		
		//save all the terms into the hashMap
		for (Term te : terms) {
			map.put(te.ShowTerm(te.contextTaggedToken), te);
		}
		
		return map;
	}
	
	/**
	 * This method used to filter terms by the term frequency
	 * @return term
	 */
	public static ArrayList<Term> filteFrequency(ArrayList<Term> terms) {
		ArrayList<Term> term = new ArrayList<Term>();
		
		//this time, set the frequency as the 2
		for (Term te : terms) {
			if (te.frequency > 0) {
				term.add(te);
			} else {
				continue;
			}
		}
		return term;
	}
	
	/**
	 * This method used to filter the one word term
	 * @param terms
	 * @return term
	 */
	public static ArrayList<Term> filterTermByLength(ArrayList<Term> terms) {
		ArrayList<Term> term = new ArrayList<Term>();
		
		//this time, set the term length as the 2
				for (Term te : terms) {
					if (te.getContextTaggedToken().size() == 1) {
						continue;
					} else {
						term.add(te);
					}
				}
		
		return term;
	}
	
	/**
	 * This method used to filter the word by apply stopWord list
	 * 
	 * @param wordList
	 * @param indexList
	 * @return returnList
	 */
	public static ArrayList<Term> applyStopList(ArrayList<Term> terms
			, Set<String> stopList) {
		
		ArrayList<Term> term = new ArrayList<Term>();
		
		//when the term have the words in the stop list, it should be deleted
		for (Term elements : terms) {
			boolean boolean_value = true; //used to filter term
			
			//when find one words of the term in the stoplist, not save it
			for (TaggedToken tt : elements.contextTaggedToken) {
				if (stopList.contains(tt.getWordForm())) {
					boolean_value = false;
					break;
				}
			}
			if (boolean_value) {
				term.add(elements);
			}
		}
		return term;
	}

}
