package application.model;

import java.util.ArrayList;

/**
 * 
 * @author Christian Wollmann <br/> <br/>
 * This class store the data in objects. 
 * Each country is an object with different properties.
 * The identifier, the name, or the numbers for infected are stored as properties and can be retrieved by other clases.
 * @param identifier - Abbreviation for the name
 * @param parentIdentifier - Shows you who is the parent of this set
 * @param name - Full name of the identifier
 * @param infected - Number of infected, per day, saved as a ArrayList(Number)
 * @param healed - Number of healed, per day, saved as a ArrayList(Number)
 * @param dead - Number of dead, per day, saved as a ArrayList(Number)
 */

public class CountrieObjects implements InterfaceCountrieObjects{
	
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
