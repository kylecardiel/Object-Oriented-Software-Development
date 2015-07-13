package ElevatorSimulationPackage.Structures;
/*Kyle Cardiel
 * SE450 - Elevator Project
 * Building Class - creates a singleton object (Building) which creates and owns a collection of floors and elevators
 * 		in the building. 
 * 
 */



import ElevatorSimulationPackage.Common.InvalidDataParameterException;


public final class Building {

	private int numberOfFloors;
	private int numberOfElevators;
	private static Building instance;
	private final int TALLEST_BUILDING_IN_THE_WORLD_HAS_163_FLOORS = 163;
	private ElevatorController elevatorController;
	
	
	//Singleton Object Constructor
	private Building (int numberOfFloorsIn, int numberOfElevatorsIn) throws InvalidDataParameterException{
		setNumberOfFloors(numberOfFloorsIn);
		setNumberOfElevators(numberOfElevatorsIn);
	}
	
	public static synchronized Building getInstance(int numberOfFloorsIn,int numberOfElevatorsIn) throws Exception {
		if (instance == null){
			instance = new Building(numberOfFloorsIn, numberOfElevatorsIn);
			}
		return instance;
	}
	
	//Access
	public int getNumberOfFloors(){
		return numberOfFloors;}
	
	public int getNumberOfElevators(){
		return numberOfElevators;}
	
	public ElevatorController getElevatorController() {
		return elevatorController;
	}

	
	//Modifiers - with exception checking
	public void setNumberOfFloors(int numberOfFloorsIn) throws InvalidDataParameterException {
			if(numberOfFloorsIn < 1 || numberOfFloorsIn > TALLEST_BUILDING_IN_THE_WORLD_HAS_163_FLOORS ){
				throw new InvalidDataParameterException("A building with this number of floors is not possible:  " + numberOfFloorsIn);
				}
			numberOfFloors = numberOfFloorsIn;
			}
	
	public void setNumberOfElevators(int numberOfElevatorsIn)throws InvalidDataParameterException {
		if(numberOfElevatorsIn < 1 || numberOfElevatorsIn > TALLEST_BUILDING_IN_THE_WORLD_HAS_163_FLOORS){
			throw new InvalidDataParameterException("A building with this number of elevators is not possible:  " + numberOfElevatorsIn);
			}
		numberOfElevators = numberOfElevatorsIn;
		}
	
	public void setElevatorController(ElevatorController elevatorControllerIn) {
		elevatorController = elevatorControllerIn;
	}
	
	//Operations
	public void createBuilding(int maxPersonCapacityIn,int timePerFloorIn, int timePerDoorIn,int defaultFloorIn) throws InvalidDataParameterException{
		createElevatorController(maxPersonCapacityIn, timePerFloorIn,  timePerDoorIn, defaultFloorIn);
	}

	private void createElevatorController(int maxPersonCapacityIn,int timePerFloorIn, int timePerDoorIn, int defaultFloorIn) throws InvalidDataParameterException{
		setElevatorController(ElevatorController.getInstance(getNumberOfElevators(), getNumberOfFloors(),maxPersonCapacityIn,timePerFloorIn,timePerDoorIn,defaultFloorIn));
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("Building created - %d floors,", getNumberOfFloors()));
		sb.append(String.format(" %d elevators and", getNumberOfElevators()));
		sb.append(String.format(" 1 elevator controller"));
		return sb.toString();
	}




	
	
	
}
