package application.view;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * 
 * @author Christian Wollmann
 * 
 */

public class ErrorMessage implements InterfaceErrorMessage{
	
	public void errorMessage(int errorType) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Warnung");
		alert.setHeaderText("Vorsicht, ein Fehler ist aufgetreten!");
		switch(errorType) {
		case 1:
			alert.setContentText("Sie haben kein Land ausgewaehlt.");
			break;
		case 2:
			alert.setContentText("Fuer diesen Monat liegen noch nicht die Daten zur Verfuegung. Bitte den Monat oder das Jahr aendern.");
			break;
		case 3:
			alert.setContentText("Die aktuellen Daten konnten nicht von der Seite geladen werden. Es werden die lokallen Daten verwendet.");
			break;
		case 4:
			alert.setContentText("Die lokalen Daten konnten nicht geladen werden. Das Program wird beendet.");
			break;
		case 5:
			alert.setContentText("Es wurde kein Diagramtyp ausgewählt.");
		case 6:
			alert.setContentText("Es konnte kein Monat gefunden werden.");
		}

		alert.showAndWait();
	}
	
}
