package application.model;

import application.control.Translation;

/**
 * 
 * @author Christian Wollmann <br/><br/>
 *Constants used for the output and processing of the months.
 */
public enum EnumMonth {
	
	JANUARY("label.month.january"), 
	FEBRUARY("label.month.february"), 
	MARCH("label.month.march"), 
	APRIL("label.month.april"), 
	MAY("label.month.may"), 
	JUNE("label.month.june"), 
	JULY("label.month.july"), 
	AUGUST("label.month.august"), 
	SEPTEMBER("label.month.september"), 
	OCTOBER("label.month.october"), 
	NOVEMBER("label.month.november"), 
	DECEMBER("label.month.december");

	private String value;

	private EnumMonth(final String value) {
		this.value = value;
	}

	public String get() {
		return this.value;
	}
	
	public static EnumMonth getEnumByString(String code) {
		for(EnumMonth em : EnumMonth.values()) {
			if(em.get().equals(code)) 
				return em;
		}
		return null;
	}
	
	@Override
	public final String toString() {
		return Translation.get(this.get());
	}
}
