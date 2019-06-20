import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class LogWriter {

    BufferedWriter writer;
    StringBuilder currentLogs = new StringBuilder();

    public LogWriter(){
        try {
            writer = new BufferedWriter(new FileWriter("C:\\Users\\Andy\\Documents\\myTestLogs.txt", true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addToLog(String message){
        currentLogs.append("\n" + DateMaker.getCurrentDateTime() + " " + message);
    }

    public void printToLogs(){
        try {
            writer.write(currentLogs.toString());
            currentLogs = new StringBuilder();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
