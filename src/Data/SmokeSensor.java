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
        System.out.println("The sensor is active.");
    }
}
