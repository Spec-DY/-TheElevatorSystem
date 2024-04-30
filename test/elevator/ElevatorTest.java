package elevator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import building.enums.Direction;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import scanerzus.Request;

/**
 * A JUnit test class for the ElevatorStatus class.
 */
public class ElevatorTest {
  private static Elevator elevatorTestStatic;
  private static Elevator elevatorTestStatic2;
  List<Request> upTenRequests;
  List<Request> upTenRequestsNotTop;
  List<Request> downTenRequests;
  List<Request> downTenRequestsNotBottom;

  List<Request> upThreeRequests;
  List<Request> downThreeRequests;
  Elevator elevatorTenFloors10Capacity;

  Elevator elevatorThreeFloors3Capacity;


  /**
   * Set up the stuff before all of the tests so we can do a static test.
   */
  @BeforeClass
  public static void setUpClass() {
    System.out.println("Testing Elevator class");
    elevatorTestStatic = new Elevator(10, 5);
    elevatorTestStatic2 = new Elevator(10, 5);
  }

  /**
   * Set up the test.
   */
  @Before
  public void setUp() {
    upTenRequests = new ArrayList<>();
    upTenRequests.add(new Request(0, 1));
    upTenRequests.add(new Request(0, 2));
    upTenRequests.add(new Request(0, 3));
    upTenRequests.add(new Request(5, 6));
    upTenRequests.add(new Request(5, 9));

    upTenRequestsNotTop = new ArrayList<>();
    upTenRequestsNotTop.add(new Request(0, 1));
    upTenRequestsNotTop.add(new Request(0, 2));
    upTenRequestsNotTop.add(new Request(0, 3));
    upTenRequestsNotTop.add(new Request(5, 6));


    downTenRequests = new ArrayList<>();
    downTenRequests.add(new Request(9, 8));
    downTenRequests.add(new Request(9, 7));
    downTenRequests.add(new Request(9, 6));
    downTenRequests.add(new Request(5, 4));
    downTenRequests.add(new Request(5, 1));

    downTenRequestsNotBottom = new ArrayList<>();
    downTenRequestsNotBottom.add(new Request(9, 8));
    downTenRequestsNotBottom.add(new Request(9, 7));
    downTenRequestsNotBottom.add(new Request(9, 6));
    downTenRequestsNotBottom.add(new Request(5, 4));


    upThreeRequests = new ArrayList<>();
    upThreeRequests.add(new Request(0, 1));
    upThreeRequests.add(new Request(0, 2));

    downThreeRequests = new ArrayList<>();
    downThreeRequests.add(new Request(2, 1));
    downThreeRequests.add(new Request(2, 0));
    downThreeRequests.add(new Request(1, 0));


    elevatorTenFloors10Capacity = new Elevator(10, 10);
    elevatorThreeFloors3Capacity = new Elevator(3, 3);
  }

  /**
   * Test the constructor exceptions.
   * Number of floors must be greater than or equal to 2.
   */
  @Test(expected = IllegalArgumentException.class)
  public void elevatorConstructorThrowsExceptionForLessThan3Floors() {
    System.out.println("Testing: elevatorConstructorThrowsExceptionForLessThan3Floors");
    new Elevator(2, 5);
  }

  /**
   * Test the constructor exceptions.
   * Number of floors must be less than 30.
   */
  @Test(expected = IllegalArgumentException.class)
  public void elevatorConstructorThrowsExceptionForThirtyFloors() {
    System.out.println("Testing: elevatorConstructorThrowsExceptionForThirtyFloors");
    new Elevator(30, 2);
  }

  /**
   * Test the constructor exceptions.
   * Occupancy must be greater than or equal to 3
   */
  @Test(expected = IllegalArgumentException.class)
  public void elevatorConstructorThrowsExceptionForLessThan3Occupancy() {
    System.out.println("Testing: elevatorConstructorThrowsExceptionForLessThan3Occupancy");
    new Elevator(10, 2);
  }

  /**
   * Test the constructor exceptions.
   * Occupancy must be less than or equal to 20
   */
  @Test(expected = IllegalArgumentException.class)
  public void elevatorConstructorThrowsExceptionForMoreThan20Occupancy() {
    System.out.println("Testing: elevatorConstructorThrowsExceptionForMoreThan20Occupancy");
    new Elevator(10, 21);
  }


  /**
   * Test the constructor.
   */
  @Test
  public void elevatorStartsAtGroundFloor() {
    System.out.println("Testing: elevatorStartsAtGroundFloor");
    elevator.Elevator elevator = new Elevator(10, 5);
    assertEquals(0, elevator.getCurrentFloor());
  }

  /**
   * Test the constructor for max occupancy.
   */
  @Test
  public void elevatorMaxOccupancy() {
    System.out.println("Testing: elevatorMaxOccupancy");
    Elevator elevator = new Elevator(10, 5);
    assertEquals(5, elevator.getMaxOccupancy());
  }

  /**
   * Test the constructor for max floor.
   */
  @Test
  public void elevatorMaxFloor() {
    System.out.println("Testing: elevatorMaxFloor");
    Elevator elevator = new Elevator(10, 5);
    assertEquals(10, elevator.getMaxFloor());
  }

  /**
   * Test the constructor for elevator id.
   */
  @Test
  public void elevatorId() {
    System.out.println("Testing: elevatorId");

    assertEquals(0, elevatorTestStatic.getElevatorId());
    assertEquals(1, elevatorTestStatic2.getElevatorId());


  }


  /**
   * Test that the elevator starts out of service.
   */
  @Test
  public void elevatorStartsStopped() {
    System.out.println("Testing: elevatorStartsOutOfService");
    Elevator elevator = new Elevator(10, 5);
    assertEquals(elevator.getDirection(), Direction.STOPPED);
    assertFalse(elevator.isTakingRequests());
  }

