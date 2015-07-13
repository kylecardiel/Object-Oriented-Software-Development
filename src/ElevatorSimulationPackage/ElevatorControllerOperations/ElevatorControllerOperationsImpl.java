/*Kyle Cardiel
 * SE450 - Elevator Project
 * 
 */

package ElevatorSimulationPackage.ElevatorControllerOperations;

import java.util.ArrayList;
import java.util.List;

import ElevatorSimulationPackage.Common.ElevatorRequests;
import ElevatorSimulationPackage.Elevator.Elevator;

public class ElevatorControllerOperationsImpl implements ElevatorControllerOperations {

	//Data members
	private ElevatorControllerOperations elevatorControllerOperations;
	private List<Elevator> collectionOfElevators = new ArrayList<Elevator>();
	private List<ElevatorRequests> collectionOfReceivedRequests = new ArrayList<ElevatorRequests>();


	//Constructor with delegate ElevatorControllerOperations 
	public ElevatorControllerOperationsImpl(){
		//setElevatorControllerOperations(ElevatorControllerOperationsImplFactory.ElevatorControllerOperationsBuilder());
	}

	//Access
	public List<ElevatorRequests> getCollectionOfReceivedRequests(){
		return collectionOfReceivedRequests;}

	public ElevatorControllerOperations getElevatorControllerOperations() {
		return elevatorControllerOperations;
	}

	public List<Elevator> getCollectionOfElevators() {
		return collectionOfElevators;
	}

	//Modifiers
	public void setElevatorControllerOperations(ElevatorControllerOperations elevatorControllerOperationsIn) {
		elevatorControllerOperations = elevatorControllerOperationsIn;
	}

	public void setCollectionOfElevators(List<Elevator> collectionOfElevatorsIn) {
		collectionOfElevators = collectionOfElevatorsIn;
	}

	//Operations
	public int selectElevatorToAddRequest(ElevatorRequests ElevatorRequestIn, List<Elevator> collectionOfElevatorsIn){
		
		setCollectionOfElevators(collectionOfElevatorsIn);
		int floor = ElevatorRequestIn.getFloorNumber();
		String direction = ElevatorRequestIn.getDirection();

		for(Elevator e: getCollectionOfElevators()){
			//Is there an elevator on this floor?
			if(e.getCurrentLocation() == floor){
				//Is there an elevator on this floor that is Idle or going in the desired direction?
				if(e.getDirection().equals("IDLE") || e.getDirection().equals(direction)){
					//Add Floor/Direction request to that elevator(in order of arrival) - EC handles this
					//timer.end();
					//System.out.println(String.format("%s\t(1) An elevator was chosen at this branch (same floor) with Elevator %d",timer.toStringTotalTime(),e.getElevatorId()));
					return e.getElevatorId();  
				}
			}	

			//Is there an elevator already moving?
			if(!e.getDirection().equals("IDLE")){
				//Is the elevator going in desired direction of travel?
				if(floor > e.getCurrentLocation() && e.getDirection().equals("UP") ||
				   floor < e.getCurrentLocation() && e.getDirection().equals("DOWN")){	
					//Is the elevator going to a rider's floor number request? 
					if(!e.getCollectionOfPeopleOnElevator().isEmpty()){
						for(ElevatorRequests er: e.getCollectionOfElevatorRequests()){
							//Is the elevator moving towards the requesting floor?
							if(er.getFloorNumber() > floor && e.getDirection().equals("UP") ||
									er.getFloorNumber() < floor && e.getDirection().equals("DOWN")){							
								//Is the elevator moving in the same direction requested by the new floor request?
								if(e.getDirection().equals(direction)){
				//				for(ElevatorRequests err: e.getCollectionOfElevatorRequests()){
				//					if(err.getFloorNumber() > floor && e.getDirection().equals("UP") && e.getDirection().equals(direction)
				//							|| err.getFloorNumber() < floor && e.getDirection().equals("DOWN") && e.getDirection().equals(direction)){
										//Add Floor/Direction request to that elevator (in order of arrival) - EC handles this
										//timer.end();
										//System.out.println(String.format("%s\t(2) No....An elevator was chosen at this branch (rider is going to this floor already) with Elevator %d",timer.toStringTotalTime(),e.getElevatorId()));
										return e.getElevatorId();
								}
									
							}
						}
					} else {
						//Is the elevator moving towards the requesting floor?
						for(ElevatorRequests er: e.getCollectionOfElevatorRequests()){
							//Is the elevator moving towards the requesting floor?
							if(er.getFloorNumber() > floor && e.getDirection().equals("UP") ||
									er.getFloorNumber() < floor && e.getDirection().equals("DOWN")){
				//		if((e.getCollectionOfElevatorRequests().get(e.getCollectionOfElevatorRequests().size()-1).getFloorNumber()) >= floor 
				//				&& e.getDirection().equals("UP") ||
				//		   (e.getCollectionOfElevatorRequests().get(e.getCollectionOfElevatorRequests().size()-1).getFloorNumber()) <= floor 
				//		   		//&& e.getDirection().equals("DOWN")){
							//Is the elevator moving in the same direction requested by the new floor request?
								if(e.getDirection().equals(direction)){
				//			for(ElevatorRequests err: e.getCollectionOfElevatorRequests()){
				//				if(err.getFloorNumber() > floor && e.getDirection().equals("UP") && e.getDirection() == direction 
				//						|| err.getFloorNumber() < floor && e.getDirection().equals("DOWN") && e.getDirection().equals(direction)){
									//Is the direction of the elevators current request the same as the direction of this request?
									if(e.getCollectionOfElevatorRequests().get(0).getDirection().equals(direction)){
				//					if(e.getDirection() == direction){
										//Add Floor/Direction request to that elevator (in order of arrival) - EC handles this
										//timer.end();
										//System.out.println(String.format("%s\t(3) YOU ARE WRONG....An elevator was chosen at this branch (elevator is moving towards this floor and byeon already) with Elevator %d",timer.toStringTotalTime(),e.getElevatorId()));
										return e.getElevatorId();
									}
								}
							}
						}
					}
				}
			}
			
			//Is there an Idle Elevator?
			if(e.getDirection() == "IDLE"){
				//Add Floor/Direction request to that elevator (in order of arrival) - EC handles this
				//timer.end();
				//System.out.println(String.format("%s\t(4) YOU ARE WRONG....An elevator was chosen at this branch (idle) with Elevator %d",timer.toStringTotalTime(),e.getElevatorId()));
				return e.getElevatorId();
			}
		}
		//Add Floor/Direction request to the unique Pending Requests list + Pending flowchart algo
		return 0;
	}


}
