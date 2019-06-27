package DBInterface;

import Businessware.Config;
import Businessware.LogWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnector {

    private static Connection con;
    private static Statement stmt;
    private static boolean connected = false;

    static Statement openConnection(){
        if (connected){
            LogWriter.prepareLogs("Already connected to db, please close previous connection before trying to open a new one.");
            return stmt;
        }
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            LogWriter.prepareLogs("Did class thing. About to attempt to connect to database");
            try {
                con = DriverManager.getConnection(Config.DBLOCATION, "root", "password");
                LogWriter.prepareLogs("Successfully initiated connection with database");
            } catch (Exception e){
                LogWriter.prepareLogs("Failed to initial connection with database");
            }
            stmt=con.createStatement();
            connected = true;
            return stmt;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    static void closeConnection(){
        try {
            con.close();
            connected = false;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to close connection with DB");
        }

    }

}
