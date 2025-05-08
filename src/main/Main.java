package main;

import operations.ContainerOperations;
import operations.PortOperations;
import operations.ShipOperations;
import interfaces.Port;
import interfaces.Ship;

import java.util.*;
// Importing the static exit method from the System class to terminate the program if needed
import static java.lang.System.exit;
// Importing all static members from the util.Utils class for convenience
import static util.Utils.*;

public class Main {

	public static void main(String[] args) {
		// Run the system methods
		try {
			PortOperations portOperations = new PortOperations();
			ShipOperations shipOperations = new ShipOperations();
			ContainerOperations containerOperations = new ContainerOperations();
			Scanner scan = new Scanner(System.in);
			// Run loop until user chooses to exit system
			while (true) {
				try {
					int option = mainMenu();
					switch (option) {
					case 1: {
						// Option 1: Create Container
						Optional<String> type = Optional.empty();
						System.out.println("::::::::::::::::::CREATE CONTAINER::::::::::::::::::");

						System.out.println("Enter ID: ");
						int ID = scan.nextInt();

						System.out.println("Enter weight: ");
						int weight = scan.nextInt();

						if (weight >= 5000) {
							System.out.println("Enter type of heavy container\n[L] for Liquid  [R] for Refrigerated [N] is not specified: ");
						// To check if the input is correct
							type = Optional.ofNullable(scan.next());
						}

						// Check if container already exists
						if (containerOperations.findContainerById(ID) != null) {
							System.out.println("ID already exists. Please try again.");
							continue;
						}

						var container = createAContainer(ID, weight, type);
						containerOperations.addContainer(container);

						if (container != null) {
							System.out.println("Container created successfully");
							System.out.println(container.toString());
						} else {
							System.out.println("Failed to create container");
						break;
					}
					}

					case 2: {
						// Option 2: Create Ship
						System.out.println("::::::::::::::::::CREATE SHIP::::::::::::::::::");

						System.out.println("Enter Ship ID: ");
						int ID = scan.nextInt();

						System.out.println("Enter Port ID: ");
						int portID = scan.nextInt();

						System.out.println("Enter total weight capacity: ");
						int totalWeightCapacity = scan.nextInt();

						System.out.println("Enter max number of all containers: ");
						int maxNumberOfAllContainers = scan.nextInt();

						System.out.println("Enter max number of heavy containers: ");
						int maxNumberOfHeavyContainers = scan.nextInt();

						System.out.println("Enter max number of refrigerated containers: ");
						int maxNumberOfRefrigeratedContainers = scan.nextInt();

						System.out.println("Enter max number of liquid containers: ");
						int maxNumberOfLiquidContainers = scan.nextInt();

						System.out.println("Enter fuel consumption per km: ");
						double fuelConsumptionPerKM = scan.nextDouble();

						System.out.println("Enter ship tank capacity: ");
						double tankCapacity = scan.nextDouble();

						System.out.println("Enter amount of fuel in tank: ");
						double fuelInTank = scan.nextInt();

						if (tankCapacity < fuelInTank) {
							System.out.println("Cannot enter fuel amount greater than tank capacity");
							fuelInTank = 0;
						}

						if (shipOperations.findShipById(ID) != null) {
							System.out.println("Ship ID already exists. Please try again.");
							continue;
						}

						Port port = portOperations.findPortById(portID);

						if (port == null) {
							System.out.println("Port does not exist. Please try again.");
							continue;
						}

						Ship ship = createAShip(ID, port, totalWeightCapacity, maxNumberOfAllContainers,
								maxNumberOfHeavyContainers, maxNumberOfRefrigeratedContainers,
								maxNumberOfLiquidContainers, fuelConsumptionPerKM, tankCapacity, fuelInTank);

						if (ship != null) {
							System.out.println("Ship created successfully");
							System.out.println(ship.toString());
							shipOperations.addShip(ship);
						}
						break;
					}

					case 3: {
						// Option 3: Create Port
						System.out.println("::::::::::::::::::CREATE PORT::::::::::::::::::");

						System.out.println("Enter longitude: ");
						double longitude = scan.nextDouble();

						System.out.println("Enter latitude: ");
						double latitude = scan.nextDouble();

						if (portOperations.findPortByLatitudeAndLongitude(latitude, longitude) != null) {
							System.out.println("Port already exists ");
							continue;
						}

						Random random = new Random();// simulating a random id
						int ID = random.nextInt();

						ID = generateID(portOperations, ID);
						Port port = createAPort(ID, longitude, latitude);

						System.out.println("How many container do you want to add on this port: ");
						int containers = scan.nextInt();

						for (int i = 1; i <= containers; i++) {
							System.out.println("Enter container " + i + " id: ");
							int containerId = scan.nextInt();
							var container = containerOperations.findContainerById(containerId);

							if (container == null) {
								System.out.println("Container does not exist");
							} else {
								port.getOnPortContainerList().add(container);
							}
						}

						portOperations.addPort(port);
						System.out.println("Port created successfully");
						System.out.println(port.toString());
						break;
					}

					case 4: {
						// Option 4: Load container to ship
						System.out.println("::::::::::::::::::LOAD CONTAINER::::::::::::::::::");

						// ship is created on a certain port
						// you can only load container on that port
						System.out.println("Enter ship id: ");
						int shipId = scan.nextInt();

						Ship ship = shipOperations.findShipById(shipId);
						var port = portOperations.findPortById(ship.getCurrentPort().getPortID());

						if (ship == null) {
							System.out.println("Ship does not exist");
							continue;
						}

						System.out.println("Ship is on port: " + ship.getCurrentPort().getPortID());

						if (!ship.getCurrentPort().getOnPortContainerList().isEmpty()) {
							System.out.println("These are the containers on this port: "
									+ ship.getCurrentPort().getOnPortContainerList());
							System.out.println("You can load container by choosing container id\n");

							System.out.println("How many container do you want to load: ");
							System.out.println("NB: Space left on ship can only load: "
									+ (ship.getMaxNumberOfAllContainers() - ship.getCurrentContainers().size())
									+ " containers");

							int numberOfContainers = scan.nextInt();

							for (int i = 1; i <= numberOfContainers; i++) {
								System.out.println("Enter container no" + i + " id: ");
								int containerId = scan.nextInt();
								var container = containerOperations.findContainerById(containerId);

								// Check if specified containerexists
								if (container == null) {
									System.out.println("Failed to load container, container does not exist");
								} else {
									loadAContainer(container, ship, port);
								}

							}
						} else {
							System.out.println("No containers are available for loading in ship");
						}
						break;
					}

					case 5: {
						// Option 5: Unload container from ship
						System.out.println("::::::::::::::::::UNLOAD CONTAINER::::::::::::::::::");

						// ship is created on a certain port
						// you can only load container on that port
						System.out.println("Enter ship id: ");
						int shipId = scan.nextInt();

						Ship ship = shipOperations.findShipById(shipId);
						var port = portOperations.findPortById(ship.getCurrentPort().getPortID());

						if (ship == null) {
							System.out.println("Ship does not exist");
							continue;
						}

						System.out.println("Ship is on port: " + ship.getCurrentPort().getPortID());

						if (!ship.getOnShipContainerList().isEmpty()) {
							System.out
									.println("These are the containers on this ship: " + ship.getOnShipContainerList());
							System.out.println("You can unload container by choosing container id\n");

							System.out.println("Specify container id to be unloaded: ");
							int containerId = scan.nextInt();
							var container = containerOperations.findContainerById(containerId);

							// Check if specified containerexists
							if (container == null) {
								System.out.println("Failed to unload container, container does not exist");
							} else {
								unloadAContainer(container, ship, port);
							}
						} else {
							System.out.println("No containers are available for offloading in ship");
						}
						break;
					}

					case 6: {
						// Option 6: Sail ship to another port
						System.out.println("::::::::::::::::::SAIL SHIP::::::::::::::::::");

						System.out.println("Enter ship id: ");
						int shipId = scan.nextInt();

						var ship = shipOperations.findShipById(shipId);
						if (ship == null) {
							System.out.println("Ship does not exist");
							continue;
						}

						List<Port> canSailToList = new ArrayList<>();

						for (Port port : portOperations.findAllPorts()) {
							if (port.getPortID() != ship.getCurrentPort().getPortID()) {
								canSailToList.add(port);
							}
						}

						System.out.println("Here is ports available for ship to sail: " + canSailToList);
						System.out.println("Enter destination port id: ");
						System.out.println("NB: make sure to enter port Id: ");
						int portId = scan.nextInt();

						var port = portOperations.findPortById(portId);
						if (port == null) {
							System.out.println("Destination port does not exist");
							continue;
						}

						sailShipToPort(ship, port);
						break;
					}

					case 7: {
						// Option 7: Refuel ship
						System.out.println("::::::::::::::::::REFUEL SHIP::::::::::::::::::");

						System.out.println("Enter ship id: ");
						int shipId = scan.nextInt();

						var ship = shipOperations.findShipById(shipId);
						System.out.println("Current ship fuel:" + ship.getFuelInTank() + "\nShip tank capacity: "
								+ ship.getTankCapacity());

						System.out.println("\nEnter fuel amount: ");
						double fuelAmount = scan.nextDouble();

						// Check if ship exists
						if (ship == null) {
							System.out.println("Ship does not exist");
							continue;
						}

						refuel(ship, fuelAmount);
						break;
					}

					case 8: {
						// Option 8: Exit system
						System.out.println("Thank you for using ships and port manager.");
						System.out.println("Exiting System......");
						exit(0);
						break;
					}

					default:
						System.out.println("Invalid input");
					}
				} catch (Exception e) {
					System.out.println("Could not run system " + e.getMessage());
					System.out.println("Returning to main menu...");
					continue;
				}
			}
		} catch (Exception e) {
			System.out.println("An error occurred: " + e.getMessage());
			System.out.println("Exiting system...");
			exit(1);
		}
	}

	private static int generateID(PortOperations portOperations, int ID) {
		if (portOperations.findPortById(ID) == null) {
			return ID;
		}
		return generateID(portOperations, ID + 1); // Increment ID here
	}
}
