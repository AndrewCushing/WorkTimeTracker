package Businessware;

import DBInterface.DBReader;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.ArrayList;

public class GetFilteredProjectEntries extends ExchangeHandler {

    public static void handle(HttpExchange exchange){
        try {
            String[] requestBody = getValues(exchange);
            LogWriter.prepareLogs("Got " + requestBody.length + " values from front end");
            ArrayList<String> entries = DBReader.getFilteredEntries(requestBody[0], requestBody[1], requestBody[2], requestBody[3], requestBody[4]);
            if (entries.size()==0){
                respond("No entries found",exchange);
                exchange.close();
                return;
            }
            String responseText = entries.get(0);
            for (int i = 1 ; i < entries.size() ; i++){
                if(i%5==4){
                    continue;
                }
                responseText += ":"+entries.get(i);
            }
            respond(responseText ,exchange);
            exchange.close();
        } catch (IOException e) {
            LogWriter.prepareLogs("Error with input from front end");
            LogWriter.prepareLogs(e.getMessage()).run();
            exchange.close();
        }
    }

}
