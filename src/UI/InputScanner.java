package UI;
import java.util.Scanner;

public class InputScanner implements ScannerInterface {
    Scanner scanner = new Scanner(System.in);

    @Override
    public String nextLine(){
        return scanner.nextLine();
    }

    @Override
    public int nextInt(){
        int value;
        while (true) {
            try {
                value = scanner.nextInt();
                return value;
            } catch (Exception e) {
                System.out.print("Input error, please enter a number.");
                scanner.nextLine();
            }
        }
    }

    @Override
    public boolean nextBoolean(){
        boolean value;
        while(true){
            try{
                value = scanner.nextBoolean();
                return value;
            } catch (Exception e){
                System.out.print("Input error, please enter true/false.");
                scanner.nextLine();
            }
        }
    }
}
