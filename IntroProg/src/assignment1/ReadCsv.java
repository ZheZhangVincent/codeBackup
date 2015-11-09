package assignment1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;

public class ReadCsv {

	public static void main(String[] args) {
		 
		ReadCsv obj = new ReadCsv();
		obj.pollingplace(); 
	  }

	private int max_column_num = 0;
	HashMap columnTitle = new HashMap();	 
	  public void pollingplace() {
	 
		String csvFile = "./HouseDopByPPDownload-17496/HouseDopByPPDownload-17496-NSW-EMON.csv";
		BufferedReader br = null;
		String line = "";
		ArrayList PPNm_list = new ArrayList();
		ArrayList PartyAb_list = new ArrayList();
		ArrayList CountNum_list = new ArrayList();
		String PPNm = null;
		String PartyAb = null;
		int PreCount = 0;
		int TransCount = 0;
		
		//in order to avoid the information include comma and has been divided
		String cvsSplitBy = "\",\"";
	 
		try {
			//this block aims to get the length of the column titles in each rows
			br = new BufferedReader(new FileReader(csvFile));
			
			while ((line = br.readLine()) != null) {
				String[] items = line.split(cvsSplitBy);
				if (max_column_num < items.length)
				{
					max_column_num = items.length;
				}				
			}
			
			//this block aims to get the relationship between column titles and numbers
			//and the column title which not contains ""
			br = new BufferedReader(new FileReader(csvFile));			
			String[] max_items = null;
			
			while ((line = br.readLine()) != null) {
				String[] items = line.split(",");
				max_items = items;
				if (items.length == max_column_num) break;
			}			
			for (int index = 0; index < max_items.length; index++) {
				columnTitle.put(max_items[index],index);
			}			
			
			//this block aims to save the data from file based on the useful column title
			br = new BufferedReader(new FileReader(csvFile));
			
			while ((line = br.readLine()) != null) {				
				String[] items = line.split(cvsSplitBy);
				
				//get the lists of PPNm, PartyAb and CountNum
				if (items.length == max_column_num){					
					PPNm_list.add(items[(int) columnTitle.get("PPNm")].replaceAll("\"", ""));
					PartyAb_list.add(items[(int) columnTitle.get("PartyAb")].replaceAll("\"", ""));
					CountNum_list.add(items[(int) columnTitle.get("CountNum")].replaceAll("\"", ""));					
				}				
			}
			
			//then remove the same elements in PPNm_list, PartyAb_list and CountNum_list
			HashSet a = new HashSet(PPNm_list); 
			PPNm_list.clear();  
			PPNm_list.addAll(a);
			
			HashSet b = new HashSet(PartyAb_list); 
			PartyAb_list.clear();  
			PartyAb_list.addAll(b);

			HashSet c = new HashSet(CountNum_list); 
			CountNum_list.clear();  
			CountNum_list.addAll(c);
			
			//this block aims to get the initial data
			br = new BufferedReader(new FileReader(csvFile));
			
			PollingPlace pp = new PollingPlace();
			
			while ((line = br.readLine()) != null) {
				
				String[] items = line.split(cvsSplitBy);
				
				if (items.length == max_column_num){
				
				//get the initial value of variables in PollingPlace class
				PPNm = items[(int) columnTitle.get("PPNm")].replaceAll("\"", "");
				PartyAb = items[(int) columnTitle.get("PartyAb")].replaceAll("\"", "");
				break;
				}
			}
			
			//creates two ArrayLists to stores the information on count_num in each count
			ArrayList PreCount_list = new ArrayList();
			ArrayList Change_list = new ArrayList();
			
				//this block aims to get the initial data
				br = new BufferedReader(new FileReader(csvFile));
				while ((line = br.readLine()) != null) {
					
					String[] items = line.split(cvsSplitBy);
					
					if (items.length == max_column_num){
						
						//at the same polling place
						if (items[(int) columnTitle.get("PPNm")].replaceAll("\"", "").equals(PPNm)) {
							
							//at the same PartyAb
							if (items[(int) columnTitle.get("PartyAb")].replaceAll("\"", "").equals(PartyAb)) {
								
								for (int count = 0; count < CountNum_list.size(); count++) {
									if (Integer.parseInt(items[(int) columnTitle.get("CountNum")].replaceAll("\"", "")) == count 
											&& items[(int) columnTitle.get("CalculationType")].replaceAll("\"", "").equals("Preference Count")) {
										PreCount = Integer.parseInt(items[(int)columnTitle.get("CalculationValue")].replaceAll("\"", ""));
										
										PreCount_list.add(PreCount);
									}
									else if (Integer.parseInt(items[(int) columnTitle.get("CountNum")].replaceAll("\"", "")) == count 
											&& items[(int) columnTitle.get("CalculationType")].replaceAll("\"", "").equals("Transfer Count")) {
										TransCount = Integer.parseInt(items[(int)columnTitle.get("CalculationValue")].replaceAll("\"", ""));
										int gap = PreCount - TransCount;
										Change_list.add(gap);
									}
								}
								
							} else {
								PartyAb = items[(int) columnTitle.get("PartyAb")];
								
								for (int count = 0; count < CountNum_list.size(); count++) {
									if (Integer.parseInt(items[(int) columnTitle.get("CountNum")].replaceAll("\"", "")) == count 
											&& items[(int) columnTitle.get("CalculationType")].replaceAll("\"", "").equals("Preference Count")) {
										PreCount = Integer.parseInt(items[(int)columnTitle.get("CalculationValue")].replaceAll("\"", ""));
										
										PreCount_list.add(PreCount);
									}
									else if (Integer.parseInt(items[(int) columnTitle.get("CountNum")].replaceAll("\"", "")) == count 
											&& items[(int) columnTitle.get("CalculationType")].replaceAll("\"", "").equals("Transfer Count")) {
										TransCount = Integer.parseInt(items[(int)columnTitle.get("CalculationValue")].replaceAll("\"", ""));
										//int gap = PreCount - TransCount;
										Change_list.add(TransCount);
									}
								}
							}

						} else {
							PPNm = items[(int) columnTitle.get("PPNm")];
							
							for (int count = 0; count < CountNum_list.size(); count++) {
								if (Integer.parseInt(items[(int) columnTitle.get("CountNum")].replaceAll("\"", "")) == count 
										&& items[(int) columnTitle.get("CalculationType")].replaceAll("\"", "").equals("Preference Count")) {
									PreCount = Integer.parseInt(items[(int)columnTitle.get("CalculationValue")].replaceAll("\"", ""));
									
									PreCount_list.add(PreCount);
								}
								else if (Integer.parseInt(items[(int) columnTitle.get("CountNum")].replaceAll("\"", "")) == count 
										&& items[(int) columnTitle.get("CalculationType")].replaceAll("\"", "").equals("Transfer Count")) {
									TransCount = Integer.parseInt(items[(int)columnTitle.get("CalculationValue")].replaceAll("\"", ""));
									int gap = PreCount - TransCount;
									Change_list.add(gap);
								}
								
							}
						}
				
					}
			
				}
				
				System.out.println(PreCount_list);
				System.out.println(Change_list);
				//after collect one party at one polling place, the information on counts changing, test the same
				

				
				
		//provides the information on special error in import files
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
		
	 
		System.out.println("Import Success");
	  }
	
}


