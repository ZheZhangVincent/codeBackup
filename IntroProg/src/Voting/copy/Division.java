package Voting.copy;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Division {
	String StateAb;
	String PartyAb;
	String PartyNm;
	int PreferenceCount;
	int VoteCount;
	
	public static void main (String args[]) {
		Division div = new Division();
		for (Division data : div.ReadDivisionData()) {
		System.out.println(data.VoteCount);
		}
	}
	
	private static List<Division> ReadDivisionData() {
		String csvFile = "./HouseTcpFlowByPPDownload-17496/HouseTcpFlowByPPDownload-17496-NSW-EMON.csv";
		String line = "";
		List<Division> dataLines = new ArrayList<Division>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(csvFile));
			br.readLine();
			br.readLine();
			while ((line = br.readLine()) != null) {
			String[] items = line.trim().split("\",\"");
			Division data = new Division();
			data.StateAb = items[0].replaceAll("\"", "");
			data.PartyAb = items[12];
			data.PreferenceCount =  (int) Double.parseDouble(items[17].replaceAll("\"", ""));
			dataLines.add(data);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return dataLines;
	}

}
