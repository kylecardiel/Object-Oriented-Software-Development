package ElevatorSimulationPackage.Elevator;
/*Kyle Cardiel
 * SE450 - Elevator Project
 * ElevatorImpl Class - Implements the Elevator behaviors (methods)
 * 
 */


import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import ElevatorSimulationPackage.Common.ElevatorRequests;
import ElevatorSimulationPackage.Common.InvalidDataParameterException;
import ElevatorSimulationPackage.Common.Timer;
import ElevatorSimulationPackage.Person.Person;
//import ElevatorSimulationPackage.Structures.ElevatorController;

public class ElevatorImpl implements Elevator, Runnable {
	
	private int elevatorId;
	private int maxPersonCapacity;
	private int timePerFloor;
	private int timePerDoor;	
	private String doorStatus = "closed";
	private int currentLocation;
	private int currentDestination;
	private String direction = "IDLE";
	private List<ElevatorRequests> collectionOfElevatorRequests = new ArrayList<ElevatorRequests>();
	private List<Person> collectionOfPeopleOnElevator = new ArrayList<Person>();
	private List<Person> collectionOfPeopleWaiting = new ArrayList<Person>();
	private List<Person> collectionOfPeopleDone = new ArrayList<Person>();		
	private int defaultFloor;
	private Timer timer = Timer.getInstance();
	private boolean running = false;
		
	//Constructor
	public ElevatorImpl(int elevatorIdIn,int maxPersonCapacityIn,int timePerFloorIn, int timePerDoorIn, int defaultFloorIn) throws InvalidParameterException {
		setElevatorId(elevatorIdIn+1);
		setCurrentLocation(1);
		setMaxPersonCapacity(maxPersonCapacityIn);
		setTimePerFloor(timePerFloorIn);
		setTimePerDoor(timePerDoorIn);
		setDefaultFloor(defaultFloorIn);
		}

	
	//Access
	public int getElevatorId() {return elevatorId;}

	public int getCurrentLocation() {
		return currentLocation;}
	
	public int getCurrentDestination() {
		return currentDestination;}

	public String getDirection() {
		return direction;}

	public int getDefaultFloor() {
		return defaultFloor;}
	

	public List<ElevatorRequests> getCollectionOfElevatorRequests(){
		return collectionOfElevatorRequests;
	}
	
	public List<Person> getCollectionOfPeopleOnElevator(){
		return collectionOfPeopleOnElevator;
	}
	
	public List<Person> getCollectionOfPeopleWaiting() {
		return collectionOfPeopleWaiting;
	}

	public List<Person> getCollectionOfPeopleDone() {
		return collectionOfPeopleDone;
	}
	
	public int getMaxPersonCapacity() {
		return maxPersonCapacity;}
	
	public int getTimePerFloor() {
		return timePerFloor;
	}

	public int getTimePerDoor() {
		return timePerDoor;
	}

	
	//Modifiers
	private void setElevatorId(int elevatorIdIn) throws InvalidParameterException {
		if(elevatorIdIn < 1){
			throw new InvalidParameterException();
			}
		elevatorId = elevatorIdIn;}

	public void setCurrentLocation(int currentLocationIn) throws InvalidParameterException {
		if(currentLocationIn < 1){
			throw new InvalidParameterException();
			}
		currentLocation = currentLocationIn;}

	public void setCurrentDestination(int currentDestinationIn) throws InvalidParameterException {
		if(currentDestinationIn < 1){
			throw new InvalidParameterException();
			}
		currentDestination = currentDestinationIn;}

	public void setDefaultFloor(int defaultFloorIn) throws InvalidParameterException {
		//if(defaultFloorIn < 1){
			//throw new InvalidParameterException();
			//}
		defaultFloor = defaultFloorIn;}

	public void setDirection(String directionIn) throws InvalidParameterException {
		//if (directionIn == "UP" || directionIn == "DOWN" || directionIn == "IDLE"){
			direction = directionIn;
		//}
		//throw new InvalidParameterException();
			}
	
