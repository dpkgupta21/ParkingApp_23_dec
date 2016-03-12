package app.parking.com.parkingapp.view;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import org.json.JSONException;
import org.json.JSONObject;

import app.parking.com.parkingapp.R;
import app.parking.com.parkingapp.iClasses.GlobalKeys;
import app.parking.com.parkingapp.navigationDrawer.UserNavigationDrawerActivity;
import app.parking.com.parkingapp.preferences.PreferenceHelper;
import app.parking.com.parkingapp.preferences.SessionManager;
import app.parking.com.parkingapp.utils.AppUtils;
import app.parking.com.parkingapp.webservices.handler.LoginAPIHandler;
import app.parking.com.parkingapp.webservices.ihelper.WebAPIResponseListener;

public class LoginScreen extends AppCompatActivity implements View.OnClickListener {


    private RelativeLayout login_button, register;
    private EditText email_et, pwd_et;
    private LoginAPIHandler mLoginAPIHandler;
    private String TAG = LoginScreen.class.getSimpleName();
    private String email, pwd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        initViews();
        assignClick();
    }


    private void initViews() {

        login_button = (RelativeLayout) findViewById(R.id.login_button);
        register = (RelativeLayout) findViewById(R.id.register);
        email_et = (EditText) findViewById(R.id.email_et);
        pwd_et = (EditText) findViewById(R.id.pwd_et);
    }

    private void assignClick() {

        login_button.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(final View v) {

        switch (v.getId()) {

            case R.id.register:
                startActivity(new Intent(LoginScreen.this, SignupScreen.class));
                break;
            case R.id.login_button:
                email = email_et.getText().toString().trim();
                pwd = pwd_et.getText().toString().trim();
                if (email.isEmpty()) {
                    Snackbar.make(v, getString(R.string.please_enter_email), Snackbar.LENGTH_LONG).show();
                } else if (pwd.isEmpty()) {
                    Snackbar.make(v, getString(R.string.please_enter_pwd), Snackbar.LENGTH_LONG).show();

                } else {
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
                                        SessionManager.getInstance(LoginScreen.this).createLoginSession(email, pwd, auth);
                                        Intent intent = new Intent(LoginScreen.this, UserNavigationDrawerActivity.class);
                                        startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                        finish();

                                    } else {
                                        AppUtils.showToast(LoginScreen.this, "Login Failed");
                                    }
                                } else {
                                    AppUtils.showToast(LoginScreen.this, "Login Failed");

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }


                        @Override
                        public void onFailOfResponse(Object... arguments) {
                            AppUtils.showToast(LoginScreen.this, "Login Failed");

                        }
                    });


                }
                break;
        }

    }
}
