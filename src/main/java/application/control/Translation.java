package application.control;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tooltip;

/**
 * 
 * @author Christian Wollmann <br/>
 *         <br/>
 *         The class is used to make a translation for the available languages.
 */

public class Translation {

	private static final ObjectProperty<Locale> locale;
	static {
		locale = new SimpleObjectProperty<>(getDefaultLocale());
		locale.addListener((observable, oldValue, newValue) -> Locale.setDefault(newValue));
	}

	/**
	 * @author Christian Wollmann <br/>
	 *         <br/>
	 *         get the default locale. This is the systems default if contained in
	 *         the supported locales, english otherwise.
	 *
	 * @return
	 */
	public static Locale getDefaultLocale() {
		Locale sysDefault = Locale.getDefault();
		return getSupportedLocales().contains(sysDefault) ? sysDefault : Locale.GERMAN;
	}

	/**
	 * @author Christian Wollmann <br/>
	 *         <br/>
	 *         get the supported Locales.
	 *
	 * @return List of Locale objects.
	 */
	public static List<Locale> getSupportedLocales() {
		return new ArrayList<>(Arrays.asList(Locale.ENGLISH, Locale.GERMAN));
	}

	public static Locale getLocale() {
		return locale.get();
	}

	public static void setLocale(Locale locale) {
		localeProperty().set(locale);
		Locale.setDefault(locale);
	}

	public static ObjectProperty<Locale> localeProperty() {
		return locale;
	}

	/**
	 * @author Christian Wollmann <br/>
	 *         <br/>
	 *         gets the string with the given key from the resource bundle for the
	 *         current locale and uses it as first argument to MessageFormat.format,
	 *         passing in the optional args and returning the result.
	 *
	 * @param key  message key
	 * @param args optional arguments for the message
	 * @return localized formatted string
	 */
	public static String get(final String key, final Object... args) {
		ResourceBundle bundle = ResourceBundle.getBundle("translation", getLocale());
		return MessageFormat.format(bundle.getString(key), args);
	}

	/**
	 * @author Christian Wollmann <br/>
	 *         <br/>
	 *         creates a String binding to a localized String for the given message
	 *         bundle key
	 *
	 * @param key key
	 * @return String binding
	 */
	public static StringBinding createStringBinding(final String key, Object... args) {
		return Bindings.createStringBinding(() -> get(key, args), locale);
	}

	/**
	 * @author Christian Wollmann <br/>
	 *         <br/>
	 *         creates a String Binding to a localized String that is computed by
	 *         calling the given func
	 *
	 * @param func function called on every change
	 * @return StringBinding
	 */
	public static StringBinding createStringBinding(Callable<String> func) {
		return Bindings.createStringBinding(func, locale);
	}

	/**
	 * @author Christian Wollmann <br/>
	 *         <br/>
	 *         creates a bound Button for the given resourcebundle key
	 *
	 * @param key  ResourceBundle key
	 * @param args optional arguments for the message
	 * @return Button
	 */
	public static Button buttonForKey(final String key, final Object... args) {
		Button button = new Button();
		button.textProperty().bind(createStringBinding(key, args));
		return button;
	}

	/**
	 * @author Christian Wollmann <br/>
	 *         <br/>
	 *         creates a bound Tooltip for the given resourcebundle key
	 *
	 * @param key  ResourceBundle key
	 * @param args optional arguments for the message
	 * @return Label
	 */
	public static Tooltip tooltipForKey(final String key, final Object... args) {
		Tooltip tooltip = new Tooltip();
		tooltip.textProperty().bind(createStringBinding(key, args));
		return tooltip;
	}

	/**
	 * @author Christian Wollmann <br/>
	 *         <br/>
	 *         creates a radiobutton for the given resourcebundle key
	 *
	 * @param key  ResourceBundle key
	 * @param args optional arguments for the message
	 * @return Label
	 */
	public static RadioButton radioButtonForKey(final String key, final Object... args) {
		RadioButton radio = new RadioButton();
		radio.textProperty().bind(createStringBinding(key, args));
		return radio;
	}

	/**
	 * @author Christian Wollmann <br/>
	 *         <br/>
	 *         creates a label for the given resourcebundle key
	 *
	 * @param key  ResourceBundle key
	 * @param args optional arguments for the message
	 * @return Label
	 */
	public static Label labelForKey(final String key, final Object... args) {
		Label label = new Label();
		label.textProperty().bind(createStringBinding(key, args));
		return label;
	}

	/**
	 * @author Christian Wollmann <br/>
	 *         <br/>
	 *         creates a yAxis for the given resourcebundle key
	 *
	 * @param key  ResourceBundle key
	 * @param args optional arguments for the message
	 * @return Label
	 */
	public static NumberAxis yAxisForKey(final String key, final Object... args) {
		NumberAxis yAxis = new NumberAxis();
		yAxis.setLabel(Translation.get(key));
		return yAxis;
	}

	/**
	 * @author Christian Wollmann <br/>
	 *         <br/>
	 *         creates a xAxis for the given resourcebundle key
	 *
	 * @param key  ResourceBundle key
	 * @param args optional arguments for the message
	 * @return Label
	 */
	public static CategoryAxis xAxisForKey(final String key, final Object... args) {
		CategoryAxis xAxis = new CategoryAxis();
		xAxis.setLabel(Translation.get(key));
		return xAxis;
	}

	/**
	 * @author Christian Wollmann <br/>
	 *         <br/>
	 *         Translation for the title of the diagram
	 * @param key           ResourceBundle key
	 * @param chart
	 * @param nameMonth
	 * @param year
	 * @param nameCountries
	 * @return Label
	 */
	public static XYChart titleChart(final String key, XYChart chart, String nameMonth, int year,
			String nameCountries) {
		chart.setTitle(Translation.get(key) + " " + nameMonth + " " + year + " " + nameCountries);
		return chart;
	}

	public static final void init() {
		get("label.chart.title");
	}

}