	public void setMaxPersonCapacity(int maxPersonCapacityIn) throws InvalidParameterException {
		if(maxPersonCapacityIn < 1){
			throw new InvalidParameterException();
			}
		maxPersonCapacity = maxPersonCapacityIn;}
	
	public void setCollectionOfPeopleWaiting(List<Person> collectionOfPeopleWaitingIn) {
		collectionOfPeopleWaiting = collectionOfPeopleWaitingIn;
	}
	
	public void setTimePerFloor(int timePerFloorIn) {
		timePerFloor = timePerFloorIn;
	}
	

	public void setTimePerDoor(int timePerDoorIn) {
		timePerDoor = timePerDoorIn;
	}
	
	//Operations
	public boolean isDoorOpen(){
		return doorStatus == "Open";}

	public void moveUpFloor() {
		try {
			Thread.sleep(timePerFloor);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		setCurrentLocation(getCurrentLocation()+1);
		}
	
	public void moveDownFloor() {
		try {
			Thread.sleep(timePerFloor);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		setCurrentLocation(getCurrentLocation()-1);
		}

	public ElevatorRequests returnToDefaultFloor() {
		ElevatorRequests er = new ElevatorRequests(getDefaultFloor());
		addElevatorRequest(er);
		return er;
	}
	
	public String getDoorStatus(){
		return doorStatus;}
	
	public void setDoorStatus(String doorStatusIn){
		if (doorStatusIn == "Open" || doorStatusIn == "Close" ){
			doorStatus = doorStatusIn;
		}
	}
	
	public void openDoor() {
		if (isDoorOpen()){}
		else {setDoorStatus("Open");}
		try {
			Thread.sleep(timePerDoor);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//closeDoor();
	}
	
	public void closeDoor() {
		if (isDoorOpen()){setDoorStatus("Close");}
	}
	
	public boolean addElevatorRequest(ElevatorRequests ElevatorRequestIn){
		synchronized (collectionOfElevatorRequests){
			if(collectionOfElevatorRequests.contains(ElevatorRequestIn)){
				timer.end();
				System.out.printf("%s\t%s\n",timer.toStringTotalTime(),toStringElevatorFloorRequest(ElevatorRequestIn.getFloorNumber()));
				return true;
			}
			if(collectionOfElevatorRequests.isEmpty()){
				collectionOfElevatorRequests.add(ElevatorRequestIn);
				if (collectionOfElevatorRequests.get(0).getFloorNumber() > getCurrentLocation()){
					setDirection("UP");
					if(ElevatorRequestIn.getDirection() != null){
						timer.end();
						System.out.printf("%s\t%s\n",timer.toStringTotalTime(),toStringElevatorFloorRequest(ElevatorRequestIn.getFloorNumber()));
						collectionOfElevatorRequests.notifyAll();
					} else {
						timer.end();
						System.out.printf("%s\t%s\n",timer.toStringTotalTime(),toStringElevatorRiderRequest(ElevatorRequestIn.getFloorNumber()));
						collectionOfElevatorRequests.notifyAll();
					}
					return true;
				} else{
					setDirection("DOWN");
					if(ElevatorRequestIn.getDirection() != null){
						timer.end();
						System.out.printf("%s\t%s\n",timer.toStringTotalTime(),toStringElevatorFloorRequest(ElevatorRequestIn.getFloorNumber()));
						collectionOfElevatorRequests.notifyAll();
					}else {
						timer.end();
						System.out.printf("%s\t%s\n",timer.toStringTotalTime(),toStringElevatorRiderRequest(ElevatorRequestIn.getFloorNumber()));
						collectionOfElevatorRequests.notifyAll();
					}
					return true;
				}
			} else {
				collectionOfElevatorRequests.add(ElevatorRequestIn);
				if (collectionOfElevatorRequests.get(collectionOfElevatorRequests.size()-1).getFloorNumber() > getCurrentLocation() && getDirection() == "UP" ||
						collectionOfElevatorRequests.get(collectionOfElevatorRequests.size()-1).getFloorNumber() < getCurrentLocation() && getDirection() == "DOWN"){
					if(getDirection() == "UP"){
						Collections.sort(collectionOfElevatorRequests);
					}else{
						Collections.sort(collectionOfElevatorRequests);
						Collections.reverse(collectionOfElevatorRequests);
					}
					if(ElevatorRequestIn.getDirection() != null){
						timer.end();
						System.out.printf("%s\t%s\n",timer.toStringTotalTime(),toStringElevatorFloorRequest(ElevatorRequestIn.getFloorNumber()));
						collectionOfElevatorRequests.notifyAll();
					}else {
						timer.end();
						System.out.printf("%s\t%s\n",timer.toStringTotalTime(),toStringElevatorRiderRequest(ElevatorRequestIn.getFloorNumber()));
						collectionOfElevatorRequests.notifyAll();
					}
					return true;
				} else {
					//reject request
					timer.end();
					System.out.println(String.format("%s\tA request has been rejected",timer.toStringTotalTime()));
					collectionOfElevatorRequests.remove(collectionOfElevatorRequests.size()-1);
					return false;
				}
			}
		}
	}
	
	
	@Override
	public void addWaitingRider(Person personIn) {
		getCollectionOfPeopleWaiting().add(personIn);
	}
	
	public boolean isElevatorFull(){
		return getMaxPersonCapacity() <=  getCollectionOfPeopleOnElevator().size();
	}
	
	public void riderRequest(Person personIn){
		ElevatorRequests er = new ElevatorRequests(personIn.getEndFloorLocation(), null);
		addElevatorRequest(er);
	}
		
	public void processElevatorRequests() throws InvalidDataParameterException{
		 boolean ElevatorRequestloop = true;
		
	     while (ElevatorRequestloop) {
	            synchronized (collectionOfElevatorRequests){
	            	if (collectionOfElevatorRequests.isEmpty() && collectionOfPeopleOnElevator.isEmpty()) {          	
	                	ElevatorRequestloop = false;
	                    continue;
	                }
	            }
	            
	            if(collectionOfElevatorRequests.isEmpty() && !collectionOfPeopleOnElevator.isEmpty()){
	            	riderRequest(collectionOfPeopleOnElevator.get(0));
	            }
	            
	            while (!collectionOfElevatorRequests.isEmpty()){
		            while (collectionOfElevatorRequests.get(0).getFloorNumber() != getCurrentLocation()){
						if (collectionOfElevatorRequests.get(0).getFloorNumber() > getCurrentLocation()){
							timer.end();
							System.out.printf("%s\t%s\n",timer.toStringTotalTime(),toStringElevatorMovementUp());
							moveUpFloor();
						} else {
							timer.end();
							System.out.printf("%s\t%s\n",timer.toStringTotalTime(),toStringElevatorMovementDown());
							moveDownFloor();
						}
		            }
						
					if (collectionOfElevatorRequests.get(0).getFloorNumber() == getCurrentLocation()){
						if(!collectionOfElevatorRequests.get(0).isDefaultFloorRequest()){
							if(collectionOfElevatorRequests.get(0).getDirection() != null){	
								timer.end();
								System.out.printf("%s\t%s\n",timer.toStringTotalTime(),toStringElevatorArrivalFloorRequest());
								timer.end();
								collectionOfElevatorRequests.remove(0);
								openDoor();
								System.out.printf("%s\t%s\n",timer.toStringTotalTime(),toStringOpenDoor());
								//People Leaving Elevator
								for(Iterator<Person> iterator = collectionOfPeopleOnElevator.iterator(); iterator.hasNext();){
									Person personRidering = iterator.next();
									if(personRidering.getEndFloorLocation() == getCurrentLocation()){
										personRidering.setPersonStatus("DONE");
										timer.end();
										System.out.println(String.format("%s\tPerson %s has left Elevator %d\t%s",
												timer.toStringTotalTime(),
												personRidering.getPersonId(),
												getElevatorId(),
												toStringElevatorRiders()));
										personRidering.setCurrentFloorLocation(getCurrentLocation());
										collectionOfPeopleDone.add(personRidering);
										//ElevatorController.getInstanceAfterCompletion().addPersonToFloorFromElevator(getCurrentLocation(), personRidering);
										timer.end();
										System.out.println(String.format("%s\tPerson %s has entered Floor %d\t%s",
												timer.toStringTotalTime(),
												personRidering.getPersonId(),
												getCurrentLocation()));
										iterator.remove();
									}
								}
								//People Entering Elevator
								for(Iterator<Person> iterator = collectionOfPeopleWaiting.iterator(); iterator.hasNext();){
									Person personWating = iterator.next();
									if(!isElevatorFull()){
										if(personWating.getCurrentFloorLocation() == getCurrentLocation()){
											personWating.setPersonStatus("RIDING");
											timer.end();
											System.out.println(String.format("%s\tPerson %s has left Floor %d\t[Floor People: ]",
													timer.toStringTotalTime(),
													personWating.getPersonId(),
													personWating.getCurrentFloorLocation()));
											collectionOfPeopleOnElevator.add(personWating);
											iterator.remove();
											timer.end();
											System.out.printf("%s\tPerson %s entered Elevator %d\t%s\n",
													timer.toStringTotalTime(),
													personWating.getPersonId(),
													getElevatorId(),
													toStringElevatorRiders());
											riderRequest(personWating);

										}
									}
								}
								timer.end();
								System.out.printf("%s\t%s\n",timer.toStringTotalTime(),toStringElevatorStatusOfRiders());
								closeDoor();
								timer.end();
								System.out.printf("%s\t%s\n",timer.toStringTotalTime(),toStringCloseDoor());
							} else {						
								timer.end();
								System.out.printf("%s\t%s\n",timer.toStringTotalTime(),toStringElevatorArrivalRiderRequest());
								timer.end();
								collectionOfElevatorRequests.remove(0);
								openDoor();
								System.out.printf("%s\t%s\n",timer.toStringTotalTime(),toStringOpenDoor());
								for(Iterator<Person> iterator = collectionOfPeopleOnElevator.iterator(); iterator.hasNext();){
									Person personRidering = iterator.next();
									if(personRidering.getEndFloorLocation() == getCurrentLocation()){
										personRidering.setPersonStatus("DONE");
										timer.end();
										System.out.println(String.format("%s\tPerson %s has left Elevator %d\t%s",
												timer.toStringTotalTime(),
												personRidering.getPersonId(),
												getElevatorId(),
												toStringElevatorRiders()));
										personRidering.setCurrentFloorLocation(getCurrentLocation());
										//ElevatorController.getInstanceAfterCompletion().addPersonToFloorFromElevator(getCurrentLocation(), personRidering);
										collectionOfPeopleDone.add(personRidering);
										timer.end();
										System.out.println(String.format("%s\tPerson %s has entered Floor %d\t[Floor People: ]",
												timer.toStringTotalTime(),
												personRidering.getPersonId(),
												getCurrentLocation()));
										iterator.remove();
									}
								}
								for(Iterator<Person> iterator = collectionOfPeopleWaiting.iterator(); iterator.hasNext();){
									Person personWating = iterator.next();
									if(!isElevatorFull()){
										if(personWating.getCurrentFloorLocation() == getCurrentLocation()){
											personWating.setPersonStatus("RIDING");
											timer.end();
											System.out.println(String.format("%s\tPerson %s has left Floor %d\t[Floor People:]",
													timer.toStringTotalTime(),
													personWating.getPersonId(),
													personWating.getCurrentFloorLocation()));
											collectionOfPeopleOnElevator.add(personWating);
											iterator.remove();
											timer.end();
											System.out.printf("%s\tPerson %s entered Elevator %d\t%s\n",
													timer.toStringTotalTime(),
													personWating.getPersonId(),
													getElevatorId(),
													toStringElevatorRiders());
											riderRequest(personWating);
										}
									}
								}
								timer.end();
								System.out.printf("%s\t%s\n",timer.toStringTotalTime(),toStringElevatorStatusOfRiders());
								closeDoor();
								timer.end();
								System.out.printf("%s\t%s\n",timer.toStringTotalTime(),toStringCloseDoor());
							}

						} else  {
							timer.end();
							System.out.printf("%s\t%s\n",timer.toStringTotalTime(),toStringDefaultRequest());
							collectionOfElevatorRequests.remove(0);
						}
		            }
	            }
	     	}
		}			     

	
	//To String Elevator movement operations
	public String toStringElevatorFloorRequest(int floorNumberIn){
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("Elevator %d ",getElevatorId()));
		sb.append(String.format("going to Floor %d ",floorNumberIn));
		sb.append(String.format("for %s request",getDirection()));
		sb.append(String.format("\t[Floor Requests: "));
		for(ElevatorRequests er: collectionOfElevatorRequests){
			if(er.getDirection() != null){
				sb.append(String.format("%d ",er.getFloorNumber()));
			}
		}
		sb.append(String.format("][Rider Request: "));
		for(ElevatorRequests err: collectionOfElevatorRequests){
			if(err.getDirection() == null){
				sb.append(String.format("%d ",err.getFloorNumber()));
			}
		}
		sb.append(String.format("]"));
		return sb.toString();
	}
	
	public String toStringElevatorRiderRequest(int floorNumberIn){
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("Elevator %d ",getElevatorId()));
		sb.append(String.format("Rider Request made for floor %d ",floorNumberIn));
		sb.append(String.format("\t[Floor Requests: "));
		for(ElevatorRequests er: collectionOfElevatorRequests){
			if(er.getDirection() != null){
				sb.append(String.format("%d ",er.getFloorNumber()));
			}
		}
		sb.append(String.format("][Rider Request: "));
		for(ElevatorRequests err: collectionOfElevatorRequests){
			if(err.getDirection() == null){
				sb.append(String.format("%d ",err.getFloorNumber()));
			}
		}
		sb.append(String.format("]"));
		return sb.toString();
	}	
	
	public String toStringElevatorMovementUp(){
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("Elevator %d ",getElevatorId()));
		sb.append(String.format("is moving from Floor %d ",getCurrentLocation()));
		sb.append(String.format("to Floor %d",getCurrentLocation()+1));
		sb.append(String.format("\t[Floor Requests: "));
		for(ElevatorRequests er: collectionOfElevatorRequests){
			if(er.getDirection() != null){
				sb.append(String.format("%d ",er.getFloorNumber()));
			}
		}
		sb.append(String.format("][Rider Request: "));
		for(ElevatorRequests err: collectionOfElevatorRequests){
			if(err.getDirection() == null){
				sb.append(String.format("%d ",err.getFloorNumber()));
			}
		}
		sb.append(String.format("]"));
		return sb.toString();
	}

