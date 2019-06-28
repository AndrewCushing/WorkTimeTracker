package Businessware;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class LogWriter implements Runnable{

    private static BufferedWriter writer;
    private static LogWriter logWriter;
    private static ArrayList<String> logList = new ArrayList<>();

    private LogWriter(){
        try {
            writer = new BufferedWriter(new FileWriter(Config.LOG_FILE_PATH, true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void destroyWriters(){
        writer = null;
        logWriter = null;
    }

    public static LogWriter prepareLogs(String logsToWrite){
        if (logWriter==null){
            logWriter = new LogWriter();
        }
        addToLog(logsToWrite);
        return logWriter;
    }

    private static void addToLog(String message){
        logList.add(message);
    }

    private static void printToLogs(){
        StringBuilder logs = new StringBuilder();
        String date = DateMaker.getCurrentDateTime();
        for (String log : logList){
            logs.append("\n");
            logs.append(date);
            logs.append(" ");
            logs.append(log);
        }
        try {
            writer.write(logs.toString());
            writer.close();
            destroyWriters();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        printToLogs();
    }
}
