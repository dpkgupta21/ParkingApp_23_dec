package app.parking.com.parkingapp.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import app.parking.com.parkingapp.R;
import app.parking.com.parkingapp.activity.BaseActivity;
import app.parking.com.parkingapp.model.CreateOrderResponseDTO;
import app.parking.com.parkingapp.model.DestinationFlightInfo;
import app.parking.com.parkingapp.model.FlightInfoDTO;
import app.parking.com.parkingapp.model.HoldOrderDTO;
import app.parking.com.parkingapp.model.HoldOrderResponseDTO;
import app.parking.com.parkingapp.model.OrderStatusDTO;
import app.parking.com.parkingapp.model.PurchaseOrderDTO;
import app.parking.com.parkingapp.model.ServiceInfoDTO;
import app.parking.com.parkingapp.model.VehicleInfoDTO;
import app.parking.com.parkingapp.preferences.ParkingPreference;
import app.parking.com.parkingapp.utils.AppConstants;
import app.parking.com.parkingapp.utils.AppUtils;
import app.parking.com.parkingapp.utils.HelpMe;
import app.parking.com.parkingapp.webservices.handler.HoldOrderAPIHandler;
import app.parking.com.parkingapp.webservices.ihelper.WebAPIResponseListener;

public class HoldOrderScreen extends BaseActivity implements View.OnClickListener {
    private Toolbar mToolbar;
    private Button confirm_button;
    private HoldOrderDTO holdOrderDTO;
    private CreateOrderResponseDTO createOrderResponseDTO;
    private String TAG = HoldOrderScreen.class.getSimpleName();
    private Activity mActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hold_order_screen);

        mActivity = HoldOrderScreen.this;

        initViews();
        assignClicks();
    }

    private void assignClicks() {

        confirm_button.setOnClickListener(this);
    }

    private void initViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        TextView toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationIcon(R.drawable.back_button);
        toolbar_title.setVisibility(View.VISIBLE);
        toolbar_title.setText(getResources().getString(R.string.parkforu));


        confirm_button = (Button) findViewById(R.id.btn_confirm);
        confirm_button.setOnClickListener(this);
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
        FlightInfoDTO flightInfoDTO = createOrderResponseDTO.getFlightInfo();


        // Arrival Flight
        DestinationFlightInfo arrivalFlightDTO = flightInfoDTO.getArrivalFlight();

        // Destination Flight
        DestinationFlightInfo destinationFlightDTO = flightInfoDTO.getDestinationFlight();

        OrderStatusDTO orderStatusDTO = createOrderResponseDTO.getOrderStatus();

        setViewText(R.id.dest_tv, destinationFlightDTO.getDestination());
        setViewText(R.id.arrival_tv, arrivalFlightDTO.getOrigin());
        String drop_off = HelpMe.getDisplayDate(destinationFlightDTO.getFlightDepatureTime())
                + " " + HelpMe.getDisplayTime(destinationFlightDTO.getFlightDepatureTime());
//        String drop_off = HelpMe.getDisplayDate(holdOrderDTO.getDropOffTime())
//                + " " + HelpMe.getDisplayTime(holdOrderDTO.getDropOffTime());
        setViewText(R.id.drop_off_tv, drop_off);

        String pick_up = HelpMe.getDisplayDate(arrivalFlightDTO.getFlightArrivalTime())
                + " " + HelpMe.getDisplayTime(arrivalFlightDTO.getFlightArrivalTime());
//        String pick_up = HelpMe.getDisplayDate(holdOrderDTO.getPickUpTime())
//                + " " + HelpMe.getDisplayTime(holdOrderDTO.getPickUpTime());
        setViewText(R.id.pickup_tv, pick_up);

        //setting duration in pick up and drop off time.
        setViewText(R.id.duration_tv,
                HelpMe.getDurationTime(holdOrderDTO.getDropOffTime(),
                        holdOrderDTO.getPickUpTime()));

        //setting departure fligh details.
        setViewText(R.id.departure_flight_no_tv, destinationFlightDTO.getFlightNumber());
        String departure_flight_arrival = HelpMe.getDisplayDate(destinationFlightDTO.getFlightArrivalTime())
                + " " + HelpMe.getDisplayTime(destinationFlightDTO.getFlightArrivalTime());
        setViewText(R.id.departure_flight_arrival_tv, departure_flight_arrival);
        setViewText(R.id.departure_flight_departure_tv, drop_off);

        //setting arrival flight details.
        setViewText(R.id.arrival_flight_no_tv, arrivalFlightDTO.getFlightNumber());
        setViewText(R.id.arrival_flight_arrival_tv, pick_up);
        String arrival_flight_departure_time = HelpMe.getDisplayDate(arrivalFlightDTO.getFlightDepatureTime())
                + " " + HelpMe.getDisplayTime(arrivalFlightDTO.getFlightDepatureTime());
        setViewText(R.id.arrival_flight_departure_tv, arrival_flight_departure_time);

        //setting vehicle details.
        VehicleInfoDTO infoDTO = createOrderResponseDTO.getVehicleInfo();
        setViewText(R.id.vehicle_make_tv, infoDTO.getVehicleMake());
        setViewText(R.id.vehicle_model_tv, infoDTO.getVehicleModel());
        setViewText(R.id.vehicle_color_tv, infoDTO.getVehicleColor());
        setViewText(R.id.vehicle_platenumber_tv, infoDTO.getVehiclePlateNumber());


        setViewText(R.id.price_tv, orderStatusDTO.getOrderTotal());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm:

                String auth = ParkingPreference.getKeyAuthtoken(mActivity);
                holdOrderDTO = (HoldOrderDTO) getIntent().
                        getSerializableExtra(AppConstants.HOLD_ORDER_KEY);
                String request = new Gson().toJson(holdOrderDTO);

                AppUtils.showLog(TAG, request);
                HoldOrderAPIHandler holdOrderAPIHandler = new HoldOrderAPIHandler(
                        mActivity, request, auth, manageHoldOrderResponse());

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


                //purchaseOrderDTO = new Gson().fromJson(response, PurchaseOrderDTO.class);

                Intent intent = new Intent(HoldOrderScreen.this,
                        OrderDetailsScreenNew.class);
                intent.putExtra(AppConstants.PURCHASE_ORDER_KEY, purchaseOrderDTO);
                //intent.putExtra(AppConstants.HOLD_ORDER_RESPONSE_KEY, holdOrderResponseDTO);
                intent.putExtra(AppConstants.ORDER_SUMMARY_KEY, createOrderResponseDTO);
                startActivity(intent);

            }

            @Override
            public void onFailOfResponse(Object... arguments) {

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
}
