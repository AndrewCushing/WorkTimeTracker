import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DBReader {

    static ArrayList<String> sendSelectSQL(String sql){
        if (!sql.substring(0,6).equalsIgnoreCase("select")){
            return null;
        }
        try{
            Statement stmt=DBConnector.openConnection();
            ResultSet rs=stmt.executeQuery(sql);
            System.out.println("Query sent");
            ArrayList<String> result = new ArrayList<>();
            putResults(result, rs);
            DBConnector.closeConnection();
            return result;
        } catch (Exception e){
            System.out.println("Failed to send query");
            DBConnector.closeConnection();
            return null;
        }
    }

    private static void putResults(ArrayList<String> listToHoldResults, ResultSet setOfResults){
        try {
            while (setOfResults.next()) {
                try{
                    for (int i = 1 ; ; i++){
                        listToHoldResults.add(setOfResults.getString(i)+" ");
                    }
                } catch (Exception e){
                    listToHoldResults.add("\n");
                }
            }
        } catch (Exception e){}
    }
}