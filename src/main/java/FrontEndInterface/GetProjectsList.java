package FrontEndInterface;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.ArrayList;

import static DBInterface.DBReader.getProjectList;

public class GetProjectsList extends ExchangeHandler{

    public static void handle(HttpExchange exchange) throws IOException {
        ArrayList<String> listOfProjects = getProjectList(getValues(exchange)[0]);
    }

}
