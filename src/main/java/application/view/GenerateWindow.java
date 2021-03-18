package application.view;

import application.control.Translation;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GenerateWindow {
	
	private Pane rootPane;
	private ScrollPane scrollpane;
	private Scene scene;
	private CategoryAxis xAxis;
	private NumberAxis yAxis;
	
	public void generateScene(Stage primaryStage, VBox boxRadio, VBox boxYear, VBox boxMonth, VBox boxCountry, HBox boxButton, HBox boxLanguage) {
		
		rootPane = new Pane();
		scrollpane = new ScrollPane();
		scrollpane.setHbarPolicy(ScrollBarPolicy.ALWAYS);
		scrollpane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		scene = null;
		
		rootPane.getChildren().addAll(boxRadio, boxYear, boxMonth, boxCountry, boxButton, boxLanguage);
		scrollpane.setContent(rootPane);
		
		scene = new Scene(scrollpane, 1600, 900);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	@SuppressWarnings({ "unchecked" })
	public void generateDiagram(Stage primaryStage, Series<String, Number> series1, Series<String, Number> series2, Series<String, Number> series3, VBox boxRadio, VBox boxYear, VBox boxMonth, VBox boxCountry, HBox boxButton, HBox boxLanguage, int switchChart, String nameMonth, int year, String nameCountries) {
		
		rootPane = new Pane();
		scrollpane = new ScrollPane();
		scrollpane.setHbarPolicy(ScrollBarPolicy.ALWAYS);
		scrollpane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		scene = null;
		
		// The axes are always reinitialized so that if the number of days changes, the formatting is adjusted.
		xAxis = Translation.xAxisForKey("label.xAxis.day");
		yAxis = Translation.yAxisForKey("label.yAxis.number");

		try {
			XYChart<String, Number> chart = null;
			switch (switchChart) {
			case 1:
				chart = new BarChart<>(xAxis, yAxis);
				break;
			case 2:
				chart = new LineChart<>(xAxis, yAxis);
				break;
			case 3:
			default:
				chart = new AreaChart<>(xAxis, yAxis);
				break;
			}
			
			if(nameMonth == null || nameCountries == null) {
				chart.setTitle("");
			}else {
				chart.setTitle("Daten "+nameMonth+" "+year+" "+nameCountries);
			}
			chart.getData().addAll(series1, series2, series3);
			chart.setLayoutX(225);
			chart.setLayoutY(20);
			chart.setPrefWidth(1350);
			chart.setPrefHeight(850);
			
			rootPane.getChildren().addAll(boxRadio, boxYear, boxMonth, boxCountry, boxButton, boxLanguage, chart);
			scrollpane.setContent(rootPane);
			
			scene = new Scene(scrollpane, 1600, 900);
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
