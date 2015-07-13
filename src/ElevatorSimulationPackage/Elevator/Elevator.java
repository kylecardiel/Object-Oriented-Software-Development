package ElevatorSimulationPackage.Elevator;
/*Kyle Cardiel
 * SE450 - Elevator Project
 * Elevator Class - This is a interface to allow for multiple kinds of elevators in the future. 
 * This class has several methods
 */


import java.util.List;

import ElevatorSimulationPackage.Common.ElevatorRequests;
import ElevatorSimulationPackage.Person.Person;

public interface Elevator extends Runnable{

	//Access Elevator Information
	int getElevatorId();
	
	int getCurrentLocation();
	
	int getCurrentDestination();
	
	String getDirection();
	
	List<ElevatorRequests> getCollectionOfElevatorRequests();
	
	List<Person> getCollectionOfPeopleOnElevator();
	
	List<Person> getCollectionOfPeopleWaiting();

	List<Person> getCollectionOfPeopleDone();
	
	
	//Modify Elevator Information
	void setDirection(String directionIn);

	
	//Elevator Operations
	boolean isDoorOpen();

	void openDoor() throws InterruptedException;
	
	void closeDoor() throws InterruptedException;
	
	boolean addElevatorRequest(ElevatorRequests elevatorRequestsIn);
	
	ElevatorRequests returnToDefaultFloor() throws InterruptedException;
	
	void riderRequest(Person personIn);
	
	void addWaitingRider(Person personIn);
	
	//implements runnable
	void run();
	
	void shutDown();
}
