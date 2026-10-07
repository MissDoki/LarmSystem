package UI;
import Data.*;
import Service.AlarmSystemService;

public class ConsoleUI {
    private static void clearScreen() {
        System.out.println("\n".repeat(10));
        try {
            String cmd = System.getProperty("os.name").toLowerCase().contains("win")
                    ? "cls" : "clear";
            new ProcessBuilder(cmd).inheritIO().start().waitFor();
        } catch (Exception e) {}
    }

    private final InputScanner scanner = new InputScanner();
    private final AlarmSystemService sensors;
    public ConsoleUI(AlarmSystemService sensors){this.sensors = sensors;}

    public void run() {
        while (true) {
            clearScreen();
            System.out.println("""
                    | ■ AAS Advanced.Alarm.System Main Menu                                       |
                    ===============================================================================
                    1: Add/Remove/Count Sensors
                    2: Ativate/Deactivate Sensors
                    3: Simulate System
                    4: Log Status
                    5: Exit Program
                    
                    Choose which option from the menu and continue
                    ===============================================================================
                    """);
            System.out.print("Option: ");
            switch (scanner.nextInt()) {
                case 1:
                    editSensors();
                    break;
                case 2:
                    alarmOnOff();
                    break;
                case 3:
                    alarmSimulator();
                    break;
                case 4:
                    alarmStatus();
                    break;
                case 5:
                    return;
            }
        }

    }

    void editSensors() {
        clearScreen();
        while (true) {
            System.out.println("""
                    | ■ AAS Advanced.Alarm.System - Add or Remove Sensors                         |
                    ===============================================================================
                    1: Add Sensors
                    2: Remove Sensors
                    3: Count Amount of Triggered Sensors
                    4: « Back
                   
                    Choose which option from the menu and continue
                   ===============================================================================
                   """);

            switch (scanner.nextInt()) {
                case 1:
                    System.out.println("""
                            ===============================================================================
                            | Add a new sensor: Door/Motion/Smoke sensors                                 |
                            ===============================================================================
                            """);
                    System.out.println(" ");
                    System.out.print("Add a new sensor (doorsensor/motionsensor/smokesensor): ");
                    scanner.nextLine().trim();
                    String addSensor = scanner.nextLine().trim();
                    System.out.print("Add a new location (Hall/Office/Storage/Garage): ");
                    String addLocation = scanner.nextLine().trim();
                    System.out.print("Add it as armed/disarmed (true/false): ");
                    boolean addArmed = scanner.nextBoolean();
                    if (addSensor.equalsIgnoreCase("doorsensor")) {
                        sensors.addSensor(new DoorSensor(), addLocation, addArmed);
                        System.out.println("DoorSensor " + addLocation+ " " + addArmed + " has been added.");
                    }
                    else if(addSensor.equalsIgnoreCase("motionsensor")){
                        sensors.addSensor(new MotionSensor(), addLocation, addArmed);
                        System.out.println("MotionSensor " + addLocation+ " " + addArmed + " has been added.");
                    }
                    else if(addSensor.equalsIgnoreCase("smokesensor")){
                        sensors.addSensor(new SmokeSensor(), addLocation, addArmed);
                        System.out.println("SmokeSensor " + addLocation+ " " + addArmed + " has been added.");
                    } else {
                        System.out.println(addSensor + " is not an option for the sensor type");
                    }

                    System.out.println("Press enter to continue...");
                    scanner.nextLine();
                    scanner.nextLine();
                    break;

                case 2:
                    System.out.println("""
                            ===============================================================================
                            | D[DoorSensor] - S[SmokeSensor] - M[MotionSensor]                            |
                            ===============================================================================
                            """);
                    sensors.printStatus();
                    System.out.println(" ");
                    System.out.print("Input which sensor ID to remove a sensor or press enter to go back: ");
                    scanner.nextLine().trim();
                    String removeSensors = scanner.nextLine().trim();
                    if(sensors.removeSensor(removeSensors)) {
                        System.out.println("Sensor ID: " + removeSensors + " has been removed.");
                    }
                    else {
                        System.out.println("Error removing a current sensor.");
                    }
                    System.out.println("Press enter to continue...");
                    scanner.nextLine();
                    break;


                    case 3:
                        System.out.println("""
                            ===============================================================================
                            | Count Amount of Triggered Sensors                                           |
                            ===============================================================================
                            """);
                        System.out.println(" ");
                        System.out.println("Amount of active alarms: " + sensors.sensorCount());
                        System.out.println("Press enter to continue...");
                        scanner.nextLine();
                        scanner.nextLine();
                        break;
                case 4:
                    return;
            }
        }
    }

