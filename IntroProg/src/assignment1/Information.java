package assignment1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Information {

	Map<String, RawData> map = new HashMap<String, RawData>();
	
	public void addRawData(RawData data) {
		String key = generateKey(data.StateAb, data.PPNm, data.CountNum, data.PartyAb, data.SittingMemberFl, data.CalculationType, data.CalculationValue);
		this.map.put(key, data);
	}

	private String generateKey(String StateAb, String PPNm, int CountNum, String PartyAb, String SittingMemberFl, String CalculationType, int CalculationValue) {
		String key = StateAb + "-" + PPNm + "-" + CountNum + "-" + PartyAb +"-" + SittingMemberFl + "-" + CalculationType + "-" + CalculationValue;
		return key;
	}
	
	public RawData getRawData(String StateAb, String PPNm, int CountNum, String PartyAb, String SittingMemberFl, String CalculationType, int CalculationValue) {
		return map.get(this.generateKey(StateAb, PPNm, CountNum, PartyAb, SittingMemberFl, CalculationType, CalculationValue));
	}
	
	public List<RawData> getRawDatas() {
		return new ArrayList<RawData>(this.map.values());
	}
	
}
