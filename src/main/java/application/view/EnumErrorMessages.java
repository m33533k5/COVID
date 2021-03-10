package application.view;

/**
 * 
 * @author Christian Wollmann <br/><br/>
 *Constants used for the output and processing of the error messages.
 */

public enum EnumErrorMessages {

	ERROR_LAND("Sie haben kein Land ausgewaehlt."), 
	ERROR_MONTH("Fuer diesen Monat liegen noch nicht die Daten zur Verfuegung. Bitte den Monat oder das Jahr aendern."), 
	ERROR_DATA_LOAD_ONLINE("Die aktuellen Daten konnten nicht von der Seite geladen werden. Es werden die lokallen Daten verwendet."), 
	ERROR_DATA_LOAD_LOCAL("Die lokalen Daten konnten nicht geladen werden. Das Program wird beendet."), 
	ERROR_DIAGRAM("Es wurde kein Diagramtyp ausgewählt."), 
	ERROR_MONTH_NOT_FOUND("Es konnte kein Monat gefunden werden."),
	ERROR_UNKNOWN("Unbekannter Fehler");

	private final String value;

	private EnumErrorMessages(final String value) {
		this.value = value;
	}

	public String get() {
		return this.value;
	}
	
	public static String getEnumByString(String code) {
		for(EnumErrorMessages erm : EnumErrorMessages.values()) {
			if(erm.get().equals(code)) 
				return erm.name();
		}
		return null;
	}
}
