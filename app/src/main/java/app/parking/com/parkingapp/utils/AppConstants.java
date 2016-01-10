package app.parking.com.parkingapp.utils;

import android.graphics.Bitmap;
import android.os.Environment;

/**
 * Created by Harish on 12/15/2015.
 */
public class AppConstants {


    public static int ONE_SECOND = 1000;
    public static String STORED_IMAGE_PATH = "ParkingApp";
    public static String CLICKED_IMAGE_PATH = "imagepath";
    public static String SERVICE = "service";
    public static String SNAPSHOT_DIRECTORY_PATH = Environment.getExternalStorageDirectory()
            + "/" + AppConstants.STORED_IMAGE_PATH + "/";
    public static Bitmap fullScreenBitmap = null;
    public static String imageName = "";
}
