import java.util.ArrayList;

public class Runner {


    public static void main(String[] args) {
        try {
            DBWriter.insertRecord("Martin", "1985-7-20");
        } catch (Exception e){
            System.out.println("Failed to insert record");
        }

        ArrayList<String> tableContents = DBReader.sendSelectSQL("Select * from new_table2;");
        for (String thing : tableContents){
            System.out.print(thing);
        }
    }

}
