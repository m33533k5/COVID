package application.model;

/**
 * 
 * @author Christian Wollmann
 *
 */
public enum EnumMonth {
	
	JANUARY("Januar"), 
	FEBRUARY("Februar"), 
	MARCH("März"), 
	APRIL("April"), 
	MAY("Mai"), 
	JUNE("Juni"), 
	JULY("July"), 
	AUGUST("August"), 
	SEPTEMBER("September"), 
	OCTOBER("Oktober"), 
	NOVEMBER("November"), 
	DECEMBER("Dezember");

	private String value;

	private EnumMonth(final String value) {
		this.value = value;
	}

	public String get() {
		return this.value;
	}
	
	public static String getEnumByString(String code) {
		for(EnumMonth em : EnumMonth.values()) {
			if(em.get().equals(code)) 
				return em.name();
		}
		return null;
	}
}
