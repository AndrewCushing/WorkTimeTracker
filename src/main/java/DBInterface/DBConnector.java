package DBInterface;

import Businessware.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnector {

    private static Connection con;
    private static boolean connected = false;

    static Statement openConnection(){
        if (connected){
            System.out.println("Already connected to db, please close previous connection before tring to open a new one.");
            return null;
        }
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Did class thing");
            con= DriverManager.getConnection(Config.DBLOCATION,"root","password");
            System.out.println("Connection canMake");
            Statement stmt=con.createStatement();
            System.out.println("Statement canMake");
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
