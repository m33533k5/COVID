package application.view;

import application.control.Translation;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * 
 * @author Christian Wollmann Class to output an error message that can be
 *         accessed by other classes.
 */

public abstract interface ErrorMessage {

	public static void errorMessage(EnumErrorMessages errorType) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle(Translation.get("error.set.title"));
		alert.setHeaderText(Translation.get("error.set.header"));
		switch (errorType) {
		case ERROR_LAND:
			alert.setContentText(Translation.get("error.land.notFound"));
			break;
		case ERROR_DATA_NOT_FOUND:
			alert.setContentText(Translation.get("error.data.notFound"));
			break;
		case ERROR_DATA_LOAD_ONLINE:
			alert.setContentText(Translation.get("error.dataOnline.notFound"));
			break;
		case ERROR_DATA_LOAD_LOCAL:
			alert.setContentText(Translation.get("error.dataLocal.notFound"));
			break;
		case ERROR_DIAGRAM_NOT_FOUND:
			alert.setContentText(Translation.get("error.diagram.notFound"));
			break;
		case ERROR_MONTH_NOT_FOUND:
			alert.setContentText(Translation.get("error.month.notFound"));
			break;
		default:
			alert.setContentText(Translation.get("error.unknown"));
			break;
		}

		alert.showAndWait();
	}

}
