package app.parking.com.parkingapp.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import app.parking.com.parkingapp.R;
import app.parking.com.parkingapp.activity.BaseActivity;
import app.parking.com.parkingapp.customViews.CustomProgressDialog;
import app.parking.com.parkingapp.iClasses.GlobalKeys;
import app.parking.com.parkingapp.navigationDrawer.UserNavigationDrawerActivity;
import app.parking.com.parkingapp.preferences.ParkingPreference;
import app.parking.com.parkingapp.utils.AppUtils;
import app.parking.com.parkingapp.utils.WebserviceResponseConstants;
import app.parking.com.parkingapp.webservices.handler.LoginAPIHandler;
import app.parking.com.parkingapp.webservices.ihelper.WebAPIResponseListener;

public class LoginScreen extends BaseActivity implements View.OnClickListener {


    private String TAG = LoginScreen.class.getSimpleName();
    private Activity mActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        mActivity = LoginScreen.this;
        initViews();
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

}
