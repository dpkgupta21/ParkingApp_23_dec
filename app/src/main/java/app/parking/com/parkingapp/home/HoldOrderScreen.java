package app.parking.com.parkingapp.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
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
import app.parking.com.parkingapp.model.OrderConfirmationDTO;
import app.parking.com.parkingapp.model.OrderStatusDTO;
import app.parking.com.parkingapp.model.PurchaseOrderDTO;
import app.parking.com.parkingapp.preferences.SessionManager;
import app.parking.com.parkingapp.utils.AppConstants;
import app.parking.com.parkingapp.utils.AppUtils;
import app.parking.com.parkingapp.webservices.handler.HoldOrderAPIHandler;
import app.parking.com.parkingapp.webservices.ihelper.WebAPIResponseListener;

public class HoldOrderScreen extends BaseActivity implements View.OnClickListener {
    private Toolbar mToolbar;
    private Button confirm_button;
    private HoldOrderDTO holdOrderDTO;
    private CreateOrderResponseDTO createOrderResponseDTO;
    private HoldOrderResponseDTO holdOrderResponseDTO;
    private PurchaseOrderDTO purchaseOrderDTO;
    private String TAG = HoldOrderScreen.class.getSimpleName();
    //private TextView dest_tv, arrival_tv, drop_off_tv, pickup_tv, duration_tv, price_tv, toolbar_title;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hold_order_screen);
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
            holdOrderDTO = (HoldOrderDTO) getIntent().getSerializableExtra(AppConstants.HOLD_ORDER_KEY);
            createOrderResponseDTO = (CreateOrderResponseDTO) getIntent().getSerializableExtra(AppConstants.ORDER_SUMMARY_KEY);
            AppUtils.showLog(TAG, holdOrderDTO.getPickUpTime() + " " + holdOrderDTO.getDropOffTime());
        } else {
            holdOrderDTO = new HoldOrderDTO();
            createOrderResponseDTO = new CreateOrderResponseDTO();
        }

        setResponseText(createOrderResponseDTO);
//        venue_et.setText(createOrderResponseDTO.getVenueName());
//        drop_time_et.setText(createOrderResponseDTO.getDropOffTime());
//        picktime_tv_et.setText(createOrderResponseDTO.getPickUpTime());

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
        setViewText(R.id.drop_off_tv, destinationFlightDTO.getFlightDepatureTime());
        setViewText(R.id.pickup_tv, arrivalFlightDTO.getFlightArrivalTime());
        setViewText(R.id.duration_tv, "");
        setViewText(R.id.price_tv, orderStatusDTO.getOrderTotal());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm:

                String auth = SessionManager.getInstance(HoldOrderScreen.this).getAuthToken();
                String request = new Gson().toJson(holdOrderDTO);
                AppUtils.showLog(TAG, request);
                HoldOrderAPIHandler holdOrderAPIHandler = new HoldOrderAPIHandler(
                        HoldOrderScreen.this, request, auth, manageHoldOrderResponse());

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
                JSONObject jsonObject = null;

                String slotId = "";
                try {
                    jsonObject = new JSONObject(response);
                    if (jsonObject.has("_slotId")) {
                        slotId = jsonObject.getString("_slotId");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                purchaseOrderDTO = new PurchaseOrderDTO();
                purchaseOrderDTO = new Gson().fromJson(response, PurchaseOrderDTO.class);
                purchaseOrderDTO.setSlotId(slotId);

                Intent intent = new Intent(HoldOrderScreen.this,
                        OrderDetailsScreenNew.class);
                intent.putExtra(AppConstants.PURCHASE_ORDER_KEY, purchaseOrderDTO);
                intent.putExtra(AppConstants.HOLD_ORDER_RESPONSE_KEY, holdOrderResponseDTO);
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
