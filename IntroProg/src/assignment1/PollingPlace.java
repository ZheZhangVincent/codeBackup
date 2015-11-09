package assignment1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;


public class PollingPlace {
	
	static HashMap columnTitle = new HashMap();
	
	//create a method named integrityTest()
	
	public static void main(String[] args) throws Exception {
		 
		PollingPlace pp = new PollingPlace();
		Information inf = ReadData();
		for (RawData data : inf.getRawDatas()) {
			System.out.println(data.getCalculationType() + "," + data.getCountNum() + ": " + data.getCalculationValue());
		}
	  }
	
	public HashMap getcolumnTitle() throws IOException {
		BufferedReader br = null;
		try {
			int max_column_num = 0;
			br = new BufferedReader(new FileReader("./HouseDopByPPDownload-17496/HouseDopByPPDownload-17496-NSW-EMON.csv"));;
			String line = "";
			while ((line = br.readLine()) != null) {
			String[] items = line.split("\\s");
			if (max_column_num < items.length)
			{
				max_column_num = items.length;
			}
			}
			
			br = new BufferedReader(new FileReader("./HouseDopByPPDownload-17496/HouseDopByPPDownload-17496-NSW-EMON.csv"));;
			line = "";
			String[] max_items = null;
			while ((line = br.readLine()) != null) {
			String[] items = line.split("\\s");
			max_items = items;
			if (items.length == max_column_num) break;
			}
			for (int index = 0; index < max_items.length; index++) {
				columnTitle.put(max_items[index],index);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}  finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return columnTitle;
		
	}
	
	private static Information ReadData() throws Exception {
		BufferedReader br = new BufferedReader(new FileReader("./HouseDopByPPDownload-17496/HouseDopByPPDownload-17496-NSW-EMON.csv"));
		String line = null;
		List<RawData> dataLines = new ArrayList<RawData>();
		Information inf = new Information();
		while ((line = br.readLine()) != null) {
			String[] items = line.trim().split("\\s");
			RawData data = new RawData();
			data.StateAb = items[(int) columnTitle.get("StateAb")];
			data.PPNm = items[(int) columnTitle.get("PPNm")];
			data.CountNum = Integer.parseInt(items[(int) columnTitle.get("CountNum")]);
			data.PartyAb = items[(int) columnTitle.get("PartyAb")];
			data.SittingMemberFl = items[(int) columnTitle.get("SittingMemberFl")];
			data.CalculationType = items[(int) columnTitle.get("CalculationType")];
			data.CalculationValue = Integer.parseInt(items[(int) columnTitle.get("CalculationValue")]);
			
			dataLines.add(data);
			inf.addRawData(data);
		}
		
		return inf;
	}
	
}


