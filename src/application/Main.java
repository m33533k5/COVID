package application;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

public class Main extends Application {

	// Listen, um die benoetigten Daten dynamisch hinzuzufuegen
	ArrayList<String> tage = new ArrayList<String>();
	ArrayList<Number> tote = new ArrayList<Number>();
	ArrayList<Number> genesen = new ArrayList<Number>();
	ArrayList<Number> erkrankt = new ArrayList<Number>();

	CategoryAxis xAchse = new CategoryAxis();
	NumberAxis yAchse = new NumberAxis();

	Stage primaryStage;

	int chartNumber;

	@Override
	public void start(Stage primaryStage) {

		this.primaryStage = primaryStage;

		yAchse.setLabel("Anzahl");
		xAchse.setLabel("Tage");

		// Aufruf der Klasse, um die Liste mit Werten zu befüllen, die für Tage genutzt
		// wird
		ListeBefuellen lb = new ListeBefuellen();
		tage = lb.listeBefuellenString(tage, 0);
		tote = lb.listeBefuellenNumber(tote, 4);
		genesen = lb.listeBefuellenNumber(genesen, 5);
		erkrankt = lb.listeBefuellenNumber(erkrankt, 6);

		// Aufruf der Klasse, um das Diagram zu erzeugen
		GenerierungDatenFuerDiagram gdfd = new GenerierungDatenFuerDiagram();

		// Anlegen einer Serie mit den jeweiligen Werten und dem Item dem sie zugeordnet
		// werden
		// Wird nachfolgend als Parameter verwendet
		Series<String, Number> datenErkrankte = gdfd.generierungBalkendiagram("Gestorben", tage, erkrankt);
		Series<String, Number> datenGenesene = gdfd.generierungBalkendiagram("Genesen", tage, genesen);
		Series<String, Number> datenGestorbene = gdfd.generierungBalkendiagram("Erkrankt", tage, tote);
	
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
			
		Label radioLabel = new Label("Diagramarten:");
		radioLabel.setStyle("-fx-font-weight: bold;");

		final ToggleGroup chartNumber = new ToggleGroup();

		RadioButton buttonBalken = new RadioButton("Balkendiagram");
		buttonBalken.setToggleGroup(chartNumber);
		buttonBalken.setUserData(1);
		buttonBalken.setSelected(true);
		RadioButton buttonLinie = new RadioButton("Liniendiagram");
		buttonLinie.setToggleGroup(chartNumber);
		buttonLinie.setUserData(2);
		RadioButton buttonFlaeche = new RadioButton("Flaechendiagram");
		buttonFlaeche.setToggleGroup(chartNumber);
		buttonFlaeche.setUserData(3);

		VBox boxRadio = new VBox();
		boxRadio.setLayoutX(8);
		boxRadio.setLayoutY(23);
		boxRadio.getChildren().addAll(radioLabel, buttonBalken, buttonLinie, buttonFlaeche);
		
		GenerierungDiagram gd = new GenerierungDiagram(primaryStage, datenErkrankte, datenGenesene, datenGestorbene,
				xAchse, yAchse, 1, boxRadio);
		
		chartNumber.selectedToggleProperty().addListener((obserableValue, old_toggle, new_toggle) -> {
		    if(Integer.parseInt(chartNumber.getSelectedToggle().getUserData().toString()) == 1) {
		    	GenerierungDiagram gd1 = new GenerierungDiagram(primaryStage, datenErkrankte, datenGenesene, datenGestorbene,
						xAchse, yAchse, 1, boxRadio);
		    }else if(Integer.parseInt(chartNumber.getSelectedToggle().getUserData().toString()) == 2){
		    	GenerierungDiagram gd2 = new GenerierungDiagram(primaryStage, datenErkrankte, datenGenesene, datenGestorbene,
						xAchse, yAchse, 2, boxRadio);
		    }else if(Integer.parseInt(chartNumber.getSelectedToggle().getUserData().toString()) == 3) {
		    	GenerierungDiagram gd3 = new GenerierungDiagram(primaryStage, datenErkrankte, datenGenesene, datenGestorbene,
						xAchse, yAchse, 3, boxRadio);
		    }
		}); 
		
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	}

	public static void main(String[] args) {
		launch(args);
	}
}
