package application;

import java.io.IOException;
import java.util.ArrayList;

public class test {

	public static void main(String[] args) throws IOException, InterruptedException {
		
		RequestDaten rd = new RequestDaten();
		ArrayList<LaenderObjekte> meineDaten = rd.getData();
		
		System.out.println(meineDaten.get(93).getName());
		System.out.println(meineDaten.get(93).getInfizierte());
		System.out.println(meineDaten.get(93).getGeheilte());
		System.out.println(meineDaten.get(93).getTote());
	}
}
