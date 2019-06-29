package Businessware;

import DBInterface.DBReader;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.ArrayList;

public class GetProjectSummary extends ExchangeHandler {

    public static void handle(HttpExchange exchange) throws IOException {
        String email = "";
        String project = "";
        try {
            String[] body = getValues(exchange);
            email = body[0];
            project = body[1];
        } catch (Exception e){
            LogWriter.prepareLogs("Error with input from front end. Unable to continue with request.").run();
            LogWriter.prepareLogs(e.getCause().getMessage()).run();
            exchange.close();
            return;
        }
        LogWriter.prepareLogs("Received email: " + email + " and project: " + project).run();
//        String summaryMode = getValues(exchange)[2];
        ArrayList<String> searchResults = DBReader.sendSelectSQL("select description, sum(time) from entries where" +
                " user_id=(select ID from users where username='" + email + "') and project='" + project + "'" +
                " group by description;");
        LogWriter.prepareLogs("Successfully queried database for matching records").run();
        String responseString = searchResults.get(0) + ":" + searchResults.get(1);
        for (int i =3 ; i < searchResults.size() ; i+=3){
            responseString += ":" + searchResults.get(i) + ":" + searchResults.get(i+1);
        }
        respond(responseString,exchange);
        LogWriter.prepareLogs("Sent response containing summary of project activity");
        exchange.close();
    }

}
