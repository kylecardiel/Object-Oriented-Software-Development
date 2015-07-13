package ElevatorSimulationPackage.Common;
/**
 * The ElevatorRequests class - creates a request for elevator movement,  
 * either to a floor request from a floor button or to a floor from a rider pressing a button.
 * 
 * @author	Kyle Cardiel
 * @since	Version 1.0
 *  
 */
import java.security.InvalidParameterException;

public class ElevatorRequests implements Comparable<ElevatorRequests>{

	/**
	 * The Floor Number of the Elevator Request
	 * @see	#getFloorNumber()
	 * @see	#setFloorNumber(int)
	 * 
	 */
	private int floorNumber;
	/**
	 * The Direction of the Elevator Request
	 * @see	#getDirection()
	 * @see	#setDirection(String)
	 * 
	 */
	private String direction;
	/**
	 * The Default Floor Request - Used to send Elevator to Default Floor
	 */
	private boolean defaultFloorRequest;
	
	/**
	 * Constructor to create a Elevator Request with a Floor Number and Direction.
	 * This elevator request is used by a person pressing an up/down button on a floor
	 * or if a request is received from an elevator rider
	 * @param	floorNumberIn	The floor number of the elevator request
	 * @param	directionIn		The direction of the elevator request
	 * @see	#setFloorNumber(int)
	 * @see	#setDirection(String)
	 * @see	#setDefaultFloorRequest(boolean)
	 * @return	ElevatorRequest Object
	 */
	//Constructor for normal request
	public ElevatorRequests(int floorNumberIn, String directionIn){
		setFloorNumber(floorNumberIn);
		setDirection(directionIn);
		setDefaultFloorRequest(false);
	}
	
	/**
	 * Constructor to create a Elevator Request to the default floor.
	 * This elevator request is used by an elevator after it has sat idle for a given period of time.
	 * @param	floorNumberIn	The floor number of the elevator request
	 * @see	#setFloorNumber(int)
	 * @see	#setDirection(String)
	 * @see	#setDefaultFloorRequest(boolean)
	 * @return	ElevatorRequest Object
	 */
	//Constructor for default floor request
	public ElevatorRequests(int floorNumberIn){
		setFloorNumber(floorNumberIn);
		setDirection("UP");
		setDefaultFloorRequest(true);
	}

	/**
	 * Returns the Floor Number of the Elevator Request.
	 * @return	int	Returns the Floor Number of the Elevator Request
	 */
	//Access
	public int getFloorNumber() {
		return floorNumber;}

	/**
	 * Returns the Direction of the Elevator Request.
	 * @return	String	Returns the Direction of the Elevator Request
	 */
	public String getDirection() {
		return direction;}

	/**
	 * Returns a boolean value if the Elevator Request made was for the default floor.
	 * @return	boolean	Returns a boolean value if the Elevator Request made was for the default floor
	 */
	public boolean isDefaultFloorRequest() {
		return defaultFloorRequest;
	}
	
	/**
	 * Sets the Floor Number of the Elevator Request to a given value.
	 * @param floorNumberIn	The Floor Number to set the Elevator Request to
	 * @throws InvalidParameterException	Throws an Exception if a Floor Number Request is for a Floor less than 0
	 */
	//Modifiers - with exception checking
	public void setFloorNumber(int floorNumberIn) throws InvalidParameterException {
		if(floorNumberIn < 1){
			throw new InvalidParameterException();
			}
		floorNumber = floorNumberIn;}
	/**
	 * Sets the Direction of the Elevator Request to a given value.
	 * @param directionIn	The Direction to set the Elevator Request to
	 */
	public void setDirection(String directionIn) {
			direction = directionIn;
	}

	/**
	 * Sets the default request variable to a boolean value based on the type of request.
	 * @param defaultFloorRequestIn	The boolean to set the Elevator Request to
	 */
	public void setDefaultFloorRequest(boolean defaultFloorRequestIn) {
		defaultFloorRequest = defaultFloorRequestIn;
	}
	
	/**
	 * Allows ElevatorRequest objects to be compared, sorted by the Collections class.  
	 * 
	 */
	public int compareTo(ElevatorRequests otherElevatorRequests){
		if(otherElevatorRequests == null || (getClass() != otherElevatorRequests.getClass())){
			return -1;
		}
		
		if(getFloorNumber() < otherElevatorRequests.getFloorNumber()){
			return -1;
		} else if(getFloorNumber() > otherElevatorRequests.getFloorNumber()){
			return 1;
		} else {
			return 0;}
	}






	
	
	
	
}
