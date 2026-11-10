package Service;
import Data.*;
import java.util.ArrayList;
import java.util.List;

public class AlarmSystemService {
    private List<Sensor> sensors = new ArrayList<>();
    private SystemLog log = new SystemLog();
    private boolean system = true;

    public AlarmSystemService(){
        addSensor(new DoorSensor(),"Hall Entrance" ,true);
        addSensor(new DoorSensor(),"Hall Window" ,true);
        addSensor(new MotionSensor(), "Hall", true);
        addSensor(new MotionSensor(), "Office", true);
        addSensor(new SmokeSensor(), "Storage", true);
        addSensor(new DoorSensor(), "Storage", true);
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
        if(!system){
            if(!id.toLowerCase().startsWith("s"))
                return ResultState.SYSTEM_OFF;
        }

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
        if(!system && !id.toUpperCase().startsWith(new SmokeSensor().getPrefix().toUpperCase())){
            System.out.println("Sensor id: " + id + ".\n" +
                    "System is off, only able to trigger smoke sensors");
            return;
        }
        for(Sensor checkSensor : sensors){
            if(id.equalsIgnoreCase(checkSensor.getId())){
                if(!checkSensor.isTriggered()){
                    checkSensor.trigger();
                    if(checkSensor.isTriggered()){
                        log.addLogStatus("Sensor with id " + id + " has been triggered.");
                    }else{
                        log.addLogStatus("Sensor with id " + id + " was not triggered.");
                    }
                }
                else{
                    System.out.println(id + " " + checkSensor.getLocation() + " is already triggered.");
                }
                return;
            }
        }
    }

    //isTriggered
    public boolean isTriggered(String id){
        for(Sensor checkSensor : sensors){
            if(id.equalsIgnoreCase(checkSensor.getId())){
                return checkSensor.isTriggered();
            }
        }
        return false;
    }

    //isArmed
    public boolean isArmed(String id){
        for(Sensor checkSensor : sensors){
            if(id.equalsIgnoreCase(checkSensor.getId())){
                return checkSensor.isArmed();
            }
        }
        return false;
    }

    public boolean isLarming() {
        for (Sensor sensor : sensors) {
            if (sensor.isTriggered()) {
                return true;
            }
        }
        return false;
    }

    //turnOnSystem
    public void turnOnSystem(){
        if(!system){
            for(Sensor checkSensor : sensors) {
                checkSensor.setArmed(true);
            }
            system = true;
        } else{System.out.println("System is already ON");}

    }

    //turnOffSystem
    public void turnOffSystem(){
        if(system){
            for(Sensor checkSensor : sensors){
                checkSensor.setArmed(false); // sensor implementation decides if it skips checking if they are armed
            }
            system = false;
        }else{System.out.println("System is already OFF");}

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
}
