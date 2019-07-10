package cn.glycol.t18n;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class T18nUtils {

	/** 从在线网站上读取内容 */
	public static List<String> getOnlineContent(String _url) {
		
		URL url;
		try {
			url = new URL(_url);
		} catch(MalformedURLException e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
		
		try {
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			InputStream is = conn.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			List<String> lines = new ArrayList<String>();
			String s;
			while((s = br.readLine()) != null) {
				lines.add(s);
			}
			is.close();
			br.close();
			return lines;
		} catch (IOException e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
		
	}
	
	/** 从本地文件读取内容 */
	public static List<String> getLocalContent(File file) {
		if (file.isFile()) {
			try {
				return Files.readAllLines(file.toPath(), Charset.forName("UTF-8"));
			} catch (Exception e) {

			}
		}
		return new ArrayList<>();
	}
	
	/** 保存翻译表到文件 */
	public static void saveLanguageMap(LanguageMap map, File file) {
		
		Objects.requireNonNull(map);
		Objects.requireNonNull(file);
		
		try {
			
			String content = flattenList(map.getAllKeyValuePairsString());
			
			if(!file.exists()) {
				file.getParentFile().mkdirs();
				file.createNewFile();
			}
			
			FileWriter fw = new FileWriter(file);
			fw.write(content);
			fw.close();
			
		} catch (Exception e) {
			System.err.println("Unable to write "+file);
		}
		
	}
	
	/** 使字符串列表扁平化。 */
	public static String flattenList(List<String> vlist) {
		
		String v = "";
		for(String o : vlist) {
			v += o + "\n";
		}
		v = v.trim();
		
		return v;
		
	}
	
}
