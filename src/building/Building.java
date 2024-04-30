package building;

import building.enums.ElevatorSystemStatus;
import elevator.Elevator;
import elevator.ElevatorInterface;
import elevator.ElevatorReport;
import java.util.ArrayList;
import java.util.List;
import scanerzus.Request;


/**
 * Represents a building with a defined number of floors and elevators.
 * It manages elevator requests and directs the operation of elevators
 * according to the building's requirements.
 * The class interacts with elevators and external request sources to
 * simulate elevator movements and manage elevator states.
 */
public class Building implements BuildingInterface {
  private final int numberOfFloors;
  private final int numberOfElevators;
  private final int elevatorCapacity;
  private final List<Elevator> elevators = new ArrayList<>();
  private ElevatorSystemStatus elevatorSystemStatus;
  private final List<Request> upRequests = new ArrayList<>();
  private final List<Request> downRequests = new ArrayList<>();

  /**
   * Constructs a Building object with specified parameters.
   *
   * @param numberOfFloors    The total number of floors in the building. Must be at least 2.
   * @param numberOfElevators The total number of elevators in the building. Must be at least 1.
   * @param elevatorCapacity  The maximum capacity of each elevator. Must be at least 3.
   * @throws IllegalArgumentException if any of the input parameters
   *                                  are outside their defined valid ranges.
   */
  public Building(int numberOfFloors, int numberOfElevators, int elevatorCapacity) {
    // Validate the parameters
    if (numberOfFloors < 2) {
      throw new IllegalArgumentException("Number of floors must be at least 2.");
    }
    if (numberOfElevators < 1) {
      throw new IllegalArgumentException("Number of elevators must be at least 1.");
    }
    if (elevatorCapacity < 3) {
      throw new IllegalArgumentException("Elevator capacity must be at least 3.");
    }

    this.numberOfFloors = numberOfFloors;
    this.numberOfElevators = numberOfElevators;
    this.elevatorCapacity = elevatorCapacity;

    // Initialize Elevator objects
    for (int i = 0; i < numberOfElevators; i++) {
      elevators.add(new Elevator(numberOfFloors, elevatorCapacity));
    }

    this.elevatorSystemStatus = ElevatorSystemStatus.outOfService;
  }

  /**
   * Returns the number of elevators in the building.
   *
   * @return The total number of elevators.
   */
  @Override
  public int getNumberOfElevators() {
    return this.numberOfElevators;
  }

  /**
   * Returns the capacity of the elevators in the building.
   *
   * @return The maximum number of individuals that can be accommodated by an elevator at one time.
   */
  @Override
  public int getElevatorCapacity() {
    return this.elevatorCapacity;
  }

  /**
   * Returns the number of floors in the building.
   *
   * @return The total number of floors.
   */
  @Override
  public int getNumberOfFloors() {
    return this.numberOfFloors;
  }


  /**
   * Provides a comprehensive report of the current status of all elevators within the building,
   * including their operational status and any active requests.
   *
   * @return A BuildingReport object encapsulating the status of the building's elevator system.
   */
  @Override
  public BuildingReport getElevatorSystemStatus() {
    // Create an array of ElevatorReport for all elevators in the building
    ElevatorReport[] elevatorReports = new ElevatorReport[elevators.size()];
    for (int i = 0; i < elevators.size(); i++) {
      // Assuming the getElevatorStatus() method returns an ElevatorReport object
      elevatorReports[i] = elevators.get(i).getElevatorStatus();
    }

    // for safety
    List<Request> upRequestsCopy = new ArrayList<>(upRequests);
    List<Request> downRequestsCopy = new ArrayList<>(downRequests);

    // Create and return a new BuildingReport object with the current state of the building
    return new BuildingReport(
        numberOfFloors,
        numberOfElevators,
        elevatorCapacity,
        elevatorReports,
        upRequestsCopy,
        downRequestsCopy,
        elevatorSystemStatus
    );
  }


  /**
   * Attempts to start the elevator system.
   * If the system is already running or is in the process of stopping,
   * appropriate actions are taken or exceptions are thrown.
   *
   * @return true if the system successfully transitions to running; false otherwise.
   * @throws IllegalStateException if the elevator system is currently stopping.
   */
  @Override
  public boolean startElevatorSystem() {
    // running
    if (elevatorSystemStatus == ElevatorSystemStatus.running) {
      return true;
    } else if (elevatorSystemStatus == ElevatorSystemStatus.stopping) {
      throw new IllegalStateException("Elevator system is stopping ");
    } else if (elevatorSystemStatus == ElevatorSystemStatus.outOfService) {
      for (Elevator elevator : this.elevators) {
        elevator.start();
      }
      this.elevatorSystemStatus = ElevatorSystemStatus.running;
      return true;
    }
    return false;
  }


  /**
   * Initiates the process of stopping the elevator system.
   * If the system is already out of service or stopping,
   * no further action is taken.
   */
  @Override
  public void stopElevatorSystem() {
    // Check if the system is already out of service or in the process of stopping
    if (elevatorSystemStatus == ElevatorSystemStatus.outOfService
        || elevatorSystemStatus == ElevatorSystemStatus.stopping) {
      return; // No action needed if already stopping or out of service
    }

    // Iterate through each elevator and take it out of service
    for (Elevator elevator : this.elevators) {
      elevator.takeOutOfService();
    }
    // Set system status to stopping
    elevatorSystemStatus = ElevatorSystemStatus.stopping;
    removeAllRequests();

  }

