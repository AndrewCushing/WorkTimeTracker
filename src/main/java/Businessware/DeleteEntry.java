package Businessware;

import DBInterface.DBWriter;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class DeleteEntry extends ExchangeHandler {

    public static void handle(HttpExchange exchange) throws IOException {
        LogWriter.prepareLogs("Received request to delete an entry. Checking credentials first.");
        String[] info = getValues(exchange);
        String email = info[0];
        String hashedPass = info[1];
        String entryID = info[2];
        LogWriter.prepareLogs("Attempting to delete record");
        try {
            DBWriter.deleteEntry(entryID, email, hashedPass);
            LogWriter.prepareLogs("Entry deleted successfully").run();
            respond("1",exchange);
        } catch (Exception e) {
            LogWriter.prepareLogs("Unable to remove record from database");
            LogWriter.prepareLogs(e.getMessage()).run();
            respond("2",exchange);
        }
        LogWriter.prepareLogs("Response sent. Closing connection").run();
        exchange.close();
    }
}