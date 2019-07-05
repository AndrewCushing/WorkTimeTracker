package Businessware;

import DBInterface.DBReader;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.ArrayList;

public class GetProjectSummary extends ExchangeHandler {

    public static void handle(HttpExchange exchange, boolean filtered) throws IOException {
        String[] requestBody = new String[0];
        try {
            requestBody = getValues(exchange);
        } catch (Exception e){
            LogWriter.prepareLogs("Error with input from front end. Unable to continue with request.").run();
            LogWriter.prepareLogs(e.getCause().getMessage()).run();
            exchange.close();
            return;
        }
        ArrayList<String> searchResults = DBReader.sendSelectSQL(getSQLString(filtered, requestBody));
        LogWriter.prepareLogs("Successfully queried database for matching records").run();
        String responseString = searchResults.get(0) + ":" + searchResults.get(1);
        for (int i = 3 ; i < searchResults.size() ; i+=3){
            responseString += ":" + searchResults.get(i) + ":" + searchResults.get(i+1);
        }
        respond(responseString,exchange);
        LogWriter.prepareLogs("Sent response containing summary of project activity");
        exchange.close();
    }

    private static String getSQLString(boolean filtered, String[] requestBody) {
        if (filtered){
            return "select description, sum(time) from entries where" +
                    " user_id=(select ID from users where username='" + requestBody[0] + "' and password='"+requestBody[1]+
                    "') and project='" + requestBody[4] + "' and date>='"+requestBody[2]+"' and date<='"+requestBody[3]+
                    "' group by description;";
        } else {
             return "select description, sum(time) from entries where" + " user_id=(select ID from users where username='"
                     + requestBody[0] + "') and project='" + requestBody[1] + "' group by description;";
        }
    }
}
