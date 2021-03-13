package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import application.control.GenerateDataForDiagram;
import application.control.RequestData;
import application.control.Translation;
import application.model.CalculateDifference;
import application.model.CountrieObjects;
import application.model.EnumMonth;
import application.view.EnumErrorMessages;
import application.view.ErrorMessage;
import application.view.GenerateDiagram;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class Main extends Application implements ErrorMessage{
	
	//Main.class sagt, dass dieser logger in dieser klasse registriert ist
	private static final Logger LOGGER = LogManager.getLogger(Main.class);

	private Stage primaryStage;

	// If the diagram type is changed, then the change is saved as a number
	private int changeChart=-1;
	
	private String labelFont = "-fx-font-weight: bold;";
	
	// Variables for the program
	private int month = 12;
	private int year = 2020;
	private String nameCountries;
	private String nameMonth;
	
	// Call the class to access the objects / methods.
	RequestData rd = new RequestData();
	GenerateDataForDiagram gdfd = new GenerateDataForDiagram();
	
	// VBox for the Layout
	private VBox boxRadio = new VBox();
	private VBox boxYear = new VBox();
	private VBox boxMonth = new VBox();
	private VBox boxCountrie = new VBox();
	private VBox boxState = new VBox();
	private HBox boxButton = new HBox();
	
	// Wenn aenderungen der Daten passieren in dieser Methode
	private void updateChart(ArrayList<CountrieObjects> myData, String name) {
		
		LOGGER.debug("Parameter = {}", name);
	
		CalculateDifference bd = new CalculateDifference(month,year);
		long diffFirstDay = bd.getDiffStart();
		long diffLastDay = bd.getDiffEnd();
		List<String> days = new ArrayList();
		List<Number> dead = new ArrayList();
		List<Number> healed = new ArrayList();
		List<Number> infected = new ArrayList();
		
		if(diffFirstDay > 0 || diffLastDay > 0) {
    		for(int k = 0; k < myData.size(); k++) {
    			if(name.equals(myData.get(k).getName())) {
    	    		for(int i = myData.get(k).getInfected().size()-Math.toIntExact(diffFirstDay); i <= myData.get(k).getInfected().size()-Math.toIntExact(diffLastDay); i++) {
    	    			if(diffFirstDay > myData.get(k).getInfected().size()) {
    	    	    		ErrorMessage.errorMessage(EnumErrorMessages.ERROR_MONTH);
    	    	    		break;
    	    			}else {
	    					days.add(Integer.toString((i+1)-(myData.get(k).getInfected().size()-Math.toIntExact(diffFirstDay))));
		        			infected.add(myData.get(k).getInfected().get(i));
		        			healed.add(myData.get(k).getHealed().get(i));
		        			dead.add(myData.get(k).getDead().get(i));
    	    			}
    	    		}
    			}
    		}
    		
    		Series<String, Number> dataHealed = gdfd.generateDataForDiagram("Genesen", days, healed);
    		Series<String, Number> dataDead = gdfd.generateDataForDiagram("Gestorben", days, dead);
    		Series<String, Number> dataInfected = gdfd.generateDataForDiagram("Erkrankt", days, infected);
    		
    		new GenerateDiagram(primaryStage, dataDead, dataHealed, dataInfected,
    				changeChart, boxRadio, boxYear, boxMonth, boxCountrie, boxState, boxButton, nameMonth, year, name);
    		}else{
    			ErrorMessage.errorMessage(EnumErrorMessages.ERROR_MONTH);
    	}
	}
	
	private void createBox( Label labelName, VBox boxName, int xLayout, int yLayout, Node objName, String tooltipText) {
		labelName.setStyle(labelFont);
		labelName.setTooltip(new Tooltip(tooltipText));
		boxName.setLayoutX(xLayout);
		boxName.setLayoutY(yLayout);
		boxName.getChildren().addAll(labelName, objName);
	}
	
	private void checkCountrieState(String nameCountries, ArrayList<CountrieObjects> myData) {
		if(nameCountries == null) {
			ErrorMessage.errorMessage(EnumErrorMessages.ERROR_LAND);
		}else {
			LOGGER.debug("Versuch4, nameState={}", nameCountries);
			updateChart(myData, nameCountries);
		}
	}
	

	
	@Override
	public void start(Stage primaryStage) throws IOException, InterruptedException {
			
		this.primaryStage = primaryStage;
		ArrayList<CountrieObjects> myData = rd.getData();
		
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		//Creating Radiobuttons
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		Label labelRadio = new Label("Diagramarten:");
		labelRadio.setStyle(labelFont);
		final ToggleGroup chartNumber = new ToggleGroup();
		RadioButton buttonBarChart = new RadioButton("Balkendiagram");
		buttonBarChart.setToggleGroup(chartNumber);
		buttonBarChart.setUserData(1);
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
		ChoiceBox<Integer> choiceYear = new ChoiceBox();
		choiceYear.getItems().add(2019);
		choiceYear.getItems().add(2020);
		choiceYear.getItems().add(2021);
		createBox(choiceLabelYear, boxYear, 8, 130, choiceYear, "Waehle das Jahr");
		
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		//Creating list month
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		Label choiceLabelMonth = new Label("Monat:");
		ChoiceBox<String> choiceMonth = new ChoiceBox(FXCollections.observableArrayList(
				EnumMonth.JANUARY.get(), 
				EnumMonth.FEBRUARY.get(),
				EnumMonth.MARCH.get(),
				EnumMonth.APRIL.get(),
				EnumMonth.MAY.get(),
				EnumMonth.JUNE.get(),
				EnumMonth.JULY.get(),
				EnumMonth.AUGUST.get(),
				EnumMonth.SEPTEMBER.get(),
				EnumMonth.OCTOBER.get(),
				EnumMonth.NOVEMBER.get(),
				EnumMonth.DECEMBER.get()));
		createBox(choiceLabelMonth, boxMonth, 100, 130, choiceMonth, "Waehle den Monat");
		
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		//Creating tree countries
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		Label labelCountries = new Label("Land:");	
		TreeItem<String> rootItem = new TreeItem<>("Länder");
		rootItem.setExpanded(true);
		for(int i = 0; i < myData.size(); i++) {
			if(myData.get(i).getParentIdentifier().equals("0")) {
				TreeItem<String> item = new TreeItem<>(myData.get(i).getName());
				rootItem.getChildren().add(item);
				String identifier = myData.get(i).getIdentifier();
				for(int k = 0; k < myData.size(); k++) {
					if(identifier.equals(myData.get(k).getParentIdentifier())) {
						TreeItem<String> item2 = new TreeItem<>(myData.get(k).getName());
						item.getChildren().add(item2);
					}
				}
			}
		}
		TreeView<String> tree = new TreeView<>(rootItem);
		labelCountries.setStyle(labelFont);
		labelCountries.setTooltip(new Tooltip("Waehle ein Land aus!"));
		
		boxCountrie.setLayoutX(8);
		boxCountrie.setLayoutY(180);
		boxCountrie.setPrefWidth(225);
		boxCountrie.getChildren().addAll(labelCountries, tree);
		
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		//Creating Buttons
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		Button reload = new Button("Reload");
		Button quit = new Button("Beenden");
		reload.setLayoutX(25);
		quit.setLayoutX(75);
		reload.setLayoutY(100);
		quit.setLayoutY(100);
		boxButton.setLayoutX(75);
		boxButton.setLayoutY(700);
		boxButton.getChildren().addAll(reload, quit);
		
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		//Creating listener for list year
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~	
		choiceYear.getSelectionModel().selectedIndexProperty().addListener((obserableValue, oldValue, newValue) ->{
			LOGGER.debug("newValue={}", choiceYear.getItems().get((Integer) newValue));
        	year = choiceYear.getItems().get((Integer) newValue);
        	
        	LOGGER.debug("Test1: {}", nameCountries);
        	LOGGER.debug("Test2: {}", myData.size());
		});
		
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		//Creating listener for list month
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~	
		choiceMonth.getSelectionModel().selectedItemProperty().addListener((obserableValue, oldValue, newValue) -> {	
			nameMonth = newValue; 
        	EnumMonth newMonth = EnumMonth.valueOf(EnumMonth.getEnumByString(newValue));
        	LOGGER.debug("nameMonth:  {}", nameMonth);
        	LOGGER.debug("newMonth:  {}", newMonth);
        	LOGGER.debug("Land:: {}", nameCountries);
        	LOGGER.debug("Daten: {}", myData.size());
        	switch(newMonth) {
        	case JANUARY:
        		month = 1;
        		break;
        	case FEBRUARY:
        		month = 2;
        		break;
        	case MARCH:
        		month = 3;
        		break;
        	case APRIL:
        		month = 4;
        		break;
        	case MAY:
        		month = 5;
        		break;
        	case JUNE:
        		month = 6;
        		break;
        	case JULY:
        		month = 7;
        		break;
        	case AUGUST:
        		month = 8;
        		break;
        	case SEPTEMBER:
        		month = 9;
        		break;
        	case OCTOBER:
        		month = 10;
        		break;
        	case NOVEMBER:
        		month = 11;
        		break;
        	case DECEMBER:
        		month = 12;
        		break;
        	default:
        		ErrorMessage.errorMessage(EnumErrorMessages.ERROR_MONTH_NOT_FOUND);
        	}
      });	
		
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		//Creating listener for tree items
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~	
		tree.getSelectionModel().selectedItemProperty().addListener((obserableValue, oldValue, newValue) ->{
			LOGGER.debug("Test10:  {}", newValue);
	        nameCountries = newValue.getValue();
		});
		
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		//Creating listener for radiobutton
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~	
		chartNumber.selectedToggleProperty().addListener((obserableValue, oldToggle, newToggle) -> {
			if(Integer.parseInt(chartNumber.getSelectedToggle().getUserData().toString()) == 1) {
		    	changeChart = 1;
		    }else if(Integer.parseInt(chartNumber.getSelectedToggle().getUserData().toString()) == 2){
		    	changeChart = 2;
		    }else if(Integer.parseInt(chartNumber.getSelectedToggle().getUserData().toString()) == 3) {
		    	changeChart = 3;
		    }
		}); 
		
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		//Creating event handler for button quit
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~	
		EventHandler<ActionEvent> buttonHandlerQuit = event -> System.exit(0);
		
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		//Creating listener for button reload
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~	
		EventHandler<ActionEvent> buttonHandlerReload = event -> {
			if(changeChart < 0) {
	    		ErrorMessage.errorMessage(EnumErrorMessages.ERROR_DIAGRAM);
	    	}else {
	    		checkCountrieState(nameCountries, myData);
	    	}
		};
		
		quit.setOnAction(buttonHandlerQuit);
		reload.setOnAction(buttonHandlerReload);
		
		updateChart(myData, "Mecklenburg Vorpommern");
	}

	public static void main(String[] args) {
		Translation.instance();
		launch(args);
	}
}
