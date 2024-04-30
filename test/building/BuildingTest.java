package building;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import building.enums.ElevatorSystemStatus;
import elevator.ElevatorReport;
import java.util.concurrent.locks.ReentrantLock;
import org.junit.Before;
import org.junit.Test;
import scanerzus.Request;

/**
 * Validates the {@link Building} class, focusing on construction,
 * elevator system operations, and request handling.
 * Tests ensure proper initialization, system start/stop functionality,
 * request processing, and elevator service management.
 */
public class BuildingTest {
  private Building building;

  /**
   * Sets up the test environment before each test method.
   * Initializes a new building instance with predefined parameters.
   */
  @Before
  public void setUp() {
    building = new Building(10, 2, 5);
  }

  /**
   * Tests the initialization of the Building class.
   * Verifies if the building is correctly initialized with the
   * provided number of floors, elevators, and elevator capacity.
   */
  @Test
  public void testBuildingInitialization() {
    assertEquals(2, building.getNumberOfElevators());
    assertEquals(5, building.getElevatorCapacity());
    assertEquals(10, building.getNumberOfFloors());
  }

  /**
   * Tests the building constructor with an invalid number of floors.
   * Expects an IllegalArgumentException to be thrown.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testBuildingConstructorInvalidNumOfFloors() {
    new Building(1, 2, 5);
  }

  /**
   * Tests the building constructor with an invalid number of elevators.
   * Expects an IllegalArgumentException to be thrown.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testBuildingConstructorInvalidNumOfElevators() {
    new Building(3, 0, 5);
  }

  /**
   * Tests the building constructor with invalid elevator capacity.
   * Expects an IllegalArgumentException to be thrown.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testBuildingConstructorInvalidElevatorCapacity() {
    new Building(3, 2, 2);
  }

  /**
   * Tests the starting of the elevator system.
   * Verifies the system status changes to running after the method call.
   */
  @Test
  public void testStartElevatorSystemStatus() {
    building.startElevatorSystem();
    // Expected conditions after starting
    assertEquals(ElevatorSystemStatus.running,
        building.getElevatorSystemStatus().getSystemStatus());
  }


  /**
   * Test up and down requests received in correct order.
   */
  @Test
  public void testStoreRequestInOrderReceived() {
    building.startElevatorSystem();
    building.addRequest(new Request(3, 7));
    building.addRequest(new Request(2, 6));
    building.addRequest(new Request(4, 2));
    building.addRequest(new Request(8, 2));
    String actual = building.getElevatorSystemStatus().toString();
    System.out.println(actual);
    assertTrue(actual.contains("Up Requests:\n"
        + " - 3->7\n"
        + " - 2->6\n\n"
        + "Down Requests:\n"
        + " - 4->2\n"
        + " - 8->2\n"));
  }

  /**
   * Test request allocation correctly.
   * One elevator can be allocated to 2,3,6,7 floor
   */
  @Test
  public void testStoreRequestAllocation() {
    building.startElevatorSystem();
    building.addRequest(new Request(3, 7));
    building.addRequest(new Request(2, 6));
    building.addRequest(new Request(4, 2));
    building.addRequest(new Request(8, 2));
    building.stepElevators();
    String actual = building.getElevatorSystemStatus().toString();
    System.out.println(actual);
    assertTrue(actual.contains("[1|^|C  ]< -- --  2  3 -- --  6  7 -- -->"));
  }


  /**
   * Test building will not allocate request than elevator's capacity.
   * The first elevator should pick up 5 people from 1st floor then
   * go to 2,3,4,5,6 floor to drop them off. The second elevator should pick up the rest.
   */
  @Test
  public void testAllocationWithinCapacity() {
    building.startElevatorSystem();
    building.addRequest(new Request(1, 2));
    building.addRequest(new Request(1, 3));
    building.addRequest(new Request(1, 4));
    building.addRequest(new Request(1, 5));
    building.addRequest(new Request(1, 6));
    building.addRequest(new Request(1, 7));
    building.addRequest(new Request(1, 8));
    building.addRequest(new Request(1, 9));
    building.stepElevators();
    String actual = building.getElevatorSystemStatus().toString();
    System.out.println(actual);
    assertTrue(actual.contains(" - Elevator ID 0: Floor 1, Door Closed, "
        + "Direction ^, Status: [1|^|C  ]< --  1  2  3  4  5  6 -- -- -->\n"
        + " - Elevator ID 1: Floor 1, Door Closed, Direction ^, "
        + "Status: [1|^|C  ]< --  1 -- -- -- -- --  7  8  9>\n"));
  }

