package app.parking.com.parkingapp.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONObject;

import app.parking.com.parkingapp.R;
import app.parking.com.parkingapp.activity.BaseActivity;
import app.parking.com.parkingapp.customViews.CustomAlert;
import app.parking.com.parkingapp.customViews.CustomProgressDialog;
import app.parking.com.parkingapp.model.CreateOrderResponseDTO;
import app.parking.com.parkingapp.model.DestinationFlightInfo;
import app.parking.com.parkingapp.model.FlightInfoDTO;
import app.parking.com.parkingapp.model.HoldOrderDTO;
import app.parking.com.parkingapp.model.OrderStatusDTO;
import app.parking.com.parkingapp.model.PurchaseOrderDTO;
import app.parking.com.parkingapp.model.VehicleInfoDTO;
import app.parking.com.parkingapp.preferences.ParkingPreference;
import app.parking.com.parkingapp.utils.AppConstants;
import app.parking.com.parkingapp.utils.AppUtils;
import app.parking.com.parkingapp.utils.HelpMe;
import app.parking.com.parkingapp.utils.WebserviceResponseConstants;
import app.parking.com.parkingapp.view.LoginScreen;
import app.parking.com.parkingapp.webservices.handler.HoldOrderAPIHandler;
import app.parking.com.parkingapp.webservices.ihelper.WebAPIResponseListener;

public class HoldOrderScreen extends BaseActivity implements View.OnClickListener {

    private static final String TAG = HoldOrderScreen.class.getSimpleName();
    private static final int HOLD_ORDER_TOKEN_EXPIRED_FAILURE = 1000;

