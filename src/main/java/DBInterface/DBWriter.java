package DBInterface;

import java.sql.Statement;

public class DBWriter {

    private static Statement stmnt;

    public static void insertRecord(String table, String values) throws Exception {
        stmnt = DBConnector.openConnection();
        switch (table){
            case "user":
                insertNewUser(values);
                break;
            case "entries":
                insertNewEntry(values);
                break;
        }
    }

    private static void insertNewUser(String values) throws Exception{
        try {
            stmnt.executeUpdate(getInsertionStringUsers(values));
        } catch (Exception e){
            throw new UserAlreadyExistsException("Cannot create new user, this user already exists");
        } finally {
            DBConnector.closeConnection();
        }
    }

    private static void insertNewEntry(String values) throws Exception{
        try {
            stmnt.executeUpdate(getInsertionStringEntry(values));
        } catch (Exception e){
            throw e;
        } finally {
            DBConnector.closeConnection();
        }
    }

    private static String getInsertionStringUsers(String values){
        return "insert into users (username, password) values (" + values + ")";
    }

    private static String getInsertionStringEntry(String values){
        return "insert into entries values (" + ")";
    }

}
