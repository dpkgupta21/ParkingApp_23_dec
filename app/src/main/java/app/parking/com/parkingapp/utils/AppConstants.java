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

    public static String APP_WEBSERVICE_API_URL = "http://104.131.119.107:3000/api/";

    public static String CREATE_ORDER = "create_order";
    public static String HOLD_ORDER_KEY = "hold_order";

    public static String PURCHASE_ORDER_KEY = "purchase_order";
    public static String ORDER_SUMMARY_KEY = "order_summary";
    public static String HOLD_ORDER_RESPONSE_KEY = "hold_order_response";
    public static String PURCHASE_ORDER_RESPONSE = "purchase_order_response";
}
