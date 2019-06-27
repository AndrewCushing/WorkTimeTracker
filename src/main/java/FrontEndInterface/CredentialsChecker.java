package FrontEndInterface;

import DBInterface.DBReader;
import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static FrontEndInterface.UserCreator.respond;

public class CredentialsChecker {

    public static void handle(HttpExchange exchange) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
        String[] usernameAndPassword = br.readLine().split(":");
        ArrayList<String> matchedUserPassword = DBReader.sendSelectSQL("select password from users where username='"+usernameAndPassword[0]+"';");
        if (matchedUserPassword.size()<2){
            respond("User does not exist.", exchange);
        } else if(matchedUserPassword.get(0).trim().equals(usernameAndPassword[1])){
            respond("Access granted.", exchange);
        } else {
            respond("Incorrect password.", exchange);
        }
        exchange.close();
    }

}
