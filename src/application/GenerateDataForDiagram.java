package application;

import java.util.ArrayList;

import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;

public class GenerateDataForDiagram {

	// Function for creating the data to be displayed in the diagram
	// name = legend entry; Tag / day = the respective day in the month; Wert / values = numerical value
	// for the respective day
	public Series<String, Number> generateDataForDiagram(String name, ArrayList<String> day, ArrayList<Number> number) {
		XYChart.Series<String, Number> dataSeries = new XYChart.Series<String, Number>();
		// set Legend entry for Diagram
		dataSeries.setName(name);
		// Loop to assign the values to the day and the corresponding values.
		for (int i = 0; i < day.size(); i++) {
			dataSeries.getData().add(new XYChart.Data<String, Number>(day.get(i), number.get(i)));
		}
		return dataSeries;

	}
}