  /**
   * Adds a request for elevator service. Requests can be to move up or
   * down and are added to a queue accordingly.
   *
   * @param request The request to be added.
   * @return true if the request is successfully added; false otherwise.
   * @throws IllegalStateException if the elevator system is not in a state to accept new requests.
   */
  @Override
  public boolean addRequest(Request request) {
    // Check if the building system is running and accept requests if so.
    if (this.elevatorSystemStatus == ElevatorSystemStatus.running) {
      if (request.getStartFloor() < request.getEndFloor()) {
        this.upRequests.add(request);
      } else {
        this.downRequests.add(request);
      }
      return true;
      // If the system is either out of service or stopping, reject requests with an exception.
    } else if (this.elevatorSystemStatus == ElevatorSystemStatus.outOfService
        || this.elevatorSystemStatus == ElevatorSystemStatus.stopping) {
      throw new IllegalStateException(
          "The building elevator system is not currently accepting requests "
              + this.elevatorSystemStatus);
    }
    return false;
  }


  /**
   * Advances the state of each elevator by one step.
   * This includes moving elevators, opening/closing doors, etc.,
   * depending on the elevator's current state and requests.
   */
  @Override
  public void stepElevators() {
    // Only distribute requests if the system is running
    if (elevatorSystemStatus == ElevatorSystemStatus.running) {
      distributeRequests();
    }
    if (elevatorSystemStatus == ElevatorSystemStatus.outOfService) {
      return;
    }

    // Step each elevator even without request
    for (Elevator elevator : this.elevators) {
      elevator.step();
    }

    // Check if the system is in the stopping phase
    if (elevatorSystemStatus == ElevatorSystemStatus.stopping) {
      // If all elevators are on the ground floor, transition to outOfService
      if (checkGroundFloor()) {
        elevatorSystemStatus = ElevatorSystemStatus.outOfService;
      }
    }
  }

  /**
   * Determines whether all elevators are on the ground floor. Iterates through elevators,
   * returning false if any is not on the ground floor.
   *
   * @return true if all elevators are on the ground floor, false otherwise.
   */
  private boolean checkGroundFloor() {
    // Iterate over all elevators to check their current floor
    for (Elevator elevator : this.elevators) {
      // If any elevator is not on the ground floor, return false
      if (elevator.getCurrentFloor() != 0) {
        return false;
      } else {
        elevator.step();
      }
    }
    // If all elevators are on the ground floor, return true
    return true;
  }


  /**
   * Takes a specific elevator out of service based on its ID.
   *
   * @param elevatorId The ID of the elevator to be taken out of service.
   *                   This must be within the range of 0 to the
   *                   total number of elevators minus one.
   */
  @Override
  public void takeElevatorOutOfService(int elevatorId) {
    if (elevatorId >= 0 && elevatorId < elevators.size()) {
      elevators.get(elevatorId).takeOutOfService();
    }
  }

  /**
   * Takes all elevators in the building out of service.
   * This is typically used in emergency situations or when the building is closing.
   */
  @Override
  public void takeAllElevatorsOutOfService() {
    for (Elevator elevator : elevators) {
      elevator.takeOutOfService();
    }
    elevatorSystemStatus = ElevatorSystemStatus.outOfService;
  }

  /**
   * Allocates elevator requests based on current positions and capacity.
   * Skips elevators not accepting requests,
   * and processes requests targeting up from the ground floor or down
   * from the top floor accordingly.
   */
  private void distributeRequests() {
    for (ElevatorInterface elevator : this.elevators) {
      if (!elevator.isTakingRequests()) {
        continue;
      }

      List<Request> requestsToProcess = new ArrayList<>();
      // Determine the direction based on the elevator's position for request allocation
      if (elevator.getCurrentFloor() == 0) {
        requestsToProcess = takeRequestsForElevator(upRequests, this.elevatorCapacity);
      } else if (elevator.getCurrentFloor() == this.numberOfFloors - 1) {
        requestsToProcess = takeRequestsForElevator(downRequests, this.elevatorCapacity);
      }

      // Process allocated requests
      if (!requestsToProcess.isEmpty()) {
        elevator.processRequests(requestsToProcess);
      }
    }
  }

  /**
   * Selects and removes requests from the given list up to the specified capacity,
   * simulating allocation to an elevator.
   * This method ensures the elevator doesn't receive more requests than it can handle.
   *
   * @param requests The list of elevator requests.
   * @param capacity The capacity of the elevator.
   * @return A list of requests allocated to the elevator.
   */
  private List<Request> takeRequestsForElevator(List<Request> requests, int capacity) {
    // Taking requests based on elevator capacity and direction
    List<Request> allocatedRequests = new ArrayList<>();
    int count = 0;
    while (!requests.isEmpty() && count < capacity) {
      allocatedRequests.add(requests.remove(0));
      count++;
    }
    return allocatedRequests;
  }

  /**
   * Clears all pending up and down elevator requests.
   * Used when the elevator system is stopped or taken out of service.
   */
  private void removeAllRequests() {
    upRequests.clear();
    downRequests.clear();
  }
}

