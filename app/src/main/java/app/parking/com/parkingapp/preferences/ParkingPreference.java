package app.parking.com.parkingapp.preferences;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;

public class ParkingPreference {


    public static final String PREF_NAME = "PARKING_PREFERENCES";


    public static final String EMAIL_ID = "emailId";
    public static final String KEY_AUTHTOKEN = "authentication_token";
    public static final String USERID = "userid";
    public static final String IS_LOGIN = "is_login";
    public static final String PWD = "password";
    public static final String PUSH_REGISTRATION_ID = "push_reg_id";


    public static void setPassword(Context context, String password) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PWD, String.valueOf(password));
        editor.apply();
    }

    public static String getPassword(Context context) {
        String pushRegistrationId = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).
                getString(
                        PWD, "");
        return pushRegistrationId;

    }

    public static void setKeyAuthtoken(Context context, String email) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_AUTHTOKEN, String.valueOf(email));
        editor.apply();
    }

    public static String getKeyAuthtoken(Context context) {
        String pushRegistrationId = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).
                getString(
                        KEY_AUTHTOKEN, "");
        return pushRegistrationId;

    }

    public static void setUserId(Context context, String email) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(USERID, String.valueOf(email));
        editor.apply();
    }

    public static String getUserid(Context context) {
        String pushRegistrationId = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).
                getString(
                        USERID, "");
        return pushRegistrationId;

    }

    public static void setEmailId(Context context, String email) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(EMAIL_ID, String.valueOf(email));
        editor.apply();
    }

    public static String getEmailId(Context context) {
        String pushRegistrationId = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).
                getString(
                        EMAIL_ID, "");
        return pushRegistrationId;

    }


    public static void setIsLogin(Context context, boolean isLogin) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(IS_LOGIN, isLogin);
        editor.apply();
    }

    public static String isLogin(Context context) {
        String pushRegistrationId = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).
                getString(
                        IS_LOGIN, "");
        return pushRegistrationId;

    }


    public static String getPushRegistrationId(Context context) {
        String pushRegistrationId = context.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE).getString(
                PUSH_REGISTRATION_ID, "");
        return pushRegistrationId;

    }

    public static void setPushRegistrationId(Context context, String pushRegistrationId) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PUSH_REGISTRATION_ID,
                String.valueOf(pushRegistrationId));
        editor.apply();
    }

    /**
     * Clear session details
     */
    public static void clearSession(Context context) {
        // Clearing all data from Shared Preferences
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }


}
