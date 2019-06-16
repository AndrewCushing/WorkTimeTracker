import java.sql.SQLException;
import java.sql.Statement;

public class DBWriter {

    public static void insertRecord(String table, String values) throws SQLException {
        Statement stmnt = DBConnector.openConnection();
        stmnt.executeUpdate("insert into " + table + " values(" + values + ")");
        System.out.println("Query sent");
        DBConnector.closeConnection();
    }



}
