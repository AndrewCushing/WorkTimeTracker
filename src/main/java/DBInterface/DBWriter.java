package DBInterface;

import Businessware.LogWriter;

import java.sql.SQLException;
import java.sql.Statement;

public class DBWriter {

    public static void insertRecord(String table, String[] hashedPassAndValues) throws Exception {
        switch (table){
            case "users":
                insertNewUser(hashedPassAndValues);
                break;
            case "entries":
                insertNewEntry(hashedPassAndValues);
                break;
        }
    }

    public static void deleteEntry(String entryID, String email, String hashedPass) throws SQLException {
        LogWriter.prepareLogs("About to attempt deletion of record").run();
        Statement stmnt = DBConnector.openConnection();
        stmnt.executeUpdate("delete from entries where entry_id=" + entryID + " and user_id=(select user_id from" +
                " users where username='" + email + "' and password='" + hashedPass + "');");
        DBConnector.closeConnection();
    }

    public static void updateEntry(String[] values) throws SQLException{
        Statement stmnt = DBConnector.openConnection();
        LogWriter.prepareLogs("Received statement object from DB connection").run();
        stmnt.executeUpdate("UPDATE entries SET project = '" + values[3] + "', " +
                "description = '" + values[4] + "', date = '" + values[5] + "', time = " + values[6] + " WHERE entry_id=" +
                values[0] + " and user_id=" + "(select ID from users where username='" + values[1] + "' and password = '" + values[2] + "');");
        DBConnector.closeConnection();
    }

    private static void insertNewEntry(String[] hashedPassAndValues) throws Exception{
        try {
            LogWriter.prepareLogs("Attempting to retrieve user ID associated with current password");

            LogWriter.prepareLogs("Successfully retrieved user ID");
            LogWriter.prepareLogs("Attempting to insert entry");
            String userID = getUserIDFromEmail(hashedPassAndValues[0]);
            Statement stmnt = DBConnector.openConnection();
            stmnt.executeUpdate(getInsertionStringEntry(userID, hashedPassAndValues));
            LogWriter.prepareLogs("Successfully inserted new entry into database");
            DBConnector.closeConnection();
            LogWriter.prepareLogs("Successfully closed connection with database").run();
        } catch (Exception e){
            throw e;
        } finally {
            DBConnector.closeConnection();
        }
    }

    private static void insertNewUser(String[] hashedPassAndValues) throws Exception{
        try {
            Statement stmnt = DBConnector.openConnection();
            stmnt.executeUpdate(getInsertionStringUsers(hashedPassAndValues[0],hashedPassAndValues[1]));
            stmnt.close();
            LogWriter.prepareLogs("Successfully closed connection with database").run();
        } catch (Exception e){
            LogWriter.prepareLogs("Unable to insert new user into database");
            throw e;
        } finally {
            DBConnector.closeConnection();
        }
    }

    private static String getUserIDFromEmail(String email){
        return DBReader.sendSelectSQL("select ID from users where username='" + email + "';").get(0);
    }

    private static String getInsertionStringUsers(String email, String password){
        return "insert into users (username, password) values ('" + email + "','" + password + "');";
    }

    private static String getInsertionStringEntry(String userID, String[] values){
        return "insert into entries (user_id, project, description, date, time) values (" + userID + ",'" + values[1] + "','" +  values[2] + "','" + values[3] + "'," + values[4] + ");";
    }

}
