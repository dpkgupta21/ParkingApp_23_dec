package app.parking.com.parkingapp.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

import app.parking.com.parkingapp.R;
import app.parking.com.parkingapp.activity.BaseActivity;
import app.parking.com.parkingapp.customViews.CustomAlert;
import app.parking.com.parkingapp.customViews.CustomProgressDialog;
import app.parking.com.parkingapp.model.CreateOrderDTO;
import app.parking.com.parkingapp.model.CreateOrderResponseDTO;
import app.parking.com.parkingapp.model.HoldOrderDTO;
import app.parking.com.parkingapp.model.ListOfServicesDTO;
import app.parking.com.parkingapp.navigationDrawer.UserNavigationDrawerActivity;
import app.parking.com.parkingapp.preferences.ParkingPreference;
import app.parking.com.parkingapp.utils.AppConstants;
import app.parking.com.parkingapp.utils.AppUtils;
import app.parking.com.parkingapp.utils.WebserviceResponseConstants;
import app.parking.com.parkingapp.view.LoginScreen;
import app.parking.com.parkingapp.webservices.handler.CreateOrderAPIHandler;
import app.parking.com.parkingapp.webservices.handler.ServicesAPIHandler;
import app.parking.com.parkingapp.webservices.ihelper.WebAPIResponseListener;

public class AddServicesScreen extends BaseActivity implements View.OnClickListener {

    //private Toolbar mToolbar;
    //private TextView toolbar_title, toolbar_right_tv, no_service_tv;
    //private RelativeLayout toolbar_right_rl;
    private static final String TAG = AddServicesScreen.class.getSimpleName();

    private static final int CREATE_ORDER_TOKEN_EXPIRED_FAILURE = 1000;
    private static final int SERVICES_TOKEN_EXPIRED_FAILURE = 1001;

