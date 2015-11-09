package assignment1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SourceData {
	
	Map<String, RowData> map = new HashMap<String, RowData>();

	public void addRowData(RowData data) {
		String key = generateKey(data.city, data.subarb, data.code);
		this.map.put(key, data);
	}

	private String generateKey(String city, String suburb, String code) {
		String key = city + "-" + suburb + "-" + code;
		return key;
	}
	
	public RowData getRowData(String city, String suburb, String code) {
		return map.get(this.generateKey(city, suburb, code));
	}
	
	public List<RowData> getRowDatas() {
		return new ArrayList<RowData>(this.map.values());
	}
}
