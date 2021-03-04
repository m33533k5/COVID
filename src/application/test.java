package application;

import java.io.IOException;
import java.util.ArrayList;

//Here I test some things. The class has no relevance.

public class test {

	public static void main(String[] args) throws IOException, InterruptedException {
		
		RequestData rd = new RequestData();
		ArrayList<CountrieObjects> meineDaten = rd.getData();
		
		System.out.println(meineDaten.get(93).getName());
		System.out.println(meineDaten.get(93).getInfected());
		System.out.println(meineDaten.get(93).getHealed());
		System.out.println(meineDaten.get(93).getDead());
	}
}
