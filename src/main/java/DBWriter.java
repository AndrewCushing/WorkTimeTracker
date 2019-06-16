import java.sql.SQLException;
import java.sql.Statement;

public class DBWriter {

    public static void insertRecord(String table, String values) throws SQLException {
        try {
            Statement stmnt = DBConnector.openConnection();
            stmnt.executeUpdate("insert into " + table + " values(" + values + ")");
            System.out.println("Query sent");
            DBConnector.closeConnection();
        } catch (Exception e){
            System.out.println("Failed to send query");
            DBConnector.closeConnection();
        }
    }



}
