/*Kyle Cardiel
 * SE450 - Elevator Project
 * 
 */

package ElevatorSimulationPackage.ElevatorControllerOperations;

import java.util.List;

import ElevatorSimulationPackage.Common.ElevatorRequests;
import ElevatorSimulationPackage.Elevator.Elevator;
import ElevatorSimulationPackage.Person.Person;

public class ElevatorControllerOperationsPendingRequestsDefault implements ElevatorControllerOperationsPendingRequests{
	
	public ElevatorControllerOperationsPendingRequestsDefault(){}
	
	
	@Override
	public void selectRequestForElevator(List<ElevatorRequests> collectionOfReceivedRequestsPendingIn, 
			List<Person> collectionOfReceivedRequestsPendingPeopleIn, 
			Elevator elevatorIn) {
		
		ElevatorRequests initialElevatorRequest = collectionOfReceivedRequestsPendingIn.get(0);

		//Select the First (Initial) Request and add to List of selected Pending Requests
		elevatorIn.addElevatorRequest(initialElevatorRequest);
		elevatorIn.addWaitingRider(collectionOfReceivedRequestsPendingPeopleIn.get(0));
		//initialElevatorRequest = getCollectionOfReceivedRequestsPending().get(0);
		collectionOfReceivedRequestsPendingIn.remove(0);
		collectionOfReceivedRequestsPendingPeopleIn.remove(0);
		
		//Any remaining Elevator Requests?
		if(!collectionOfReceivedRequestsPendingIn.isEmpty()){
				for(ElevatorRequests er: collectionOfReceivedRequestsPendingIn){
	    			//Is the Next Request’s direction the same as the Initial requests direction?
					if(collectionOfReceivedRequestsPendingIn.get(0).getDirection().equals(er.getDirection())){
						//Is the direction of the Initial Request UP?
						if(initialElevatorRequest.getDirection().equals("UP")){
							//Is the direction from the initial request floor to the Next Request floor UP?
							if(initialElevatorRequest.getFloorNumber() < collectionOfReceivedRequestsPendingIn.get(0).getFloorNumber()){
								//Add the Next Request to the List of Selected Requests
								elevatorIn.addElevatorRequest(collectionOfReceivedRequestsPendingIn.get(0));
								elevatorIn.addWaitingRider(collectionOfReceivedRequestsPendingPeopleIn.get(0));
								collectionOfReceivedRequestsPendingIn.remove(0);
								collectionOfReceivedRequestsPendingPeopleIn.remove(0);
							}
						} else {
							//Is the direction from the initial request floor to the Next Request floor DOWN?
							if(initialElevatorRequest.getFloorNumber() > collectionOfReceivedRequestsPendingIn.get(0).getFloorNumber()){
								//Add the Next Request to the List of Selected Requests
								elevatorIn.addElevatorRequest(collectionOfReceivedRequestsPendingIn.get(0));
								elevatorIn.addWaitingRider(collectionOfReceivedRequestsPendingPeopleIn.get(0));
								collectionOfReceivedRequestsPendingIn.remove(0);
								collectionOfReceivedRequestsPendingPeopleIn.remove(0);
							}
						}
	
					}
					}
			
		}
		
		
		
	}
		
		
		
		
	}


