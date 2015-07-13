/*Kyle Cardiel
 * SE450 - Elevator Project
 */

package ElevatorSimulationPackage.ElevatorControllerOperations;

import java.util.List;

import ElevatorSimulationPackage.Common.ElevatorControllerOperationsImplFactory;
import ElevatorSimulationPackage.Common.ElevatorRequests;
import ElevatorSimulationPackage.Elevator.Elevator;

public class ElevatorControllerOperationsDefault implements ElevatorControllerOperations {

	private ElevatorControllerOperations elevatorControllerOperations;
	
	//Constructor with delegate elevator
	public ElevatorControllerOperationsDefault() {
		elevatorControllerOperations = ElevatorControllerOperationsImplFactory.ElevatorControllerOperationsBuilder();}
	
	@Override
	public int selectElevatorToAddRequest(ElevatorRequests ElevatorRequestIn, List<Elevator> collectionOfElevators) {
		return elevatorControllerOperations.selectElevatorToAddRequest(ElevatorRequestIn, collectionOfElevators);
	}


}



