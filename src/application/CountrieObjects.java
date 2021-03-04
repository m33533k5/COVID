package application;

import java.util.ArrayList;

//I created this class to store the data in objects.
//Each country is an object with different properties. 
//The identifier, the name, or the numbers for infected are stored as properties 
//and can be retrieved by other classes.

public class CountrieObjects {
	
	private String identifier;
	private String name;
	private String parentIdentifier;
	private ArrayList<Number> infected;
	private ArrayList<Number> healed;
	private ArrayList<Number> dead;

	public CountrieObjects(String identifier, String parentIdentifier, String name, ArrayList<Number> infected, ArrayList<Number> healed, ArrayList<Number> dead) {
		this.identifier = identifier;
		this.parentIdentifier = parentIdentifier;
		this.name = name;
		this.infected = infected;
		this.healed = healed;
		this.dead = dead;
	}

	public String getIdentifier() {
		return identifier;
	}
	
	public String getParentIdentifier() {
		return parentIdentifier;
	}
	
	public String getName() {
		return name;
	}
	
	public ArrayList<Number> getInfected() {
		return infected;
	}
	
	public ArrayList<Number> getHealed() {
		return healed;
	}
	
	public ArrayList<Number> getDead() {
		return dead;
	}
}
