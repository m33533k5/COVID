package application.model;
import application.control.Translation;

/**
 * 
 * @author Christian Wollmann <br/><br/>
 *Constants used for the output and processing of the months.
 */
public enum EnumMonth {
	
	JANUARY(Translation.translate("label.month.january")), 
	FEBRUARY(Translation.translate("label.month.february")), 
	MARCH(Translation.translate("label.month.march")), 
	APRIL(Translation.translate("label.month.april")), 
	MAY(Translation.translate("label.month.may")), 
	JUNE(Translation.translate("label.month.june")), 
	JULY(Translation.translate("label.month.july")), 
	AUGUST(Translation.translate("label.month.august")), 
	SEPTEMBER(Translation.translate("label.month.september")), 
	OCTOBER(Translation.translate("label.month.october")), 
	NOVEMBER(Translation.translate("label.month.november")), 
	DECEMBER(Translation.translate("label.month.december"));

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
