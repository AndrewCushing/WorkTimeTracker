import javax.swing.plaf.nimbus.State;
import java.sql.SQLException;
import java.sql.Statement;
import java.security.PublicKey;

public class DBWriter {

    public static void insertRecord(String name, String date) throws SQLException {
        Statement stmnt = DBConnector.openConnection();
        stmnt.executeUpdate("insert into new_table2 values('" + name + "','" + date + "')");
        System.out.println("Query sent");
        DBConnector.closeConnection();
    }

}