    private ListView services_lv;
    private AddServicesAdapter addServicesAdapter;
    private ArrayList<ListOfServicesDTO> listOfServicesDTOArrayList;
    private CreateOrderDTO createOrderDTO;
    private ArrayList<ListOfServicesDTO> listOfServicesDTO;
    private Activity mActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_services_screen);
        mActivity = AddServicesScreen.this;

        initViews();
        assignClicks();

        if (getIntent() != null) {
            createOrderDTO = (CreateOrderDTO) getIntent().getSerializableExtra(AppConstants.CREATE_ORDER);
            AppUtils.showLog(TAG, createOrderDTO.getPickUpTime() + " " + createOrderDTO.getDropOffTime());
        } else {
            createOrderDTO = new CreateOrderDTO();
        }

        listOfServicesDTO = new ArrayList<ListOfServicesDTO>();

        listOfServicesDTOArrayList = new ArrayList<>();
        String auth = ParkingPreference.getKeyAuthtoken(mActivity);
        String userId = ParkingPreference.getUserid(mActivity);

        new ServicesAPIHandler(mActivity,
                auth, userId,
                fetchServiceResponseListner());
    }

    private void assignClicks() {
        setClick(R.id.toolbar_right_rl);
        setClick(R.id.submit_button);
    }

    private void initViews() {


        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        services_lv = (ListView) findViewById(R.id.services_lv);


        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(" ");
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mToolbar.setNavigationIcon(R.drawable.back_button);
        TextView toolbar_right_tv = (TextView) findViewById(R.id.toolbar_right_tv);
        toolbar_title.setVisibility(View.VISIBLE);
        toolbar_title.setText(getResources().getString(R.string.parkforu));


        setViewVisibility(R.id.toolbar_right_rl, View.VISIBLE);
        toolbar_right_tv.setText(R.string.skip);


    }

    private WebAPIResponseListener fetchServiceResponseListner() {

        WebAPIResponseListener webAPIResponseListener = new WebAPIResponseListener() {
            @Override
            public void onSuccessOfResponse(Object... arguments) {

                String response = (String) arguments[0];

                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<ListOfServicesDTO>>() {
                }.getType();
                listOfServicesDTOArrayList = gson.fromJson(response, listType);

                AppUtils.showLog(TAG, listOfServicesDTOArrayList.size() + "");

                addServicesAdapter = new AddServicesAdapter(AddServicesScreen.this,
                        listOfServicesDTOArrayList, ((TextView) findViewById(R.id.no_service_tv)));
                services_lv.setAdapter(addServicesAdapter);

            }

            @Override
            public void onFailOfResponse(Object... arguments) {
                try {
                    CustomProgressDialog.hideProgressDialog();

                    if (arguments != null) {
                        JSONObject errorJsonObj = (JSONObject) arguments[0];

                        if (AppUtils.getWebServiceErrorCode(errorJsonObj).
                                equalsIgnoreCase
                                        (WebserviceResponseConstants.ERROR_TOKEN_EXPIRED)) {

                            new CustomAlert(mActivity, mActivity)
                                    .singleButtonAlertDialog(
                                            AppUtils.getWebServiceErrorMsg(errorJsonObj),
                                            getString(R.string.ok),
                                            "singleBtnCallbackResponse",
                                            SERVICES_TOKEN_EXPIRED_FAILURE);
                        }
                    }
                } catch (Exception e) {
                    CustomProgressDialog.hideProgressDialog();
                    AppUtils.showDialog(mActivity,
                            getString(R.string.dialog_title_alert),
                            getString(R.string.network_error_please_try_again) );
                    e.printStackTrace();
                }
            }
        };

        return webAPIResponseListener;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();
        switch (id) {

            case android.R.id.home:
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onClick(View v) {


        Intent intent = new Intent();

        switch (v.getId()) {


            case R.id.submit_button:
            case R.id.toolbar_right_rl:


                submitOrder();

                break;
        }

    }


    private void submitOrder() {

        for (int i = 0; i < listOfServicesDTOArrayList.size(); i++) {
            if (listOfServicesDTOArrayList.get(i).isAdded()) {
                listOfServicesDTO.add(listOfServicesDTOArrayList.get(i));
            }
        }
        createOrderDTO.setServices(listOfServicesDTO);
        Gson gson = new Gson();
        String orderRequest = gson.toJson(createOrderDTO);
        AppUtils.showLog(TAG, orderRequest);
        String auth = ParkingPreference.getKeyAuthtoken(mActivity);
        String userId = ParkingPreference.getUserid(mActivity);

        CustomProgressDialog.showProgDialog(mActivity, null);
        CreateOrderAPIHandler createOrderAPIHandler = new CreateOrderAPIHandler(mActivity,
                orderRequest, auth, userId,
                createOrderResponseListner());

    }


    /**
     * Mananging response of orderCreation.
     *
     * @return
     */
    private WebAPIResponseListener createOrderResponseListner() {
        WebAPIResponseListener webAPIResponseListener = new WebAPIResponseListener() {
            @Override
            public void onSuccessOfResponse(Object... arguments) {

                String response = (String) arguments[0];
                CreateOrderResponseDTO createOrderResponseDTO = new Gson().fromJson(response,
                        CreateOrderResponseDTO.class);


                HoldOrderDTO holdOrderDTO = new HoldOrderDTO();
                holdOrderDTO.setUserEmail(ParkingPreference.getEmailId(mActivity));
                holdOrderDTO.setOrderId(createOrderResponseDTO.getOrderStatus().getOrder_id());
                holdOrderDTO.setDropOffTime(createOrderDTO.getDropOffTime());
                holdOrderDTO.setPickUpTime(createOrderDTO.getPickUpTime());
                holdOrderDTO.setVenueName(createOrderDTO.getVenueName());

                AppUtils.showLog(TAG, response);
                Intent intent = new Intent(AddServicesScreen.this, HoldOrderScreen.class);
                intent.putExtra(AppConstants.HOLD_ORDER_KEY, holdOrderDTO);
                intent.putExtra(AppConstants.ORDER_SUMMARY_KEY, createOrderResponseDTO);
                startActivity(intent);

                CustomProgressDialog.hideProgressDialog();

            }

            @Override
            public void onFailOfResponse(Object... arguments) {
                try {
                    CustomProgressDialog.hideProgressDialog();

                    if (arguments != null) {
                        JSONObject errorJsonObj = (JSONObject) arguments[0];

                        if (AppUtils.getWebServiceErrorCode(errorJsonObj).
                                equalsIgnoreCase
                                        (WebserviceResponseConstants.ERROR_TOKEN_EXPIRED)) {

                            new CustomAlert(mActivity, mActivity)
                                    .singleButtonAlertDialog(
                                            AppUtils.getWebServiceErrorMsg(errorJsonObj),
                                            getString(R.string.ok),
                                            "singleBtnCallbackResponse",
                                            CREATE_ORDER_TOKEN_EXPIRED_FAILURE);
                        }
                    }
                } catch (Exception e) {
                    CustomProgressDialog.hideProgressDialog();
                    AppUtils.showDialog(mActivity,
                            getString(R.string.dialog_title_alert),
                            getString(R.string.network_error_please_try_again) );
                    e.printStackTrace();
                }
            }
        };
        return webAPIResponseListener;

    }


    public void singleBtnCallbackResponse(Boolean flag, int code) {
        if (flag) {
            if (code == CREATE_ORDER_TOKEN_EXPIRED_FAILURE
                    || code == CREATE_ORDER_TOKEN_EXPIRED_FAILURE) {

                ParkingPreference.clearSession(mActivity);
                Intent intent = new Intent(mActivity, LoginScreen.class);
                startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
            }
        }
    }

}
