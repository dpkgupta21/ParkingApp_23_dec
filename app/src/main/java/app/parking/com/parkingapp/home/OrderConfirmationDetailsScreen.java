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
import com.stripe.android.model.Card;

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
import app.parking.com.parkingapp.webservices.handler.PurchaseOrderAPIHandler;
import app.parking.com.parkingapp.webservices.ihelper.WebAPIResponseListener;

public class OrderConfirmationDetailsScreen extends AppCompatActivity implements View.OnClickListener {
    private Toolbar mToolbar;
    private RelativeLayout confirm_button;
    private PurchaseOrderDTO purchaseOrderDTO;
    private HoldOrderResponseDTO holdOrderResponseDTO;
    private String TAG = OrderConfirmationDetailsScreen.class.getSimpleName();
    private TextView venue_et, drop_time_et, picktime_tv_et, order_total_et, tax_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_confirm_details_screen);
        initViews();
        assignClicks();
    }

    private void assignClicks() {
        confirm_button.setOnClickListener(this);
    }

    private void initViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.order_confirm));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationIcon(R.drawable.back_button);

        confirm_button = (RelativeLayout) findViewById(R.id.confirm_button);
        venue_et = (TextView) findViewById(R.id.venue_et);
        drop_time_et = (TextView) findViewById(R.id.drop_time_et);
        picktime_tv_et = (TextView) findViewById(R.id.picktime_tv_et);
        order_total_et = (TextView) findViewById(R.id.order_total_et);
        tax_et = (TextView) findViewById(R.id.tax_et);

        if (getIntent() != null) {
            purchaseOrderDTO = (PurchaseOrderDTO) getIntent().getSerializableExtra(AppConstants.PURCHASE_ORDER_KEY);
            holdOrderResponseDTO = (HoldOrderResponseDTO) getIntent().getSerializableExtra(AppConstants.HOLD_ORDER_RESPONSE_KEY);
        } else {
            purchaseOrderDTO = new PurchaseOrderDTO();
            holdOrderResponseDTO = new HoldOrderResponseDTO();
        }


        venue_et.setText(holdOrderResponseDTO.getVenueName());
        drop_time_et.setText(holdOrderResponseDTO.getDropOffTime());
        picktime_tv_et.setText(holdOrderResponseDTO.getPickUpTime());
        order_total_et.setText(holdOrderResponseDTO.getOrderTotal());
        tax_et.setText(holdOrderResponseDTO.getOrderTax());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirm_button:

                startActivity(new Intent(OrderConfirmationDetailsScreen.this, CreditCardScreen.class).putExtra(AppConstants.PURCHASE_ORDER_KEY, purchaseOrderDTO));
                break;
        }
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