  /**
   * Tests the return value of starting the elevator system.
   * Verifies that starting the system returns true under normal conditions.
   */
  @Test
  public void testStartElevatorSystemReturnValue() {
    assertTrue(building.startElevatorSystem());
    building.takeAllElevatorsOutOfService();
    assertTrue(building.startElevatorSystem());
  }

  /**
   * Tests the return value of starting the elevator system when it's already running.
   * Verifies that calling start on a running system still returns true.
   */
  @Test
  public void testStartElevatorSystemReturnValueWhileRunning() {
    building.startElevatorSystem();
    assertTrue(building.startElevatorSystem());

  }

  /**
   * Tests starting the elevator system while it is stopping.
   * Expects an IllegalStateException to be thrown.
   */
  @Test(expected = IllegalStateException.class)
  public void testStartElevatorSystemWhileStopping() {
    building.startElevatorSystem();
    building.addRequest(new Request(2, 3));
    building.stepElevators();
    building.stepElevators();
    building.stopElevatorSystem();
    assertTrue(building.startElevatorSystem());
  }

  /**
   * Tests adding an invalid request (invalid floors).
   * Expects an IllegalStateException to be thrown due to the system's status.
   */
  @Test(expected = IllegalStateException.class)
  public void testAddRequestInvalidArgument() {
    building.addRequest(new Request(99, 999));
  }

  /**
   * Tests adding a request when the system is running.
   * Verifies that the request is successfully added.
   */
  @Test
  public void testAddRequestWhenRunning() {
    building.startElevatorSystem();
    assertTrue(building.addRequest(new Request(1, 3)));
    String actual = building.getElevatorSystemStatus().toString();
    assertTrue(actual.contains("Up Requests:\n"
        + " - 1->3\n"));
  }

  /**
   * Tests adding a request without starting the system first.
   * Expects an IllegalStateException due to the system not accepting requests.
   */
  @Test(expected = IllegalStateException.class)
  public void testAddRequestWhenNotStarting() {
    // Directly testing without starting
    building.addRequest(new Request(1, 3));
  }

  /**
   * Tests adding a request when the system is out of service.
   * Expects an IllegalStateException to be thrown.
   */
  @Test(expected = IllegalStateException.class)
  public void testAddRequestWhenOutOfService() {
    building.startElevatorSystem();
    building.stopElevatorSystem();
    building.stepElevators();
    building.addRequest(new Request(1, 3));
  }

  /**
   * Tests adding a request when the system is in the process of stopping.
   * Expects an IllegalStateException to be thrown.
   */
  @Test(expected = IllegalStateException.class)
  public void testAddRequestWhenStopping() {
    building.startElevatorSystem();
    building.addRequest(new Request(2, 3));
    building.stepElevators();
    building.stopElevatorSystem();
    building.addRequest(new Request(1, 3));
  }

  /**
   * Tests adding a request to move down when the elevator is at the top floor.
   * Verifies the request is correctly processed.
   */
  @Test
  public void testAddDownRequest() {
    building.startElevatorSystem();
    for (int i = 0; i < 15; i++) {
      building.stepElevators();
    }
    // now at top
    Request downRequest = new Request(9, 8);
    building.addRequest(downRequest);

    String actual = building.getElevatorSystemStatus().toString();

    assertTrue("Down request was not correctly added",
        actual.contains("9->8"));
  }

  /**
   * Tests stopping the elevator system when it's already in the process of stopping.
   * Verifies the system status after the operation.
   */
  @Test
  public void testStopElevatorSystemWithStoppingStatus() {
    building.startElevatorSystem();
    building.stopElevatorSystem();
    // Verify the system is in the correct state after stopping
    assertEquals(ElevatorSystemStatus.stopping,
        building.getElevatorSystemStatus().getSystemStatus());
  }

