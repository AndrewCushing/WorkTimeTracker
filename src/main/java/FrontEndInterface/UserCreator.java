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
            String respText = "User created";
            exchange.sendResponseHeaders(200, respText.getBytes().length);
            exchange.getResponseBody().write(respText.getBytes());
            exchange.getResponseBody().flush();
        } catch (SQLException se){
            String respText = "Invalid username or password";
            exchange.sendResponseHeaders(200, respText.getBytes().length);
            exchange.getResponseBody().write(respText.getBytes());
            exchange.getResponseBody().flush();
        } catch (Exception e){
            String respText = "Unable to create user\n"+e.getMessage();
            exchange.sendResponseHeaders(200, respText.getBytes().length);
            exchange.getResponseBody().write(respText.getBytes());
            exchange.getResponseBody().flush();
            System.out.println(e.getMessage());
        }
        exchange.close();
        LogWriter.prepareLogs("PUT request reached PUT handler class and was dealt with accordingly").run();
    }

}
