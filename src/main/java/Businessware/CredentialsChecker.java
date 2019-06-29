package Businessware;

import DBInterface.DBReader;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.ArrayList;

import static Businessware.ExchangeHandler.getValues;
import static Businessware.UserCreator.respond;

public class CredentialsChecker {

    public static void handle(HttpExchange exchange) throws IOException {
        String[] usernameAndPassword = getValues(exchange);
        ArrayList<String> matchedUserPassword = DBReader.sendSelectSQL("select password from users where username='"+usernameAndPassword[0]+"';");
        if (matchedUserPassword.size()<2){
            respond("User does not exist.", exchange);
        } else if(matchedUserPassword.get(0).trim().equals(usernameAndPassword[1])){
            respond("Access granted.", exchange);
        } else {
            respond("Incorrect password.", exchange);
        }
        LogWriter.prepareLogs("Sent response for credential verification").run();
        exchange.close();
    }

}
