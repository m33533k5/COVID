package application.control;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import application.Main;
import application.model.CountrieObjects;
import application.view.EnumErrorMessages;
import application.view.ErrorMessage;

/**
 * 
 * @author Christian Wollmann <br/> <br/>
 * With this class, the data was retrieved and is processed so that it can be used by the program.
 */

public class RequestData {

	private static final String POSTS_API_URL = "https://interaktiv.morgenpost.de/data/corona/history.compact.json";
	private JsonArray arr;
	private static final Logger LOGGER = LogManager.getLogger(RequestData.class);
	
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
				
		ArrayList<String> identifier = new ArrayList<>();
		ArrayList<String> name = new ArrayList<>();
		ArrayList<String> parentIdentifier = new ArrayList<>();
		
		identifier = getListNameIdentifier(arr, identifier, maxNumberCountrys, 0, false);
		name = getListNameIdentifier(arr, name, maxNumberCountrys, 2, false);
		parentIdentifier = getListNameIdentifier(arr, parentIdentifier, maxNumberCountrys, 1, false);
		
		ArrayList<ArrayList<Number>> listInfected = new ArrayList<ArrayList<Number>>();
		ArrayList<Number> valuesInfected = new ArrayList<>();
		ArrayList<ArrayList<Number>> listHealed = new ArrayList<ArrayList<Number>>();
		ArrayList<Number> valuesHealed = new ArrayList<>();
		ArrayList<ArrayList<Number>> listDead = new ArrayList<ArrayList<Number>>();
		ArrayList<Number> valuesDead = new ArrayList<>();
		
		listInfected = getListData(listInfected, valuesInfected, maxNumberCountrys, maxDays, 4);
		listHealed = getListData(listHealed, valuesHealed, maxNumberCountrys, maxDays, 5);
		listDead = getListData(listDead, valuesDead, maxNumberCountrys, maxDays, 6);		
		
		ArrayList<CountrieObjects> myData = new ArrayList<>();
		for(int i = 0; i < maxNumberCountrys; i++) {
			myData.add(new CountrieObjects(identifier.get(i).replace("\"",""), parentIdentifier.get(i).replace("\"",""), name.get(i).replace("\"",""), listInfected.get(i), listHealed.get(i), listDead.get(i)));
		}
		
		return myData;
	}
	
	@SuppressWarnings("deprecation")
	private JsonArray getRequestData() throws IOException, InterruptedException {
		String fileURL = "jsonData.json";
		JsonElement el;
		JsonParser parser = new JsonParser();
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().GET().header("accept", "asdasdasdasd/json")
				.uri(URI.create(POSTS_API_URL)).build();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		int statuscode = response.statusCode();
		if(statuscode != 200) {	
			try {
				ErrorMessage.errorMessage(EnumErrorMessages.ERROR_DATA_LOAD_ONLINE);
				Object obj = parser.parse(new FileReader(fileURL));
				JsonElement el2 = parser.parse(obj.toString());
				arr = el2.getAsJsonArray();
			} catch (Exception e) {
				e.printStackTrace();
				ErrorMessage.errorMessage(EnumErrorMessages.ERROR_DATA_LOAD_LOCAL);
				System.exit(0);
			}
			return arr;
		}else {
			LOGGER.debug("statuscode = {}", statuscode);
			el = parser.parse(response.body());
			arr = el.getAsJsonArray();
			FileWriter file = new FileWriter(fileURL);
			try {
				file.write(arr.toString());
			}catch(IOException e) {
				e.printStackTrace();
			}
			file.flush();
			file.close();
			
			try {
				Object obj = parser.parse(new FileReader(fileURL));
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
			if(array.get(i).getAsJsonArray().get(1).toString().equals("0") && !children) {
				list.add(array.get(i).getAsJsonArray().get(position).toString());
			}else if(!array.get(i).getAsJsonArray().get(1).toString().equals("0") && children){
				list.add(array.get(i).getAsJsonArray().get(position).toString());
			}else if(!array.get(i).getAsJsonArray().get(1).toString().equals("0") && !children){
				list.add(array.get(i).getAsJsonArray().get(position).toString());
			}else {
				list.add("");
			}
		}
		return list;
	}

	private  ArrayList<ArrayList<Number>> getListData (ArrayList<ArrayList<Number>> data, ArrayList<Number> values, int numberOfCountries, int numberOfDays, int position){
		for(int i = 0; i < numberOfCountries; i++) {
			values = new ArrayList<>();
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