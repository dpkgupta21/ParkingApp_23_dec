package app.parking.com.parkingapp.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.gson.Gson;

import app.parking.com.parkingapp.R;
import app.parking.com.parkingapp.activity.BaseActivity;
import app.parking.com.parkingapp.model.PurchaseOrderDTO;
import app.parking.com.parkingapp.preferences.ParkingPreference;
import app.parking.com.parkingapp.utils.AppConstants;
import app.parking.com.parkingapp.utils.AppUtils;
import app.parking.com.parkingapp.webservices.handler.PurchaseOrderAPIHandler;
import app.parking.com.parkingapp.webservices.ihelper.WebAPIResponseListener;

public class OrderConfirmation extends BaseActivity implements View.OnClickListener {
    private Toolbar mToolbar;
    private RelativeLayout make_payment;
    private PurchaseOrderDTO purchaseOrderDTO;
    private String TAG = OrderConfirmation.class.getSimpleName();
    private Activity mActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_confirmation);

        mActivity= OrderConfirmation.this;
        initViews();
        assignClicks();

    }

    private void assignClicks() {
        make_payment.setOnClickListener(this);
    }

    private void initViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.order_confirm));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationIcon(R.drawable.back_button);
        make_payment = (RelativeLayout) findViewById(R.id.make_payment);


        if (getIntent() != null) {
            purchaseOrderDTO = (PurchaseOrderDTO) getIntent().
                    getSerializableExtra(AppConstants.PURCHASE_ORDER_KEY);
            AppUtils.showLog(TAG, purchaseOrderDTO.getPickUpTime() + " " +
                    purchaseOrderDTO.getDropOffTime());
        } else {
            purchaseOrderDTO = new PurchaseOrderDTO();
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

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.make_payment:

                purchaseOrderDTO.setUserEmail(ParkingPreference.getEmailId(mActivity));
                String auth = ParkingPreference.getKeyAuthtoken(mActivity);
                String request = new Gson().toJson(purchaseOrderDTO);
                String userId = ParkingPreference.getUserid(mActivity);

                new PurchaseOrderAPIHandler(OrderConfirmation.this, request,
                        auth, userId,
                        managePurchaseResponse());

                break;
        }

    }

    private WebAPIResponseListener managePurchaseResponse() {

        WebAPIResponseListener webAPIResponseListener = new WebAPIResponseListener() {
            @Override
            public void onSuccessOfResponse(Object... arguments) {
                String response = (String) arguments[0];
                AppUtils.showLog(TAG, response);
                startActivity(new Intent(OrderConfirmation.this, OrderAmountScreen.class));

            }

            @Override
            public void onFailOfResponse(Object... arguments) {

            }
        };

        return webAPIResponseListener;
    }
}
