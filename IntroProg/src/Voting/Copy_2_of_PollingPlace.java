package Voting;

import java.util.*;

public class Copy_2_of_PollingPlace {
	
	public String PPNm;
	public int CountNum;
	public String PartyNm;
	public String CalculationType;
	public int Value;
	public String FromCandidateSurname;

	public static void main (String args[]) {
		Copy_2_of_PollingPlace.GetHouseDopByPPDownloadData();
		Copy_2_of_PollingPlace.GetHouseTcpFlowByPPDownloadData();
	}
	
	public Copy_2_of_PollingPlace(String PPNm, int CountNum, String PartyNm, String CalculationType, int CalculationValue) {
		this.PPNm = PPNm;
		this.CountNum = CountNum;
		this.PartyNm = PartyNm;
		this.CalculationType = CalculationType;
		this.Value = CalculationValue;
	}
	
	public Copy_2_of_PollingPlace(String PPNm, String FromCandidateSurname, String PartyNm, int Value) {
		this.PPNm = PPNm;
		this.FromCandidateSurname = FromCandidateSurname;
		this.PartyNm = PartyNm;
		this.Value = Value;
	}
	
	private static List<Copy_2_of_PollingPlace> GetHouseDopByPPDownloadData() {
		List<Copy_2_of_PollingPlace> dataLines = new ArrayList<Copy_2_of_PollingPlace>();
		List<List<String>> line = ElectionScrutinizer.readFile("HouseDopByPPDownload-17496-NSW-EMON.csv");
		System.out.println(line.get(line.size()-1).get(0));
		//System.out.println(line.size());
		for (int index = 0; index < line.size(); index++) {
			List<String> list1 = line.get(line.size()-1);
			for(String str : list1) 
//				System.out.println(str);
			
//			PollingPlace pp = new PollingPlace(line.get(index).get(4), Integer.parseInt(line.get(index).get(5)), line.get(index).get(11), line.get(index).get(line.size()-2), Integer.parseInt(line.get(index).get(line.size()-1)));
//			dataLines.add(pp);
		}
		return dataLines;
	}
	
	public static List<Copy_2_of_PollingPlace> GetHouseTcpFlowByPPDownloadData() {
		List<Copy_2_of_PollingPlace> dataLines = new ArrayList<Copy_2_of_PollingPlace>();
		List<List<String>> line = ElectionScrutinizer.readFile("HouseTcpFlowByPPDownload-17496-NSW-EMON.csv");
		for (int index = 0; index < line.size(); index++) {
			Copy_2_of_PollingPlace pp = new Copy_2_of_PollingPlace(line.get(index).get(4), line.get(index).get(8), line.get(index).get(13), Integer.parseInt(line.get(index).get(line.size()-2)));
			dataLines.add(pp);
		}
		return dataLines;
	}
	
	public static Boolean integrityTestDop () {
		Boolean total_bool = true;
		List<Copy_2_of_PollingPlace> data1 = Copy_2_of_PollingPlace.GetHouseDopByPPDownloadData();
		List<Copy_2_of_PollingPlace> data2 = Copy_2_of_PollingPlace.GetHouseDopByPPDownloadData();
		for (int index1 = 0; index1 < data1.size(); index1++) {
			int pre_count;
			int gap_count;
			for (int index2 = 0; index2 < data2.size(); index2++) {
				if (data1.get(index1).PartyNm.equals(data2.get(index2).PartyNm) && data1.get(index1).PPNm.equals(data2.get(index2).PPNm)) {
					if (data1.get(index1).CountNum == data2.get(index2).CountNum) {
						if (data1.get(index1).CalculationType.equals("Preference Count") && data2.get(index2).CalculationType.equals("Transfer Count")) {
							pre_count = data1.get(index1).Value;
						}
						if (data1.get(index1).CountNum == data2.get(index2).CountNum - 1) {
							if (data1.get(index1).Value - data2.get(index2).Value) {
								
							}
						}
					}											
				}
			}
		}
		return total_bool;
	}
	
	public static void integrityTestTcp () {
		
	}
	
	
	
	public static void integrityTest() {
		System.out.print("loading the disturbution of preference set ... checking---");
		Boolean total_bool = true;
		for (Copy_2_of_PollingPlace pp : Copy_2_of_PollingPlace.GetData()) {		
		
			Set<String> set = pp.pas_count_map.keySet();
			
			for (String key : set) {
				if (! (pp.pas_count_map.get(key).equals(pp.gap_count_map.get(key)))) {
					//System.out.println("The data for " + pp.PPNm + " at count " + pp.CountNum + " for " + key + " have problems.");
					Boolean tem_bool = false;
					total_bool = total_bool && tem_bool;
				}
			}
		}	
		if (total_bool) {
			System.out.println(" the data is consistent");
		}
		
		
		
		//System.out.print("loading the HoR seats data set, checking the total count ...");
		//Division.integrityTest(csvFiles);
		//System.out.println(" correct");
	}
}