  /**
   * Test that the elevator reports stopped when at the top floor,
   * then reports STOPPED when it returns to the ground floor.
   * then test that it reports STOPPED when out of service.
   */
  @Test
  public void elevatorReportsStopped() {
    System.out.println("Testing: elevatorStoppedAtTopFloor");
    Elevator elevator = new Elevator(3, 3);
    elevator.start();
    // wait for the a job and then give up
    for (int i = 0; i < 5; i++) {
      elevator.step();
    }
    // send the elevator to the top floor
    for (int i = 0; i < 3; i++) {
      elevator.step();
    }
    assertEquals(elevator.getDirection(), Direction.STOPPED);
    assertEquals(elevator.getCurrentFloor(), 2);
    // wait for the a job and then give up
    for (int i = 0; i < 5; i++) {
      elevator.step();
    }
    // send the elevator to the ground floor
    for (int i = 0; i < 3; i++) {
      elevator.step();
    }
    assertEquals(elevator.getDirection(), Direction.STOPPED);
    assertEquals(elevator.getCurrentFloor(), 0);

    // now put the elevator out of service.
    elevator.takeOutOfService();
    // Do one step to let the elevator process the out of service request.
    elevator.step();
    assertFalse(elevator.isTakingRequests());
    // check to see if it returns STOPPED when out of service.
    assertEquals(elevator.getDirection(), Direction.STOPPED);
  }

  /**
   * Test that the elevator can be taken out of service when it is not on the ground floor
   * and has no requests.
   */
  @Test
  public void elevatorOutOfService() {
    System.out.println("Testing: elevatorOutOfService");
    Elevator elevator = new Elevator(10, 5);
    elevator.start();
    // wait for the a job and then give up
    for (int i = 0; i < 5; i++) {
      elevator.step();
    }
    // send the elevator up three floors
    for (int i = 0; i < 3; i++) {
      elevator.step();
    }

    elevator.takeOutOfService();

    // it should not be taking requests now.
    assertFalse(elevator.isTakingRequests());
    // it should now move to the ground floor
    for (int i = 0; i < 3; i++) {
      elevator.step();
    }
    assertEquals(elevator.getCurrentFloor(), 0);
    // it should be out of service
    assertFalse(elevator.isTakingRequests());

    // one more step to take it out of service
    elevator.step();
    assertEquals(Direction.STOPPED, elevator.getElevatorStatus().getDirection());
  }

  /**
   * Test the start method.
   */
  @Test
  public void elevatorStart() {
    System.out.println("Testing: elevatorStart");
    Elevator elevator = new Elevator(10, 5);
    elevator.start();
    assertTrue(elevator.isTakingRequests());
  }

  /**
   * Test that the elevator can process a request.
   */
  @Test
  public void elevatorProcessRequest() {
    System.out.println("Testing: elevatorProcessRequest");
    Elevator elevator = new Elevator(10, 5);
    elevator.start();
    List<Request> singleRequest = new ArrayList<>();
    singleRequest.add(new Request(0, 1));
    elevator.processRequests(singleRequest);
    boolean[] floorRequests = elevator.getFloorRequests();
    assertTrue(floorRequests[0]);
    assertTrue(floorRequests[1]);
    for (int i = 2; i < floorRequests.length; i++) {
      assertFalse(floorRequests[i]);
    }
  }

  @Test
  public void elevatorProcessRequest2() {
    System.out.println("Testing: elevatorProcessRequest2");
    Elevator elevator = new Elevator(10, 5);
    elevator.start();
    List<Request> singleRequest = new ArrayList<>();
    singleRequest.add(new Request(0, 1));
    singleRequest.add(new Request(0, 2));
    elevator.processRequests(singleRequest);
    boolean[] floorRequests = elevator.getFloorRequests();
    assertTrue(floorRequests[0]);
    assertTrue(floorRequests[1]);
    assertTrue(floorRequests[2]);
    for (int i = 3; i < floorRequests.length; i++) {
      assertFalse(floorRequests[i]);
    }
  }

