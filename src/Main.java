import Service.AlarmSystemService;
import UI.ConsoleUI;

public class Main {
    public static void main(String[] args) {
        ConsoleUI menu = new ConsoleUI(new AlarmSystemService());
        menu.run();

    }
}