  /**
   * Tests stopping the elevator system with a specific direction of down.
   * Verifies the system and elevator status after stopping.
   */
  @Test
  public void testStopElevatorSystemWithDownDirection() {
    building.startElevatorSystem();
    building.addRequest(new Request(1, 3));
    building.stepElevators();
    building.stepElevators();
    building.stopElevatorSystem();
    String actual = building.getElevatorSystemStatus().toString();
    System.out.println(actual);
    assertTrue(actual.contains("Direction v"));  // both elevators change direction
    assertTrue(actual.contains(
        "Status: [1|v|O 3]< -- -- -- -- -- -- -- -- -- -->"));  // the other one is going down
  }

  /**
   * Tests the stepping of elevators.
   * Verifies the status of elevators before and after the step operation.
   */
  @Test
  public void testStepElevators() {
    building.startElevatorSystem();
    building.addRequest(new Request(1, 3));
    // Expected status after adding request but before stepping
    String expectedInitialStatus = "Waiting[Floor 0, Time 5]";
    String actual = building.getElevatorSystemStatus().toString();

    assertTrue(actual.contains(expectedInitialStatus));

    building.stepElevators();

    // Expected status after stepping once`
    String expectedFirstElevator = "[1|^|C  ]< --  1 --  3 -- -- -- -- -- -->";
    String expectedSecondElevator = "Waiting[Floor 0, Time 4]";
    actual = building.getElevatorSystemStatus().toString();
    assertTrue(actual.contains(expectedFirstElevator));
    assertTrue(actual.contains(expectedSecondElevator));
  }


  /**
   * Tests the checkGroundFloor method to verify it returns false
   * when not all elevators are on the ground floor.
   */
  @Test
  public void testCheckGroundFloorReturnsFalse() {
    building.startElevatorSystem();
    building.addRequest(new Request(0, 2));
    for (int i = 0; i < 5; i++) {
      building.stepElevators();
    }
    building.stopElevatorSystem();
    assertNotEquals(
        "System should not transition to outOfService if elevators are not all on the ground floor",
        ElevatorSystemStatus.outOfService, building.getElevatorSystemStatus().getSystemStatus());
  }

  /**
   * Tests taking a single elevator out of service.
   * Verifies the elevator status after the operation.
   */
  @Test
  public void testTakeElevatorOutOfService() {
    building.startElevatorSystem();

    // Take the first elevator out of service
    building.takeElevatorOutOfService(0);
    building.stepElevators();

    ElevatorReport elevatorReport = building.getElevatorSystemStatus().getElevatorReports()[0];
    System.out.println(elevatorReport);
    String actual = building.getElevatorSystemStatus().toString();
    System.out.println(actual);
    String expected = "Floor 0, Door Open, Direction -, "
        + "Status: Out of Service[Floor 0]";
    assertTrue("The elevator should be out of service", elevatorReport.isOutOfService());
    assertTrue(actual.contains(expected));
  }

  /**
   * Test take out of service of an elevator while it's going up.
   * The elevator should immediately change direction to down.
   */
  @Test
  public void testTakeElevatorOutOfServiceWhileGoingUp() {
    building.startElevatorSystem();
    building.addRequest(new Request(4, 7));
    building.stepElevators();
    building.stepElevators();
    // Take the first elevator out of service
    building.takeElevatorOutOfService(0);
    building.stepElevators();

    ElevatorReport elevatorReport = building.getElevatorSystemStatus().getElevatorReports()[0];
    System.out.println(elevatorReport);
    String actual = building.getElevatorSystemStatus().toString();
    System.out.println(actual);
    String expected = "Floor 1, Door Closed, Direction v, "
        + "Status: [1|v|C  ]< -- -- -- -- -- -- -- -- -- -->";
    assertTrue("The elevator should be out of service", elevatorReport.isOutOfService());
    assertTrue(actual.contains(expected));
  }

  /**
   * Tests taking all elevators out of service.
   * Verifies the status of each elevator and the system after the operation.
   */
  @Test
  public void testTakeAllElevatorsOutOfService() {
    building.startElevatorSystem();

    // Take all elevators out of service
    building.takeAllElevatorsOutOfService();

    for (ElevatorReport report : building.getElevatorSystemStatus().getElevatorReports()) {
      assertTrue("Each elevator should be out of service", report.isOutOfService());
    }

    assertEquals("The elevator system status should be out of service",
        ElevatorSystemStatus.outOfService,
        building.getElevatorSystemStatus().getSystemStatus());
  }
}
