package Businessware;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class LogWriter {

    private static BufferedWriter writer;
    private static StringBuilder currentLogs = new StringBuilder();
    private static boolean canMake = true;

    private LogWriter(){
        try {
            writer = new BufferedWriter(new FileWriter("C:\\Users\\Andy\\Documents\\myTestLogs.txt", true));
            canMake = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static LogWriter makeLogWriter(){
        if (canMake){
            return new LogWriter();
        } else {
            return null;
        }
    }

    public static void addToLog(String message){
        currentLogs.append("\n" + DateMaker.getCurrentDateTime() + " " + message);
    }

    public static void printToLogs(){
        try {
            writer.write(currentLogs.toString());
            currentLogs = new StringBuilder();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
