/*Kyle Cardiel
 * SE450 - Elevator Project
 * 
 */

package ElevatorSimulationPackage.ElevatorControllerOperations;

import java.util.List;

import ElevatorSimulationPackage.Common.ElevatorRequests;
import ElevatorSimulationPackage.Elevator.Elevator;
import ElevatorSimulationPackage.Person.Person;

public interface ElevatorControllerOperationsPendingRequests {

	void selectRequestForElevator(List<ElevatorRequests> collectionOfReceivedRequestsPending, List<Person> collectionOfReceivedRequestsPendingPeopleIn, Elevator elevatorIn);	
	
}
