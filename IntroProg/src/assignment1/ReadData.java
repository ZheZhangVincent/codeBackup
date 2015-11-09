package assignment1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class ReadData {
	
	public static void main(String[] args) throws Exception {
		SourceData sd = ReadData();
		for (RowData data : sd.getRowDatas()) {
			System.out.println(data.getCity() + "," + data.getSubarb() + "," + data.getCode() + ": " + data.getNum());
		}
	}

	private static SourceData ReadData() throws Exception {
		BufferedReader br = new BufferedReader(new FileReader("data.txt"));
		String line = null;
		List<RowData> dataLines = new ArrayList<RowData>();
		SourceData sd = new SourceData();
		while ((line = br.readLine()) != null) {
			String[] items = line.trim().split(",");
			RowData data = new RowData();
			data.city = items[0];
			data.subarb = items[1];
			data.code = items[2];
			data.num = Integer.parseInt(items[3]);
			dataLines.add(data);
			sd.addRowData(data);
		}
		return sd;
	}

}
