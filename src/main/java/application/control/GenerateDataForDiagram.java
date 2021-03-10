package application.control;

import java.util.List;

import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;

public class GenerateDataForDiagram {

	/**
	 * @author Christian Wollmann <br/> <br/>
	 * Function for creating the data to be displayed in the diagram
	 * @param name - legend entry
	 * @param day - the respective day in the month
	 * @param number - numerical value for the respective day
	 * @return
	 */

	public Series<String, Number> generateDataForDiagram(String name, List<String> day, List<Number> number) {
		XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
		// set Legend entry for Diagram
		dataSeries.setName(name);
		// Loop to assign the values to the day and the corresponding values.
		for (int i = 0; i < day.size(); i++) {
			dataSeries.getData().add(new XYChart.Data<>(day.get(i), number.get(i)));
		}
		return dataSeries;

	}
}
