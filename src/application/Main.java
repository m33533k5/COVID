package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;

public class Main extends Application {

	// Lists to dynamically add the required data.
	private ArrayList<String> days = new ArrayList<String>();
	private ArrayList<Number> dead = new ArrayList<Number>();
	private ArrayList<Number> healed = new ArrayList<Number>();
	private ArrayList<Number> infected = new ArrayList<Number>();

	Stage primaryStage;

	// If the diagram type is changed, then the change is saved as a number
	private int changeChart = 1;
	
	// Variables for the program
	private int month = 12;
	private int year = 2020;
	private String nameCountrie;
	private String nameState;
	private String nameMonth = "Dezember";
	private String name;
	private long diffFirstDay = 0;
	private long diffLastDay = 0;
	
	// Call the class to access the objects / methods.
	RequestData rd = new RequestData();
	GenerateDataForDiagram gdfd = new GenerateDataForDiagram();
	ErrorMessage em = new ErrorMessage();
	
	// VBox for the Layout
	private VBox boxRadio = new VBox();
	private VBox boxYear = new VBox();
	private VBox boxMonth = new VBox();
	private VBox boxCountrie = new VBox();
	private VBox boxState = new VBox();

	
	// This method is responsible for displaying a state/province etc.
	// If it is present
	private ObservableList<String> showingState(String countrie, ArrayList<CountrieObjects> data) {
		ObservableList<String> state = FXCollections.observableArrayList();
		for(int i = 0; i < data.size(); i++) {
			if(countrie.equals(data.get(i).getName())) {
				String identifier = data.get(i).getIdentifier();
				for(int k = 0; k < data.size(); k++) {
					if(identifier.equals(data.get(k).getParentIdentifier())) {
						state.add(data.get(k).getName());
					}
				}
			}
		}
		Collections.sort(state);
		return state;
	}
	
	// Wenn änderungen der Daten passieren in dieser Methode
	private void updateChart(ArrayList<CountrieObjects> myData, String name) {
		
		System.out.println("Parameter: "+name);
		
    	days = new ArrayList<String>();
    	dead = new ArrayList<Number>();
    	healed = new ArrayList<Number>();
    	infected = new ArrayList<Number>();
		
		CalculateDifference bd = new CalculateDifference(month,year);
    	diffFirstDay = bd.getDiffAnfang();
    	diffLastDay = bd.getDiffEnde();
		
		if(diffFirstDay > 0 || diffLastDay > 0) {
    		System.out.println(1);
    		for(int k = 0; k < myData.size(); k++) {
    			if(name.equals(myData.get(k).getName())) {
    	    		for(int i = myData.get(k).getInfected().size()-Math.toIntExact(diffFirstDay); i <= myData.get(k).getInfected().size()-Math.toIntExact(diffLastDay); i++) {
    	    			if(diffFirstDay > myData.get(k).getInfected().size()) {
    	    	    		em.errorMessage(2);
    	    	    		break;
    	    			}else {
	    					days.add(Integer.toString((i+1)-(myData.get(k).getInfected().size()-Math.toIntExact(diffFirstDay))));
		        			infected.add(myData.get(k).getInfected().get(i));
		        			healed.add(myData.get(k).getHealed().get(i));
		        			dead.add(myData.get(k).getDead().get(i));
    	    			}
    	    		}
    	    		//System.out.println(meineDaten.get(k).getInfizierte().get(i)+" "+meineDaten.get(k).getGeheilte().get(i)+ " "+meineDaten.get(k).getTote().get(i));
    			}
    		}
    		
    		Series<String, Number> dataHealed = gdfd.generateDataForDiagram("Genesen", days, healed);
    		Series<String, Number> dataDead = gdfd.generateDataForDiagram("Gestorben", days, dead);
    		Series<String, Number> dataInfected = gdfd.generateDataForDiagram("Erkrankt", days, infected);
    		
    		GenerateDiagram gd5 = new GenerateDiagram(primaryStage, dataDead, dataHealed, dataInfected,
    				changeChart, boxRadio, boxYear, boxMonth, boxCountrie, boxState, nameMonth, year, name);
    		}else{
	    		em.errorMessage(2);
    	}
	}
	
