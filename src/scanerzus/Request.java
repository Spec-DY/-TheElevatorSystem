package scanerzus;


/**
 * This class represents a request to the elevator.
 * All requests have a startFloor and endFloor
 */
public class Request implements RequestInterface {
  private final int startFloor;
  private final int endFloor;

  /**
   * The constructor for the request.
   * This is a very simple container that is used by scanezus to
   * represent a request to the elevator.  Because they implement the
   * scanning software depending on the elevator system, they do not do any
   * error checking on the construction of the requests.  These are simply tickets
   * that are passed to the elevator system.
   * Some buildings have negative floors, but for the purposes of this assignment
   * we can assume that all floors are positive.
   *
   * @param startFloor the start floor.
   * @param endFloor   the end floor.
   */
  public Request(int startFloor, int endFloor) {
    this.startFloor = startFloor;
    this.endFloor = endFloor;
  }

  /**
   * Gets the start floor of the request.
   *
   * @return the start floor of the request.
   */
  public int getStartFloor() {
    return startFloor;
  }

  /**
   * Gets the end floor of the request.
   *
   * @return the end floor of the request.
   */
  public int getEndFloor() {
    return endFloor;
  }

  /**
   * Returns a string representation of the request.
   * This is a string of the form:
   * "startFloor->endFloor"
   *
   * @return a string representation of the request.
   */
  public String toString() {
    return startFloor + "->" + endFloor;
  }
}


