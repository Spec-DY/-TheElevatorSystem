package building;

import building.enums.ElevatorSystemStatus;
import elevator.ElevatorReport;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import scanerzus.Request;

/**
 * Represents the graphical user interface for managing a building's elevator system.
 * This class is responsible for visualizing elevator movements, and displaying
 * elevator statuses and requests.
 */
public class BuildingView extends JFrame {
  private JButton stepButton;
  private JButton startButton;
  private JButton stopButton;
  private JTextField startFloorField;
  private JTextField endFloorField;
  private JButton submitRequestButton;
  private JTextArea upRequestListArea;
  private JTextArea downRequestListArea;
  private List<JLabel> elevatorLabels;
  private final int numberOfFloors;
  private ImageIcon elevatorUpIcon;
  private ImageIcon elevatorDownIcon;
  private ImageIcon elevatorNoIcon;
  private ImageIcon doorOpenIcon;
  private ImageIcon elevatorWaitIcon;
  private ImageIcon elevatorStopIcon;

  /**
   * Constructs a BuildingView with a specified number of elevators and floors.
   *
   * @param numElevators   The number of elevators in the building.
   * @param numberOfFloors The total number of floors in the building.
   */
  public BuildingView(int numElevators, int numberOfFloors) {
    this.numberOfFloors = numberOfFloors;
    loadElevatorIcons();
    initializeComponents(numElevators);
  }

  /**
   * Loads icons for various elevator states from the resource directory.
   */
  private void loadElevatorIcons() {
    elevatorUpIcon = createIcon("/elevatorup.jpg", 50, 50);
    elevatorDownIcon = createIcon("/elevatordown.jpg", 50, 50);
    elevatorNoIcon = createIcon("/elevatorno.png", 50, 50);
    doorOpenIcon = createIcon("/dooropen.png", 50, 50);
    elevatorWaitIcon = createIcon("/elevatorwait.png", 50, 50);
    elevatorStopIcon = createIcon("/elevatorstop.jpg", 50, 50);
  }

  /**
   * Creates an ImageIcon from a given file path with specified dimensions.
   *
   * @param path   The path to the image file.
   * @param width  The width to which the image should be scaled.
   * @param height The height to which the image should be scaled.
   * @return ImageIcon that has been scaled to the specified dimensions.
   */
  private ImageIcon createIcon(String path, int width, int height) {
    URL imageUrl = getClass().getResource(path);
    if (imageUrl != null) {
      ImageIcon icon = new ImageIcon(imageUrl);
      Image image = icon.getImage();
      Image newImg = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
      return new ImageIcon(newImg);
    } else {
      System.err.println("Image file not found at " + path);
      return null;
    }
  }