	private void createBox( Label labelName, VBox boxName, int xLayout, int yLayout, Node objName, String tooltipText) {
		labelName.setStyle("-fx-font-weight: bold;");
		labelName.setTooltip(new Tooltip(tooltipText));
		boxName.setLayoutX(xLayout);
		boxName.setLayoutY(yLayout);
		boxName.getChildren().addAll(labelName, objName);
	}
	
	private void createList(ListView<String> listName, int scrollTo, int height, int width, ObservableList<String> OLName) {
		Collections.sort(OLName);
		listName.setItems(OLName);
		listName.scrollTo(scrollTo);
		listName.setPrefWidth(width);
		listName.setPrefHeight(height);
	}
	
	private void checkCountrieState(String nameCountrie, String nameState, ArrayList<CountrieObjects> myData) {
		if(nameState == null && nameCountrie == null) {
			em.errorMessage(1);
		}else if(nameState == null) {
			System.out.println("Versuch3");
			updateChart(myData, nameCountrie);
		}else {
			System.out.println("Versuch4");
			updateChart(myData, nameState);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage primaryStage) throws IOException, InterruptedException {
			
		this.primaryStage = primaryStage;
		ArrayList<CountrieObjects> myData = rd.getData();
		
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		//Creating Radiobuttons
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		Label labelRadio = new Label("Diagramarten:");
		labelRadio.setStyle("-fx-font-weight: bold;");
		final ToggleGroup chartNumber = new ToggleGroup();
		RadioButton buttonBarChart = new RadioButton("Balkendiagram");
		buttonBarChart.setToggleGroup(chartNumber);
		buttonBarChart.setUserData(1);
		buttonBarChart.setSelected(true);
		RadioButton buttonLineChart = new RadioButton("Liniendiagram");
		buttonLineChart.setToggleGroup(chartNumber);
		buttonLineChart.setUserData(2);
		RadioButton buttonAreaChart = new RadioButton("Flaechendiagram");
		buttonAreaChart.setToggleGroup(chartNumber);
		buttonAreaChart.setUserData(3);

		boxRadio.setLayoutX(8);
		boxRadio.setLayoutY(55);
		boxRadio.getChildren().addAll(labelRadio, buttonBarChart, buttonLineChart, buttonAreaChart);
		
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		//Creating list year
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		Label choiceLabelYear = new Label("Jahr:");
		ChoiceBox<Integer> choiceYear = new ChoiceBox<Integer>();
		choiceYear.getItems().add(2019);
		choiceYear.getItems().add(2020);
		choiceYear.getItems().add(2021);
		choiceYear.setValue(2020);
		createBox(choiceLabelYear, boxYear, 8, 130, choiceYear, "Waehle das Jahr");
		
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		//Creating list month
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		
		Label choiceLabelMonth = new Label("Monat:");
		ChoiceBox<String> choiceMonth = new ChoiceBox<String>(FXCollections.observableArrayList("Januar", "Februar",
				"Maerz", "April", "Mai", "Juni", "July", "August", "September", "Oktober", "November", "Dezember"));
		choiceMonth.setValue("Dezember");
		createBox(choiceLabelMonth, boxMonth, 100, 130, choiceMonth, "Waehle den Monat");
		
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		//Creating list countries
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		
		Label labelCountries = new Label("Land:");
		ListView<String> listCountries = new ListView<String>();
		ObservableList<String> countries = FXCollections.observableArrayList();		
		for(int i = 0; i < myData.size(); i++) {
			if(myData.get(i).getParentIdentifier().equals("0")) {
				countries.add(myData.get(i).getName());
			}
		}
		createList(listCountries, 40, 215, 210, countries);
		createBox(labelCountries, boxCountrie, 8, 180, listCountries, "Waehle ein Land aus!");
		
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		//Creating list state
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		
		Label labelState = new Label("Bundesland / Bundesstaat / Staat etc.:");
		ListView<String> listState = new ListView<String>();
		ObservableList<String> state = FXCollections.observableArrayList();	
		state = showingState("Deutschland", myData);
    	createList(listState, 7, 200, 210, state);
		createBox(labelState, boxState, 8, 410, listState, "Waehle ein Bundesland aus!");
		
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		//Creating listener for list year
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~			
		
		choiceYear.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
	        public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
	        	System.out.println(choiceYear.getItems().get((Integer) newValue));
	        	year = choiceYear.getItems().get((Integer) newValue);
	        	
        		System.out.println("Test1: "+nameCountrie);
        		System.out.println("Test2: "+ myData.size());
	        	
        		checkCountrieState(nameCountrie, nameState, myData);
	        }
	      });	
		
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		//Creating listener for list month
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~			
		
		choiceMonth.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
	        @Override
	        public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
	          
	        	nameMonth = newValue;
	        	
	        	/*
	        	int monat2 = Integer.parseInt(choiceMonat.getSelectionModel().selectedIndexProperty().toString());
	        	System.out.println(monat2);
	            */
	        	
	        	if(newValue.equals("Januar")) month = 1;
	        	if(newValue.equals("Februar")) month = 2;      
	        	if(newValue.equals("Maerz")) month = 3;
	        	if(newValue.equals("April")) month = 4;
	        	if(newValue.equals("Mai")) month = 5;
	        	if(newValue.equals("Juni")) month = 6;
	        	if(newValue.equals("July")) month = 7;
	        	if(newValue.equals("August")) month = 8;
	        	if(newValue.equals("September")) month = 9;
	        	if(newValue.equals("Oktober")) month = 10;
	        	if(newValue.equals("November")) month = 11;
	        	if(newValue.equals("Dezember")) month = 12;
	        	
	        	System.out.println("Monat: "+nameMonth);
        		System.out.println("Land: "+nameCountrie);
        		System.out.println("Daten: "+myData.size());
	        	
        		checkCountrieState(nameCountrie, nameState, myData);
        		
	        	System.out.println(diffFirstDay + " "+ diffLastDay);
	        }
	      });	
		
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		//Creating listener for list state
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~		
		
		listState.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		        // Your action here
		        System.out.println("Test10: "+newValue);
		        nameState = newValue;
		        name = newValue;
		        int index = listCountries.getFocusModel().getFocusedIndex();
		        System.out.println(listCountries.getFocusModel().isFocused(index));
		        listCountries.getFocusModel().focus(-1);
		        System.out.println(listCountries.getFocusModel().isFocused(index));
		        System.out.println("Name_Land: "+name);
		    }
		});
		
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		//Creating listener for list countries
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~		
		
		listCountries.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		        // Your action here
		        System.out.println("Test9: "+newValue);
		        nameCountrie = newValue;
		        name= newValue;
		        System.out.println("Name_Bundesland: "+name);
	    		listState.setItems(showingState(nameCountrie,myData));
		    }
		});
		
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		//Creating listener for radiobutton
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~	
		
		chartNumber.selectedToggleProperty().addListener((obserableValue, old_toggle, new_toggle) -> {
			if(name == null) {
				em.errorMessage(1);
			}else {
				if(Integer.parseInt(chartNumber.getSelectedToggle().getUserData().toString()) == 1) {
			    	changeChart = 1;
			    	updateChart(myData,name);
			    }else if(Integer.parseInt(chartNumber.getSelectedToggle().getUserData().toString()) == 2){
			    	changeChart = 2;
			    	updateChart(myData,name);
			    }else if(Integer.parseInt(chartNumber.getSelectedToggle().getUserData().toString()) == 3) {
			    	changeChart = 3;
			    	updateChart(myData,name);
			    }
			}
		    
		}); 

		// Aufruf der Klasse, um die Liste mit Werten zu befüllen, die für Tage genutzt
		// wird
		ListFill lf = new ListFill();
		
		days = lf.listFillString(days, 0);
		dead = lf.listFillNumber(dead, 4);
		healed = lf.listFillNumber(healed, 5);
		infected = lf.listFillNumber(infected, 6);

		// Anlegen einer Serie mit den jeweiligen Werten und dem Item dem sie zugeordnet
		// werden
		// Wird nachfolgend als Parameter verwendet
		Series<String, Number> dataInfected = gdfd.generateDataForDiagram("Gestorben", days, infected);
		Series<String, Number> dataHealed = gdfd.generateDataForDiagram("Genesen", days, healed);
		Series<String, Number> dataDead = gdfd.generateDataForDiagram("Erkrankt", days, dead);
		
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		//Erstellung des Listeners für den Radiobutton
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~		
		
		GenerateDiagram gd = new GenerateDiagram(primaryStage, dataInfected, dataHealed, dataDead,
				1, boxRadio, boxYear, boxMonth, boxCountrie, boxState, nameMonth, year, "Mecklenburg Vorpommern");
	}

	public static void main(String[] args) {
		launch(args);
	}
}
