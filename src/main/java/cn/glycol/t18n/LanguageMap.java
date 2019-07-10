package cn.glycol.t18n;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class LanguageMap extends HashMap<String, String> {

	private static final long serialVersionUID = 6462356389654896361L;

	public String get(Object key) {
		return super.getOrDefault(key, key.toString());
	}
	
	/** 返回所有键值对
	 * @see #entrySet()
	 */
	public Set<Entry<String, String>> getAllKeyValuePairs() {
		return this.entrySet();
	}
	
	/** 获取所有键值对文字 */
	public ArrayList<String> getAllKeyValuePairsString() {
		
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
	
	/** 输出键值对 */
	public static void printKVPair(LanguageMap langMap) {
		ArrayList<String> lines = langMap.getAllKeyValuePairsString();
		lines.forEach(s->System.out.println(s));
	}
	
}
