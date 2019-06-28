package cn.glycol.t18n;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

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
	
	/** 自动翻译（translate）后再执行格式化（format）  */
	public static String format(String key, Object...format) {
		return tryFormat(
				translate(key),
				format);
	}
	
	/** 自动从语言文件中提取翻译，空翻译时返回原键值 */
	public static String translate(String key) {
		return reEncode(getLanguageMapSafe().get(key), charset);
	}
	
	/** 自动翻译（translate）列表，将多行替换为一行后再执行格式化（format）<br>
	 * @param key 进入列表的格式化代码（format key），例如<code>app.message.welcome.%s</code>，其中<code>%s</code>会被替换为从<code>0</code>开始的数字，逐行读取，直到空翻译或最大值（1000）
	 */
	public static String format2(String key, Object...format) {
		
		List<String> vlist = new ArrayList<String>();
		
		int i = 0;
		int max = 1000;
		
		while(true) {
			if(i >= max) break;
			String k = tryFormat(key, i);
			if(hasKey(k)) {
				vlist.add(translate(k));
			} else {
				break;
			}
			i++;
		}
		
		String v = "";
		for(String o : vlist) {
			v += o + "\n";
		}
		v = v.trim();
		
		v = tryFormat(v, format);
		
		return v;
		
	}
	
	public static boolean hasKey(String key) {
		return getLanguageMapSafe().containsKey(key);
	}
	
	public static boolean canTranslate(String key) {
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
