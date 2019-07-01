package cn.glycol.t18n;

import java.util.ArrayList;
import java.util.HashMap;

public class LanguageMap extends HashMap<String, String> {

	private static final long serialVersionUID = 6462356389654896361L;

	public String get(Object key) {
		return super.getOrDefault(key, key.toString());
	}
	
	public ArrayList<String> getAllKeyValuePairs() {
		
		ArrayList<String> list = new ArrayList<String>();
		for(Entry<String, String> set : this.entrySet()) {
			String key = set.getKey();
			String val = set.getValue();
			String line;
			
			if(key != null && !key.equals("")) {
				line = key + "=" + val;
				list.add(line);
			}
		}
		
		return list;
	}
	
}
