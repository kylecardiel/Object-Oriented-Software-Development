/*Kyle Cardiel
 * SE450 - Elevator Project
 * 
 */

package ElevatorSimulationPackage.Person;

import ElevatorSimulationPackage.Common.InvalidDataParameterException;

public class Person implements Comparable<Person>{

	private String personId;
	private int startFloorLocation;
	private int currentFloorLocation;
	private int endFloorLocation;
	private String personStatus = "WAITING";
	private long waitStartTime;
	private long waitEndandRideStartTime;
	private long rideEndTime;
	private double totalWaitTime;
	private double totalRideTime;
	
	//Constructor
	public Person(int personIdIn, int currentFloorLocationIn, int personDestinationIn){
		setPersonId(personIdIn);
		setCurrentFloorLocation(currentFloorLocationIn);
		setStartFloorLocation(currentFloorLocationIn);
		setEndFloorLocation(personDestinationIn);
		setWaitStartTime(System.currentTimeMillis());
	}

	//Access
	public String getPersonId() {
		return personId;}

	public int getStartFloorLocation() {
		return startFloorLocation;}
	
	public int getCurrentFloorLocation() {
		return currentFloorLocation;}
	
	public int getEndFloorLocation() {
		return endFloorLocation;}
	
	public String getPersonStatus() {
		return personStatus;}
	

	private long getWaitStartTime() {
		return waitStartTime;}

	private long getWaitEndandRideStartTime() {
		return waitEndandRideStartTime;}
	
	private long getRideEndTime() {
		return rideEndTime;}
	
	public double getTotalWaitTime() {
		return totalWaitTime;}
	
	public double getTotalRideTime() {
		return totalRideTime;}
	
	
	//Modifiers
	private void setPersonId(int personIdIn) {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("P%d",personIdIn));
		personId = sb.toString();}

	private void setStartFloorLocation(int startFloorLocationIn) {
		startFloorLocation = startFloorLocationIn;}
	
	public void setCurrentFloorLocation(int currentFloorLocationIn)  {
		currentFloorLocation = currentFloorLocationIn;}

	private void setEndFloorLocation(int endFloorLocationIn) {
		endFloorLocation = endFloorLocationIn;}
	
	public void setPersonStatus(String personStatusIn) throws InvalidDataParameterException  {
		if(personStatusIn.equals("WAITING") || personStatusIn.equals("RIDING") || personStatusIn.equals("DONE")){
			personStatus = personStatusIn;
			if(personStatus.equals("RIDING")){
				setWaitEndandRideStartTime(System.currentTimeMillis());
				setTotalWaitTime();
			} else {
				setRideEndTime(System.currentTimeMillis());
				setTotalRideTime();
			}
		} else {
			throw new InvalidDataParameterException(personStatusIn + " is not a valid status"); 
		}
	}
	
	private void setWaitStartTime(long waitStartTimeIn) {
		waitStartTime = waitStartTimeIn;}

	private void setWaitEndandRideStartTime(long waitEndandRideStartTimeIn) {
		waitEndandRideStartTime = waitEndandRideStartTimeIn;}

	private void setRideEndTime(long rideEndTimeIn) {
		rideEndTime = rideEndTimeIn;}

	private void setTotalWaitTime() {
		totalWaitTime = (getWaitEndandRideStartTime() - getWaitStartTime())/1000;}

	private void setTotalRideTime() {
		totalRideTime = (getRideEndTime() - getWaitEndandRideStartTime())/1000;}
	
	public int compareTo(Person otherPerson){
		if(otherPerson == null || (getClass() != otherPerson.getClass())){
			return -1;
		}
		
		if( getEndFloorLocation() < otherPerson.getEndFloorLocation()){
			return -1;
		} else if(getEndFloorLocation() > otherPerson.getEndFloorLocation()){
			return 1;
		} else {
			return 0;}
	}


	





	
	
	
}
