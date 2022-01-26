package application.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Christian Wollmann <br/>
 *         <br/>
 *         Description for the class "CountrieObjects"
 */

public interface InterfaceCountrieObjects {

	/**
	 * @author Christian Wollmann <br/>
	 *         <br/>
	 *         You get back the abbreviation for the respective object, as a string.
	 * @return
	 */
	public String getIdentifier();

	/**
	 * @author Christian Wollmann <br/>
	 *         <br/>
	 *         You get back the full name for the respective object, as a string.
	 * @return
	 */
	public String getName();

	/**
	 * @author Christian Wollmann <br/>
	 *         <br/>
	 *         You get back the List of infected people for the respective object,
	 *         as a ArrayList(Number).
	 * @return
	 */
	public List<Number> getInfected();

	/**
	 * @author Christian Wollmann <br/>
	 *         <br/>
	 *         * You get back the List of healed people for the respective object,
	 *         as a ArrayList(Number).
	 * @return
	 */
	public List<Number> getHealed();

	/**
	 * @author Christian Wollmann <br/>
	 *         <br/>
	 *         * You get back the List of dead people for the respective object, as
	 *         a ArrayList(Number).
	 * @return
	 */
	public List<Number> getDead();
}
