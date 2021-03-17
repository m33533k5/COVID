package application.model;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;

import application.view.EnumErrorMessages;
import application.view.ErrorMessage;


/**
 * 
 * @author Christian <br/> <br/>
 *With this class I calculated the difference between today's date and
 *the first and last day of the month which is selected in the program
 *I use these numbers in the program to pull out the digits for the dates
 *which should be displayed in the diagram
 */
public class CalculateDifference implements InterfaceCalculateDifference{ 

	private long diffFirstDay;
	private long diffLastDay;
	
	public CalculateDifference(int monat, int jahr) {
	
		if(monat == 0) {
			ErrorMessage.errorMessage(EnumErrorMessages.ERROR_MONTH_NOT_FOUND);
		}else if(jahr == 0) {
			ErrorMessage.errorMessage(EnumErrorMessages.ERROR_YEAR_NOT_FOUND);
		}else {
			LocalDate dateToday = LocalDate.now();
			LocalDate dateFirstDay = LocalDate.of(jahr, monat, 1);
			LocalDate dateLastDay = YearMonth.of(jahr,monat).atEndOfMonth();
			
			setDiffFirstDay(dateFirstDay, dateToday);
			setDiffLastDay(dateLastDay, dateToday);
		}

	}
	
	private void setDiffFirstDay(LocalDate start, LocalDate today) {
		diffFirstDay = ChronoUnit.DAYS.between(start, today);
	}

	private void setDiffLastDay(LocalDate end, LocalDate today) {
		diffLastDay = ChronoUnit.DAYS.between(end, today);
	}
	
	public long getDiffStart() {
		return diffFirstDay;
	}
	
	public long getDiffEnd() {
		return diffLastDay;
	}
}
