package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GenerierungDiagram {

	@SuppressWarnings({ "unchecked", "static-access" })
	public GenerierungDiagram(Stage primaryStage, Series<String, Number> series1, Series<String, Number> series2,
			Series<String, Number> series3, CategoryAxis xAchse, NumberAxis yAchse, int switchChart, VBox box1) {

		Pane rootPane = new Pane();
		Scene scene = null;

		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		/*
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
		*/
		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

		Label choiceLabelJahr = new Label("Jahr:");
		choiceLabelJahr.setStyle("-fx-font-weight: bold;");
		ChoiceBox<Integer> choiceJahr = new ChoiceBox<Integer>(FXCollections.observableArrayList(2020, 2021));
		choiceJahr.setTooltip(new Tooltip("Waehle das Jahr aus"));

		VBox boxJahr = new VBox();
		boxJahr.setLayoutX(8);
		boxJahr.setLayoutY(100);
		boxJahr.getChildren().addAll(choiceLabelJahr, choiceJahr);

		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

		Label choiceLabelMonat = new Label("Monat:");
		choiceLabelMonat.setStyle("-fx-font-weight: bold;");
		ChoiceBox<String> choiceMonat = new ChoiceBox<String>(FXCollections.observableArrayList("Januar", "Februar",
				"Maerz", "April", "Mai", "Juni", "July", "August", "September", "Oktober", "November", "Dezember"));
		choiceMonat.setTooltip(new Tooltip("Waehle den Monat aus"));

		VBox boxMonat = new VBox();
		boxMonat.setLayoutX(100);
		boxMonat.setLayoutY(100);
		boxMonat.getChildren().addAll(choiceLabelMonat, choiceMonat);

		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

		Label landLabel = new Label("Land:");
		landLabel.setStyle("-fx-font-weight: bold;");
		ListView<String> laenderListe = new ListView<String>();
		ObservableList<String> laender = FXCollections.observableArrayList("Deutschland", "Frankreich", "England",
				"Russland", "Italien", "Belgien");
		laenderListe.setTooltip(new Tooltip("Waehle ein Land aus!"));
		laenderListe.setItems(laender);
		laenderListe.setPrefWidth(200);
		laenderListe.setPrefHeight(120);

		VBox boxLaender = new VBox();
		boxLaender.setLayoutX(8);
		boxLaender.setLayoutY(150);
		boxLaender.getChildren().addAll(landLabel, laenderListe);

		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

		Label bundeslandLabel = new Label("Bundesland:");
		bundeslandLabel.setStyle("-fx-font-weight: bold;");

		ListView<String> bundeslandListe = new ListView<String>();
		ObservableList<String> bundesland = FXCollections.observableArrayList("Berlin", "Hamburg", "Brandenburg",
				"Baden Wuertemberg", "Mecklenburg Vorpommern", "Sachsen Anhalt");
		bundeslandListe.setTooltip(new Tooltip("Waehle ein Bundesland aus!"));
		bundeslandListe.setItems(bundesland);
		bundeslandListe.setPrefWidth(200);
		bundeslandListe.setPrefHeight(120);

		VBox boxBundeslaender = new VBox();

		boxBundeslaender.setLayoutX(8);
		boxBundeslaender.setLayoutY(300);
		boxBundeslaender.getChildren().addAll(bundeslandLabel, bundeslandListe);

		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

		Label landeskreisLabel = new Label("Landkreis:");
		landeskreisLabel.setStyle("-fx-font-weight: bold;");

		ListView<String> landkreisListe = new ListView<String>();
		ObservableList<String> landkreis = FXCollections.observableArrayList("Nordwestmecklenburg",
				"Mecklenburgische Seenplatte", "Ludwigslust-Parchim", "Rostock", "Schwerien", "Vorpommern-Greifswald",
				"Vorpommern-Rügen");
		landkreisListe.setTooltip(new Tooltip("Landkreise sind nur fuer das Land Deutschland vorhanden!"));
		landkreisListe.setItems(landkreis);
		landkreisListe.setPrefWidth(200);
		landkreisListe.setPrefHeight(120);

		VBox boxLandkreise = new VBox();

		boxLandkreise.setLayoutX(8);
		boxLandkreise.setLayoutY(450);
		boxLandkreise.getChildren().addAll(landeskreisLabel, landkreisListe);

		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

		Button reload = new Button("Reload");
		Button exit = new Button("Beenden");

		HBox boxButton = new HBox();

		boxButton.setLayoutX(8);
		boxButton.setLayoutY(625);
		boxButton.getChildren().addAll(reload, exit);

		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

		/*
		chartNumber.selectedToggleProperty().addListener((obserableValue, old_toggle, new_toggle) -> {
		    if (chartNumber.getSelectedToggle() != null) {
		        System.out.println("chartNumber: " + chartNumber.getSelectedToggle().getUserData().toString());
		    }
		}); 
		*/
		// Falls es zu einem Fehler kommt, soll eine Fehlermeldung ausgegeben werden
		try {

			XYChart<String, Number> chart = null;
			switch (switchChart) {
			case 1:
				chart = new BarChart<String, Number>(xAchse, yAchse);
				break;
			case 2:
				chart = new LineChart<String, Number>(xAchse, yAchse);
				break;
			case 3:
			default:
				chart = new AreaChart<String, Number>(xAchse, yAchse);
				break;
			}

			// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

			chart.setTitle("Daten für Monat Dezember");
			chart.getData().addAll(series1, series2, series3);
			chart.setLayoutX(200);
			chart.setLayoutY(23);
			chart.setPrefWidth(1400);
			chart.setPrefHeight(850);

			rootPane.getChildren().addAll(box1, boxJahr, boxMonat, boxLaender, boxBundeslaender, boxLandkreise,
					boxButton, chart);

			scene = new Scene(rootPane, 1600, 900);

			primaryStage.setScene(scene);
			primaryStage.show();

			// Ausgabe des Fehlers
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
