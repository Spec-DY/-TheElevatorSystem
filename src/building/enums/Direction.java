package building.enums;

/**
 * The direction of the elevator.
 */
public enum Direction {
  UP("^"),
  DOWN("v"),
  STOPPED("-");

  private final String display;

  Direction(String symbol) {
    this.display = symbol;
  }

  @Override
  public String toString() {
    return this.display;
  }
}
