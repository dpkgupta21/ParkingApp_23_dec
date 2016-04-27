package app.parking.com.parkingapp.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import app.parking.com.parkingapp.R;
import app.parking.com.parkingapp.activity.BaseActivity;
import app.parking.com.parkingapp.model.CreateOrderDTO;
import app.parking.com.parkingapp.model.CreateOrderResponseDTO;
import app.parking.com.parkingapp.model.DestinationFlightInfo;
import app.parking.com.parkingapp.model.FlightArrivalDTO;
import app.parking.com.parkingapp.model.FlightInfoDTO;
import app.parking.com.parkingapp.model.HoldOrderDTO;
import app.parking.com.parkingapp.model.ListOfServicesDTO;
import app.parking.com.parkingapp.model.OrderConfirmationDTO;
import app.parking.com.parkingapp.model.OrderStatusDTO;
import app.parking.com.parkingapp.model.Service;
import app.parking.com.parkingapp.model.ServiceInfoDTO;
import app.parking.com.parkingapp.model.VehicleInfoDTO;
import app.parking.com.parkingapp.preferences.SessionManager;
import app.parking.com.parkingapp.utils.AppConstants;
import app.parking.com.parkingapp.utils.AppUtils;
import app.parking.com.parkingapp.webservices.handler.CreateOrderAPIHandler;
import app.parking.com.parkingapp.webservices.handler.ServicesAPIHandler;
import app.parking.com.parkingapp.webservices.ihelper.WebAPIResponseListener;

