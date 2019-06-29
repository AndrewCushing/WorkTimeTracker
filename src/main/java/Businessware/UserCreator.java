package Businessware;

import DBInterface.DBWriter;
import DBInterface.UserAlreadyExistsException;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.sql.SQLException;

public class UserCreator extends ExchangeHandler {

    public static void handle(HttpExchange exchange) throws IOException {
        String[] usernameAndPassword = getValues(exchange);
        try{
            LogWriter.prepareLogs("Attempting to create new user in database");
            DBWriter.insertRecord("users", usernameAndPassword);
            LogWriter.prepareLogs("User " + usernameAndPassword[0] + " inserted into database successfully");
            respond("1", exchange);
            LogWriter.prepareLogs("Sent appropriate response");
        } catch (SQLException se){
            LogWriter.prepareLogs("User " + usernameAndPassword[0] + " was not inserted into database as the username or password is invalid");
            respond("2", exchange);
            LogWriter.prepareLogs("Sent appropriate response");
        } catch (UserAlreadyExistsException e){
            LogWriter.prepareLogs("User " + usernameAndPassword[0] + " was not inserted into database as this user already exists");
            respond("3", exchange);
        } catch (Exception e){
            LogWriter.prepareLogs("Error inserting user " + usernameAndPassword[0] + " into database. Perhaps user already exists?");
            respond("4"+e.getMessage(),exchange);
            System.out.println(e.getMessage());
            LogWriter.prepareLogs("Sent appropriate response");
        }
        exchange.close();
        LogWriter.prepareLogs("PUT request reached PUT handler class and was dealt with accordingly").run();
    }

}