    //activate sensors, deactivate sensors
    void alarmOnOff() {
        clearScreen();
        while (true) {
            System.out.println("""
                    | ■ AAS Advanced.Alarm.System - Activate or Deactivate Sensors                |
                    ===============================================================================
                    1: Activate Sensor
                    2: Deactivate Sensor
                    3: « Back
                    
                    Choose which option from the menu and continue
                    ===============================================================================
                    """);

            switch (scanner.nextInt()) {
                case 1: //arm sensor(s)
                    System.out.println("""
                            ===============================================================================
                            | Activate Sensor [DoorSensor[D] - [MotionSensor[M] - [SmokeSensor[S]         |
                            ===============================================================================
                            """);
                    sensors.printStatus();
                    System.out.println(" ");
                    System.out.print("Input which sensor ID to activate: ");
                    scanner.nextLine().trim();
                    String activateSensor = scanner.nextLine().trim();

                    switch (sensors.sensorArm(activateSensor)) {
                        case ResultState.SUCCESS:
                            System.out.println("Sensor ID: " + activateSensor + " has been activated");
                            break;

                        case ResultState.FOUND_BUT_UNABLE:
                            System.out.println("Error activating the sensor - sensor already active");
                            break;

                        case ResultState.NOT_FOUND:
                            System.out.println("Error activating the sensor - sensor not found.");
                            break;
                    }
                    System.out.println("Press enter to continue...");
                    scanner.nextLine();
                    scanner.nextLine();
                    break;

                case 2:
                    System.out.println("""
                            ===============================================================================
                            | Deactivate Sensor [DoorSensor[D] - [MotionSensor[M] - [SmokeSensor[S]       |
                            ===============================================================================
                            """);
                    sensors.printStatus();
                    System.out.println(" ");
                    System.out.print("Input which sensor ID to activate: ");
                    scanner.nextLine().trim();
                    String deactivateSensor = scanner.nextLine().trim();

                    switch (sensors.sensorDisarm(deactivateSensor)) {
                        case ResultState.SUCCESS:
                            System.out.println("Sensor ID: " + deactivateSensor + " has been activated");
                            break;

                        case ResultState.FOUND_BUT_UNABLE:
                            System.out.println("Error activating the sensor - sensor already active");
                            break;

                        case ResultState.NOT_FOUND:
                            System.out.println("Error activating the sensor - sensor not found.");
                            break;
                    }
                    System.out.println("Press enter to continue...");
                    scanner.nextLine();
                    scanner.nextLine();
                    break;

                case 3:
                    return;
            }
        }
    }

