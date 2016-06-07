package app.parking.com.parkingapp.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import app.parking.com.parkingapp.R;
import app.parking.com.parkingapp.appIntroduction.WelcomeScreen;
import app.parking.com.parkingapp.iClasses.GlobalKeys;
import app.parking.com.parkingapp.navigationDrawer.UserNavigationDrawerActivity;
import app.parking.com.parkingapp.preferences.ParkingPreference;
import app.parking.com.parkingapp.webservices.handler.LoginAPIHandler;
import app.parking.com.parkingapp.webservices.ihelper.WebAPIResponseListener;

public class SplashScreen extends AppCompatActivity {

    private static final String TAG = SplashScreen.class.getSimpleName();
    private Activity mActivity;
    private long splashDelay = 3000;

    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        mActivity = SplashScreen.this;

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                String password =
                        ParkingPreference.getPassword(mActivity);
                Intent i = null;
                if (password == null || password.equalsIgnoreCase("")) {
                    i = new Intent(mActivity, WelcomeScreen.class);
                    startActivity(i);
                    finish();
                } else {

                    loginInBackground();
                }

            }
        };
        timer = new Timer();
        timer.schedule(task, splashDelay);


    }

    private void loginInBackground() {

        String email = ParkingPreference.getEmailId(mActivity);
        String pwd = ParkingPreference.getPassword(mActivity);

        LoginAPIHandler mLoginAPIHandler = new LoginAPIHandler(this, email, pwd, new WebAPIResponseListener() {
            @Override
            public void onSuccessOfResponse(Object... arguments) {

                try {
                    JSONObject mJsonObject = (JSONObject) arguments[0];
                    if (mJsonObject != null) {
                        if (mJsonObject.has(GlobalKeys.AUTHTOKEN) && mJsonObject.has(GlobalKeys.EMAIL)) {

                            String authToken = mJsonObject.getString(GlobalKeys.AUTHTOKEN);
                            ParkingPreference.setKeyAuthtoken(mActivity, authToken);
                            Intent intent = new Intent(SplashScreen.this,
                                    UserNavigationDrawerActivity.class);
                            startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                            finish();
                        } else {

                            //CustomProgressDialog.hideProgressDialog();
                            //AppUtils.showDialog(mActivity, "Message",
                            // AppUtils.getWebServiceMessage(response));

                            openLoginScreen();
                        }
                    } else {
                       // CustomProgressDialog.hideProgressDialog();
                        openLoginScreen();

                    }

                } catch (JSONException e) {
                   // CustomProgressDialog.hideProgressDialog();
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailOfResponse(Object... arguments) {
                //CustomProgressDialog.hideProgressDialog();
                openLoginScreen();

            }
        });

    }

    private void openLoginScreen() {
        ParkingPreference.clearSession(mActivity);
        Intent intent = new Intent(SplashScreen.this, LoginScreen.class);
        startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
    }
}
