import java.util.ArrayList;

public class Runner {

    public static void main(String[] args) {
//        try {
//            DBWriter.insertRecord("new_table2", "'Catrina', '2019-05-27'");
//        } catch (Exception e){
//            System.out.println("Failed to insert record");
//        }

        ArrayList<String> tableContents = DBReader.sendSelectSQL("select * from new_table2 " +
                "where dob > " + DateMaker.lastXDays(30) + ";");
        for (String thing : tableContents){
            System.out.print(thing);
        }
    }

}
