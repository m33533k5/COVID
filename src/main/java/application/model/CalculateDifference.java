package application.model;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;

import application.view.EnumErrorMessages;
import application.view.ErrorMessage;

/**
 * 
 * @author Christian <br/>
 *         <br/>
 *         With this class I calculated the difference between today's date and
 *         the first and last day of the month which is selected in the program
 *         I use these numbers in the program to pull out the digits for the
 *         dates which should be displayed in the diagram
 */
public class CalculateDifference implements InterfaceCalculateDifference {

	private long diffFirstDay;
	private long diffLastDay;

	/**
	 * @author Christian <br/>
	 *         <br/>
	 *         Today's date is taken and the first and last day are determined from
	 *         this date.
	 * @param month Month of which the first and last days are to be determined.
	 * @param year
	 */
	public CalculateDifference(int month, int year) {

		if (month == 0) {
			ErrorMessage.errorMessage(EnumErrorMessages.ERROR_MONTH_NOT_FOUND);
		} else if (year == 0) {
			ErrorMessage.errorMessage(EnumErrorMessages.ERROR_YEAR_NOT_FOUND);
		} else {
			LocalDate dateToday = LocalDate.now();
			LocalDate dateFirstDay = LocalDate.of(year, month, 1);
			LocalDate dateLastDay = YearMonth.of(year, month).atEndOfMonth();

			setDiffFirstDay(dateFirstDay, dateToday);
			setDiffLastDay(dateLastDay, dateToday);
		}

	}

	/**
	 * @author Christian <br/>
	 *         <br/>
	 *         Determine the first day of a month.
	 * @param start First day of the month.
	 * @param today Todays date.
	 */
	private void setDiffFirstDay(LocalDate start, LocalDate today) {
		diffFirstDay = ChronoUnit.DAYS.between(start, today);
	}

	/**
	 * @author Christian <br/>
	 *         <br/>
	 *         Determine the last day of a month.
	 * @param end   Last day of the month.
	 * @param today Todays date.
	 */
	private void setDiffLastDay(LocalDate end, LocalDate today) {
		diffLastDay = ChronoUnit.DAYS.between(end, today);
	}

	/**
	 * @author Christian <br/>
	 *         <br/>
	 *         The first day of the month is returned.
	 */
	public long getDiffStart() {
		return diffFirstDay;
	}

	/**
	 * @author Christian <br/>
	 *         <br/>
	 *         The last day of the month is returned.
	 */
	public long getDiffEnd() {
		return diffLastDay;
	}
}
