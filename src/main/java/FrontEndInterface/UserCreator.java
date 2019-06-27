package FrontEndInterface;

import Businessware.LogWriter;
import DBInterface.DBWriter;
import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class UserCreator {

    private static Map<String, String> getParams(String rawParams){
        if (rawParams == null || rawParams == ""){
            return null;
        }
        String[] splitByAnd = rawParams.split("&");
        String[][] splitIntoParams = new String[splitByAnd.length][];
        for (int i = 0 ; i < splitByAnd.length ; i++){
            splitIntoParams[i] = splitByAnd[i].split("=");
        }
        Map<String, String> result = new HashMap<>();
        for (int i = 0 ; i < splitIntoParams.length ; i++){
            result.put(splitIntoParams[i][0], splitIntoParams[i][1]);
        }
        return result;
    }

    public static void handle(HttpExchange exchange) throws IOException {
//        Map<String, String> params = getParams(exchange.getRequestURI().getRawQuery());
        BufferedReader br = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
        String[] usernameAndPassword = br.readLine().split(":");
        try{
            DBWriter.insertRecord("users", "'" + usernameAndPassword[0] + "','" + usernameAndPassword[1] + "'");
            respond("User created", exchange);
        } catch (SQLException se){
            respond("Invalid username or password", exchange);
        } catch (Exception e){
            respond("Unable to create user\n"+e.getMessage(),exchange);
            System.out.println(e.getMessage());
        }
        exchange.close();
        LogWriter.prepareLogs("PUT request reached PUT handler class and was dealt with accordingly").run();
    }

    private static void respond(String responseText, HttpExchange exchange) throws IOException{
        exchange.sendResponseHeaders(200, responseText.getBytes().length);
        exchange.getResponseBody().write(responseText.getBytes());
        exchange.getResponseBody().flush();
    }

}
