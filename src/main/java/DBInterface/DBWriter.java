package DBInterface;

import java.sql.SQLException;
import java.sql.Statement;

public class DBWriter {

    public static void insertRecord(String table, String values) throws SQLException {
        try {
            System.out.println(1);
            Statement stmnt = DBConnector.openConnection();
            System.out.println(2);
            stmnt.executeUpdate(getInsertionStringUsers(values));
            System.out.println("Query sent");
            DBConnector.closeConnection();
        } catch (Exception e){
            System.out.println("Failed to send query");
            System.out.println(e.getMessage());
            DBConnector.closeConnection();
        }
    }

    private static String getInsertionStringUsers(String values){
        return "insert into users (username, password) values (" + values + ")";
    }

}