  /**
   * Initializes the components of the BuildingView interface.
   * This includes window, button, elevator, requests.
   *
   * @param numElevators The number of elevators to be displayed in the interface.
   */
  private void initializeComponents(int numElevators) {
    int elevatorWidth = 240; // each elevator width
    int windowWidth = elevatorWidth * numElevators; // window width
    int windowHeight = 615; // window height

    setTitle("Building Elevator System");
    setSize(windowWidth, windowHeight); // set window size
    setResizable(false);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    JPanel mainElevatorPanel = new JPanel(new GridLayout(1, numElevators, 1, 1));
    elevatorLabels = new ArrayList<>();
    JPanel[] elevatorShafts = new JPanel[numElevators];

    for (int i = 0; i < numElevators; i++) {
      elevatorShafts[i] = new JPanel(null);
      elevatorShafts[i].setPreferredSize(new Dimension(242, 500));  // each elevator's tunnel size
      elevatorShafts[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));

      JLabel elevatorLabel = new JLabel("", SwingConstants.LEFT);
      elevatorLabel.setOpaque(true);
      elevatorLabel.setBackground(Color.LIGHT_GRAY);
      elevatorLabel.setBounds(1, 1000, 240, 50);  // elevator status label size n position
      elevatorLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));  //elevator label font
      elevatorShafts[i].add(elevatorLabel);
      elevatorLabels.add(elevatorLabel);

      mainElevatorPanel.add(elevatorShafts[i]);
    }

    add(new JScrollPane(mainElevatorPanel), BorderLayout.CENTER);

    //button area
    stepButton = new JButton("Step");
    startButton = new JButton("Start System");
    stopButton = new JButton("Stop System");

    startFloorField = new JTextField(3);
    endFloorField = new JTextField(3);
    submitRequestButton = new JButton("Submit Request");

    JPanel controlPanel = new JPanel();
    controlPanel.add(stepButton);
    controlPanel.add(startButton);
    controlPanel.add(stopButton);
    controlPanel.add(new JLabel("Start Floor:"));
    controlPanel.add(startFloorField);
    controlPanel.add(new JLabel("End Floor:"));
    controlPanel.add(endFloorField);
    controlPanel.add(submitRequestButton);

    // request area
    upRequestListArea = new JTextArea(10, 6);
    upRequestListArea.setEditable(false);
    JScrollPane upScrollPane = new JScrollPane(upRequestListArea);
    upScrollPane.setBorder(BorderFactory.createTitledBorder("Up Requests"));

    downRequestListArea = new JTextArea(10, 10);
    downRequestListArea.setEditable(false);
    JScrollPane downScrollPane = new JScrollPane(downRequestListArea);
    downScrollPane.setBorder(BorderFactory.createTitledBorder("Down Requests"));

    JPanel requestPanel = new JPanel(new GridLayout(1, 2));
    requestPanel.add(upScrollPane);
    requestPanel.add(downScrollPane);

    add(controlPanel, BorderLayout.SOUTH);
    add(requestPanel, BorderLayout.EAST);
  }

  /**
   * Registers an ActionListener for the step button.
   *
   * @param listener The ActionListener to attach to the step button.
   */
  public void addStepListener(ActionListener listener) {
    stepButton.addActionListener(listener);
  }

  /**
   * Registers an ActionListener for the start button.
   *
   * @param listener The ActionListener to attach to the start button.
   */
  public void addStartListener(ActionListener listener) {
    startButton.addActionListener(listener);
  }

  /**
   * Registers an ActionListener for the stop button.
   *
   * @param listener The ActionListener to attach to the stop button.
   */
  public void addStopListener(ActionListener listener) {
    stopButton.addActionListener(listener);
  }

  /**
   * Registers an ActionListener for the submit request button.
   *
   * @param listener The ActionListener to attach to the submit request button.
   */
  public void addSubmitRequestListener(ActionListener listener) {
    submitRequestButton.addActionListener(listener);
  }

  /**
   * Retrieves the text from the start floor field, which is input by the user.
   * This method is typically used to get the floor from which the elevator is
   * requested to start its journey.
   *
   * @return The text entered the start floor input field as a String.
   */
  public String getStartFloor() {
    return startFloorField.getText();
  }

  /**
   * Retrieves the text from the end floor field, which is input by the user.
   * This method is typically used to get the destination floor for the elevator.
   *
   * @return The text entered the end floor input field as a String.
   */
  public String getEndFloor() {
    return endFloorField.getText();
  }

  /**
   * Updates the statuses of elevators based on the provided list of ElevatorReports.
   *
   * @param elevatorReports A list of ElevatorReport objects representing
   *                       the current state of each elevator.
   * @param systemStatus    The overall system status, affecting the iconography displayed.
   */
  public void updateElevatorStatuses(List<ElevatorReport> elevatorReports,
                                     ElevatorSystemStatus systemStatus) {
    for (int i = 0; i < elevatorLabels.size(); i++) {
      ElevatorReport report = elevatorReports.get(i);
      JLabel label = elevatorLabels.get(i);
      updateElevatorIcon(label, report, systemStatus);
      int positionY = calculateVerticalPosition(report.getCurrentFloor());
      label.setLocation(label.getX(), positionY);
      label.setText(report.toString());
    }
  }

  /**
   * Updates the displayed icon for a given elevator label
   * based on the elevator's current report and system status.
   *
   * @param label        The JLabel representing an elevator.
   * @param report       The ElevatorReport for the elevator.
   * @param systemStatus The current status of the elevator system.
   */
  private void updateElevatorIcon(JLabel label, ElevatorReport report,
                                  ElevatorSystemStatus systemStatus) {
    if (systemStatus == ElevatorSystemStatus.stopping) {
      // when elevator is stopping
      label.setIcon(elevatorNoIcon);
    } else if (systemStatus == ElevatorSystemStatus.outOfService && !report.isDoorClosed()) {
      // when door is open but out of service
      label.setIcon(elevatorStopIcon);
    } else if (!report.isDoorClosed()) {
      // when door is open but still running
      label.setIcon(doorOpenIcon);
    } else {
      switch (report.getDirection()) {
        case UP:
          // going up
          label.setIcon(elevatorUpIcon);
          break;
        case DOWN:
          // going down
          label.setIcon(elevatorDownIcon);
          break;
        default:
          // elevator waiting
          label.setIcon(elevatorWaitIcon);
          break;
      }
    }
  }

  /**
   * Calculates the vertical position of an elevator label within
   * its shaft based on the current floor. The method computes the
   * position so that higher floors correspond to lower y-values
   * within the GUI, simulating the physical movement of an
   * elevator in a building.
   *
   * @param floor The current floor number where the elevator is located.
   * @return The y-coordinate for the elevator label, representing its position in the GUI.
   */
  private int calculateVerticalPosition(int floor) {
    // elevator label position calculate based on number of floors
    return (500 - 30) - (floor * (500 / numberOfFloors));
  }

  /**
   * Updates the list of up-direction requests displayed in the interface.
   *
   * @param requests A list of Requests indicating the requested floors for upward movement.
   */
  public void updateUpRequestList(List<Request> requests) {
    StringBuilder sb = new StringBuilder();
    for (Request req : requests) {
      sb.append(req.toString()).append("\n");
    }
    upRequestListArea.setText(sb.toString());
  }

  /**
   * Updates the list of down-direction requests displayed in the interface.
   *
   * @param requests A list of Requests indicating the requested floors for downward movement.
   */
  public void updateDownRequestList(List<Request> requests) {
    StringBuilder sb = new StringBuilder();
    for (Request req : requests) {
      sb.append(req.toString()).append("\n");
    }
    downRequestListArea.setText(sb.toString());
  }
}