package cn.glycol.t18n;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class LanguageMapBuilder {

	public static LanguageMap fromFile(File langFile) {
		return fromFile(langFile, LanguageMapConfiguration.DEFAULT);
	}

	public static LanguageMap fromFile(File langFile, LanguageMapConfiguration config) {

		List<String> lines = T18nUtils.getLocalContent(langFile);
		return processFinal(config, lines);
	}

	public static LanguageMap fromJarResource(String path) {
		return fromJarResource(path, "UTF8", LanguageMapConfiguration.DEFAULT);
	}

	public static LanguageMap fromJarResource(String path, String encode, LanguageMapConfiguration config) {

		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
		if (is == null) {
			return new LanguageMap();
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName(encode)));

		String cache;
		List<String> strs = new ArrayList<>();

		try {
			while (true) {
				if ((cache = br.readLine()) != null) {
					strs.add(cache);
				} else {
					break;
				}
			}
		} catch (IOException e) {

		}

		return processFinal(config, strs);

	}
	
	public static LanguageMap fromURL(String url) {
		return fromURL(url, LanguageMapConfiguration.DEFAULT);
	}
	
	public static LanguageMap fromURL(String url, LanguageMapConfiguration config) {
		return processFinal(config, T18nUtils.getOnlineContent(url));
	}

	private static final String[] UNREADABLE = new String[] { null, null };

	/** 处理单行翻译 */
	private static String[] process(LanguageMapConfiguration config, String s) {

		/* Empty Line */
		if (s == null || s.length() == 0) {
			return UNREADABLE;
		}

		/* Annotation */
		if (config.isAnnotation(s)) {
			return UNREADABLE;
		}

		String[] parts = s.split(config.getSplitter(s), 2);
		
		/* Part fewer than 2 */
		if(parts.length < 2) {
			return UNREADABLE;
		}

		/* The Key is Null */
		if (parts[0] == null || parts[0].length() == 0) {
			return UNREADABLE;
		}

		/* The Value is Null */
		if (parts[1] == null) {
			parts[1] = "null";
		}

		return parts;

	}

	private static boolean isUnreadable(String[] s) {
		return s[0] == null;
	}
	
	/** 将处理的结果整合到翻译表中 */
	private static LanguageMap processFinal(LanguageMapConfiguration config, List<String> lines) {

		final LanguageMap map = new LanguageMap();

		for (String s : lines) {
			String[] pair = process(config, s);
			if (!isUnreadable(pair)) {
				map.put(pair[0], pair[1]);
			}
		}

		return map;

	}

}
