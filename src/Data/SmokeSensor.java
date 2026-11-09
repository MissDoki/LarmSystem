package Data;

public class SmokeSensor extends Sensor{
    public SmokeSensor(){}

    @Override
    public String getPrefix(){
        return "S";
    }

    @Override
    public void trigger(){
        this.setTriggered(true);
    }
    @Override
    public void setArmed(boolean armed){
        super.setArmed(true);
    }
}
