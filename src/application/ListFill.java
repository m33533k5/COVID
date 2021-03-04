package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

//This class was my very first attempt to read and visualize data. 
//Actually I could remove this class and do it right away via the current way. 
//It has grown historically. 
//In addition, I wanted to have something with which I can control myself. 
//Therefore, I had copied the data by hand at the first attempt.

public class ListFill {

	public ArrayList<String> listFillString(ArrayList<String> list, int position) {
		Scanner scan = null;
		// If the file cannot be loaded, then the error message should be output
		try {
			// Read in the text file, file is located in the project folder
			scan = new Scanner(new File("Daten_Corona.txt"));
			// Print error message
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// Loop to read out the lines of the text file
		while (scan.hasNext()) {
			String line = scan.nextLine();
			String[] s = line.split("\t");
			list.add(s[position]);
		}
		scan.close();
		return list;
	}

	public ArrayList<Number> listFillNumber(ArrayList<Number> list, int position) {
		Scanner scan = null;
		try {
			scan = new Scanner(new File("Daten_Corona.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while (scan.hasNext()) {
			String line = scan.nextLine();
			String[] s = line.split("\t");
			list.add(Integer.parseInt(s[position]));
		}
		scan.close();
		return list;
	}

}
