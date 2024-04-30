package scanerzus;

/**
 * This interface is used define requests for the elevator.
 */
public interface RequestInterface {

  /**
   * This is the place where the elevator should go to pickup the request.
   *
   * @return the floor number where the request is.
   */
  int getStartFloor();

  /**
   * This is the place where the elevator should go to drop off the request.
   *
   * @return the floor number where the request is.
   */
  int getEndFloor();

}
