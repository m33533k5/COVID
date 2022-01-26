package application.view;

import application.control.Translation;

/**
 * 
 * @author Christian Wollmann <br/>
 *         <br/>
 *         Constants used for the output and processing of the error messages.
 */

public enum EnumErrorMessages {

	ERROR_LAND(Translation.get("error.land.notFound")), ERROR_DATA_NOT_FOUND(Translation.get("error.data.notFound")),
	ERROR_DATA_LOAD_ONLINE(Translation.get("error.dataOnline.notFound")),
	ERROR_DATA_LOAD_LOCAL(Translation.get("error.dataLocal.notFound")),
	ERROR_DIAGRAM_NOT_FOUND(Translation.get("error.diagram.notFound")),
	ERROR_MONTH_NOT_FOUND(Translation.get("error.month.notFound")),
	ERROR_YEAR_NOT_FOUND(Translation.get("error.year.notFound")), ERROR_UNKNOWN(Translation.get("error.unknown"));

	private final String value;

	private EnumErrorMessages(final String value) {
		this.value = value;
	}

	public String get() {
		return this.value;
	}

	public static String getEnumByString(String code) {
		for (EnumErrorMessages erm : EnumErrorMessages.values()) {
			if (erm.get().equals(code))
				return erm.name();
		}
		return null;
	}
}
