package Businessware;

import DBInterface.DBReader;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.ArrayList;

public class GetAllEntries extends ExchangeHandler {

    public static void handle(HttpExchange exchange) throws IOException{
        String[] requestData = getValues(exchange);
        String email = requestData[0];
        String project = requestData[1];
        ArrayList<String> allEntries = DBReader.sendSelectSQL("select description, date, time, entry_id from entries " +
                "where user_id=(select ID from users where username='" + email + "') and project='" + project + "' order by date desc;");
        String responseString = allEntries.get(0);
        for (int i = 1 ; i < allEntries.size() ; i++){
            if (i % 5 == 4){
                continue;
            }
            responseString += ":" + allEntries.get(i);
        }
        respond(responseString,exchange);
        exchange.close();
    }

}
