package app.parking.com.parkingapp;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;

import java.util.Calendar;

import app.parking.com.parkingapp.navigationDrawer.UserNavigationDrawerActivity;
import app.parking.com.parkingapp.preferences.ParkingPreference;

import static app.parking.com.parkingapp.CommonUtilities.SENDER_ID;
import static app.parking.com.parkingapp.CommonUtilities.displayMessage;


public class GCMIntentService extends GCMBaseIntentService {

    private static final String TAG = "GCMIntentService";

    public GCMIntentService() {
        super(SENDER_ID);
    }

    /**
     * Method called on device registered
     **/
    @Override
    protected void onRegistered(Context context, String registrationId) {
        Log.i(TAG, "Device registered: regId = " + registrationId);
        ParkingPreference.setPushRegistrationId(context, registrationId);
        displayMessage(context, "Your device registred with GCM");

    }

    /**
     * Method called on device un registred
     */
    @Override
    protected void onUnregistered(Context context, String registrationId) {
        Log.i(TAG, "Device unregistered");
        displayMessage(context, getString(R.string.gcm_unregistered));
        //ServerUtilities.unregister(context, registrationId);
    }

    /**
     * Method called on Receiving a new message
     */
    @Override
    protected void onMessage(Context context, Intent intent) {
        String message = intent.getExtras().getString(CommonUtilities.EXTRA_MESSAGE);
        Log.i(TAG, "Received message :" + message);

        displayMessage(context, message);
        // notifies user

        generateNotification(context, message);

    }

    /**
     * Method called on receiving a deleted message
     */
    @Override
    protected void onDeletedMessages(Context context, int total) {
        Log.i(TAG, "Received deleted messages notification");
        String message = getString(R.string.gcm_deleted, total);
        displayMessage(context, message);
        // notifies user
        generateNotification(context, message);
    }

    /**
     * Method called on Error
     */
    @Override
    public void onError(Context context, String errorId) {
        Log.i(TAG, "Received error: " + errorId);
        displayMessage(context, getString(R.string.gcm_error, errorId));
    }

    @Override
    protected boolean onRecoverableError(Context context, String errorId) {
        // log message
        Log.i(TAG, "Received recoverable error: " + errorId);
        displayMessage(context,
                getString(R.string.gcm_recoverable_error, errorId));
        return super.onRecoverableError(context, errorId);
    }

    /**
     * Issues a notification to inform the user that server has sent a message.
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private static void generateNotification(Context context, String message) {
//        int icon = R.mipmap.ic_launcher;
//        long when = System.currentTimeMillis();
//        NotificationManager notificationManager = (NotificationManager) context
//                .getSystemService(Context.NOTIFICATION_SERVICE);
//        // Notification notification = new Notification(icon, message, when);
//
//        String title = message;
//
//        Intent notificationIntent = new Intent(context, LandingScreen.class);
//        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
//                Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        PendingIntent intent = PendingIntent.getActivity(context, 0,
//                notificationIntent, 0);
//        //notification.setLatestEventInfo(context, title, message, intent);
//        //notification.flags |= Notification.FLAG_AUTO_CANCEL;
//
//        // Play default notification sound
//        //  notification.defaults |= Notification.DEFAULT_SOUND;
//
//        // Vibrate if vibrate is enabled
//        //  notification.defaults |= Notification.DEFAULT_VIBRATE;
//
//
//        Notification notification = null;
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
//            notification = new Notification();
//            notification.icon = R.mipmap.ic_launcher;
//            try {
//                Method deprecatedMethod = notification.getClass().getMethod("setLatestEventInfo", Context.class, CharSequence.class, CharSequence.class, PendingIntent.class);
//                deprecatedMethod.invoke(notification, context, title, message, intent);
//            } catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException
//                    | InvocationTargetException e) {
//                Log.w(TAG, "Method not found", e);
//            }
//        } else {
//            // Use new API
//            Notification.Builder builder = new Notification.Builder(context)
//                    .setContentIntent(intent)
//                    .setSmallIcon(R.mipmap.ic_launcher)
//                    .setContentTitle(title).setContentText(message);
//            notification = builder.build();
//        }
//
//        notification.flags |= Notification.FLAG_AUTO_CANCEL;
//
//        // Play default notification sound
//        notification.defaults |= Notification.DEFAULT_SOUND;
//
//        // Vibrate if vibrate is enabled
//        notification.defaults |= Notification.DEFAULT_VIBRATE;
//        notificationManager.notify(0, notification);


        if (message != null) {
            int i = (int) Calendar.getInstance().getTimeInMillis();
            int icon = R.mipmap.ic_launcher;
            long when = System.currentTimeMillis();
            NotificationManager notificationManager = (NotificationManager) context
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            builder.setSmallIcon(icon);
            builder.setContentText(message);
            builder.setWhen(when);
            builder.setColor(Color.parseColor("#1f667e"));
            String title = context.getString(R.string.app_name);
            builder.setContentTitle(title);
            Intent notificationIntent = null;
            PendingIntent intent = null;
            builder.setContentText(message);
            builder.setStyle(new NotificationCompat.BigTextStyle().bigText(message));
            notificationIntent = new Intent(context,
                    UserNavigationDrawerActivity.class);
            notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            notificationIntent.putExtra("fragmentPos", 2);
            intent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
            builder.setContentIntent(intent);

            Notification notification = builder.build();
            notification.flags |= Notification.FLAG_AUTO_CANCEL;

            // Play default notification sound
            notification.defaults |= Notification.DEFAULT_SOUND;

            // Vibrate if vibrate is enabled
            notification.defaults |= Notification.DEFAULT_VIBRATE;
            notificationManager.notify(i, notification);


        }
    }

}