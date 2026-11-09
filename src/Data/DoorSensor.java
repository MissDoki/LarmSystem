package Data;

public class DoorSensor extends Sensor{
    public DoorSensor(){}

    @Override
    public String getPrefix(){
        return "D";
    }

    @Override
    public void trigger(){
        if(isArmed()){
            this.setTriggered(true);
        }
    }
}
