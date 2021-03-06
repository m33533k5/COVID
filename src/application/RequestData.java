package application;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

// Mit dieser Klasse wurden die Daten abgerufen und werden so verarbeitet, dass diese vom Program genutzt werden können

public class RequestData {

	private static final String POSTS_API_URL = "https://interaktiv.morgenpost.de/data/corona/history.compact.json";
	private static JsonArray arr;
	
	public ArrayList<CountrieObjects> getData() throws IOException, InterruptedException {
		arr = getRequestData();
		//Here the maximum number of countries and states is stored. 
		int maxNumberCountrys = getMaxNumberCountrys(arr);
		
		//Data not needed for me will be removed from the file
		for(int i = 0; i < maxNumberCountrys; i++) {
			arr.get(i).getAsJsonArray().remove(13);
			arr.get(i).getAsJsonArray().remove(9);
			arr.get(i).getAsJsonArray().remove(8);
			arr.get(i).getAsJsonArray().remove(7);
			arr.get(i).getAsJsonArray().remove(6);
			arr.get(i).getAsJsonArray().remove(5);
			arr.get(i).getAsJsonArray().remove(4);
		}
		
		//Which country has the most documented days?
		//The number is used to normalize other countries. 
		//For example: If China has 400 documented days and Germany has only 395, 
		//then the first five days for Germany will be filled with a 0.
		int maxDays = getMaxDays(arr, maxNumberCountrys);
				
		ArrayList<String> identifier = new ArrayList<String>();
		ArrayList<String> name = new ArrayList<String>();
		ArrayList<String> parentIdentifier = new ArrayList<String>();
		
		identifier = getListNameIdentifier(arr, identifier, maxNumberCountrys, 0, false);
		name = getListNameIdentifier(arr, name, maxNumberCountrys, 2, false);
		parentIdentifier = getListNameIdentifier(arr, parentIdentifier, maxNumberCountrys, 1, false);
		
		ArrayList<ArrayList<Number>> listInfected = new ArrayList<ArrayList<Number>>();
		ArrayList<Number> valuesInfected = new ArrayList<Number>();
		ArrayList<ArrayList<Number>> listHealed = new ArrayList<ArrayList<Number>>();
		ArrayList<Number> valuesHealed = new ArrayList<Number>();
		ArrayList<ArrayList<Number>> listDead = new ArrayList<ArrayList<Number>>();
		ArrayList<Number> valuesDead = new ArrayList<Number>();
		
		listInfected = getListData(arr, listInfected, valuesInfected, maxNumberCountrys, maxDays, 4);
		listHealed = getListData(arr, listHealed, valuesHealed, maxNumberCountrys, maxDays, 5);
		listDead = getListData(arr, listDead, valuesDead, maxNumberCountrys, maxDays, 6);		
		
		ArrayList<CountrieObjects> myData = new ArrayList<CountrieObjects>();
		for(int i = 0; i < maxNumberCountrys; i++) {
			myData.add(new CountrieObjects(identifier.get(i).replaceAll("\"",""), parentIdentifier.get(i).replaceAll("\"",""), name.get(i).replaceAll("\"",""), listInfected.get(i), listHealed.get(i), listDead.get(i)));
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
		
		return myData;
	}
	
	@SuppressWarnings("deprecation")
	private JsonArray getRequestData() throws IOException, InterruptedException {
		ErrorMessage em = new ErrorMessage();
		JsonArray arr = new JsonArray();
		JsonElement el;
		JsonParser parser = new JsonParser();
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().GET().header("accept", "asdasdasdasd/json")
				.uri(URI.create(POSTS_API_URL)).build();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		int statuscode = response.statusCode();
		if(statuscode != 200) {	
			try {
				em.errorMessage(3);
				Object obj = parser.parse(new FileReader("jsonData.json"));
				JsonElement el2 = parser.parse(obj.toString());
				arr = el2.getAsJsonArray();
			} catch (Exception e) {
				e.printStackTrace();
				em.errorMessage(4);
				System.exit(0);
			}
			return arr;
		}else {
			System.out.println(statuscode);
			el = parser.parse(response.body());
			arr = el.getAsJsonArray();
			FileWriter file = new FileWriter("jsonData.json");
			try {
				file.write(arr.toString());
			}catch(IOException e) {
				e.printStackTrace();
			}
			file.flush();
			file.close();
			try {
				Object obj = parser.parse(new FileReader("jsonData.json"));
				JsonElement el2 = parser.parse(obj.toString());
				arr = el2.getAsJsonArray();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return arr;
		}
	}
	
	private  ArrayList<String> getListNameIdentifier (JsonArray array, ArrayList<String> list, int number, int position, boolean children){
		for(int i = 0; i < number;i++) {
			if(array.get(i).getAsJsonArray().get(1).toString().equals("0") && children == false) {
				list.add(array.get(i).getAsJsonArray().get(position).toString());
			}else if(!array.get(i).getAsJsonArray().get(1).toString().equals("0") && children == true){
				list.add(array.get(i).getAsJsonArray().get(position).toString());
			}else if(!array.get(i).getAsJsonArray().get(1).toString().equals("0") && children == false){
				list.add(array.get(i).getAsJsonArray().get(position).toString());
			}else {
				list.add("");
			}
		}
		return list;
	}

	private  ArrayList<ArrayList<Number>> getListData (JsonArray array, ArrayList<ArrayList<Number>> data, ArrayList<Number> values, int numberOfCountries, int numberOfDays, int position){
		for(int i = 0; i < numberOfCountries; i++) {
			values = new ArrayList<Number>();
			for(int k = 0; k < numberOfDays; k++) {
				if(k < arr.get(i).getAsJsonArray().get(4).getAsJsonArray().size()) {
					values.add(k,Integer.parseInt(arr.get(i).getAsJsonArray().get(position).getAsJsonArray().get(k).toString()));
				}else {
					values.add(0,0);
				}
			}
			data.add(i,values);
		}
		return data;
	}
	
	public int getMaxNumberCountrys(JsonArray array) {
		return array.size();
	}
	
	public int getMaxDays(JsonArray array, int maxNumberOfCountries) {
		int maxDay = 0;
		for(int i = 0; i < maxNumberOfCountries;i++) {
			if(maxDay < arr.get(i).getAsJsonArray().get(4).getAsJsonArray().size() ) maxDay = array.get(i).getAsJsonArray().get(4).getAsJsonArray().size();
		}
		return maxDay;
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		RequestData rd = new RequestData();
		rd.getData();
	}
}