package cn.glycol.t18n;

public interface LanguageMapConfiguration {

	public String getSplitter(String s);
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