    private HoldOrderDTO holdOrderDTO;
    private CreateOrderResponseDTO createOrderResponseDTO;
    private Activity mActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hold_order_screen);

        mActivity = HoldOrderScreen.this;
        holdOrderDTO = (HoldOrderDTO) getIntent().
                getSerializableExtra(AppConstants.HOLD_ORDER_KEY);

        initViews();
        assignClicks();
    }

    private void assignClicks() {

        setClick(R.id.btn_confirm);
    }

    private void initViews() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);

        TextView toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationIcon(R.drawable.back_button);
        toolbar_title.setVisibility(View.VISIBLE);
        toolbar_title.setText(getResources().getString(R.string.parkforu));


        if (getIntent() != null) {
            createOrderResponseDTO = (CreateOrderResponseDTO) getIntent().
                    getSerializableExtra(AppConstants.ORDER_SUMMARY_KEY);
            //AppUtils.showLog(TAG, holdOrderDTO.getPickUpTime() + " " + holdOrderDTO.getDropOffTime());
        } else {

            createOrderResponseDTO = new CreateOrderResponseDTO();
        }

        setResponseText(createOrderResponseDTO);

    }

    private void setResponseText(CreateOrderResponseDTO createOrderResponseDTO) {

        // pick up drop off
        setViewText(R.id.pickup_tv, HelpMe.getPickDropDisplayDateTime(holdOrderDTO.getPickUpTime()));
        setViewText(R.id.drop_off_tv, HelpMe.getPickDropDisplayDateTime(holdOrderDTO.getDropOffTime()));
        setViewText(R.id.duration_tv, HelpMe.getDurationTime(holdOrderDTO.getDropOffTime(),
                holdOrderDTO.getPickUpTime()));


        FlightInfoDTO flightInfoDTO = createOrderResponseDTO.getFlightInfo();

        // Arrival Flight
        DestinationFlightInfo arrivalFlightDTO = flightInfoDTO.getArrivalFlight();
        // Destination Flight
        DestinationFlightInfo destinationFlightDTO = flightInfoDTO.getDestinationFlight();


        //setting departure flight details.
        setViewText(R.id.departure_flight_no_tv, destinationFlightDTO.getFlightNumber());
        setViewText(R.id.departure_flight_name_tv, destinationFlightDTO.getFlightName());
//        setViewText(R.id.departure_flight_arrival_tv,
//                destinationFlightDTO.getFlightArrivalTime());
//        setViewText(R.id.departure_flight_departure_tv,
//                destinationFlightDTO.getFlightDepatureTime());

        //setting arrival flight details.
        setViewText(R.id.arrival_flight_no_tv, arrivalFlightDTO.getFlightNumber());
        setViewText(R.id.arrival_flight_name_tv, arrivalFlightDTO.getFlightName());
//        setViewText(R.id.arrival_flight_arrival_tv,
//                arrivalFlightDTO.getFlightArrivalTime());
//        setViewText(R.id.arrival_flight_departure_tv,
//                arrivalFlightDTO.getFlightDepatureTime());


        //setting vehicle details.
        VehicleInfoDTO infoDTO = createOrderResponseDTO.getVehicleInfo();
        setViewText(R.id.vehicle_make_tv, infoDTO.getVehicleMake());
        setViewText(R.id.vehicle_model_tv, infoDTO.getVehicleModel());
        setViewText(R.id.vehicle_color_tv, infoDTO.getVehicleColor());
        setViewText(R.id.vehicle_platenumber_tv, infoDTO.getPlateNo());

        OrderStatusDTO orderStatusDTO = createOrderResponseDTO.getOrderStatus();

        String tax = orderStatusDTO.getOrderTax().equalsIgnoreCase("0") ? "" : orderStatusDTO.getOrderTax();
        String total = orderStatusDTO.getOrderTotal().equalsIgnoreCase("0") ? "" :
                orderStatusDTO.getOrderTotal();
        //setViewText(R.id.tax_tv, tax);
        setViewText(R.id.price_tv, total);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm:

                String auth = ParkingPreference.getKeyAuthtoken(mActivity);
                String userId = ParkingPreference.getUserid(mActivity);
                String request = new Gson().toJson(holdOrderDTO);
                CustomProgressDialog.showProgDialog(mActivity, null);
                AppUtils.showLog(TAG, request);
                HoldOrderAPIHandler holdOrderAPIHandler = new HoldOrderAPIHandler(
                        mActivity, request, auth, userId, manageHoldOrderResponse());

                break;
        }
    }

    private WebAPIResponseListener manageHoldOrderResponse() {

        WebAPIResponseListener webAPIResponseListener = new WebAPIResponseListener() {
            @Override
            public void onSuccessOfResponse(Object... arguments) {
                String response = (String) arguments[0];

                createOrderResponseDTO = new Gson().fromJson(response, CreateOrderResponseDTO.class);
                AppUtils.showLog(TAG, response);

                PurchaseOrderDTO purchaseOrderDTO = new PurchaseOrderDTO();

                purchaseOrderDTO.setSlotId(createOrderResponseDTO.getOrderConfirmation().getSlotId());
                purchaseOrderDTO.setUserEmail(holdOrderDTO.getUserEmail());
                purchaseOrderDTO.setDropOffTime(holdOrderDTO.getDropOffTime());
                purchaseOrderDTO.setOrderId(holdOrderDTO.getOrderId());
                purchaseOrderDTO.setVenueName(holdOrderDTO.getVenueName());
                purchaseOrderDTO.setPickUpTime(holdOrderDTO.getPickUpTime());
                purchaseOrderDTO.setPurchaseStripeToken("");

                CustomProgressDialog.hideProgressDialog();
                Intent intent = new Intent(HoldOrderScreen.this,
                        OrderDetailsScreenNew.class);
                intent.putExtra(AppConstants.PURCHASE_ORDER_KEY, purchaseOrderDTO);
                intent.putExtra(AppConstants.ORDER_SUMMARY_KEY, createOrderResponseDTO);
                startActivity(intent);

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
                                            HOLD_ORDER_TOKEN_EXPIRED_FAILURE);
                        }
                    }
                } catch (Exception e) {
                    CustomProgressDialog.hideProgressDialog();
                    AppUtils.showDialog(mActivity,
                            getString(R.string.dialog_title_alert),
                            getString(R.string.network_error_please_try_again));
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


    public void singleBtnCallbackResponse(Boolean flag, int code) {
        if (flag) {
            if (code == HOLD_ORDER_TOKEN_EXPIRED_FAILURE) {
                ParkingPreference.clearSession(mActivity);
                Intent intent = new Intent(mActivity, LoginScreen.class);
                startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
            }
        }
    }

}
