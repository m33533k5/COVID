package application;

import java.util.ArrayList;

// Diese Klasse hatte ich mir erstellt um die anfallenden Daten in Objekte zu speichern

public class LaenderObjekte {
	
	private String kuerzel;
	private String name;
	private String parentKuerzel;
	private ArrayList<Number> infizierte;
	private ArrayList<Number> geheilte;
	private ArrayList<Number> tote;

	public LaenderObjekte(String kuerzel, String parentKuerzel, String name, ArrayList<Number> infizierte, ArrayList<Number> geheilte, ArrayList<Number> tote) {
		this.kuerzel = kuerzel;
		this.parentKuerzel = parentKuerzel;
		this.name = name;
		this.infizierte = infizierte;
		this.geheilte = geheilte;
		this.tote = tote;
	}

	public String getKuerzel() {
		return kuerzel;
	}
	
	public String getParentKuerzel() {
		return parentKuerzel;
	}
	
	public String getName() {
		return name;
	}
	
	public ArrayList<Number> getInfizierte() {
		return infizierte;
	}
	
	public ArrayList<Number> getGeheilte() {
		return geheilte;
	}
	
	public ArrayList<Number> getTote() {
		return tote;
	}
}
