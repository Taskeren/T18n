package cn.glycol.t18n;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class I18n {

	private static LanguageMap map;
	private static Charset charset;
	
	public static final int LOOP_MAX_COUNT = 1000;
	
	static {
		charset = Charset.forName(System.getProperty("file.encoding"));
	}
	
	public static void setLanguageMap(LanguageMap map) {
		I18n.map = map;
	}
	
	public static void setEncoding(String charset) {
		I18n.charset = Charset.forName(charset);
	}
	
	/** 自动从语言文件中提取翻译，空翻译时返回原键值 */
	public static String translate(String key) {
		return reEncode(getLanguageMapSafe().get(key), charset);
	}
	
	/** 自动翻译（translate）后再执行格式化（format）  */
	public static String format(String key, Object...format) {
		return tryFormat(
				translate(key),
				format);
	}
	
	/**
	 * 连续读取翻译（translate）。
	 * @param keyRegular 翻译键值表达式，需要填入一个“%s”用于替换为行数。行数从0开始。例如“welcome.%s”，则程序会依次向列表中添加“welcome.0”，“welcome.1”...的翻译，直到获取到空值。
	 */
	public static List<String> translateList(String keyRegular) {
		
		List<String> vlist = new ArrayList<String>();
		
		int i = 0;
		
		while(true) {
			if(i >= LOOP_MAX_COUNT) break;
			String k = tryFormat(keyRegular, i);
			if(hasKey(k)) {
				vlist.add(translate(k));
			} else {
				break;
			}
			i++;
		}
		
		return vlist;
		
	}
	
	/** 使字符串列表扁平化。 */
	private static String flattenList(List<String> vlist) {
		
		String v = "";
		for(String o : vlist) {
			v += o + "\n";
		}
		v = v.trim();
		
		return v;
		
	}
	
	/**
	 * 将列表翻译中的内容进行format操作。
	 * @see #translateList(String)
	 */
	public static String formatList(String keyRegular, Object...format) {
		
		return tryFormat(flattenList(translateList(keyRegular)), format);
		
	}
	
	public static boolean hasKey(String key) {
		return getLanguageMapSafe().containsKey(key);
	}
	
	/** @see #hasKey(String) */
	private static boolean canTranslate(String key) {
		return hasKey(key);
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
