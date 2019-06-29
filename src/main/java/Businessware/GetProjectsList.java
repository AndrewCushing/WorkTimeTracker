package Businessware;

import DBInterface.DBReader;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.ArrayList;

public class GetProjectsList extends ExchangeHandler{

    public static void handle(HttpExchange exchange) throws IOException {
        String email = getValues(exchange)[0];
        LogWriter.prepareLogs("About to send query to get list of projects from database");
        ArrayList<String> listOfProjects = DBReader.getProjectList(email);
        if (listOfProjects == null || listOfProjects.size()==0) {
            LogWriter.prepareLogs("No projects found for user " + email).run();
            respond(exchange, 204);
        } else {
            LogWriter.prepareLogs("Got a list of projects back for user " + email);
            String response = listOfProjects.get(0);
            for (int i = 2 ; i < listOfProjects.size() ; i+=2){
                response += ":" + listOfProjects.get(i);
            }
            respond(response,exchange);
            exchange.close();
            LogWriter.prepareLogs("Send list of projects back to front end").run();
        }
    }

}
