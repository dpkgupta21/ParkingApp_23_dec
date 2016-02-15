package app.parking.com.parkingapp.home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import app.parking.com.parkingapp.R;
import app.parking.com.parkingapp.utils.AppConstants;

public class AddServicesScreen extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private TextView toolbar_title;
    private LinearLayout refill, car_wash, oil_change;
    private CheckBox refill_cb, car_wash_cb, oil_change_cb;

    private RelativeLayout submit_button;

    private ArrayList<ServicesModel> servicesModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_services_screen);
        initViews();
        assignClicks();

    }

    private void assignClicks() {
        submit_button.setOnClickListener(this);
    }

    private void initViews() {


        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        refill = (LinearLayout) findViewById(R.id.refill);
        car_wash = (LinearLayout) findViewById(R.id.car_wash);
        oil_change = (LinearLayout) findViewById(R.id.oil_change);
        submit_button = (RelativeLayout) findViewById(R.id.submit_button);

        car_wash_cb = (CheckBox) findViewById(R.id.car_wash_cb);
        refill_cb = (CheckBox) findViewById(R.id.refill_cb);
        oil_change_cb = (CheckBox) findViewById(R.id.oil_change_cb);


        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.add_services));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationIcon(R.drawable.back_button);


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

                servicesModelArrayList = new ArrayList<ServicesModel>();
                ServicesModel servicesModel;

                if (car_wash_cb.isChecked()) {
                    servicesModel = new ServicesModel();
                    servicesModel.setServiceCost("$20");
                    servicesModel.setServiceName("Car Wash");
                    servicesModelArrayList.add(servicesModel);
                }
                if (oil_change_cb.isChecked()) {
                    servicesModel = new ServicesModel();
                    servicesModel.setServiceCost("$20");
                    servicesModel.setServiceName("Oil Change");
                    servicesModelArrayList.add(servicesModel);
                }

                if (refill_cb.isChecked()) {
                    servicesModel = new ServicesModel();
                    servicesModel.setServiceCost("$20");
                    servicesModel.setServiceName("Re-Fill Petrol");
                    servicesModelArrayList.add(servicesModel);
                }

                intent.putExtra(AppConstants.SERVICE, servicesModelArrayList);
                setResult(RESULT_OK, intent);
                finish();

                break;
        }

    }
}
