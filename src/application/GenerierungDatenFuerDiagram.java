package application;

import java.util.ArrayList;

import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;

public class GenerierungDatenFuerDiagram {

	// Funktion für das erstellen der Daten die im Diagram angezeigt werden sollen
	// name = Legendeneintrag; tag = der jeweilige Tag im Monat; zahl = Zahlenwert
	// für den jeweiligen Tag
	public Series<String, Number> generierungDatenDiagram(String name, ArrayList<String> tag, ArrayList<Number> zahl) {
		
		System.out.println(tag);
		System.out.println(zahl);
		
		// Erstellung des Objektes, mit dem in der FUnktion gearbeitet werden soll
		XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
		// Legendeneintrag für die Daten aus dem Diagram
		series.setName(name);
		// Schleife um dem Tag und der dazugehoerigen Zahl die Werte zuzuordnen
		for (int i = 0; i < tag.size(); i++) {
			series.getData().add(new XYChart.Data<String, Number>(tag.get(i), zahl.get(i)));
		}
		// Die Daten werden zurückgegeben
		return series;

	}
}
