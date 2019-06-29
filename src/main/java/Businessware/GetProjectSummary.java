package Businessware;

import DBInterface.DBReader;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.ArrayList;

public class GetProjectSummary extends ExchangeHandler {

    public static void handle(HttpExchange exchange) throws IOException {
        String email = getValues(exchange)[0];
        String project = getValues(exchange)[1];
        String summaryMode = getValues(exchange)[2];

        ArrayList<String> searchResults = DBReader.sendSelectSQL("select description, sum(time) from entries where" +
                " user_id=(select ID from users where username='" + email + "') and project='test project'" +
                " group by description;");

    }

}
