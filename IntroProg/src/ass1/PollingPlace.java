package ass1;

import java.util.*;

public class PollingPlace {
	
	public String PPNm;
	public int CountNum;
	public String PartyNm;
	public String CalculationType;
	public double Value;
	public String FromCandidateSurname;
	public String DivisionNm;

	public static void main (String args[]) {
	}
	
	public PollingPlace(String PPNm, int CountNum, String PartyNm, String CalculationType, double CalculationValue) {
		this.PPNm = PPNm;
		this.CountNum = CountNum;
		this.PartyNm = PartyNm;
		this.CalculationType = CalculationType;
		this.Value = CalculationValue;
	}
	
	public PollingPlace(String PPNm, String FromCandidateSurname, String PartyNm, double Value, String DivisionNm) {
		this.PPNm = PPNm;
		this.FromCandidateSurname = FromCandidateSurname;
		this.PartyNm = PartyNm;
		this.Value = Value;
		this.DivisionNm = DivisionNm;
	}
	
	private static List<PollingPlace> GetHouseDopByPPDownloadData(String[] csvfiles) {
		List<PollingPlace> dataLines = new ArrayList<PollingPlace>();
		List<List<String>> line = ElectionScrutinizer.readFile(csvfiles[0]);
		for (int index = 0; index < line.size(); index++) {
			PollingPlace pp = new PollingPlace(line.get(index).get(4), Integer.parseInt(line.get(index).get(5)), line.get(index).get(11), line.get(index).get(line.get(index).size() - 2), Double.parseDouble(line.get(index).get((line.get(index)).size() - 1)));
			dataLines.add(pp);
		}
		return dataLines;
	}
	
	public static List<PollingPlace> GetHouseTcpFlowByPPDownloadData(String[] csvfiles) {
		List<PollingPlace> dataLines = new ArrayList<PollingPlace>();
		List<List<String>> line = ElectionScrutinizer.readFile(csvfiles[1]);
		for (int index = 0; index < line.size(); index++) {
			PollingPlace pp = new PollingPlace(line.get(index).get(4), line.get(index).get(8), line.get(index).get(13),  Double.parseDouble(line.get(index).get((line.get(index)).size() - 2)), line.get(index).get(2));
			dataLines.add(pp);
		}
		return dataLines;
	}
	
	public static Boolean integrityTestDop (String[] csvfiles) {
		Boolean bool_value = true;
		List<PollingPlace> data1 = PollingPlace.GetHouseDopByPPDownloadData(csvfiles);
		List<PollingPlace> data2 = PollingPlace.GetHouseDopByPPDownloadData(csvfiles);
		for (int index1 = 0; index1 < data1.size(); index1++) {
			double num1 = 0;
			double num2 = 0;	
			if (data1.get(index1).CalculationType.equals("Preference Count")) {
				for (int index2 = index1; index2 < data2.size(); index2++) {
					if (data1.get(index1).PartyNm.equals(data2.get(index2).PartyNm) && data1.get(index1).PPNm.equals(data2.get(index2).PPNm) && data1.get(index1).CountNum == data2.get(index2).CountNum - 1) {
						if (data2.get(index2).CalculationType.equals("Preference Count")) {
							num1 = data2.get(index2).Value;
						}
						if (data2.get(index2).CalculationType.equals("Transfer Count")) {
							num2 = data2.get(index2).Value;
							
							if (data1.get(index1).Value != num1 - num2) {
								System.out.println("There is mistake for " + data2.get(index2).PPNm + " at round " + data2.get(index2).CountNum + ".");
								return false;
							} 
						}
					}		
				}
			}	
		}
		return bool_value;
	}
	
	public static Boolean integrityTestTcp (String[] csvfiles) {
		Boolean bool_value = true;
		List<PollingPlace> data1 = PollingPlace.GetHouseTcpFlowByPPDownloadData(csvfiles);
		List<PollingPlace> data2 = PollingPlace.GetHouseTcpFlowByPPDownloadData(csvfiles);
		List<PollingPlace> data3 = PollingPlace.GetHouseDopByPPDownloadData(csvfiles);
		int max_count = 0;
		
		for (int index3 = 0; index3 < data3.size() - 1; index3++) {
			if (data3.get(index3).CountNum > data3.get(index3 + 1).CountNum) {
				max_count = data3.get(index3).CountNum;
				break;
			}
		}
		
		for (int index1 = 0; index1 < data1.size(); index1++) {
			Double total_value = 0.0;
			if (data1.get(index1).FromCandidateSurname.equals("First Preferences")) {
				for (int index2 = 0; index2 < data2.size(); index2++) {
					if (data2.get(index2).PartyNm.equals(data1.get(index1).PartyNm) && data2.get(index2).PPNm.equals(data1.get(index1).PPNm)) {
						total_value = total_value + data2.get(index2).Value;
					}
				}
				for (int index3 = 0; index3 < data3.size(); index3++) {
					if (data3.get(index3).PPNm.equals(data1.get(index1).PPNm) && data3.get(index3).PartyNm.equals(data1.get(index1).PartyNm) && data3.get(index3).CountNum == max_count && data3.get(index3).CalculationType.equals("Preference Count")) {
						if (total_value == data3.get(index3).Value) {
							break;
						}
						else {
							System.out.println("There is mistake for " + data3.get(index3).PPNm + " at round " + data3.get(index3).CountNum + ".");
							return false;
						}
					}
				}
			}
		}
		return bool_value;
	}
	
	public static void integrityTest(String[] csvfiles) {
		System.out.print("loading the disturbution of preference set ... checking ---");
		if (PollingPlace.integrityTestDop (csvfiles)) {
			System.out.println(" the data is consistent");
		}
		System.out.print("loading the disturbution of preference set ... checking ---");
		if (PollingPlace.integrityTestTcp (csvfiles)) {
			System.out.println(" the data is consistent");
		}
		
		System.out.print("loading the HoR seats data set, checking the total count ...");
		if (Division.integritySummaryDownload(csvfiles)) {
			System.out.println(" correct");
		}
	}
}

