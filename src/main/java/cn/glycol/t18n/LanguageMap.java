package cn.glycol.t18n;

import java.util.HashMap;

public class LanguageMap extends HashMap<String, String> {

	private static final long serialVersionUID = 6462356389654896361L;

	public String get(Object key) {
		return super.getOrDefault(key, key.toString());
	}
	
}
