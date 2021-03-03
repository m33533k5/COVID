package application;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

// Mit dieser Klasse wurden die Daten abgerufen und werden so verarbeitet, dass diese vom Program genutzt werden können

public class RequestDaten {

	private static final String POSTS_API_URL = "https://interaktiv.morgenpost.de/data/corona/history.compact.json";
	private static JsonArray arr;
	
	public ArrayList<LaenderObjekte> getData() throws IOException, InterruptedException {
		arr = getRequestData();
		
		int maxAnzahlLaender = getMaxLaender(arr);
		
		for(int i = 0; i < maxAnzahlLaender; i++) {
			arr.get(i).getAsJsonArray().remove(13);
			arr.get(i).getAsJsonArray().remove(9);
			arr.get(i).getAsJsonArray().remove(8);
			arr.get(i).getAsJsonArray().remove(7);
			arr.get(i).getAsJsonArray().remove(6);
			arr.get(i).getAsJsonArray().remove(5);
			arr.get(i).getAsJsonArray().remove(4);
		}
		
		int maxTage = getMaxTage(arr, maxAnzahlLaender);
				
		ArrayList<String> kuerzel = new ArrayList<String>();
		ArrayList<String> name = new ArrayList<String>();
		ArrayList<String> parentKuerzel = new ArrayList<String>();
		
		kuerzel = getListNamenKuerzel(arr, kuerzel, maxAnzahlLaender, 0, false);
		name = getListNamenKuerzel(arr, name, maxAnzahlLaender, 2, false);
		parentKuerzel = getListNamenKuerzel(arr, parentKuerzel, maxAnzahlLaender, 1, false);
		
		ArrayList<ArrayList<Number>> listeInfizierte = new ArrayList<ArrayList<Number>>();
		ArrayList<Number> werteInfizierte = new ArrayList<Number>();
		ArrayList<ArrayList<Number>> listeGeheilte = new ArrayList<ArrayList<Number>>();
		ArrayList<Number> werteGeheilte = new ArrayList<Number>();
		ArrayList<ArrayList<Number>> listeTote = new ArrayList<ArrayList<Number>>();
		ArrayList<Number> werteTote = new ArrayList<Number>();
		
		listeInfizierte = getListDaten(arr, listeInfizierte, werteInfizierte, maxAnzahlLaender, maxTage, 4);
		listeGeheilte = getListDaten(arr, listeGeheilte, werteGeheilte, maxAnzahlLaender, maxTage, 5);
		listeTote = getListDaten(arr, listeTote, werteTote, maxAnzahlLaender, maxTage, 6);		
		
		ArrayList<LaenderObjekte> meineDaten = new ArrayList<LaenderObjekte>();
		for(int i = 0; i < maxAnzahlLaender; i++) {
			meineDaten.add(new LaenderObjekte(kuerzel.get(i).replaceAll("\"",""), parentKuerzel.get(i).replaceAll("\"",""), name.get(i).replaceAll("\"",""), listeInfizierte.get(i), listeGeheilte.get(i), listeTote.get(i)));
		}
		
		/*
		System.out.println("Kuerzel:"+meineDaten.get(0).getKuerzel() +" parentKuerzel: "+ meineDaten.get(0).getParentKuerzel() +" Name: "+ meineDaten.get(0).getName() + meineDaten.get(0).getInfizierte());
		System.out.println("Kuerzel:"+meineDaten.get(93).getKuerzel() +" parentKuerzel: "+ meineDaten.get(93).getParentKuerzel() +" Name: "+ meineDaten.get(93).getName() + meineDaten.get(93).getInfizierte());
		System.out.println("Kuerzel:"+meineDaten.get(94).getKuerzel() +" parentKuerzel: "+ meineDaten.get(94).getParentKuerzel() +" Name: "+ meineDaten.get(94).getName() + meineDaten.get(94).getInfizierte());
		System.out.println("Kuerzel:"+meineDaten.get(95).getKuerzel() +" parentKuerzel: "+ meineDaten.get(95).getParentKuerzel() +" Name: "+ meineDaten.get(95).getName() + meineDaten.get(95).getInfizierte());
		System.out.println("Kuerzel:"+meineDaten.get(96).getKuerzel() +" parentKuerzel: "+ meineDaten.get(96).getParentKuerzel() +" Name: "+ meineDaten.get(96).getName() + meineDaten.get(96).getInfizierte());
		System.out.println("Kuerzel:"+meineDaten.get(97).getKuerzel() +" parentKuerzel: "+ meineDaten.get(97).getParentKuerzel() +" Name: "+ meineDaten.get(97).getName() + meineDaten.get(97).getInfizierte());
		*/
		//System.out.println(meineDaten.get(93).getInfizierte().size());
		
		return meineDaten;
	}
	
	private JsonArray getRequestData() throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().GET().header("accept", "asdasdasdasd/json")
				.uri(URI.create(POSTS_API_URL)).build();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

		@SuppressWarnings("deprecation")
		JsonParser parser = new JsonParser();
		@SuppressWarnings("deprecation")
		JsonElement el = parser.parse(response.body());

		JsonArray arr = el.getAsJsonArray();
		
		return arr;
	}
	
	private  ArrayList<String> getListNamenKuerzel (JsonArray array, ArrayList<String> liste, int anzahl, int position, boolean children){
		for(int i = 0; i < anzahl;i++) {
			if(array.get(i).getAsJsonArray().get(1).toString().equals("0") && children == false) {
				liste.add(array.get(i).getAsJsonArray().get(position).toString());
			}else if(!array.get(i).getAsJsonArray().get(1).toString().equals("0") && children == true){
				liste.add(array.get(i).getAsJsonArray().get(position).toString());
			}else if(!array.get(i).getAsJsonArray().get(1).toString().equals("0") && children == false){
				liste.add(array.get(i).getAsJsonArray().get(position).toString());
			}else {
				liste.add("");
			}
		}
		return liste;
	}

	private  ArrayList<ArrayList<Number>> getListDaten (JsonArray array, ArrayList<ArrayList<Number>> daten, ArrayList<Number> werte, int anzahlLaender, int anzahlTage, int position){
		for(int i = 0; i < anzahlLaender; i++) {
			werte = new ArrayList<Number>();
			for(int k = 0; k < anzahlTage; k++) {
				if(k < arr.get(i).getAsJsonArray().get(4).getAsJsonArray().size()) {
					werte.add(k,Integer.parseInt(arr.get(i).getAsJsonArray().get(position).getAsJsonArray().get(k).toString()));
				}else {
					werte.add(0,0);
				}
			}
			daten.add(i,werte);
		}
		return daten;
	}
	
	public int getMaxLaender(JsonArray array) {
		return array.size();
	}
	
	public int getMaxTage(JsonArray array, int maxLaender) {
		int maxTage = 0;
		for(int i = 0; i < maxLaender;i++) {
			if(maxTage < arr.get(i).getAsJsonArray().get(4).getAsJsonArray().size() ) maxTage = array.get(i).getAsJsonArray().get(4).getAsJsonArray().size();
		}
		return maxTage;
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		RequestDaten rd = new RequestDaten();
		rd.getData();
	}
}