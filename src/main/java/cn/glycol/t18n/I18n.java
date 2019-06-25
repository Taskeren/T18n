package cn.glycol.t18n;

public class I18n {

	private static LanguageMap map;
	
	public static void setLanguageMap(LanguageMap map) {
		I18n.map = map;
	}
	
	public static String format(String key, Object...format) {
		return tryFormat(getLanguageMapSafe().get(key), format);
	}
	
	public static boolean hasKey(String key) {
		return getLanguageMapSafe().containsKey(key);
	}
	
	private static String tryFormat(String context, Object...format) {
		try {
			return String.format(context, format);
		} catch (Exception e) {
			return context;
		}
	}
	
	private static LanguageMap getLanguageMapSafe() {
		return map == null ? new LanguageMap() : map;
	}
	
}
