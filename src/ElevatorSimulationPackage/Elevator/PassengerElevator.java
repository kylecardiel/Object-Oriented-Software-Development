package ElevatorSimulationPackage.Elevator;
/*Kyle Cardiel
 * SE450 - Elevator Project
 * PassengerElevator Class - delegates all responsibility to ElevatorImpl
 * 
 */


import java.util.List;

import ElevatorSimulationPackage.Common.ElevatorImplFactory;
import ElevatorSimulationPackage.Common.ElevatorRequests;
import ElevatorSimulationPackage.Person.Person;


public class PassengerElevator implements Elevator {

	private Elevator elevator;

	//Constructor with delegate elevator
	public PassengerElevator(int elevatorIdIn,int maxPersonCapacityIn,int timePerFloorIn, int timePerDoorIn, int defaultFloorIn) {
		elevator = ElevatorImplFactory.ElevatorBuilder(elevatorIdIn,maxPersonCapacityIn,timePerFloorIn,timePerDoorIn,defaultFloorIn);}
	
	//Access
	@Override
	public int getElevatorId() {
		return elevator.getElevatorId();}
	
	@Override
	public int getCurrentLocation() {
		return elevator.getCurrentLocation();}

	@Override
	public int getCurrentDestination() {
		return elevator.getCurrentDestination();}
	
	@Override
	public List<ElevatorRequests> getCollectionOfElevatorRequests() {
		return elevator.getCollectionOfElevatorRequests();
	}

	@Override
	public String getDirection() {
		return elevator.getDirection();}

	
	@Override
	public List<Person> getCollectionOfPeopleOnElevator() {
		return elevator.getCollectionOfPeopleOnElevator();
	}

	@Override
	public List<Person> getCollectionOfPeopleWaiting() {
		return elevator.getCollectionOfPeopleWaiting();
	}

	@Override
	public List<Person> getCollectionOfPeopleDone() {
		return elevator.getCollectionOfPeopleDone();
	}

	
	
	//Modifiers
	@Override
	public void setDirection(String directionIn) {
		elevator.setDirection(directionIn);	}

	//Operations
	@Override
	public void openDoor() throws InterruptedException {
		elevator.openDoor();}
	
	@Override
	public void closeDoor() throws InterruptedException {
		elevator.closeDoor();}

	@Override
	public boolean addElevatorRequest(ElevatorRequests elevatorRequestsIn) {
		return elevator.addElevatorRequest(elevatorRequestsIn);
	}

	@Override
	public void riderRequest(Person personIn) {
		elevator.riderRequest(personIn);}
	
	@Override
	public boolean isDoorOpen() {
		return elevator.isDoorOpen();}

	@Override
	public ElevatorRequests returnToDefaultFloor() throws InterruptedException {
		return elevator.returnToDefaultFloor();
	}
	
	//implement runnable
	@Override
	public void run() {
		elevator.run();
	}

	@Override
	public void shutDown() {
		elevator.shutDown();
	}

	@Override
	public void addWaitingRider(Person personIn) {
		elevator.addWaitingRider(personIn);
		
	}







}
