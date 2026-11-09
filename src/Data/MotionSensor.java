package Data;

public class MotionSensor extends Sensor{
    public MotionSensor(){}

    @Override
    public String getPrefix(){
        return "M";
    }

    @Override
    public void trigger(){
        if(isArmed()){
            this.setTriggered(true);
        }
    }
}
