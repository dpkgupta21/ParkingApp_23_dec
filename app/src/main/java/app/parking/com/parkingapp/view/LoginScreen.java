package app.parking.com.parkingapp.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import app.parking.com.parkingapp.R;
import app.parking.com.parkingapp.navigationDrawer.NavigationDrawerActivity;

public class LoginScreen extends AppCompatActivity implements View.OnClickListener {


    RelativeLayout login_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        initViews();
        assignClick();
    }


    private void initViews() {

        login_button = (RelativeLayout) findViewById(R.id.login_button);
    }

    private void assignClick() {

        login_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.login_button:

                Intent intent = new Intent(this, NavigationDrawerActivity.class);
                startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
                break;
        }

    }
}
