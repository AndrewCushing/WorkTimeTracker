package FrontEndInterface;

import Businessware.LogWriter;
import DBInterface.DBWriter;
import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class EntryInserter {

    public static void handle(HttpExchange exchange) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
        String[] valuesArr = br.readLine().split(":");
        LogWriter.prepareLogs("Received the following values from front end: " + valuesArr[0] + valuesArr[1] + valuesArr[2] + valuesArr[3]);
        try {
            DBWriter.insertRecord("entries", valuesArr[0] + ",'" + valuesArr[1] + "','" + valuesArr[2] + "'," + valuesArr[3]);
        } catch (Exception e) {
            LogWriter.prepareLogs("Error inserting new entry into database. Please check that the database is up and running.");
        }
    }

}
