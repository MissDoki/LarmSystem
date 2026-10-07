package Data;

public abstract class Sensor {
    private String id;
    private String location;
    private boolean triggered;
    private boolean armed;

    public Sensor (){}
    public Sensor(String id, String location, boolean triggered, boolean armed){
        this.id = id;
        this.location = location;
        this.triggered = triggered;
        this.armed = armed;
    }

    public String getId(){return id;}
    public String getLocation(){return location;}
    public boolean isTriggered(){return triggered;}
    public boolean isArmed(){return armed;}

    public void setId(String id){this.id = id;}
    public void setLocation(String location){this.location = location;}
    public void setTriggered(boolean triggered){this.triggered = triggered;}
    public void setArmed(boolean armed){this.armed = armed;}

    //printStatus
    public void printStatus() {
        System.out.println("| Sensor ID: " + getId()+ ", Location: " + getLocation()+ ", if Armed: " + isArmed() + ", if Triggered " + isTriggered() + " |");
    }

    //reset
    public void reset(boolean alarm){
        if(alarm){
            System.out.println("The alarm has been reset");
            alarm = false;
        }
        System.out.println("There is no active alarm at the moment");
    }

    //trigger
    public abstract void trigger();

    //prefix
    public abstract String getPrefix();
}
