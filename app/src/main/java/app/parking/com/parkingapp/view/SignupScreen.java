package app.parking.com.parkingapp.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import org.json.JSONObject;

import app.parking.com.parkingapp.R;
import app.parking.com.parkingapp.utils.AppUtils;
import app.parking.com.parkingapp.utils.WebserviceResponseConstants;
import app.parking.com.parkingapp.webservices.handler.SignupAPIHandler;
import app.parking.com.parkingapp.webservices.ihelper.WebAPIResponseListener;

public class SignupScreen extends AppCompatActivity implements View.OnClickListener {


    private RelativeLayout signup_button;
    private EditText email_et, pwd_et, name_et, phone_et, cnfrm_pwd_et, lastname_et;
    private SignupAPIHandler mSignupHandler;
    private String TAG = SignupScreen.class.getSimpleName();
    private Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_screen);
        mActivity = SignupScreen.this;
        initViews();
        assignClick();
    }


    private void initViews() {

        signup_button = (RelativeLayout) findViewById(R.id.signup_button);
        email_et = (EditText) findViewById(R.id.email_et);
        pwd_et = (EditText) findViewById(R.id.pwd_et);
        name_et = (EditText) findViewById(R.id.name_et);
        phone_et = (EditText) findViewById(R.id.phone_et);
        cnfrm_pwd_et = (EditText) findViewById(R.id.cnfrm_pwd_et);
        lastname_et = (EditText) findViewById(R.id.lastname_et);

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
                String firstname = name_et.getText().toString().trim();
                String confrmpwd = cnfrm_pwd_et.getText().toString().trim();
                String lastname = lastname_et.getText().toString().trim();
                String mobNumber = phone_et.getText().toString().trim();


                if (firstname.isEmpty()) {
                    Snackbar.make(v, getString(R.string.please_enter_firtsname), Snackbar.LENGTH_LONG).show();

                } else if (lastname.isEmpty()) {
                    Snackbar.make(v, getString(R.string.please_enter_secondname), Snackbar.LENGTH_LONG).show();

                } else if (email.isEmpty()) {
                    Snackbar.make(v, getString(R.string.please_enter_email), Snackbar.LENGTH_LONG).show();
                } else if (mobNumber.isEmpty()) {
                    Snackbar.make(v, getString(R.string.please_enter_secondname), Snackbar.LENGTH_LONG).show();

                } else if (pwd.isEmpty()) {
                    Snackbar.make(v, getString(R.string.please_enter_pwd), Snackbar.LENGTH_LONG).show();

                } else if (confrmpwd.isEmpty()) {
                    Snackbar.make(v, getString(R.string.please_enter_cnfrm_pwd), Snackbar.LENGTH_LONG).show();
                } else if (!confrmpwd.equalsIgnoreCase(pwd)) {
                    Snackbar.make(v, getString(R.string.pwd_mismatch), Snackbar.LENGTH_LONG).show();
                } else {
                    mSignupHandler = new SignupAPIHandler(this, email, pwd, firstname,
                            lastname, mobNumber, new WebAPIResponseListener() {
                        @Override
                        public void onSuccessOfResponse(Object... arguments) {


                            JSONObject mJsonObject = (JSONObject) arguments[0];
                            if (mJsonObject != null) {
                                //AppUtils.showToast(SignupScreen.this, "Registration Success");

                                Intent intent = new Intent(SignupScreen.this, LoginScreen.class);
                                startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                finish();

                            } else {
                                AppUtils.showToast(SignupScreen.this, "Registration Failed");

                            }


                        }


                        @Override
                        public void onFailOfResponse(Object... arguments) {
                            JSONObject mJsonObject = (JSONObject) arguments[0];
                            if (mJsonObject != null) {
                                if (AppUtils.getWebServiceErrorCode(mJsonObject) != null
                                        && AppUtils.getWebServiceErrorCode(mJsonObject).
                                        equalsIgnoreCase(WebserviceResponseConstants.
                                                ERROR_USER_ALREADY_SIGNUP)) {
                                    AppUtils.showDialog(mActivity, "Message",
                                            AppUtils.getWebServiceErrorMsg(mJsonObject));
                                }


                            }


                        }
                    });


                }
                break;
        }

    }
}