  @Test
  public void elevatorProcessesRequests() {
    System.out.println("Testing: elevatorProcessesRequests");
    elevatorTenFloors10Capacity.start();
    elevatorTenFloors10Capacity.processRequests(upTenRequests);
    boolean[] floorRequests = elevatorTenFloors10Capacity.getFloorRequests();
    assertTrue(floorRequests[0]);
    assertTrue(floorRequests[1]);
    assertTrue(floorRequests[2]);
    assertTrue(floorRequests[3]);
    assertFalse(floorRequests[4]);
    assertTrue(floorRequests[5]);
    assertTrue(floorRequests[6]);
    assertFalse(floorRequests[7]);
    assertFalse(floorRequests[8]);
    assertTrue(floorRequests[9]);

    // Elevator should be on floor 0 with the door open for 3 steps.
    elevatorTenFloors10Capacity.step();
    // check that the door is open
    assertFalse(elevatorTenFloors10Capacity.isDoorClosed());
    elevatorTenFloors10Capacity.step();
    // check that the door is open
    assertFalse(elevatorTenFloors10Capacity.isDoorClosed());
    elevatorTenFloors10Capacity.step();
    // check that the door is open
    assertFalse(elevatorTenFloors10Capacity.isDoorClosed());
    elevatorTenFloors10Capacity.step();
    // check that the door is closed
    assertTrue(elevatorTenFloors10Capacity.isDoorClosed());

    // The elevator has finished on the first floor and is now on the floor 1.
    // The door is closed.
    elevatorTenFloors10Capacity.step();
    // check that the floor is 1
    assertEquals(1, elevatorTenFloors10Capacity.getCurrentFloor());
    // check that the door is closed
    assertTrue(elevatorTenFloors10Capacity.isDoorClosed());

    // There are requests for this floor so we will open and wait 3 steps
    elevatorTenFloors10Capacity.step();
    // check that the door is open
    assertFalse(elevatorTenFloors10Capacity.isDoorClosed());
    elevatorTenFloors10Capacity.step();
    // check that the door is open
    assertFalse(elevatorTenFloors10Capacity.isDoorClosed());
    elevatorTenFloors10Capacity.step();
    // check that the door is open
    assertFalse(elevatorTenFloors10Capacity.isDoorClosed());
    elevatorTenFloors10Capacity.step();
    // check that the door is closed
    assertTrue(elevatorTenFloors10Capacity.isDoorClosed());

    // The elevator has finished on the second floor and is now on the floor 2.
    elevatorTenFloors10Capacity.step();
    // check that the floor is 2
    assertEquals(2, elevatorTenFloors10Capacity.getCurrentFloor());
    // check that the door is closed
    assertTrue(elevatorTenFloors10Capacity.isDoorClosed());
    elevatorTenFloors10Capacity.step();
    // check that the door is open
    assertFalse(elevatorTenFloors10Capacity.isDoorClosed());
    elevatorTenFloors10Capacity.step();
    // check that the door is open
    assertFalse(elevatorTenFloors10Capacity.isDoorClosed());
    elevatorTenFloors10Capacity.step();
    // check that the door is open
    assertFalse(elevatorTenFloors10Capacity.isDoorClosed());
    elevatorTenFloors10Capacity.step();
    // check that the door is closed
    assertTrue(elevatorTenFloors10Capacity.isDoorClosed());

    // The elevator has finished on the third floor and is now on the floor 3.
    elevatorTenFloors10Capacity.step();
    // check that the floor is 3
    assertEquals(3, elevatorTenFloors10Capacity.getCurrentFloor());
    // check that the door is closed
    assertTrue(elevatorTenFloors10Capacity.isDoorClosed());
    elevatorTenFloors10Capacity.step();
    // check that the door is open
    assertFalse(elevatorTenFloors10Capacity.isDoorClosed());
    elevatorTenFloors10Capacity.step();
    // check that the door is open
    assertFalse(elevatorTenFloors10Capacity.isDoorClosed());
    elevatorTenFloors10Capacity.step();
    // check that the door is open
    assertFalse(elevatorTenFloors10Capacity.isDoorClosed());
    elevatorTenFloors10Capacity.step();
    // check that the door is closed
    assertTrue(elevatorTenFloors10Capacity.isDoorClosed());

    // The elevator has finished on the fourth floor and is now on the floor 4.
    elevatorTenFloors10Capacity.step();
    // check that the floor is 4
    assertEquals(4, elevatorTenFloors10Capacity.getCurrentFloor());
    // check that the door is closed
    assertTrue(elevatorTenFloors10Capacity.isDoorClosed());


    // The elevator has finished on the fifth floor and is now on the floor 5.
    elevatorTenFloors10Capacity.step();
    // check that the floor is 5
    assertEquals(5, elevatorTenFloors10Capacity.getCurrentFloor());
    // check that the door is closed
    assertTrue(elevatorTenFloors10Capacity.isDoorClosed());
    elevatorTenFloors10Capacity.step();
    // check that the door is open
    assertFalse(elevatorTenFloors10Capacity.isDoorClosed());
    elevatorTenFloors10Capacity.step();
    // check that the door is open
    assertFalse(elevatorTenFloors10Capacity.isDoorClosed());
    elevatorTenFloors10Capacity.step();
    // check that the door is open
    assertFalse(elevatorTenFloors10Capacity.isDoorClosed());
    elevatorTenFloors10Capacity.step();
    // check that the door is closed
    assertTrue(elevatorTenFloors10Capacity.isDoorClosed());

    // The elevator has finished on the sixth floor and is now on the floor 6.
    elevatorTenFloors10Capacity.step();
    // check that the floor is 6
    assertEquals(6, elevatorTenFloors10Capacity.getCurrentFloor());
    // check that the door is closed
    assertTrue(elevatorTenFloors10Capacity.isDoorClosed());


    // The elevator has finished on the seventh floor and is now on the floor 7.
    elevatorTenFloors10Capacity.step();
    // check that the floor is 6
    assertEquals(6, elevatorTenFloors10Capacity.getCurrentFloor());
    // check that the door is open
    assertFalse(elevatorTenFloors10Capacity.isDoorClosed());
    elevatorTenFloors10Capacity.step();
    // check that the floor is open
    assertFalse(elevatorTenFloors10Capacity.isDoorClosed());
    elevatorTenFloors10Capacity.step();
    // check that the floor is open
    assertFalse(elevatorTenFloors10Capacity.isDoorClosed());
    elevatorTenFloors10Capacity.step();
    // check that the floor is closed
    assertTrue(elevatorTenFloors10Capacity.isDoorClosed());
    elevatorTenFloors10Capacity.step();


    // check that the floor is 7 and the door is closed
    assertEquals(7, elevatorTenFloors10Capacity.getCurrentFloor());
    assertTrue(elevatorTenFloors10Capacity.isDoorClosed());
    elevatorTenFloors10Capacity.step();

    // check that the floor is 8
    assertEquals(8, elevatorTenFloors10Capacity.getCurrentFloor());
    // check that the door is closed
    assertTrue(elevatorTenFloors10Capacity.isDoorClosed());
    elevatorTenFloors10Capacity.step();

    // check that the floor is 9
    assertEquals(9, elevatorTenFloors10Capacity.getCurrentFloor());
    // check that the door is closed
    assertTrue(elevatorTenFloors10Capacity.isDoorClosed());
    elevatorTenFloors10Capacity.step();
    // check that the door is open
    assertFalse(elevatorTenFloors10Capacity.isDoorClosed());
    elevatorTenFloors10Capacity.step();
    // check that the door is open
    assertFalse(elevatorTenFloors10Capacity.isDoorClosed());
    elevatorTenFloors10Capacity.step();
    // check that the door is open
    assertFalse(elevatorTenFloors10Capacity.isDoorClosed());
    elevatorTenFloors10Capacity.step();
    // check that the door is closed
    assertTrue(elevatorTenFloors10Capacity.isDoorClosed());
    elevatorTenFloors10Capacity.step();

    // check that the waits at the top for 5 steps
    assertEquals(Direction.STOPPED, elevatorTenFloors10Capacity.getDirection());
    assertEquals("Waiting[Floor 9, Time 5]", elevatorTenFloors10Capacity.toString());
    elevatorTenFloors10Capacity.step();
    assertEquals("Waiting[Floor 9, Time 4]", elevatorTenFloors10Capacity.toString());
    elevatorTenFloors10Capacity.step();
    assertEquals("Waiting[Floor 9, Time 3]", elevatorTenFloors10Capacity.toString());
    elevatorTenFloors10Capacity.step();
    assertEquals("Waiting[Floor 9, Time 2]", elevatorTenFloors10Capacity.toString());
    elevatorTenFloors10Capacity.step();
    assertEquals("Waiting[Floor 9, Time 1]", elevatorTenFloors10Capacity.toString());
    elevatorTenFloors10Capacity.step();

    // check that the floor is 9 and the direction is down and the door is closed
    assertEquals(9, elevatorTenFloors10Capacity.getCurrentFloor());
    assertEquals(Direction.DOWN, elevatorTenFloors10Capacity.getDirection());
    assertTrue(elevatorTenFloors10Capacity.isDoorClosed());
    elevatorTenFloors10Capacity.step();

    // check that the floor is 8
    assertEquals(8, elevatorTenFloors10Capacity.getCurrentFloor());
    // check that the door is closed
    assertTrue(elevatorTenFloors10Capacity.isDoorClosed());

    elevatorTenFloors10Capacity.step();
    // check that the floor is 7
    assertEquals(7, elevatorTenFloors10Capacity.getCurrentFloor());
    // check that the door is closed
    assertTrue(elevatorTenFloors10Capacity.isDoorClosed());

    elevatorTenFloors10Capacity.step();
    // check that the floor is 6
    assertEquals(6, elevatorTenFloors10Capacity.getCurrentFloor());
    // check that the door is closed
    assertTrue(elevatorTenFloors10Capacity.isDoorClosed());

    elevatorTenFloors10Capacity.step();
    // check that the floor is 5
    assertEquals(5, elevatorTenFloors10Capacity.getCurrentFloor());
    // check that the door is closed
    assertTrue(elevatorTenFloors10Capacity.isDoorClosed());

    elevatorTenFloors10Capacity.step();
    // check that the floor is 4
    assertEquals(4, elevatorTenFloors10Capacity.getCurrentFloor());
    // check that the door is closed
    assertTrue(elevatorTenFloors10Capacity.isDoorClosed());

    elevatorTenFloors10Capacity.step();
    // check that the floor is 3
    assertEquals(3, elevatorTenFloors10Capacity.getCurrentFloor());
    // check that the door is closed
    assertTrue(elevatorTenFloors10Capacity.isDoorClosed());

    elevatorTenFloors10Capacity.step();
    // check that the floor is 2
    assertEquals(2, elevatorTenFloors10Capacity.getCurrentFloor());
    // check that the door is closed
    assertTrue(elevatorTenFloors10Capacity.isDoorClosed());

    elevatorTenFloors10Capacity.step();
    // check that the floor is 1
    assertEquals(1, elevatorTenFloors10Capacity.getCurrentFloor());
    // check that the door is closed
    assertTrue(elevatorTenFloors10Capacity.isDoorClosed());

    elevatorTenFloors10Capacity.step();
    // check that the floor is 0
    assertEquals(0, elevatorTenFloors10Capacity.getCurrentFloor());
    // check that the door is closed
    assertTrue(elevatorTenFloors10Capacity.isDoorClosed());

    elevatorTenFloors10Capacity.step();

    // check that we are waiting for 5 steps
    assertEquals("Waiting[Floor 0, Time 5]", elevatorTenFloors10Capacity.toString());
    elevatorTenFloors10Capacity.step();
    assertEquals("Waiting[Floor 0, Time 4]", elevatorTenFloors10Capacity.toString());
    elevatorTenFloors10Capacity.step();
    assertEquals("Waiting[Floor 0, Time 3]", elevatorTenFloors10Capacity.toString());
    elevatorTenFloors10Capacity.step();
    assertEquals("Waiting[Floor 0, Time 2]", elevatorTenFloors10Capacity.toString());
    elevatorTenFloors10Capacity.step();
    assertEquals("Waiting[Floor 0, Time 1]", elevatorTenFloors10Capacity.toString());
    elevatorTenFloors10Capacity.step();


    // the elevator should now be going up, its door closed and on floor 0
    assertEquals(Direction.UP, elevatorTenFloors10Capacity.getDirection());
    assertTrue(elevatorTenFloors10Capacity.isDoorClosed());
    assertEquals(0, elevatorTenFloors10Capacity.getCurrentFloor());

    // The elevator has finished on the first floor and is now on the floor 1.
    elevatorTenFloors10Capacity.step();
    // check that the floor is 1
    assertEquals(1, elevatorTenFloors10Capacity.getCurrentFloor());

  }

