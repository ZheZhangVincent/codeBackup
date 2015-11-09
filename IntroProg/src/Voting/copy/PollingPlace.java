package Voting.copy;

import java.util.*;

public class PollingPlace {
	
	public String PPNm;
	public int CountNum;
	public String PartyAb;
	public String SittingMemberFl;
	public String CalculationType;
	public double CalculationValue;
	public double ShortTransferCount;
	public Map<String ,Integer> pre_count_map = new HashMap<String ,Integer>();
	public Map<String ,Integer> gap_count_map = new HashMap<String ,Integer>();
	static String csvFile = "./HouseDopByPPDownload-17496/HouseDopByPPDownload-17496-NSW-EMON.csv";
	
	public static void main (String args[]) {
		PollingPlace pp = new PollingPlace();
		pp.integrityTest(csvFile);
	}
	
	private static List<PollingPlace> ReadPPFile(String csvFile) {
		
		List<PollingPlace> dataLines = new ArrayList<PollingPlace>();
		List<LineInformation> temp_array = new ArrayList<LineInformation>();
		for (String line : ElectionScrutinizer.readFile(csvFile)) {
			List<String> temp_list = new ArrayList<String>();
			StringTokenizer token = new StringTokenizer(line,",");
			while (token.hasMoreTokens()) {
				temp_list.add(token.nextToken());
			}
			LineInformation data = new LineInformation(temp_list);
			temp_array.add(data);
		}
		Map<String ,Integer> tem_pre_count_map = new HashMap<String ,Integer>();
		Map<String ,Integer> tem_ter_count_map = new HashMap<String ,Integer>();
		for (int index = 0; index < (temp_array.size() - 1); index++) {
			LineInformation temp1 = temp_array.get(index);
			LineInformation temp2 = temp_array.get(index + 1);
			if (temp1.PPNm.equals(temp2.PPNm)) {
				if (temp1.CountNum == temp2.CountNum) {
					if (temp1.CalculationType.equals("Preference Count")) {
						tem_pre_count_map.put(temp1.PartyAb, (int) temp1.CalculationValue);
					}
					if (temp1.CalculationType.equals("Transfer Count")) {
						tem_ter_count_map.put(temp1.PartyAb, (int) temp1.CalculationValue);
					}
				}
				else {
					PollingPlace pp = new PollingPlace();
					pp.PPNm = temp1.PPNm;
					pp.PartyAb = temp1.PartyAb;
					pp.CountNum = temp1.CountNum;
					pp.pre_count_map = tem_pre_count_map;
					Set<String> set = tem_ter_count_map.keySet();
					for (String temp : set) {
						if (tem_pre_count_map.get(temp) != null){
							tem_ter_count_map.put(temp, tem_pre_count_map.get(temp).intValue() - tem_ter_count_map.get(temp).intValue());
						}
					}
					pp.gap_count_map = tem_ter_count_map;
					dataLines.add(pp);
					tem_pre_count_map = null;
					tem_ter_count_map = null;
				}
			}
		}
		return dataLines;
	}
	
	public static List<PollingPlace> ReadTFFile(String csvFile) {
		List<PollingPlace> dataLines = new ArrayList<PollingPlace>();
		for (String line : ElectionScrutinizer.readFile(csvFile)) {
			List<String> temp_list = new ArrayList<String>();
			StringTokenizer token = new StringTokenizer(line,",");
			while (token.hasMoreTokens()) {
				temp_list.add(token.nextToken());
			}
			PollingPlace data = new PollingPlace();
			data.PPNm = temp_list.get(4).replaceAll("\"", "");
			data.PartyAb = temp_list.get(12).replaceAll("\"", "");
			data.ShortTransferCount =  Double.parseDouble(temp_list.get(17).replaceAll("\"", ""));
			dataLines.add(data);
		}
		return dataLines;
	}
	
	public static void integrityTest(String csvFile) {
		for (int index = 0; index < (PollingPlace.ReadTFFile(csvFile).size() - 1); index++) {
			PollingPlace temp1 = new PollingPlace();
			PollingPlace temp2 = new PollingPlace();
			while (temp1.PPNm.equals(temp2.PPNm) && temp1.CountNum == (temp2.CountNum - 1)) {
				Set<String> set = (temp1.pre_count_map).keySet();
				for (String temp : set) {
					if ((temp2.gap_count_map).get(temp) != null){
						if (((temp1.pre_count_map).get(temp)).intValue() == (temp2.gap_count_map).get(temp).intValue()) {
						}
						else {
							System.out.println("The data at the " + temp1.PPNm + " at the " + temp1.CountNum +  "for " + temp + " have mistake.");
						}
					}
				}
			}
		}
	}
	
	public static class LineInformation {
		public String PPNm;
		public int CountNum;
		public String PartyAb;
		public String SittingMemberFl;
		public String CalculationType;
		public double CalculationValue;
		public double ShortTransferCount;
		
		public LineInformation(List<String> list) {
			this.PPNm = list.get(4).replaceAll("\"", "");
			this.CountNum = Integer.parseInt(list.get(5).replaceAll("\"", ""));
			this.PartyAb = list.get(10).replaceAll("\"", "");
			this.SittingMemberFl = list.get(12).replaceAll("\"", "");
			this.CalculationType = list.get(13).replaceAll("\"", "");
			this.CalculationValue =  Double.parseDouble(list.get(14).replaceAll("\"", ""));
		}
	}
}

