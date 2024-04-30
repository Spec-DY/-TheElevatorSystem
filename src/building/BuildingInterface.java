package building;

import scanerzus.Request;

/**
 * Specifies operations for managing an elevator system within a building,
 * including starting and stopping the system,
 * handling elevator requests, and querying elevator and building properties.
 * Please check Building Class for specific documentations for each method.
 */
public interface BuildingInterface {
  int getNumberOfElevators();

  int getElevatorCapacity();

  int getNumberOfFloors();

  BuildingReport getElevatorSystemStatus();

  boolean startElevatorSystem();

  void stopElevatorSystem();

  boolean addRequest(Request request);

  void stepElevators();

  void takeElevatorOutOfService(int elevatorId);

  void takeAllElevatorsOutOfService();
}