public class OrderDetailsScreenNew extends BaseActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private TextView toolbar_title, toolbar_right_tv, no_service_tv;
    private RelativeLayout toolbar_right_rl;
    private ImageView payment;
    private ListView services_lv;
    private RelativeLayout submit_button, action_button;
    private CreateOrderResponseDTO createOrderResponseDTO;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_details_screen_new);
        initViews();
        assignClicks();
        if (createOrderResponseDTO.getOrderConfirmation().getPaymentTransactionId() != null &&
                !createOrderResponseDTO.getOrderConfirmation().getPaymentTransactionId().equalsIgnoreCase("")) {

        } else {
            AppDialogs.paymentDialog(this, createOrderResponseDTO);
        }

    }

    private void assignClicks() {
        action_button.setOnClickListener(this);
        payment.setOnClickListener(this);
        ImageView flight_details = (ImageView) findViewById(R.id.flight_details);
        ImageView vehicle_details = (ImageView) findViewById(R.id.vehicle_details);
        ImageView service_details = (ImageView) findViewById(R.id.service_details);
        ImageView order_confirmation = (ImageView) findViewById(R.id.order_confirmation);
        ImageView drop_off = (ImageView) findViewById(R.id.drop_off);
        ImageView pick_up = (ImageView) findViewById(R.id.pick_up);

        flight_details.setOnClickListener(this);
        vehicle_details.setOnClickListener(this);
        service_details.setOnClickListener(this);
        order_confirmation.setOnClickListener(this);
        drop_off.setOnClickListener(this);
        pick_up.setOnClickListener(this);


    }

    private void initViews() {


        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        services_lv = (ListView) findViewById(R.id.services_lv);
        no_service_tv = (TextView) findViewById(R.id.no_service_tv);
        submit_button = (RelativeLayout) findViewById(R.id.submit_button);
        action_button = (RelativeLayout) findViewById(R.id.action_button);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(" ");
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mToolbar.setNavigationIcon(R.drawable.back_button);
        toolbar_right_rl = (RelativeLayout) findViewById(R.id.toolbar_right_rl);
        toolbar_right_tv = (TextView) findViewById(R.id.toolbar_right_tv);
        toolbar_title.setVisibility(View.VISIBLE);
        toolbar_title.setText(getResources().getString(R.string.parkforu));

        toolbar_right_rl.setVisibility(View.INVISIBLE);

        payment = (ImageView) findViewById(R.id.payment);

        createOrderResponseDTO = (CreateOrderResponseDTO) getIntent().getSerializableExtra(AppConstants.ORDER_SUMMARY_KEY);
        setValue();
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
            case R.id.flight_details:
                showDetailPopup(0);

                break;
            case R.id.vehicle_details:
                showDetailPopup(1);

                break;
            case R.id.service_details:
                showDetailPopup(2);

                break;

            case R.id.order_confirmation:
                showDetailPopup(3);

                break;

            case R.id.payment:
                showDetailPopup(4);

                break;

            case R.id.drop_off:
                showDetailPopup(5);

                break;
            case R.id.pick_up:
                showDetailPopup(6);

                break;
        }

    }

    private void setValue() {
        showFlightInfo();
        showVehicleInfo();
        showServiceInfo();
    }

    private void showFlightInfo() {
        FlightInfoDTO flightInfoDTO = createOrderResponseDTO.getFlightInfo();


        // Arrival Flight
        DestinationFlightInfo arrivalFlighDTO = flightInfoDTO.getArrivalFlight();

        setViewText(R.id.txt_flight_number_val, arrivalFlighDTO.getFlightNumber());
        setViewText(R.id.txt_flight_name_val, arrivalFlighDTO.getFlightName());
        setViewText(R.id.txt_arrival_time_val, arrivalFlighDTO.getFlightArrivalTime());
        setViewText(R.id.txt_destination_time_val, arrivalFlighDTO.getFlightDepatureTime());
        setViewText(R.id.txt_origin_val, arrivalFlighDTO.getOrigin());
        setViewText(R.id.txt_destination_val, arrivalFlighDTO.getDestination());


        // Destination Flight
        DestinationFlightInfo destinationFlighDTO = flightInfoDTO.getDestinationFlight();

        setViewText(R.id.txt_dest_flight_number_val, destinationFlighDTO.getFlightNumber());
        setViewText(R.id.txt_dest_flight_name_val, destinationFlighDTO.getFlightName());
        setViewText(R.id.txt_dest_arrival_time_val, destinationFlighDTO.getFlightArrivalTime());
        setViewText(R.id.txt_dest_destination_time_val, destinationFlighDTO.getFlightDepatureTime());
        setViewText(R.id.txt_dest_origin_val, destinationFlighDTO.getOrigin());
        setViewText(R.id.txt_dest_destination_val, destinationFlighDTO.getDestination());

    }

    private void showVehicleInfo() {
        VehicleInfoDTO vehicleInfoDTO = createOrderResponseDTO.getVehicleInfo();

        setViewText(R.id.txt_vehicle_make_val, vehicleInfoDTO.getVehicleMake());
        setViewText(R.id.txt_vehicle_model_val, vehicleInfoDTO.getVehicleModel());
        setViewText(R.id.txt_vehicle_color_val, vehicleInfoDTO.getVehicleColor());
        setViewText(R.id.txt_vehicle_plate_number_val, "");
    }

    private void showServiceInfo() {
        ServiceInfoDTO serviceInfoDTO = createOrderResponseDTO.getServiceInfo();
        List<Service> servicesList = serviceInfoDTO.getServices();
        for (Service mService : servicesList) {
            setViewText(R.id.txt_service_val, mService.getName());
            setViewText(R.id.txt_service_price_val, mService.getPrice());

        }


    }

    private void showOrderInfo() {

    }

    private void showPaymentInfo() {

    }

    private void showDropOffInfo() {

    }

    private void showPickUpInfo() {

    }

    private void showDetailPopup(int status) {
        switch (status) {
            case 0:
                setViewVisibility(R.id.relative_flight_info, 1);
                setViewVisibility(R.id.relative_vehicle_info, 0);
                setViewVisibility(R.id.relative_service_info, 0);
                setViewVisibility(R.id.relative_order_info, 0);
                setViewVisibility(R.id.relative_payment_info, 0);
                setViewVisibility(R.id.relative_drop_off_info, 0);
                setViewVisibility(R.id.relative_pick_up_info, 0);
                break;
            case 1:
                setViewVisibility(R.id.relative_flight_info, 0);
                setViewVisibility(R.id.relative_vehicle_info, 1);
                setViewVisibility(R.id.relative_service_info, 0);
                setViewVisibility(R.id.relative_order_info, 0);
                setViewVisibility(R.id.relative_payment_info, 0);
                setViewVisibility(R.id.relative_drop_off_info, 0);
                setViewVisibility(R.id.relative_pick_up_info, 0);
                break;
            case 2:
                setViewVisibility(R.id.relative_flight_info, 0);
                setViewVisibility(R.id.relative_vehicle_info, 0);
                setViewVisibility(R.id.relative_service_info, 1);
                setViewVisibility(R.id.relative_order_info, 0);
                setViewVisibility(R.id.relative_payment_info, 0);
                setViewVisibility(R.id.relative_drop_off_info, 0);
                setViewVisibility(R.id.relative_pick_up_info, 0);
                break;
            case 3:
                setViewVisibility(R.id.relative_flight_info, 0);
                setViewVisibility(R.id.relative_vehicle_info, 0);
                setViewVisibility(R.id.relative_service_info, 0);
                setViewVisibility(R.id.relative_order_info, 1);
                setViewVisibility(R.id.relative_payment_info, 0);
                setViewVisibility(R.id.relative_drop_off_info, 0);
                setViewVisibility(R.id.relative_pick_up_info, 0);
                break;
            case 4:
                setViewVisibility(R.id.relative_flight_info, 0);
                setViewVisibility(R.id.relative_vehicle_info, 0);
                setViewVisibility(R.id.relative_service_info, 0);
                setViewVisibility(R.id.relative_order_info, 0);
                setViewVisibility(R.id.relative_payment_info, 1);
                setViewVisibility(R.id.relative_drop_off_info, 0);
                setViewVisibility(R.id.relative_pick_up_info, 0);
                break;
            case 5:
                setViewVisibility(R.id.relative_flight_info, 0);
                setViewVisibility(R.id.relative_vehicle_info, 0);
                setViewVisibility(R.id.relative_service_info, 0);
                setViewVisibility(R.id.relative_order_info, 0);
                setViewVisibility(R.id.relative_payment_info, 0);
                setViewVisibility(R.id.relative_drop_off_info, 1);
                setViewVisibility(R.id.relative_pick_up_info, 0);
                break;
            case 6:
                setViewVisibility(R.id.relative_flight_info, 0);
                setViewVisibility(R.id.relative_vehicle_info, 0);
                setViewVisibility(R.id.relative_service_info, 0);
                setViewVisibility(R.id.relative_order_info, 0);
                setViewVisibility(R.id.relative_payment_info, 0);
                setViewVisibility(R.id.relative_drop_off_info, 0);
                setViewVisibility(R.id.relative_pick_up_info, 1);
                break;

        }
    }

}