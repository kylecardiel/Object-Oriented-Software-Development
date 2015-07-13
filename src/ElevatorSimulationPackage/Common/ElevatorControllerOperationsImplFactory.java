/*Kyle Cardiel
 * SE450 - Elevator Project
 * 
 */

package ElevatorSimulationPackage.Common;

import ElevatorSimulationPackage.ElevatorControllerOperations.ElevatorControllerOperations;
import ElevatorSimulationPackage.ElevatorControllerOperations.ElevatorControllerOperationsImpl;

public class ElevatorControllerOperationsImplFactory {

	public static ElevatorControllerOperations ElevatorControllerOperationsBuilder()
	{
			return new ElevatorControllerOperationsImpl();
	}

}
