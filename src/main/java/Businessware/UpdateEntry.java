package Businessware;

import DBInterface.DBWriter;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class UpdateEntry extends ExchangeHandler {

    public static void handle(HttpExchange exchange) throws IOException {
        String[] values = getValues(exchange);
        try {
            LogWriter.prepareLogs("Attempting to update record");
            DBWriter.updateEntry(values);
            LogWriter.prepareLogs("Record updated successfully");
            respond("1",exchange);
            LogWriter.prepareLogs("Response sent").run();

        } catch (Exception e){
            LogWriter.prepareLogs("Error while attempting to update record");
            LogWriter.prepareLogs(e.getMessage());
            respond("2", exchange);
            LogWriter.prepareLogs("Response sent").run();
        }
        exchange.close();
    }

}
