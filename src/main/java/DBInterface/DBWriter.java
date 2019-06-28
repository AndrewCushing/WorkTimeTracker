package DBInterface;

import Businessware.LogWriter;

import java.sql.Statement;

public class DBWriter {

    public static void insertRecord(String table, String[] hashedPassAndValues) throws Exception {

        switch (table){
            case "user":
                insertNewUser(hashedPassAndValues);
                break;
            case "entries":
                insertNewEntry(hashedPassAndValues);
                break;
        }
    }

    private static void insertNewEntry(String[] hashedPassAndValues) throws Exception{
        try {
            LogWriter.prepareLogs("Attempting to retrieve user ID associated with current password");
            String userID = DBReader.sendSelectSQL("select ID from users where password='" + hashedPassAndValues[0] + "';").get(0);
            LogWriter.prepareLogs("Successfully retrieved user ID");
            LogWriter.prepareLogs("Attempting to insert entry");
            Statement stmnt = DBConnector.openConnection();
            stmnt.executeUpdate(getInsertionStringEntry(userID, hashedPassAndValues));
            LogWriter.prepareLogs("Successfully inserted new entry into database");
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
        } catch (Exception e){
            LogWriter.prepareLogs("Unable to insert new user into database");
            throw e;
        } finally {
            DBConnector.closeConnection();
        }
    }

    private static String getInsertionStringUsers(String email, String password){
        return "insert into users (username, password) values ('" + email + "','" + password + "');";
    }

    private static String getInsertionStringEntry(String userID, String[] values){
        return "insert into entries values (" + userID + ",'" + values[1] + "','" +  values[2] + "','" + values[3] + "'," + values[4] + ");";
    }

}
