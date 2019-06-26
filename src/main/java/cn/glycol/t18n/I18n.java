package cn.glycol.t18n;

import java.nio.charset.Charset;

public class I18n {

	private static LanguageMap map;
	private static Charset charset;
	
	static {
		charset = Charset.forName(System.getProperty("file.encoding"));
	}
	
	public static void setLanguageMap(LanguageMap map) {
		I18n.map = map;
	}
	
	public static void setEncoding(String charset) {
		I18n.charset = Charset.forName(charset);
	}
	
	public static String format(String key, Object...format) {
		return tryFormat(
				reEncode(getLanguageMapSafe().get(key), charset),
				format);
	}
	
	public static boolean hasKey(String key) {
		return getLanguageMapSafe().containsKey(key);
	}
	
	private static String reEncode(String bef, Charset charset) {
		byte[] bytes = bef.getBytes();
		return new String(bytes, charset);
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
