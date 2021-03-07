package application.control;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * @author Christian Wollmann
 *
 */

public class Translation {
	
	private static final Logger LOGGER = LogManager.getLogger(Translation.class);
	private static final Map<String, Properties> map;
	private static String language;
	static {
		map = new HashMap<>();
	}
	
	private Translation() {
		
		instance();
	}
	
	public static final void instance() {
		
		setLanguage("de");
		Properties propDE = new Properties();
		Properties propEN = new Properties();
		try {
			propDE.load(new FileInputStream("src\\main\\resources\\de.properties"));
			propEN.load(new FileInputStream("src\\main\\resources\\en.properties"));
		}catch(Exception e) {
			LOGGER.error("Konnte properties nicht laden", e);
		}
		map.put("de", propDE);
		map.put("en", propEN);
	}
	
	public static final String translate(String key) {

		return map.get(language).getProperty(key, key);
	}

	public String getLanguage() {
		return language;
	}

	public static final void setLanguage(String lang) {
		language = lang;
	}
	
}