    //alarm simulator: //TODO
    void alarmSimulator() {
        clearScreen();
        while (true) {
            System.out.println("""
                    | ■ AAS Advanced.Alarm.System - Alarm Simulator                               |
                    ===============================================================================
                    1: Door Sensor
                    2: Motion Sensor
                    3: Smoke Sensor
                    4: High Security (all sensors)
                    5: « Back
                    
                    Choose which option from the menu and continue
                    ===============================================================================
                    """);

            AlarmSystemService simulation = new AlarmSystemService();

            switch (scanner.nextInt()) {
                case 1:
                    clearScreen();
                    System.out.println("""
                            | ■ AAS Advanced.Alarm.System - Door Sensors                                  |
                            ===============================================================================
                            1: Door Sensor Simulation
                            2: « Back
                            
                            Choose which option from the menu and continue
                            ===============================================================================
                            """);
                    //extra menu
                    switch (scanner.nextInt()) {
                        case 1:
                            System.out.println("Door sensor simulator to check if the sensor is working.");
                            System.out.println(" ");

                            if(!simulation.isArmed("D0")){
                                simulation.sensorArm("D0");
                                System.out.println("Door Sensor ID D0 has been turned on");
                            }
                            simulation.triggerById("D0");
                            if(simulation.isTriggered("D0")){
                                System.out.println("Test successful on Sensor ID - D0");
                                System.out.println(" ");
                            }
                            System.out.println("Press enter to continue...");
                            scanner.nextLine();
                            scanner.nextLine();
                            break;

                        case 2:
                            break;
                    }
                    break;

                case 2:
                    clearScreen();
                    System.out.println("""
                            | ■ AAS Advanced.Alarm.System - Motion Sensors                                |
                            ===============================================================================
                            1: Motion Sensor Simulation
                            2: « Back
                            
                            Choose which option from the menu and continue
                            ===============================================================================
                            """);
                    //extra menu
                    switch (scanner.nextInt()) {
                        case 1:
                            System.out.println("Motion sensor simulator to check if the sensor is working.");
                            System.out.println(" ");
                            if(!simulation.isArmed("M0")){
                                simulation.sensorArm("M0");
                                System.out.println("Motion Sensor ID M0 has been turned on");
                            }
                            simulation.triggerById("M0");
                            if(simulation.isTriggered("M0")){
                                System.out.println("Test successful on Sensor ID - M0");
                                System.out.println(" ");
                            }
                            System.out.println("Press enter to continue...");
                            scanner.nextLine();
                            scanner.nextLine();
                            break;

                        case 2:
                            break;
                    }
                    break;

                case 3:
                    clearScreen();
                    System.out.println("""
                            | ■ AAS Advanced.Alarm.System - Smoke Sensors                                 |
                            ===============================================================================
                            1: Smoke Sensor Simulation
                            2: « Back
                            
                            Choose which option from the menu and continue
                            ===============================================================================
                            """);
                    //extra menu
                    switch (scanner.nextInt()) {
                        case 1:
                            System.out.println("Smoke sensor simulator to check if the sensor is working.");
                            System.out.println(" ");

                            if(!simulation.isArmed("S0")){
                                simulation.sensorArm("S0");
                                System.out.println("Smoke Sensor ID S0 has been turned on");
                            }
                            simulation.triggerById("S0");
                            if(simulation.isTriggered("S0")){
                                System.out.println("Test successful on Sensor ID - S0");
                                System.out.println(" ");
                            }
                            System.out.println("Press enter to continue...");
                            scanner.nextLine();
                            scanner.nextLine();
                            break;

                        case 2:
                            break;
                    }
                    break;

                case 4:
                    clearScreen();
                    System.out.println("""
                            | ■ AAS Advanced.Alarm.System - High Security                                 |
                            ===============================================================================
                            1: All Sensors Simulation
                            2: « Back
                            
                            Choose which option from the menu and continue
                            ===============================================================================
                            """);
                    //extra menu
                    switch (scanner.nextInt()) {
                        case 1:
                            System.out.println("High Security:\n" +
                                    "Simulator to check if all the sensors is working.");
                            System.out.println(" ");

                            boolean success = true;
                            for(Sensor sensor : simulation.getSensors()){
                                String simulateID = sensor.getId();
                                if(!simulation.isArmed(simulateID)){
                                    simulation.sensorArm(simulateID);
                                }
                                simulation.triggerById(simulateID);
                                if(!simulation.isTriggered(simulateID)){
                                    success = false;
                                }
                            }
                            if(success){
                                System.out.println("High Security simulator test was successful.");
                                System.out.println(" ");
                            }
                            System.out.println("Press enter to continue...");
                            scanner.nextLine();
                            scanner.nextLine();
                            break;


                        case 2:
                            break;
                    }
                case 5:
                    return;
            }
        }
    }

