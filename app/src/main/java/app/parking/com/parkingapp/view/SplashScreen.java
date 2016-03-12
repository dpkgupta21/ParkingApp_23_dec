package app.parking.com.parkingapp.view;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import org.json.JSONException;
import org.json.JSONObject;

import app.parking.com.parkingapp.R;
import app.parking.com.parkingapp.appIntroduction.WelcomeScreen;
import app.parking.com.parkingapp.iClasses.GlobalKeys;
import app.parking.com.parkingapp.navigationDrawer.UserNavigationDrawerActivity;
import app.parking.com.parkingapp.preferences.SessionManager;
import app.parking.com.parkingapp.utils.AppConstants;
import app.parking.com.parkingapp.utils.AppUtils;
import app.parking.com.parkingapp.webservices.handler.LoginAPIHandler;
import app.parking.com.parkingapp.webservices.ihelper.WebAPIResponseListener;

public class SplashScreen extends AppCompatActivity {

    private static final String TAG = SplashScreen.class.getSimpleName();
    private LoginAPIHandler mLoginAPIHandler;
    private String email, pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        boolean isAlreadyLogin = SessionManager.getInstance(this).isLoggedIn();

        if (isAlreadyLogin) {
            email = SessionManager.getInstance(this).getEmail();
            pwd = SessionManager.getInstance(this).getPwd();
            mLoginAPIHandler = new LoginAPIHandler(this, email, pwd, new WebAPIResponseListener() {
                @Override
                public void onSuccessOfResponse(Object... arguments) {

                    try {
                        JSONObject mJsonObject = (JSONObject) arguments[0];
                        if (mJsonObject != null) {
                            if (mJsonObject.has(GlobalKeys.AUTHTOKEN) && mJsonObject.has(GlobalKeys.EMAIL)) {
                                email = mJsonObject.getString(GlobalKeys.EMAIL);
                                String auth = mJsonObject.getString(GlobalKeys.AUTHTOKEN);
                                AppUtils.showLog(TAG, "email: " + email + " " + auth);
                                SessionManager.getInstance(SplashScreen.this).createLoginSession(email, pwd, auth);
                                Intent intent = new Intent(SplashScreen.this, UserNavigationDrawerActivity.class);
                                startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                finish();
                            } else {
                                openLoginScreen();
                            }
                        } else {
                            openLoginScreen();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void onFailOfResponse(Object... arguments) {
                    openLoginScreen();

                }
            });


        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashScreen.this, WelcomeScreen.class);
                    startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    finish();
                }
            }, AppConstants.ONE_SECOND * 3);
        }
    }


    private void openLoginScreen() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SessionManager.getInstance(SplashScreen.this).clearSession();
                Intent intent = new Intent(SplashScreen.this, LoginScreen.class);
                startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
            }
        }, AppConstants.ONE_SECOND * 3);
    }
}
