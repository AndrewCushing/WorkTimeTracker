import java.util.ArrayList;

public class Runner {


    public static void main(String[] args) {
        try {
            DBWriter.insertRecord("Catrina", "1978-3-26");
        } catch (Exception e){
            System.out.println("Failed to insert record");
        }

        ArrayList<String> tableContents = DBReader.sendSelectSQL("Select * from new_table2;");
        for (String thing : tableContents){
            System.out.print(thing);
        }
    }

}
