package app.parking.com.parkingapp.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import app.parking.com.parkingapp.R;
import app.parking.com.parkingapp.model.HoldOrderResponseDTO;
import app.parking.com.parkingapp.model.PurchaseOrderDTO;
import app.parking.com.parkingapp.model.PurchaseOrderResponseDTO;
import app.parking.com.parkingapp.utils.AppConstants;

public class PurchaseReceiptDetailsScreen extends AppCompatActivity {
    private Toolbar mToolbar;
    private PurchaseOrderResponseDTO purchaseOrderResponseDTO;
    private String TAG = PurchaseReceiptDetailsScreen.class.getSimpleName();
    private TextView venue_et, drop_time_et, picktime_tv_et, order_total_et, tax_et, purchase_id_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.purchase_reciept_screen);
        initViews();
    }


    private void initViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.order_confirm));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationIcon(R.drawable.back_button);

        venue_et = (TextView) findViewById(R.id.venue_et);
        drop_time_et = (TextView) findViewById(R.id.drop_time_et);
        picktime_tv_et = (TextView) findViewById(R.id.picktime_tv_et);
        order_total_et = (TextView) findViewById(R.id.order_total_et);
        tax_et = (TextView) findViewById(R.id.tax_et);
        purchase_id_tv = (TextView) findViewById(R.id.purchase_id_tv);

        if (getIntent() != null) {

            purchaseOrderResponseDTO = (PurchaseOrderResponseDTO) getIntent().getSerializableExtra(AppConstants.PURCHASE_ORDER_RESPONSE);

        } else {
            purchaseOrderResponseDTO = new PurchaseOrderResponseDTO();
        }


        venue_et.setText(purchaseOrderResponseDTO.getVenueName());
        drop_time_et.setText(purchaseOrderResponseDTO.getDropOffTime());
        picktime_tv_et.setText(purchaseOrderResponseDTO.getPickUpTime());
        order_total_et.setText(purchaseOrderResponseDTO.getOrderTotal());
        tax_et.setText(purchaseOrderResponseDTO.getOrderTax());
        purchase_id_tv.setText(purchaseOrderResponseDTO.getPaymentTransactionId());

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