	public String toStringElevatorMovementDown(){
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("Elevator %d ",getElevatorId()));
		sb.append(String.format("is moving from Floor %d ",getCurrentLocation()));
		sb.append(String.format("to Floor %d",getCurrentLocation()-1));
		sb.append(String.format("\t[Floor Requests: "));
		for(ElevatorRequests er: collectionOfElevatorRequests){
			if(er.getDirection() != null){
				sb.append(String.format("%d ",er.getFloorNumber()));
			}
		}
		sb.append(String.format("][Rider Request: "));
		for(ElevatorRequests err: collectionOfElevatorRequests){
			if(err.getDirection() == null){
				sb.append(String.format("%d ",err.getFloorNumber()));
			}
		}
		sb.append(String.format("]"));
		return sb.toString();
	}
	
	public String toStringOpenDoor(){
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("Elevator %d ",getElevatorId()));
		sb.append(String.format("Doors %s",getDoorStatus()));
		return sb.toString();
	}

	public String toStringCloseDoor() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("Elevator %d ",getElevatorId()));
		sb.append(String.format("Doors %s",getDoorStatus()));
		return sb.toString();
	}

	public String toStringElevatorArrivalFloorRequest() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("Elevator %d ",getElevatorId()));
		sb.append(String.format("has arrived at Floor %d ",getCurrentLocation()));
		sb.append(String.format("for Floor request"));
		return sb.toString();
	}

	public String toStringElevatorArrivalRiderRequest() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("Elevator %d ",getElevatorId()));
		sb.append(String.format("has arrived at Floor %d ",getCurrentLocation()));
		sb.append(String.format("for Rider Request"));
		return sb.toString();
	}
	
	public String toStringDefaultRequest() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("Elevator %d ",getElevatorId()));
		if(getCurrentLocation() != getDefaultFloor()){
			sb.append(String.format("has timed out - returning to Default Floor %d ",getDefaultFloor()));
			setDirection("IDLE");
		} else {
			sb.append(String.format("has arrived at Default Floor %d after Timeout",getDefaultFloor()));
			setDirection("IDLE");
		}
		return sb.toString();
	}
	
	public String toStringRejectRequest() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("Elevator %d ",getElevatorId()));
		sb.append(String.format("a request has been made for a floor in the WRONG DIRECTION - Ignoring Request"));
		return sb.toString();
	}
		
	public String toStringNoCurrentRequest() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("Elevator %d ",getElevatorId()));
		sb.append(String.format("has No Request [Floor Requests:][Rider Request:]"));
		return sb.toString();
	}	
	
	
	public String toStringElevatorRiders(){
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("[Riders: "));
		if(!collectionOfPeopleOnElevator.isEmpty()){
			for(Person person: collectionOfPeopleOnElevator){
				sb.append(String.format("%s ",person.getPersonId()));
			}
		}
		sb.append(String.format("]"));
		return sb.toString();
	}
	
	
	
	public String toStringElevatorStatusOfRiders(){
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("Elevator %d ",getElevatorId()));
		sb.append(String.format("[Riders ",getElevatorId()));
		if(!collectionOfPeopleOnElevator.isEmpty()){
			for(Person person: collectionOfPeopleOnElevator){
				sb.append(String.format("%s ",person.getPersonId()));
			}
		} else {
			sb.append(String.format("None"));
		}
		sb.append(String.format("]",getElevatorId()));
		return sb.toString();
	}
	
	
	
	public void run() {
		running = true;
		
		while(running){
			try{
				synchronized (collectionOfElevatorRequests) { 
					synchronized (collectionOfPeopleOnElevator){
						if(collectionOfElevatorRequests.isEmpty() && collectionOfPeopleOnElevator.isEmpty()){
							System.out.printf(String.format("%s\t%s\n",timer.toStringTotalTime(),toStringNoCurrentRequest()));
							setDirection("IDLE");
							if(getCurrentLocation() == defaultFloor){
								collectionOfElevatorRequests.wait();
							} else {
								collectionOfElevatorRequests.wait(15000);
							}
						}
					}
				}
			} catch (InterruptedException ex){
				System.out.printf(String.format("Interrupted! Going back to check for requests/wait\n"));
                continue;
			}
			
			if(collectionOfElevatorRequests.isEmpty() && collectionOfPeopleOnElevator.isEmpty() && getCurrentLocation() != defaultFloor){
				timer.end();
				System.out.printf("%s\t%s\n",timer.toStringTotalTime(),toStringDefaultRequest());
				returnToDefaultFloor();
				try {
					processElevatorRequests();
				} catch (InvalidDataParameterException e) {
					e.printStackTrace();
				}
			} else {
				try {
					processElevatorRequests();
				} catch (InvalidDataParameterException e) {
					e.printStackTrace();
				}
			}
			
        }
		//timer.end();
        //System.out.println(String.format("%s\tElevator %d 'run' method ending shut down",timer.toStringTotalTime(),getElevatorId()));
    }


    public void shutDown() {
    	//timer.end();
        //System.out.println(String.format("%s\tRequesting Elevator thread to shutdown",timer.toStringTotalTime()));
        synchronized (collectionOfElevatorRequests) { 
            running = false;
            collectionOfElevatorRequests.notifyAll();
        }
    }








}
