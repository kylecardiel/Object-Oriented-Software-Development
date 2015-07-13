/*Kyle Cardiel
 * SE450 - Elevator Project
 * ElevatorController Class - creates a singleton object (ElevatorController) which creates and owns a collection of floors and elevators
 * 		in the building. 
 * 
 */

package ElevatorSimulationPackage.Structures;



import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import ElevatorSimulationPackage.Common.ElevatorRequests;
import ElevatorSimulationPackage.Common.InvalidDataParameterException;
import ElevatorSimulationPackage.Common.Timer;
import ElevatorSimulationPackage.Elevator.Elevator;
import ElevatorSimulationPackage.Elevator.PassengerElevator;
import ElevatorSimulationPackage.ElevatorControllerOperations.ElevatorControllerOperations;
import ElevatorSimulationPackage.ElevatorControllerOperations.ElevatorControllerOperationsDefault;
import ElevatorSimulationPackage.ElevatorControllerOperations.ElevatorControllerOperationsPendingRequests;
import ElevatorSimulationPackage.ElevatorControllerOperations.ElevatorControllerOperationsPendingRequestsDefault;
import ElevatorSimulationPackage.Person.Person;

public class ElevatorController implements Runnable {

	private int numberOfElevators;
	private int numberOfFloors;
	private Timer timer = Timer.getInstance();
	private List<Floor> collectionOfFloors = new ArrayList<Floor>();
	private List<Elevator> collectionOfElevators = new ArrayList<Elevator>();
	private int maxPersonCapacity;
	private int timePerFloor;
	private int timePerDoor;
	private int defaultFloor;
	private List<ElevatorRequests> collectionOfReceivedRequestsPending = new ArrayList<ElevatorRequests>();
	private List<Person> collectionOfReceivedRequestsPendingPeople = new ArrayList<Person>();
	private static ElevatorController instance;
	private ElevatorControllerOperations eco = new ElevatorControllerOperationsDefault();
	private ElevatorControllerOperationsPendingRequests ecopr = new ElevatorControllerOperationsPendingRequestsDefault();
	
	private boolean running = false;
	

	//Singleton Object Constructor
	private ElevatorController(int numberOfElevatorsIn, int numberOfFloorsIn,
			int maxPersonCapacityIn,int timePerFloorIn, int timePerDoorIn, int defualtFloorIn) throws InvalidDataParameterException{
		setNumberOfFloors(numberOfFloorsIn);
		setNumberOfElevators(numberOfElevatorsIn);
		setMaxPersonCapacity(maxPersonCapacityIn);
		setTimePerFloor(timePerFloorIn);
		setTimePerDoor(timePerDoorIn);
		setDefaultFloor(defualtFloorIn);
		createFloors();
		createElevators();
	}

	//Access
	public static synchronized ElevatorController getInstance(int numberOfElevatorsIn, int numberOfFloorsIn,
			int maxPersonCapacityIn,int timePerFloorIn, int timePerDoorIn, int defaultFloorIn) throws InvalidDataParameterException{
		if(instance == null){
			instance = new ElevatorController(numberOfElevatorsIn, numberOfFloorsIn, 
					maxPersonCapacityIn, timePerFloorIn, timePerDoorIn, defaultFloorIn);
		}
		return instance;}

	public static synchronized ElevatorController getInstanceAfterCompletion(){
		return instance;}
	
	public int getNumberOfFloors(){
		return numberOfFloors;}
	
	public int getNumberOfElevators(){
		return numberOfElevators;}
	
	public Elevator getElevator(int elevatorNumberIn){
		return collectionOfElevators.get(elevatorNumberIn-1);}
	
	public int getDefaultFloor(){
		return defaultFloor;}
	
	public List<Elevator> getElevators(){
		return collectionOfElevators;}

	public ElevatorControllerOperations getElevatorControllerOperations(){
		return eco;}

	public Floor getFloor(int floorNumberIn){
		return collectionOfFloors.get(floorNumberIn-1);}
	
	public List<Floor> getFloors(){
		return collectionOfFloors;}
	
	public List<ElevatorRequests> getCollectionOfReceivedRequestsPending(){
		return collectionOfReceivedRequestsPending;}
	
