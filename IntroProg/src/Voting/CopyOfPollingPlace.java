package Voting;

import java.util.*;

public class CopyOfPollingPlace {
	
	public String PPNm;
	public int CountNum;
	public Map pas_count_map;
	public Map gap_count_map;
	public Map per_count_map;
	public Map FinalCountNum;

	public static void main (String args[]) {
		CopyOfPollingPlace.integrityTest();
	}
	
	public CopyOfPollingPlace(String string1, int int1, Map pre_map, Map pas_map, Map gap_map, Map temp_map) {
		this.PPNm = string1;
		this.CountNum = int1;
		this.per_count_map = pre_map;
		this.pas_count_map = pas_map;
		this.gap_count_map = gap_map;
		this.FinalCountNum = (Map) temp_map.get(string1);
	}
	
	private static List<CopyOfPollingPlace> GetData() {
		//read HouseTcpFlowByPPDownload style files
		List<CopyOfPollingPlace> dataLines = new ArrayList<CopyOfPollingPlace>();
		List<List> datafromfile = ElectionScrutinizer.readFile("HouseTcpFlowByPPDownload-17496-NSW-EMON.csv");
		String candidate_name1 = null;
		int candidate_num1 = 0;
		String candidate_name2 = null;
		int candidate_num2 = 0;
		for (int index = 0; index < (datafromfile.size() - 1); index++) {
			if (((String) datafromfile.get(index).get(8)).equals("First Preferences") && ((String) datafromfile.get(index + 1).get(8)).equals("First Preferences")) {
				candidate_name1 = (String) (datafromfile.get(index)).get(11);
				candidate_name2 = (String) (datafromfile.get(index + 1)).get(11);
				break;
			}
		}
		Map<String, Map> temp_map = new HashMap<String, Map>();
		Map temp_submap = new HashMap();
		for (int index = 0; index < (datafromfile.size() - 1); index++) {
			int temp_num = Integer.parseInt((String) (datafromfile.get(index)).get(datafromfile.get(index).size() - 2));
			if (((String) datafromfile.get(index).get(11)).equals(candidate_name1)) {
				candidate_num1 = candidate_num1 + temp_num;
			}
			if (((String) datafromfile.get(index).get(11)).equals(candidate_name2)) {
				candidate_num2 = candidate_num2 + temp_num;
			}
			if (! datafromfile.get(index).get(4).equals(datafromfile.get(index + 1).get(4)) || index == (datafromfile.size() - 2)) {
				temp_submap.put(candidate_name1, candidate_num1);
				temp_submap.put(candidate_name2, candidate_num2);
				Map save_map = new HashMap(temp_submap);
				temp_map.put(((String)datafromfile.get(index).get(4)), save_map);
				temp_submap.clear();
				candidate_num1 = 0;
				candidate_num2 = 0;
			}
		}
		//read HouseDopByPPDownload style files
		datafromfile = ElectionScrutinizer.readFile("HouseDopByPPDownload-17496-NSW-EMON.csv");
		List<List> data_pp = new ArrayList<List>();
		Map pre_map = new HashMap();
		Map ter_map = new HashMap();
		Map gap_map = new HashMap();
		Map pas_map = new HashMap();
		for (int index = 0; index < (datafromfile.size() - 1); index++) {
			if (((datafromfile.get(index)).get(5)).equals((datafromfile.get(index + 1)).get(5))) {
				if ((datafromfile.get(index)).get(datafromfile.get(index).size() - 2).equals("Preference Count")) {
					pre_map.put((datafromfile.get(index)).get(10), (datafromfile.get(index)).get(datafromfile.get(index).size() - 1));
				}
				if ((datafromfile.get(index)).get(datafromfile.get(index).size() - 2).equals("Transfer Count")) {
					ter_map.put((datafromfile.get(index)).get(10), (datafromfile.get(index)).get(datafromfile.get(index).size() - 1));
				}
			}
			else {
				Set<String> set = pre_map.keySet();
				for (String key : set) {
					if (pre_map.get(key) != null){
						gap_map.put(key, (Integer.parseInt((String) pre_map.get(key)) - Integer.parseInt((String) ter_map.get(key))));
					}
				}
				if (pas_map.isEmpty()) {
					pas_map = new HashMap(pre_map);
				}

				CopyOfPollingPlace pp = new CopyOfPollingPlace(((String) (datafromfile.get(index)).get(4)), (Integer.parseInt((String) (datafromfile.get(index)).get(5))), pre_map, pas_map, gap_map, temp_map);
				dataLines.add(pp);
				pas_map = new HashMap(pre_map);
			}
		}
		
		return dataLines;
	}
	
	public static void integrityTest() {
		System.out.print("loading the disturbution of preference set ... checking---");
		Boolean total_bool = true;
		for (CopyOfPollingPlace pp : CopyOfPollingPlace.GetData()) {		
		
			Set<String> set = pp.pas_count_map.keySet();
			
			for (String key : set) {
				if (! (pp.pas_count_map.get(key).equals(pp.gap_count_map.get(key)))) {
					System.out.println("The data for " + pp.PPNm + " at count " + pp.CountNum + " for " + key + " have problems.");
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

