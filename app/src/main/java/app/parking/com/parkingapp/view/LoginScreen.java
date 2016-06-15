package app.parking.com.parkingapp.view;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import com.google.android.gcm.GCMRegistrar;

import org.json.JSONException;
import org.json.JSONObject;

import app.parking.com.parkingapp.R;
import app.parking.com.parkingapp.WakeLocker;
import app.parking.com.parkingapp.activity.BaseActivity;
import app.parking.com.parkingapp.customViews.CustomProgressDialog;
import app.parking.com.parkingapp.iClasses.GlobalKeys;
import app.parking.com.parkingapp.navigationDrawer.UserNavigationDrawerActivity;
import app.parking.com.parkingapp.preferences.ParkingPreference;
import app.parking.com.parkingapp.utils.AppUtils;
import app.parking.com.parkingapp.utils.WebserviceResponseConstants;
import app.parking.com.parkingapp.webservices.handler.LoginAPIHandler;
import app.parking.com.parkingapp.webservices.ihelper.WebAPIResponseListener;

import static app.parking.com.parkingapp.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static app.parking.com.parkingapp.CommonUtilities.EXTRA_MESSAGE;
import static app.parking.com.parkingapp.CommonUtilities.SENDER_ID;

public class LoginScreen extends BaseActivity implements View.OnClickListener {


    private String TAG = LoginScreen.class.getSimpleName();
    private Activity mActivity;
    private AsyncTask<Void, Void, Void> mRegisterTask;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        mActivity = LoginScreen.this;
        initViews();

        String pushRegistrationId = ParkingPreference.getPushRegistrationId(mActivity);
        if (pushRegistrationId == null || pushRegistrationId.equalsIgnoreCase("")) {
            registrationPushNotification();
        }
    }


    private void initViews() {
        setClick(R.id.login_button);
        setClick(R.id.register);
        setClick(R.id.txt_forget_pwd);
    }


    @Override
    public void onClick(final View v) {

        switch (v.getId()) {

            case R.id.register:
                startActivity(new Intent(LoginScreen.this, SignupScreen.class));
                break;
            case R.id.txt_forget_pwd:
                startActivity(new Intent(LoginScreen.this, ForgetPasswordScreen.class));
                break;
            case R.id.login_button:
                final String pwd = getViewText(R.id.pwd_et);
                final String email = getViewText(R.id.email_et);
                if (validateForm(v)) {
                    CustomProgressDialog.showProgDialog(mActivity, null);

                    LoginAPIHandler mLoginAPIHandler = new LoginAPIHandler(mActivity,
                            email, pwd, new WebAPIResponseListener() {
                        @Override
                        public void onSuccessOfResponse(Object... arguments) {

                            try {
                                JSONObject mJsonObject = (JSONObject) arguments[0];
                                if (mJsonObject != null) {
                                    if (mJsonObject.has(GlobalKeys.AUTHTOKEN) &&
                                            mJsonObject.has(GlobalKeys.EMAIL)) {

                                        String email = mJsonObject.getString(GlobalKeys.EMAIL).
                                                toLowerCase();
                                        String auth = mJsonObject.getString(GlobalKeys.AUTHTOKEN);
                                        String userId = mJsonObject.
                                                getString(GlobalKeys.RESPONSE__USERID);

                                        ParkingPreference.setEmailId(mActivity, email);
                                        ParkingPreference.setPassword(mActivity, pwd);
                                        ParkingPreference.setKeyAuthtoken(mActivity, auth);
                                        ParkingPreference.setUserId(mActivity, userId);
                                        ParkingPreference.setIsLogin(mActivity, true);

                                        CustomProgressDialog.hideProgressDialog();

                                        Intent intent = new Intent(LoginScreen.this,
                                                UserNavigationDrawerActivity.class);
                                        startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                        finish();
                                    }
                                }

                            } catch (JSONException e) {
                                CustomProgressDialog.hideProgressDialog();
                                e.printStackTrace();
                            }
                        }


                        @Override
                        public void onFailOfResponse(Object... arguments) {
                            try {
                                CustomProgressDialog.hideProgressDialog();

                                JSONObject errorJsonObj = (JSONObject) arguments[0];
                                if (AppUtils.getWebServiceErrorCode(errorJsonObj).
                                        equalsIgnoreCase
                                                (WebserviceResponseConstants.LOGIN_ERROR)) {

                                    AppUtils.showDialog(mActivity, "Message",
                                            AppUtils.getWebServiceErrorMsg(errorJsonObj));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });


                }
                break;
        }

    }


    public boolean validateForm(View v) {

        if (getEditTextText(R.id.email_et).equals("")) {

            Snackbar.make(v, getString(R.string.please_enter_email), Snackbar.LENGTH_LONG).show();
            return false;
        } else if (!AppUtils.isEmailIDValidate(getEditTextText(R.id.email_et))) {
            Snackbar.make(v, getString(R.string.please_enter_valid_email),
                    Snackbar.LENGTH_LONG).show();
            return false;
        } else if (getEditTextText(R.id.pwd_et).equals("")) {
            Snackbar.make(v, getString(R.string.please_enter_pwd), Snackbar.LENGTH_LONG).show();
            return false;
        }
        return true;
    }


    // For Push notification
    private void registrationPushNotification() {
        // Make sure the device has the proper dependencies.
        GCMRegistrar.checkDevice(mActivity);

        // Make sure the manifest was properly set - comment out this line
        // while developing the app, then uncomment it when it's ready.
        GCMRegistrar.checkManifest(mActivity);

        registerReceiver(mHandleMessageReceiver, new IntentFilter(
                DISPLAY_MESSAGE_ACTION));

        // Get GCM registration id
        final String regId = GCMRegistrar
                .getRegistrationId(mActivity);

        ParkingPreference.setPushRegistrationId(mActivity, regId);
        Log.i("info", "RegId :" + regId);
        // Check if regid already presents
        if (regId.equals("")) {
            Log.i("info", "RegId :" + regId);
            // Registration is not present, register now with GCM
            GCMRegistrar.register(mActivity, SENDER_ID);
        } else {
            // Device is already registered on GCM
            if (GCMRegistrar
                    .isRegisteredOnServer(mActivity)) {
                // Skips registration.
                Log.i("info", "Already registered with GCM");
            } else {
                Log.i("info", "Not registered with GCM");
                // Try to register again, but not in the UI thread.
                // It's also necessary to cancel the thread onDestroy(),
                // hence the use of AsyncTask instead of a raw thread.
                mRegisterTask = new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... params) {
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void result) {
                        mRegisterTask = null;
                    }

                };
                mRegisterTask.execute(null, null, null);
            }
        }
    }

    /**
     * Receiving push messages
     */
    private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
            // Waking up mobile if it is sleeping
            WakeLocker.acquire(getApplicationContext());

            /**
             * Take appropriate action on this message depending upon your app
             * requirement For now i am just displaying it on the screen
             * */

            // Showing received message

            // Releasing wake lock
            WakeLocker.release();
        }
    };


    @Override
    protected void onDestroy() {
        if (mRegisterTask != null) {
            mRegisterTask.cancel(true);
        }
        try {
            unregisterReceiver(mHandleMessageReceiver);
            GCMRegistrar.onDestroy(mActivity);
        } catch (Exception e) {
            AppUtils.showLog(TAG, "UnRegister Receiver Error " + e.getMessage());
        }
        super.onDestroy();
    }


}
