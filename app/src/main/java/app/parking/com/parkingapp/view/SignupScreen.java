package app.parking.com.parkingapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import org.json.JSONException;
import org.json.JSONObject;

import app.parking.com.parkingapp.R;
import app.parking.com.parkingapp.iClasses.GlobalKeys;
import app.parking.com.parkingapp.navigationDrawer.UserNavigationDrawerActivity;
import app.parking.com.parkingapp.preferences.SessionManager;
import app.parking.com.parkingapp.utils.AppUtils;
import app.parking.com.parkingapp.webservices.handler.LoginAPIHandler;
import app.parking.com.parkingapp.webservices.handler.SignupAPIHandler;
import app.parking.com.parkingapp.webservices.ihelper.WebAPIResponseListener;

public class SignupScreen extends AppCompatActivity implements View.OnClickListener {


    private RelativeLayout signup_button;
    private EditText email_et, pwd_et, secondname_et, firstname_et;
    private SignupAPIHandler mSignupHandler;
    private String TAG = SignupScreen.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_screen);

        initViews();
        assignClick();
    }


    private void initViews() {

        signup_button = (RelativeLayout) findViewById(R.id.signup_button);
        email_et = (EditText) findViewById(R.id.email_et);
        pwd_et = (EditText) findViewById(R.id.pwd_et);
        secondname_et = (EditText) findViewById(R.id.secondname_et);
        firstname_et = (EditText) findViewById(R.id.firstname_et);
    }

    private void assignClick() {

        signup_button.setOnClickListener(this);
    }

    @Override
    public void onClick(final View v) {

        switch (v.getId()) {
            case R.id.signup_button:

                String email = email_et.getText().toString().trim();
                String pwd = pwd_et.getText().toString().trim();
                String firstname = firstname_et.getText().toString().trim();
                String lastname = secondname_et.getText().toString().trim();

                if (email.isEmpty()) {
                    Snackbar.make(v, getString(R.string.please_enter_email), Snackbar.LENGTH_LONG).show();
                } else if (pwd.isEmpty()) {
                    Snackbar.make(v, getString(R.string.please_enter_pwd), Snackbar.LENGTH_LONG).show();

                } else if (firstname.isEmpty()) {
                    Snackbar.make(v, getString(R.string.please_enter_firtsname), Snackbar.LENGTH_LONG).show();

                } else if (lastname.isEmpty()) {
                    Snackbar.make(v, getString(R.string.please_enter_secondname), Snackbar.LENGTH_LONG).show();

                } else {
                    mSignupHandler = new SignupAPIHandler(this, email, pwd, firstname, lastname, new WebAPIResponseListener() {
                        @Override
                        public void onSuccessOfResponse(Object... arguments) {


                            JSONObject mJsonObject = (JSONObject) arguments[0];
                            if (mJsonObject != null) {
                                AppUtils.showToast(SignupScreen.this, "Registration Success");

                                Intent intent = new Intent(SignupScreen.this, LoginScreen.class);
                                startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                finish();

                            } else {
                                AppUtils.showToast(SignupScreen.this, "Registration Failed");

                            }


                        }


                        @Override
                        public void onFailOfResponse(Object... arguments) {
                            AppUtils.showToast(SignupScreen.this, "Registration Failed");

                        }
                    });


                }
                break;
        }

    }
}
