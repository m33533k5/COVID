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
	OKTOBER("Oktober"), 
	NOVEMBER("November"), 
	DEZEMBER("Dezember");

	private final String value;

	private EnumMonth(final String value) {
		this.value = value;
	}

	public String get() {
		return this.value;
	}
}
