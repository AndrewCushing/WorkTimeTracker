package DBInterface;

import Businessware.LogWriter;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DBReader {

    public static ArrayList<String> sendSelectSQL(String sql){
        if (!sql.substring(0,6).equalsIgnoreCase("select")){
            return null;
        }
        try{
            Statement stmt=DBConnector.openConnection();
            ResultSet rs=stmt.executeQuery(sql);
            LogWriter.prepareLogs("Select query sent").run();
            ArrayList<String> result = new ArrayList<>();
            putResults(result, rs);
            DBConnector.closeConnection();
            LogWriter.prepareLogs("Results returned to middleware").run();
            return result;
        } catch (Exception e){
            LogWriter.prepareLogs("Failed to send query").run();
            DBConnector.closeConnection();
            return null;
        }
    }

    public static ArrayList<String> getProjectList(String email){
        String userID = getUserIDFromEmail(email);
        return sendSelectSQL("select project from entries where user_id=" + userID + " group by project;");
    }

    private static void putResults(ArrayList<String> listToHoldResults, ResultSet setOfResults){
        try {
            while (setOfResults.next()) {
                try{
                    for (int i = 1 ; ; i++){
                        listToHoldResults.add(setOfResults.getString(i));
                    }
                } catch (Exception e){
                    listToHoldResults.add("\n");
                }
            }
        } catch (Exception e){}
    }

    private static String getUserIDFromEmail(String email){
        return DBReader.sendSelectSQL("select ID from users where username='" + email + "';").get(0);
    }

    public static ArrayList<String> getFilteredEntries(String email, String password, String startDate,
                                                              String endDate, String project){
        String selectionStatement = "select description, date, time, entry_id from entries where " +
                "user_id=(select id from users where username='" + email + "' and password='" + password +
                "') and date >= '" + startDate + "' and date <= '" + endDate + "' and project='" + project + "';";
        LogWriter.prepareLogs("Sent the following SQL to database: " + selectionStatement);
        return DBReader.sendSelectSQL(selectionStatement);
    }
}