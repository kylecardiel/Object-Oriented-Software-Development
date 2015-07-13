/*Kyle Cardiel
 * SE450 - Elevator Project
 * 
 */

package ElevatorSimulationPackage.Common;

import ElevatorSimulationPackage.ElevatorControllerOperations.ElevatorControllerOperationsPendingRequestsDefault;

public class ElevatorControllerOperationsPendingRequestsImplFactory {

	public static ElevatorControllerOperationsPendingRequestsDefault ElevatorControllerOperationsPendingRequestsBuilder()
	{
			return new ElevatorControllerOperationsPendingRequestsDefault();
	}
	
}
