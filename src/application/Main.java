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
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
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

	// Listen, um die benoetigten Daten dynamisch hinzuzufuegen
	private ArrayList<String> tage = new ArrayList<String>();
	private ArrayList<Number> tot = new ArrayList<Number>();
	private ArrayList<Number> genesen = new ArrayList<Number>();
	private ArrayList<Number> erkrankt = new ArrayList<Number>();

	Stage primaryStage;

	// Wenn die Diagramart gewechselt wird, dann wird der Wechsel als Zahl gespeichert
	private int wechselChart = 1;
	
	// Variablen für das Programm
	private int monat = 12;
	private int jahr = 2020;
	private String nameLand;
	private String nameBundesland;
	private String nameMonat = "Dezember";
	private String name;
	private long diff_maximum = 0;
	private long diff_minimum = 0;
	
	//Aufruf der Klasse, um auf die Objekte / Methoden zuzugreifen
	RequestDaten rd = new RequestDaten();
	
	private VBox boxRadio = new VBox();
	private VBox boxJahr = new VBox();
	private VBox boxMonat = new VBox();
	private VBox boxLaender = new VBox();
	private VBox boxBundeslaender = new VBox();
	
	// Aufruf der Klasse, um das Diagram zu erzeugen
	GenerierungDatenFuerDiagram gdfd = new GenerierungDatenFuerDiagram();
	
	// Diese Methode ist für das anzeigen eines Bundeslandes / Bundesstaates etc. verantwortlich
	// Wenn es vorhanden ist
	private ObservableList<String> anzeigeBundesland(String Land, ArrayList<LaenderObjekte> daten) {
		ObservableList<String> bundesland = FXCollections.observableArrayList();
		for(int i = 0; i < daten.size(); i++) {
			if(Land.equals(daten.get(i).getName())) {
				String kuerzel = daten.get(i).getKuerzel();
				for(int k = 0; k < daten.size(); k++) {
					if(kuerzel.equals(daten.get(k).getParentKuerzel())) {
						bundesland.add(daten.get(k).getName());
					}
				}
			}
		}
		Collections.sort(bundesland);
		return bundesland;
	}
	
	// Fehlermeldungen werden über diese Methode ausgegeben
	private void fehlermeldung(int fehlerTyp) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Warnung");
		alert.setHeaderText("Vorsicht, ein Fehler ist aufgetreten!");
		switch(fehlerTyp) {
		case 1:
			alert.setContentText("Sie haben kein Land ausgewaehlt.");
			break;
		case 2:
			alert.setContentText("Fuer diesen Monat liegen noch nicht die Daten zur Verfuegung. Bitte den Monat aendern.");
			break;
		}
		alert.showAndWait();
	}
	
	// Wenn änderungen der Daten passieren in dieser Methode
	private void updateChart(ArrayList<LaenderObjekte> meineDaten, String name) {
		
		System.out.println("Parameter: "+name);
		
    	tage = new ArrayList<String>();
    	tot = new ArrayList<Number>();
    	genesen = new ArrayList<Number>();
    	erkrankt = new ArrayList<Number>();
		
		BerechnungDifferenz bd = new BerechnungDifferenz(monat,jahr);
    	diff_maximum = bd.getDiffAnfang();
    	diff_minimum = bd.getDiffEnde();
		
		if(diff_maximum > 0 || diff_minimum > 0) {
    		System.out.println(1);
    		for(int k = 0; k < meineDaten.size(); k++) {
    			if(name.equals(meineDaten.get(k).getName())) {
    				System.out.println("Test3: "+name);
    				System.out.println("Test4: "+meineDaten.get(k).getName());
    				System.out.println("Minimum: "+Math.toIntExact(diff_minimum));
    				System.out.println("Maximum: "+Math.toIntExact(diff_maximum));
    				System.out.println("Start:" +(meineDaten.get(k).getInfizierte().size()-Math.toIntExact(diff_minimum)));
    				System.out.println("Ende: " + (meineDaten.get(k).getInfizierte().size()-Math.toIntExact(diff_maximum)));
    				int ende = (meineDaten.get(k).getInfizierte().size()-Math.toIntExact(diff_maximum));
    	    		for(int i = meineDaten.get(k).getInfizierte().size()-Math.toIntExact(diff_maximum); i <= meineDaten.get(k).getInfizierte().size()-Math.toIntExact(diff_minimum); i++) {
    	    			if(diff_maximum > meineDaten.get(k).getInfizierte().size()) {
    	    				System.out.println("groeße: "+meineDaten.get(k).getInfizierte().size());
    	    	    		fehlermeldung(2);
    	    	    		break;
    	    			}else {
	    					tage.add(Integer.toString((i+1)-(meineDaten.get(k).getInfizierte().size()-Math.toIntExact(diff_maximum))));
		        			erkrankt.add(meineDaten.get(k).getInfizierte().get(i));
		        			genesen.add(meineDaten.get(k).getGeheilte().get(i));
		        			tot.add(meineDaten.get(k).getTote().get(i));
    	    			}
    	    		}
    	    		//System.out.println(meineDaten.get(k).getInfizierte().get(i)+" "+meineDaten.get(k).getGeheilte().get(i)+ " "+meineDaten.get(k).getTote().get(i));
    			}
    		}
    		
    		System.out.println(3);
    		Series<String, Number> datenGenesene = gdfd.generierungDatenDiagram("Genesen", tage, genesen);
    		System.out.println("fertig");
    		Series<String, Number> datenGestorbene = gdfd.generierungDatenDiagram("Gestorben", tage, tot);
    		System.out.println("fertig");
    		Series<String, Number> datenErkrankte = gdfd.generierungDatenDiagram("Erkrankt", tage, erkrankt);
    		System.out.println("fertig");
    		
    		GenerierungDiagram gd5 = new GenerierungDiagram(primaryStage, datenGestorbene, datenGenesene, datenErkrankte,
    				wechselChart, boxRadio, boxJahr, boxMonat, boxLaender, boxBundeslaender, nameMonat, jahr, name);
    		}else{
	    		fehlermeldung(2);
    	}
	}
	
	private void boxErstellen( Label labelName, VBox boxName, int xLayout, int yLayout, Node objName, String tooltipText) {
		labelName.setStyle("-fx-font-weight: bold;");
		labelName.setTooltip(new Tooltip(tooltipText));
		boxName.setLayoutX(xLayout);
		boxName.setLayoutY(yLayout);
		boxName.getChildren().addAll(labelName, objName);
	}
	
	private void listeErstellen(ListView<String> listName, int scrollTo, int hoehe, int breite, ObservableList<String> OLName) {
		Collections.sort(OLName);
		listName.setItems(OLName);
		listName.scrollTo(scrollTo);
		listName.setPrefWidth(breite);
		listName.setPrefHeight(hoehe);
	}
	
	private void pruefungLand(String nameLand, String nameBundesland, ArrayList<LaenderObjekte> meineDaten) {
		if(nameBundesland == null && nameLand == null) {
			fehlermeldung(1);
		}else if(nameBundesland == null) {
			System.out.println("Versuch3");
			updateChart(meineDaten, nameLand);
		}else {
			System.out.println("Versuch4");
			updateChart(meineDaten, nameBundesland);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage primaryStage) throws IOException, InterruptedException {
			
		this.primaryStage = primaryStage;
		ArrayList<LaenderObjekte> meineDaten = rd.getData();
		
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		//Erstellung der Radio Buttons
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		Label radioLabel = new Label("Diagramarten:");
		radioLabel.setStyle("-fx-font-weight: bold;");
		final ToggleGroup chartNumber = new ToggleGroup();
		RadioButton buttonBalken = new RadioButton("Balkendiagram");
		buttonBalken.setToggleGroup(chartNumber);
		buttonBalken.setUserData(1);
		buttonBalken.setSelected(true);
		RadioButton buttonLinie = new RadioButton("Liniendiagram");
		buttonLinie.setToggleGroup(chartNumber);
		buttonLinie.setUserData(2);
		RadioButton buttonFlaeche = new RadioButton("Flaechendiagram");
		buttonFlaeche.setToggleGroup(chartNumber);
		buttonFlaeche.setUserData(3);

		boxRadio.setLayoutX(8);
		boxRadio.setLayoutY(55);
		boxRadio.getChildren().addAll(radioLabel, buttonBalken, buttonLinie, buttonFlaeche);
		
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		//Erstellung der Liste für das Jahr
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		Label choiceLabelJahr = new Label("Jahr:");
		ChoiceBox<Integer> choiceJahr = new ChoiceBox<Integer>();
		choiceJahr.getItems().add(2019);
		choiceJahr.getItems().add(2020);
		choiceJahr.getItems().add(2021);
		choiceJahr.setValue(2020);
		boxErstellen(choiceLabelJahr, boxJahr, 8, 130, choiceJahr, "Waehle das Jahr");
		
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		//Erstellung der Liste für den Monat
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		
		Label choiceLabelMonat = new Label("Monat:");
		ChoiceBox<String> choiceMonat = new ChoiceBox<String>(FXCollections.observableArrayList("Januar", "Februar",
				"Maerz", "April", "Mai", "Juni", "July", "August", "September", "Oktober", "November", "Dezember"));
		choiceMonat.setValue("Dezember");
		boxErstellen(choiceLabelMonat, boxMonat, 100, 130, choiceMonat, "Waehle den Monat");
		
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		//Erstellung der Liste für das Land
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		
		Label landLabel = new Label("Land:");
		ListView<String> laenderListe = new ListView<String>();
		ObservableList<String> laender = FXCollections.observableArrayList();		
		for(int i = 0; i < meineDaten.size(); i++) {
			if(meineDaten.get(i).getParentKuerzel().equals("0")) {
				laender.add(meineDaten.get(i).getName());
			}
		}
		listeErstellen(laenderListe, 40, 215, 210, laender);
		boxErstellen(landLabel, boxLaender, 8, 180, laenderListe, "Waehle ein Land aus!");
		
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		//Erstellung der Liste für Bundeslaender / Provinzen / Staaten
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		
		Label bundeslandLabel = new Label("Bundesland / Bundesstaat / Staat etc.:");
		ListView<String> bundeslandListe = new ListView<String>();
		ObservableList<String> bundesland = FXCollections.observableArrayList();	
		bundesland = anzeigeBundesland("Deutschland", meineDaten);
    	listeErstellen(bundeslandListe, 7, 200, 210, bundesland);
		boxErstellen(bundeslandLabel, boxBundeslaender, 8, 410, bundeslandListe, "Waehle ein Bundesland aus!");
		
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		//Erstellung des Listeners für das Listenfeld Jahr
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~			
		
		choiceJahr.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
	        public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
	        	System.out.println(choiceJahr.getItems().get((Integer) newValue));
	        	jahr = choiceJahr.getItems().get((Integer) newValue);
	        	
        		System.out.println("Test1: "+nameLand);
        		System.out.println("Test2: "+ meineDaten.size());
	        	
        		pruefungLand(nameLand, nameBundesland, meineDaten);
	        }
	      });	
		
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		//Erstellung des Listeners für das Listenfeld Monat
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~			
		
		choiceMonat.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
	        @Override
	        public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
	          
	        	nameMonat = newValue;
	        	
	        	/*
	        	int monat2 = Integer.parseInt(choiceMonat.getSelectionModel().selectedIndexProperty().toString());
	        	System.out.println(monat2);
	            */
	        	
	        	if(newValue.equals("Januar")) monat = 1;
	        	if(newValue.equals("Februar")) monat = 2;      
	        	if(newValue.equals("Maerz")) monat = 3;
	        	if(newValue.equals("April")) monat = 4;
	        	if(newValue.equals("Mai")) monat = 5;
	        	if(newValue.equals("Juni")) monat = 6;
	        	if(newValue.equals("July")) monat = 7;
	        	if(newValue.equals("August")) monat = 8;
	        	if(newValue.equals("September")) monat = 9;
	        	if(newValue.equals("Oktober")) monat = 10;
	        	if(newValue.equals("November")) monat = 11;
	        	if(newValue.equals("Dezember")) monat = 12;
	        	
	        	System.out.println("Monat: "+nameMonat);
        		System.out.println("Land: "+nameLand);
        		System.out.println("Daten: "+meineDaten.size());
	        	
        		pruefungLand(nameLand, nameBundesland, meineDaten);
        		
	        	System.out.println(diff_maximum + " "+ diff_minimum);
	        }
	      });	
		
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		//Erstellung des Listeners für das Bundesland / Bundesland / Staat
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~		
		
		bundeslandListe.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		        // Your action here
		        System.out.println("Test10: "+newValue);
		        nameBundesland = newValue;
		        name = newValue;
		        System.out.println("Name_Land: "+name);
		    }
		});
		
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		//Erstellung des Listeners für das Land
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~		
		
		laenderListe.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		        // Your action here
		        System.out.println("Test9: "+newValue);
		        nameLand = newValue;
		        name= newValue;
		        System.out.println("Name_Bundesland: "+name);
	    		bundeslandListe.setItems(anzeigeBundesland(nameLand,meineDaten));
		    }
		});
		
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		//Erstellung des Listeners für die RadioBox, um das Chart zu wechseln
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~	
		
		chartNumber.selectedToggleProperty().addListener((obserableValue, old_toggle, new_toggle) -> {
		    if(Integer.parseInt(chartNumber.getSelectedToggle().getUserData().toString()) == 1) {
		    	wechselChart = 1;
		    	updateChart(meineDaten,name);
		    }else if(Integer.parseInt(chartNumber.getSelectedToggle().getUserData().toString()) == 2){
		    	wechselChart = 2;
		    	updateChart(meineDaten,name);
		    }else if(Integer.parseInt(chartNumber.getSelectedToggle().getUserData().toString()) == 3) {
		    	wechselChart = 3;
		    	updateChart(meineDaten,name);
		    }
		}); 

		// Aufruf der Klasse, um die Liste mit Werten zu befüllen, die für Tage genutzt
		// wird
		ListeBefuellen lb = new ListeBefuellen();
		
		tage = lb.listeBefuellenString(tage, 0);
		tot = lb.listeBefuellenNumber(tot, 4);
		genesen = lb.listeBefuellenNumber(genesen, 5);
		erkrankt = lb.listeBefuellenNumber(erkrankt, 6);

		// Anlegen einer Serie mit den jeweiligen Werten und dem Item dem sie zugeordnet
		// werden
		// Wird nachfolgend als Parameter verwendet
		Series<String, Number> datenErkrankte = gdfd.generierungDatenDiagram("Gestorben", tage, erkrankt);
		Series<String, Number> datenGenesene = gdfd.generierungDatenDiagram("Genesen", tage, genesen);
		Series<String, Number> datenGestorbene = gdfd.generierungDatenDiagram("Erkrankt", tage, tot);
		
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		//Erstellung des Listeners für den Radiobutton
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~		
		
		GenerierungDiagram gd = new GenerierungDiagram(primaryStage, datenErkrankte, datenGenesene, datenGestorbene,
				1, boxRadio, boxJahr, boxMonat, boxLaender, boxBundeslaender, nameMonat, jahr, "Mecklenburg Vorpommern");
	}

	public static void main(String[] args) {
		launch(args);
	}
}
