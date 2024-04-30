# Building Elevator System Simulation

## About/Overview
The Building Elevator System Simulation is designed to manage and simulate the operations of elevators within a multi-story building. This software aims to provide a realistic representation of how elevators operate in response to user requests, including handling multiple elevators, floors, and dynamic user-generated requests.

## Features
- **Dynamic Elevator Simulation:** Simulate multiple elevators operating simultaneously in a building.
- **Interactive User Interface:** Allows users to directly interact with the simulation through a graphical interface.
- **Real-time Updates:** View real-time updates on the status of each elevator, including current floor, direction, and door status.
- **Request Handling:** Users can input requests for elevator service from various floors.
- **Customizable Settings:** Start the simulation with a customizable number of floors, elevators, and capacity settings.

## Install
To run the program:
1. Ensure Java Runtime Environment (JRE) is installed on your system.
2. Download `BuildingElevatorSystem.jar`.
3. Open a command prompt or terminal window.
4. Change to the directory where the jar file is located.
5. Run the command: `java -jar BuildingElevatorSystem.jar`

## How to Use
- **Starting the Simulation:** Upon launching the application, user will be prompted to enter the number of elevators, floors, and the capacity of each elevator. After setting these values, the main simulation window will open.
- **Making Requests:** To make an elevator request, enter the start and end floors in the designated text fields and press the "Submit Request" button.
- **Controlling the Simulation:** 
    - `Step` button to advance the simulation one step at a time.
    - `Start System` to start the elevator system.
    - `Stop System` to stop the elevator system.
        - Notice: After "Stop System" is clicked, elevators will first go to ground floor then open the door and stop the system. 

## Limitations
- The simulation does not support emergency or high-priority calls.
- Congestion and optimized routing based on real-time traffic are not considered.
- Building only distributes requests when elevators on either ground floor or top floor

## Citations
- image:Flaticon.com
