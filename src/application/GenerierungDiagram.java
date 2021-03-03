package application;

import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

// In dieser Klasse wird das Diagram erzeugt

public class GenerierungDiagram {

	@SuppressWarnings({ "unchecked", "static-access" })
	public GenerierungDiagram(Stage primaryStage, Series<String, Number> series1, Series<String, Number> series2,
			Series<String, Number> series3, CategoryAxis xAchse, NumberAxis yAchse, int switchChart, VBox boxRadio, VBox boxJahr, VBox boxMonat, VBox boxLand, VBox boxBundeslaender, String nameMonat, int jahr, String nameLand) {

		Pane rootPane = new Pane();
		Scene scene = null;
		System.out.println(1);

		try {
			System.out.println(2);
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
			System.out.println(series1.getData());
			System.out.println(series2.getData());
			System.out.println(series3.getData());

			chart.setTitle("Daten "+nameMonat+" "+jahr+" "+nameLand);
			
			chart.getData().addAll(series1, series2, series3);
			chart.setLayoutX(225);
			chart.setLayoutY(20);
			chart.setPrefWidth(1350);
			chart.setPrefHeight(850);

			rootPane.getChildren().addAll(boxRadio, boxJahr, boxMonat, boxLand, boxBundeslaender,
					chart);

			scene = new Scene(rootPane, 1600, 900);
			
			primaryStage.setScene(scene);
			primaryStage.show();

			// Ausgabe des Fehlers
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
