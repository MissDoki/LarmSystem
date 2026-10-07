package Service;
import Data.*;
import java.util.ArrayList;
import java.util.List;

public class AlarmSystemService {
    private List<Sensor> sensors = new ArrayList<>();
    private SystemLog log = new SystemLog();

    public AlarmSystemService(){
        addSensor(new DoorSensor(),"Hall Entrance" ,false);
        addSensor(new DoorSensor(),"Hall Window" ,false);
        addSensor(new MotionSensor(), "Hall", false);
        addSensor(new MotionSensor(), "Office", false);
        addSensor(new SmokeSensor(), "Storage", true);
        addSensor(new DoorSensor(), "Storage", false);

        //test data made for testing status
//        triggerById("D0");
//        triggerById("M1");
//        triggerById("S0");


    }

    //loggService
    public void logService(){
        log.displayLogs();
    }

    //add
    public void addSensor(Sensor sensor, String location, boolean armed){
        int id = 0;
        boolean check = true;
        while(check){
            check = false;
            String finalID = sensor.getPrefix().toUpperCase() + id;
            for(Sensor checkSensor : sensors){
                if(checkSensor.getId().equals(finalID)){
                    check = true;
                    id ++;
                    break;
                }
            }
            if(!check){
                sensor.setId(finalID);
                break;
            }
        }
        sensor.getPrefix();
        sensor.setLocation(location);
        sensor.setArmed(armed);
        sensors.add(sensor);
    }

    //removeSensor
    public boolean removeSensor(String id){
        for(Sensor checkSensor : sensors){
            if(id.equalsIgnoreCase(checkSensor.getId())){
                sensors.remove(checkSensor);
                return true;
            }
        }
        return false;
    }

    //armSensors
    public ResultState sensorArm(String id){
        for(Sensor checkSensor : sensors){
            if(id.equalsIgnoreCase(checkSensor.getId())){
                if(!checkSensor.isArmed()){
                    checkSensor.setArmed(true);
                    log.addLogStatus("Sensor with id " + id + " has been activated.");
                    return ResultState.SUCCESS;
                }
                return ResultState.FOUND_BUT_UNABLE;
            }
        }
        return ResultState.NOT_FOUND;
    }

    //disarmSensors
    public ResultState sensorDisarm(String id){
        for(Sensor checkSensor : sensors){
            if(id.equalsIgnoreCase(checkSensor.getId())){
                if(checkSensor.isArmed()){
                    checkSensor.setArmed(false);
                    log.addLogStatus("Sensor with id " + id + " has been deactivated.");
                    return ResultState.SUCCESS;
                }
                return ResultState.FOUND_BUT_UNABLE;
            }
        }
        return ResultState.NOT_FOUND;
    }

    //triggerById
    public void triggerById(String id){
        for(Sensor checkSensor : sensors){
            if(id.equalsIgnoreCase(checkSensor.getId())){
                if(!checkSensor.isTriggered()){
                    checkSensor.trigger();
                    log.addLogStatus("Sensor with id " + id + " has been triggered.");

                    return;
                }
                else{
                    System.out.println(checkSensor.getId() + checkSensor.getLocation() + " is already triggered.");
                }
            }
        }
        System.out.println("No sensor with this ID found.");
    }

    //isTriggered
    public boolean isTriggered(String id){
        for(Sensor checkSensor : sensors){
            if(id.equalsIgnoreCase(checkSensor.getId())){
                return checkSensor.isTriggered();
            }
        }
        System.out.println("No sensor with this ID found.");
        return false;
    }

    //isArmed
    public boolean isArmed(String id){
        for(Sensor checkSensor : sensors){
            if(id.equalsIgnoreCase(checkSensor.getId())){
                return checkSensor.isArmed();
            }
        }
        System.out.println("No sensor with this ID found.");
        return false;
    }

    //resetOneSensor
    public boolean resetOneSensor(String id){
        for(Sensor checkSensor : sensors){
            if(id.equalsIgnoreCase(checkSensor.getId())){
                log.addLogStatus("Sensor with id " + id + "has been reset.");
                checkSensor.reset();
                return true;
            }
        }
        return false;
    }

    //resetAllSensors
    public void resetAll(){
        for(Sensor checkSensor : sensors){
            if(checkSensor.isTriggered()){
                checkSensor.reset();
            }
        }
        log.addLogStatus("All the sensors has been reset.");
    }

    //printStatus
    public void printStatus(){
        for (Sensor sensor : sensors) {
            sensor.printStatus();
        }
    }

    //sensorCount
    public int sensorCount(){
        int count = 0;
        for(Sensor checkSensor : sensors){
            if(checkSensor.isTriggered()){
                count ++;
            }
        }
        return count;
    }

    //getSensors
    public List<Sensor> getSensors(){
        return sensors;
    }

    // Hall: door sensor + motion sensor (door sensor connected with motion sensor)
    // Office: motion sensor (sensor no connection)
    // Storage: smoke sensor + door sensor (door sensor connected with garage)
    // Garage: door sensor (door sensor connected with storage)

    //door and motion sensor only to activate when the system is on
    //smoke sensor active even if the system is off
    //when a sensor is triggered, and it is allowed according to previous statements the system will trigger an alarm
    //possible to reset and turn of during an alarm

    //AlarmSystem: add, arm, disarm, triggerById, printStatus, resetAll
}
