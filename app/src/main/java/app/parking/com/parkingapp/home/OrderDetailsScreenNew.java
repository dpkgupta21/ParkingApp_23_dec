package app.parking.com.parkingapp.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import app.parking.com.parkingapp.R;
import app.parking.com.parkingapp.activity.BaseActivity;
import app.parking.com.parkingapp.model.CreateOrderResponseDTO;
import app.parking.com.parkingapp.model.DestinationFlightInfo;
import app.parking.com.parkingapp.model.FlightInfoDTO;
import app.parking.com.parkingapp.model.PurchaseOrderDTO;
import app.parking.com.parkingapp.model.Service;
import app.parking.com.parkingapp.model.ServiceInfoDTO;
import app.parking.com.parkingapp.model.VehicleInfoDTO;
import app.parking.com.parkingapp.navigationDrawer.UserNavigationDrawerActivity;
import app.parking.com.parkingapp.utils.AppConstants;

public class OrderDetailsScreenNew extends BaseActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private TextView toolbar_title, toolbar_right_tv, no_service_tv;
    private RelativeLayout toolbar_right_rl;
    private ImageView payment;
    private ListView services_lv;
    ///private RelativeLayout submit_button;
    private CreateOrderResponseDTO createOrderResponseDTO;
    private PurchaseOrderDTO purchaseOrderDTO;
    private boolean isFlight;
    private boolean isVehicle;
    private boolean isService;
    private boolean isPayment;
    private boolean isOrder;
    private boolean firstTime;
    private boolean isPickUpInfo;
    private boolean isDropOffInfo;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_details_screen_new);
        initViews();
        assignClicks();
