package app.parking.com.parkingapp.navigationDrawer;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gcm.GCMRegistrar;

import org.json.JSONObject;

import app.parking.com.parkingapp.R;
import app.parking.com.parkingapp.WakeLocker;
import app.parking.com.parkingapp.currentbooking.CurrentBookingFragment;
import app.parking.com.parkingapp.customViews.CustomAlert;
import app.parking.com.parkingapp.customViews.CustomProgressDialog;
import app.parking.com.parkingapp.home.HomeScreenFragment;
import app.parking.com.parkingapp.orderstatus.OrderStausFragment;
import app.parking.com.parkingapp.preferences.ParkingPreference;
import app.parking.com.parkingapp.utils.AppUtils;
import app.parking.com.parkingapp.utils.WebserviceResponseConstants;
import app.parking.com.parkingapp.view.LoginScreen;
import app.parking.com.parkingapp.view.UserProfileScreen;
import app.parking.com.parkingapp.webservices.handler.AddTokenPushAPIHandler;
import app.parking.com.parkingapp.webservices.handler.LogoutAPIHandler;
import app.parking.com.parkingapp.webservices.ihelper.WebAPIResponseListener;

import static app.parking.com.parkingapp.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static app.parking.com.parkingapp.CommonUtilities.EXTRA_MESSAGE;
import static app.parking.com.parkingapp.CommonUtilities.SENDER_ID;


public class UserNavigationDrawerActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private ActionBarDrawerToggle mDrawerToggle;
    private int mCurrentSelectedPosition;
    private View headerView;
    public static Activity mActivity;
    private String TAG = UserNavigationDrawerActivity.class.getSimpleName();
    private AsyncTask<Void, Void, Void> mRegisterTask;
    private AddTokenPushAPIHandler mAddTokenAPIHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_nav_drawer_activity);


        mActivity = this;

        int fragmentPos = getIntent().getIntExtra("fragmentPos", 0);
        initViews();
        assignClickOnView();
        assignClickonNavigationMenu();
        displayView(fragmentPos);

        String pushRegistrationId = ParkingPreference.getPushRegistrationId(mActivity);
        if (pushRegistrationId == null || pushRegistrationId.equalsIgnoreCase("")) {
            registrationPushNotification();
        }

    }


    private void initViews() {


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);

        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
        headerView = mNavigationView.inflateHeaderView(R.layout.drawer_header);
        mDrawerLayout.setDrawerListener(mDrawerToggle);


        mDrawerToggle.syncState();
    }

    private void assignClickOnView() {


        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.closeDrawer(GravityCompat.START);

                Intent user_profile_intent = new Intent(UserNavigationDrawerActivity.this,
                        UserProfileScreen.class);
                startActivity(user_profile_intent);
            }
        });

    }


    private void assignClickonNavigationMenu() {

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                item.setChecked(true);
                mDrawerLayout.closeDrawer(GravityCompat.START);
                switch (item.getItemId()) {
                    case R.id.nav_item_home:
                        displayView(0);
                        mCurrentSelectedPosition = 0;
                        return true;
                    case R.id.nav_item_current_booking:
                        displayView(1);
                        mCurrentSelectedPosition = 1;
                        return true;
                    case R.id.nav_item_boooking_history:
                        displayView(2);
                        mCurrentSelectedPosition = 2;
                        return true;
                    case R.id.nav_item_order_discount:
                        displayView(3);
                        mCurrentSelectedPosition = 3;
                        return true;
                    case R.id.nav_item_about:
                        displayView(4);
                        mCurrentSelectedPosition = 4;
                        return true;
                    case R.id.nav_item_notifications:
                        displayView(5);
                        mCurrentSelectedPosition = 5;
                        return true;

                    case R.id.nav_item_logout:

                        AppUtils.showDialog(mActivity, getString(R.string.logout),
                                getString(R.string.logout_msg), "YES", "NO",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String email = ParkingPreference.getEmailId(mActivity);
                                        String auth = ParkingPreference.getKeyAuthtoken(mActivity);
                                        String userId = ParkingPreference.getUserid(mActivity);

                                        AppUtils.showLog(TAG, "email: " + email + " auth: " + auth);
                                        CustomProgressDialog.showProgDialog(mActivity, null);
                                        LogoutAPIHandler mLogoutAPIHandler = new
                                                LogoutAPIHandler(mActivity,
                                                onLogoutResponseListner());

                                    }
                                });

                        mCurrentSelectedPosition = 5;

                        return true;


                    default:
                        return true;
                }

            }
        });

    }

    private WebAPIResponseListener onLogoutResponseListner() {

        WebAPIResponseListener mWebAPIResponseListener;

        mWebAPIResponseListener = new WebAPIResponseListener() {
            @Override
            public void onSuccessOfResponse(Object... arguments) {
                CustomProgressDialog.hideProgressDialog();
                try {
                    ParkingPreference.clearSession(mActivity);
                    Intent intent = new Intent(UserNavigationDrawerActivity.this, LoginScreen.class);
                    startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    //AppUtils.showToast(mActivity, message);

                    finish();


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onFailOfResponse(Object... arguments) {
                CustomProgressDialog.hideProgressDialog();
                AppUtils.showToast(mActivity, "Logout Failed");

            }
        }

        ;

        return mWebAPIResponseListener;
    }

    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {

            case 0:
                addTokenHandler();
                fragment = new HomeScreenFragment();
                title = " ";
                break;
            case 1:
                fragment = new OrderStausFragment();
                title = " ";
                break;
            case 2:
                fragment = new CurrentBookingFragment();
                title = " ";
                break;
            case 3:
                // fragment = new AlertFragment();
                title = " ";
                break;
            case 4:
                // fragment = new AlertFragment();
                title = " ";
                break;
            case 5:
                // fragment = new AlertFragment();
                title = " ";
                break;
            default:
                break;
        }


        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.body_layout, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }

    private void addTokenHandler() {


        mAddTokenAPIHandler = new AddTokenPushAPIHandler(UserNavigationDrawerActivity.this,
                new WebAPIResponseListener() {
                    @Override
                    public void onSuccessOfResponse(Object... arguments) {

                        try {
                            JSONObject mJsonObject = (JSONObject) arguments[0];

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                    @Override
                    public void onFailOfResponse(Object... arguments) {
                        try {
                            CustomProgressDialog.hideProgressDialog();

                            String errorResponse = (String) arguments[0];
                            JSONObject errorJsonObj = new JSONObject(errorResponse);
                            if (AppUtils.getWebServiceErrorCode(errorJsonObj).
                                    equalsIgnoreCase(WebserviceResponseConstants.ERROR_TOKEN_EXPIRED)) {

                                new CustomAlert(mActivity, mActivity)
                                        .singleButtonAlertDialog(
                                                AppUtils.getWebServiceErrorMsg(errorJsonObj),
                                                getString(R.string.ok),
                                                "singleBtnCallbackResponse", 1000);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
    }


    public void singleBtnCallbackResponse(Boolean flag, int code) {
        if (flag) {
            CustomProgressDialog.showProgDialog(mActivity, null);
            LogoutAPIHandler mLogoutAPIHandler = new
                    LogoutAPIHandler(mActivity,
                    onLogoutResponseListner());
        }
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
