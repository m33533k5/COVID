package application;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;

// Mit dieser Klasse habe ich die Differenz zwischen dem heutigen Datum und
// dem ersten und letzten Tages des Monats errechnet der im Programm ausgewählt wird
// Diese zahlen benutze ich im Programm, um mir die Stellen für die Daten herauszuziehen
// die im Diagram dann angezeigt werden sollen

public class BerechnungDifferenz {

	private long diff_anfang;
	private long diff_ende;
	
	public BerechnungDifferenz(int monat, int jahr) {
		
		LocalDate date_heute = LocalDate.now();
		LocalDate date_anfang = LocalDate.of(jahr, monat, 1);
		LocalDate date_ende = YearMonth.of(jahr,monat).atEndOfMonth();
		
		setDiffAnfang(date_anfang, date_heute);
		setDiffEnde(date_ende, date_heute);
	}
	
	private void setDiffAnfang(LocalDate anfang, LocalDate heute) {
		diff_anfang = ChronoUnit.DAYS.between(anfang, heute);
	}
	
	private void setDiffEnde(LocalDate ende, LocalDate heute) {
		diff_ende = ChronoUnit.DAYS.between(ende, heute);
	}
	
	public long getDiffAnfang() {
		return diff_anfang;
	}
	
	public long getDiffEnde() {
		return diff_ende;
	}
	/*
	public static void main(String[] args) {
		BerechnungDifferenz bd = new BerechnungDifferenz(5,2020);
		System.out.println(bd.getDiffAnfang());
		System.out.println(bd.getDiffEnde());
	}
	*/
}
