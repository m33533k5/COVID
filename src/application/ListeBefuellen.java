package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ListeBefuellen {

	public ArrayList<String> listeBefuellenString(ArrayList<String> liste, int position) {
		// Einlesen der Daten aus der Textdatei und Speicherung in einem Listenfeld
		Scanner scan = null;
		// Wenn die Datei nicht geladen werden kann, dann soll die Fehlermeldung
		// ausgegeben werden
		try {
			// Einlesen der Textdatei, Datei liegt im Projektordner
			scan = new Scanner(new File("Daten_Corona.txt"));
			// Ausgabe der Fehlermeldung
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// Schleife um die Zeilen der Textdatei auszulesen
		while (scan.hasNext()) {
			String line = scan.nextLine();
			String[] s = line.split("\t");
			liste.add(s[position]);
		}
		scan.close();
		// Liste wird zurückgegeben
		return liste;
	}

	// Einlesen der Daten aus der Textdatei und Speicherung in einem Listenfeld
	// (Number)
	public ArrayList<Number> listeBefuellenNumber(ArrayList<Number> liste, int position) {
		// Einlesen der Daten aus der Textdatei und Speicherung in einem Listenfeld
		Scanner scan = null;
		// Wenn die Datei nicht geladen werden kann, dann soll die Fehlermeldung
		// ausgegeben werden
		try {
			// Einlesen der Textdatei, Datei liegt im Projektordner
			scan = new Scanner(new File("Daten_Corona.txt"));
			// Ausgabe der Fehlermeldung
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// Schleife um die Zeilen der Textdatei auszulesen
		while (scan.hasNext()) {
			String line = scan.nextLine();
			String[] s = line.split("\t");
			liste.add(Integer.parseInt(s[position]));
		}
		scan.close();
		// Liste wird zurückgegeben
		return liste;
	}

}
