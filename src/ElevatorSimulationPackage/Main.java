package ElevatorSimulationPackage;


import java.io.File;
import java.util.Random;
import java.util.Scanner;

import ElevatorSimulationPackage.Common.ElevatorRequests;
import ElevatorSimulationPackage.Common.Timer;
import ElevatorSimulationPackage.Person.Person;
import ElevatorSimulationPackage.Structures.Building;
import ElevatorSimulationPackage.Structures.ElevatorController;

public class Main {
	

	public static void main(String[] args) throws Exception {
		
		String csvFile = "ElevatorInputs.csv";
		int[] inputs = new int[8];
		int count = 0;
		
		Scanner scanner = new Scanner(new File(csvFile));
	    scanner.useDelimiter(",");
	    while(scanner.hasNextInt()){
	    	inputs[count] = scanner.nextInt();
	    	count++;
	    }
	    scanner.close();
	
	    System.out.println("These are the inputs:");
	    System.out.println(String.format("Simulation duration time: %d minutes",inputs[0]));
	    System.out.println(String.format("Number of Floors in Building: %d",inputs[1]));
	    System.out.println(String.format("Number of Elevators in Building: %d",inputs[2]));
	    System.out.println(String.format("Max Persons Per Elevator: %d",inputs[3]));
	    System.out.println(String.format("Time (in milliseconds) per floor: %d ms",inputs[4]));
	    System.out.println(String.format("Door-operation-time (in milliseconds): %d ms",inputs[5]));
	    System.out.println(String.format("Default Elevator Floor: %d",inputs[6]));
	    System.out.println(String.format("Number of Persons per Minute: %d\n\n",inputs[7]));
	    
	    
	    
		Timer timer = Timer.getInstance();
		boolean running = true;
		//create building
		timer.start();
		Building tower = Building.getInstance(inputs[1],inputs[2]);
		timer.end();
		System.out.printf("%s\tCreating Building...\n",timer.toStringTotalTime());
		
		//create floors, elevators and elevator controller in building
		tower.createBuilding(inputs[3],inputs[4],inputs[5],inputs[6]);
		timer.end();
		System.out.printf("%s\t%s\n",timer.toStringTotalTime(),tower.toString());
		
		//create thread for each elevator
		Thread[] threads = new Thread[tower.getNumberOfElevators()+1];
		for (int i = 0; i < tower.getNumberOfElevators();i++){
			threads[i] = new Thread(tower.getElevatorController().getElevator(i+1));
			threads[i].setName("elevatorThread" + (i));
			threads[i].start();
		}

		
		
		threads[tower.getNumberOfElevators()] = new Thread((tower.getElevatorController()));
		threads[tower.getNumberOfElevators()].start();
		
		
		Random rand = new Random();
		long simulationTime = inputs[0]*60000;
		int personIdCounter = 1;
			while(running){
				int RAND1 = rand.nextInt((tower.getNumberOfFloors() - 1) + 1) + 1;
				int RAND2 = rand.nextInt((tower.getNumberOfFloors() - 1) + 1) + 1;
				while (RAND1 == RAND2){
					RAND2 = rand.nextInt((tower.getNumberOfFloors() - 1) + 1) + 1;
				}
				Person newPerson = new Person(personIdCounter,RAND1,RAND2);
				String direction = null;
				if(RAND1 > RAND2){
					direction = "DOWN";
				} else {
					direction = "UP";
				}
				timer.end();
				System.out.println(String.format("%s\tPerson %s created on Floor %d, wants to go %s to Floor %d",
						timer.toStringTotalTime(),
						newPerson.getPersonId(),
						newPerson.getStartFloorLocation(),
						direction,						
						newPerson.getEndFloorLocation()));
				int floor = newPerson.getStartFloorLocation();
				ElevatorRequests er = tower.getElevatorController().getFloor(floor).addPersonToFloor(newPerson);
				timer.end();
				System.out.println(String.format("%s\tPerson %s pressed %s on Floor %d",
						timer.toStringTotalTime(),
						newPerson.getPersonId(),
						er.getDirection(),
						newPerson.getStartFloorLocation()));
				tower.getElevatorController().receiveElevatorFloorRequest(er, newPerson);
				personIdCounter++;
				Thread.sleep(60000/inputs[7]);
				timer.end();
				if(timer.getTotalTime() > simulationTime ){
					running = false;
				}
			}
		

		tower.getElevatorController().getElevator(1).shutDown();
		tower.getElevatorController().getElevator(2).shutDown();
		tower.getElevatorController().getElevator(3).shutDown();
		tower.getElevatorController().shutDown();
		
		try{
			for(int i = 0; i < tower.getNumberOfElevators();i++){
				threads[i].join();}
		} catch (InterruptedException e){
			e.printStackTrace();
		}
	
		ElevatorController.getInstanceAfterCompletion().reports();
		
		
	}
		
		

}

