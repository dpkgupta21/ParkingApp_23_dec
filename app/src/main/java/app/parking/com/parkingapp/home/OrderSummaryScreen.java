package app.parking.com.parkingapp.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import app.parking.com.parkingapp.R;
import app.parking.com.parkingapp.model.CreateOrderResponseDTO;
import app.parking.com.parkingapp.model.HoldOrderDTO;
import app.parking.com.parkingapp.model.HoldOrderResponseDTO;
import app.parking.com.parkingapp.model.PurchaseOrderDTO;
import app.parking.com.parkingapp.preferences.SessionManager;
import app.parking.com.parkingapp.utils.AppConstants;
import app.parking.com.parkingapp.utils.AppUtils;
import app.parking.com.parkingapp.webservices.handler.HoldOrderAPIHandler;
import app.parking.com.parkingapp.webservices.ihelper.WebAPIResponseListener;

public class OrderSummaryScreen extends AppCompatActivity implements View.OnClickListener {
    private Toolbar mToolbar;
    private RelativeLayout confirm_button;
    private HoldOrderDTO holdOrderDTO;
    private CreateOrderResponseDTO createOrderResponseDTO;
    private HoldOrderResponseDTO holdOrderResponseDTO;
    private PurchaseOrderDTO purchaseOrderDTO;
    private String TAG = OrderSummaryScreen.class.getSimpleName();
    private TextView venue_et, drop_time_et, picktime_tv_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_summary_screen);
        initViews();
        assignClicks();
    }

    private void assignClicks() {
        confirm_button.setOnClickListener(this);
    }

    private void initViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.order_summary));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationIcon(R.drawable.back_button);

        confirm_button = (RelativeLayout) findViewById(R.id.confirm_button);
        venue_et = (TextView) findViewById(R.id.venue_et);
        drop_time_et = (TextView) findViewById(R.id.drop_time_et);
        picktime_tv_et = (TextView) findViewById(R.id.picktime_tv_et);

        if (getIntent() != null) {
            holdOrderDTO = (HoldOrderDTO) getIntent().getSerializableExtra(AppConstants.HOLD_ORDER_KEY);
            createOrderResponseDTO = (CreateOrderResponseDTO) getIntent().getSerializableExtra(AppConstants.ORDER_SUMMARY_KEY);
            AppUtils.showLog(TAG, holdOrderDTO.getPickUpTime() + " " + holdOrderDTO.getDropOffTime());
        } else {
            holdOrderDTO = new HoldOrderDTO();
            createOrderResponseDTO = new CreateOrderResponseDTO();
        }


//        venue_et.setText(createOrderResponseDTO.getVenueName());
//        drop_time_et.setText(createOrderResponseDTO.getDropOffTime());
//        picktime_tv_et.setText(createOrderResponseDTO.getPickUpTime());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirm_button:

                String auth = SessionManager.getInstance(OrderSummaryScreen.this).getAuthToken();
                String request = new Gson().toJson(holdOrderDTO);
                AppUtils.showLog(TAG, request);
                HoldOrderAPIHandler holdOrderAPIHandler = new HoldOrderAPIHandler(OrderSummaryScreen.this, request, auth, manageHoldOrderResponse());

                break;
        }
    }

    private WebAPIResponseListener manageHoldOrderResponse() {

        WebAPIResponseListener webAPIResponseListener = new WebAPIResponseListener() {
            @Override
            public void onSuccessOfResponse(Object... arguments) {
                String response = (String) arguments[0];

                holdOrderResponseDTO = new Gson().fromJson(response, HoldOrderResponseDTO.class);
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

                Intent intent = new Intent(OrderSummaryScreen.this, OrderConfirmationDetailsScreen.class);
                intent.putExtra(AppConstants.PURCHASE_ORDER_KEY, purchaseOrderDTO);
                intent.putExtra(AppConstants.HOLD_ORDER_RESPONSE_KEY, holdOrderResponseDTO);
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
