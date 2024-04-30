package elevator;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import building.enums.Direction;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link ElevatorReport}, setting up a specific report state before each test.
 */
public class ElevatorReportTest {

  ElevatorReport report;

  /**
   * Initializes a test {@link ElevatorReport} instance with predefined attributes.
   */
  @Before
  public void setUp() {
    report = new ElevatorReport(
        1,  // elevatorId
        1,  // currentFloor
        Direction.STOPPED, // direction
        true,  // doorClosed
        new boolean[] {false, true, true},  // floorRequests
        0,  // doorOpenTimer
        0,  // endWaitTimer
        false,  // outOfService
        false  // isTakingRequests
    );
  }

  @Test
  public void getElevatorId() {
    assertEquals(1, report.getElevatorId());
  }

  @Test
  public void getCurrentFloor() {
    assertEquals(1, report.getCurrentFloor());
  }

  @Test
  public void isDoorClosed() {
    assertTrue(report.isDoorClosed());
  }

  @Test
  public void getFloorRequests() {
    assertArrayEquals(new boolean[] {false, true, true}, report.getFloorRequests());
  }

  @Test
  public void getDirection() {
    assertEquals(Direction.STOPPED, report.getDirection());
  }

  @Test
  public void getDoorOpenTimer() {
    assertEquals(0, report.getDoorOpenTimer());
  }

  @Test
  public void getEndWaitTimer() {
    assertEquals(0, report.getEndWaitTimer());
  }

  @Test
  public void isOutOfService() {
    assertFalse(report.isOutOfService());
  }

  @Test
  public void getOutOfService() {

    assertFalse(report.isOutOfService());
  }

  @Test
  public void isTakingRequests() {
    assertFalse(report.isTakingRequests());
  }

  @Test
  public void testToString() {
    String expected = "[1|-|C  ]< --  1  2>";
    assertEquals(expected, report.toString());
  }

  @Test
  public void testEquals() {
    ElevatorReport report2 = new ElevatorReport(
        1,  // elevatorId
        1,  // currentFloor
        Direction.STOPPED, // direction
        true,  // doorClosed
        new boolean[] {false, true, true},  // floorRequests
        0,  // doorOpenTimer
        0,  // endWaitTimer
        false,  // outOfService
        false  // isTakingRequests
    );

    assertEquals(report, report2);

    ElevatorReport report3 = new ElevatorReport(
        2,  // elevatorId
        1,  // currentFloor
        Direction.STOPPED, // direction
        true,  // doorClosed
        new boolean[] {false, true, true},  // floorRequests
        0,  // doorOpenTimer
        0,  // endWaitTimer
        false,  // outOfService
        false  // isTakingRequests
    );

    assertNotEquals(report, report3);

    ElevatorReport report4 = new ElevatorReport(
        1,  // elevatorId
        2,  // currentFloor
        Direction.STOPPED, // direction
        true,  // doorClosed
        new boolean[] {false, true, true},  // floorRequests
        0,  // doorOpenTimer
        0,  // endWaitTimer
        false,  // outOfService
        false  // isTakingRequests
    );

    assertNotEquals(report, report4);

    ElevatorReport report5 = new ElevatorReport(
        1,  // elevatorId
        1,  // currentFloor
        Direction.UP, // direction
        true,  // doorClosed
        new boolean[] {false, true, true},  // floorRequests
        0,  // doorOpenTimer
        0,  // endWaitTimer
        false,  // outOfService
        false  // isTakingRequests
    );

    assertNotEquals(report, report5);

    ElevatorReport report6 = new ElevatorReport(
        1,  // elevatorId
        1,  // currentFloor
        Direction.STOPPED, // direction
        false,  // doorClosed
        new boolean[] {false, true, true},  // floorRequests
        0,  // doorOpenTimer
        0,  // endWaitTimer
        false,  // outOfService
        false  // isTakingRequests
    );

    assertNotEquals(report, report6);

    ElevatorReport report7 = new ElevatorReport(
        1,  // elevatorId
        1,  // currentFloor
        Direction.STOPPED, // direction
        true,  // doorClosed
        new boolean[] {true, true, true},  // floorRequests
        0,  // doorOpenTimer
        0,  // endWaitTimer
        false,  // outOfService
        false  // isTakingRequests
    );

    assertNotEquals(report, report7);

    ElevatorReport report8 = new ElevatorReport(
        1,  // elevatorId
        1,  // currentFloor
        Direction.STOPPED, // direction
        true,  // doorClosed
        new boolean[] {false, true, true},  // floorRequests
        1,  // doorOpenTimer
        0,  // endWaitTimer
        false,  // outOfService
        false  // isTakingRequests
    );

    assertNotEquals(report, report8);

    ElevatorReport report9 = new ElevatorReport(
        1,  // elevatorId
        1,  // currentFloor
        Direction.STOPPED, // direction
        true,  // doorClosed
        new boolean[] {false, true, true},  // floorRequests
        0,  // doorOpenTimer
        1,  // endWaitTimer
        false,  // outOfService
        false  // isTakingRequests
    );

    assertNotEquals(report, report9);

    ElevatorReport report10 = new ElevatorReport(
        1,  // elevatorId
        1,  // currentFloor
        Direction.STOPPED, // direction
        true,  // doorClosed
        new boolean[] {false, true, true},  // floorRequests
        0,  // doorOpenTimer
        0,  // endWaitTimer
        true,  // outOfService
        false  // isTakingRequests
    );

    assertNotEquals(report, report10);

    ElevatorReport report11 = new ElevatorReport(
        1,  // elevatorId
        1,  // currentFloor
        Direction.STOPPED, // direction
        true,  // doorClosed
        new boolean[] {false, true, true},  // floorRequests
        0,  // doorOpenTimer
        0,  // endWaitTimer
        false,  // outOfService
        true  // isTakingRequests
    );

    assertNotEquals(report, report11);
  }

  @Test
  public void testHashCode() {
    ElevatorReport report2 = new ElevatorReport(
        1,  // elevatorId
        1,  // currentFloor
        Direction.STOPPED, // direction
        true,  // doorClosed
        new boolean[] {false, true, true},  // floorRequests
        0,  // doorOpenTimer
        0,  // endWaitTimer
        false,  // outOfService
        false  // isTakingRequests
    );

    assertEquals(report.hashCode(), report2.hashCode());


  }

  @Test
  public void testOtherClassesNotEqual() {
    assertNotEquals(report, new Object());
  }

  @Test
  public void testSameObjectEqual() {
    assertEquals(report, report);
  }
}