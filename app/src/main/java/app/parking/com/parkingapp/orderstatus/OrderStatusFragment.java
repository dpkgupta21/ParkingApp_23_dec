package app.parking.com.parkingapp.orderstatus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import app.parking.com.parkingapp.R;
import app.parking.com.parkingapp.fragments.BaseFragment;
import app.parking.com.parkingapp.model.CreateOrderResponseDTO;
import app.parking.com.parkingapp.model.DestinationFlightInfo;
import app.parking.com.parkingapp.model.FlightInfoDTO;
import app.parking.com.parkingapp.model.Service;
import app.parking.com.parkingapp.model.ServiceInfoDTO;
import app.parking.com.parkingapp.model.VehicleInfoDTO;
import app.parking.com.parkingapp.preferences.ParkingPreference;
import app.parking.com.parkingapp.webservices.handler.CreateOrderAPIHandler;
import app.parking.com.parkingapp.webservices.ihelper.WebAPIResponseListener;

public class OrderStatusFragment extends BaseFragment implements View.OnClickListener {

    private Toolbar mToolbar;
    private View mView;
    private CreateOrderResponseDTO createOrderResponseDTO;
    private Activity mActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.order_details_screen_new, container, false);
        return mView;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mActivity = getActivity();

        assignClicks();
        try {
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("email", ParkingPreference.getEmailId(mActivity));

            String auth = ParkingPreference.getKeyAuthtoken(mActivity);
            CreateOrderAPIHandler createOrderAPIHandler = new CreateOrderAPIHandler(getActivity(),
                    jsonObject.toString(), auth,
                    createOrderResponseListner());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private void assignClicks() {

        ImageView flight_details = (ImageView) mView.findViewById(R.id.flight_details);
        ImageView vehicle_details = (ImageView) mView.findViewById(R.id.vehicle_details);
        ImageView service_details = (ImageView) mView.findViewById(R.id.service_details);
        ImageView order_confirmation = (ImageView) mView.findViewById(R.id.order_confirmation);
        ImageView drop_off = (ImageView) mView.findViewById(R.id.drop_off);
        ImageView pick_up = (ImageView) mView.findViewById(R.id.pick_up);
        vehicle_details.setVisibility(View.VISIBLE);
        flight_details.setOnClickListener(this);
        vehicle_details.setOnClickListener(this);
        service_details.setOnClickListener(this);
        order_confirmation.setOnClickListener(this);
        drop_off.setOnClickListener(this);
        pick_up.setOnClickListener(this);


    }


    /**
     * Mananging response of orderCreation.
     *
     * @return
     */
    private WebAPIResponseListener createOrderResponseListner() {
        WebAPIResponseListener webAPIResponseListener = new WebAPIResponseListener() {
            @Override
            public void onSuccessOfResponse(Object... arguments) {

                String response = (String) arguments[0];
                createOrderResponseDTO = new Gson().fromJson(response,
                        CreateOrderResponseDTO.class);


            }

            @Override
            public void onFailOfResponse(Object... arguments) {

            }
        };
        return webAPIResponseListener;

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

        setViewText(R.id.txt_flight_number_val, arrivalFlighDTO.getFlightNumber(), mView);
        setViewText(R.id.txt_flight_name_val, arrivalFlighDTO.getFlightName(), mView);
        setViewText(R.id.txt_arrival_time_val, arrivalFlighDTO.getFlightArrivalTime(), mView);
        setViewText(R.id.txt_destination_time_val, arrivalFlighDTO.getFlightDepatureTime(), mView);
        setViewText(R.id.txt_origin_val, arrivalFlighDTO.getOrigin(), mView);
        setViewText(R.id.txt_destination_val, arrivalFlighDTO.getDestination(), mView);


        // Destination Flight
        DestinationFlightInfo destinationFlighDTO = flightInfoDTO.getDestinationFlight();

        setViewText(R.id.txt_dest_flight_number_val, destinationFlighDTO.getFlightNumber(), mView);
        setViewText(R.id.txt_dest_flight_name_val, destinationFlighDTO.getFlightName(), mView);
        setViewText(R.id.txt_dest_arrival_time_val, destinationFlighDTO.getFlightArrivalTime(), mView);
        setViewText(R.id.txt_dest_destination_time_val, destinationFlighDTO.getFlightDepatureTime(), mView);
        setViewText(R.id.txt_dest_origin_val, destinationFlighDTO.getOrigin(), mView);
        setViewText(R.id.txt_dest_destination_val, destinationFlighDTO.getDestination(), mView);

    }

    private void showVehicleInfo() {
        VehicleInfoDTO vehicleInfoDTO = createOrderResponseDTO.getVehicleInfo();

        setViewText(R.id.txt_vehicle_make_val, vehicleInfoDTO.getVehicleMake(), mView);
        setViewText(R.id.txt_vehicle_model_val, vehicleInfoDTO.getVehicleModel(), mView);
        setViewText(R.id.txt_vehicle_color_val, vehicleInfoDTO.getVehicleColor(), mView);
        setViewText(R.id.txt_vehicle_plate_number_val, "", mView);
    }

    private void showServiceInfo() {
        ServiceInfoDTO serviceInfoDTO = createOrderResponseDTO.getServiceInfo();
        List<Service> servicesList = serviceInfoDTO.getServices();
        for (Service mService : servicesList) {
            setViewText(R.id.txt_service_val, mService.getName(), mView);
            setViewText(R.id.txt_service_price_val, mService.getPrice(), mView);

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
                setViewVisibility(R.id.relative_flight_info, mView, View.VISIBLE);
                setViewVisibility(R.id.relative_vehicle_info, mView, View.GONE);
                setViewVisibility(R.id.relative_service_info, mView, View.GONE);
                setViewVisibility(R.id.relative_order_info, mView, View.GONE);
                setViewVisibility(R.id.relative_payment_info, mView, View.GONE);
                setViewVisibility(R.id.relative_drop_off_info, mView, View.GONE);
                setViewVisibility(R.id.relative_pick_up_info, mView, View.GONE);
                break;
            case 1:
                setViewVisibility(R.id.relative_flight_info, mView, View.GONE);
                setViewVisibility(R.id.relative_vehicle_info, mView, View.VISIBLE);
                setViewVisibility(R.id.relative_service_info, mView, View.GONE);
                setViewVisibility(R.id.relative_order_info, mView, View.GONE);
                setViewVisibility(R.id.relative_payment_info, mView, View.GONE);
                setViewVisibility(R.id.relative_drop_off_info, mView, View.GONE);
                setViewVisibility(R.id.relative_pick_up_info, mView, View.GONE);
                break;
            case 2:
                setViewVisibility(R.id.relative_flight_info, mView, View.GONE);
                setViewVisibility(R.id.relative_vehicle_info, mView, View.GONE);
                setViewVisibility(R.id.relative_service_info, mView, View.VISIBLE);
                setViewVisibility(R.id.relative_order_info, mView, View.GONE);
                setViewVisibility(R.id.relative_payment_info, mView, View.GONE);
                setViewVisibility(R.id.relative_drop_off_info, mView, View.GONE);
                setViewVisibility(R.id.relative_pick_up_info, mView, View.GONE);
                break;
            case 3:
                setViewVisibility(R.id.relative_flight_info, mView, View.GONE);
                setViewVisibility(R.id.relative_vehicle_info, mView, View.GONE);
                setViewVisibility(R.id.relative_service_info, mView, View.GONE);
                setViewVisibility(R.id.relative_order_info, mView, View.VISIBLE);
                setViewVisibility(R.id.relative_payment_info, mView, View.GONE);
                setViewVisibility(R.id.relative_drop_off_info, mView, View.GONE);
                setViewVisibility(R.id.relative_pick_up_info, mView, View.GONE);
                break;
            case 4:
                setViewVisibility(R.id.relative_flight_info, mView, View.GONE);
                setViewVisibility(R.id.relative_vehicle_info, mView, View.GONE);
                setViewVisibility(R.id.relative_service_info, mView, View.GONE);
                setViewVisibility(R.id.relative_order_info, mView, View.GONE);
                setViewVisibility(R.id.relative_payment_info, mView, View.VISIBLE);
                setViewVisibility(R.id.relative_drop_off_info, mView, View.GONE);
                setViewVisibility(R.id.relative_pick_up_info, mView, View.GONE);
                break;
            case 5:
                setViewVisibility(R.id.relative_flight_info, mView, View.GONE);
                setViewVisibility(R.id.relative_vehicle_info, mView, View.GONE);
                setViewVisibility(R.id.relative_service_info, mView, View.GONE);
                setViewVisibility(R.id.relative_order_info, mView, View.GONE);
                setViewVisibility(R.id.relative_payment_info, mView, View.GONE);
                setViewVisibility(R.id.relative_drop_off_info, mView, View.VISIBLE);
                setViewVisibility(R.id.relative_pick_up_info, mView, View.GONE);
                break;
            case 6:
                setViewVisibility(R.id.relative_flight_info, mView, View.GONE);
                setViewVisibility(R.id.relative_vehicle_info, mView, View.GONE);
                setViewVisibility(R.id.relative_service_info, mView, View.GONE);
                setViewVisibility(R.id.relative_order_info, mView, View.GONE);
                setViewVisibility(R.id.relative_payment_info, mView, View.GONE);
                setViewVisibility(R.id.relative_drop_off_info, mView, View.GONE);
                setViewVisibility(R.id.relative_pick_up_info, mView, View.VISIBLE);
                break;

        }
    }

}
