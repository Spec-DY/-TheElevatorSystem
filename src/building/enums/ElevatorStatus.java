package building.enums;

/**
 * This enum is used to represent the status of the elevators.
 */
public enum ElevatorStatus {
  running("Running"),
  stopping("Stopping"),
  stopped("Stopped");
  final String display;

  ElevatorStatus(String display) {
    this.display = display;
  }

  @Override
  public String toString() {
    return this.display;
  }
}
