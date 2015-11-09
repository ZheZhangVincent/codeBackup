package Voting;

import java.util.*;

import ass1.PollingPlace;

public class Division {
	String DivisionNm;
	String PartyNm;
	int winer_vote;
	double winer_percent;
	
	public Division (String DivisionNm, String PartyNm, int winer_vote, double winer_percent) {
		this.DivisionNm = DivisionNm;
		this.PartyNm = PartyNm;
		this.winer_vote = winer_vote;
		this.winer_percent = winer_percent;
	}

	public static void main (String args[]) {
		Division.getData();
	}

	public static List<Division> getData() {
		List<Division> datalines = new ArrayList<Division>();
		List<List> line = ElectionScrutinizer.readFile("HouseSeatSummaryDownload-17496.csv");
		
		for (int index = 0; index < line.size(); index++) {
			Division pp = new Division(line.get(index).get(1), line.get(index).get(3), Integer.parseInt((String) line.get(index).get(6)), line.get(index).get(line.get(index).size() - 2), Double.parseDouble(line.get(index).get((line.get(index)).size() - 1)));
			dataLines.add(pp);
		}
		
		return datalines;
	}
	
	public static void integrityTest(String[] csvFiles) {
		
	}
}
