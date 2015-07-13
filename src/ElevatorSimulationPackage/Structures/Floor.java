package ElevatorSimulationPackage.Structures;
/*Kyle Cardiel
 * SE450 - Elevator Project
 * Floor Class - holds people in a building and also has elevator request buttons (Up/Down)
 * 
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ElevatorSimulationPackage.Common.ElevatorRequests;
import ElevatorSimulationPackage.Common.InvalidDataParameterException;
import ElevatorSimulationPackage.Common.Timer;
import ElevatorSimulationPackage.Person.Person;

public class Floor {
	
	private int floorNumber;
	private int MAX_NUMBER_OF_PEOPLE_PER_FLOOR = 1000;
	private List<Person> collectionOfPeopleWaiting = new ArrayList<Person>();
	private List<Person> collectionOfPeopleDone = new ArrayList<Person>();
	Timer timer = Timer.getInstance();
	
	//Constructor
	public Floor (int floorNumberIn) throws InvalidDataParameterException{
		setFloorNumber(floorNumberIn);}
	
	//Access
	public int getFloorNumber(){
		return floorNumber;}

	public int getMaxNumberPeoplePerFloor(){
		return MAX_NUMBER_OF_PEOPLE_PER_FLOOR;}
	
	public List<Person> getCollectionOfPeopleWaiting(){
		return collectionOfPeopleWaiting;}
	
	public List<Person> getCollectionOfPeopleDone(){
		return collectionOfPeopleDone;}
	
	
	//Modifiers - with exception checking
	public void setFloorNumber(int floorNumberIn) throws InvalidDataParameterException {
		if(floorNumberIn < 1 ){
			throw new InvalidDataParameterException("Floor number can not be lower than 1. Input provided:  " + floorNumberIn);
			}
		floorNumber = floorNumberIn;}
	
	//Operations
	
	public boolean isFloorFull(){
		if( (getCollectionOfPeopleWaiting().size() +  getCollectionOfPeopleDone().size()) > getMaxNumberPeoplePerFloor()){
			return true;} 
		return false;
	}
	
	public ElevatorRequests pressUpButton(){
		ElevatorRequests er = new ElevatorRequests(getFloorNumber(),"UP");
		return er;
	}
	
	public ElevatorRequests pressDownButton(){
		if(getFloorNumber() == 1){
			return null;
		}
		ElevatorRequests er = new ElevatorRequests(getFloorNumber(),"DOWN");
		return er;
	}

	public ElevatorRequests addPersonToFloor(Person personIn){
		getCollectionOfPeopleWaiting().add(personIn);
		if(personIn.getStartFloorLocation() < personIn.getEndFloorLocation()){
			return pressUpButton();
		} else {
			return pressDownButton();
		}
	}
	
	public void addPersonToFloorFromElevator(Person personIn){
		getCollectionOfPeopleDone().add(personIn);
		timer.end();
		System.out.println(String.format("%s\tPerson %s  %s",
				timer.toStringTotalTime(),
				personIn.getPersonId(),
				toStringPeopleOnFloor()));
	}
	
	public void reomvePersonFromFloor(Person personIn){
		for(Iterator<Person> iterator = collectionOfPeopleWaiting.iterator(); iterator.hasNext();){
			Person personRidering = iterator.next();
			if(personRidering.getPersonId() == personIn.getPersonId()){
				iterator.remove();
			}
		}
	}
	
	
	public String toStringPeopleOnFloor(){
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("has entered Floor %d ",getFloorNumber()));
		sb.append(String.format("\t[Floor People: "));
		for(Person p: getCollectionOfPeopleDone()){
			sb.append(String.format("%s ",p.getPersonId()));
		}
		sb.append(String.format("]"));
		return sb.toString();
	}
	
	
	
}
