package application.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import application.control.RequestData;
import application.control.Translation;
import application.model.CountrieObjects;
import application.model.EnumMonth;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class GenerateUserInterface {

	private VBox boxRadio;
	private VBox boxYear;
	private VBox boxMonth = new VBox();
	private VBox boxCountries;
	private HBox boxButton;
	private HBox boxLanguage;
	private RadioButton buttonBarChart;
	private RadioButton buttonLineChart;
	private RadioButton buttonAreaChart;
	private ToggleGroup chartNumber;
	private ComboBox<Integer> choiceYear;
	private ComboBox<EnumMonth> choiceMonth = new ComboBox();
	private TreeView<String> tree;
	private Button reload;
	private Button quit;
	private Button de;
	private Button en;
	
	private String labelFont = "-fx-font-weight: bold;";
	
	private void createBox( Label labelName, VBox boxName, int xLayout, int yLayout, Node objName, String tooltipText) {
		labelName.setStyle(labelFont);
		labelName.setTooltip(new Tooltip(tooltipText));
		boxName.setLayoutX(xLayout);
		boxName.setLayoutY(yLayout);
		boxName.getChildren().addAll(labelName, objName);
	}
	
	public void generateUserInterface() throws IOException, InterruptedException {
		generateBoxRadioButton();
		generateToggleGroup();
		generateBoxYear();
		generateBoxMonth();
		generateTreeCountries();
		generateBoxCountries();
		generateButtonReloadQuit();
		generateButtonLanguage();
	}
	
	private void generateBoxRadioButton() {
		Label labelRadio = Translation.labelForKey("label.diagram.types");
		labelRadio.setStyle(labelFont);
		buttonBarChart = Translation.radioButtonForKey("label.diagram.barChart");
		buttonLineChart = Translation.radioButtonForKey("label.diagram.lineChart");
		buttonAreaChart = Translation.radioButtonForKey("label.diagram.areaChart");
		boxRadio = new VBox();
		boxRadio.setLayoutX(8);
		boxRadio.setLayoutY(55);
		boxRadio.getChildren().addAll(labelRadio, buttonBarChart, buttonLineChart, buttonAreaChart);
	}
	
	private void generateToggleGroup() {
		chartNumber = new ToggleGroup();
		buttonBarChart.setToggleGroup(chartNumber);
		buttonBarChart.setUserData(1);
		buttonLineChart.setToggleGroup(chartNumber);
		buttonLineChart.setUserData(2);
		buttonAreaChart.setToggleGroup(chartNumber);
		buttonAreaChart.setUserData(3);
	}
	
	private void generateBoxYear(){
		choiceYear = new ComboBox<>();
		choiceYear.getItems().add(2019);
		choiceYear.getItems().add(2020);
		choiceYear.getItems().add(2021);
		Label choiceLabelYear = Translation.labelForKey("label.list.year");
		boxYear = new VBox();
		createBox(choiceLabelYear, boxYear, 8, 130, choiceYear, Translation.get("label.tooltip.chooseYear"));
	}
	
	public void generateBoxMonth() {
		ObservableList<EnumMonth> i = FXCollections.observableArrayList(
				EnumMonth.JANUARY, 
				EnumMonth.FEBRUARY,
				EnumMonth.MARCH,
				EnumMonth.APRIL,
				EnumMonth.MAY,
				EnumMonth.JUNE,
				EnumMonth.JULY,
				EnumMonth.AUGUST,
				EnumMonth.SEPTEMBER,
				EnumMonth.OCTOBER,
				EnumMonth.NOVEMBER,
				EnumMonth.DECEMBER);
		choiceMonth = new ComboBox<>(i);
		createBox(Translation.labelForKey("label.list.month"), boxMonth, 100, 130, choiceMonth, Translation.get("label.tooltip.chooseMonth"));
	}
	
	public void updateBoxMonth() {
		ObservableList<EnumMonth> i = FXCollections.observableArrayList(
				EnumMonth.JANUARY, 
				EnumMonth.FEBRUARY,
				EnumMonth.MARCH,
				EnumMonth.APRIL,
				EnumMonth.MAY,
				EnumMonth.JUNE,
				EnumMonth.JULY,
				EnumMonth.AUGUST,
				EnumMonth.SEPTEMBER,
				EnumMonth.OCTOBER,
				EnumMonth.NOVEMBER,
				EnumMonth.DECEMBER);
		choiceMonth.setItems(i);
		choiceMonth.show();
	}
	
	private void generateTreeCountries() throws IOException, InterruptedException {
		RequestData rd = new RequestData();
		ArrayList<CountrieObjects> myData = rd.getData();
		TreeItem<String> rootItem = new TreeItem<>(Translation.get("label.tree.countries"));
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
		tree = new TreeView<>(rootItem);
	}
	
	private void generateBoxCountries() {
		Label labelCountries = Translation.labelForKey("label.list.countrie");	
		labelCountries.setStyle(labelFont);
		labelCountries.setTooltip(Translation.tooltipForKey("label.tooltip.chooseCountrie"));
		boxCountries = new VBox();
		boxCountries.setLayoutX(8);
		boxCountries.setLayoutY(225); //180
		boxCountries.setPrefWidth(225);
		boxCountries.getChildren().addAll(labelCountries, tree);
	}
	
	private void generateButtonReloadQuit() {
		reload = Translation.buttonForKey("label.button.reload");
		quit = Translation.buttonForKey("label.button.quit");
		boxButton = new HBox();
		boxButton.setLayoutX(75);
		boxButton.setLayoutY(700);
		boxButton.getChildren().addAll(reload, quit);
	}
	
	private void generateButtonLanguage() throws FileNotFoundException {
		de = new Button();
		Image imgDe = new Image(new FileInputStream("src\\main\\resources\\deFlag.png"));
		ImageView ivDe = new ImageView(imgDe);
		de.setGraphic(ivDe);
		en = new Button();
		Image imgEn = new Image(new FileInputStream("src\\main\\resources\\enFlag.png"));
		ImageView ivEn = new ImageView(imgEn);
		en.setGraphic(ivEn);
		boxLanguage = new HBox();
		boxLanguage.setLayoutX(10);
		boxLanguage.setLayoutY(10);
		boxLanguage.getChildren().addAll(de, en);
	}
	
	public Button getButtonReload() {
		return reload;
	}
	
	public Button getButtonQuit() {
		return quit;
	}
	
	public Button getButtonDe() {
		return de;
	}
	
	public Button getButtonEn() {
		return en;
	}
	
	public ComboBox<Integer> getChoiceBoxYear(){
		return choiceYear;
	}
	
	@SuppressWarnings("rawtypes")
	public ComboBox getChoiceBoxMonth() {
		return choiceMonth;
	}
	
	public VBox getBoxMonth() {
		return boxMonth;
	}
	
	public void setBoxMonth() {
		createBox(Translation.labelForKey("label.list.month"), boxMonth, 100, 130, choiceMonth, Translation.get("label.tooltip.chooseMonth"));
	}
	
	public HBox getBoxButton() {
		return boxButton;
	}
	
	public HBox getBoxLanguage() {
		return boxLanguage;
	}
	
	public ToggleGroup getChartNumber() {
		return chartNumber;
	}
	
	@SuppressWarnings("rawtypes")
	public TreeView getTreeCountries() {
		return tree;
	}
	
	public VBox getBoxRadio() {
		return boxRadio;
	}
	
	public VBox getBoxYear() {
		return boxYear;
	}
	
	public VBox getBoxCountries() {
		return boxCountries;
	}
	
}
