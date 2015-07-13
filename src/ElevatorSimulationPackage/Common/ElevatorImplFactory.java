package ElevatorSimulationPackage.Common;

import ElevatorSimulationPackage.Elevator.Elevator;
import ElevatorSimulationPackage.Elevator.ElevatorImpl;
/*Kyle Cardiel
 * SE450 - Elevator Project
 * ElevatorImplFactory Class - creates elevators based on their type (only 1 type as of 2/9/15)
 * 
 */

public class ElevatorImplFactory {


	public static Elevator ElevatorBuilder(int elevatorIdIn,int maxPersonCapacityIn,int timePerFloorIn, int timePerDoorIn, int defaultFloorIn){
		
		return new ElevatorImpl(elevatorIdIn,maxPersonCapacityIn,timePerFloorIn,timePerDoorIn,defaultFloorIn);
	}
	
	
}
