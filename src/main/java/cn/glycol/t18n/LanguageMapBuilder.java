package cn.glycol.t18n;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class LanguageMapBuilder {

	public static LanguageMap fromFile(File langFile) {
		return fromFile(langFile, LanguageMapConfiguration.DEFAULT);
	}
	
	public static LanguageMap fromFile(File langFile, LanguageMapConfiguration config) {
		
		List<String> lines = getLinesFromFile(langFile);
		return getLanguageMapFromLines(config, lines);
	}
	
	private static List<String> getLinesFromFile(File file) {
		if(file.isFile()) {
			try {
				return Files.readAllLines(file.toPath(), Charset.forName("UTF-8"));
			} catch (Exception e) {
				
			}
		}
		return new ArrayList<>();
	}
	
	private static LanguageMap getLanguageMapFromLines(LanguageMapConfiguration config, List<String> lines) {
		
		final LanguageMap map = new LanguageMap();
		
		for(String s : lines) {
			String[] pair = getLanguageMapFromLine(config, s);
			if(!isUnreadable(pair)) {
				map.put(pair[0], pair[1]);
			}
		}
		
		return map;
		
	}
	
	private static final String[] UNREADABLE = new String[] {null, null};
	
	private static String[] getLanguageMapFromLine(LanguageMapConfiguration config, String s) {
		
		/* Empty Line */
		if(s == null || s.length() == 0) {
			return UNREADABLE;
		}
		
		/* Annotation */
		if(config.isAnnotation(s)) {
			return UNREADABLE;
		}
		
		String[] parts = s.split(config.getSplitter(s), 2);
		
		/* The Key is Null */
		if(parts[0] == null || parts[0].length() == 0) {
			return UNREADABLE;
		}
		
		/* The Value is Null */
		if(parts[1] == null) {
			parts[1] = "null";
		}
		
		return parts;
		
	}
	
	private static boolean isUnreadable(String[] s) {
		return s[0] == null;
	}
	
	public static LanguageMap fromJarResource(String path) {
		return fromJarResource(path, LanguageMapConfiguration.DEFAULT);
	}

	public static LanguageMap fromJarResource(String path, LanguageMapConfiguration config) {
		
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
		if(is == null) {
			return new LanguageMap();
		}
		
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		
		String cache;
		List<String> strs = new ArrayList<>();
		
		try {
			while(true) {
				if((cache = br.readLine()) != null) {
					strs.add(cache);
				} else {
					break;
				}
			}
		} catch(IOException e) {
			
		}
		
		return getLanguageMapFromLines(config, strs);
		
	}
	
}
