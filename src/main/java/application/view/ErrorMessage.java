package application.view;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * 
 * @author Christian Wollmann
 * Class to output an error message that can be accessed by other classes.
 */

public abstract interface ErrorMessage {
	
	public static void errorMessage(EnumErrorMessages errorType) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Warnung");
		alert.setHeaderText("Vorsicht, ein Fehler ist aufgetreten!");
		switch(errorType) {
		case ERROR_LAND:
			alert.setContentText(EnumErrorMessages.ERROR_LAND.get());
			break;
		case ERROR_MONTH:
			alert.setContentText(EnumErrorMessages.ERROR_MONTH.get());
			break;
		case ERROR_DATA_LOAD_ONLINE:
			alert.setContentText(EnumErrorMessages.ERROR_DATA_LOAD_ONLINE.get());
			break;
		case ERROR_DATA_LOAD_LOCAL:
			alert.setContentText(EnumErrorMessages.ERROR_DATA_LOAD_LOCAL.get());
			break;
		case ERROR_DIAGRAM:
			alert.setContentText(EnumErrorMessages.ERROR_DIAGRAM.get());
			break;
		case ERROR_MONTH_NOT_FOUND:
			alert.setContentText(EnumErrorMessages.ERROR_MONTH_NOT_FOUND.get());
			break;
		default:
			alert.setContentText(EnumErrorMessages.ERROR_UNKNOWN.get());
			break;
		}

		alert.showAndWait();
	}
	
}