  @Test
  public void elevatorOnGroundFloorSentToTopFloor() {
    System.out.println("Testing: elevatorOnGroundFloorTakesDownRequests");
    elevatorThreeFloors3Capacity.start();
    elevatorThreeFloors3Capacity.step();
    elevatorThreeFloors3Capacity.step();
    elevatorThreeFloors3Capacity.step();
    elevatorThreeFloors3Capacity.step();
    elevatorThreeFloors3Capacity.step();
    assertEquals(0, elevatorThreeFloors3Capacity.getCurrentFloor());
    elevatorThreeFloors3Capacity.step();

    // elevator should be on floor 1, with door closed.
    assertEquals(1, elevatorThreeFloors3Capacity.getCurrentFloor());
    assertTrue(elevatorThreeFloors3Capacity.isDoorClosed());
    elevatorThreeFloors3Capacity.step();

    // elevator should be on floor 2, with door closed.
    assertEquals(2, elevatorThreeFloors3Capacity.getCurrentFloor());
    assertTrue(elevatorThreeFloors3Capacity.isDoorClosed());
    elevatorThreeFloors3Capacity.step();


    // elevator should be waiting for 5 steps
    assertEquals("Waiting[Floor 2, Time 5]", elevatorThreeFloors3Capacity.toString());
    elevatorThreeFloors3Capacity.processRequests(downThreeRequests);

    // elevator should be on floor 2 with the door closed.
    assertEquals(2, elevatorThreeFloors3Capacity.getCurrentFloor());

    // Now we follow the elevator down the building.
    elevatorThreeFloors3Capacity.step();

    // The elevator should be on floor 2 with the door opened.
    assertEquals(2, elevatorThreeFloors3Capacity.getCurrentFloor());
    assertFalse(elevatorThreeFloors3Capacity.isDoorClosed());
    elevatorThreeFloors3Capacity.step();

    // The elevator should be on floor 2 with the door opened.
    assertEquals(2, elevatorThreeFloors3Capacity.getCurrentFloor());
    assertFalse(elevatorThreeFloors3Capacity.isDoorClosed());
    elevatorThreeFloors3Capacity.step();

    // The elevator should be on floor 2 with the door opened.
    assertEquals(2, elevatorThreeFloors3Capacity.getCurrentFloor());
    assertFalse(elevatorThreeFloors3Capacity.isDoorClosed());
    elevatorThreeFloors3Capacity.step();

    // The elevator should be on floor 2 with the door closed.
    assertEquals(2, elevatorThreeFloors3Capacity.getCurrentFloor());
    assertTrue(elevatorThreeFloors3Capacity.isDoorClosed());
    elevatorThreeFloors3Capacity.step();

    // The elevator should be on floor 1 with the door closed.
    assertEquals(1, elevatorThreeFloors3Capacity.getCurrentFloor());
    assertTrue(elevatorThreeFloors3Capacity.isDoorClosed());
    elevatorThreeFloors3Capacity.step();

    // The elevator should be on floor 1 with the door opened.
    assertEquals(1, elevatorThreeFloors3Capacity.getCurrentFloor());
    assertFalse(elevatorThreeFloors3Capacity.isDoorClosed());
    elevatorThreeFloors3Capacity.step();

    // The elevator should be on floor 1 with the door opened.
    assertEquals(1, elevatorThreeFloors3Capacity.getCurrentFloor());
    assertFalse(elevatorThreeFloors3Capacity.isDoorClosed());
    elevatorThreeFloors3Capacity.step();

    // The elevator should be on floor 1 with the door opened.
    assertEquals(1, elevatorThreeFloors3Capacity.getCurrentFloor());
    assertFalse(elevatorThreeFloors3Capacity.isDoorClosed());
    elevatorThreeFloors3Capacity.step();

    // The elevator should be on floor 1 with the door closed.
    assertEquals(1, elevatorThreeFloors3Capacity.getCurrentFloor());
    assertTrue(elevatorThreeFloors3Capacity.isDoorClosed());
    elevatorThreeFloors3Capacity.step();

    // The elevator should be on floor 0 with the door closed.
    assertEquals(0, elevatorThreeFloors3Capacity.getCurrentFloor());
    assertTrue(elevatorThreeFloors3Capacity.isDoorClosed());
    elevatorThreeFloors3Capacity.step();

    // The elevator should be on floor 0 with the door opened.
    assertEquals(0, elevatorThreeFloors3Capacity.getCurrentFloor());
    assertFalse(elevatorThreeFloors3Capacity.isDoorClosed());
    elevatorThreeFloors3Capacity.step();

    // The elevator should be on floor 0 with the door opened.
    assertEquals(0, elevatorThreeFloors3Capacity.getCurrentFloor());
    assertFalse(elevatorThreeFloors3Capacity.isDoorClosed());
    elevatorThreeFloors3Capacity.step();

    // The elevator should be on floor 0 with the door opened.
    assertEquals(0, elevatorThreeFloors3Capacity.getCurrentFloor());
    assertFalse(elevatorThreeFloors3Capacity.isDoorClosed());
    elevatorThreeFloors3Capacity.step();

    // The elevator should be on floor 0 with the door closed.
    assertEquals(0, elevatorThreeFloors3Capacity.getCurrentFloor());
    assertTrue(elevatorThreeFloors3Capacity.isDoorClosed());
    elevatorThreeFloors3Capacity.step();

    // the elevator should be on floor 0 and waiting for 5 steps
    assertEquals("Waiting[Floor 0, Time 5]", elevatorThreeFloors3Capacity.toString());
    elevatorThreeFloors3Capacity.step();
    assertEquals("Waiting[Floor 0, Time 4]", elevatorThreeFloors3Capacity.toString());
    elevatorThreeFloors3Capacity.step();
    assertEquals("Waiting[Floor 0, Time 3]", elevatorThreeFloors3Capacity.toString());
    elevatorThreeFloors3Capacity.step();
    assertEquals("Waiting[Floor 0, Time 2]", elevatorThreeFloors3Capacity.toString());
    elevatorThreeFloors3Capacity.step();
    assertEquals("Waiting[Floor 0, Time 1]", elevatorThreeFloors3Capacity.toString());
    elevatorThreeFloors3Capacity.step();

    // the elevator should now be going up, its door closed and on floor 0
    assertEquals(Direction.UP, elevatorThreeFloors3Capacity.getDirection());
    assertTrue(elevatorThreeFloors3Capacity.isDoorClosed());
    assertEquals(0, elevatorThreeFloors3Capacity.getCurrentFloor());

    // The elevator has finished on the  floor 0 and is now on the floor 1.
    elevatorThreeFloors3Capacity.step();
    // check that the floor is 1
    assertEquals(1, elevatorThreeFloors3Capacity.getCurrentFloor());

    // The elevator has finished on the floor 1 and is now on the floor 2.
    elevatorThreeFloors3Capacity.step();
    // check that the floor is 2
    assertEquals(2, elevatorThreeFloors3Capacity.getCurrentFloor());
    elevatorThreeFloors3Capacity.step();

    // the elevator should be waiting for 5 steps
    assertEquals("Waiting[Floor 2, Time 5]", elevatorThreeFloors3Capacity.toString());

  }

