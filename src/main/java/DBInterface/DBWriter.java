package DBInterface;

import java.sql.Statement;

public class DBWriter {

    public static void insertRecord(String table, String values) throws Exception {
        Statement stmnt;
        stmnt = DBConnector.openConnection();
        try {
            stmnt.executeUpdate(getInsertionStringUsers(values));
        } catch (Exception e){
            throw new UserAlreadyExistsException("Cannot create new user, this user already exists");
        } finally {
            DBConnector.closeConnection();
        }
    }

    private static String getInsertionStringUsers(String values){
        return "insert into users (username, password) values (" + values + ")";
    }

}