//        Toast.makeText(OrderDetailsScreenNew.this, "Transaction Id :" +
//                createOrderResponseDTO.getOrderConfirmation().getPaymentTransactionId(),
//                Toast.LENGTH_SHORT).show();
        if (getIntent() != null) {
            purchaseOrderDTO = (PurchaseOrderDTO) getIntent().
                    getSerializableExtra(AppConstants.PURCHASE_ORDER_KEY);
        }
        createOrderResponseDTO = (CreateOrderResponseDTO) getIntent().
                getSerializableExtra(AppConstants.ORDER_SUMMARY_KEY);


        if (createOrderResponseDTO.getOrderConfirmation().getPaymentTransactionId() != null &&
                !createOrderResponseDTO.getOrderConfirmation().
                        getPaymentTransactionId().equalsIgnoreCase("")) {

        } else {
            AppDialogs.paymentDialog(this, createOrderResponseDTO, purchaseOrderDTO);
        }


        setValue();
    }

    private void assignClicks() {
        payment.setOnClickListener(this);
        ImageView flight_details = (ImageView) findViewById(R.id.flight_details);
        ImageView vehicle_details = (ImageView) findViewById(R.id.vehicle_details);
        ImageView service_details = (ImageView) findViewById(R.id.service_details);
        ImageView order_confirmation = (ImageView) findViewById(R.id.order_confirmation);
        ImageView drop_off = (ImageView) findViewById(R.id.drop_off);
        ImageView pick_up = (ImageView) findViewById(R.id.pick_up);
        vehicle_details.setVisibility(View.VISIBLE);
        flight_details.setOnClickListener(this);
        vehicle_details.setOnClickListener(this);
        service_details.setOnClickListener(this);
        order_confirmation.setOnClickListener(this);
        drop_off.setOnClickListener(this);
        pick_up.setOnClickListener(this);
        setClick(R.id.action_button);


    }

    private void initViews() {


        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        services_lv = (ListView) findViewById(R.id.services_lv);
        no_service_tv = (TextView) findViewById(R.id.no_service_tv);
        //submit_button = (RelativeLayout) findViewById(R.id.submit_button);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");

        //mToolbar.setNavigationIcon(R.drawable.back_button);
        toolbar_right_rl = (RelativeLayout) findViewById(R.id.toolbar_right_rl);
        toolbar_right_tv = (TextView) findViewById(R.id.toolbar_right_tv);
        toolbar_title.setVisibility(View.VISIBLE);
        toolbar_title.setText(getResources().getString(R.string.parkforu));
        firstTime = true;
        toolbar_right_rl.setVisibility(View.INVISIBLE);

        payment = (ImageView) findViewById(R.id.payment);

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
                if (!isFlight) {
                    showFlightInfo();
                } else {
                    showDetailPopup(7);
                    isFlight = false;
                }

                break;
            case R.id.vehicle_details:
                if (!isVehicle) {
                    showVehicleInfo();
                } else {
                    showDetailPopup(7);
                    isVehicle = false;
                }


                break;
            case R.id.service_details:
                if (!isService) {
                    showServiceInfo();
                } else {
                    showDetailPopup(7);
                    isService = false;
                }
                break;

            case R.id.order_confirmation:
                if (!isOrder) {
                    showDetailPopup(3);
                    isOrder = true;
                } else {
                    showDetailPopup(7);
                    isOrder = false;
                }

                break;

            case R.id.payment:
                if (!isPayment) {
                    showDetailPopup(4);
                    isPayment = true;
                } else {
                    showDetailPopup(7);
                    isPayment = false;
                }
                break;
            case R.id.drop_off:

                break;
            case R.id.pick_up:
                break;
            case R.id.action_button:
                goToDashBoard();
                break;
        }

    }

    private void goToDashBoard() {
        Intent intent = new Intent(OrderDetailsScreenNew.this, UserNavigationDrawerActivity.class);
        startActivity(intent);
        this.finish();
    }

    private void setValue() {
        showFlightInfo();
//        showVehicleInfo();
//        showServiceInfo();
//        showOrderInfo();
//        showPaymentInfo();
    }

    private void showFlightInfo() {
        FlightInfoDTO flightInfoDTO = createOrderResponseDTO.getFlightInfo();

        if (flightInfoDTO != null) {
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
            showDetailPopup(0);
            isFlight = true;

        } else {
            setImageResourseBackground(R.id.flight_details, R.drawable.flight_detail_btn_white);
        }

    }

    private void showVehicleInfo() {
        VehicleInfoDTO vehicleInfoDTO = createOrderResponseDTO.getVehicleInfo();

        setViewText(R.id.txt_vehicle_make_val, vehicleInfoDTO.getVehicleMake());
        setViewText(R.id.txt_vehicle_model_val, vehicleInfoDTO.getVehicleModel());
        setViewText(R.id.txt_vehicle_color_val, vehicleInfoDTO.getVehicleColor());
        setViewText(R.id.txt_vehicle_plate_number_val, "");

        if (vehicleInfoDTO != null && !vehicleInfoDTO.getVehicleModel().equalsIgnoreCase("")) {
            showDetailPopup(1);
            isVehicle = true;
        } else {
            setImageResourseBackground(R.id.vehicle_details, R.drawable.vehicle_details_white);
        }
    }

    private void showServiceInfo() {
        ServiceInfoDTO serviceInfoDTO = createOrderResponseDTO.getServiceInfo();
        List<Service> servicesList = serviceInfoDTO.getServices();
        if (servicesList != null && servicesList.size() != 0) {
            for (Service mService : servicesList) {
                setViewText(R.id.txt_service_val, mService.getName());
                setViewText(R.id.txt_service_price_val, mService.getPrice());

            }

            showDetailPopup(2);
            isService = true;
        } else {
            setImageResourseBackground(R.id.service_details, R.drawable.add_servies_btn_white);
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
                isFlight = true;
                isVehicle = false;
                isOrder = false;
                isService = false;
                isPayment = false;

                checkClickedButton();
                if (!firstTime) {
                    setViewVisibility(R.id.relative_flight_info, View.VISIBLE);
                    setViewVisibility(R.id.relative_vehicle_info, View.GONE);
                    setViewVisibility(R.id.relative_service_info, View.GONE);
                    setViewVisibility(R.id.relative_order_info, View.GONE);
                    setViewVisibility(R.id.relative_payment_info, View.GONE);
                    setViewVisibility(R.id.relative_drop_off_info, View.GONE);
                    setViewVisibility(R.id.relative_pick_up_info, View.GONE);
                } else {
                    firstTime = false;
                }
                break;
            case 1:
                isFlight = false;
                isVehicle = true;
                isOrder = false;
                isService = false;
                isPayment = false;

                checkClickedButton();

                setViewVisibility(R.id.relative_flight_info, View.GONE);
                setViewVisibility(R.id.relative_vehicle_info, View.VISIBLE);
                setViewVisibility(R.id.relative_service_info, View.GONE);
                setViewVisibility(R.id.relative_order_info, View.GONE);
                setViewVisibility(R.id.relative_payment_info, View.GONE);
                setViewVisibility(R.id.relative_drop_off_info, View.GONE);
                setViewVisibility(R.id.relative_pick_up_info, View.GONE);
                break;
            case 2:
                isFlight = false;
                isVehicle = false;
                isOrder = false;
                isService = true;
                isPayment = false;

                checkClickedButton();

                setViewVisibility(R.id.relative_flight_info, View.GONE);
                setViewVisibility(R.id.relative_vehicle_info, View.GONE);
                setViewVisibility(R.id.relative_service_info, View.VISIBLE);
                setViewVisibility(R.id.relative_order_info, View.GONE);
                setViewVisibility(R.id.relative_payment_info, View.GONE);
                setViewVisibility(R.id.relative_drop_off_info, View.GONE);
                setViewVisibility(R.id.relative_pick_up_info, View.GONE);
                break;
            case 3:

                isFlight = false;
                isVehicle = false;
                isOrder = true;
                isService = false;
                isPayment = false;

                checkClickedButton();

                setViewVisibility(R.id.relative_flight_info, View.GONE);
                setViewVisibility(R.id.relative_vehicle_info, View.GONE);
                setViewVisibility(R.id.relative_service_info, View.GONE);
                setViewVisibility(R.id.relative_order_info, View.VISIBLE);
                setViewVisibility(R.id.relative_payment_info, View.GONE);
                setViewVisibility(R.id.relative_drop_off_info, View.GONE);
                setViewVisibility(R.id.relative_pick_up_info, View.GONE);
                break;
            case 4:

                isFlight = false;
                isVehicle = false;
                isOrder = false;
                isService = false;
                isPayment = true;

                checkClickedButton();

                setViewVisibility(R.id.relative_flight_info, View.GONE);
                setViewVisibility(R.id.relative_vehicle_info, View.GONE);
                setViewVisibility(R.id.relative_service_info, View.GONE);
                setViewVisibility(R.id.relative_order_info, View.GONE);
                setViewVisibility(R.id.relative_payment_info, View.VISIBLE);
                setViewVisibility(R.id.relative_drop_off_info, View.GONE);
                setViewVisibility(R.id.relative_pick_up_info, View.GONE);
                break;
            case 5:
                isFlight = false;
                isVehicle = false;
                isOrder = false;
                isService = false;
                isPayment = false;

                checkClickedButton();

                setViewVisibility(R.id.relative_flight_info, View.GONE);
                setViewVisibility(R.id.relative_vehicle_info, View.GONE);
                setViewVisibility(R.id.relative_service_info, View.GONE);
                setViewVisibility(R.id.relative_order_info, View.GONE);
                setViewVisibility(R.id.relative_payment_info, View.GONE);
                setViewVisibility(R.id.relative_drop_off_info, View.VISIBLE);
                setViewVisibility(R.id.relative_pick_up_info, View.GONE);
                break;
            case 6:
                isFlight = false;
                isVehicle = false;
                isOrder = false;
                isService = false;
                isPayment = false;

                checkClickedButton();

                setViewVisibility(R.id.relative_flight_info, View.GONE);
                setViewVisibility(R.id.relative_vehicle_info, View.GONE);
                setViewVisibility(R.id.relative_service_info, View.GONE);
                setViewVisibility(R.id.relative_order_info, View.GONE);
                setViewVisibility(R.id.relative_payment_info, View.GONE);
                setViewVisibility(R.id.relative_drop_off_info, View.GONE);
                setViewVisibility(R.id.relative_pick_up_info, View.VISIBLE);
                break;
            case 7:
                isFlight = false;
                isVehicle = false;
                isOrder = false;
                isService = false;
                isPayment = false;

                checkClickedButton();

                setViewVisibility(R.id.relative_flight_info, View.GONE);
                setViewVisibility(R.id.relative_vehicle_info, View.GONE);
                setViewVisibility(R.id.relative_service_info, View.GONE);
                setViewVisibility(R.id.relative_order_info, View.GONE);
                setViewVisibility(R.id.relative_payment_info, View.GONE);
                setViewVisibility(R.id.relative_drop_off_info, View.GONE);
                setViewVisibility(R.id.relative_pick_up_info, View.GONE);
                break;

        }
    }

    private void checkClickedButton() {
        if (isFlight && !firstTime) {
            setImageResourseBackground(R.id.flight_details, R.drawable.flight_detail_btn_white);
        } else {
            setImageResourseBackground(R.id.flight_details, R.drawable.flight_detail_btn_normal);
        }

        if (isVehicle || createOrderResponseDTO.getVehicleInfo().getVehicleMake().equalsIgnoreCase("")) {
            setImageResourseBackground(R.id.vehicle_details, R.drawable.vehicle_details_white);
        } else {
            setImageResourseBackground(R.id.vehicle_details, R.drawable.vehicle_details_normal);
        }

        if (isOrder) {
            setImageResourseBackground(R.id.order_confirmation, R.drawable.confirmation_btn_white);
        } else {
            setImageResourseBackground(R.id.order_confirmation, R.drawable.confirmation_btn_normal);
        }

        if (isService || (createOrderResponseDTO.getServiceInfo().getServices().size() == 0)) {
            setImageResourseBackground(R.id.service_details, R.drawable.add_servies_btn_white);
        } else {
            setImageResourseBackground(R.id.service_details, R.drawable.add_servies_btn_normal);
        }

        if (isPayment) {
            setImageResourseBackground(R.id.payment, R.drawable.payment_details_btn_white);
        } else {
            setImageResourseBackground(R.id.payment, R.drawable.payment_details_btn_normal);
        }
    }

    @Override
    public void onBackPressed() {
    }
}