  /**
   * test that an elevator midrun reports it is not taking requests
   * and throws an exception when processRequests is called.
   */
  @Test(expected = IllegalStateException.class)
  public void elevatorThrowsExceptionWhenProcessCalledMidRun() {
    System.out.println("Testing: elevatorThrowsExceptionWhenProcessCalledMidRun");
    elevatorTenFloors10Capacity.start();

    for (int i = 0; i < 10; i++) {
      elevatorTenFloors10Capacity.step();
    }
    // elevator is on floor 5
    assertEquals(5, elevatorTenFloors10Capacity.getCurrentFloor());

    // elevator is not taking requests
    assertFalse(elevatorTenFloors10Capacity.isTakingRequests());

    // elevator should throw an exception
    elevatorTenFloors10Capacity.processRequests(upTenRequests);
  }

  /**
   * test toString method.
   * The elevator should be waiting for 5 steps
   */
  @Test
  public void elevatorToString() {
    System.out.println("Testing: elevatorToString");
    elevatorThreeFloors3Capacity.start();
    elevatorThreeFloors3Capacity.processRequests(upThreeRequests);
    assertEquals("[0|^|C  ]<  0  1  2>", elevatorThreeFloors3Capacity.toString());

    elevatorThreeFloors3Capacity.step();
    assertEquals("[0|^|O 3]< --  1  2>", elevatorThreeFloors3Capacity.toString());
    elevatorThreeFloors3Capacity.step();
    assertEquals("[0|^|O 2]< --  1  2>", elevatorThreeFloors3Capacity.toString());
    elevatorThreeFloors3Capacity.step();
    assertEquals("[0|^|O 1]< --  1  2>", elevatorThreeFloors3Capacity.toString());
    elevatorThreeFloors3Capacity.step();
    assertEquals("[0|^|C  ]< --  1  2>", elevatorThreeFloors3Capacity.toString());

    elevatorThreeFloors3Capacity.step();
    assertEquals("[1|^|C  ]< --  1  2>", elevatorThreeFloors3Capacity.toString());
    elevatorThreeFloors3Capacity.step();
    assertEquals("[1|^|O 3]< -- --  2>", elevatorThreeFloors3Capacity.toString());
    elevatorThreeFloors3Capacity.step();
    assertEquals("[1|^|O 2]< -- --  2>", elevatorThreeFloors3Capacity.toString());
    elevatorThreeFloors3Capacity.step();
    assertEquals("[1|^|O 1]< -- --  2>", elevatorThreeFloors3Capacity.toString());
    elevatorThreeFloors3Capacity.step();
    assertEquals("[1|^|C  ]< -- --  2>", elevatorThreeFloors3Capacity.toString());

    elevatorThreeFloors3Capacity.step();
    assertEquals("[2|^|C  ]< -- --  2>", elevatorThreeFloors3Capacity.toString());
    elevatorThreeFloors3Capacity.step();
    assertEquals("[2|^|O 3]< -- -- -->", elevatorThreeFloors3Capacity.toString());
    elevatorThreeFloors3Capacity.step();
    assertEquals("[2|^|O 2]< -- -- -->", elevatorThreeFloors3Capacity.toString());
    elevatorThreeFloors3Capacity.step();
    assertEquals("[2|^|O 1]< -- -- -->", elevatorThreeFloors3Capacity.toString());
    elevatorThreeFloors3Capacity.step();
    assertEquals("[2|^|C  ]< -- -- -->", elevatorThreeFloors3Capacity.toString());

    elevatorThreeFloors3Capacity.step();
    assertEquals("Waiting[Floor 2, Time 5]", elevatorThreeFloors3Capacity.toString());
    elevatorThreeFloors3Capacity.step();
    assertEquals("Waiting[Floor 2, Time 4]", elevatorThreeFloors3Capacity.toString());
    elevatorThreeFloors3Capacity.step();
    assertEquals("Waiting[Floor 2, Time 3]", elevatorThreeFloors3Capacity.toString());
    elevatorThreeFloors3Capacity.step();
    assertEquals("Waiting[Floor 2, Time 2]", elevatorThreeFloors3Capacity.toString());
    elevatorThreeFloors3Capacity.step();
    assertEquals("Waiting[Floor 2, Time 1]", elevatorThreeFloors3Capacity.toString());
    elevatorThreeFloors3Capacity.step();
    assertEquals("[2|v|C  ]< -- -- -->", elevatorThreeFloors3Capacity.toString());

    elevatorThreeFloors3Capacity.step();
    assertEquals("[1|v|C  ]< -- -- -->", elevatorThreeFloors3Capacity.toString());
    elevatorThreeFloors3Capacity.step();
    assertEquals("[0|v|C  ]< -- -- -->", elevatorThreeFloors3Capacity.toString());
    elevatorThreeFloors3Capacity.step();
    assertEquals("Waiting[Floor 0, Time 5]", elevatorThreeFloors3Capacity.toString());
    elevatorThreeFloors3Capacity.step();
    assertEquals("Waiting[Floor 0, Time 4]", elevatorThreeFloors3Capacity.toString());
    elevatorThreeFloors3Capacity.step();
    assertEquals("Waiting[Floor 0, Time 3]", elevatorThreeFloors3Capacity.toString());
    elevatorThreeFloors3Capacity.step();
    assertEquals("Waiting[Floor 0, Time 2]", elevatorThreeFloors3Capacity.toString());
    elevatorThreeFloors3Capacity.step();
    assertEquals("Waiting[Floor 0, Time 1]", elevatorThreeFloors3Capacity.toString());
    elevatorThreeFloors3Capacity.step();
    assertEquals("[0|^|C  ]< -- -- -->", elevatorThreeFloors3Capacity.toString());

  }

