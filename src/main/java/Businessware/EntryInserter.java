package Businessware;

import DBInterface.DBWriter;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class EntryInserter extends ExchangeHandler {

    public static void handle(HttpExchange exchange) throws IOException {
        String[] hashedPassAndValues = getValues(exchange);
        LogWriter.prepareLogs("Received the following values from front end: " + hashedPassAndValues[0] + " " +
                hashedPassAndValues[1] + " " + hashedPassAndValues[2] + " " + hashedPassAndValues[3] + " " +
                hashedPassAndValues[4]);

        try {
            DBWriter.insertRecord("entries",hashedPassAndValues);
            LogWriter.prepareLogs("New entry inserted into database").run();
            respond("Entry inserted into database", exchange);
        } catch (Exception e) {
            LogWriter.prepareLogs("Error inserting new entry into database. Please check that the database is up and running.");
            LogWriter.prepareLogs(e.getMessage()).run();
            respond("Error while trying to insert entry into database", exchange);
        }
    }

}
