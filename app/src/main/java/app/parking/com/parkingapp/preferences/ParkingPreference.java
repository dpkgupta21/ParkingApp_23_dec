package app.parking.com.parkingapp.preferences;


import android.content.Context;
import android.content.SharedPreferences;

public class ParkingPreference {


    public static final String PREF_NAME = "PARKING_PREFERENCES";


    public static String getPushRegistrationId(Context context) {
        String pushRegistrationId = context.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE).getString(
                PreferenceHelper.PUSH_REGISTRATION_ID, "");
        return pushRegistrationId;

    }

    public static void setPushRegistrationId(Context context, String pushRegistrationId) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PreferenceHelper.PUSH_REGISTRATION_ID,
                String.valueOf(pushRegistrationId));
        editor.apply();
    }


}