  /**
   * Test the take out of service method.
   * The elevator should immediately flush all of its stop requests and head to
   * the ground floor with the door closed.
   * when it gets there it should open the door and remain out of service with the door open
   * The final state of the elevator should be [0|-|O 3]< -- -- -- -- -- -- -- -- -- -->
   */
  @Test
  public void takeElevatorOutOfServiceWhenDoorIsClosed() {
    System.out.println("Testing: takeElevatorOutOfServiceWhenDoorIsClosed");
    elevatorTenFloors10Capacity.start();
    elevatorTenFloors10Capacity.processRequests(upTenRequests);
    for (int i = 0; i < 20; i++) {
      elevatorTenFloors10Capacity.step();
    }
    // elevator is on floor 4
    assertEquals(4, elevatorTenFloors10Capacity.getCurrentFloor());
    assertEquals("[4|^|C  ]< -- -- -- -- --  5  6 -- --  9>",
        elevatorTenFloors10Capacity.toString());

    elevatorTenFloors10Capacity.takeOutOfService();

    assertEquals("[4|v|C  ]< -- -- -- -- -- -- -- -- -- -->",
        elevatorTenFloors10Capacity.toString());

    elevatorTenFloors10Capacity.step();
    assertEquals("[3|v|C  ]< -- -- -- -- -- -- -- -- -- -->",
        elevatorTenFloors10Capacity.toString());

    elevatorTenFloors10Capacity.step();
    assertEquals("[2|v|C  ]< -- -- -- -- -- -- -- -- -- -->",
        elevatorTenFloors10Capacity.toString());

    elevatorTenFloors10Capacity.step();
    assertEquals("[1|v|C  ]< -- -- -- -- -- -- -- -- -- -->",
        elevatorTenFloors10Capacity.toString());


    elevatorTenFloors10Capacity.step();
    assertEquals("Out of Service[Floor 0]",
        elevatorTenFloors10Capacity.toString());
    for (int i = 0; i < 1000; i++) {
      elevatorTenFloors10Capacity.step();
    }
    assertEquals("Out of Service[Floor 0]",
        elevatorTenFloors10Capacity.toString());

    // Now we put the elevator back in service
    elevatorTenFloors10Capacity.start();
    elevatorTenFloors10Capacity.processRequests(upTenRequests);

    elevatorTenFloors10Capacity.step();
    // elevator is on floor 0, direction is up and door is open for 3 steps
    assertEquals(0, elevatorTenFloors10Capacity.getCurrentFloor());
    assertEquals(Direction.UP, elevatorTenFloors10Capacity.getDirection());
    assertFalse(elevatorTenFloors10Capacity.isDoorClosed());
    assertEquals("[0|^|O 3]< --  1  2  3 --  5  6 -- --  9>",
        elevatorTenFloors10Capacity.toString());
    elevatorTenFloors10Capacity.step();

    // the elevator is on floor 0 and the door is open for 2 steps
    assertEquals("[0|^|O 2]< --  1  2  3 --  5  6 -- --  9>",
        elevatorTenFloors10Capacity.toString());

  }

