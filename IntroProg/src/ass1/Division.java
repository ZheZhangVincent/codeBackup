package ass1;

import java.text.DecimalFormat;
import java.util.*;

import ass1.PollingPlace;

public class Division {
	String DivisionNm;
	String PartyNm;
	int winer_vote;
	String winer_percent;
	
	public Division (String DivisionNm, String PartyNm, int winer_vote, String winer_percent) {
		this.DivisionNm = DivisionNm;
		this.PartyNm = PartyNm;
		this.winer_vote = winer_vote;
		this.winer_percent = winer_percent;
	}

	public static void main (String args[]) {
	}

	public static List<Division> getHouseSeatSummaryDownload() {
		List<Division> datalines = new ArrayList<Division>();
		List<List<String>> line = ElectionScrutinizer.readFile("HouseSeatSummaryDownload-17496.csv");
		for (int index = 0; index < line.size(); index++) {
			Division pp = new Division(line.get(index).get(1), line.get(index).get(4), Integer.parseInt(line.get(index).get(5)),line.get(index).get(7));
			datalines.add(pp);
		}
		return datalines;
	}
	
	public static boolean integritySummaryDownload(String[] csvfiles) {
		Boolean bool_value = true;
		List<PollingPlace> data1 = PollingPlace.GetHouseTcpFlowByPPDownloadData(csvfiles);
		List<PollingPlace> data2 = PollingPlace.GetHouseTcpFlowByPPDownloadData(csvfiles);
		List<Division> data3 = Division.getHouseSeatSummaryDownload();
		
		for (int index1 = 0; index1 < data1.size(); index1++) {
			double temp_num = 0;
			double total_num = 0;
			if (data1.get(index1).FromCandidateSurname.equals("First Preferences")) {
				for (int index2 = 0; index2 < data1.size(); index2++) {
					total_num = total_num + data2.get(index2).Value;
					if (data2.get(index2).PartyNm.equals(data1.get(index1).PartyNm)) {
						temp_num = temp_num + data2.get(index2).Value;
					}
				}
				DecimalFormat df = new DecimalFormat("0.00");
				String num_percent = df.format(((temp_num/total_num) * 100));
				for (int index3 = 0; index3 < data3.size(); index3++) {
					String[] list1 =  (data1.get(index1).PartyNm).split(" ");
					String[] list2 =  (data3.get(index3).PartyNm).split(" ");
					int temp_count = 0;
					for (String str : list2) {
						for (int index = 0; index < list1.length; index++) {
							if (str.equals(list1[index])) {
								temp_count++;
							}
						}
					}
					if (data3.get(index3).DivisionNm.equals(data1.get(index1).DivisionNm)) {
						if (temp_count == list2.length) {
							if (data3.get(index3).winer_vote != temp_num || !(data3.get(index3).winer_percent.equals(num_percent))) {
								System.out.println("There is mistake for " + data3.get(index3).DivisionNm + " of " + data3.get(index3).PartyNm + ".");
								return false;
							}
						}
					}
				}
			}
			else {
				break;
			}
		}
		return bool_value;
	}
}
