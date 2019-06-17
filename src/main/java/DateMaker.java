import java.util.Calendar;
import java.util.GregorianCalendar;

public class DateMaker {

    public static void main(String[] args) {
        System.out.println(last30Days());
    }

    public static String last30Days(){
        return lastXDays(30);
    }

    public static String lastWeek(){
        return lastXDays(7);
    }

    public static String last2Weeks(){
        return lastXDays(14);
    }

    public static String last2Months(){
        return lastXMonths(2);
    }

    public static String last3Months(){
        return lastXMonths(3);
    }

    public static String last6Months(){
        return lastXMonths(6);
    }

    public static String lastYear(){
        return lastXMonths(12);
    }

    private static String lastXDays(int xDays){
        Calendar myCal = new GregorianCalendar();
        myCal.set(Calendar.DAY_OF_MONTH,myCal.get(Calendar.DAY_OF_MONTH)-xDays);
        return dateToString(myCal);
    }

    private static String lastXMonths(int xMonths){
        Calendar myCal = new GregorianCalendar();
        myCal.set(Calendar.DAY_OF_MONTH,myCal.get(Calendar.MONTH)-xMonths);
        return dateToString(myCal);
    }

    private static String dateToString(Calendar inputCal){
        StringBuilder result = new StringBuilder();
        result.append("'" + inputCal.get(Calendar.YEAR));
        result.append("-" + (inputCal.get(Calendar.MONTH) + 1));
        result.append("-" + inputCal.get(Calendar.DAY_OF_MONTH) + "'");
        return result.toString();
    }

}
