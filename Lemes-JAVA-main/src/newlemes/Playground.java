package newlemes;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Playground {

    public static void main(String[] args) {
        String pattern = "yyyy-MM-dd HH:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date()); //this time now
        String d = date.substring(0, 10);

        System.out.println(date); //with time
        System.out.println(d);

        System.out.println(d + " 12:00");

        // Convert Date to Calendar
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());

        c.add(Calendar.MONTH, 1);

        // Convert calendar back to Date
        Date currentDatePlusOne = c.getTime();

        System.out.println("Updated Date " + simpleDateFormat.format(currentDatePlusOne));
    }
}
