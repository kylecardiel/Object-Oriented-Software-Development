/*Kyle Cardiel
 * SE450 - Elevator Project
 */

package ElevatorSimulationPackage.ElevatorControllerOperations;

import java.util.List;

import ElevatorSimulationPackage.Common.ElevatorRequests;
import ElevatorSimulationPackage.Elevator.Elevator;

public interface ElevatorControllerOperations {

	int selectElevatorToAddRequest(ElevatorRequests ElevatorRequestIn, List<Elevator> collectionOfElevators);

	
}
