package application.view;
import application.control.Translation;

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
		alert.setTitle(Translation.translate("error.set.title"));
		alert.setHeaderText(Translation.translate("error.set.header"));
		switch(errorType) {
		case ERROR_LAND:
			alert.setContentText(Translation.translate("error.land.notFound"));
			break;
		case ERROR_DATA_NOT_FOUND:
			alert.setContentText(Translation.translate("error.data.notFound"));
			break;
		case ERROR_DATA_LOAD_ONLINE:
			alert.setContentText(Translation.translate("error.dataOnline.notFound"));
			break;
		case ERROR_DATA_LOAD_LOCAL:
			alert.setContentText(Translation.translate("error.dataLocal.notFound"));
			break;
		case ERROR_DIAGRAM_NOT_FOUND:
			alert.setContentText(Translation.translate("error.diagram.notFound"));
			break;
		case ERROR_MONTH_NOT_FOUND:
			alert.setContentText(Translation.translate("error.month.notFound"));
			break;
		default:
			alert.setContentText(Translation.translate("error.unknown"));
			break;
		}

		alert.showAndWait();
	}
	
}
