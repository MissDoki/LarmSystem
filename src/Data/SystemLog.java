package Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//TODO add notifications to any missing logins.
public class SystemLog {
    private final List<String> logSystem = new ArrayList<>();

    public SystemLog(){}

    //addlogStatus
    public void addLogStatus(String log){
        String finalLog = "[ " + LocalDateTime.now() + " ] " + log;
        logSystem.add(finalLog);
        System.out.println(finalLog);
    }

    //displayLogs
    public void displayLogs(){
        for(String log : logSystem){
            System.out.println(log);
        }
    }
}