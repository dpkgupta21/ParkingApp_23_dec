package app.parking.com.parkingapp.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.xml.datatype.Duration;

public class HelpMe {

    public HelpMe() {
        Calendar rightNow = Calendar.getInstance();
    }

    //private static final String SERVER_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    private static final String REQUEST_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    private static final String SERVER_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String DISPLAY_DATE_FORMAT = "dd MMM, yyyy";
    public static final String SELECT_DATE_FORMAT = "dd-MM-yyyy";
    public static final String DISPLAY_DATE_TIME_FORMAT = "dd MMM, yyyy HH:mm a";

    private static final String DISPLAY_TIME_FORMAT = "HH:mm a";

    private static DateFormat currentFormat = new SimpleDateFormat(SERVER_DATE_TIME_FORMAT);


    public static String getDurationTime(String startTime, String endTime) {
        //String startDateString = "06/27/2007";

        String diff = null;
        try {
            DateFormat df = new SimpleDateFormat(REQUEST_DATE_FORMAT);
            Date startDate = df.parse(startTime);
            Date endDate = df.parse(endTime);
            long timeDiff = Math.abs(endDate.getTime() - startDate.getTime());
            diff = String.format("%d hour(s) %d min(s)", TimeUnit.MILLISECONDS.toHours(timeDiff),
                    TimeUnit.MILLISECONDS.toMinutes(timeDiff) -
                            TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeDiff)));


        } catch (ParseException e) {
            e.printStackTrace();
        }

        return diff;

    }


    public static String getDisplayDate(String dateString) {
        try {
            DateFormat df = new SimpleDateFormat(DISPLAY_DATE_FORMAT);
            String newDateString = null;

            try {
                Date startDate = currentFormat.parse(dateString);
                newDateString = df.format(startDate);
                System.out.println(newDateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return newDateString;
        }catch (Exception e){
            e.printStackTrace();
        }

        return "";

    }


    public static String getDisplayTime(String dateString) {

        try {
            DateFormat df = new SimpleDateFormat(DISPLAY_TIME_FORMAT);
            String newDateString = null;

            try {
                Date startDate = currentFormat.parse(dateString);
                newDateString = df.format(startDate);
                System.out.println(newDateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return newDateString;
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";

    }

    public static Date getDateTimeObject(String dateString) {

        DateFormat df = new SimpleDateFormat(REQUEST_DATE_FORMAT);
        Date startDate = null;

        try {
            startDate = currentFormat.parse(dateString);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return startDate;

    }

    public static String getPickDropDisplayDateTime(String dateString) {

        DateFormat df = new SimpleDateFormat(REQUEST_DATE_FORMAT);
        DateFormat currentDf = new SimpleDateFormat(DISPLAY_DATE_TIME_FORMAT);
        String newDateString = null;

        try {
            Date startDate = df.parse(dateString);
            newDateString = currentDf.format(startDate);
            System.out.println(newDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newDateString;

    }


    public static String getFlightDisplayDateTime(String dateString) {

        DateFormat df = new SimpleDateFormat(SERVER_DATE_TIME_FORMAT);
        DateFormat currentDf = new SimpleDateFormat(DISPLAY_DATE_TIME_FORMAT);
        String newDateString = null;

        try {
            Date startDate = df.parse(dateString);
            newDateString = currentDf.format(startDate);
            System.out.println(newDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newDateString;

    }


    public static void pullDb(Context context) {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "data/data/" + context.getPackageName() + "/databases/memories.db";
                String backupDBPath = "memories.sqlite";
                File currentDB = new File(currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
