package NCValue;

import java.util.HashSet;
import java.util.Set;


/**
 * This class used to save the context word information
 * 
 * @author masai
 *
 */
public class ContextWord {
	
	public Set<String> TermNames;
	public double weight;
	public String contextWord;
	public Integer t_w;
	
	public ContextWord(String contextWord) {
		this.contextWord = contextWord;
		this.TermNames = new HashSet<String>();
		this.weight = 0;
		this.t_w = 0;
	}
	
	public void setWeight(double weight) {
		this.weight = weight;
	}
	
	/**
	 * This method used to change the t(w) number
	 * 
	 */
	public void changeT_W(String termName) {
		if (!TermNames.contains(termName)) {
			TermNames.add(termName);
			t_w = t_w + 1; //when find one more with the same Term
		}
	}
	

}
