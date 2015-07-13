# SE450

This is an elevator controlerl simualtion, which based on the csv file take a few parameters and processes elevator requests.

Goal:  Utilize object oriented design principals and patterns to simulate the logic and functionality of an elevator control system.
The following principals were used in object creation:
  •	Abstraction – to ensure objects are minimal in size, but complete in information
  •	Encapsulation – to ensure all data and methods on data are located within a single object
  •	Separation – used in conjunction with interfaces, which hides how an object works vs how to use an object
  •	Composition – Objects are made up of other objects
  •	Polymorphism – executing a behavior that several object have, but not necessarily implement the same way
  •	Interfaces – Define the role or behavior of objects who implement them, used to execute polymorphism
  •	Delegation – used to provide another level of indirection in code for future flexibility
  •	Open Closed – to ensure the program code was open to changes in the future and closed to modification based on changes
  •	Single Responsibility – to ensure objects only have one purpose for existence, supported by abstraction and encapsulation 

The following patterns were used in this project:
  •	Strategy and Factory – Where used to support the open closed principal, in case additional types of elevators or more efficient elevator logic algorithms needed to be added in the future with minimal code changes.
  •	Singleton – There is only ever a need for one elevator controller

Additional features used:
  •	Multi-threading including
   o	Thread creation/execution
   o	Synchronization with locks
   o	Wait/Notify for efficient memory management
  •	UML Class Diagram
