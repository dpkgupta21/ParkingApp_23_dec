package app.parking.com.parkingapp.view;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import app.parking.com.parkingapp.R;
import app.parking.com.parkingapp.appIntroduction.WelcomeScreen;
import app.parking.com.parkingapp.utils.AppConstants;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

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
