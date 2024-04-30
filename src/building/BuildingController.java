package building;

import java.util.Arrays;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import scanerzus.Request;

/**
 * Controls the interactions between the model (Building) and the view (BuildingView)
 * for an elevator simulation system. This class handles user inputs, updates the model,
 * and refreshes the view based on the changes in the model.
 */
public class BuildingController {
  private BuildingView view;
  private Building model;

  /**
   * This is a not used constructor, can be used by setting fields in Main.
   * Intentionally left here in case user don't want to define number of floors,
   * number of elevators and elevator capacity at the start of program.
   *
   * @param model building model
   * @param view  building view
   */
  public BuildingController(Building model, BuildingView view) {
    this.model = model;
    this.view = view;
    initView();
  }

  /**
   * Default constructor that prompts the user for building settings such as number of floors,
   * number of elevators, and elevator capacity. This constructor initializes the building model
   * and view based on user input. If the input is invalid or the user cancels, the program will
   * exit.
   */
  public BuildingController() {
    int[] settings = promptForBuildingSettings(); // initial settings
    if (settings != null) {
      // use initial setting initialize model
      this.model = new Building(settings[0], settings[1], settings[2]);
      // use initial setting initialize view
      this.view = new BuildingView(settings[1], settings[0]);
      initView();
      view.setVisible(true);
    } else {
      // user no input or invalid input then exit
      System.exit(0);
    }
  }

  /**
   * Prompts the user for building settings via a dialog box.
   *
   * @return An array of integers containing number of floors,number of elevators,elevator capacity.
   */
  private int[] promptForBuildingSettings() {
    JTextField floorsField = new JTextField();
    JTextField elevatorsField = new JTextField();
    JTextField capacityField = new JTextField();
    Object[] message = {
        "Number of floors:", floorsField,
        "Number of elevators:", elevatorsField,
        "Elevator capacity:", capacityField
    };

    int option = JOptionPane.showConfirmDialog(null, message,
        "Enter Building Settings", JOptionPane.OK_CANCEL_OPTION);
    if (option == JOptionPane.OK_OPTION) {
      try {
        // setting up fields
        int floors = Integer.parseInt(floorsField.getText());
        int elevators = Integer.parseInt(elevatorsField.getText());
        int capacity = Integer.parseInt(capacityField.getText());
        return new int[] {floors, elevators, capacity};
      } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null,
            "Please enter valid integer numbers.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        return null;
      }
    } else {
      return null; // user no input
    }
  }

  /**
   * Initializes the view components and adds event listeners to handle user actions.
   */
  private void initView() {
    view.setVisible(true);
    view.addStartListener(e -> {
      try {
        model.startElevatorSystem();
        updateView();
      } catch (IllegalStateException ise) {
        JOptionPane.showMessageDialog(
            view, ise.getMessage(), "Error Starting System", JOptionPane.ERROR_MESSAGE);
      }
    });
    view.addStepListener(e -> {
      model.stepElevators();
      updateView();
    });
    view.addStartListener(e -> {
      try {
        model.startElevatorSystem();
        updateView();
      } catch (IllegalStateException ise) {
        JOptionPane.showMessageDialog(
            view, ise.getMessage(), "Error Starting System", JOptionPane.ERROR_MESSAGE);
      }
    });
    view.addStopListener(e -> {
      model.stopElevatorSystem();
      updateView();
    });
    view.addSubmitRequestListener(e -> {
      submitNewRequest();
    });
  }

  /**
   * Updates the view with the latest data from the building model.
   */
  private void updateView() {
    BuildingReport report = model.getElevatorSystemStatus();
    view.updateElevatorStatuses(
        Arrays.asList(report.getElevatorReports()), report.getSystemStatus());
    view.updateUpRequestList(report.getUpRequests());
    view.updateDownRequestList(report.getDownRequests());
  }

  /**
   * Handles the submission of a new elevator request by validating the user's input
   * and updating the model and view accordingly.
   */
  private void submitNewRequest() {
    try {
      int startFloor = Integer.parseInt(view.getStartFloor());
      int endFloor = Integer.parseInt(view.getEndFloor());
      int numberOfFloors = model.getNumberOfFloors();

      // Check if start and end floors are the same
      if (startFloor == endFloor) {
        JOptionPane.showMessageDialog(view,
            "Start floor and end floor cannot be the same.",
            "Invalid Floor Number", JOptionPane.ERROR_MESSAGE);
        return;
      }

      // Validate the start floor
      if (startFloor < 0 || startFloor > numberOfFloors - 1) {
        JOptionPane.showMessageDialog(view,
            "Invalid start floor number. It must be greater than or equal to 0 and less than "
                + (numberOfFloors - 1), "Invalid Floor Number", JOptionPane.ERROR_MESSAGE);
        return;
      }

      // Validate the end floor
      if (endFloor >= numberOfFloors || endFloor < 0) {
        JOptionPane.showMessageDialog(view,
            "Invalid end floor number. It must be less than "
                + numberOfFloors + " and greater than or equal to 0",
            "Invalid Floor Number", JOptionPane.ERROR_MESSAGE);
        return;
      }

      // If all checks pass, then create the request
      Request request = new Request(startFloor, endFloor);
      model.addRequest(request);
      updateView();
    } catch (NumberFormatException nfe) {
      JOptionPane.showMessageDialog(view,
          "Please enter valid numbers.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
    } catch (IllegalStateException ise) {
      // handle exceptions thrown by the model when the system is out of service or stopping
      JOptionPane.showMessageDialog(view, ise.getMessage(),
          "Request Error", JOptionPane.ERROR_MESSAGE);
    }
  }
}
