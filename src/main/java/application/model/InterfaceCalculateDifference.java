package application.model;
/**
 * 
 * @author Christian Wollmann <br/> <br/>
 * Description for the class "CalculateDifference"
 */

public interface InterfaceCalculateDifference {
	
	/**
	 * With this method you get the difference between today and the first day of the month you are looking for.
	 * @return - (Long) Number of days between today and first day of month
	 */
	public long getDiffStart();
	
	/**
	 * With this method you get the difference between today and the last day of the month you are looking for.
	 * @return - (Long) Number of days between today and last day of month
	 */
	public long getDiffEnd();
}
