package app.parking.com.parkingapp.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import app.parking.com.parkingapp.R;
import app.parking.com.parkingapp.activity.BaseActivity;
import app.parking.com.parkingapp.model.CreateOrderDTO;
import app.parking.com.parkingapp.model.VehicleDTO;
import app.parking.com.parkingapp.utils.AppConstants;
import app.parking.com.parkingapp.utils.AppUtils;

public class VehicleDetailsScreen extends BaseActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private CreateOrderDTO createOrderDTO;
    private String TAG = VehicleDetailsScreen.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vehicle_detail_screen);
        initViews();
        assignClicks();
    }

    private void assignClicks() {
        setClick(R.id.make_ll);
        setClick(R.id.color_ll);
        setClick(R.id.model_et);
        setClick(R.id.make_ll);
        setClick(R.id.toolbar_right_rl);

    }

    private void initViews() {

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setViewVisibility(R.id.toolbar_title, View.VISIBLE);
        setViewText(R.id.toolbar_title, getResources().getString(R.string.parkforu));

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(" ");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationIcon(R.drawable.back_button);


        setViewVisibility(R.id.toolbar_right_rl, View.VISIBLE);
        setViewText(R.id.toolbar_right_tv, getResources().getString(R.string.next));


        if (getIntent() != null) {
            createOrderDTO = (CreateOrderDTO) getIntent().getSerializableExtra(AppConstants.CREATE_ORDER);
            AppUtils.showLog(TAG, createOrderDTO.getPickUpTime() + " " + createOrderDTO.getDropOffTime());
        } else {
            createOrderDTO = new CreateOrderDTO();
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_right_rl:
                VehicleDTO vehicleDTO = new VehicleDTO();
                vehicleDTO.setColor(getViewText(R.id.color_value_tv));
                vehicleDTO.setMake(getViewText(R.id.make_value_tv));
                vehicleDTO.setModel(getViewText(R.id.model_value_tv));
                vehicleDTO.setPlateNo(getViewText(R.id.number_plate_et));
                createOrderDTO.setVehicle(vehicleDTO);

                Intent intent = new Intent(getApplicationContext(), AddServicesScreen.class);
                startActivity(intent.putExtra(AppConstants.CREATE_ORDER, createOrderDTO));

                break;
            case R.id.make_ll:
                TextView make_value_tv = (TextView) findViewById(R.id.make_value_tv);
                AppDialogs.selectCarMake(this, make_value_tv);
                break;

            case R.id.model_et:
                TextView model_value_tv = (TextView) findViewById(R.id.model_value_tv);
                AppDialogs.selectCarModel(this, model_value_tv);
                break;
            case R.id.color_ll:
                TextView color_value_tv = (TextView) findViewById(R.id.color_value_tv);
                AppDialogs.selectCarColor(this, color_value_tv);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


}