  /**
   * Test the take out of service method.
   * The elevator should immediately flush all of its stop requests and head to
   * the ground floor with the door closed.
   * when it gets there it should open the door and remain out of service with the door open
   * The final state of the elevator should be [0|-|O 3]< -- -- -- -- -- -- -- -- -- -->
   */
  @Test
  public void takeElevatorOutOfServiceWhenDoorIsOpened() {
    System.out.println("Testing: takeElevatorOutOfServiceWhenDoorIsOpened");
    elevatorTenFloors10Capacity.start();
    elevatorTenFloors10Capacity.processRequests(upTenRequests);
    for (int i = 0; i < 22; i++) {
      elevatorTenFloors10Capacity.step();
    }
    // elevator is on floor 5
    assertEquals(5, elevatorTenFloors10Capacity.getCurrentFloor());
    assertEquals("[5|^|O 3]< -- -- -- -- -- --  6 -- --  9>",
        elevatorTenFloors10Capacity.toString());

    elevatorTenFloors10Capacity.takeOutOfService();

    // The door must close normally but the requests are gone
    elevatorTenFloors10Capacity.step();
    assertEquals("[5|v|O 2]< -- -- -- -- -- -- -- -- -- -->",
        elevatorTenFloors10Capacity.toString());
    elevatorTenFloors10Capacity.step();
    assertEquals("[5|v|O 1]< -- -- -- -- -- -- -- -- -- -->",
        elevatorTenFloors10Capacity.toString());
    elevatorTenFloors10Capacity.step();
    assertEquals("[5|v|C  ]< -- -- -- -- -- -- -- -- -- -->",
        elevatorTenFloors10Capacity.toString());

    elevatorTenFloors10Capacity.step();
    assertEquals("[4|v|C  ]< -- -- -- -- -- -- -- -- -- -->",
        elevatorTenFloors10Capacity.toString());

    elevatorTenFloors10Capacity.step();
    assertEquals("[3|v|C  ]< -- -- -- -- -- -- -- -- -- -->",
        elevatorTenFloors10Capacity.toString());

    elevatorTenFloors10Capacity.step();
    assertEquals("[2|v|C  ]< -- -- -- -- -- -- -- -- -- -->",
        elevatorTenFloors10Capacity.toString());

    elevatorTenFloors10Capacity.step();
    assertEquals("[1|v|C  ]< -- -- -- -- -- -- -- -- -- -->",
        elevatorTenFloors10Capacity.toString());

    elevatorTenFloors10Capacity.step();
    assertEquals("Out of Service[Floor 0]",
        elevatorTenFloors10Capacity.toString());

    elevatorTenFloors10Capacity.step();
    assertEquals("Out of Service[Floor 0]",
        elevatorTenFloors10Capacity.toString());
    for (int i = 0; i < 1000; i++) {
      elevatorTenFloors10Capacity.step();
    }
    assertEquals("Out of Service[Floor 0]",
        elevatorTenFloors10Capacity.toString());

    // Now we put the elevator back in service
    elevatorTenFloors10Capacity.start();
    elevatorTenFloors10Capacity.processRequests(upTenRequests);

    elevatorTenFloors10Capacity.step();
    // elevator is on floor 0, direction is up and door is open for 3 steps
    assertEquals(0, elevatorTenFloors10Capacity.getCurrentFloor());
    assertEquals(Direction.UP, elevatorTenFloors10Capacity.getDirection());
    assertFalse(elevatorTenFloors10Capacity.isDoorClosed());
    assertEquals("[0|^|O 3]< --  1  2  3 --  5  6 -- --  9>",
        elevatorTenFloors10Capacity.toString());
    elevatorTenFloors10Capacity.step();

    // the elevator is on floor 0 and the door is open for 2 steps
    assertEquals("[0|^|O 2]< --  1  2  3 --  5  6 -- --  9>",
        elevatorTenFloors10Capacity.toString());

  }

