package cn.glycol.t18n;

import java.nio.charset.Charset;
import java.util.Objects;

public class T18n {

	/**
	 * 设置本地化对照表
	 * @param map 本地化对照表
	 */
	public static void set(LanguageMap map) {
		Objects.requireNonNull(map, "map cannot be null");
		I18n.map = map;
	}
	
	/**
	 * 添加额外的本地化对照表
	 * @param map 本地化对照表
	 */
	public static void add(LanguageMap map) {
		Objects.requireNonNull(map, "map cannot be null");
		I18n.map.putAll(map);
	}
	
	/**
	 * 设置输出的编码
	 * @param charset 编码
	 */
	public static void charset(Charset charset) {
		Objects.requireNonNull(charset, "charset cannot be null");
		I18n.charset = charset;
	}
	
	/**
	 * 获取本地化对照表
	 */
	public static LanguageMap map() {
		return I18n.map;
	}
	
}
