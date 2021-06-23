package src.utils;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtilsNinjaAutomatedTest {


    static public int getHoursUntilTargetTime(int targetHH) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        return hour < targetHH ? targetHH - hour : targetHH - hour + 24;
    }


    static public int getMinutesUntilTargetTime(int targetHH , int tragetMM ) {

        Calendar calendar = Calendar.getInstance();
        int currentHH = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMM = calendar.get(Calendar.MINUTE);
        int currentSS = calendar.get(Calendar.SECOND);

        int currentTime = 60*currentHH + currentMM;
        int targetTime  = 60*targetHH + tragetMM;

        int resultMM = targetTime - currentTime;

        if (  targetTime <= currentTime){
            // past time
            resultMM += 60*24;
        }

        return resultMM;



    }


    static public long getSecondsUntilTargetTime(int targetHH , int tragetMM, int targetSS ) {

        Calendar calendar = Calendar.getInstance();
        int currentHH = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMM = calendar.get(Calendar.MINUTE);
        int currentSS = calendar.get(Calendar.SECOND);

        long currentTime = 3600*currentHH + 60*currentMM + currentSS;
        long targetTime  = 3600*targetHH  + 60*tragetMM  + targetSS;

        long result = targetTime - currentTime;

        if (  targetTime <= currentTime){
            // target past time + add one day
            result += 3600*24;
        }

        return result;



    }


    static public Calendar getNowPlusDelayInSeconds(long delayInSeconds ) {

        //System.out.println("getNowPlusDelayInSeconds : delayInSeconds " + delayInSeconds);

        Calendar calendar = Calendar.getInstance();
        //System.out.println("Current Date = " + calendar.getTime());

        calendar.add(Calendar.SECOND, (int) delayInSeconds);
        //System.out.println("Updated Date = " + calendar.getTime());

        return calendar;

    }

    static public String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }
}