  /**
   * Test the getElevatorStatus method.
   */
  @Test
  public void getElevatorStatus() {
    System.out.println("Testing: getElevatorStatus");
    elevatorTenFloors10Capacity.start();

    elevatorTenFloors10Capacity.processRequests(upTenRequests);
    for (int i = 0; i < 6; i++) {
      elevatorTenFloors10Capacity.step();
    }

    assertEquals(1, elevatorTenFloors10Capacity.getCurrentFloor());
    assertEquals("[1|^|O 3]< -- --  2  3 --  5  6 -- --  9>",
        elevatorTenFloors10Capacity.toString());


    ElevatorReport report = elevatorTenFloors10Capacity.getElevatorStatus();
    assertEquals("[1|^|O 3]< -- --  2  3 --  5  6 -- --  9>", report.toString());

    // The data in the report should match the state of the elevator
    assertEquals(1, report.getCurrentFloor());
    assertEquals(Direction.UP, report.getDirection());
    assertEquals(elevatorTenFloors10Capacity.getElevatorId(), report.getElevatorId());
    assertEquals(10, report.getFloorRequests().length);
    assertFalse(report.getFloorRequests()[0]);
    assertFalse(report.getFloorRequests()[1]);
    assertTrue(report.getFloorRequests()[2]);
    assertTrue(report.getFloorRequests()[3]);
    assertFalse(report.getFloorRequests()[4]);
    assertTrue(report.getFloorRequests()[5]);
    assertTrue(report.getFloorRequests()[6]);
    assertFalse(report.getFloorRequests()[7]);
    assertFalse(report.getFloorRequests()[8]);
    assertTrue(report.getFloorRequests()[9]);
    assertFalse(report.isDoorClosed());
    assertEquals(elevatorTenFloors10Capacity.isTakingRequests(), report.isTakingRequests());
  }

  /**
   * Make the elevator go up and down 100000 times.
   * This is a stress test to see if the elevator can handle a large number of requests.
   */
  @Test
  public void stressTest() {
    System.out.println("Testing: stressTest");
    elevatorTenFloors10Capacity.start();

    for (int i = 0; i < 100000; i++) {

      // check that the elevator is on the ground floor
      assertEquals(0, elevatorTenFloors10Capacity.getCurrentFloor());
      // check that the elevator is taking requests
      assertTrue(elevatorTenFloors10Capacity.isTakingRequests());

      // wait 5 steps for the elevator to start
      for (int j = 0; j < 5; j++) {
        elevatorTenFloors10Capacity.step();
      }

      // step up ten floors (there are no requests)
      for (int j = 0; j < 10; j++) {
        elevatorTenFloors10Capacity.step();
      }
      // check to see if the elevator is taking requests
      assertTrue(elevatorTenFloors10Capacity.isTakingRequests());
      // check to see if the elevator is at the top
      assertEquals(9, elevatorTenFloors10Capacity.getCurrentFloor());

      // wait for 5 steps and see if the elevator is going down.
      for (int j = 0; j < 5; j++) {
        elevatorTenFloors10Capacity.step();
      }
      assertEquals(Direction.DOWN, elevatorTenFloors10Capacity.getDirection());
      // check to see if the elevator is taking requests
      assertFalse(elevatorTenFloors10Capacity.isTakingRequests());
      // now go to the ground floor
      for (int j = 0; j < 10; j++) {
        elevatorTenFloors10Capacity.step();
      }
      // check to see if the elevator is taking requests
      assertTrue(elevatorTenFloors10Capacity.isTakingRequests());
      // check to see if the elevator is at the bottom
      assertEquals(0, elevatorTenFloors10Capacity.getCurrentFloor());

    }

  }

}