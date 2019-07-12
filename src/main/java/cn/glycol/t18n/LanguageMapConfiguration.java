package cn.glycol.t18n;

public interface LanguageMapConfiguration {

	/**
	 * 获取分割符<br>
	 * 用于将键和值分割开来
	 * @param s 每行文字
	 * @return 分隔符
	 */
	public String getSplitter(String s);
	
	/**
	 * 检测是否是注解<br>
	 * 如果是注解，则忽略本行
	 * @param s 每行文字
	 * @return 是否是注解，<code>true</code>则忽略读取
	 */
	public boolean isAnnotation(String s);
	
	public static final LanguageMapConfiguration DEFAULT = new LanguageMapConfiguration() {
		
		@Override
		public String getSplitter(String s) {
			return "=";
		}
		
		@Override
		public boolean isAnnotation(String s) {
			return s.startsWith("#");
		}
		
	};
	
}
