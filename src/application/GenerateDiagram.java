package application;

import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

// In this class the diagram is created.
// The required data is passed from other classes as parameters.

public class GenerateDiagram {

	private NumberAxis yAxis;
	private CategoryAxis xAxis;
	
	final ScrollBar sc = new ScrollBar();
	
	@SuppressWarnings({ "unchecked", "static-access" })
	public GenerateDiagram(Stage primaryStage, Series<String, Number> series1, Series<String, Number> series2,
			Series<String, Number> series3, int switchChart, VBox boxRadio, VBox boxYear, VBox boxMonth, VBox boxCountry, VBox boxState, String nameMonth, int year, String nameCountry) {
		
		Pane rootPane = new Pane();
		ScrollPane scrollpane = new ScrollPane();
		scrollpane.setHbarPolicy(ScrollBarPolicy.ALWAYS);
		scrollpane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		Scene scene = null;
		
		// The axes are always reinitialized so that if the number of days changes, the formatting is adjusted.
		xAxis = new CategoryAxis();
		yAxis = new NumberAxis();
		xAxis.setLabel("Anzahl");
		yAxis.setLabel("Tage");

		try {
			XYChart<String, Number> chart = null;
			switch (switchChart) {
			case 1:
				chart = new BarChart<String, Number>(xAxis, yAxis);
				break;
			case 2:
				chart = new LineChart<String, Number>(xAxis, yAxis);
				break;
			case 3:
			default:
				chart = new AreaChart<String, Number>(xAxis, yAxis);
				break;
			}
			chart.setTitle("Daten "+nameMonth+" "+year+" "+nameCountry);		
			chart.getData().addAll(series1, series2, series3);
			chart.setLayoutX(225);
			chart.setLayoutY(20);
			chart.setPrefWidth(1350);
			chart.setPrefHeight(850);
			
			rootPane.getChildren().addAll(boxRadio, boxYear, boxMonth, boxCountry, boxState, chart);
			scrollpane.setContent(rootPane);
			
			scene = new Scene(scrollpane, 1600, 900);
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
