package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
import application.view.GenerateUserInterface;
import application.view.GenerateWindow;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class Main extends Application implements ErrorMessage {

	// Main.class sagt, dass dieser logger in dieser klasse registriert ist
	private static final Logger LOGGER = LogManager.getLogger(Main.class);

	// If the diagram type is changed, then the change is saved as a number
	private int changeChart = -1;

	private Stage primaryStage;
	// Variables for the program
	private int month;
	private int year;
	private String nameCountries;
	private String nameMonth;

	// Call the class to access the objects / methods.
	RequestData rd = new RequestData();
	GenerateDataForDiagram gdfd = new GenerateDataForDiagram();
	GenerateUserInterface gi = new GenerateUserInterface();
	GenerateWindow gw = new GenerateWindow();
	
	// Wenn aenderungen der Daten passieren in dieser Methode
	private void updateChart(ArrayList<CountrieObjects> myData, String name) {

		LOGGER.debug("Parameter = {}", name);

		CalculateDifference bd = new CalculateDifference(month, year);
		long diffFirstDay = bd.getDiffStart();
		long diffLastDay = bd.getDiffEnd();
		List<String> days = new ArrayList();
		List<Number> dead = new ArrayList();
		List<Number> healed = new ArrayList();
		List<Number> infected = new ArrayList();

		if (diffFirstDay > 0 || diffLastDay > 0) {
			for (int k = 0; k < myData.size(); k++) {
				if (name.equals(myData.get(k).getName())) {
					for (int i = myData.get(k).getInfected().size() - Math.toIntExact(diffFirstDay); i <= myData.get(k)
							.getInfected().size() - Math.toIntExact(diffLastDay); i++) {
						if (diffFirstDay > myData.get(k).getInfected().size()) {
							ErrorMessage.errorMessage(EnumErrorMessages.ERROR_DATA_NOT_FOUND);
							break;
						} else {
							days.add(Integer.toString(
									(i + 1) - (myData.get(k).getInfected().size() - Math.toIntExact(diffFirstDay))));
							infected.add(myData.get(k).getInfected().get(i));
							healed.add(myData.get(k).getHealed().get(i));
							dead.add(myData.get(k).getDead().get(i));
						}
					}
				}
			}

			Series<String, Number> dataHealed = gdfd
					.generateDataForDiagram(Translation.translate("label.series.healed"), days, healed);
			Series<String, Number> dataDead = gdfd.generateDataForDiagram(Translation.translate("label.series.dead"),
					days, dead);
			Series<String, Number> dataInfected = gdfd
					.generateDataForDiagram(Translation.translate("label.series.infected"), days, infected);
			
			gw.generateDiagram(primaryStage, dataDead, dataHealed, dataInfected, gi.getBoxRadio(),
					gi.getBoxYear(), gi.getBoxMonth(), gi.getBoxCountries(), gi.getBoxButton(), gi.getBoxLanguage(), changeChart, nameMonth, year, name);
		} else {
			ErrorMessage.errorMessage(EnumErrorMessages.ERROR_DATA_NOT_FOUND);
		}
	}

	private void checkCountrieState(String nameCountries, ArrayList<CountrieObjects> myData) {
		if (nameCountries == null) {
			ErrorMessage.errorMessage(EnumErrorMessages.ERROR_LAND);
		} else {
			LOGGER.debug("Versuch4, nameState={}", nameCountries);
			updateChart(myData, nameCountries);
		}
	}

	@Override
	public void start(Stage primaryStage) throws IOException, InterruptedException {

		this.primaryStage = primaryStage;
		gi.generateUserInterface();
		ChoiceBox choiceYear = gi.getChoiceBoxYear();
		ChoiceBox choiceMonth = gi.getChoiceBoxMonth();
		TreeView<String> tree = gi.getTreeCountries();
		ToggleGroup chartNumber = gi.getChartNumber();
		ArrayList<CountrieObjects> myData = rd.getData();
		Button quit = gi.getButtonQuit();
		Button reload = gi.getButtonReload();
		Button de = gi.getButtonDe();
		Button en = gi.getButtonEn();

		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		// Creating listener for list year
		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		choiceYear.getSelectionModel().selectedIndexProperty().addListener((obserableValue, oldValue, newValue) -> {
			LOGGER.debug("newValue={}", choiceYear.getItems().get((Integer) newValue));
			year = (int) choiceYear.getItems().get((Integer) newValue);
		});

		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		// Creating listener for list month
		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		choiceMonth.getSelectionModel().selectedItemProperty().addListener((obserableValue, oldValue, newValue) -> {
			nameMonth = (String) newValue;
			EnumMonth newMonth = EnumMonth.valueOf(EnumMonth.getEnumByString((String) newValue));
			switch (newMonth) {
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

		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		// Creating listener for tree items
		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		tree.getSelectionModel().selectedItemProperty().addListener((obserableValue, oldValue, newValue) -> {
			LOGGER.debug("Test10:  {}", newValue);
			nameCountries = newValue.getValue();
		});

		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		// Creating listener for radiobutton
		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		chartNumber.selectedToggleProperty().addListener((obserableValue, oldToggle, newToggle) -> {
			if (Integer.parseInt(chartNumber.getSelectedToggle().getUserData().toString()) == 1) {
				changeChart = 1;
			} else if (Integer.parseInt(chartNumber.getSelectedToggle().getUserData().toString()) == 2) {
				changeChart = 2;
			} else if (Integer.parseInt(chartNumber.getSelectedToggle().getUserData().toString()) == 3) {
				changeChart = 3;
			}
		});

		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		// Creating event handler for button quit
		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		EventHandler<ActionEvent> buttonHandlerQuit = event -> System.exit(0);

		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		// Creating listener for button reload
		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		EventHandler<ActionEvent> buttonHandlerReload = event -> {
			if (changeChart < 0) {
				ErrorMessage.errorMessage(EnumErrorMessages.ERROR_DIAGRAM_NOT_FOUND);
			} else {
				checkCountrieState(nameCountries, myData);
			}
		};

		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		// Creating event handler for button language de
		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		EventHandler<ActionEvent> buttonHandlerLanguageDe = event -> {
			Translation.setLanguage("de");
			Translation.setLocale(Locale.GERMAN);
			if (changeChart < 0) {
				ErrorMessage.errorMessage(EnumErrorMessages.ERROR_DIAGRAM_NOT_FOUND);
			} else {
				checkCountrieState(nameCountries, myData);
			}
		};

		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		// Creating event handler for button language en
		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		EventHandler<ActionEvent> buttonHandlerLanguageEn = event -> {
			Translation.setLanguage("en");
			Translation.setLocale(Locale.ENGLISH);
			if (changeChart < 0) {
				ErrorMessage.errorMessage(EnumErrorMessages.ERROR_DIAGRAM_NOT_FOUND);
			} else {
				checkCountrieState(nameCountries, myData);
			}
		};
		quit.setOnAction(buttonHandlerQuit);
		reload.setOnAction(buttonHandlerReload);
		de.setOnAction(buttonHandlerLanguageDe);
		en.setOnAction(buttonHandlerLanguageEn);
		
		gw.generateScene(primaryStage, gi.getBoxRadio(),
				gi.getBoxYear(), gi.getBoxMonth(), gi.getBoxCountries(), gi.getBoxButton(), gi.getBoxLanguage());
	}

	public static void main(String[] args) {
		Translation.instance();
		launch(args);
	}
}
