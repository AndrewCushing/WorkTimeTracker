import java.util.Calendar;
import java.util.GregorianCalendar;

public class DateMaker {

    public static void main(String[] args) {
        System.out.println(getCurrentDateTime());
    }

    public static String getCurrentDateTime(){
        Calendar myCal = new GregorianCalendar();
        StringBuilder result = new StringBuilder();
        result.append(myCal.get(Calendar.YEAR));
        result.append("-" + (myCal.get(Calendar.MONTH) + 1));
        result.append("-" + myCal.get(Calendar.DAY_OF_MONTH));
        result.append(" " + myCal.get(Calendar.HOUR_OF_DAY));
        String min = ""+myCal.get(Calendar.MINUTE);
        if (min.length()==1){
            min = "0"+min;
        }
        result.append(":" + min);
        return result.toString();
    }

    public static String lastXDays(int xDays){
        Calendar myCal = new GregorianCalendar();
        myCal.set(Calendar.DAY_OF_MONTH,myCal.get(Calendar.DAY_OF_MONTH)-xDays);
        return dateToString(myCal);
    }

    public static String lastXMonths(int xMonths){
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
