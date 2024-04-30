package elevator;

import building.enums.Direction;

/**
 * This class is used to represent the status of the elevators.
 */
public class ElevatorReport {
  private final int elevatorId;
  private final int currentFloor;
  private final boolean doorClosed;
  private final boolean[] floorRequests;
  private final Direction direction;

  private final int doorOpenTimer;
  private final int endWaitTimer;

  private final boolean outOfService;

  private final boolean isTakingRequests;


  /**
   * This constructor is used to create a new ElevatorReport object.
   *
   * @param elevatorId       The id of the elevator.
   * @param currentFloor     The current floor of the elevator.
   * @param doorClosed       The status of the door.
   * @param floorRequests    The requests for the floors.
   * @param direction        The direction of the elevator.
   * @param doorOpenTimer    The timer for the door.
   * @param endWaitTimer     The timer for the end of the run.
   * @param outOfService     The status of the elevator.
   * @param isTakingRequests Is the elevator taking requests.
   */
  public ElevatorReport(int elevatorId,
                        int currentFloor,
                        Direction direction,
                        boolean doorClosed,
                        boolean[] floorRequests,

                        int doorOpenTimer,
                        int endWaitTimer,
                        boolean outOfService,
                        boolean isTakingRequests) {
    this.elevatorId = elevatorId;
    this.currentFloor = currentFloor;
    this.doorClosed = doorClosed;
    this.floorRequests = floorRequests;
    this.direction = direction;
    this.doorOpenTimer = doorOpenTimer;
    this.endWaitTimer = endWaitTimer;
    this.outOfService = outOfService;
    this.isTakingRequests = isTakingRequests;
  }


  // getters, no setters

  /**
   * This method is used to get the id of the elevator.
   *
   * @return The id of the elevator.
   */
  public int getElevatorId() {
    return elevatorId;
  }

  /**
   * This method is used to get the current floor of the elevator.
   *
   * @return The current floor of the elevator.
   */
  public int getCurrentFloor() {
    return currentFloor;
  }

  /**
   * This method is used to get the status of the door.
   *
   * @return The status of the door.
   */
  public boolean isDoorClosed() {
    return doorClosed;
  }

  /**
   * This method is used to get the requests for the floors.
   *
   * @return The requests for the floors.
   */
  public boolean[] getFloorRequests() {
    return floorRequests;
  }

  /**
   * This method is used to get the direction of the elevator.
   *
   * @return The direction of the elevator.
   */
  public Direction getDirection() {
    return direction;
  }

  /**
   * This method is used to get the timer for the door.
   *
   * @return The timer for the door.
   */
  public int getDoorOpenTimer() {
    return doorOpenTimer;
  }

  /**
   * This method is used to get the timer for the end of the run.
   *
   * @return The timer for the end of the run.
   */
  public int getEndWaitTimer() {
    return endWaitTimer;
  }

  /**
   * This method is used to get the status of the elevator.
   *
   * @return The status of the elevator.
   */
  public boolean isOutOfService() {
    return outOfService;
  }


  /**
   * Is the elevator taking requests.
   *
   * @return is the elevator taking requests.
   */
  public boolean isTakingRequests() {
    return isTakingRequests;
  }

  /**
   * toString method for the ElevatorReport.
   *
   * @return the string representation of the ElevatorReport.
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();

    // if the elevator is out of service and on the ground floor
    if (this.outOfService && this.currentFloor == 0) {
      sb.append(String.format("Out of Service[Floor %d]", this.currentFloor));
      return sb.toString();
    }

    if (this.endWaitTimer > 0) {
      sb.append(String.format("Waiting[Floor %d, Time %d]", this.currentFloor, this.endWaitTimer));
      return sb.toString();
    }

    sb.append(String.format("[%d|%s|",
        this.currentFloor,
        this.direction));

    if (this.doorClosed) {
      sb.append("C  ]<");
    } else {
      sb.append(String.format("O %d]<", this.doorOpenTimer));
    }

    for (int i = 0; i < this.floorRequests.length; i++) {
      if (this.floorRequests[i]) {
        sb.append(String.format(" %2d", i));
      } else {
        sb.append(" --");
      }
    }
    sb.append(">");

    return sb.toString();

  }

  /**
   * The equals method for the ElevatorReport.
   *
   * @param o the object to compare to.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ElevatorReport)) {
      return false;
    }
    ElevatorReport that = (ElevatorReport) o;
    if (this.elevatorId != that.elevatorId) {
      return false;
    }
    if (this.currentFloor != that.currentFloor) {
      return false;
    }
    if (this.doorClosed != that.doorClosed) {
      return false;
    }
    if (this.doorOpenTimer != that.doorOpenTimer) {
      return false;
    }
    if (this.endWaitTimer != that.endWaitTimer) {
      return false;
    }
    if (this.outOfService != that.outOfService) {
      return false;
    }
    if (this.direction != that.direction) {
      return false;
    }
    if (this.isTakingRequests != that.isTakingRequests) {
      return false;
    }
    for (int i = 0; i < this.floorRequests.length; i++) {
      if (this.floorRequests[i] != that.floorRequests[i]) {
        return false;
      }
    }
    return true;
  }

  /**
   * The hashcode method for the ElevatorReport.
   */
  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + this.elevatorId;
    result = 31 * result + this.currentFloor;
    result = 31 * result + (this.doorClosed ? 1 : 0);
    result = 31 * result + this.doorOpenTimer;
    result = 31 * result + this.endWaitTimer;
    result = 31 * result + (this.outOfService ? 1 : 0);
    result = 31 * result + (this.isTakingRequests ? 1 : 0);
    result = 31 * result + this.direction.hashCode();
    for (boolean floorRequest : this.floorRequests) {
      result = 31 * result + (floorRequest ? 1 : 0);
    }
    return result;
  }
}