    //alarm status, reset active status
    void alarmStatus() {
        clearScreen();
        while (true) {
            System.out.println("""
                    | ■ AAS Advanced.Alarm.System - Current Status                                |
                    ===============================================================================
                    1: Check Door Sensor Staus
                    2: Check Motion Sensor Status
                    3: Check Smoke Sensor Status
                    4: Reset Active Status
                    5: Log System
                    6: « Back
                    
                    Choose which option from the menu and continue
                    ===============================================================================
                    """);

            switch (scanner.nextInt()) {
                case 1:
                    clearScreen();
                    System.out.println("""
                            | ■ AAS Advanced.Alarm.System - Door Sensors Status                           |
                            ===============================================================================
                            1: Door Sensor Status
                            2: « Back
                            
                            Choose which option from the menu and continue
                            ===============================================================================
                            """);
                    //extra menu
                    switch (scanner.nextInt()) {
                        case 1:
                            System.out.println("""
                            ===============================================================================
                            | Door Sensors Status                                                         |
                            ===============================================================================
                            """);
                            for(Sensor sensor : sensors.getSensors()){
                                if(sensor.getPrefix().equalsIgnoreCase(new DoorSensor().getPrefix())){
                                    sensor.printStatus();
                                }
                            }
                            System.out.println(" ");
                            System.out.println("Press enter to continue...");
                            scanner.nextLine();
                            scanner.nextLine();
                            break;

                        case 2:
                            break;
                    }
                    break;

                case 2:
                    clearScreen();
                    System.out.println("""
                            | ■ AAS Advanced.Alarm.System - Motion Sensors Status                         |
                            ===============================================================================
                            1: Motion Sensor Status
                            2: « Back
                            
                            Choose which option from the menu and continue
                            ===============================================================================
                            """);
                    //extra menu
                    switch (scanner.nextInt()) {
                        case 1:
                            System.out.println("""
                            ===============================================================================
                            | Motion Sensors Status                                                       |
                            ===============================================================================
                            """);
                            for(Sensor sensor : sensors.getSensors()){
                                if(sensor.getPrefix().equalsIgnoreCase(new MotionSensor().getPrefix())){
                                    sensor.printStatus();
                                }
                            }
                            System.out.println(" ");
                            System.out.println("Press enter to continue...");
                            scanner.nextLine();
                            scanner.nextLine();
                            break;

                        case 2:
                            break;
                    }
                    break;

                case 3:
                    clearScreen();
                    System.out.println("""
                            | ■ AAS Advanced.Alarm.System - Smoke Sensors Status                          |
                            ===============================================================================
                            1: Smoke Sensor Status
                            2: « Back
                            
                            Choose which option from the menu and continue
                            ===============================================================================
                            """);
                    //extra menu
                    switch (scanner.nextInt()) {
                        case 1:
                            System.out.println("""
                            ===============================================================================
                            | Smoke Sensors Status                                                        |
                            ===============================================================================
                            """);
                            for(Sensor sensor : sensors.getSensors()){
                                if(sensor.getPrefix().equalsIgnoreCase(new SmokeSensor().getPrefix())){
                                    sensor.printStatus();
                                }
                            }
                            System.out.println(" ");
                            System.out.println("Press enter to continue...");
                            scanner.nextLine();
                            scanner.nextLine();
                            break;

                        case 2:
                            break;

                    }
                    break;

                case 4:
                    clearScreen();
                    System.out.println("""
                            | ■ AAS Advanced.Alarm.System - Reset Active Sensors                          |
                            ===============================================================================
                            1: Reset Active Sensors
                            2: « Back
                            
                            Choose which option from the menu and continue
                            ===============================================================================
                            """);
                    //extra menu
                    switch (scanner.nextInt()) {
                        case 1:
                            sensors.resetAll();
                            System.out.println(" ");
                            System.out.println("Press enter to continue...");
                            scanner.nextLine();
                            scanner.nextLine();
                            break;

                        case 2:
                            break;
                    }
                case 5:
                    System.out.println("""
                            ===============================================================================
                            | ■ Log Entry                                                                 |
                            ===============================================================================
                            """);
                    sensors.logService();
                    System.out.println(" ");
                    System.out.println("Press enter to continue...");
                    scanner.nextLine();
                    scanner.nextLine();
                    break;
                case 6:
                    return;


            }
        }
    }

}