	public List<Person> getCollectionOfReceivedRequestsPendingPeople() {
		return collectionOfReceivedRequestsPendingPeople;
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
	private void setNumberOfElevators(int numberOfElevatorsIn){
		numberOfElevators = numberOfElevatorsIn;}

	private void setNumberOfFloors(int numberOfFloorsIn) {
		numberOfFloors = numberOfFloorsIn;}

	public void setMaxPersonCapacity(int maxPersonCapacityIn) throws InvalidParameterException {
		if(maxPersonCapacityIn < 1){
			throw new InvalidParameterException();
			}
		maxPersonCapacity = maxPersonCapacityIn;}
	
	public void setTimePerFloor(int timePerFloorIn) {
		timePerFloor = timePerFloorIn;
	}
	
	public void setTimePerDoor(int timePerDoorIn) {
		timePerDoor = timePerDoorIn;}
	
	public void setDefaultFloor(int defaultFloorIn) {
		defaultFloor = defaultFloorIn;}

	//Operations

	private void createElevators(){
		int count = 0;
		while (count < numberOfElevators){
			PassengerElevator elevator = new PassengerElevator(count,getMaxPersonCapacity(),getTimePerFloor(),getTimePerDoor(),getDefaultFloor());
			collectionOfElevators.add(elevator);
			count++;
		}
	}

	private void createFloors() throws InvalidDataParameterException{
		int count = 1;
		while (count < numberOfFloors+1){
			Floor floor = new Floor(count);
			collectionOfFloors.add(floor);
			count++;
		}
	}

	
	public void movePeople(){
		for(Elevator e: getElevators()){
			for(Person p: e.getCollectionOfPeopleDone()){
				getFloor(p.getEndFloorLocation()).getCollectionOfPeopleDone().add(p);
			}
		}
		for(Elevator e: getElevators()){
			e.getCollectionOfPeopleDone().removeAll(e.getCollectionOfPeopleDone());
		}
		
	}
	
	public void addPersonToFloorFromElevator(int floorNumberIn, Person personIn){
		getFloor(floorNumberIn).addPersonToFloor(personIn);
	}

	public void receiveElevatorFloorRequest(ElevatorRequests ElevatorRequestIn, Person personIn){
		int elevatorId = getElevatorControllerOperations().selectElevatorToAddRequest(ElevatorRequestIn, collectionOfElevators);
		if(elevatorId > 0){
			//timer.end();
			//System.out.println(String.format("%s",toStringOperation(ElevatorRequestIn,elevatorId)));
			if(getElevator(elevatorId).addElevatorRequest(ElevatorRequestIn)){
				getElevator(elevatorId).addWaitingRider(personIn);
				getFloor(ElevatorRequestIn.getFloorNumber()).reomvePersonFromFloor(personIn);
			} else {
				synchronized (collectionOfReceivedRequestsPending){
					collectionOfReceivedRequestsPendingPeople.add(personIn);
					collectionOfReceivedRequestsPending.add(ElevatorRequestIn);
					collectionOfReceivedRequestsPending.notifyAll();
				}
			}
		} else {
			synchronized (collectionOfReceivedRequestsPending){
				collectionOfReceivedRequestsPendingPeople.add(personIn);
				collectionOfReceivedRequestsPending.add(ElevatorRequestIn);
				collectionOfReceivedRequestsPending.notifyAll();
			}
		}
	}


	public void selectElevator(){
		
		boolean ElevatorPendingRequestloop = true;
		
		while (ElevatorPendingRequestloop) {
			synchronized (collectionOfReceivedRequestsPending){
				if (collectionOfReceivedRequestsPending.isEmpty()) {          	
						ElevatorPendingRequestloop = false;
						continue;
					}
			}

			synchronized (collectionOfReceivedRequestsPending){
		        //Are there any pending elevator requests?
		        if(!collectionOfReceivedRequestsPending.isEmpty() && !collectionOfReceivedRequestsPendingPeople.isEmpty()){
		        	for(Elevator e: getElevators()){
		        		if(e.getDirection().equals("IDLE") || e.getCollectionOfElevatorRequests().isEmpty()){
		        			ecopr.selectRequestForElevator(collectionOfReceivedRequestsPending, 
		        					collectionOfReceivedRequestsPendingPeople, e);
		        		} 
		        		break;
		        	}
		        }
	        
			}
		}
	}
	
	//toString
	public String toStringOperation(ElevatorRequests ElevatorRequestIn, int ElevatorId){
		StringBuilder sb = new StringBuilder();
		sb.append((String.format("%s\tElevatorController added ",timer.toStringTotalTime())));
		sb.append((String.format("floor request %d ",ElevatorRequestIn.getFloorNumber())));
		sb.append((String.format("to Elevator %d",ElevatorId)));
		return sb.toString();
	}

	
	
	@Override
	public void run() {
		running = true;
		
		while(running){
			try{
				synchronized (collectionOfReceivedRequestsPending) {
					if(collectionOfReceivedRequestsPending.isEmpty()){
						collectionOfReceivedRequestsPending.wait();
					}
				}
			} catch (InterruptedException ex){
					System.out.printf(String.format("Interrupted! Going back to check for requests/wait\n"));
	                continue;
			}
			selectElevator();
		}
	}

	
    public void shutDown() {
        synchronized (collectionOfReceivedRequestsPending) { 
            running = false;
            collectionOfReceivedRequestsPending.notifyAll();
        }
        
    }

	public void reports(){
		movePeople();

		//Table 2A - Average/Min/Max Wait Time by floor (in seconds)
		System.out.println("\n\n\nTable 2A - Average/Min/Max Wait Time by floor (in seconds)");
		StringBuilder sbHeader = new StringBuilder();
		sbHeader.append(String.format("%-10s\t","Floor"));
		sbHeader.append(String.format("%-20s\t","Average Wait Time"));
		sbHeader.append(String.format("%-20s\t","Min Wait Time"));
		sbHeader.append(String.format("%-20s\t","Max Wait Time"));
		System.out.println(sbHeader.toString());
		
		double waitTimeByFloor[][] = new double[numberOfFloors][3];
		int PeoplePerFloor [] = new int[numberOfFloors];
		
		for(Floor f: getFloors()){
			for(Person p: f.getCollectionOfPeopleDone()){
				double waitTime = p.getTotalWaitTime();
				waitTimeByFloor[p.getStartFloorLocation()-1][0] += waitTime;
				PeoplePerFloor [p.getStartFloorLocation()-1] += 1;
				
				if(Double.isNaN(waitTimeByFloor[p.getStartFloorLocation()-1][1])){
					waitTimeByFloor[p.getStartFloorLocation()-1][1] = waitTime;
				}else if(waitTimeByFloor[p.getStartFloorLocation()-1][1] > waitTime){
					waitTimeByFloor[p.getStartFloorLocation()-1][1] = waitTime;
				}
				if(waitTimeByFloor[p.getStartFloorLocation()-1][2] < waitTime){
					waitTimeByFloor[p.getStartFloorLocation()-1][2] = waitTime;
				}
			}
		}
		for (int i = 0; i < numberOfFloors; i++){
			waitTimeByFloor[i][0] = (waitTimeByFloor[i][0]/PeoplePerFloor [i]);
			StringBuilder rows = new StringBuilder();
			rows.append(String.format("%-10s\t","Floor " + (i+1)));
			if(waitTimeByFloor[i][0] > 0){
				rows.append(String.format("%-2.0f %-15s\t",waitTimeByFloor[i][0],"seconds"));
				rows.append(String.format("%-2.0f %-15s\t",waitTimeByFloor[i][1],"seconds"));
				rows.append(String.format("%-2.0f %-15s\t",waitTimeByFloor[i][2],"seconds"));
			}else{
				rows.append(String.format("%-20s\t","N/A"));
				rows.append(String.format("%-20s\t","N/A"));
				rows.append(String.format("%-20s\t","N/A"));
			}

			System.out.println(rows.toString());
		}
		
		
		double avgRideTimeFloorToFloor[][] = new double[numberOfFloors][numberOfFloors];
		double PeopleCountFloorToFloor [][] = new double[numberOfFloors][numberOfFloors];
		
		//Table 2B - Average Ride Time from Floor to Floor by Person (in seconds)
		System.out.println("\n\nTable 2B - Average Ride Time from Floor to Floor by Person (in seconds)");
		StringBuilder bHeader = new StringBuilder();
		bHeader.append(String.format("%-5s\t","Floor"));
		for(int i = 0; i < numberOfFloors; i++){
			bHeader.append(String.format("%-3d ",i+1));
		}
		System.out.println(bHeader.toString());
		
		for(Floor f: getFloors()){
			for(Person p: f.getCollectionOfPeopleDone()){
				double rideTime = p.getTotalRideTime();
				avgRideTimeFloorToFloor[p.getStartFloorLocation()-1][p.getEndFloorLocation()-1] += rideTime;
				PeopleCountFloorToFloor[p.getStartFloorLocation()-1][p.getEndFloorLocation()-1] += 1;
			}
		}
		
		for (int i = 0; i < numberOfFloors; i++){
			StringBuilder rows = new StringBuilder();
			rows.append(String.format("%-5d\t",(i+1)));
			for (int j = 0; j < numberOfFloors; j++){
				if(i == j){
					rows.append(String.format("%-3s ",("x")));
				} else {
					double avg = avgRideTimeFloorToFloor[i][j]/PeopleCountFloorToFloor[i][j];
					if(Double.isNaN(avg)){
						rows.append(String.format("%-3s ",("-")));
					} else{
						rows.append(String.format("%-3.0f ",(avg)));	
					}
				}
			}
			System.out.println(rows.toString());
		}
		
				
		
		//Table 2C - Max Ride Time from Floor to Floor by Person (in seconds)
		System.out.println("\n\nTable 2C - Max Ride Time from Floor to Floor by Person (in seconds)");
		System.out.println(bHeader.toString());
		
		double maxRideTimeFloorToFloor[][] = new double[numberOfFloors][numberOfFloors];
		
		for(Floor f: getFloors()){
			for(Person p: f.getCollectionOfPeopleDone()){
				double rideTime = p.getTotalRideTime();
				if(rideTime > maxRideTimeFloorToFloor[p.getStartFloorLocation()-1][p.getEndFloorLocation()-1]){
					maxRideTimeFloorToFloor[p.getStartFloorLocation()-1][p.getEndFloorLocation()-1] = rideTime;
				}
			}
		}
		
		for (int i = 0; i < numberOfFloors; i++){
			StringBuilder rows = new StringBuilder();
			rows.append(String.format("%-5d\t",(i+1)));
			for (int j = 0; j < numberOfFloors; j++){
				if(j==i){
					rows.append(String.format("%-3s ",("x")));
				} else {
				rows.append(String.format("%-3.0f ",maxRideTimeFloorToFloor[i][j]));
				}
			}
			System.out.println(rows.toString());
		}
		
		
		//Table 2D - Min Ride Time from Floor to Floor by Person (in seconds)
		System.out.println("\n\nTable 2D - Min Ride Time from Floor to Floor by Person (in seconds)");
		System.out.println(bHeader.toString());
		
		double minRideTimeFloorToFloor[][] = new double[numberOfFloors][numberOfFloors];
		
		for(Floor f: getFloors()){
			for(Person p: f.getCollectionOfPeopleDone()){
				double rideTime = p.getTotalRideTime();
				if(Double.isNaN(minRideTimeFloorToFloor[p.getStartFloorLocation()-1][p.getEndFloorLocation()-1])){
					minRideTimeFloorToFloor[p.getStartFloorLocation()-1][p.getEndFloorLocation()-1] = rideTime;
				}
				if(rideTime < minRideTimeFloorToFloor[p.getStartFloorLocation()-1][p.getEndFloorLocation()-1]){
					minRideTimeFloorToFloor[p.getStartFloorLocation()-1][p.getEndFloorLocation()-1] = rideTime;
				}
			}
		}
		
		for (int i = 0; i < numberOfFloors; i++){
			StringBuilder rows = new StringBuilder();
			rows.append(String.format("%-5d\t",(i+1)));
			for (int j = 0; j < numberOfFloors; j++){
				if(j==i){
					rows.append(String.format("%-3s ",("x")));
				} else {				
					rows.append(String.format("%-3.0f ",minRideTimeFloorToFloor[i][j]));
				}
			}
			System.out.println(rows.toString());
		}
		
				
		//Table 2E - Wait/Ride/Total Time by Person
		System.out.println("\n\nTable 2E - Wait/Ride/Total Time by Person");
		StringBuilder eHeader = new StringBuilder();
		eHeader.append(String.format("%-6s  ","Person"));
		eHeader.append(String.format("%-12s  ","Start Floor"));
		eHeader.append(String.format("%-18s  ","Destination Floor"));
		eHeader.append(String.format("%-15s  ","Wait Time"));
		eHeader.append(String.format("%-15s  ","Ride Time"));
		eHeader.append(String.format("%-15s  ","Total Time"));
		System.out.println(eHeader.toString());		

		for(Floor f: getFloors()){
			for(Person p: f.getCollectionOfPeopleDone()){
				StringBuilder sb = new StringBuilder();
				sb.append(String.format("%-6s  ",p.getPersonId()));
				sb.append(String.format("    %-6d  ",p.getStartFloorLocation()));
				sb.append(String.format("        %-12d  ",p.getEndFloorLocation()));
				sb.append(String.format("%-2.0f %-12s  ",p.getTotalWaitTime(),"seconds"));
				sb.append(String.format("%-2.0f %-12s  ",p.getTotalRideTime(),"seconds"));
				sb.append(String.format("%-2.0f %-12s  ",p.getTotalWaitTime() + p.getTotalRideTime(),"seconds"));
				System.out.println(sb.toString());
			}
		}


	}




}
