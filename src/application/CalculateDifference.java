package application;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;

//With this class I calculated the difference between today's date and
//the first and last day of the month which is selected in the program
//I use these numbers in the program to pull out the digits for the dates
//which should be displayed in the diagram

public class CalculateDifference {

	private long diffFirstDay;
	private long diffLastDay;
	
	public CalculateDifference(int monat, int jahr) {
		
		LocalDate dateToday = LocalDate.now();
		LocalDate dateFirstDay = LocalDate.of(jahr, monat, 1);
		LocalDate dateLastDay = YearMonth.of(jahr,monat).atEndOfMonth();
		
		setDiffFirstDay(dateFirstDay, dateToday);
		setDiffLastDay(dateLastDay, dateToday);
	}
	
	private void setDiffFirstDay(LocalDate anfang, LocalDate heute) {
		diffFirstDay = ChronoUnit.DAYS.between(anfang, heute);
	}
	
	private void setDiffLastDay(LocalDate ende, LocalDate heute) {
		diffLastDay = ChronoUnit.DAYS.between(ende, heute);
	}
	
	public long getDiffAnfang() {
		return diffFirstDay;
	}
	
	public long getDiffEnde() {
		return diffLastDay;
	}
	/*
	public static void main(String[] args) {
		BerechnungDifferenz bd = new BerechnungDifferenz(5,2020);
		System.out.println(bd.getDiffAnfang());
		System.out.println(bd.getDiffEnde());
	}
	*/
}
